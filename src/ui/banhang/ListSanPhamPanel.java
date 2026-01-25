package ui.banhang;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import bus.SanPhamBUS;
import dto.NhaCungCap;
import dto.SanPham;
import ui.component.SanPhamClickListener;
import util.TaoUI;

public class ListSanPhamPanel extends JPanel {

    private SanPhamClickListener listener;
    private SanPhamBUS sanPhamBUS = new SanPhamBUS();
    private int currentPage = 1;
    private final int PAGE_SIZE = 16;
    private JPanel listSanPhamPanel;
    private JLabel lblPage;


    public ListSanPhamPanel() {
        TaoUI.taoPanelBorderLayout(this, 0, 0);
        this.listener = sanPham -> {
            System.out.println("Đã click sản phẩm: " + sanPham.getTenSP()); //Dòng này để test
        };
        initGUI();
    }

    private void taoTopPanel() {
        JPanel titlePanel = TaoUI.taoPanelCanGiua(Integer.MAX_VALUE, 40);
        titlePanel.add(new JLabel("Danh sách sản phẩm"));
        titlePanel.setBackground(new Color(225, 235, 245));
        add(titlePanel, BorderLayout.NORTH);
    }

    private void renderTrang() {
        listSanPhamPanel.removeAll();

        ArrayList<SanPham> ds = sanPhamBUS.layTrang(currentPage, PAGE_SIZE);

        for (SanPham sp : ds) {
            listSanPhamPanel.add(
                    new SanPhamBhItemPanel(sp, "Mặc định", listener)
            );
        }

        lblPage.setText(currentPage + "/" + sanPhamBUS.getTongSoTrang(PAGE_SIZE));

        listSanPhamPanel.revalidate();
        listSanPhamPanel.repaint();
    }


    private void taoListSpPanel() {
        this.listSanPhamPanel = TaoUI.taoPanelFlowLayout(530, 550, 10, 10);
        TaoUI.suaBorderChoPanel(listSanPhamPanel, 0, 5, 0, 10);

        JScrollPane scrollPaneListSp = TaoUI.taoScrollPane(listSanPhamPanel);

       
        add(scrollPaneListSp, BorderLayout.CENTER);

        renderTrang();
    }


    private void taoThanhChuyenPage() {
        JPanel phanTrang = TaoUI.taoPanelFlowLayout(Integer.MAX_VALUE, 40, 2, 0);
        JButton btnPrev = new JButton("<");
        JButton btnNext = new JButton(">");
        lblPage = new JLabel();

        btnPrev.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                renderTrang();
            }
        });

        btnNext.addActionListener(e -> {
            if (currentPage < sanPhamBUS.getTongSoTrang(PAGE_SIZE)) {
                currentPage++;
                renderTrang();
            }
        });

        phanTrang.add(btnPrev);
        phanTrang.add(lblPage);
        phanTrang.add(btnNext);

        add(phanTrang, BorderLayout.SOUTH);
    }

    private void initGUI() {
        taoTopPanel();
        taoThanhChuyenPage();
        taoListSpPanel();
    }
}
