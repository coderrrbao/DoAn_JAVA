package ui.banhang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dto.NhaCungCap;
import dto.SanPham;
import util.TaoUI;

public class ListSanPhamPanel extends JPanel {

    public ListSanPhamPanel() {
        TaoUI.taoPanelBorderLayout(this, 0, 0);
        initGUI();
    }

    private void taoTopPanel() {
        JPanel titlePanel = TaoUI.taoPanelCanGiua(Integer.MAX_VALUE, 40);
        titlePanel.add(new JLabel("Danh sách sản phẩm"));
        titlePanel.setBackground(new Color(225, 235, 245));
        add(titlePanel, BorderLayout.NORTH);
    }

    private void taoListSpPanel() {
        JPanel listSanPhamPanel = TaoUI.taoPanelFlowLayout(530, 550, 10, 10);
        TaoUI.suaBorderChoPanel(listSanPhamPanel, 0, 5, 0, 10);
        JScrollPane scrollPaneListSp = TaoUI.taoScrollPane(listSanPhamPanel);
        for (int i = 0; i < 21; i++) {
            NhaCungCap ncc = new NhaCungCap("NCC01", "PepsiCo", "0123456789", "VN");

            SanPham sp = new SanPham();
            sp.setMaSP("SP" + System.currentTimeMillis() % 1000);
            sp.setTenSP("Pepsi Lon 320ml");
            sp.setGiaBan(11000);
            sp.setLoaiNuoc("Có sẵn");
            sp.setNhaCungCap(ncc);
            listSanPhamPanel.add(new SanPhamBhItemPanel(sp, "Mặc định"));
        }
        add(scrollPaneListSp, BorderLayout.CENTER);
    }

    private void taoThanhChuyenPage() {
        JPanel phanTrang = TaoUI.taoPanelFlowLayout(Integer.MAX_VALUE, 40, 2, 0);
        phanTrang.add(new JButton("<"));
        phanTrang.add(new JLabel("1/2"));
        phanTrang.add(new JButton(">"));
        add(phanTrang, BorderLayout.SOUTH);
    }

    private void initGUI() {
        taoTopPanel();
        taoListSpPanel();
        taoThanhChuyenPage();
    }
}
