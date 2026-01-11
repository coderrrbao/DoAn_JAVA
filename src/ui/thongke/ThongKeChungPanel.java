package ui.thongke;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import util.TaoUI;

public class ThongKeChungPanel extends JPanel{
     private JPanel taoTheThongKe(String iconPath, String so, String tieuDe, Color mauNen) {
        JPanel card = TaoUI.taoPanelCanGiua(200, 100);
        card.setBackground(mauNen);

        JLabel icon = TaoUI.taoJlabelAnh(iconPath, 50, 50);
        TaoUI.addItem(card, icon, 10, true);

        JPanel info = new JPanel(new BorderLayout());
        info.setOpaque(false);

        JLabel lblSo = new JLabel(so);
        lblSo.setFont(new Font("Arial", Font.BOLD, 22));
        info.add(lblSo, BorderLayout.CENTER);

        JLabel lblTitle = new JLabel(tieuDe);
        lblTitle.setFont(new Font("Arial", Font.PLAIN, 13));
        info.add(lblTitle, BorderLayout.SOUTH);

        TaoUI.addItem(card, info, 10, true);
        return card;
    }
    public ThongKeChungPanel(){
        TaoUI.taoPanelBoxLayoutNgang(this, 3000, 100);
        add(taoTheThongKe("/assets/img/logo.png", "36", "Sản phẩm", Color.RED));
        add(taoTheThongKe("/assets/img/logo.png", "12", "Hóa đơn", Color.YELLOW));
        add(taoTheThongKe("/assets/img/logo.png", "5", "Phiếu nhập", Color.CYAN));
        add(taoTheThongKe("/assets/img/logo.png", "99tr", "Doanh thu", Color.GREEN));
    }
}
