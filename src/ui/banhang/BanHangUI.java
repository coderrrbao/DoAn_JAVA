package ui.banhang;

import bus.HoaDonBUS;
import bus.SanPhamBUS;
import bus.ThongtinKhachHangBUS;
import dto.ChiTietHoaDon;
import dto.HoaDon;
import dto.NhanVien;
import dto.SanPham;
import dto.Size;
import ui.component.BoLocListener;
import ui.component.SanPhamClickListener;
import util.Xulypdf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.sql.Date;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class BanHangUI extends JPanel {

    private HoaDonBUS hoaDonBUS = new HoaDonBUS();
    private SanPhamBUS sanPhamBUS = new SanPhamBUS();
    private Xulypdf xulyPDF = new Xulypdf();

    private double phanTramGiam = 0;
    private double tienGiamGiaTrucTiep = 0;

    private ThongTinKhachHangPanel thongTinKhachHangPanel;
    private ThanhToanPanel thanhToanPanel;
    private ThongTinHoaDonPanel thongTinHoaDonPanel;
    private ListSanPhamPanel listSanPhamPanel;
    private BoLocPanel boLocPanel;

    public BanHangUI() {
        setLayout(new GridLayout(1, 2, 0, 0));
        setBackground(Color.white);
        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel rightPanel = new JPanel(new BorderLayout());

        add(leftPanel);
        add(rightPanel);

        thongTinKhachHangPanel = new ThongTinKhachHangPanel();
        leftPanel.add(thongTinKhachHangPanel, BorderLayout.NORTH);

        ThongtinKhachHangBUS khachHangBUS = new ThongtinKhachHangBUS();

        thongTinKhachHangPanel.getTxtSdt().getDocument().addDocumentListener(
                new javax.swing.event.DocumentListener() {
                    private void xuLy() {
                        String sdt = thongTinKhachHangPanel.getTxtSdt().getText().trim();
                        var kh = khachHangBUS.timTheoSDT(sdt);
                        if (kh != null) {
                            thongTinKhachHangPanel.getTxtTenKh().setText(kh.getTenKH());
                            thongTinKhachHangPanel.getTxtTenKh().setEditable(false);
                        } else {
                            thongTinKhachHangPanel.getTxtTenKh().setText("");
                            thongTinKhachHangPanel.getTxtTenKh().setEditable(true);
                        }
                    }
                    public void insertUpdate(javax.swing.event.DocumentEvent e) { xuLy(); }
                    public void removeUpdate(javax.swing.event.DocumentEvent e) { xuLy(); }
                    public void changedUpdate(javax.swing.event.DocumentEvent e) { xuLy(); }
                }
        );

        thanhToanPanel = new ThanhToanPanel();
        leftPanel.add(thanhToanPanel, BorderLayout.SOUTH);

        thongTinHoaDonPanel = new ThongTinHoaDonPanel();
        leftPanel.add(thongTinHoaDonPanel, BorderLayout.CENTER);

        thongTinHoaDonPanel.getModel().addTableModelListener(e -> {
            capNhatGiaoDien();
        });

        listSanPhamPanel = new ListSanPhamPanel();
        listSanPhamPanel.setListener(new SanPhamClickListener() {
            @Override
            public void onSanPhamClicked(SanPham sp) {
                thongTinHoaDonPanel.themSanPham(sp);
            }
        });
        listSanPhamPanel.reset();
        rightPanel.add(listSanPhamPanel, BorderLayout.CENTER);

        boLocPanel = new BoLocPanel();
        boLocPanel.setboLocListener(new BoLocListener() {
            @Override
            public void onLoc(ArrayList<SanPham> ds) {
                listSanPhamPanel.render(ds);
            }
            @Override
            public void onLamMoi() {
                SanPhamBUS.getSanPhamBUS().khoitao();
                listSanPhamPanel.reset();
            }
        });
        rightPanel.add(boLocPanel, BorderLayout.NORTH);

        ganSuKienThanhToan();
        ganSuKienGiamGia();
    }

    private void ganSuKienGiamGia() {
        thanhToanPanel.getBtnXacNhanMGG().addActionListener(e -> {
            String inputCode = thanhToanPanel.getMaGiamGiaInput();
            if (thanhToanPanel.getBtnXacNhanMGG().getText().equals("Xác nhận")) {
                if (inputCode.equalsIgnoreCase("GIAM10")) {
                    phanTramGiam = 0.1; // 10%
                    tienGiamGiaTrucTiep = 0;
                    JOptionPane.showMessageDialog(this, "Áp dụng mã giảm 10% thành công!");
                    lockMaGiamGia(true);
                } else if (inputCode.equalsIgnoreCase("TRU20K")) {
                    phanTramGiam = 0;
                    tienGiamGiaTrucTiep = 20000;
                    JOptionPane.showMessageDialog(this, "Áp dụng mã trừ 20k thành công!");
                    lockMaGiamGia(true);
                } else if (inputCode.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập mã!");
                } else {
                    JOptionPane.showMessageDialog(this, "Mã giảm giá không tồn tại!");
                    resetGiamGia();
                }
            } else {
                resetGiamGia();
                lockMaGiamGia(false);
            }
            capNhatGiaoDien();
        });
    }

    private void lockMaGiamGia(boolean lock) {
        thanhToanPanel.getTxtMaGiamGia().setEditable(!lock);
        thanhToanPanel.getBtnXacNhanMGG().setText(lock ? "Hủy" : "Xác nhận");
    }

    private void resetGiamGia() {
        phanTramGiam = 0;
        tienGiamGiaTrucTiep = 0;
        thanhToanPanel.getTxtMaGiamGia().setText("");
    }

    private void ganSuKienThanhToan() {
        thanhToanPanel.getBtnThanhToan().addActionListener(e -> {
            DefaultTableModel model = thongTinHoaDonPanel.getModel();
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Giỏ hàng đang trống! Vui lòng chọn món.");
                return;
            }

            HoaDon hd = new HoaDon();
            String maHDMoi = hoaDonBUS.taoMaHoaDonMoi();
            hd.setMaHD(maHDMoi);

            long millis = System.currentTimeMillis();
            hd.setNgayBan(new Date(millis));

            NhanVien nvDemo = new NhanVien();
            nvDemo.setMaNV("NV01");
            nvDemo.setTenNV("Nguyễn Văn A");
            hd.setMaKH("KH001");
            hd.setNhanVien(nvDemo);

            hd.setTongTien(thanhToanPanel.getTongThanhToan());

            double tongTienHang = thongTinHoaDonPanel.layTongTienHang();
            double tienDaGiam = tongTienHang - hd.getTongTien();
            hd.setTienKhuyenMai(tienDaGiam);

            hd.setTrangThai(true);

            ArrayList<ChiTietHoaDon> listCT = new ArrayList<>();
            ArrayList<SanPham> dsSanPhamHienCo = sanPhamBUS.layListSanPham();

            for (int i = 0; i < model.getRowCount(); i++) {
                String tenSP = model.getValueAt(i, 0).toString();
                double giaBan = Double.parseDouble(model.getValueAt(i, 1).toString());
                int soLuong = Integer.parseInt(model.getValueAt(i, 2).toString());
                // double thanhTien = Double.parseDouble(model.getValueAt(i, 3).toString());

                SanPham spGoc = timSanPhamTheoTen(dsSanPhamHienCo, tenSP);
                if (spGoc == null) {
                    JOptionPane.showMessageDialog(this, "Lỗi: Không tìm thấy mã sản phẩm cho " + tenSP);
                    return;
                }

                ChiTietHoaDon ct = new ChiTietHoaDon();
                ct.setMaCTHD(hd.getMaHD() + "CT" + (i + 1));
                ct.setMaHD(hd.getMaHD());
                ct.setSanPham(spGoc);
                ct.setSoLuong(soLuong);
                ct.setGia(giaBan);

                Size sizeMacDinh = new Size();
                if (spGoc.getLoaiNuoc().equals("Pha chế") && spGoc.getListSize() != null && !spGoc.getListSize().isEmpty()) {
                    sizeMacDinh.setMaSize(spGoc.getMaSP().replace("SP", "SZ")+ "_S");
                    ct.setSize(sizeMacDinh);
                } else {
                    ct.setSize(null);
                }
                listCT.add(ct);
            }
            hd.setListChiTietHoaDon(listCT);

            String loiTonKho = hoaDonBUS.kiemTraTonKho(hd);

            if (loiTonKho != null) {
                JOptionPane.showMessageDialog(this, loiTonKho, "Cảnh báo kho hàng", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (hoaDonBUS.ThanhToan(hd)) {

                int luaChon = JOptionPane.showConfirmDialog(this,
                        "Thanh toán thành công! Bạn có muốn in hóa đơn không?",
                        "Thông báo", JOptionPane.YES_NO_OPTION);

                if (luaChon == JOptionPane.YES_OPTION) {
                    xulyPDF.xuatHoaDon(hd);
                }


                model.setRowCount(0);
                thanhToanPanel.repaint();
                thongTinKhachHangPanel.getTxtSdt().setText("");
                thongTinKhachHangPanel.getTxtTenKh().setText("");
                resetGiamGia();
                lockMaGiamGia(false);
                capNhatGiaoDien();

            } else {
                JOptionPane.showMessageDialog(this, "Thanh toán thất bại! Vui lòng kiểm tra lại kết nối hoặc kho hàng.");
            }
        });
    }

    private SanPham timSanPhamTheoTen(ArrayList<SanPham> list, String ten) {
        for (SanPham sp : list) {
            if (sp.getTenSP().equals(ten)) {
                return sp;
            }
        }
        return null;
    }

    private void capNhatGiaoDien() {
        double tongTienHang = thongTinHoaDonPanel.layTongTienHang();

        double tienGiam = (tongTienHang * phanTramGiam) + tienGiamGiaTrucTiep;

        if (tienGiam > tongTienHang) tienGiam = tongTienHang;

        thanhToanPanel.capNhatThongTinThanhToan(tongTienHang, tienGiam);
    }
}