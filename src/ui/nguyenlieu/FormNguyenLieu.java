package ui.nguyenlieu;

import dto.NguyenLieu;
import java.awt.*;
import java.util.HashSet;
import javax.swing.*;
import ui.login.PhienDangNhap;

public class FormNguyenLieu extends JDialog {
  private JTextField txtMa, txtTen, txtGia, txtDonVi, txtMucCanhBao;
  private JButton btnThem, btnSua, btnLuu, btnHuy;

  private NguyenLieu ketQua = null;
  private boolean isEdit = false;

  public FormNguyenLieu(Frame owner, NguyenLieu editNL) {
    super(owner, editNL == null ? "Thêm Nguyên Liệu" : "Chi tiết Nguyên Liệu", true);
    this.isEdit = (editNL != null);

    setSize(new Dimension(500, 350));
    initUI();

    if (isEdit) {
      duLieuCu(editNL);
    }

    initLoaiDialog();
    ganSuKien(editNL);
    suaLaiGiaoDienTheoQuyen();

    setLocationRelativeTo(owner);
  }

  private void initUI() {
    setLayout(new BorderLayout());
    JPanel pnlMain = new JPanel();
    pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
    pnlMain.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    txtMa = new JTextField("Tự động");
    txtMa.setEditable(false);
    txtTen = new JTextField();
    txtGia = new JTextField();
    txtDonVi = new JTextField();
    txtMucCanhBao = new JTextField();

    String[] labels = {
        "Mã nguyên liệu:", "Tên nguyên liệu:", "Giá nhập:", "Đơn vị tính:", "Mức cảnh báo:"
    };
    JTextField[] fields = { txtMa, txtTen, txtGia, txtDonVi, txtMucCanhBao };

    for (int i = 0; i < labels.length; i++) {
      Box row = Box.createHorizontalBox();

      JLabel lbl = new JLabel(labels[i]);
      lbl.setPreferredSize(new Dimension(120, 30));
      lbl.setMaximumSize(new Dimension(120, 30));

      row.add(lbl);
      row.add(Box.createHorizontalStrut(10));
      row.add(fields[i]);

      pnlMain.add(row);
      pnlMain.add(Box.createVerticalStrut(15));
    }

    JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

    // Khởi tạo các nút
    btnThem = new JButton("Thêm");
    btnSua = new JButton("Sửa");
    btnLuu = new JButton("Lưu");
    btnHuy = new JButton("Đóng");

    pnlButtons.add(btnThem);
    pnlButtons.add(btnSua);
    pnlButtons.add(btnLuu);
    pnlButtons.add(btnHuy);

    add(pnlMain, BorderLayout.CENTER);
    add(pnlButtons, BorderLayout.SOUTH);
  }

  private void initLoaiDialog() {
    if (isEdit) {
      btnHuy.setVisible(false);
      btnThem.setVisible(false);
      anThaoTacSua();
    } else {
      // Chế độ Thêm: Ẩn Sửa/Lưu, hiện Thêm
      btnSua.setVisible(false);
      btnLuu.setVisible(false);
      btnThem.setVisible(true);
      setEditableForm(true);
    }
  }

  public void suaLaiGiaoDienTheoQuyen() {
    HashSet<String> listQuyen = PhienDangNhap.getListQuyen();

    // 1. Kiểm tra quyền THÊM
    if (!listQuyen.contains("NL_TAO")) {
      btnThem.setVisible(false);
    }

    // 2. Kiểm tra quyền SỬA
    if (!listQuyen.contains("NL_SUA")) {
      btnSua.setVisible(false);
      btnLuu.setVisible(false);
    }

    if (!listQuyen.contains("NL_TAO") && !listQuyen.contains("NL_SUA")) {
      this.setTitle("Chi tiết Nguyên Liệu (Chế độ xem)");
      btnHuy.setText("Thoát");
      btnHuy.setVisible(true);
    }
  }

  private void setEditableForm(boolean status) {
    txtTen.setEditable(status);
    txtGia.setEditable(status);
    txtDonVi.setEditable(status);
    txtMucCanhBao.setEditable(status);
  }

  private void anThaoTacSua() {
    btnSua.setEnabled(true);
    btnLuu.setEnabled(false);
    setEditableForm(false);
  }

  private void batThaoTacSua() {
    btnSua.setEnabled(false);
    btnLuu.setEnabled(true);
    setEditableForm(true);
  }

  private void ganSuKien(NguyenLieu editNL) {
    btnHuy.addActionListener(e -> dispose());

    btnSua.addActionListener(e -> batThaoTacSua());

    // Sự kiện nút Thêm
    btnThem.addActionListener(
        e -> {
          if (kiemTraDuLieu()) {
            ketQua = new NguyenLieu();
            ganDuLieu(ketQua);
            dispose();
          }
        });

    // Sự kiện nút Lưu (khi Sửa)
    btnLuu.addActionListener(
        e -> {
          if (kiemTraDuLieu()) {
            ketQua = editNL; // Cập nhật trên đối tượng cũ
            ganDuLieu(ketQua);
            dispose();
          }
        });
  }

  private void duLieuCu(NguyenLieu editNL) {
    txtMa.setText(editNL.getMaNL());
    txtTen.setText(editNL.getTenNL());
    txtGia.setText(String.valueOf(editNL.getGia()));
    txtDonVi.setText(editNL.getDonVi());
    txtMucCanhBao.setText(String.valueOf(editNL.getMucCanhBao()));
  }

  private boolean kiemTraDuLieu() {
    if (txtTen.getText().trim().isEmpty()) {
      JOptionPane.showMessageDialog(this, "Vui lòng nhập tên nguyên liệu!");
      return false;
    }

    if (txtGia.getText().trim().isEmpty()) {
      JOptionPane.showMessageDialog(this, "Vui lòng nhập giá!");
      return false;
    }

    if (txtDonVi.getText().trim().isEmpty()) {
      JOptionPane.showMessageDialog(this, "Vui lòng nhập đơn vị tính!");
      return false;
    }

    if (txtMucCanhBao.getText().trim().isEmpty()) {
      JOptionPane.showMessageDialog(this, "Vui lòng nhập mức cảnh báo!");
      return false;
    }

    try {
      double gia = Double.parseDouble(txtGia.getText().trim());
      if (gia <= 0) {
        JOptionPane.showMessageDialog(this, "Giá phải lớn hơn 0!");
        return false;
      }

      int mucCanhBao = Integer.parseInt(txtMucCanhBao.getText().trim());
      if (mucCanhBao < 0) {
        JOptionPane.showMessageDialog(this, "Mức cảnh báo phải >= 0!");
        return false;
      }

      return true;
    } catch (NumberFormatException ex) {
      JOptionPane.showMessageDialog(this, "Giá và Mức cảnh báo phải là số hợp lệ!");
      return false;
    }
  }

  private void ganDuLieu(NguyenLieu nl) {
    nl.setTenNL(txtTen.getText().trim());
    nl.setGia(Double.parseDouble(txtGia.getText().trim()));
    nl.setDonVi(txtDonVi.getText().trim());
    nl.setMucCanhBao(Integer.parseInt(txtMucCanhBao.getText().trim()));
  }

  public NguyenLieu getKetQua() {
    return ketQua;
  }

  public static void main(String[] args) {
    FormNguyenLieu formNguyenLieu = new FormNguyenLieu(null, null);
    formNguyenLieu.setVisible(true);
  }
}
