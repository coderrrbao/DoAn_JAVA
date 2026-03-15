package ui.thongtinuser;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import bus.TaiKhoanBUS;
import ui.login.PhienDangNhap;
import ui.login.LoginUI;
import util.TaoTinNhan;
import util.TaoUI;

public class ThongTinTaiKhoanPanel extends JPanel {
    private JPasswordField  tfMatKhau,tfMatKhauMoi, tfXacNhan;
    JButton btnXacNhan, btnHuy;

    public ThongTinTaiKhoanPanel() {
        TaoUI.taoPanelCanGiua(this, 400, 530);
        initGUI();
        ganSuKien();
    }

    private void initGUI() {
        tfMatKhau = new JPasswordField();
        tfMatKhauMoi = new JPasswordField();
        tfXacNhan = new JPasswordField();

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

    private void ganSuKien() {

        btnXacNhan.addActionListener(e -> {
            TaiKhoanBUS taiKhoanBUS = TaiKhoanBUS.getTaiKhoanBUS();
            char[] mkMkCu = tfMatKhau.getPassword();
            char[] mkMoi = tfMatKhauMoi.getPassword();
            char[] mkXacNhan = tfXacNhan.getPassword();
            String matKhauCu = new String(mkMkCu);
            String matKhauMoi = new String(mkMoi);
            String xacNhan = new String(mkXacNhan);

            if (matKhauCu.equals(PhienDangNhap.getTaiKhoan().getMatKhau())) {
                if (xacNhan.equals(matKhauMoi)) {
                    if (taiKhoanBUS.suaMatKhau(PhienDangNhap.getTaiKhoan().getTenDangNhap(), matKhauMoi)) {
                        TaoTinNhan.showAutoCloseMessage("Đổi mật khẩu thành công", "Thông báo", 1);
                        LoginUI.getLoginUI().getMainFrame().loadAllData();
                    } else {
                        TaoTinNhan.showAutoCloseMessage("Đổi mật khẩu thất bại", "Thông báo", 1);
                    }
                } else {
                    TaoTinNhan.showAutoCloseMessage("Mật khẩu mới và xác nhận mật khẩu không  trùng khớp", "Thông báo",
                            1);
                }
            } else {
                TaoTinNhan.showAutoCloseMessage("Mật khẩu không chính xác", "Thông báo", 1);
            }
        });
    }

}
