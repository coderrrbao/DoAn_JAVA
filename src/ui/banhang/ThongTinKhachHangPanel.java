package ui.banhang;

import java.awt.Dimension;

import javax.swing.Box;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import util.TaoUI;

public class ThongTinKhachHangPanel extends JPanel {
    public ThongTinKhachHangPanel() {
        TaoUI.taoPanelBoxLayoutDoc(this, Integer.MAX_VALUE, 120);

        JPanel title = TaoUI.taoPanelCanGiua(Integer.MAX_VALUE, 30);
        title.add(new JLabel("Thông tin khách hàng"));
        add(title);

        JPanel input = TaoUI.taoPanelBoxLayoutDoc(Integer.MAX_VALUE, 100);
        JTextField tfSdt = new JTextField();
        JTextField tfTenKh = new JTextField();
        JPanel sdt = TaoUI.taoFieldText("Số điện thoại", 100, 250, 35, 3, tfSdt);
        JPanel tenKh = TaoUI.taoFieldText("Tên khách hàng", 100, 250, 35, 3, tfTenKh);
        input = TaoUI.suaBorderChoPanel(input, 10, 10, 10, 10);
        input.add(sdt);
        input.add(Box.createRigidArea(new Dimension(0, 5)));
        input.add(tenKh);
        add(input);
    }
}
