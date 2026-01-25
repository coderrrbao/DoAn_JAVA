package ui.banhang;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import util.TaoUI;

public class ThongTinKhachHangPanel extends JPanel {
    private JTextField txtSdt;
    private JTextField txtTenKh;

    public ThongTinKhachHangPanel() {
        TaoUI.taoPanelBoxLayoutDoc(this, Integer.MAX_VALUE, 140);
         TaoUI.suaBorderChoPanel(this, 0, 0, 0, 10);
        JPanel title = TaoUI.taoPanelCanGiua(Integer.MAX_VALUE, 40);
        title.setBackground(new Color(225, 235, 245));
        title.add(new JLabel("Thông tin khách hàng"));
        add(title);
        add(Box.createVerticalGlue());
        JPanel input = TaoUI.taoPanelBoxLayoutDoc(Integer.MAX_VALUE, 100);
        txtSdt = new JTextField();
        txtTenKh = new JTextField();
        JPanel sdt = TaoUI.taoFieldText("Số điện thoại", 100, 250, 30, 3, txtSdt);
        JPanel tenKh = TaoUI.taoFieldText("Tên khách hàng", 100, 250, 30, 3, txtTenKh);
        input = TaoUI.suaBorderChoPanel(input, 10, 10, 10, 10);
        input.add(sdt);
        input.add(Box.createRigidArea(new Dimension(0, 10)));
        input.add(tenKh);
        add(input);
    }

    public JTextField getTxtSdt() {
        return txtSdt;
    }

    public JTextField getTxtTenKh() {
        return txtTenKh;
    }
}
