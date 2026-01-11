package ui.thongke;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartPanel;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import util.TaoUI;

public class ThongKeSanPhamPanel extends JPanel {

    public ThongKeSanPhamPanel() {
        setLayout(new BorderLayout());

        // Panel cha dùng BoxLayout Y
        JPanel thongKeSp = new JPanel();
        thongKeSp.setLayout(new BoxLayout(thongKeSp, BoxLayout.Y_AXIS));

        JPanel top = TaoUI.taoPanelBoxLayoutNgang(870, 125);
        top.add(new ThongKeChungPanel());
        thongKeSp.add(top);

        JPanel mid = TaoUI.taoPanelBoxLayoutNgang(870, 380);
        mid.setBackground(Color.red);
        thongKeSp.add(mid);
        JPanel ctnMid = TaoUI.taoPanelBorderLayout(870, 380);
        ctnMid.setBackground(Color.yellow);
        mid.add(ctnMid);
        JPanel bieuDoTron = TaoUI.taoPanelBorderLayout(400, 380);
        JPanel bieuDoCot = TaoUI.taoPanelBorderLayout(470, 380);
        ctnMid.add(bieuDoTron, BorderLayout.WEST);
        ctnMid.add(bieuDoCot, BorderLayout.CENTER);
        bieuDoCot.setBackground(Color.red);
        bieuDoTron.setBackground(Color.ORANGE);

        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
        barDataset.addValue(500, "Sản phẩm", "Tháng 1");
        barDataset.addValue(800, "Sản phẩm", "Tháng 2");
        barDataset.addValue(650, "Sản phẩm", "Tháng 3");
        barDataset.addValue(900, "Sản phẩm", "Tháng 4");
        bieuDoCot.add(TaoUI.taoBieuDoCot("SỐ LƯỢNG BÁN THEO THÁNG", "Số lượng", "Tháng", barDataset),
                BorderLayout.CENTER);

        DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("Laptop", 40);
        pieDataset.setValue("Điện thoại", 35);
        pieDataset.setValue("Phụ kiện", 15);
        pieDataset.setValue("Máy tính bảng", 10);

        bieuDoTron.add(TaoUI.taoBieuDoTron("TỶ TRỌNG DOANH THU", pieDataset), BorderLayout.CENTER);

        JPanel bottom = TaoUI.taoPanelBorderLayout(870, 200);
        bottom.setBackground(Color.cyan);
        thongKeSp.add(bottom);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Mã sản phẩm");
        model.addColumn("Tên sản phẩm");
        model.addColumn("Số lượng mua");
        model.addRow(new Object[] { "SP001", "Chuột máy tính", 2 });
        model.addRow(new Object[] { "SP002", "Bàn phím cơ", 1 });
        model.addRow(new Object[] { "SP001", "Chuột máy tính", 2 });
        model.addRow(new Object[] { "SP002", "Bàn phím cơ", 1 });
        model.addRow(new Object[] { "SP001", "Chuột máy tính", 2 });
        model.addRow(new Object[] { "SP002", "Bàn phím cơ", 1 });
        model.addRow(new Object[] { "SP001", "Chuột máy tính", 2 });
        model.addRow(new Object[] { "SP002", "Bàn phím cơ", 1 });
        model.addRow(new Object[] { "SP001", "Chuột máy tính", 2 });
        model.addRow(new Object[] { "SP002", "Bàn phím cơ", 1 });
        model.addRow(new Object[] { "SP001", "Chuột máy tính", 2 });
        model.addRow(new Object[] { "SP002", "Bàn phím cơ", 1 });
        model.addRow(new Object[] { "SP001", "Chuột máy tính", 2 });
        model.addRow(new Object[] { "SP002", "Bàn phím cơ", 1 });
        model.addRow(new Object[] { "SP001", "Chuột máy tính", 2 });
        model.addRow(new Object[] { "SP002", "Bàn phím cơ", 1 });
        JTable table = new JTable(model);
        JScrollPane scrollPaneTb = TaoUI.taoScrollPane(table);
        bottom.add(scrollPaneTb);

        JScrollPane scrollPane = TaoUI.taoScrollPane(thongKeSp);
        add(scrollPane, BorderLayout.CENTER);
    }

}
