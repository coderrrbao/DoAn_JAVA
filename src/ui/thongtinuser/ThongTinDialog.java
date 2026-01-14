package ui.thongtinuser;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import dto.NhanVien;

public class ThongTinDialog extends JDialog {
    private ThongTinTaiKhoanPanel thongTinTaiKhoanPanel;
    private ThongTinCaNhanPanel thongTinCaNhanPanel;
    public ThongTinDialog(JFrame frame,NhanVien user) {
        super(frame, "Thông tin user", true);
        setSize(400, 530);
        setLocationRelativeTo(frame);
        setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        add(tabbedPane,BorderLayout.CENTER);
        thongTinCaNhanPanel = new ThongTinCaNhanPanel();
        thongTinTaiKhoanPanel = new ThongTinTaiKhoanPanel();
        tabbedPane.addTab("Thông tin", thongTinCaNhanPanel);
        tabbedPane.addTab("Tài khoản", thongTinTaiKhoanPanel);
    }
}
