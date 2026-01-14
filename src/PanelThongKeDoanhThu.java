import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.AreaRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class PanelThongKeDoanhThu extends JPanel {

    public PanelThongKeDoanhThu() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 1. Panel Bộ lọc (Phía trên)
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTuNgay = new JLabel("Từ ngày:");
        JTextField txtTuNgay = new JTextField("01/01/2024", 10); // Thay bằng JDateChooser nếu có thư viện
        JLabel lblDenNgay = new JLabel("Đến ngày:");
        JTextField txtDenNgay = new JTextField("15/01/2024", 10);
        JButton btnThongKe = new JButton("Thống kê");
        btnThongKe.setPreferredSize(new Dimension(100, 30));

        filterPanel.add(lblTuNgay);
        filterPanel.add(txtTuNgay);
        filterPanel.add(lblDenNgay);
        filterPanel.add(txtDenNgay);
        filterPanel.add(btnThongKe);

        // 2. Tạo Biểu đồ miền (Phía giữa)
        DefaultCategoryDataset dataset = createDataset();
        JFreeChart areaChart = ChartFactory.createAreaChart(
                "BIỂU ĐỒ DOANH THU THEO THỜI GIAN", 
                "Ngày",                   // Trục X
                "Doanh thu (VNĐ)",         // Trục Y
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        // Tùy chỉnh màu sắc giống trong ảnh
        customizeChart(areaChart);

        ChartPanel chartPanel = new ChartPanel(areaChart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.WHITE);

        // 3. Panel Tổng tiền (Phía dưới)
        JLabel lblTongDoanhThu = new JLabel("Tổng doanh thu: 35,450,000 VNĐ", SwingConstants.CENTER);
        lblTongDoanhThu.setFont(new Font("Arial", Font.BOLD, 18));
        lblTongDoanhThu.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        // Thêm vào Panel chính
        add(filterPanel, BorderLayout.NORTH);
        add(chartPanel, BorderLayout.CENTER);
        add(lblTongDoanhThu, BorderLayout.SOUTH);
    }

    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String series = "Doanh thu";

        // Dữ liệu giả lập khớp với biểu đồ mẫu
        dataset.addValue(1200000, series, "Jan 1");
        dataset.addValue(2050000, series, "Jan 2");
        dataset.addValue(950000,  series, "Jan 3");
        dataset.addValue(2900000, series, "Jan 4");
        dataset.addValue(3150000, series, "Jan 5");
        dataset.addValue(1250000, series, "Jan 6");
        dataset.addValue(1700000, series, "Jan 7");
        dataset.addValue(2750000, series, "Jan 8");
        dataset.addValue(2400000, series, "Jan 9");
        dataset.addValue(1800000, series, "Jan 10");
        dataset.addValue(3050000, series, "Jan 11");
        dataset.addValue(2800000, series, "Jan 12");
        dataset.addValue(1150000, series, "Jan 13");
        dataset.addValue(3200000, series, "Jan 14");
        dataset.addValue(1300000, series, "Jan 15");

        return dataset;
    }

    private void customizeChart(JFreeChart chart) {
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.GRAY);
        
        // Chỉnh màu miền xanh lá cây nhạt giống ảnh
        AreaRenderer renderer = (AreaRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(76, 175, 80, 180)); // Màu xanh lá có độ trong suốt
    }

    // Hàm main để chạy thử nghiệm
    public static void main(String[] args) {
        JFrame frame = new JFrame("Bao Store - Thống kê");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.add(new PanelThongKeDoanhThu());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}