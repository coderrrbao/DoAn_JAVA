package ui.thongke.thongkechung;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import bus.HoaDonBUS;
import bus.KhachHangBUS;
import bus.SanPhamBUS;
import util.TaoUI;

public class ThongKeChungSpPanel extends JPanel {
    private JLabel lbSoSP, lbSoHoaDon, lbSoKh, lbDoanhThu;

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
        info.add(lblTitle, BorderLayout.SOUTH);

        TaoUI.addItem(card, info, 10, true);
        return card;
    }

    public ThongKeChungSpPanel() {

        TaoUI.suaBorderChoPanel(this, 0, 0, 10, 0);
        setLayout(new GridLayout(1, 2, 10, 10));
        JPanel ctn1 = new JPanel(new GridLayout(1, 2, 10, 10));
        JPanel ctn2 = new JPanel(new GridLayout(1, 2, 10, 10));

        add(ctn1);
        add(ctn2);

        lbDoanhThu = new JLabel();
        lbSoHoaDon = new JLabel();
        lbSoKh = new JLabel();
        lbSoSP = new JLabel();
        ctn1.add(taoTheThongKe("/assets/icon/thesp.svg", lbSoSP, "Sản phẩm", new Color(255, 118, 117)));
        ctn1.add(taoTheThongKe("/assets/icon/thehd.svg", lbSoHoaDon, "Hóa đơn", new Color(255, 234, 167)));

        ctn2.add(taoTheThongKe("/assets/icon/thekh.svg", lbSoKh, "Khách hàng", new Color(129, 236, 236)));
        ctn2.add(taoTheThongKe("/assets/icon/thedt.svg", lbDoanhThu, "Doanh thu", new Color(85, 230, 193)));
    }

    public void loadDuLieu() {
        SanPhamBUS sanPhamBUS = SanPhamBUS.getSanPhamBUS();
        HoaDonBUS hoaDonBUS = new HoaDonBUS();
        KhachHangBUS khachHangBUS = new KhachHangBUS();
        lbSoSP.setText(String.valueOf(sanPhamBUS.layListSanPham().size()));
        lbSoHoaDon.setText(String.valueOf(hoaDonBUS.layDanhSachHoaDon().size()));
        lbSoKh.setText(String.valueOf(khachHangBUS.layDanhSachKhachHang().size()));
        double tongDanhThu = hoaDonBUS.layTongDanhThu();
        String tongDtText;
        if (tongDanhThu < 1000000) {
            tongDtText=String.valueOf(tongDanhThu)+" VNĐ";
        } else if (1000000 <= tongDanhThu || tongDanhThu <= 999999999) {
            tongDtText = String.valueOf(tongDanhThu / 1000000) + " Tr";
        } else {
            tongDtText = String.valueOf(tongDanhThu / 1000000000) + " Tỉ";
        }
        lbDoanhThu.setText(tongDtText);
    }
}
