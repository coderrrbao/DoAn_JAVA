package ui.thongke;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import util.TaoUI;

public class ThongKeUI extends JPanel {

    private CardLayout cardLayout;
    private JPanel contentPanel;

   
    public ThongKeUI() {
        setLayout(new BorderLayout());

        JPanel menuThongKe = TaoUI.taoPanelBoxLayoutNgang(0, 40);
        JButton btnSanPham = new JButton("Sản phẩm");
        JButton btnHoaDon = new JButton("Hóa đơn");
        JButton btnNhapHang = new JButton("Nhập hàng");
        JButton btnDoanhThu = new JButton("Doanh thu");

        menuThongKe.add(btnSanPham);
        menuThongKe.add(btnHoaDon);
        menuThongKe.add(btnNhapHang);
        menuThongKe.add(btnDoanhThu);

        add(menuThongKe, BorderLayout.NORTH);
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.add(new ThongKeDoanhThuPanel(), "DOANH_THU");
        contentPanel.add(new ThongKeSanPhamPanel(), "SAN_PHAM");
        cardLayout.show(contentPanel, "SAN_PHAM");
        add(contentPanel, BorderLayout.CENTER);
    }
}
