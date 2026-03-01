package ui.nguyenlieu;

import dto.NguyenLieu;
import java.awt.*;
import javax.swing.*;

public class FormNguyenLieu extends JDialog {
  private JTextField txtMa, txtTen, txtGia, txtDonVi, txtMucCanhBao;
  private JButton btnLuu, btnHuy;
  private NguyenLieu ketQua = null;
  private boolean isEdit = false;

  public FormNguyenLieu(Frame owner, NguyenLieu editNL) {
    super(owner, editNL == null ? "Thêm Nguyên Liệu" : "Sửa Nguyên Liệu", true);
    this.isEdit = (editNL != null);

    setSize(new Dimension(500, 300));
    initUI();

    if (isEdit) {
      duLieuCu(editNL);
    }

    btnLuu.addActionListener(
        e -> {
          xuLyLuu(editNL);
        });

    btnHuy.addActionListener(
        e -> {
          dispose();
        });

    setLocationRelativeTo(owner);
  }

  private void initUI() {
    setLayout(new BorderLayout());
    JPanel pnlMain = new JPanel();
    pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
    pnlMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    txtMa = new JTextField("Tự động");
    txtMa.setEditable(false);
    txtTen = new JTextField();
    txtGia = new JTextField();
    txtDonVi = new JTextField();
    txtMucCanhBao = new JTextField();

    String[] labels = { "Mã nguyên liệu:", "Tên nguyên liệu:", "Giá nhập:", "Đơn vị tính:", "Mức cảnh báo:" };
    JTextField[] fields = { txtMa, txtTen, txtGia, txtDonVi, txtMucCanhBao };

    for (int i = 0; i < labels.length; i++) {
      Box row = Box.createHorizontalBox();

      JLabel lbl = new JLabel(labels[i]);
      lbl.setPreferredSize(new Dimension(150, 50));
      lbl.setMaximumSize(new Dimension(150, 50));

      row.add(lbl);
      row.add(fields[i]);

      pnlMain.add(row);
      pnlMain.add(Box.createVerticalStrut(10));
    }

    JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
    btnLuu = new JButton("Lưu");
    btnHuy = new JButton("Hủy");
    pnlButtons.add(btnLuu);
    pnlButtons.add(btnHuy);

    add(pnlMain, BorderLayout.CENTER);
    add(pnlButtons, BorderLayout.SOUTH);
  }

  private void duLieuCu(NguyenLieu editNL) {
    txtMa.setText(editNL.getMaNL());
    txtTen.setText(editNL.getTenNL());
    txtGia.setText(String.valueOf(editNL.getGia()));
    txtDonVi.setText(editNL.getDonVi());
    txtMucCanhBao.setText(String.valueOf(editNL.getMucCanhBao()));
  }

  private void xuLyLuu(NguyenLieu editNL) {
    try {
      if (txtTen.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập tên!");
        return;
      }

      ketQua = (isEdit) ? editNL : new NguyenLieu();
      ketQua.setTenNL(txtTen.getText().trim());
      ketQua.setGia(Double.parseDouble(txtGia.getText().trim()));
      ketQua.setDonVi(txtDonVi.getText().trim());
      ketQua.setMucCanhBao(Integer.parseInt(txtMucCanhBao.getText().trim()));

      dispose();
    } catch (NumberFormatException ex) {
      JOptionPane.showMessageDialog(this, "Giá và Mức cảnh báo phải là số!");
    }
  }

  public NguyenLieu getKetQua() {
    return ketQua;
  }

  public static void main(String[] args) {
    FormNguyenLieu formNguyenLieu = new FormNguyenLieu(null, null);
    formNguyenLieu.setVisible(true);
  }
}
