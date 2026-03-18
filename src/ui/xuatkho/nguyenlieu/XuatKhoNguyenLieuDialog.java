package ui.xuatkho.nguyenlieu;

import bus.LoNguyenLieuBUS;
import bus.NguyenLieuBUS;
import bus.PhieuHuyNguyenLieuBUS;
import dto.LoNguyenLieu;
import dto.NguyenLieu;
import dto.PhieuHuyNguyenLieu;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import ui.component.Search_Item;
import ui.login.PhienDangNhap;
import ui.xuatkho.ButtonColumn;
import util.TaoUI;

public class XuatKhoNguyenLieuDialog extends JDialog {
  private JTable tblTonKho, tblChoXuat;
  private DefaultTableModel modelTonKho, modelChoXuat;
  private JTextField txtMaNL, txtTenNL, txtSoLuongXuat, txtMaLo, txtLyDo;
  private JButton btnThem, btnXacNhan;
  private Search_Item search_Item;
  private XuatKhoNguyenLieuPanel parentPanel;

  public XuatKhoNguyenLieuDialog(XuatKhoNguyenLieuPanel parent) {
    super((Frame) null, "Tạo Phiếu Hủy Nguyên Liệu", true);
    this.parentPanel = parent;
    setSize(1000, 650);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());

    JPanel main = new JPanel(new GridLayout(1, 2, 10, 0));

    JPanel left = new JPanel(new BorderLayout(0, 10));
    search_Item = new Search_Item(250, 32);
    left.add(search_Item, BorderLayout.NORTH);

    modelTonKho = new DefaultTableModel(new String[] { "Mã NL", "Mã Lô", "Hạn SD", "Tồn", "Giá Nhập" }, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    JScrollPane scrollTonKho = TaoUI.taoTableScroll(modelTonKho);
    tblTonKho = (JTable) scrollTonKho.getViewport().getView();
    left.add(scrollTonKho, BorderLayout.CENTER);

    JPanel right = new JPanel(new BorderLayout(0, 10));
    JPanel form = new JPanel();
    form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
    form.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 10));

    txtMaNL = new JTextField();
    txtMaNL.setEditable(false);
    txtTenNL = new JTextField();
    txtTenNL.setEditable(false);
    txtMaLo = new JTextField();
    txtMaLo.setEditable(false);
    txtSoLuongXuat = new JTextField();
    txtLyDo = new JTextField();

    form.add(taoDong("Mã Nguyên Liệu:", txtMaNL));
    form.add(taoDong("Tên Nguyên Liệu:", txtTenNL));
    form.add(taoDong("Mã Lô:", txtMaLo));
    form.add(taoDong("Số lượng hủy:", txtSoLuongXuat));
    form.add(taoDong("Lý do hủy:", txtLyDo));

    JPanel btnthemJPanel = new JPanel();
    btnthemJPanel.add(btnThem);
    TaoUI.setFixSize(btnThem, 490, 25);
    form.add(btnthemJPanel);

    modelChoXuat = new DefaultTableModel(
        new String[] { "Mã NL", "Tên NL", "SL Hủy", "Mã Lô", "Giá Nhập", " " }, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return column == 5;
      }
    };

    JScrollPane scrollChoXuat = TaoUI.taoTableScroll(modelChoXuat);
    tblChoXuat = (JTable) scrollChoXuat.getViewport().getView();

    Action deleteAction = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(
            () -> {
              try {
                int row = Integer.parseInt(e.getActionCommand());
                if (row >= 0 && row < modelChoXuat.getRowCount()) {
                  modelChoXuat.removeRow(row);
                  tblChoXuat.revalidate();
                  tblChoXuat.repaint();
                }
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            });
      }
    };
    new ButtonColumn(tblChoXuat, deleteAction, 5);
    tblChoXuat.getColumnModel().getColumn(5).setMaxWidth(35);
    tblChoXuat.getColumnModel().getColumn(5).setMinWidth(35);
    tblChoXuat.getColumnModel().getColumn(5).setMaxWidth(40);

    btnXacNhan = new JButton("XÁC NHẬN");
    btnXacNhan.setBackground(new Color(220, 53, 69));
    btnXacNhan.setForeground(Color.WHITE);
    btnXacNhan.setFont(new Font("Arial", Font.BOLD, 14));
    btnXacNhan.setPreferredSize(new Dimension(0, 40));

    right.add(form, BorderLayout.NORTH);
    right.add(scrollChoXuat, BorderLayout.CENTER);
    right.add(btnXacNhan, BorderLayout.SOUTH);

    main.add(left);
    main.add(right);
    add(main, BorderLayout.CENTER);

    loadData();
    ganSuKien();
    suaLaiGiaoDienTheoQuyen();
  }

  private JPanel taoDong(String tenLabel, JComponent comp) {
    JPanel pn = new JPanel(new BorderLayout(0, 5));
    pn.add(new JLabel(tenLabel), BorderLayout.NORTH);
    pn.add(comp, BorderLayout.CENTER);
    pn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
    pn.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
    return pn;
  }

  private void loadData() {
    modelTonKho.setRowCount(0);
    String keyword = search_Item.getTextSearch().toLowerCase();
    ArrayList<LoNguyenLieu> list = LoNguyenLieuBUS.getLoNguyenLieuBUS().layListLoNguyenLieu();
    for (LoNguyenLieu lo : list) {
      if (lo.getSoLuong() > 0 && lo.getMaLoNL().toLowerCase().contains(keyword)) {
        modelTonKho.addRow(
            new Object[] {
                lo.getMaNL(), lo.getMaLoNL(), lo.getHanSuDung(), lo.getSoLuong(), lo.getGiaNhap()
            });
      }
    }
  }

  private void ganSuKien() {
    search_Item.setEvent(this::loadData);
    tblTonKho
        .getSelectionModel()
        .addListSelectionListener(
            e -> {
              int r = tblTonKho.getSelectedRow();
              if (r != -1) {
                txtMaNL.setText(modelTonKho.getValueAt(r, 0).toString());
                txtMaLo.setText(modelTonKho.getValueAt(r, 1).toString());
                NguyenLieu nl = NguyenLieuBUS.getNguyenLieuBUS().timNguyenLieu(txtMaNL.getText());
                txtTenNL.setText(nl != null ? nl.getTenNL() : "N/A");
              }
            });

    btnThem.addActionListener(
        e -> {
          try {
            int r = tblTonKho.getSelectedRow();
            if (r == -1)
              return;
            double sl = Double.parseDouble(txtSoLuongXuat.getText());
            double ton = Double.parseDouble(modelTonKho.getValueAt(r, 3).toString());
            if (sl <= 0 || sl > ton) {
              JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ!");
              return;
            }
            modelChoXuat.addRow(
                new Object[] {
                    txtMaNL.getText(),
                    txtTenNL.getText(),
                    sl,
                    txtMaLo.getText(),
                    modelTonKho.getValueAt(r, 4),
                    ""
                });
            txtSoLuongXuat.setText("");
          } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Nhập số lượng!");
          }
        });

    btnXacNhan.addActionListener(
        e -> {
          if (modelChoXuat.getRowCount() == 0)
            return;
          Object[][] data = new Object[modelChoXuat.getRowCount()][5];
          double tong = 0;
          for (int i = 0; i < modelChoXuat.getRowCount(); i++) {
            for (int j = 0; j < 5; j++)
              data[i][j] = modelChoXuat.getValueAt(i, j);
            tong += Double.parseDouble(data[i][2].toString())
                * Double.parseDouble(data[i][4].toString());
          }
          PhieuHuyNguyenLieu ph = new PhieuHuyNguyenLieu();
          ph.setMaNV(PhienDangNhap.getUser() != null ? PhienDangNhap.getUser().getMaNV() : "");
          ph.setLyDo(txtLyDo.getText());
          ph.setTongTien(tong);
          if (PhieuHuyNguyenLieuBUS.getPhieuHuyNguyenLieuBUS().thucHienHuy(ph, data)) {
            parentPanel.loadDuLieu();
            dispose();
          }
        });
  }

  public void suaLaiGiaoDienTheoQuyen() {
    if (!PhienDangNhap.getListQuyen().contains("XK_TAO")) {
      btnThem.setVisible(false);
      btnXacNhan.setVisible(false);
    }
  }

}
