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

    private SanPhamBUS sanPhamBUS = SanPhamBUS.getSanPhamBUS();
    private SanPhamClickListener listener;

    private final int PAGE_SIZE = 12;
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

    public void renderTrang() {
        listSanPhamPanel.removeAll();
        SanPhamBUS sanPhamBUS = SanPhamBUS.getSanPhamBUS();

        int tongTrang = sanPhamBUS.layTongPageSanPhamBanHang(PAGE_SIZE);
        if (currentPage > tongTrang) {
            currentPage = 1;
        }
        ArrayList<SanPham> listSanPham = sanPhamBUS.laySanPhamHienThiBanHang(currentPage, PAGE_SIZE);
        if (listSanPham.size() == 0) {
            setChiSoTrang(0, 0);
        }
        setChiSoTrang(currentPage, tongTrang);
        for (SanPham sanPham : listSanPham) {
            listSanPhamPanel.add(
                    new SanPhamBhItemPanel(sanPham, "Mặc định", listener));
        }
        listSanPhamPanel.revalidate();
        listSanPhamPanel.repaint();
    }

    private void setChiSoTrang(int chiSoHt, int csMax) {
        lblPage.setText(chiSoHt + "/" + csMax);
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
