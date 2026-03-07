package ui.nhapkho.sanpham;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import bus.NhaCungCapBUS;
import bus.PhieuNhapSanPhamBUS;
import dto.NhaCungCap;
import dto.PhieuNhapSanPham;
import ui.component.LocNgay_Item;
import ui.login.PhienDangNhap;
import util.TaoTinNhan;
import util.TaoUI;

public class NhapKhoSanPhamPanel extends JPanel {
    private JButton nhapHangBtn, xemChiTietBtn, xoaBtn, NhapExcelBtn, XuatExcelBtn;
    private LocNgay_Item locNgay_Item;
    private JTable table;
    private DefaultTableModel model;

    public NhapKhoSanPhamPanel() {
        setLayout(new BorderLayout());
        JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 45);
        top.setBackground(Color.WHITE);
        nhapHangBtn = new JButton("Thêm");
        xemChiTietBtn = new JButton("Xem Chi tiết");
        xoaBtn = new JButton("Xóa");

        TaoUI.setFixSize(nhapHangBtn, 100, 32);
        TaoUI.setFixSize(xemChiTietBtn, 150, 32);
        TaoUI.setFixSize(xoaBtn, 100, 32);

        locNgay_Item = new LocNgay_Item(400, 32);
        top.add(locNgay_Item);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(nhapHangBtn);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(xemChiTietBtn);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(xoaBtn);
        top.add(Box.createHorizontalGlue());

        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel();
        model.addColumn("Mã Phiếu nhập");
        model.addColumn("Ngày nhập");
        model.addColumn("Nhân viên tạo phiếu");
        model.addColumn("Ghi chú");
        model.addColumn("Nhà cung cấp");
        model.addColumn("Trạng thái");

        JScrollPane scrollPane = TaoUI.taoTableScroll(model);
        table = (JTable) scrollPane.getViewport().getView();

        add(scrollPane, BorderLayout.CENTER);

        loadDuLieu();
        ganSuKien();
    }

    /**
     * Cập nhật hiển thị: Ẩn các nút Thêm, Xóa, Xem dựa trên danh sách quyền của
     * user
     */
    public void suaLaiGiaoDienTheoQuyen() {
        var listQuyen = PhienDangNhap.getListQuyen();

        // 1. Quyền Thêm mới phiếu nhập (NK_TAO)
        if (!listQuyen.contains("NK_TAO")) {
            nhapHangBtn.setVisible(false);
        }

        // 2. Quyền Xóa phiếu nhập (NK_XOA)
        if (!listQuyen.contains("NK_XOA")) {
            xoaBtn.setVisible(false);
        }
    }

    private void ganSuKien() {
        nhapHangBtn.addActionListener(e -> {
            JDialog dialogNhapHang = new NhapKhoSanPhamDialog(this);
            dialogNhapHang.setVisible(true);
        });

        xemChiTietBtn.addActionListener(e -> {
            int dongChon = table.getSelectedRow();
            PhieuNhapSanPhamBUS phieuNhapSanPhamBUS = PhieuNhapSanPhamBUS.getPhieuNhapSanPhamBUS();
            if (dongChon >= 0) {
                PhieuNhapSanPham phieuNhapSanPham = phieuNhapSanPhamBUS
                        .timPhieuNhapSanPham(model.getValueAt(dongChon, 0).toString());
                ChiTietPhieuNhapSanPhamDialog chiTietPhieuNhapSanPhamDialog = new ChiTietPhieuNhapSanPhamDialog(null,
                        phieuNhapSanPham, this);
                chiTietPhieuNhapSanPhamDialog.setVisible(true);
            } else {
                TaoTinNhan.showAutoCloseMessage("Vui lòng chọn phiếu nhập để xem chi tiết", "Thông báo", dongChon);
            }
        });

        xoaBtn.addActionListener(e -> {
            int dongChon = table.getSelectedRow();
            PhieuNhapSanPhamBUS phieuNhapSanPhamBUS = PhieuNhapSanPhamBUS.getPhieuNhapSanPhamBUS();
            if (dongChon >= 0) {
                if (model.getValueAt(dongChon, 5).toString().equals("Đang xử lý")) {
                    if (phieuNhapSanPhamBUS.xoaPhieuNhapSanPham(
                            phieuNhapSanPhamBUS.timPhieuNhapSanPham(model.getValueAt(dongChon, 0).toString()))) {
                        TaoTinNhan.showAutoCloseMessage("Xóa phiếu nhập sản phẩm thành công", "Thông báo",
                                dongChon);
                        loadDuLieu();
                    }
                } else {
                    TaoTinNhan.showAutoCloseMessage("Phiếu nhập sản phẩm đã xác nhận, không thể xóa", "Thông báo",
                            dongChon);
                }
            } else {
                TaoTinNhan.showAutoCloseMessage("Vui lòng chọn phiếu nhập để xóa", "Thông báo", dongChon);
            }
        });

        locNgay_Item.setEvent(() -> {
            loadDuLieu();
        });
    }

    public void loadDuLieu() {
        model.setRowCount(0);
        PhieuNhapSanPhamBUS phieuNhapSanPhamBUS = PhieuNhapSanPhamBUS.getPhieuNhapSanPhamBUS();

        for (PhieuNhapSanPham phieuNhapSanPham : phieuNhapSanPhamBUS.layListPhieuNhapSanPham()) {
            if (locNgay_Item.ngayTrongKhoan(phieuNhapSanPham.getNgayNhap())) {
                NhaCungCap nhaCungCap = NhaCungCapBUS.getNhaCungCapBUS().timNhaCungCap(phieuNhapSanPham.getMaNCC());
                model.addRow(new Object[] { phieuNhapSanPham.getMaPN(), phieuNhapSanPham.getNgayNhap(),
                        phieuNhapSanPham.getMaNV(), phieuNhapSanPham.getGhiChu(),
                        nhaCungCap != null ? nhaCungCap.getTenNCC() : "",
                        phieuNhapSanPham.getTrangThaiXuLy() });
            }
        }
    }
}
