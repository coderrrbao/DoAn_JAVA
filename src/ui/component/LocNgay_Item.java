package ui.component;

import com.toedter.calendar.JDateChooser;

import util.TaoUI;

import javax.swing.*;
import java.awt.*;

public class LocNgay_Item extends JPanel {

    private JDateChooser tuNgayDc;
    private JDateChooser denNgayDc;
    private JButton locBtn;

    public LocNgay_Item(int width, int height) {
        TaoUI.taoPanelBoxLayoutNgang(this, width, height);
        setBackground(Color.white);
        initUI();
    }

    private void initUI() {
        JLabel lblTuNgay = new JLabel("Từ :");
        tuNgayDc = new JDateChooser();
        tuNgayDc.setDateFormatString("dd-MM-yyyy");

        JLabel lblDenNgay = new JLabel("Đến :");
        denNgayDc = new JDateChooser();
        denNgayDc.setDateFormatString("dd-MM-yyyy");

        locBtn = new JButton("Lọc");

        add(lblTuNgay);
        add(Box.createRigidArea(new Dimension(5, 0)));
        add(tuNgayDc);
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(lblDenNgay);
        add(Box.createRigidArea(new Dimension(5, 0)));
        add(denNgayDc);
        add(Box.createRigidArea(new Dimension(5, 0)));
        add(locBtn);
    }

    private void layNgay() {
        // Date ngay = dateChooser.getDate();
        // if (ngay != null) {
        // java.text.SimpleDateFormat sdf = new
        // java.text.SimpleDateFormat("dd-MM-yyyy");
        // String ngayDinhDang = sdf.format(ngay);

        // JOptionPane.showMessageDialog(this, "Ngày đã chọn: " + ngayDinhDang);
        // } else {
        // JOptionPane.showMessageDialog(this, "Chưa chọn ngày!");
        // }
    }
}
