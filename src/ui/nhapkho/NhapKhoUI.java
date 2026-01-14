package ui.nhapkho;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import ui.nhapkho.nguyenlieu.NhapKhoNguyenLieuPanel;
import ui.nhapkho.sanpham.NhapKhoSanPhamPanel;

public class NhapKhoUI extends JPanel {
    private NhapKhoSanPhamPanel nhapKhoSanPham;
    private NhapKhoNguyenLieuPanel nhapKhoNguyenLieu;
    public NhapKhoUI(Frame ouner) {
        setLayout(new BorderLayout());
        
        nhapKhoNguyenLieu = new NhapKhoNguyenLieuPanel(ouner);
        nhapKhoSanPham = new NhapKhoSanPhamPanel(ouner);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Sản phẩm", nhapKhoSanPham);
        tabbedPane.addTab("Nguyên liệu", nhapKhoNguyenLieu);

        add(tabbedPane,BorderLayout.CENTER);
    }
}
