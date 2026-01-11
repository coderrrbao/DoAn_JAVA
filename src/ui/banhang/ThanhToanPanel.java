package ui.banhang;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

import javax.swing.JPanel;
import javax.swing.JTextField;

import util.TaoUI;

public class ThanhToanPanel extends JPanel {
    private long tongHoaDon = 1600;
    private JButton thanhToanButton, huyButton;

    public ThanhToanPanel() {
        TaoUI.taoPanelBoxLayoutDoc(this, 0, 80);

        JTextField tfTongHoaDon = new JTextField();
        JPanel tongHoaDonPanel = TaoUI.taoFieldText("Tổng hóa đơn :", 100, 250, 35, 3, tfTongHoaDon);
        tfTongHoaDon.setBorder(null);
        tfTongHoaDon.setBackground(null);
        tfTongHoaDon.setText(tongHoaDon + "đ");
        tfTongHoaDon.setFont(new Font("Arial", Font.PLAIN, 16));
        tfTongHoaDon.setForeground(Color.red);
        add(tongHoaDonPanel);

        JPanel buttonsPanel = TaoUI.taoPanelCanGiua(Integer.MAX_VALUE, 50);
        thanhToanButton = new JButton("Thanh toán");
        huyButton = new JButton("Hủy");
        TaoUI.addItem(buttonsPanel, thanhToanButton, 5, true);
        TaoUI.addItem(buttonsPanel, huyButton, 5, true);
        add(buttonsPanel);
    }

}
