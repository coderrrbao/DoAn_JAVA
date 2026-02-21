package ui.xuatkho.nguyenlieu;

import bus.PhieuHuyNguyenLieuBUS;
import dto.PhieuHuyNguyenLieu;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import ui.component.Search_Item;
import util.TaoUI;

public class XuatKhoNguyenLieuPanel extends JPanel {
  private JTable table;
  private DefaultTableModel model;

  public XuatKhoNguyenLieuPanel() {
    setLayout(new BorderLayout());
    JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 35);

    JButton btnXuat = new JButton("Xuất nguyên liệu");
    btnXuat.addActionListener(
        e -> {
          XuatKhoNguyenLieuDialog dialog = new XuatKhoNguyenLieuDialog(this);
          dialog.setVisible(true);
        });

    top.add(new Search_Item(300, 30));
    top.add(btnXuat);
    add(top, BorderLayout.NORTH);

    model =
        new DefaultTableModel(
            new String[] {"Mã phiếu", "Ngày xuất", "Nhân viên", "Lý do", "Tổng tiền"}, 0);
    JScrollPane scrollPane = TaoUI.taoTableScroll(model);
    table = (JTable) scrollPane.getViewport().getView();

    add(scrollPane, BorderLayout.CENTER);
    loadDuLieu();
  }

  public void loadDuLieu() {
    model.setRowCount(0);
    ArrayList<PhieuHuyNguyenLieu> list =
        PhieuHuyNguyenLieuBUS.getPhieuHuyNguyenLieuBUS().layListPhieuHuy();
    for (PhieuHuyNguyenLieu ph : list) {
      model.addRow(
          new Object[] {
            ph.getMaPH(),
            ph.getNgayHuy(),
            ph.getMaNV(),
            ph.getLyDo(),
            String.format("%,.0f VNĐ", ph.getTongTien())
          });
    }
  }
}
