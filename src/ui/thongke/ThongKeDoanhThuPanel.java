package ui.thongke;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.data.category.DefaultCategoryDataset;

import bus.HoaDonBUS;
import ui.component.LocNgay_Item;
import util.TaoUI;

public class ThongKeDoanhThuPanel extends JPanel {
    private DefaultCategoryDataset dataset;
    private JLabel lbTongDoanhThu;
    private JComboBox<String> cbLoaiTk;
    JButton btnThongKe;

    public ThongKeDoanhThuPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.white);
        TaoUI.suaBorderChoPanel(this, 0, 10, 0, 10);
        initGUI();
    }

    private void loadDuLieu() {
        HoaDonBUS hoaDonBUS = new HoaDonBUS();

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
        cbLoaiTk = new JComboBox<>(loai);
        top.add(cbLoaiTk);
        cbLoaiTk.setPreferredSize(new Dimension(200,28));
        cbLoaiTk.setMaximumSize(new Dimension(200,28));
        top.add(Box.createRigidArea(new Dimension(20, 0)));
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