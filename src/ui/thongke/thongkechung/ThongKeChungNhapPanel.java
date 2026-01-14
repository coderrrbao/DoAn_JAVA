package ui.thongke.thongkechung;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import util.TaoUI;

public class ThongKeChungNhapPanel extends JPanel {
    private JLabel lblTongLoSo, lblLoSanPhamSo, lblLoNguyenLieuSo;

    public ThongKeChungNhapPanel() {
        TaoUI.taoPanelBoxLayoutNgang(this, 3000, 100);
        
        lblTongLoSo = new JLabel("0");
        lblLoSanPhamSo = new JLabel("0");
        lblLoNguyenLieuSo = new JLabel("0");

        add(taoTheThongKe("/assets/img/logo.png", lblTongLoSo, "Tổng lô", Color.RED));
        add(Box.createRigidArea(new Dimension(5, 0)));
        add(taoTheThongKe("/assets/img/logo.png", lblLoSanPhamSo, "Lô sản phẩm", Color.YELLOW));
        add(Box.createRigidArea(new Dimension(5, 0)));
        add(taoTheThongKe("/assets/img/logo.png", lblLoNguyenLieuSo, "Lô nguyên liệu", Color.CYAN));
    }
    private JPanel taoTheThongKe(String iconPath, JLabel lblSo, String tieuDe, Color mauNen) {
        JPanel card = TaoUI.taoPanelCanGiua(250, 100);
        card.setBackground(mauNen);

        JLabel icon = TaoUI.taoJlabelAnh(iconPath, 50, 50);
        TaoUI.addItem(card, icon, 10, true);

        JPanel info = new JPanel(new BorderLayout());
        info.setOpaque(false);
        lblSo.setFont(new Font("Arial", Font.BOLD, 22));
        info.add(lblSo, BorderLayout.CENTER);

        JLabel lblTitle = new JLabel(tieuDe);
        lblTitle.setFont(new Font("Arial", Font.PLAIN, 13));
        info.add(lblTitle, BorderLayout.SOUTH);

        TaoUI.addItem(card, info, 10, true);
        return card;
    }

    public void setTongLo(int so) {
        lblTongLoSo.setText(String.valueOf(so));
    }

    public void setLoSanPham(int so) {
        lblLoSanPhamSo.setText(String.valueOf(so));
    }

    public void setLoNguyenLieu(int so) {
        lblLoNguyenLieuSo.setText(String.valueOf(so));
    }
    
    public void capNhatThongKe(int tong, int sp, int nl) {
        setTongLo(tong);
        setLoSanPham(sp);
        setLoNguyenLieu(nl);
    }
}