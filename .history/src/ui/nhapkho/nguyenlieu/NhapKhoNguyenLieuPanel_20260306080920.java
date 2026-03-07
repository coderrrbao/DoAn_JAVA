package ui.nhapkho.nguyenlieu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.HashSet;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import bus.NhaCungCapBUS;
import bus.PhieuNhapNguyenLieuBUS;
import dto.NhaCungCap;
import dto.PhieuNhapNguyenLieu;
import ui.component.LocNgay_Item;
import ui.login.PhienDangNhap;
import util.TaoTinNhan;
import util.TaoUI;

public class NhapKhoNguyenLieuPanel extends JPanel {
    private JButton nhapHangBtn, xemChiTietBtn, xoaBtn, NhapExcelBtn, XuatExcelBtn;
    private LocNgay_Item locNgay_Item;
    private JTable table;
    private DefaultTableModel model;

    public NhapKhoNguyenLieuPanel() {
        setLayout(new BorderLayout());
        JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 45);
        top.setBackground(Color.WHITE);

        nhapHangBtn = new JButton("Thêm");
        xemChiTietBtn = new JButton("Xem Chi tiết");
        xoaBtn = new JButton("Xóa");
        NhapExcelBtn = new JButton("Nhập Excel");
        XuatExcelBtn = new JButton("Xuất Excel");

        TaoUI.setFixSize(nhapHangBtn, 100, 32);
        TaoUI.setFixSize(xemChiTietBtn, 150, 32);
        TaoUI.setFixSize(xoaBtn, 100, 32);
        TaoUI.setFixSize(NhapExcelBtn, 150, 32);
        TaoUI.setFixSize(XuatExcelBtn, 150, 32);

        locNgay_Item = new LocNgay_Item(400, 32);
        top.add(locNgay_Item);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(nhapHangBtn);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(xemChiTietBtn);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(xoaBtn);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(NhapExcelBtn);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(XuatExcelBtn);
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

    public void suaLaiGiaoDienTheoQuyen() {

        HashSet<String> listQuyen = PhienDangNhap.getListQuyen();
        if (!listQuyen.contains("NK_TAO")) {
            nhapHangBtn.setVisible(false);
        }

        if (!listQuyen.contains("NK_XOA")) {
            xoaBtn.setVisible(false);
        }
    }

    private void ganSuKien() {
        nhapHangBtn.addActionListener(e -> {
            JDialog dialogNhapHang = new NhapKhoNguyenLieuDialog(this);
            dialogNhapHang.setVisible(true);
        });

        xemChiTietBtn.addActionListener(e -> {
            int dongChon = table.getSelectedRow();
            PhieuNhapNguyenLieuBUS phieuNhapNguyenLieuBUS = PhieuNhapNguyenLieuBUS.getPhieuNhapNguyenLieuBUS();
            if (dongChon >= 0) {
                PhieuNhapNguyenLieu phieuNhapNguyenLieu = phieuNhapNguyenLieuBUS
                        .timPhieuNhapNguyenLieu(model.getValueAt(dongChon, 0).toString());

                ChiTietPhieuNhapNguyenLieuDialog chiTietDialog = new ChiTietPhieuNhapNguyenLieuDialog(null,
                        phieuNhapNguyenLieu, this);
                chiTietDialog.setVisible(true);
            } else {
                TaoTinNhan.showAutoCloseMessage("Vui lòng chọn phiếu nhập để xem chi tiết", "Thông báo", 1);
            }
        });

        xoaBtn.addActionListener(e -> {
            int dongChon = table.getSelectedRow();
            PhieuNhapNguyenLieuBUS phieuNhapNguyenLieuBUS = PhieuNhapNguyenLieuBUS.getPhieuNhapNguyenLieuBUS();
            if (dongChon >= 0) {
                if (model.getValueAt(dongChon, 5).toString().equals("Đang xử lý")) {
                    if (phieuNhapNguyenLieuBUS.xoaPhieuNhapNguyenLieu(
                            phieuNhapNguyenLieuBUS.timPhieuNhapNguyenLieu(model.getValueAt(dongChon, 0).toString()))) {
                        TaoTinNhan.showAutoCloseMessage("Xóa phiếu nhập nguyên liệu thành công", "Thông báo", 1);
                        loadDuLieu();
                    }
                } else {
                    TaoTinNhan.showAutoCloseMessage("Phiếu nhập nguyên liệu đã xác nhận, không thể xóa", "Thông báo",
                            1);
                }
            } else {
                TaoTinNhan.showAutoCloseMessage("Vui lòng chọn phiếu nhập để xóa", "Thông báo", 1);
            }
        });

        
        locNgay_Item.setEvent(() -> {
            loadDuLieu();
        });
    }

    public void loadDuLieu() {
        model.setRowCount(0);
        PhieuNhapNguyenLieuBUS phieuNhapNguyenLieuBUS = PhieuNhapNguyenLieuBUS.getPhieuNhapNguyenLieuBUS();

        for (PhieuNhapNguyenLieu phieuNhapNguyenLieu : phieuNhapNguyenLieuBUS.layListPhieuNhapNguyenLieu()) {
            if (locNgay_Item.ngayTrongKhoan(phieuNhapNguyenLieu.getNgayNhap())) {
                NhaCungCap nhaCungCap = NhaCungCapBUS.getNhaCungCapBUS().timNhaCungCap(phieuNhapNguyenLieu.getMaNCC());
                model.addRow(new Object[] {
                        phieuNhapNguyenLieu.getMaPN(),
                        phieuNhapNguyenLieu.getNgayNhap(),
                        phieuNhapNguyenLieu.getMaNV(),
                        phieuNhapNguyenLieu.getGhiChu(),
                        nhaCungCap != null ? nhaCungCap.getTenNCC() : "",
                        phieuNhapNguyenLieu.getTrangThaiXuLy()
                });
            }
        }
    }
}