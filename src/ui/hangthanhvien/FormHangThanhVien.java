package ui.hangthanhvien;

import java.awt.*;
import javax.swing.*;

import dto.HangThanhVien;

public class FormHangThanhVien extends JDialog {
    private JTextField txtMa, txtTen, txtPhanTram, txtDieuKien;
    private JButton btnThem, btnSua, btnLuu, btnHuy;

    private HangThanhVien ketQua = null;
    private boolean isEdit = false;

    public FormHangThanhVien(Frame owner, HangThanhVien editHTV) {
        super(owner, editHTV == null ? "Thêm Hạng Thành Viên" : "Chi tiết Hạng Thành Viên", true);
        this.isEdit = (editHTV != null);

        setSize(new Dimension(500, 300));
        initUI();

        if (isEdit) {
            duLieuCu(editHTV);
        }

        initLoaiDialog();
        ganSuKien(editHTV);

        setLocationRelativeTo(owner);
        suaLaiGiaoDienTheoQuyen();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        JPanel pnlMain = new JPanel();
        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
        pnlMain.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        txtMa = new JTextField("Tự động");
        txtMa.setEditable(false);
        txtTen = new JTextField();
        txtPhanTram = new JTextField();
        txtDieuKien = new JTextField();

        String[] labels = { "Mã hạng:", "Tên hạng:", "Phần trăm giảm (%):", "Điều kiện chi tiêu:" };
        JTextField[] fields = { txtMa, txtTen, txtPhanTram, txtDieuKien };

        for (int i = 0; i < labels.length; i++) {
            Box row = Box.createHorizontalBox();

            JLabel lbl = new JLabel(labels[i]);
            lbl.setPreferredSize(new Dimension(130, 30));
            lbl.setMaximumSize(new Dimension(130, 30));

            row.add(lbl);
            row.add(Box.createHorizontalStrut(10));
            row.add(fields[i]);

            pnlMain.add(row);
            pnlMain.add(Box.createVerticalStrut(15));
        }

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

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

    public void suaLaiGiaoDienTheoQuyen() {
        var listQuyen = ui.login.PhienDangNhap.getListQuyen();

        if (!isEdit) {
            if (!listQuyen.contains("HTV_TAO")) {
                btnThem.setVisible(false);
                setEditableForm(false);
                this.setTitle("Thông tin hạng (Chỉ xem)");
            }
        }

        else {
            if (!listQuyen.contains("HTV_SUA")) {
                btnSua.setVisible(false);
                btnLuu.setVisible(false);
                this.setTitle("Chi tiết hạng (Chế độ chỉ đọc)");
            }
        }
    }

    private void initLoaiDialog() {
        if (isEdit) {
            btnHuy.setVisible(false);
            btnThem.setVisible(false);
            anThaoTacSua();
        } else {
            btnSua.setVisible(false);
            btnLuu.setVisible(false);
            btnThem.setVisible(true);
            setEditableForm(true);
        }
    }

    private void setEditableForm(boolean status) {
        txtTen.setEditable(status);
        txtPhanTram.setEditable(status);
        txtDieuKien.setEditable(status);
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

    private void ganSuKien(HangThanhVien editHTV) {
        btnHuy.addActionListener(e -> dispose());
        btnSua.addActionListener(e -> batThaoTacSua());

        btnThem.addActionListener(e -> {
            if (kiemTraHopLe()) {
                ketQua = new HangThanhVien();
                ganDuLieu(ketQua);
                dispose();
            }
        });

        btnLuu.addActionListener(e -> {
            if (kiemTraHopLe()) {
                ketQua = editHTV;
                ganDuLieu(ketQua);
                dispose();
            }
        });
    }

    private void duLieuCu(HangThanhVien editHTV) {
        txtMa.setText(editHTV.getMaHang());
        txtTen.setText(editHTV.getTenHang());
        txtPhanTram.setText(String.valueOf(editHTV.getPhanTramGiam()));

        txtDieuKien.setText(String.format("%.0f", editHTV.getDieuKien()));
    }

    private boolean kiemTraHopLe() {
        if (txtTen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên hạng thành viên!");
            return false;
        }
        try {
            int phanTram = Integer.parseInt(txtPhanTram.getText().trim());
            if (phanTram < 0 || phanTram > 100) {
                JOptionPane.showMessageDialog(this, "Phần trăm giảm phải từ 0 đến 100!");
                return false;
            }
            Double.parseDouble(txtDieuKien.getText().trim());
            return true;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Phần trăm giảm và Điều kiện phải là số hợp lệ!");
            return false;
        }
    }

    private void ganDuLieu(HangThanhVien htv) {
        htv.setTenHang(txtTen.getText().trim());
        htv.setPhanTramGiam(Integer.parseInt(txtPhanTram.getText().trim()));
        htv.setDieuKien(Double.parseDouble(txtDieuKien.getText().trim()));
    }

    public HangThanhVien getKetQua() {
        return ketQua;
    }
}