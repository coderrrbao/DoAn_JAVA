package ui.khuyenmai;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ui.component.Search_Item;

public class KhuyenMaiUI extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private Search_Item search_Item;
    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;

    public KhuyenMaiUI() {
        setLayout(new BorderLayout());

        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.add(taoTopPanel(), BorderLayout.NORTH);
        centerContainer.add(taoPanelTable(), BorderLayout.CENTER);

        add(centerContainer, BorderLayout.CENTER);
    }

    private JPanel taoTopPanel() {
        JPanel top = new JPanel();
        top.setPreferredSize(new Dimension(100, 45));
        top.setLayout(new FlowLayout(FlowLayout.LEFT));
        top.setBackground(Color.WHITE);

        search_Item = new Search_Item(300, 35);
        top.add(search_Item);

        btnThem = new JButton("Thêm");
        btnThem.setPreferredSize(new Dimension(80, 35));
        top.add(btnThem);

        btnSua = new JButton("Sửa");
        btnSua.setPreferredSize(new Dimension(80, 35));
        top.add(btnSua);

        btnXoa = new JButton("Xóa");
        btnXoa.setPreferredSize(new Dimension(80, 35));
        top.add(btnXoa);

        return top;
    }

    private JPanel taoPanelTable() {
        JPanel panel = new JPanel(new BorderLayout());

        // Các cột tương ứng với DB: MaKM, PhanTramGiam, TuNgay, DenNgay, TrangThai
        String[] columns = { "Mã KM", "Phần trăm giảm", "Từ ngày", "Đến ngày", "Trạng thái" };
        model = new DefaultTableModel(columns, 0);

        // Dữ liệu mẫu (Giả lập hiển thị trạng thái BIT: 1=Đang áp dụng, 0=Kết thúc)
        Object[][] data = {
                { "KM001", "10%", "2024-01-01", "2024-01-31", "Đang áp dụng" },
                { "KM002", "20%", "2024-02-14", "2024-02-14", "Chờ kích hoạt" },
                { "KM003", "5%", "2023-12-01", "2023-12-31", "Đã kết thúc" },
                { "KM004", "15%", "2024-03-08", "2024-03-08", "Chờ kích hoạt" },
                { "KM005", "50%", "2024-01-20", "2024-01-25", "Đang áp dụng" }
        };

        for (Object[] row : data) {
            model.addRow(row);
        }

        table = new JTable(model);
        table.setRowHeight(35);

        // Căn chỉnh độ rộng cột (Tùy chọn)
        table.getColumnModel().getColumn(0).setPreferredWidth(100); // Mã KM
        table.getColumnModel().getColumn(1).setPreferredWidth(100); // % Giảm
        table.getColumnModel().getColumn(4).setPreferredWidth(150); // Trạng thái

        JScrollPane scrollPaneTable = new JScrollPane(table);
        panel.add(scrollPaneTable, BorderLayout.CENTER);

        return panel;
    }
}