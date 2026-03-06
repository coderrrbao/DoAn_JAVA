package ui.thongke;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.chart.ChartPanel;
import org.jfree.data.category.DefaultCategoryDataset;

import com.toedter.calendar.JDateChooser;

import bus.HoaDonBUS;
import bus.ThongKeBUS;
import util.TaoUI;

public class ThongKeDoanhThuPanel extends JPanel {
    private DefaultCategoryDataset dataset;
    private JLabel lbTongDoanhThu;
    private JComboBox<String> cbLoaiTk, cbThang;
    JButton btnThongKe;
    private JTextField tfNam;
    private JPanel locNgayPanel;
    private JDateChooser dateChooser;

    private JPanel cbThangPanel;
    JPanel namPanel;

    public ThongKeDoanhThuPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.white);
        TaoUI.suaBorderChoPanel(this, 0, 10, 0, 10);
        initGUI();
        ganSuKien();
        loadDuLieu();
    }

    private void ganSuKien() {
        cbLoaiTk.addActionListener(e -> {
            if (cbLoaiTk.getSelectedItem().toString().equals("Theo ngày")) {
                batThongKeTheoNgay();
            } else if (cbLoaiTk.getSelectedItem().toString().equals("Theo tháng")) {
                batThongKeTheoThang();
            } else if (cbLoaiTk.getSelectedItem().toString().equals("Theo năm")) {
                batThongKeTheoNam();
            }
        });
        cbLoaiTk.setSelectedIndex(0);
        btnThongKe.addActionListener(e -> {
            loadDuLieu();
        });
    }

    private void loadDuLieu() {
        dataset.clear();
        HoaDonBUS hoaDonBUS = new HoaDonBUS();
        ArrayList<ThongKeValue> list = new ArrayList<>();
        String loai = cbLoaiTk.getSelectedItem().toString();
        if (loai.equals("Theo ngày")) {
            if (dateChooser.getDate() == null) {
                return;
            }
            LocalDate localDate = dateChooser.getDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            String ngay = localDate.toString();

            list = hoaDonBUS.getThongKeTheoNgay(ngay);
        } else if (loai.equals("Theo tháng")) {
            int thang = Integer.parseInt(cbThang.getSelectedItem().toString());
            int nam = Integer.parseInt(tfNam.getText().trim());
            list = hoaDonBUS.getThongKeTheoThang(thang, nam);
        } else if (loai.equals("Theo năm")) {
            int nam = Integer.parseInt(tfNam.getText().trim());
            list = hoaDonBUS.getThongKeTheoNam(nam);
        }
        double tong = 0;
        for (ThongKeValue item : list) {
            dataset.addValue(item.getTongTien(), "Doanh thu", item.getThoiGian());
            tong += item.getTongTien();
        }
        lbTongDoanhThu.setText(String.format("%,.0fđ", tong));
    }

    private void initGUI() {
        add(topButtonPanel(), BorderLayout.NORTH);
        add(bieuDoPanel(), BorderLayout.CENTER);
        add(tongDoanhThu(), BorderLayout.SOUTH);
    }

    private JPanel topButtonPanel() {
        JPanel top = TaoUI.taoPanelBoxLayoutNgang(880, 40);
        top.setBackground(Color.white);
        btnThongKe = new JButton("Thống kê");
        TaoUI.setFixSize(btnThongKe, 100, 28);

        String[] loai = { "Theo ngày", "Theo tháng", "Theo năm" };
        String[] thang = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };
        cbLoaiTk = new JComboBox<>(loai);
        cbThang = new JComboBox<>(thang);
        JPanel locPanel = new JPanel();
        locPanel.setLayout(new BoxLayout(locPanel, BoxLayout.X_AXIS));
        tfNam = new JTextField();
        namPanel = TaoUI.taoFieldText("Năm : ", 40, 60, 28, 5, tfNam);
        namPanel.setBackground(Color.white);
        locNgayPanel = TaoUI.taoPanelBorderLayout(120, 28);
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        locNgayPanel.add(dateChooser, BorderLayout.CENTER);

        TaoUI.setFixSize(cbLoaiTk, 110, 28);
        TaoUI.setFixSize(cbThang, 80, 28);
        cbThangPanel = TaoUI.taoPanelBoxLayoutNgang(130, 28);
        cbThangPanel.setBackground(Color.white);
        cbThangPanel.add(new JLabel("Tháng : "));
        cbThangPanel.add(Box.createHorizontalGlue());
        cbThangPanel.add(cbThang);

        top.add(Box.createRigidArea(new Dimension(5, 0)));
        top.add(cbLoaiTk);
        top.add(Box.createRigidArea(new Dimension(5, 0)));
        top.add(locNgayPanel);
        top.add(Box.createRigidArea(new Dimension(5, 0)));
        top.add(cbThangPanel);
        top.add(Box.createRigidArea(new Dimension(5, 0)));
        top.add(namPanel);

        top.add(Box.createRigidArea(new Dimension(20, 0)));
        top.add(btnThongKe);
        return top;
    }

    private void batThongKeTheoNgay() {
        locNgayPanel.setVisible(true);
        namPanel.setVisible(false);
        cbThangPanel.setVisible(false);
    }

    private void batThongKeTheoThang() {
        locNgayPanel.setVisible(false);
        namPanel.setVisible(true);
        cbThangPanel.setVisible(true);
    }

    private void batThongKeTheoNam() {
        locNgayPanel.setVisible(false);
        namPanel.setVisible(true);
        cbThangPanel.setVisible(false);
    }

    private ChartPanel bieuDoPanel() {
        dataset = new DefaultCategoryDataset();

        ChartPanel chartPanel = TaoUI.taoBieuDoMien(
                "THỐNG KÊ DOANH THU CỬA HÀNG",
                "Số tiền (VNĐ)",
                "Ngày",
                dataset);

        return chartPanel;
    }

    private JPanel tongDoanhThu() {
        JPanel tongDTPanel = TaoUI.taoPanelCanGiua(880, 60);
        tongDTPanel.setBackground(Color.white);
        JLabel titleTongDT = new JLabel("Tổng doanh thu : ");
        titleTongDT.setFont(new Font(null, Font.BOLD, 18));
        lbTongDoanhThu = new JLabel("99999999đ");
        lbTongDoanhThu.setFont(new Font(null, Font.BOLD, 18));
        lbTongDoanhThu.setForeground(Color.red);
        TaoUI.addItem(tongDTPanel, titleTongDT, 5, true);
        TaoUI.addItem(tongDTPanel, lbTongDoanhThu, 5, true);
        return tongDTPanel;
    }

}