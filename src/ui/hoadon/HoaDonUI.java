package ui.hoadon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ui.component.LocNgay_Item;
import ui.component.Search_Item;
import util.TaoUI;

public class HoaDonUI extends JPanel {
    private JButton btnXemChiTiet, btnXoa;
    private Search_Item search_Item;
    private JTable tableUI;
    private DefaultTableModel model;
    private LocNgay_Item locNgay;

    public HoaDonUI() {
        setLayout(new BorderLayout());

        JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 35);
        top.setBackground(Color.WHITE);

        locNgay = new LocNgay_Item(350, 27);
        search_Item = new Search_Item(300, 30);
        
        btnXemChiTiet = new JButton("Chi tiết");
        TaoUI.setHeightButton(btnXemChiTiet, 27);
        
        btnXoa = new JButton("Xóa");
        TaoUI.setHeightButton(btnXoa, 27);

        top.add(locNgay);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(search_Item);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnXemChiTiet);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnXoa);
        top.add(Box.createHorizontalGlue());

        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("Mã HD");
        model.addColumn("Ngày tạo");
        model.addColumn("Mã nhân viên");
        model.addColumn("Mã khách hàng");
        model.addColumn("Tổng tiền");

        model.addRow(new Object[] { "HD001", "2026-01-06", "NV01", "KH01", "100.000" });
        model.addRow(new Object[] { "HD002", "2026-01-06", "NV02", "KH02", "200.000" });
        model.addRow(new Object[] { "HD003", "2026-01-13", "NV01", "KH03", "150.000" });

        tableUI = new JTable(model);
        tableUI.setRowHeight(35);
        tableUI.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tableUI.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        tableUI.getColumnModel().getColumn(0).setPreferredWidth(80);
        tableUI.getColumnModel().getColumn(1).setPreferredWidth(150);
        tableUI.getColumnModel().getColumn(2).setPreferredWidth(100);
        tableUI.getColumnModel().getColumn(3).setPreferredWidth(100);
        tableUI.getColumnModel().getColumn(4).setPreferredWidth(120);

        JScrollPane scrollPane = TaoUI.taoScrollPane(tableUI);
        
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(new Color(238, 238, 238));
        tableContainer.add(scrollPane, BorderLayout.CENTER);

        add(tableContainer, BorderLayout.CENTER);
    }

    public JButton getBtnXemChiTiet() { return btnXemChiTiet; }
    public JButton getBtnXoa() { return btnXoa; }
    public Search_Item getSearch_Item() { return search_Item; }
    public JTable getTableUI() { return tableUI; }
    public DefaultTableModel getModel() { return model; }
}