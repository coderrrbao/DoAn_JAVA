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

    xuatKhoSanPham = new XuatKhoSanPhamPanel(owner);
    xuatKhoNguyenLieu = new XuatKhoNguyenLieuPanel(owner);

    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.addTab("Sản phẩm", xuatKhoSanPham);
    tabbedPane.addTab("Nguyên liệu", xuatKhoNguyenLieu); // Thêm tab mới

    add(tabbedPane, BorderLayout.CENTER);
  }
}
