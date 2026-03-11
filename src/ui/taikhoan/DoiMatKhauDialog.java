package ui.taikhoan;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import bus.TaiKhoanBUS;

import java.awt.Font;
import util.TaoUI;

public class DoiMatKhauDialog extends JDialog {
    private TaiKhoanUI taiKhoanUI;
    private JPasswordField TXTMatKhauMoi;
    private JPasswordField TXTXacNhanMatKhau;
    private TaiKhoanBUS taiKhoanBUS;

    public DoiMatKhauDialog(JFrame jFrame, TaiKhoanUI taiKhoanUI) {
        super(jFrame, "Đổi mật khẩu", true);

        initDoiMatKhau();

        this.taiKhoanUI = taiKhoanUI;
        setSize(400, 250);
        setLocationRelativeTo(jFrame);
        setResizable(false);
    }

    public void initDoiMatKhau() {

        JPanel mainPanel = TaoUI.taoPanelBoxLayoutDoc(400, 250);
        TaoUI.suaBorderChoPanel(mainPanel, 15, 15, 15, 15);

        JLabel lblTitle = new JLabel("ĐỔI MẬT KHẨU");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        JPanel titlePanel = TaoUI.taoPanelCanGiua(400, 40);
        titlePanel.add(lblTitle);

        TXTMatKhauMoi = new JPasswordField();
        JPanel MatKhauMoiPanel = TaoUI.taoFieldText("Mật Khẩu Mới", 80, 220, 30, 10, TXTMatKhauMoi);

        TXTXacNhanMatKhau = new JPasswordField();
        JPanel xacNhanMatKhauJPanel = TaoUI.taoFieldText("Mật Khẩu Mới", 80, 220, 30, 10, TXTXacNhanMatKhau);

        JPanel buttonPanel = TaoUI.taoPanelCanGiua(330, 30);
        JButton btnThem = new JButton("Đổi mật khẩu");
        btnThem.addActionListener(e -> xuLyDoiMatKhau());
        JButton btnHuy = new JButton("Hủy");
        btnHuy.addActionListener(e -> dispose());

        TaoUI.addItem(buttonPanel, btnThem, 5, true);
        TaoUI.addItem(buttonPanel, btnHuy, 5, true);

        mainPanel.add(titlePanel);
        mainPanel.add(javax.swing.Box.createVerticalStrut(20));
        mainPanel.add(MatKhauMoiPanel);
        mainPanel.add(javax.swing.Box.createVerticalStrut(10));
        mainPanel.add(xacNhanMatKhauJPanel);
        mainPanel.add(javax.swing.Box.createVerticalStrut(20));
        mainPanel.add(buttonPanel);

        add(mainPanel);
    }

    private void xuLyDoiMatKhau() {

        String matKhauMoi = new String(TXTMatKhauMoi.getPassword());
        String xacNhan = new String(TXTXacNhanMatKhau.getPassword());

        if (matKhauMoi.trim().isEmpty() || xacNhan.trim().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập đầy đủ thông tin");
            return;
        }

        if (!matKhauMoi.equals(xacNhan)) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Mật khẩu xác nhận không khớp");
            return;
        }

        int row = taiKhoanUI.getTableUI().getSelectedRow();

        if (row == -1) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn tài khoản cần đổi mật khẩu");
            return;
        }
        String tenDangNhap = taiKhoanUI.getModel().getValueAt(row, 1).toString();

        taiKhoanBUS = TaiKhoanBUS.getTaiKhoanBUS();
        boolean kq = taiKhoanBUS.suaMatKhau(tenDangNhap, matKhauMoi);

        if (kq) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Đổi mật khẩu thành công");
            dispose();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Đổi mật khẩu thất bại");
        }
    }

    public static void main(String[] args) {
        DoiMatKhauDialog ui = new DoiMatKhauDialog(null, null);
        ui.setVisible(true);
    }
}