package ui.xuatkho.nguyenlieu;

import bus.LoNguyenLieuBUS;
import bus.NguyenLieuBUS;
import bus.PhieuHuyNguyenLieuBUS;
import dto.LoNguyenLieu;
import dto.NguyenLieu;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import ui.login.PhienDangNhap;
import util.TaoUI;

public class XuatKhoNguyenLieuDialog extends JDialog {
  private JTable tblKho, tblChoXuat;
  private DefaultTableModel modelKho, modelChoXuat;
  private JTextField txtMaNL, txtTenNL, txtSoLuong, txtMaLo, txtMaNV;
  private JButton btnThem, btnXacNhan;
  private XuatKhoNguyenLieuPanel parentPanel;

  public XuatKhoNguyenLieuDialog(XuatKhoNguyenLieuPanel parent) {
    super((Frame) null, "Hủy Kho Nguyên Liệu Theo Lô", true);
    this.parentPanel = parent;
    setSize(1000, 650);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());

    JPanel main = new JPanel(new GridLayout(1, 2, 10, 0));
    JPanel left = TaoUI.taoPanelBorderLayout(450, 600);

    // Thêm cột Giá Nhập vào bảng tồn kho
    modelKho =
        new DefaultTableModel(new String[] {"Mã NL", "Mã Lô", "Hạn SD", "Tồn", "Giá Nhập"}, 0);
    tblKho = new JTable(modelKho);
    left.add(new JScrollPane(tblKho), BorderLayout.CENTER);

    JPanel right = new JPanel(new BorderLayout());
    JPanel form = TaoUI.taoPanelBoxLayoutDoc(400, 250);

    String maNVHienTai = (PhienDangNhap.getUser() != null) ? PhienDangNhap.getUser().getMaNV() : "";
    txtMaNV = new JTextField(maNVHienTai);
    txtMaNV.setEditable(false);
    txtMaNL = new JTextField();
    TaoUI.setDisabled(txtMaNL);
    txtTenNL = new JTextField();
    TaoUI.setDisabled(txtTenNL);
    txtMaLo = new JTextField();
    TaoUI.setDisabled(txtMaLo);
    txtSoLuong = new JTextField();

    form.add(new JLabel("Mã Nhân Viên:"));
    form.add(txtMaNV);
    form.add(new JLabel("Mã Nguyên liệu:"));
    form.add(txtMaNL);
    form.add(new JLabel("Mã Lô:"));
    form.add(txtMaLo);
    form.add(new JLabel("Tên Nguyên liệu:"));
    form.add(txtTenNL);
    form.add(new JLabel("Số lượng hủy:"));
    form.add(txtSoLuong);
    btnThem = new JButton("Thêm vào danh sách hủy");
    form.add(btnThem);

    // Bảng chờ hủy thêm cột Giá Nhập
    modelChoXuat =
        new DefaultTableModel(new String[] {"Mã NL", "Tên NL", "SL Hủy", "Mã Lô", "Giá Nhập"}, 0);
    tblChoXuat = new JTable(modelChoXuat);

    btnXacNhan = new JButton("XÁC NHẬN HỦY & TRỪ KHO");
    btnXacNhan.setBackground(new Color(0, 153, 76));
    btnXacNhan.setForeground(Color.WHITE);

    right.add(form, BorderLayout.NORTH);
    right.add(new JScrollPane(tblChoXuat), BorderLayout.CENTER);
    right.add(btnXacNhan, BorderLayout.SOUTH);

    main.add(left);
    main.add(right);
    add(main, BorderLayout.CENTER);

    ganSuKien();
    loadDataKhoNL();
  }

  private void loadDataKhoNL() {
    modelKho.setRowCount(0);
    ArrayList<LoNguyenLieu> listLo = LoNguyenLieuBUS.getLoNguyenLieuBUS().layListLoNguyenLieu();
    for (LoNguyenLieu lo : listLo) {
      if (lo.getSoLuong() > 0)
        modelKho.addRow(
            new Object[] {
              lo.getMaNL(), lo.getMaLoNL(), lo.getHanSuDung(), lo.getSoLuong(), lo.getGiaNhap()
            });
    }
  }

  private void ganSuKien() {
    tblKho
        .getSelectionModel()
        .addListSelectionListener(
            e -> {
              int row = tblKho.getSelectedRow();
              if (row != -1) {
                txtMaNL.setText(modelKho.getValueAt(row, 0).toString());
                txtMaLo.setText(modelKho.getValueAt(row, 1).toString());
                NguyenLieu nl = NguyenLieuBUS.getNguyenLieuBUS().timNguyenLieu(txtMaNL.getText());
                txtTenNL.setText(nl != null ? nl.getTenNL() : "N/A");
              }
            });

    btnThem.addActionListener(
        e -> {
          try {
            int r = tblKho.getSelectedRow();
            double slHuy = Double.parseDouble(txtSoLuong.getText());
            double ton = Double.parseDouble(modelKho.getValueAt(r, 3).toString());
            double gia = Double.parseDouble(modelKho.getValueAt(r, 4).toString());
            if (slHuy <= 0 || slHuy > ton) {
              JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ!");
              return;
            }
            modelChoXuat.addRow(
                new Object[] {
                  txtMaNL.getText(), txtTenNL.getText(), slHuy, txtMaLo.getText(), gia
                });
            txtSoLuong.setText("");
          } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Nhập số lượng hợp lệ!");
          }
        });

    btnXacNhan.addActionListener(
        e -> {
          int rowCount = modelChoXuat.getRowCount();
          if (rowCount == 0) return;
          Object[][] data = new Object[rowCount][5];
          for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < 5; j++) data[i][j] = modelChoXuat.getValueAt(i, j);
          }
          if (PhieuHuyNguyenLieuBUS.getPhieuHuyNguyenLieuBUS()
              .thucHienHuy(txtMaNV.getText(), "Hủy hỏng", data)) {
            JOptionPane.showMessageDialog(this, "Thành công!");
            parentPanel.loadDuLieu(); //
            dispose();
          } else {
            JOptionPane.showMessageDialog(
                this, "Thất bại! Kiểm tra lại mã cột MaLoNL trong Database.");
          }
        });
  }
}
