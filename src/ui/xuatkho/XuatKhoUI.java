package ui.xuatkho;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class XuatKhoUI extends JPanel {
    private XuatKhoSanPhamPanel xuatKhoSanPham;
    private XuatKhoNguyenLieuPanel xuatKhoNguyenLieu;

    public XuatKhoUI() {
        setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        xuatKhoNguyenLieu = new XuatKhoNguyenLieuPanel();
        xuatKhoSanPham= new XuatKhoSanPhamPanel();
        tabbedPane.addTab("Sản phẩm", xuatKhoSanPham);
        tabbedPane.addTab("Nguyên liệu", xuatKhoNguyenLieu);
        add(tabbedPane, BorderLayout.CENTER);
    }
}