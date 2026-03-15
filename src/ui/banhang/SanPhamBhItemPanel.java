package ui.banhang;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bus.LoNguyenLieuBUS;
import bus.LoSanPhamBUS;
import dto.SanPham;
import ui.component.SanPhamClickListener;
import util.TaoUI;

public class SanPhamBhItemPanel extends JPanel {

    private SanPham sanPham;

    public SanPhamBhItemPanel(SanPham sanPham, String size, SanPhamClickListener listener) {
        TaoUI.taoPanelBoxLayoutDoc(132, 170);
        TaoUI.setFixSize(this, 132, 170);
        JPanel anhPanel = TaoUI.taoPanelCanGiua(100, 70);
        TaoUI.addItem(anhPanel, TaoUI.taoJlabelAnh(sanPham.getAnh(), 70, 70), 0, true);

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

        Font fontNho = new Font(null, Font.BOLD, 10);
  
        int soLuongTon = (sanPham.getLoaiNuoc().equals("Có sẵn")
                ? LoSanPhamBUS.getLoSanPhamBUS().laySoLuongSanPhamTrongKho(sanPham.getMaSP())
                : LoNguyenLieuBUS.getLoNguyenLieuBUS().laySoLuongSanPhamPhaCheTrongKho(sanPham));
        JLabel tonKhoLb = new JLabel("Tồn kho : " + soLuongTon);
        tonKhoPanel.add(tonKhoLb);
        tonKhoPanel.add(Box.createHorizontalGlue());
        tonKhoLb.setFont(fontNho);

        add(anhPanel);
        add(tenSanPhamPanel);
        add(giaSpPanel);
        add(sizeSanPhamPanel);
        add(tonKhoPanel);

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        for (Component c : getComponents()) {
            if (c instanceof JPanel) {
                ((JPanel) c).setOpaque(false);
            }
        }
        setBackground(Color.white);

        this.sanPham = sanPham;
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
