package ui.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import dto.NhanVien;
import util.TaoUI;

public class TopPaner extends JPanel {
    private JLabel anhDaiDien;
    private JLabel ten, chucVu;

    public TopPaner() {
        setBackground(Color.white);
        TaoUI.setFixSize(this, 1000, 80);
        setBorder(BorderFactory.createEmptyBorder(0, 65, 0, 0));
        setLayout(new BorderLayout());

        ImageIcon icon = new ImageIcon(getClass().getResource("/assets/img/logo.png"));
        Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        ImageIcon anh = new ImageIcon(img);
        JLabel jLabel = new JLabel("BAO STORE", SwingConstants.LEFT);
        jLabel.setIcon(anh);
        jLabel.setFont(new Font("Lora", Font.TYPE1_FONT, 30));
        jLabel.setIconTextGap(70);
        add(jLabel);

        JPanel thongTinUser = new JPanel(new BorderLayout());
        thongTinUser.setLayout(new BoxLayout(thongTinUser, BoxLayout.X_AXIS));

        anhDaiDien = new JLabel();
        anhDaiDien.setBackground(Color.white);
        anhDaiDien.setAlignmentX(Component.CENTER_ALIGNMENT);

        thongTinUser.add(anhDaiDien, BorderLayout.CENTER);
        thongTinUser.setBackground(Color.white);

        JPanel tenVaChucVu = TaoUI.taoPanelCanGiua(140, 80);
        tenVaChucVu.setBackground(Color.white);
        ten = new JLabel();
        chucVu = new JLabel();
        chucVu.setFont(new Font("Arial", Font.BOLD, 13));
        chucVu.setForeground(Color.red);
        TaoUI.addItem(tenVaChucVu, ten, 5, false);
        TaoUI.addItem(tenVaChucVu, chucVu, 5, false);
        thongTinUser.add(tenVaChucVu, BorderLayout.EAST);

        thongTinUser.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        ten.setFont(new Font("Arial", Font.BOLD, 14));
        add(thongTinUser, BorderLayout.EAST);

        NhanVien nhanVien = new NhanVien();
        nhanVien.setAnh("/assets/img/goku.png");
        nhanVien.setChucVu("Admin");
        nhanVien.setTenNV("Nguyễn Hoài Bảo");

        capNhapThongTin(nhanVien);
    }

    public void capNhapThongTin(NhanVien user) {
        ImageIcon anh = TaoUI.taoImageIcon(user.getAnh(), 70, 70);
        anhDaiDien.setIcon(anh);
        ten.setText(user.getTenNV());
        chucVu.setText(user.getChucVu());
    }

}
