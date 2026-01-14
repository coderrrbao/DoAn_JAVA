package ui.tonkho;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TonKhoUI extends JPanel {
    private TonKhoNguyenLieuPanel tonKhoNguyenLieu;
    private TonKhoSanPhamPanel tonKhoSanPham;
    public TonKhoUI(){
        setLayout(new BorderLayout());
        tonKhoNguyenLieu = new TonKhoNguyenLieuPanel();
        tonKhoSanPham = new TonKhoSanPhamPanel();
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Sản phẩm", tonKhoSanPham);
        tabbedPane.addTab("Nguyên liệu", tonKhoNguyenLieu);
        add(tabbedPane,BorderLayout.CENTER);
    }
}
