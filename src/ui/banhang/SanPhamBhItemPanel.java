package ui.banhang;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dto.SanPham;
import ui.component.SanPhamClickListener;
import util.TaoUI;

public class SanPhamBhItemPanel extends JPanel {


    private SanPhamClickListener listener;
    private SanPham sanPham;


    public SanPhamBhItemPanel(SanPham sanPham, String size, SanPhamClickListener listener) {
        TaoUI.taoPanelBoxLayoutDoc(120, 170);
        TaoUI.setFixSize(this, 120, 170);
        JPanel anhPanel = TaoUI.taoPanelCanGiua(100, 70);
        TaoUI.addItem(anhPanel, TaoUI.taoJlabelAnh("../assets/img/pepsi.png", 70, 70), 0, true);

        JPanel tenSanPhamPanel = TaoUI.taoPanelCanGiua(100, 20);
        TaoUI.addItem(tenSanPhamPanel, new JLabel(sanPham.getTenSP()), 0, true);

        JPanel giaSpPanel = TaoUI.taoPanelCanGiua(100, 20);
        JLabel lblGia = new JLabel(String.format("%,d", sanPham.getGiaBan()) + "đ");
        lblGia.setForeground(Color.RED);
        lblGia.setFont(new Font("Arial", Font.BOLD, 13));
        TaoUI.addItem(giaSpPanel, lblGia, 0, true);

        JPanel sizeSanPhamPanel = TaoUI.taoPanelCanGiua(100, 15);
        TaoUI.addItem(sizeSanPhamPanel, new JLabel(size), 0, true);

        JPanel tonKhoPanel = TaoUI.taoPanelBoxLayoutNgang(100, 15);
        tonKhoPanel.add(Box.createHorizontalGlue());
        JLabel titleTon = new JLabel("Tồn : ");
        JLabel tonNumber = new JLabel(String.valueOf(sanPham.getSoLuongTon()));
        Font fontNho = new Font(null, Font.BOLD, 10);
        titleTon.setFont(fontNho);
        tonNumber.setFont(fontNho);
        tonKhoPanel.add(titleTon);
        tonKhoPanel.add(tonNumber);
        tonKhoPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        add(anhPanel);
        add(tenSanPhamPanel);
        add(giaSpPanel);
        add(sizeSanPhamPanel);
        add(tonKhoPanel);
        for (Component c : getComponents()) {
            if (c instanceof JPanel) {
                ((JPanel) c).setOpaque(false);
            }
        }
        setBackground(Color.white);

        this.sanPham = sanPham;
        this.listener = listener;


        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (listener != null) {
                    listener.onSanPhamClicked(SanPhamBhItemPanel.this.sanPham);
                }
            }
        });
    }
}
