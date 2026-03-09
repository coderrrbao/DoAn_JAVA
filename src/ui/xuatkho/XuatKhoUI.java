package ui.xuatkho;

import java.awt.BorderLayout;
import java.awt.Frame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import ui.xuatkho.nguyenlieu.XuatKhoNguyenLieuPanel;
import ui.xuatkho.sanpham.XuatKhoSanPhamPanel;

public class XuatKhoUI extends JPanel {
  private XuatKhoSanPhamPanel xuatKhoSanPham;
  private XuatKhoNguyenLieuPanel xuatKhoNguyenLieu;

  public XuatKhoUI(Frame owner) {
    setLayout(new BorderLayout());

    xuatKhoSanPham = new XuatKhoSanPhamPanel();
    xuatKhoNguyenLieu = new XuatKhoNguyenLieuPanel();

    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.addTab("Sản phẩm", xuatKhoSanPham);
    tabbedPane.addTab("Nguyên liệu", xuatKhoNguyenLieu);

    add(tabbedPane, BorderLayout.CENTER);
  }

  public void loadData() {
    xuatKhoNguyenLieu.loadDuLieu();
    xuatKhoSanPham.loadDuLieu();
  }

  public void suaLaiGiaoDienTheoQuyen() {
    xuatKhoNguyenLieu.suaLaiGiaoDienTheoQuyen();
    xuatKhoSanPham.suaLaiGiaoDienTheoQuyen();
    this.revalidate();
    this.repaint();
  }
}
