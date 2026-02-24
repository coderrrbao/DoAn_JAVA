package ui.nguyenlieu;

import dto.NguyenLieu;
import java.awt.*;
import javax.swing.*;

public class FormNguyenLieu extends JDialog {
  private JTextField txtMa, txtTen, txtGia, txtDonVi, txtMucCanhBao;
  // Đã xóa JComboBox cbNhaCungCap
  private JButton btnLuu, btnHuy;
  private NguyenLieu ketQua = null;
  private boolean isEdit = false;

  public FormNguyenLieu(Frame owner, NguyenLieu editNL) {
    super(owner, editNL == null ? "Thêm Nguyên Liệu" : "Sửa Nguyên Liệu", true);
    this.isEdit = (editNL != null);

    initUI();
    // Đã xóa loadNhaCungCap()

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

    pack();
    setLocationRelativeTo(owner);
  }

  private void initUI() {
    setLayout(new BorderLayout());
    // Giảm số hàng của GridLayout từ 6 xuống 5 vì đã bỏ NCC
    JPanel pnlInput = new JPanel(new GridLayout(5, 2, 10, 10));
    pnlInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    txtMa = new JTextField("Tự động");
    txtMa.setEditable(false);
    txtTen = new JTextField();
    txtGia = new JTextField();
    txtDonVi = new JTextField();
    txtMucCanhBao = new JTextField();

    pnlInput.add(new JLabel("Mã nguyên liệu:"));
    pnlInput.add(txtMa);
    pnlInput.add(new JLabel("Tên nguyên liệu:"));
    pnlInput.add(txtTen);
    // Đã xóa Label và ComboBox Nhà cung cấp ở đây
    pnlInput.add(new JLabel("Giá nhập:"));
    pnlInput.add(txtGia);
    pnlInput.add(new JLabel("Đơn vị tính:"));
    pnlInput.add(txtDonVi);
    pnlInput.add(new JLabel("Mức cảnh báo:"));
    pnlInput.add(txtMucCanhBao);

    JPanel pnlButtons = new JPanel();
    btnLuu = new JButton("Lưu");
    btnHuy = new JButton("Hủy");
    pnlButtons.add(btnLuu);
    pnlButtons.add(btnHuy);

    add(pnlInput, BorderLayout.CENTER);
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

      // Đã xóa dòng NhaCungCapBUS.getNhaCungCapBUS().danhDauCanCapNhat();

      dispose();
    } catch (NumberFormatException ex) {
      JOptionPane.showMessageDialog(this, "Giá và Mức cảnh báo phải là số!");
    }
  }

  public NguyenLieu getKetQua() {
    return ketQua;
  }
}
