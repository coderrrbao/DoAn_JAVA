package ui.thongke;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.data.category.DefaultCategoryDataset;

import util.TaoUI;

public class ThongKeDoanhThuPanel extends JPanel {
    private DefaultCategoryDataset dataset;
    private JLabel lbTongDoanhThu;

    JButton btntuNgay, btnDenNgay, btnThongKe;

    public ThongKeDoanhThuPanel() {
        setLayout(new BorderLayout());
        initGUI();
    }

    private void initGUI() {
        add(topButtonPanel(), BorderLayout.NORTH);
        add(bieuDoPanel(), BorderLayout.CENTER);
        add(tongDoanhThu(), BorderLayout.SOUTH);
    }

    private JPanel topButtonPanel() {
        JPanel top = TaoUI.taoPanelCanGiua(880, 50);
        btntuNgay = new JButton("Từ ngày : ");
        btnDenNgay = new JButton("Đến ngày : ");
        btnThongKe = new JButton("Thống kê");
        top.add(btntuNgay);
        top.add(btnDenNgay);
        top.add(btnThongKe);
        return top;
    }

    private ChartPanel bieuDoPanel() {
        dataset = new DefaultCategoryDataset();
        String label = "Doanh thu";

        dataset.addValue(1500000, label, "14/01");
        dataset.addValue(2200000, label, "15/01");
        dataset.addValue(1800000, label, "16/01");
        dataset.addValue(2500000, label, "17/01");
        dataset.addValue(3100000, label, "18/01"); // Đỉnh cao
        dataset.addValue(1200000, label, "19/01"); // Giảm sâu
        dataset.addValue(1700000, label, "20/01");
        dataset.addValue(2100000, label, "21/01");
        dataset.addValue(2900000, label, "22/01");
        dataset.addValue(3500000, label, "23/01"); // Đỉnh mới
        dataset.addValue(1900000, label, "24/01");
        dataset.addValue(2300000, label, "25/01");

        // Gọi hàm tạo biểu đồ miền bạn đã viết
        ChartPanel chartPanel = TaoUI.taoBieuDoMien(
                "THỐNG KÊ DOANH THU CỬA HÀNG",
                "Số tiền (VNĐ)",
                "Ngày",
                dataset);

        return chartPanel;
    }

    private JPanel tongDoanhThu() {
        JPanel tongDTPanel = TaoUI.taoPanelCanGiua(880, 60);
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