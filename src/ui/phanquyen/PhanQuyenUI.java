package ui.phanquyen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dto.NhomQuyen;
import ui.component.Search_Item;

public class PhanQuyenUI extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private Search_Item search_Item;
    private JButton btnThem;
    private JButton btnXoa;
    private JButton btnXemChiTiet;

    public PhanQuyenUI() {
        setLayout(new BorderLayout());

        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.add(taoTopPanel(), BorderLayout.NORTH);
        centerContainer.add(taoPanelTable(), BorderLayout.CENTER);

        add(centerContainer, BorderLayout.CENTER);
        ganSuKien();
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

        btnXoa = new JButton("Xóa");
        btnXoa.setPreferredSize(new Dimension(80, 35));
        top.add(btnXoa);

        btnXemChiTiet = new JButton("Xem chi tiết quyền");
        btnXemChiTiet.setPreferredSize(new Dimension(150, 35));
        top.add(btnXemChiTiet);

        return top;
    }

    private JPanel taoPanelTable() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columns = { "STT", "Mã nhóm quyền", "Tên nhóm quyền" };
        model = new DefaultTableModel(columns, 0);

        Object[][] data = {
                { 1, "NQ001", "Quản trị viên (Admin)" },
                { 2, "NQ002", "Quản lý cửa hàng" },
                { 3, "NQ003", "Nhân viên bán hàng" },
                { 4, "NQ004", "Nhân viên kho" },
                { 5, "NQ005", "Kế toán" }
        };

        for (Object[] row : data) {
            model.addRow(row);
        }

        table = new JTable(model);
        table.setRowHeight(35);

        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(400);

        JScrollPane scrollPaneTable = new JScrollPane(table);
        panel.add(scrollPaneTable, BorderLayout.CENTER);

        return panel;
    }

    private void ganSuKien(){
        btnXemChiTiet.addActionListener(e->{
            new PhanQuyenDialog(new NhomQuyen("123","Admin"));

        });
        btnThem.addActionListener(e->{

        });
    }
}