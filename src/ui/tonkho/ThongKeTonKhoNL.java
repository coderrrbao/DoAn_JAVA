package ui.tonkho;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bus.LoNguyenLieuBUS;
import bus.NguyenLieuBUS;
import util.TaoUI;

public class ThongKeTonKhoNL extends JPanel {

    private JLabel lbSoNl, lbTongLo, lbTongLoHetHan;

    public ThongKeTonKhoNL() {

        lbSoNl = new JLabel("0");
        lbTongLo = new JLabel("0");
        lbTongLoHetHan = new JLabel("0");

        TaoUI.taoPanelBoxLayoutNgang(this, 3000, 100);
        this.setBackground(Color.WHITE);

        add(taoTheThongKe("/assets/icon/thenl.svg", "Nguyên liệu", new Color(187, 222, 251), lbSoNl));
        add(Box.createRigidArea(new Dimension(25, 0)));

        add(taoTheThongKe("/assets/icon/thelonl.svg", "Lô nguyên liệu", new Color(255, 249, 196), lbTongLo));
        add(Box.createRigidArea(new Dimension(25, 0)));

        add(taoTheThongKe("/assets/icon/thelohethan.svg", "Lô hết hạn", new Color(255, 205, 210), lbTongLoHetHan));
        add(Box.createRigidArea(new Dimension(25, 0)));

        loadDuLieu();
    }

    private JPanel taoTheThongKe(String iconPath, String tieuDe, Color mauNen, JLabel lblSo) {
        JPanel card = TaoUI.taoPanelCanGiua(280, 100);
        card.setBackground(mauNen);

        JLabel icon = TaoUI.taoJlabelAnh_Svg(iconPath, 70, 70);
        TaoUI.addItem(card, icon, 10, true);

        JPanel info = new JPanel(new BorderLayout());
        info.setOpaque(false);

        lblSo.setFont(new Font("Arial", Font.BOLD, 25));
        info.add(lblSo, BorderLayout.CENTER);

        JLabel lblTitle = new JLabel(tieuDe);
        lblTitle.setFont(new Font("Arial", Font.PLAIN, 15));
        info.add(lblTitle, BorderLayout.SOUTH);

        TaoUI.addItem(card, info, 10, true);
        return card;
    }

    public void loadDuLieu() {

        NguyenLieuBUS nguyenLieuBUS = NguyenLieuBUS.getNguyenLieuBUS();
        LoNguyenLieuBUS loNguyenLieuBUS = LoNguyenLieuBUS.getLoNguyenLieuBUS();

        if (nguyenLieuBUS.layListNguyenLieu() != null) {
            lbSoNl.setText(String.valueOf(nguyenLieuBUS.layListNguyenLieu().size()));
        }

        if (loNguyenLieuBUS.layListLoNguyenLieu() != null) {
            lbTongLo.setText(String.valueOf(loNguyenLieuBUS.layListLoNguyenLieu().size()));
        }

        lbTongLoHetHan.setText(String.valueOf(loNguyenLieuBUS.layTongLoHetHan()));
    }
}