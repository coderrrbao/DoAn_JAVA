package ui.banhang;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import util.TaoUI;

public class ThanhToanPanel extends JPanel {
    private JLabel tongHoaDonText, tienKMText, tongThanhToanText;
    private JComboBox<String> cbxKhuyenMai;
    private JButton btnMGG, thanhToanButton, huyButton;

    public ThanhToanPanel() {
        TaoUI.taoPanelBoxLayoutDoc(this, 0, 180);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel mainGridPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        mainGridPanel.setOpaque(false);

        JPanel leftPanel = new JPanel();
        TaoUI.taoPanelBoxLayoutDoc(leftPanel, 0, 40);
        leftPanel.add(Box.createVerticalGlue());

        tongHoaDonText = addDongTien(leftPanel, "Tổng hóa đơn     : ", "0 VNĐ", Color.BLACK, 14);
        tienKMText = addDongTien(leftPanel, "Tiền khuyến mãi  : ", "0 VNĐ", Color.RED, 14);
        tongThanhToanText = addDongTien(leftPanel, "Tổng thanh toán : ", "0 VNĐ", new Color(0, 128, 0), 16);

        leftPanel.add(Box.createVerticalGlue());

        JPanel rightPanel = TaoUI.taoPanelCanGiua(300, 40);
        JLabel lblMa = new JLabel("Chương trình KM:");
        lblMa.setFont(new Font("Arial", Font.PLAIN, 14));

        cbxKhuyenMai = new JComboBox<>();
        cbxKhuyenMai.addItem("-- Không áp dụng --");
        TaoUI.setFixSize(cbxKhuyenMai, 180, 30);

        btnMGG = new JButton("Áp dụng");
        TaoUI.setFixSize(btnMGG, 100, 30);

        TaoUI.addItem(rightPanel, lblMa, 3, false);
        TaoUI.addItem(rightPanel, cbxKhuyenMai, 3, false);
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
    }

    public JComboBox<String> getCbxKhuyenMai() {
        return cbxKhuyenMai;
    }

    public String getMaGiamGiaInput() {
        if (cbxKhuyenMai.getSelectedIndex() == 0 || cbxKhuyenMai.getSelectedItem() == null) {
            return "";
        }
        String selectedItem = cbxKhuyenMai.getSelectedItem().toString();
        if (selectedItem.contains(" - ")) {
            return selectedItem.split(" - ")[0].trim();
        }
        return selectedItem;
    }

    public JButton getBtnThanhToan() {
        return thanhToanButton;
    }

    public JButton getBtnHuy() {
        return huyButton;
    }

    public JButton getBtnXacNhanMGG() {
        return btnMGG;
    }

    public double getTongThanhToan() {
        String text = tongThanhToanText.getText();
        String soSach = text.replace(".", "").replace(",", "").replace(" VNĐ", "").trim();
        try {
            return Double.parseDouble(soSach);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void capNhatThongTinThanhToan(double tongTienHang, double tienGiam) {
        DecimalFormat df = new DecimalFormat("#,###");

        tongHoaDonText.setText(df.format(tongTienHang) + " VNĐ");
        tienKMText.setText(df.format(tienGiam) + " VNĐ");

        double tongThanhToan = tongTienHang - tienGiam;
        if (tongThanhToan < 0)
            tongThanhToan = 0;

        tongThanhToanText.setText(df.format(tongThanhToan) + " VNĐ");
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
}