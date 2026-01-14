package ui.xuatkho;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ui.component.Search_Item;
import util.TaoUI;

public class XuatKhoNguyenLieuPanel extends JPanel {
    private JButton btnXuatKho, btnXemChiTiet, btnLoc;
    private Search_Item search_Item;
    private JTable tableUI;
    private DefaultTableModel model;

    public XuatKhoNguyenLieuPanel() {
        setLayout(new BorderLayout());

        JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 35);
        top.setBackground(Color.WHITE);

        search_Item = new Search_Item(300, 30);
        
        btnXuatKho = new JButton("Xuất kho");
        TaoUI.setHeightButton(btnXuatKho, 27);
        
        btnXemChiTiet = new JButton("Xem chi tiết");
        TaoUI.setHeightButton(btnXemChiTiet, 27);

        btnLoc = new JButton("Lọc");
        TaoUI.setHeightButton(btnLoc, 27);

        top.add(search_Item);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnXuatKho);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnXemChiTiet);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnLoc);
        top.add(Box.createHorizontalGlue());

        add(top, BorderLayout.NORTH);

        // 2. Bảng dữ liệu nguyên liệu
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("Mã xuất");
        model.addColumn("Mã lô");
        model.addColumn("Tên nguyên liệu");
        model.addColumn("Số lượng (kg/l)");
        model.addColumn("Tổng tiền");

        model.addRow(new Object[] { "XKN01", "LNL01", "Hạt cà phê", "10", "2.000.000" });
        model.addRow(new Object[] { "XKN02", "LNL02", "Đường tinh luyện", "20", "400.000" });

        tableUI = new JTable(model);
        tableUI.setRowHeight(35);
        tableUI.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tableUI.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        JScrollPane scrollPane = TaoUI.taoScrollPane(tableUI);
        
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(new Color(238, 238, 238));
        tableContainer = TaoUI.suaBorderChoPanel(tableContainer, 10, 10, 10, 10);
        tableContainer.add(scrollPane, BorderLayout.CENTER);

        add(tableContainer, BorderLayout.CENTER);
    }
}