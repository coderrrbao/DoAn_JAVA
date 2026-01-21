package ui.banhang;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import util.TaoUI;

public class ThanhToanPanel extends JPanel {
    private JLabel tongHoaDonText, tienKMText, tongThanhToanText;
    private JTextField txtMaGiamGia;
    private JButton btnMGG, thanhToanButton, huyButton;

    public ThanhToanPanel() {
        // Layout tổng của ThanhToanPanel là dọc
        TaoUI.taoPanelBoxLayoutDoc(this, 0, 180);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel mainGridPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        mainGridPanel.setOpaque(false);
        JPanel leftPanel = new JPanel();
        TaoUI.taoPanelBoxLayoutDoc(leftPanel, 0, 40);
        leftPanel.add(Box.createVerticalGlue());

        tongHoaDonText = addDongTien(leftPanel, "Tổng hóa đơn     : ", "100.000", Color.BLACK, 14);
        tienKMText = addDongTien(leftPanel, "Tiền khuyến mãi  : ", "9.000", Color.RED, 14);
        tongThanhToanText = addDongTien(leftPanel, "Tổng thanh toán : ", "91.000", new Color(0, 128, 0), 16);
        leftPanel.add(Box.createVerticalGlue());
        JPanel rightPanel = TaoUI.taoPanelCanGiua(300, 40);
        JLabel lblMa = new JLabel("Mã giảm giá:");
        lblMa.setFont(new Font("Arial", Font.PLAIN, 14));

        txtMaGiamGia = new JTextField(15);
        txtMaGiamGia.setPreferredSize(new Dimension(180, 30));
        btnMGG = new JButton("Xác nhận");
        TaoUI.setFixSize(btnMGG, 150, 30);

        TaoUI.addItem(rightPanel, lblMa, 3, false);
        TaoUI.addItem(rightPanel, txtMaGiamGia, 3, false);
        TaoUI.addItem(rightPanel, btnMGG, 3, false);

        mainGridPanel.add(leftPanel);
        mainGridPanel.add(rightPanel);

        add(mainGridPanel);

        JPanel buttonsPanel = TaoUI.taoPanelCanGiua(Integer.MAX_VALUE, 40);
        thanhToanButton = new JButton("Thanh toán");
        thanhToanButton.setPreferredSize(new Dimension(120, 25));
        huyButton = new JButton("Hủy");
        huyButton.setPreferredSize(new Dimension(100, 25));

        TaoUI.addItem(buttonsPanel, thanhToanButton, 5, true);
        TaoUI.addItem(buttonsPanel, huyButton, 5, true);
        add(buttonsPanel);

        ganSuKien();
    }

    private JLabel addDongTien(JPanel parent, String title, String value, Color color, int fontSize) {
        JPanel p = TaoUI.taoPanelBoxLayoutNgang(3000, 30);
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Arial", Font.BOLD, fontSize));

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Arial", Font.BOLD, fontSize));
        lblValue.setForeground(color);

        p.add(lblTitle);
        p.add(lblValue);
        parent.add(p);
        return lblValue;
    }

    private void ganSuKien() {
        btnMGG.addActionListener(e -> {
            if (btnMGG.getText().equals("Xác nhận")) {
                // viết logic ở đây babe
                btnMGG.setText("Hủy");
            } else if (btnMGG.getText().equals("Hủy")) {
                // xóa tiền khuyến mãi ,cập nhập....
                btnMGG.setText("Xác nhận");
            }
        });
    }
}