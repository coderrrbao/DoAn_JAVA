package ui.tonkho;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import ui.component.Search_Item;
import util.TaoUI;

public class TonKhoSanPhamPanel extends JPanel {
    private JTable table;

    private JButton btnLoc;
    private Search_Item search_Item;
    private JButton btnNhapHang;
    private JButton btnSua;
    private JButton btnXemLo;
    private JTable tableUI;
    private DefaultTableModel model;
    private ThongKeTonKhoPanel thongKeTonKho;

    public TonKhoSanPhamPanel() {

        setLayout(new BorderLayout());
        thongKeTonKho = new ThongKeTonKhoPanel();
        add(thongKeTonKho, BorderLayout.NORTH);

        JPanel topContent = new JPanel();
        topContent.setPreferredSize(new Dimension(100, 45));
        topContent.setLayout(new FlowLayout(FlowLayout.LEFT));
        topContent.setBackground(Color.WHITE);

        btnLoc = new JButton("Lọc");
        btnLoc.setPreferredSize(new Dimension(btnLoc.getPreferredSize().width, 35));
        topContent.add(btnLoc);

        search_Item = new Search_Item(300, 35);
        topContent.add(search_Item);

        btnNhapHang = new JButton("Nhập");
        btnNhapHang.setPreferredSize(new Dimension(80, 35));
        topContent.add(btnNhapHang);

        btnXemLo = new JButton("Xem lô");
        btnXemLo.setPreferredSize(new Dimension(80, 35));
        topContent.add(btnXemLo);

        btnSua = new JButton("Sửa cảnh báo");
        btnSua.setPreferredSize(new Dimension(120, 35));
        topContent.add(btnSua);

        String[] columns = { "Mã Sản phẩm", "Tên sản phẩm", "Loại sản phẩm", "Số lượng", "Mức cảnh báo", "Tổng lô",
                "Lô hết hạn sd" };
        model = new DefaultTableModel(columns, 0);
        Object[][] data = {
                { "SP01", "Sản phẩm 1", "Pha chế", 10, 5, 2, 0 },
                { "SP02", "Sản phẩm 2", "Pha chế", 15, 5, 3, 0 },
                { "SP03", "Sản phẩm 3", "Pha chế", 3, 5, 1, 1 },
                { "SP04", "Sản phẩm 4", "Pha chế", 20, 10, 4, 0 },
                { "SP05", "Sản phẩm 5", "Pha chế", 0, 5, 0, 0 },
                { "SP06", "Sản phẩm 6", "Có sẵn", 50, 20, 5, 0 },
                { "SP07", "Sản phẩm 7", "Pha chế", 8, 10, 2, 1 },
                { "SP08", "Sản phẩm 8", "Có sẵn", 12, 5, 2, 0 },
                { "SP09", "Sản phẩm 9", "Pha chế", 2, 5, 1, 0 },
                { "SP10", "Sản phẩm 10", "Có sẵn", 30, 15, 3, 0 },
                { "SP10", "Sản phẩm 10", "Có sẵn", 30, 15, 3, 0 },
                { "SP10", "Sản phẩm 10", "Có sẵn", 30, 15, 3, 0 },
                { "SP10", "Sản phẩm 10", "Có sẵn", 30, 15, 3, 0 },
                { "SP10", "Sản phẩm 10", "Có sẵn", 30, 15, 3, 0 }
        };

        for (Object[] row : data) {
            model.addRow(row);
        }

        table = new JTable(model);
        table.setRowHeight(35);
        JPanel center = new JPanel(new BorderLayout());
        center.add(topContent, BorderLayout.NORTH);
        JScrollPane scrollPaneTable = TaoUI.taoScrollPane(table);
        center.add(scrollPaneTable, BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        ganSuKien();
    }

    private void ganSuKien() {
        btnXemLo.addActionListener(e -> {
            JDialog xemLoDialog = new TonKhoLoSanPhamDialog(null, "SP001", "Pepsi");
            xemLoDialog.setVisible(true);
        });
    }

}