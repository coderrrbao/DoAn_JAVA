package ui.kiemke;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ui.component.LocNgay_Item;
import ui.component.Search_Item;

public class KiemKeUI extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private LocNgay_Item locNgay;
    private Search_Item search_Item;
    private JButton btnNhapHang;
    private JButton btnSua;

    public KiemKeUI() {
        setLayout(new BorderLayout());

        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.add(taoTopPanel(), BorderLayout.NORTH);
        centerContainer.add(taoPanelTable(), BorderLayout.CENTER);

        add(centerContainer, BorderLayout.CENTER);
    }

    private JPanel taoTopPanel() {
        JPanel top = new JPanel();
        top.setPreferredSize(new Dimension(100, 35));
        top.setLayout(new FlowLayout(FlowLayout.LEFT));
        top.setBackground(Color.WHITE);

        locNgay = new LocNgay_Item(350, 35);
        top.add(locNgay);

        search_Item = new Search_Item(300, 35);
        top.add(search_Item);

        btnNhapHang = new JButton("Nhập");
        btnNhapHang.setPreferredSize(new Dimension(80, 35));
        top.add(btnNhapHang);

        btnSua = new JButton("Sửa");
        btnSua.setPreferredSize(new Dimension(btnSua.getPreferredSize().width, 35));
        top.add(btnSua);

        return top;
    }

    private JPanel taoPanelTable() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = { "STT", "Mã Phiếu Kiểm", "Ngày kiểm", "Mã lô", "Loại lô", "SL sổ sách", "SL thực tế", "Chênh lệch",
                "Ghi chú" };
        model = new DefaultTableModel(columns, 0);

        Object[][] data = {
                { 1, "PK20240115", "15/01/2024", "L001", "Nguyên liệu", 100, 98, -2, "Hao hụt tự nhiên" },
                { 2, "PK20240115", "15/01/2024", "SP055", "Sản phẩm", 50, 50, 0, "" },
                { 3, "PK20240116", "16/01/2024", "L003", "Nguyên liệu", 200, 205, 5, "Nhập dư chưa ghi sổ" },
                { 4, "PK20240116", "16/01/2024", "SP012", "Sản phẩm", 30, 28, -2, "Vỡ hỏng" },
                { 5, "PK20240117", "17/01/2024", "L004", "Nguyên liệu", 150, 150, 0, "" },
                { 6, "PK20240117", "17/01/2024", "SP088", "Sản phẩm", 80, 75, -5, "Thất thoát" },
                { 7, "PK20240118", "18/01/2024", "L005", "Nguyên liệu", 60, 60, 0, "" },
                { 8, "PK20240118", "18/01/2024", "SP099", "Sản phẩm", 120, 122, 2, "Tìm thấy hàng cũ" },
                { 9, "PK20240119", "19/01/2024", "L006", "Nguyên liệu", 45, 40, -5, "Ẩm mốc hủy bỏ" },
                { 10, "PK20240119", "19/01/2024", "SP101", "Sản phẩm", 10, 10, 0, "" }
        };

        for (Object[] row : data) {
            model.addRow(row);
        }

        table = new JTable(model);
        table.setRowHeight(35);

        table.getColumnModel().getColumn(0).setPreferredWidth(40); // STT
        table.getColumnModel().getColumn(1).setPreferredWidth(100); // Mã Phiếu
        table.getColumnModel().getColumn(2).setPreferredWidth(100); // Ngày kiểm

        JScrollPane scrollPaneTable = new JScrollPane(table);
        panel.add(scrollPaneTable, BorderLayout.CENTER);

        return panel;
    }
}