package ui.xuatkho.sanpham;

import bus.PhieuHuySanPhamBUS;
import dto.PhieuHuySanPham;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import ui.component.Search_Item;
import util.TaoUI;

public class XuatKhoSanPhamPanel extends JPanel {
  private JTable table;
  private DefaultTableModel model;
  private JButton xuatHangBtn;

  public XuatKhoSanPhamPanel() { // Xóa Frame owner để giống NhapKhoSanPhamPanel
    setLayout(new BorderLayout());
    JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 35);

    xuatHangBtn = new JButton("Tạo Phiếu Hủy");
    xuatHangBtn.addActionListener(
        e -> {
          XuatKhoSanPhamDialog dialog = new XuatKhoSanPhamDialog(this);
          dialog.setVisible(true);
        });

    top.add(new Search_Item(300, 30));
    top.add(xuatHangBtn);
    add(top, BorderLayout.NORTH);

    model =
        new DefaultTableModel(
            new String[] {"Mã Phiếu", "Ngày Hủy", "Nhân Viên", "Lý Do", "Tổng Giá Trị"}, 0);
    JScrollPane scrollPane = TaoUI.taoTableScroll(model);
    table = (JTable) scrollPane.getViewport().getView();

    add(scrollPane, BorderLayout.CENTER);
    loadDuLieu();
  }

  public void loadDuLieu() {
    model.setRowCount(0);
    ArrayList<PhieuHuySanPham> list = PhieuHuySanPhamBUS.getPhieuHuySanPhamBUS().layListPhieuHuy();
    for (PhieuHuySanPham ph : list) {
      model.addRow(
          new Object[] {
            ph.getMaPH(),
            ph.getNgayHuy(),
            ph.getMaNV(),
            ph.getLyDo(),
            String.format("%,.0f VNĐ", ph.getTongGiaTri())
          });
    }
  }
}
