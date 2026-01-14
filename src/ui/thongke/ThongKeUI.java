package ui.thongke;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class ThongKeUI extends JPanel {
    private ThongKeDoanhThuPanel thongKeDoanhThu;
    private ThongKeSanPhamPanel thongKeSanPham;
    private ThongKeNhapHangPanel thongKeNhapHang;

    public ThongKeUI() {
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        thongKeDoanhThu = new ThongKeDoanhThuPanel();
        thongKeSanPham = new ThongKeSanPhamPanel();
        thongKeNhapHang = new ThongKeNhapHangPanel();

        tabbedPane.addTab("Sản phẩm", thongKeSanPham);
        tabbedPane.addTab("Doanh thu", thongKeDoanhThu);
        tabbedPane.addTab("Nhập hàng", thongKeNhapHang);

        add(tabbedPane, BorderLayout.CENTER);
    }
}