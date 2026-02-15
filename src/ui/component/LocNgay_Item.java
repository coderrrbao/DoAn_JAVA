package ui.component;

import com.toedter.calendar.JDateChooser;

import util.TaoUI;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class LocNgay_Item extends JPanel {

    private JDateChooser tuNgayDc;
    private JDateChooser denNgayDc;
    private Runnable event;

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

        add(lblTuNgay);
        add(Box.createRigidArea(new Dimension(5, 0)));
        add(tuNgayDc);
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(lblDenNgay);
        add(Box.createRigidArea(new Dimension(5, 0)));
        add(denNgayDc);

        ganSuKien();
    }

    public boolean ngayTrongKhoan(String ngayTxt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ngay = LocalDate.parse(ngayTxt, formatter);

        if (tuNgayDc.getDate() == null || denNgayDc.getDate() == null) {
            return true;
        }

        Date dateTuNgay = tuNgayDc.getDate();
        Date dateDenNgay = denNgayDc.getDate();
        LocalDate ldTuNgay = dateTuNgay.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate ldDenNgay = dateDenNgay.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        if (ldTuNgay.isAfter(ldDenNgay)) {
            return false;
        }
        if ((ldTuNgay.isEqual(ngay) || ldTuNgay.isBefore(ngay))
                && (ldDenNgay.isEqual(ngay) || ldDenNgay.isAfter(ngay))) {
            return true;
        }
        return false;
    }

    private void ganSuKien() {
        tuNgayDc.addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                event.run();
            }
        });

        denNgayDc.addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("date".equals(evt.getPropertyName())) {
                    event.run();
                }
            }
        });

    }

    public void setEvent(Runnable event) {
        this.event = event;
    }
}
