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

import util.TaoUI;

public class TopPaner extends JPanel {
    public TopPaner() {
        setBackground(Color.white);
        setPreferredSize(new Dimension(1000, 80));
        setMinimumSize(new Dimension(1000, 80));
        setMaximumSize(new Dimension(new Dimension(1000, 80)));
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

        JPanel thongTinUser = new JPanel();
        thongTinUser.setLayout(new BoxLayout(thongTinUser, BoxLayout.X_AXIS));

        ImageIcon icon1 = new ImageIcon(getClass().getResource("/assets/img/goku.png"));
        icon1 = TaoUI.taoAnhBoTron(icon1, 50);
        Image img1 = icon1.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon avata = new ImageIcon(img1);
        JLabel anhDaiDien = new JLabel(avata);
        
        anhDaiDien.setBackground(Color.white);
        anhDaiDien.setAlignmentX(Component.CENTER_ALIGNMENT);
        thongTinUser.add(anhDaiDien);
        thongTinUser.setBackground(Color.white);
        JLabel ten = new JLabel("Nguyễn Hoài Bảo");
        thongTinUser.add(Box.createRigidArea(new Dimension(3, 0)));
        thongTinUser.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        ten.setAlignmentX(Component.CENTER_ALIGNMENT);
        ten.setFont(new Font("Arial", Font.BOLD, 15));
        thongTinUser.add(ten);
        add(thongTinUser, BorderLayout.EAST);

    }
}
