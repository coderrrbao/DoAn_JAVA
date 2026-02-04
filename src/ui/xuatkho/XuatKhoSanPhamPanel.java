package ui.xuatkho;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ui.component.Search_Item;
import util.TaoUI;

public class XuatKhoSanPhamPanel extends JPanel {
    private JButton btnXuatKho, btnXemChiTiet, btnLoc;
    private Search_Item search_Item;
    private JTable tableUI;
    private DefaultTableModel model;

    public XuatKhoSanPhamPanel() {
        setLayout(new BorderLayout());

        JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 35);
        top.setBackground(Color.WHITE);

        search_Item = new Search_Item(300, 30);

        btnXuatKho = new JButton("Xuất kho");

        btnXemChiTiet = new JButton("Xem chi tiết");


        btnLoc = new JButton("Lọc");

        top.add(search_Item);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnXuatKho);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnXemChiTiet);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnLoc);
        top.add(Box.createHorizontalGlue());

        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("Mã xuất");
        model.addColumn("Mã lô");
        model.addColumn("Tên sản phẩm");
        model.addColumn("Số lượng");
        model.addColumn("Tổng tiền");

        model.addRow(new Object[] { "XK001", "LOT123", "Pepsi 330ml", "50", "500.000" });
        model.addRow(new Object[] { "XK002", "LOT456", "Coca Cola", "100", "1.000.000" });

        JScrollPane scrollPane = TaoUI.taoTableScroll(model);
        tableUI  = (JTable) scrollPane.getViewport().getView();

        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(new Color(238, 238, 238));
        add(scrollPane, BorderLayout.CENTER);
    }
}