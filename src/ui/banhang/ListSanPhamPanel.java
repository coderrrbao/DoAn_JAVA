package ui.banhang;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import bus.SanPhamBUS;
import dto.SanPham;
import ui.component.SanPhamClickListener;
import util.TaoUI;

public class ListSanPhamPanel extends JPanel {

    private SanPhamBUS sanPhamBUS = new SanPhamBUS();
    private SanPhamClickListener listener;

    private final int PAGE_SIZE = 16;
    private int currentPage = 1;


    private ArrayList<SanPham> dsHienTai = null;

    private JPanel listSanPhamPanel;
    private JLabel lblPage;

    public ListSanPhamPanel() {
        TaoUI.taoPanelBorderLayout(this, 0, 0);

        listener = sp -> {
            System.out.println("Đã click sản phẩm: " + sp.getTenSP());
        };

        initGUI();
    }


    private void taoTopPanel() {
        JPanel titlePanel = TaoUI.taoPanelCanGiua(Integer.MAX_VALUE, 40);
        titlePanel.setBackground(new Color(225, 235, 245));
        titlePanel.add(new JLabel("Danh sách sản phẩm"));
        add(titlePanel, BorderLayout.NORTH);
    }

    private void taoListSpPanel() {
        listSanPhamPanel = TaoUI.taoPanelFlowLayout(530, 550, 10, 10);
        TaoUI.suaBorderChoPanel(listSanPhamPanel, 0, 5, 0, 10);

        JScrollPane scrollPane = TaoUI.taoScrollPane(listSanPhamPanel);
        add(scrollPane, BorderLayout.CENTER);

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
            int tongTrang = getTongSoTrang();
            if (currentPage < tongTrang) {
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


    private void renderTrang() {
        listSanPhamPanel.removeAll();

        ArrayList<SanPham> dsTrang = new ArrayList<>();

        if (dsHienTai == null) {
            dsTrang = sanPhamBUS.layTrang(currentPage, PAGE_SIZE);
        } else {
            if (dsHienTai.isEmpty()) {
                lblPage.setText("0/0");
                listSanPhamPanel.revalidate();
                listSanPhamPanel.repaint();
                return;
            }

            int tongTrang = getTongSoTrang();
            if (currentPage > tongTrang) {
                currentPage = 1;
            }

            int from = (currentPage - 1) * PAGE_SIZE;
            int to = Math.min(from + PAGE_SIZE, dsHienTai.size());

            dsTrang = new ArrayList<>(dsHienTai.subList(from, to));
        }

        for (SanPham sp : dsTrang) {
            listSanPhamPanel.add(
                    new SanPhamBhItemPanel(sp, "Mặc định", listener)
            );
        }

        lblPage.setText(currentPage + "/" + getTongSoTrang());

        listSanPhamPanel.revalidate();
        listSanPhamPanel.repaint();
    }


    private int getTongSoTrang() {
        if (dsHienTai == null) {
            return sanPhamBUS.getTongSoTrang(PAGE_SIZE);
        }
        return (int) Math.ceil(dsHienTai.size() * 1.0 / PAGE_SIZE);
    }


    public void render(ArrayList<SanPham> dsLoc) {
        currentPage = 1;
        this.dsHienTai = dsLoc;
        renderTrang();
    }


    public void reset() {
        currentPage = 1;
        dsHienTai = null;
        renderTrang();
    }


    public void setListener(SanPhamClickListener listener) {
        this.listener = listener;
        render(null);
    }
}
