package ui.thongke.thongkechung;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import util.TaoUI;

public class ThongKeChungNhapPanel extends JPanel {
    private JLabel lblTongLoSo, lblLoSanPhamSo, lblLoNguyenLieuSo;

    public ThongKeChungNhapPanel() {
        TaoUI.taoPanelBoxLayoutNgang(this, 3000, 130);
         TaoUI.suaBorderChoPanel(this, 10, 0, 0, 10);
        setBackground(Color.white);

        lblTongLoSo = new JLabel("0");
        lblLoSanPhamSo = new JLabel("0");
        lblLoNguyenLieuSo = new JLabel("0");

        add(taoTheThongKe("/assets/icon/thetonglo.svg", lblTongLoSo, "Tổng lô", new Color(255, 118, 117)));
        add(Box.createRigidArea(new Dimension(10, 0)));

        add(taoTheThongKe("/assets/icon/thelosp.svg", lblLoSanPhamSo, "Lô sản phẩm", new Color(255, 234, 167)));
        add(Box.createRigidArea(new Dimension(10, 0)));

        add(taoTheThongKe("/assets/icon/thelonl.svg", lblLoNguyenLieuSo, "Lô nguyên liệu", new Color(129, 236, 236)));
    }

    private JPanel taoTheThongKe(String iconPath, JLabel lblSo, String tieuDe, Color mauNen) {

        JPanel card = TaoUI.taoPanelCanGiua(285, 130);

        card.setBackground(mauNen);

        JLabel icon = TaoUI.taoJlabelAnh_Svg(iconPath, 70, 70);
        TaoUI.addItem(card, icon, 10, true);

        JPanel info = new JPanel(new BorderLayout());
        info.setOpaque(false);

        lblSo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblSo.setForeground(new Color(45, 52, 54));
        info.add(lblSo, BorderLayout.CENTER);

        JLabel lblTitle = new JLabel(tieuDe);
        lblTitle.setFont(new Font("Arial", Font.PLAIN, 15));
        lblTitle.setForeground(new Color(45, 52, 54));
        info.add(lblTitle, BorderLayout.SOUTH);
        TaoUI.addItem(card, info, 10, true);
        card.setBorder(new EmptyBorder(10, 10, 10, 10));
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