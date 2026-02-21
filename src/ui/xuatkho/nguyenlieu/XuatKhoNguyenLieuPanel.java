package ui.xuatkho.nguyenlieu;

import java.awt.BorderLayout;
import java.awt.Frame;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import ui.component.Search_Item;
import util.TaoUI;

public class XuatKhoNguyenLieuPanel extends JPanel {
  private JButton btnXuat;

  public XuatKhoNguyenLieuPanel(Frame owner) {
    setLayout(new BorderLayout());
    JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 35);

    btnXuat = new JButton("Xuất nguyên liệu");
    btnXuat.addActionListener(
        e -> {
          XuatKhoNguyenLieuDialog dialog = new XuatKhoNguyenLieuDialog(owner);
          dialog.setVisible(true);
        });

    top.add(new Search_Item(300, 30));
    top.add(btnXuat);
    add(top, BorderLayout.NORTH);

    String[] cols = {"Mã phiếu xuất", "Ngày xuất", "Nhân viên", "Ghi chú"};
    DefaultTableModel model = new DefaultTableModel(cols, 0);
    add(TaoUI.taoTableScroll(model), BorderLayout.CENTER);
  }
}
