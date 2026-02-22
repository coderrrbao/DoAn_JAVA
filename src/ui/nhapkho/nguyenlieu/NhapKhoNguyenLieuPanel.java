package ui.nhapkho.nguyenlieu;

import java.awt.BorderLayout;
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
import util.TaoTinNhan;
import util.TaoUI;

public class NhapKhoNguyenLieuPanel extends JPanel {
    private JButton nhapHangBtn, xemChiTietBtn, xoaBtn;
    private LocNgay_Item locNgay_Item;
    private JTable table;
    private DefaultTableModel model;

    public NhapKhoNguyenLieuPanel() {
        setLayout(new BorderLayout());
        JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 30);

        nhapHangBtn = new JButton("Thêm");
        xemChiTietBtn = new JButton("Xem Chi tiết");
        xoaBtn = new JButton("Xóa");

        TaoUI.setFixSize(nhapHangBtn, 100, 30);
        TaoUI.setFixSize(xemChiTietBtn, 150, 30);
        TaoUI.setFixSize(xoaBtn, 100, 30);

        locNgay_Item = new LocNgay_Item(300, 30);
        top.add(locNgay_Item);
        top.add(nhapHangBtn);
        top.add(xemChiTietBtn);
        top.add(xoaBtn);

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