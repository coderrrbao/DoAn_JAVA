package ui.nhacungcap;

import javax.swing.*;
import java.awt.*;
import dto.ChiTietNhaCungCap;
import util.TaoUI;

public class SuaChiTietNhaCungCapDialog extends JDialog {
    private JTextField txtMa, txtTen, txtGiaNhap;
    private JButton btnLuu, btnHuy;
    private boolean isUpdated = false;
    private ChiTietNhaCungCap chiTiet;
    private ChiTietNhaCungCapDialog chiTietNhaCungCapDialog;
    private int dong;

    public SuaChiTietNhaCungCapDialog(Dialog parent, ChiTietNhaCungCap ct, String tenDoiTuong, int dong) {
        super(parent, "Sửa giá nhập hàng hóa", true);
        this.dong = dong;
        this.chiTiet = ct;
        chiTietNhaCungCapDialog = (ChiTietNhaCungCapDialog) parent;
        setSize(350, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        // ==================== PHẦN FORM ====================
        JPanel pnForm = new JPanel();
        pnForm.setLayout(new BoxLayout(pnForm, BoxLayout.Y_AXIS));
        pnForm.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        txtMa = new JTextField(ct.getMaDoiTuong());
        txtTen = new JTextField(tenDoiTuong);
        txtGiaNhap = new JTextField(String.valueOf(ct.getGiaNhap()));

        TaoUI.setFixSize(txtMa, 300, 35);
        TaoUI.setFixSize(txtTen, 300, 35);
        TaoUI.setFixSize(txtGiaNhap, 300, 35);

        txtMa.setEnabled(false);
        txtTen.setEnabled(false);

        pnForm.add(taoDong("Mã hàng hóa:", txtMa));
        pnForm.add(taoDong("Tên hàng hóa:", txtTen));
        pnForm.add(taoDong("Giá nhập mới:", txtGiaNhap));

        add(pnForm, BorderLayout.CENTER);

        // ==================== PHẦN NÚT BẤM ====================
        JPanel pnBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Hủy");

        pnBottom.add(btnLuu);
        pnBottom.add(btnHuy);
        add(pnBottom, BorderLayout.SOUTH);

        ganSuKien();
    }

    private JPanel taoDong(String labelText, JTextField tf) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        JLabel lbl = new JLabel(labelText);
        lbl.setPreferredSize(new Dimension(100, 25));

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(tf, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        return panel;
    }

    private void ganSuKien() {
        btnHuy.addActionListener(e -> dispose());

        btnLuu.addActionListener(e -> {
            try {
                double giaMoi = Double.parseDouble(txtGiaNhap.getText().trim());
                if (giaMoi < 0) {
                    JOptionPane.showMessageDialog(this, "Giá nhập không được âm!");
                    return;
                }
                chiTiet.setGiaNhap(giaMoi);

                chiTietNhaCungCapDialog.suaChiTietNhaCungCap(chiTiet, dong);

                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập giá đúng định dạng số!");
            }
        });
    }

}