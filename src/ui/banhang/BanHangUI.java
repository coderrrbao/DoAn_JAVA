package ui.banhang;

import bus.HangThanhVienBUS;
import bus.HoaDonBUS;
import bus.KhachHangBUS;
import bus.KhuyenMaiBUS;
import bus.SanPhamBUS;
import dto.*;
import ui.component.BoLocListener;
import ui.component.SanPhamClickListener;
import ui.login.LoginUI;
import ui.login.PhienDangNhap;
import util.Xulypdf;

import java.awt.*;
import java.util.ArrayList;
import java.sql.Date;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class BanHangUI extends JPanel {

    private HoaDonBUS hoaDonBUS = new HoaDonBUS();
    private SanPhamBUS sanPhamBUS = SanPhamBUS.getSanPhamBUS();
    private Xulypdf xulyPDF = new Xulypdf();

    private KhuyenMai maGiamGiaDangDung = null;

    private ThongTinKhachHangPanel thongTinKhachHangPanel;
    private ThanhToanPanel thanhToanPanel;
    private ThongTinHoaDonPanel thongTinHoaDonPanel;
    private ListSanPhamPanel listSanPhamPanel;
    private BoLocPanel boLocPanel;
    private Runnable onThanhToanSuccess;

    public void setOnThanhToanSuccess(Runnable onThanhToanSuccess) {
        this.onThanhToanSuccess = onThanhToanSuccess;
    }

    public BanHangUI() {
        setLayout(new GridLayout(1, 2, 0, 0));
        setBackground(Color.white);
        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel rightPanel = new JPanel(new BorderLayout());

        add(leftPanel);
        add(rightPanel);

        thongTinKhachHangPanel = new ThongTinKhachHangPanel();
        leftPanel.add(thongTinKhachHangPanel, BorderLayout.NORTH);

        KhachHangBUS khachHangBUS = new KhachHangBUS();

        thongTinKhachHangPanel.getTxtSdt().getDocument().addDocumentListener(
                new javax.swing.event.DocumentListener() {
                    private void xuLy() {
                        String sdt = thongTinKhachHangPanel.getTxtSdt().getText().trim();
                        var kh = khachHangBUS.timTheoSDT(sdt);
                        if (kh != null) {
                            thongTinKhachHangPanel.getTxtTenKh().setText(kh.getTenKH());
                            thongTinKhachHangPanel.getTxtTenKh().setEditable(false);
                            capNhatGiaoDien();
                        } else {
                            thongTinKhachHangPanel.getTxtTenKh().setText("");
                            thongTinKhachHangPanel.getTxtTenKh().setEditable(true);
                            capNhatGiaoDien();
                        }
                    }

                    public void insertUpdate(javax.swing.event.DocumentEvent e) {
                        xuLy();
                    }

                    public void removeUpdate(javax.swing.event.DocumentEvent e) {
                        xuLy();
                    }

                    public void changedUpdate(javax.swing.event.DocumentEvent e) {
                        xuLy();
                    }
                });

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
                for (SanPham sanPham : dsTatCaSP) {
                    if (sanPham.getDanhMuc().getTenDM().equals("Topping") && !sanPham.getTrangThaiXuLy()
                            .equals("Ẩn")) {
                        dsTopping.add(sanPham);
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
                        tpCopy.setTenSP("  ->" + tp.getTenSP());
                        tpCopy.setGiaBan(tp.getGiaBan());
                        tpCopy.setDanhMuc(tp.getDanhMuc());
                        tpCopy.setLoaiNuoc(tp.getLoaiNuoc());

                        thongTinHoaDonPanel.themSanPham(tpCopy);
                    }
                }
            }
        });

        ArrayList<SanPham> dsBanDau = locDanhSachKhongTopping(sanPhamBUS.layListSanPham());
        listSanPhamPanel.render(dsBanDau);
        rightPanel.add(listSanPhamPanel, BorderLayout.CENTER);

        boLocPanel = new BoLocPanel();
        boLocPanel.setboLocListener(new BoLocListener() {
            @Override
            public void onLoc(ArrayList<SanPham> ds) {
                listSanPhamPanel.render(locDanhSachKhongTopping(ds));
            }

            @Override
            public void onLamMoi() {
                SanPhamBUS.getSanPhamBUS().khoitao();
                ArrayList<SanPham> dsMoi = locDanhSachKhongTopping(SanPhamBUS.getSanPhamBUS().layListSanPham());
                listSanPhamPanel.render(dsMoi);
            }
        });
        rightPanel.add(boLocPanel, BorderLayout.NORTH);

        ganSuKienThanhToan();
        ganSuKienGiamGia();
        ganSuKienHuy();
        loadDanhSachKhuyenMai();
    }

    public void loadDuLieu() {
        loadDanhSachKhuyenMai();
        thongTinKhachHangPanel.loadDataKhachHang();
        listSanPhamPanel.renderTrang();
    }

    public void loadDanhSachKhuyenMai() {
        KhuyenMaiBUS kmBUS = KhuyenMaiBUS.getKhuyenMaiBUS();
        ArrayList<KhuyenMai> ds = kmBUS.layListKhuyenMai();
        if (ds != null) {
            thanhToanPanel.getCbxKhuyenMai().removeAllItems();
            thanhToanPanel.getCbxKhuyenMai().addItem("-- Không áp dụng --");
            for (KhuyenMai mgg : ds) {
                if (kmBUS.kiemTraTrangThaiHopLe(mgg).isEmpty()) {
                    thanhToanPanel.getCbxKhuyenMai().addItem(mgg.getMaKM() + " - Giảm " + mgg.getPhanTramGiam() + "%");
                }
            }
        }
    }

    private void ganSuKienGiamGia() {
        thanhToanPanel.getBtnXacNhanMGG().addActionListener(e -> {
            String inputCode = thanhToanPanel.getMaGiamGiaInput();

            if (thanhToanPanel.getBtnXacNhanMGG().getText().equals("Áp dụng")
                    || thanhToanPanel.getBtnXacNhanMGG().getText().equals("Xác nhận")) {
                if (inputCode.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn mã khuyến mãi từ danh sách!");
                    return;
                }
                KhuyenMaiBUS khuyenMaiBUS = KhuyenMaiBUS.getKhuyenMaiBUS();
                KhuyenMai mgg = khuyenMaiBUS.timKhuyenMai(inputCode);

                if (mgg != null) {
                    String thongBaoLoi = khuyenMaiBUS.kiemTraTrangThaiHopLe(mgg);
                    if (thongBaoLoi.isEmpty()) {
                        maGiamGiaDangDung = mgg;
                        lockMaGiamGia(true);
                        capNhatGiaoDien();
                    } else {
                        JOptionPane.showMessageDialog(this, thongBaoLoi, "Lỗi Khuyến Mãi", JOptionPane.ERROR_MESSAGE);
                        resetGiamGia();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Mã giảm giá không tồn tại hoặc đã bị xóa!");
                    resetGiamGia();
                }
            } else {
                resetGiamGia();
                lockMaGiamGia(false);
            }
        });
    }

    private void lockMaGiamGia(boolean lock) {
        thanhToanPanel.getCbxKhuyenMai().setEnabled(!lock);
        thanhToanPanel.getBtnXacNhanMGG().setText(lock ? "Hủy" : "Áp dụng");
    }

    private ArrayList<SanPham> locDanhSachKhongTopping(ArrayList<SanPham> dsGoc) {
        ArrayList<SanPham> dsDaLoc = new ArrayList<>();
        if (dsGoc != null) {
            for (SanPham sp : dsGoc) {
                if (sp.getDanhMuc() != null && !sp.getDanhMuc().getTenDM().equals("Topping")) {
                    dsDaLoc.add(sp);
                }
            }
        }
        return dsDaLoc;
    }

    private void resetGiamGia() {
        maGiamGiaDangDung = null;
        thanhToanPanel.getCbxKhuyenMai().setSelectedIndex(0);
        capNhatGiaoDien();
    }

    private void ganSuKienHuy() {
        thanhToanPanel.getBtnHuy().addActionListener(e -> {
            DefaultTableModel model = thongTinHoaDonPanel.getModel();
            String sdt = thongTinKhachHangPanel.getTxtSdt().getText().trim();

            if (model.getRowCount() > 0 || !sdt.isEmpty()) {
                int luaChon = JOptionPane.showConfirmDialog(this,
                        "Bạn có chắc chắn muốn làm mới (hủy) hóa đơn đang tạo không?",
                        "Xác nhận hủy", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (luaChon == JOptionPane.YES_OPTION) {
                    model.setRowCount(0);
                    thongTinKhachHangPanel.getTxtSdt().setText("");
                    thongTinKhachHangPanel.getTxtTenKh().setText("");
                    resetGiamGia();
                    lockMaGiamGia(false);
                }
            }
        });
    }

    private void ganSuKienThanhToan() {

        thongTinHoaDonPanel.getTable().getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    if (e.getColumn() != 2) {
                        return;
                    }
                    try {
                        int soLuong = Integer.parseInt(thongTinHoaDonPanel.getModel().getValueAt(row, 2).toString());
                        if (soLuong < 0) {
                            JOptionPane.showMessageDialog(null, "Số lượng sản phẩm không được để số âm!", "Lỗi",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        int gia = Integer.parseInt(thongTinHoaDonPanel.getModel().getValueAt(row, 1).toString());
                        thongTinHoaDonPanel.getModel().setValueAt(soLuong * gia, row, 3);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Lỗi", "Lỗi định dạng sản phẩm", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });

        thanhToanPanel.getBtnThanhToan().addActionListener(e -> {
            DefaultTableModel model = thongTinHoaDonPanel.getModel();
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Giỏ hàng đang trống! Vui lòng chọn món.");
                return;
            }
            DefaultTableModel modeCt = thongTinHoaDonPanel.getModel();
            for (int i = 0; i < modeCt.getRowCount(); i++) {
                try {
                    if (Integer.parseInt(modeCt.getValueAt(i, 2).toString()) < 0) {
                        JOptionPane.showMessageDialog(this, "Số lượng sản phẩm không được để số âm!");
                        return;
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi định dạng số lượng sản phẩm!");
                    return;
                }

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
            String sdtNhap = thongTinKhachHangPanel.getTxtSdt().getText().trim();
            String tenNhap = thongTinKhachHangPanel.getTxtTenKh().getText().trim();
            KhachHang khChon = thongTinKhachHangPanel.getKhachHangDuocChon();

            if (!sdtNhap.isEmpty() && khChon == null) {
                if (tenNhap.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Phát hiện Số điện thoại mới! Vui lòng điền Tên Khách Hàng để lưu thông tin tích điểm.",
                            "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                    thongTinKhachHangPanel.getTxtTenKh().requestFocus();
                    return;
                }

                KhachHangBUS khBUS = new KhachHangBUS();
                String maKHMoi = khBUS.taoMaKHMoi();

                KhachHang khMoi = new KhachHang(maKHMoi, tenNhap, "Khác", sdtNhap, 0.0, "HTV01");
                khMoi.setTrangThai(true);

                try {
                    if (khBUS.themKhachHang(khMoi)) {
                        hd.setMaKH(maKHMoi);
                        System.out.println("Đã tự động lưu Khách hàng mới: " + tenNhap);
                    } else {
                        JOptionPane.showMessageDialog(this, "Lỗi khi lưu thông tin khách hàng mới vào Database!");
                        return;
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
                }

            } else if (khChon != null) {
                hd.setMaKH(khChon.getMaKH());

            } else {
                hd.setMaKH(null);
            }

            hd.setTienKhuyenMai(thanhToanPanel.getTienKhuyenMai());

            if (maGiamGiaDangDung != null) {
                hd.setMaGiamGia(maGiamGiaDangDung);
            } else {
                hd.setMaGiamGia(null);
            }
            hd.setTrangThai(true);

            ArrayList<ChiTietHoaDon> listCT = new ArrayList<>();

            for (int i = 0; i < model.getRowCount(); i++) {
                String tenSP = model.getValueAt(i, 0).toString();
                double giaBan = Double.parseDouble(model.getValueAt(i, 1).toString());
                int soLuong = Integer.parseInt(model.getValueAt(i, 2).toString());

                SanPham spGoc = SanPhamBUS.getSanPhamBUS().timSanPhamTheoTen(tenSP);
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
                } else if (spGoc.getListSize() != null && !spGoc.getListSize().isEmpty()
                        && !tenSP.startsWith("  ->")) {
                    sizeChon = spGoc.getListSize().get(0);
                }
                ct.setSize(sizeChon);
                listCT.add(ct);
            }
            hd.setListChiTietHoaDon(listCT);

            ArrayList<String> loiTonKho = hoaDonBUS.kiemTraTonKho(hd);
            String loi = "";

            for (String loiitem : loiTonKho) {
                loi += "\n " + loiitem;
            }

            System.out.println(loi);
            if (loiTonKho.size() != 0) {
                JOptionPane.showMessageDialog(this, loi, "Cảnh báo kho hàng", JOptionPane.WARNING_MESSAGE);
                return;
            }
            ArrayList<String> listThongBao = hoaDonBUS.ThanhToan(hd);
            if (listThongBao != null) {
                if (hd.getMaKH() != null) {
                    KhachHangBUS khBUS = new KhachHangBUS();
                    khBUS.capNhatTienDaMua(hd.getMaKH(), hd.getTongTien());
                }

                String thongBao = "";
                for (String tb : listThongBao) {
                    thongBao += tb + "\n";
                }

                JOptionPane.showMessageDialog(null, thongBao);

                int luaChon = JOptionPane.showConfirmDialog(this,
                        "Thanh toán thành công! Bạn có muốn in hóa đơn không?", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (luaChon == JOptionPane.YES_OPTION) {
                    xulyPDF.xuatHoaDon(hd);
                }

                model.setRowCount(0);
                thongTinKhachHangPanel.getTxtSdt().setText("");
                thongTinKhachHangPanel.getTxtTenKh().setText("");
                resetGiamGia();
                lockMaGiamGia(false);
                LoginUI.getLoginUI().getMainFrame().loadAllData();

                if (onThanhToanSuccess != null) {
                    onThanhToanSuccess.run();
                }

            } else {
                JOptionPane.showMessageDialog(this,
                        "Thanh toán thất bại! Vui lòng kiểm tra lại kết nối hoặc kho hàng.");
            }
        });
    }

    private void capNhatGiaoDien() {
        double tongTienHang = thongTinHoaDonPanel.layTongTienHang();
        double tienGiam = 0;
        double tongPhanTramGiam = 0;

        if (maGiamGiaDangDung != null) {
            tongPhanTramGiam += maGiamGiaDangDung.getPhanTramGiam();
        }

        KhachHang khChon = thongTinKhachHangPanel.getKhachHangDuocChon();

        if (khChon != null && khChon.getMaHang() != null && !khChon.getMaHang().isEmpty()) {
            HangThanhVienBUS htvBus = new HangThanhVienBUS();

            ArrayList<HangThanhVien> listHtv = htvBus.layListHangThanhVien();

            if (listHtv != null) {
                for (HangThanhVien htv : listHtv) {
                    if (htv.getMaHang().equals(khChon.getMaHang())) {

                        tongPhanTramGiam += htv.getPhanTramGiam();

                        break;
                    }
                }
            }
        }

        tienGiam += tongTienHang * (tongPhanTramGiam / 100.0);

        if (tienGiam > tongTienHang) {
            tienGiam = tongTienHang;
        }

        thanhToanPanel.capNhatThongTinThanhToan(tongTienHang, tienGiam);
    }
}