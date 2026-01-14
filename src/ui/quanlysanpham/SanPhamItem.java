
package ui.quanlysanpham;

import java.awt.Color;
import java.awt.Dimension;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import dto.SanPham;

public class SanPhamItem extends JPanel {
    JDialog chiTietSanPhamDialog;

    public SanPhamItem(SanPham sanPham) {
        Dimension size = new Dimension(150, 200);
        this.setPreferredSize(size);
        this.setBackground(Color.WHITE);

        ImageIcon icon1 = new ImageIcon(getClass().getResource("/assets/img/pepsi.png"));
        Image img1 = icon1.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon avata = new ImageIcon(img1);
        JLabel anh = new JLabel(avata);
        anh.setAlignmentX(CENTER_ALIGNMENT);

        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(anh);
        JLabel tenSp = new JLabel("Pepsi");
        tenSp.setAlignmentX(CENTER_ALIGNMENT);

        JLabel gia = new JLabel("9999999đ");
        gia.setAlignmentX(CENTER_ALIGNMENT);
        gia.setForeground(Color.red);
        this.add(tenSp);
        this.add(gia);

        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                chiTietSanPhamDialog = new ChiTietSanPhamDialog(sanPham);
            }
        });
    }
}
