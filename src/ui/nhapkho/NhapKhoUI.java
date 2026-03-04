package ui.nhapkho;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import ui.nhapkho.nguyenlieu.NhapKhoNguyenLieuPanel;
import ui.nhapkho.sanpham.NhapKhoSanPhamPanel;

public class NhapKhoUI extends JPanel {
    private NhapKhoSanPhamPanel nhapKhoSanPham;
    private NhapKhoNguyenLieuPanel nhapKhoNguyenLieu;
    public NhapKhoUI() {
        setLayout(new BorderLayout());
        
        nhapKhoNguyenLieu = new NhapKhoNguyenLieuPanel();
        nhapKhoSanPham = new NhapKhoSanPhamPanel();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Sản phẩm", nhapKhoSanPham);
        tabbedPane.addTab("Nguyên liệu", nhapKhoNguyenLieu);

        add(tabbedPane,BorderLayout.CENTER);
    }
    public void suaLaiGiaoDienTheoQuyen(){
        nhapKhoNguyenLieu.suaLaiGiaoDienTheoQuyen();
        nhapKhoSanPham.suaLaiGiaoDienTheoQuyen();
    }
}
