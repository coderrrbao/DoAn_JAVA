package ui.banhang;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import util.TaoUI;

public class ListSanPhamPanel extends JPanel {

    public JPanel taoSanPham() {
        JPanel sanPhamPanel = TaoUI.taoPanelBoxLayoutDoc(100, 150);
        sanPhamPanel.setBackground(Color.red);
        return sanPhamPanel;
    }

    public ListSanPhamPanel() {
        TaoUI.taoPanelBorderLayout(this, 0, 0);
        setBackground(Color.red);
        JPanel titlePanel = TaoUI.taoPanelCanGiua(Integer.MAX_VALUE, 30);
        titlePanel.add(new JLabel("Danh sách sản phẩm"));
        add(titlePanel, BorderLayout.NORTH);

        JPanel listSanPhamPanel = new JPanel(new GridLayout(0,4,5,5));
        listSanPhamPanel.setBorder(BorderFactory.createEmptyBorder(5, 5,5, 5));
        JScrollPane scrollPaneListSp = TaoUI.taoScrollPane(listSanPhamPanel);
        for (int i = 0; i < 9; i++) {
            listSanPhamPanel.add(taoSanPham());
        }
        add(scrollPaneListSp, BorderLayout.CENTER);

        JPanel phanTrang = TaoUI.taoPanelFlowLayout(Integer.MAX_VALUE, 40, 2, 0);
        phanTrang.add(new Button("<"));
        phanTrang.add(new JLabel("1/2"));
        phanTrang.add(new Button(">"));
        add(phanTrang, BorderLayout.SOUTH);
    }
}
