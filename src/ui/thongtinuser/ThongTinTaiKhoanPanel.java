package ui.thongtinuser;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import util.TaoUI;

public class ThongTinTaiKhoanPanel extends JPanel {
    private JTextField tfMatKhau, tfMatKhauMoi, tfXacNhan;
    JButton btnXacNhan, btnHuy;

    public ThongTinTaiKhoanPanel() {
        TaoUI.taoPanelCanGiua(this, 400, 530);

        initGUI();
    }

    private void initGUI() {
        tfMatKhau = new JTextField();
        tfMatKhauMoi = new JTextField();
        tfXacNhan = new JTextField();

        JPanel title = TaoUI.taoPanelCanGiua(400, 40);
        JLabel lblDoiMK = new JLabel("Đổi mật khẩu");
        lblDoiMK.setFont(new Font("Arial", Font.BOLD, 15));
        TaoUI.addItem(title, lblDoiMK, 0, true);
        TaoUI.addItem(this, title, 10, false);
        TaoUI.addItem(this, TaoUI.taoFieldText("Nhập mật khẩu", 120, 200, 35, 3, tfMatKhau), 10, false);
        TaoUI.addItem(this, TaoUI.taoFieldText("Nhập mật khẩu mới", 120, 200, 35, 3, tfMatKhauMoi), 10, false);
        TaoUI.addItem(this, TaoUI.taoFieldText("Xác nhận mật khẩu", 120, 200, 35, 3, tfXacNhan), 10, false);

        btnHuy = new JButton("Hủy");
        btnXacNhan = new JButton("Xác nhận");
        JPanel buttoPanel = TaoUI.taoPanelCanGiua(400, 30);
        TaoUI.addItem(buttoPanel, btnXacNhan, 10, true);
        TaoUI.addItem(buttoPanel, btnHuy, 10, true);
        TaoUI.addItem(this, buttoPanel, 10, false);
    }
}
