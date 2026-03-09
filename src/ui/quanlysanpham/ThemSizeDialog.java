package ui.quanlysanpham;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dto.Size;
import util.TaoUI;

public class ThemSizeDialog extends JDialog {
    private JTextField tfTenSize, tfNLThem, tfGiaThem;
    private JButton themBtn, lamMoiBtn;
    ChiTietSanPhamDialog chiTietSanPhamDialog;

    public ThemSizeDialog(JDialog ouner) {
        super(ouner, "Thêm size");
        chiTietSanPhamDialog = (ChiTietSanPhamDialog) ouner;
        setSize(400, 250);
        setLocationRelativeTo(ouner);
        setLayout(new BorderLayout());
        JPanel center = TaoUI.taoPanelCanGiua(400, 300);
        initCenter(center);
        initButtons();
        ganSuKien();
        setVisible(true);
    }

    private void initCenter(JPanel center) {
        tfGiaThem = new JTextField();
        tfNLThem = new JTextField();
        tfTenSize = new JTextField();
        JPanel input1 = TaoUI.taoFieldText("Tên size", 80, 260, 30, 5, tfTenSize);
        JPanel input2 = TaoUI.taoFieldText("%NL thêm", 80, 260, 30, 5, tfNLThem);
        JPanel input3 = TaoUI.taoFieldText("%Giá thêm", 80, 260, 30, 5, tfGiaThem);

        TaoUI.addItem(center, input1, 15, false);
        TaoUI.addItem(center, input2, 15, false);
        TaoUI.addItem(center, input3, 15, false);
        add(center, BorderLayout.CENTER);
    }

    private void initButtons() {
        JPanel buttonPanel = TaoUI.taoPanelCanGiua(400, 50);
        themBtn = new JButton("Thêm");
        lamMoiBtn = new JButton("Làm mới");
        TaoUI.addItem(buttonPanel, themBtn, 5, true);
        TaoUI.addItem(buttonPanel, lamMoiBtn, 5, true);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private boolean kiemTraDuLieu() {

        String tenSize = tfTenSize.getText().trim();
        if (tenSize.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên size không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            tfTenSize.requestFocus();
            return false;
        }

        try {
            String nlStr = tfNLThem.getText().trim();
            if (nlStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập % nguyên liệu thêm!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                tfNLThem.requestFocus();
                return false;
            }
            int nlThem = Integer.parseInt(nlStr);
            if (nlThem < 0) {
                JOptionPane.showMessageDialog(this, "% Nguyên liệu không được âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "% Nguyên liệu phải là số nguyên!", "Lỗi định dạng",
                    JOptionPane.ERROR_MESSAGE);
            tfNLThem.requestFocus();
            tfNLThem.selectAll();
            return false;
        }

        try {
            String giaStr = tfGiaThem.getText().trim();
            if (giaStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập % giá thêm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                tfGiaThem.requestFocus();
                return false;
            }
            int giaThem = Integer.parseInt(giaStr);
            if (giaThem < 0) {
                JOptionPane.showMessageDialog(this, "% Giá thêm không được âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "% Giá thêm phải là số nguyên!", "Lỗi định dạng",
                    JOptionPane.ERROR_MESSAGE);
            tfGiaThem.requestFocus();
            tfGiaThem.selectAll();
            return false;
        }

        return true;
    }

    private void ganSuKien() {
        themBtn.addActionListener(e -> {
            if (kiemTraDuLieu()) {
                Size size = new Size("", "", tfTenSize.getText(), Integer.parseInt(tfGiaThem.getText()),
                        Integer.parseInt(tfNLThem.getText()));
                chiTietSanPhamDialog.themSizeVaoBang(size);
                dispose();
            }
        });

    }

}
