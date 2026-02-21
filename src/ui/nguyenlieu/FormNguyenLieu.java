package ui.nguyenlieu;

import bus.NhaCungCapBUS;
import dto.NguyenLieu;
import dto.NhaCungCap;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class FormNguyenLieu extends JDialog {
  private JTextField txtMa, txtTen, txtGia, txtDonVi, txtMucCanhBao;
  private JComboBox<String> cbNhaCungCap;
  private JButton btnLuu, btnHuy;
  private NguyenLieu ketQua = null;
  private boolean isEdit = false;

  public FormNguyenLieu(Frame owner, NguyenLieu editNL) {
    super(owner, editNL == null ? "Thêm Nguyên Liệu" : "Sửa Nguyên Liệu", true);
    this.isEdit = (editNL != null);

    initUI();
    loadNhaCungCap();

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
    JPanel pnlInput = new JPanel(new GridLayout(6, 2, 10, 10));
    pnlInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    txtMa = new JTextField("Tự động");
    txtMa.setEditable(false);
    txtTen = new JTextField();
    cbNhaCungCap = new JComboBox<>();
    txtGia = new JTextField();
    txtDonVi = new JTextField();
    txtMucCanhBao = new JTextField();

    pnlInput.add(new JLabel("Mã nguyên liệu:"));
    pnlInput.add(txtMa);
    pnlInput.add(new JLabel("Tên nguyên liệu:"));
    pnlInput.add(txtTen);
    pnlInput.add(new JLabel("Nhà cung cấp:"));
    pnlInput.add(cbNhaCungCap);
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

  private void loadNhaCungCap() {
    cbNhaCungCap.removeAllItems();
    ArrayList<String> dsTenNCC = NhaCungCapBUS.getNhaCungCapBUS().layLuaChonNCCNguyenLieu();

    if (dsTenNCC != null && !dsTenNCC.isEmpty()) {
      for (String ten : dsTenNCC) {
        cbNhaCungCap.addItem(ten);
      }
    } else {
      ArrayList<NhaCungCap> all = NhaCungCapBUS.getNhaCungCapBUS().laylistNhaCungCap();
      if (all != null) {
        for (NhaCungCap ncc : all) {
          cbNhaCungCap.addItem(ncc.getTenNCC());
        }
      }
    }
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

      NhaCungCapBUS.getNhaCungCapBUS().danhDauCanCapNhat();

      dispose();
    } catch (NumberFormatException ex) {
      JOptionPane.showMessageDialog(this, "Giá và Mức cảnh báo phải là số!");
    }
  }

  public NguyenLieu getKetQua() {
    return ketQua;
  }
}
