package ui.nhapkho.sanpham;

import java.awt.BorderLayout;
import java.awt.Frame;

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
import ui.component.Search_Item;
import util.TaoUI;

public class NhapKhoSanPhamPanel extends JPanel {
    private JButton nhapHangBtn, xemChiTietBtn;
    private Search_Item search_Item;
    private JTable table;
    private DefaultTableModel model;

    public NhapKhoSanPhamPanel() {
        setLayout(new BorderLayout());
        JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 30);
        nhapHangBtn = new JButton("Thêm");
        xemChiTietBtn = new JButton("Xem Chi tiết");

        TaoUI.setFixSize(nhapHangBtn, 100, 30);
        TaoUI.setFixSize(xemChiTietBtn, 150, 30);

        search_Item = new Search_Item(300, 30);
        top.add(search_Item);
        top.add(nhapHangBtn);
        top.add(xemChiTietBtn);

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
            JDialog dialogNhapHang = new NhapKhoSanPhamDialog(this);
            dialogNhapHang.setVisible(true);
        });
    }

    public void loadDuLieu() {
        model.setRowCount(0);
        PhieuNhapSanPhamBUS phieuNhapSanPhamBUS = PhieuNhapSanPhamBUS.getPhieuNhapSanPhamBUS();

        for (PhieuNhapSanPham phieuNhapSanPham : phieuNhapSanPhamBUS.layListPhieuNhapSanPham()) {
            NhaCungCap nhaCungCap = NhaCungCapBUS.getNhaCungCapBUS().timNhaCungCap(phieuNhapSanPham.getMaNCC());
            model.addRow(new Object[] { phieuNhapSanPham.getMaPN(), phieuNhapSanPham.getNgayNhap(),
                    phieuNhapSanPham.getMaNV(), phieuNhapSanPham.getGhiChu(),
                    nhaCungCap != null ? nhaCungCap.getTenNCC() : "",
                    phieuNhapSanPham.getTrangThaiXuLy() });
        }
    }
}
