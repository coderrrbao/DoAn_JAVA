package ui.tonkho;

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
import util.TaoUI;

public class TonKhoNguyenLieuPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JButton btnLoc;
    private Search_Item search_Item;
    private JButton btnNhapHang;
    private JButton btnSua;
    private ThongKeTonKhoPanel thongKeTonKho;

    public TonKhoNguyenLieuPanel() {
        setLayout(new BorderLayout());

        thongKeTonKho = new ThongKeTonKhoPanel();
        add(thongKeTonKho, BorderLayout.NORTH);

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

        btnLoc = new JButton("Lọc");
        btnLoc.setPreferredSize(new Dimension(btnLoc.getPreferredSize().width, 35));
        top.add(btnLoc);

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

        String[] columns = { "Mã Nguyên liệu", "Tên nguyên liệu", "Đơn vị tính", "Số lượng tồn", "Mức cảnh báo",
                "Trạng thái" };
        model = new DefaultTableModel(columns, 0);

        Object[][] data = {
                { "NL01", "Hạt Cà phê Arabica", "Kg", 20, 5, "Còn hàng" },
                { "NL02", "Sữa tươi không đường", "Lít", 45, 10, "Còn hàng" },
                { "NL03", "Đường cát", "Kg", 4, 10, "Sắp hết hàng" },
                { "NL04", "Bột Cacao", "Kg", 15, 5, "Còn hàng" },
                { "NL05", "Trà đen", "Kg", 2, 5, "Sắp hết hàng" },
                { "NL06", "Sữa đặc", "Hộp", 60, 20, "Còn hàng" },
                { "NL07", "Kem béo", "Lít", 3, 5, "Sắp hết hàng" },
                { "NL08", "Siro Dâu", "Chai", 12, 5, "Còn hàng" },
                { "NL09", "Trân châu đen", "Kg", 0, 5, "Hết hàng" },
                { "NL10", "Giấy lọc cà phê", "Xấp", 30, 10, "Còn hàng" }
        };

        for (Object[] row : data) {
            model.addRow(row);
        }

        table = new JTable(model);
        table.setRowHeight(35);

        JScrollPane scrollPaneTable = new JScrollPane(table);
        panel.add(scrollPaneTable, BorderLayout.CENTER);

        return panel;
    }
}