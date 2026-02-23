package ui.banhang;

import bus.HoaDonBUS;
import bus.MaGiamGiaBUS;
import bus.SanPhamBUS;
import bus.ThongtinKhachHangBUS;
import dto.*;
import ui.component.BoLocListener;
import ui.component.SanPhamClickListener;
import ui.login.PhienDangNhap;
import util.Xulypdf;

import java.awt.*;
import java.util.ArrayList;
import java.sql.Date;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class BanHangUI extends JPanel {

    private HoaDonBUS hoaDonBUS = new HoaDonBUS();
    private SanPhamBUS sanPhamBUS = new SanPhamBUS();
    private Xulypdf xulyPDF = new Xulypdf();

    private MaGiamGia maGiamGiaDangDung = null;
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
                ArrayList<SanPham> dsTopping = new ArrayList<>();
                ArrayList<SanPham> dsTatCaSP = sanPhamBUS.layListSanPham();
                for (SanPham s : dsTatCaSP) {
                    if ("DM10".equals(s.getDanhMuc().getMaDM())) {
                        dsTopping.add(s);
                    }
                }
                ArrayList<Size> listSize = sp.getListSize();
                if ((listSize == null || listSize.isEmpty()) && "Có sẵn".equals(sp.getLoaiNuoc())) {
                    thongTinHoaDonPanel.themSanPham(sp);
                    return;
                }

                Window ancestor = SwingUtilities.getWindowAncestor(BanHangUI.this);
                TuyChonDialog dialog = new TuyChonDialog((Frame) ancestor, sp, listSize, dsTopping);
                dialog.setVisible(true);

                if (dialog.isXacNhan()) {
                    Size sizeChon = dialog.getSizeDuocChon();
                    ArrayList<SanPham> toppingChon = dialog.getToppingDuocChon();

                    SanPham monChinh = new SanPham();
                    monChinh.setMaSP(sp.getMaSP());
                    monChinh.setTenSP(sp.getTenSP());
                    monChinh.setGiaBan(sp.getGiaBan());
                    monChinh.setDanhMuc(sp.getDanhMuc());
                    monChinh.setLoaiNuoc(sp.getLoaiNuoc());
                    monChinh.setListSize(sp.getListSize());

                    if (sizeChon != null) {
                        monChinh.setTenSP(sp.getTenSP() + " (" + sizeChon.getTenSize() + ")");
                        double giaMoi = sp.getGiaBan() + (sp.getGiaBan() * sizeChon.getPhanTramGia() / 100.0);
                        monChinh.setGiaBan((long) giaMoi);
                    }

                    thongTinHoaDonPanel.themSanPham(monChinh);

                    for (SanPham tp : toppingChon) {
                        SanPham tpCopy = new SanPham();
                        tpCopy.setMaSP(tp.getMaSP());
                        tpCopy.setTenSP("  ↳ " + tp.getTenSP());
                        tpCopy.setGiaBan(tp.getGiaBan());
                        tpCopy.setDanhMuc(tp.getDanhMuc());
                        tpCopy.setLoaiNuoc(tp.getLoaiNuoc());

                        thongTinHoaDonPanel.themSanPham(tpCopy);
                    }
                }
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
                if (inputCode.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập mã!");
                    return;
                }
                
                MaGiamGiaBUS maGiamGiaBUS = new MaGiamGiaBUS();
                MaGiamGia mgg = maGiamGiaBUS.timMaGiamGia(inputCode);

                if (mgg != null) {
                    String thongBaoLoi = maGiamGiaBUS.kiemTraTrangThaiHopLe(mgg);
                    if (thongBaoLoi.isEmpty()) {
                        maGiamGiaDangDung = mgg;
                        JOptionPane.showMessageDialog(this, "Áp dụng mã thành công! Giảm " + mgg.getPhanTramGiam() + "%");
                        lockMaGiamGia(true);
                        capNhatGiaoDien();
                    } else {
                        JOptionPane.showMessageDialog(this, thongBaoLoi, "Lỗi Khuyến Mãi", JOptionPane.ERROR_MESSAGE);
                        resetGiamGia();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Mã giảm giá không tồn tại!");
                    resetGiamGia();
                }
            }
            else {
                resetGiamGia();
                lockMaGiamGia(false);
            }
        });
    }

    private void lockMaGiamGia(boolean lock) {
        thanhToanPanel.getTxtMaGiamGia().setEditable(!lock);
        thanhToanPanel.getBtnXacNhanMGG().setText(lock ? "Hủy" : "Xác nhận");
    }

    private void resetGiamGia() {
        maGiamGiaDangDung = null;
        thanhToanPanel.getTxtMaGiamGia().setText("");
        capNhatGiaoDien();
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


            NhanVien nvDangNhap = PhienDangNhap.getUser();
            if (nvDangNhap != null) {
                hd.setNhanVien(nvDangNhap);
            }


            hd.setTongTien(thanhToanPanel.getTongThanhToan());

            double tongTienHang = thongTinHoaDonPanel.layTongTienHang();
            double tienDaGiam = tongTienHang - hd.getTongTien();
            hd.setTienKhuyenMai(tienDaGiam);

            if(maGiamGiaDangDung != null) {
                hd.setMaGiamGia(maGiamGiaDangDung);
            }
            else {
                hd.setMaGiamGia(null);
            }
            hd.setTrangThai(true);

            ArrayList<ChiTietHoaDon> listCT = new ArrayList<>();
            ArrayList<SanPham> dsSanPhamHienCo = sanPhamBUS.layListSanPham();

            for (int i = 0; i < model.getRowCount(); i++) {
                String tenSP = model.getValueAt(i, 0).toString();
                double giaBan = Double.parseDouble(model.getValueAt(i, 1).toString());
                int soLuong = Integer.parseInt(model.getValueAt(i, 2).toString());

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

                Size sizeChon = null;
                if (tenSP.contains(" (")) {
                    String tenSizeUI = tenSP.substring(tenSP.lastIndexOf(" (") + 2, tenSP.lastIndexOf(")"));

                    if (spGoc.getListSize() != null) {
                        for (Size s : spGoc.getListSize()) {
                            if (s.getTenSize().equalsIgnoreCase(tenSizeUI)) {
                                sizeChon = s;
                                break;
                            }
                        }
                    }
                } else if (spGoc.getListSize() != null && !spGoc.getListSize().isEmpty() && !tenSP.startsWith("  ↳ ")) {
                    sizeChon = spGoc.getListSize().get(0);
                }
                ct.setSize(sizeChon);
                listCT.add(ct);
            }
            hd.setListChiTietHoaDon(listCT);

            String loiTonKho = hoaDonBUS.kiemTraTonKho(hd);
            if (loiTonKho != null) {
                JOptionPane.showMessageDialog(this, loiTonKho, "Cảnh báo kho hàng", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (hoaDonBUS.ThanhToan(hd)) {
                int luaChon = JOptionPane.showConfirmDialog(this, "Thanh toán thành công! Bạn có muốn in hóa đơn không?", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (luaChon == JOptionPane.YES_OPTION) { xulyPDF.xuatHoaDon(hd); }

                model.setRowCount(0);
                thongTinKhachHangPanel.getTxtSdt().setText("");
                thongTinKhachHangPanel.getTxtTenKh().setText("");
                resetGiamGia();
                lockMaGiamGia(false);

            } else {
                JOptionPane.showMessageDialog(this, "Thanh toán thất bại! Vui lòng kiểm tra lại kết nối hoặc kho hàng.");
            }
        });
    }

    private SanPham timSanPhamTheoTen(ArrayList<SanPham> list, String tenGiaoDien) {
        String tenGoc = tenGiaoDien.replace("  ↳ ", "");

        if (tenGoc.contains(" (")) {
            tenGoc = tenGoc.substring(0, tenGoc.lastIndexOf(" ("));
        }

        for (SanPham sp : list) {
            if (sp.getTenSP().equalsIgnoreCase(tenGoc.trim())) {
                return sp;
            }
        }
        return null;
    }

    private void capNhatGiaoDien() {
        double tongTienHang = thongTinHoaDonPanel.layTongTienHang();
        double tienGiam = 0;

        if (maGiamGiaDangDung != null) {
            tienGiam = tongTienHang * (maGiamGiaDangDung.getPhanTramGiam() / 100.0);
        }

        if (tienGiam > tongTienHang) tienGiam = tongTienHang;

        thanhToanPanel.capNhatThongTinThanhToan(tongTienHang, tienGiam);
    }
}
