package ui.khachhang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ui.component.Search_Item;
import util.TaoUI;

public class KhachHangUI extends JPanel {
    private JButton btnTao, btnSua, btnXoa;
    private JComboBox<String> cbHangThanhVien;
    private Search_Item search_Item;
    private JTable tableUI;
    private DefaultTableModel model;

    public KhachHangUI() {
        setLayout(new BorderLayout());

        JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 35);
        top.setBackground(Color.WHITE);
        top = TaoUI.suaBorderChoPanel(top, 0, 10, 0, 10);

        String[] hang = { "Tất cả hạng", "Đồng", "Bạc", "Vàng" };
        cbHangThanhVien = new JComboBox<>(hang);
        cbHangThanhVien.setPreferredSize(new Dimension(120, 30));
        cbHangThanhVien.setMaximumSize(new Dimension(120, 30));

        search_Item = new Search_Item(300, 30);
        
        btnTao = new JButton("Thêm");
        TaoUI.setHeightButton(btnTao, 27);
        
        btnSua = new JButton("Sửa");
        TaoUI.setHeightButton(btnSua, 27);
        
        btnXoa = new JButton("Xóa");
        TaoUI.setHeightButton(btnXoa, 27);

        top.add(cbHangThanhVien);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(search_Item);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnTao);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnSua);
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
        model.addColumn("Mã khách hàng");
        model.addColumn("Tên khách hàng");
        model.addColumn("Giới tính");
        model.addColumn("Số điện thoại");
        model.addColumn("Tổng chi tiêu");
        model.addColumn("Hạng thành viên");

        model.addRow(new Object[] { "KH001", "Nguyễn Văn A", "Nam", "0123456789", "5.000.000", "Vàng" });
        model.addRow(new Object[] { "KH002", "Trần Thị B", "Nữ", "0987654321", "2.500.000", "Bạc" });
        model.addRow(new Object[] { "KH003", "Lê Văn C", "Nam", "0912345678", "500.000", "Đồng" });
        model.addRow(new Object[] { "KH004", "Phạm Thị D", "Nữ", "0898765432", "10.000.000", "Vàng" });

        tableUI = new JTable(model);
        tableUI.setRowHeight(35);
        tableUI.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tableUI.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        tableUI.getColumnModel().getColumn(0).setPreferredWidth(100);
        tableUI.getColumnModel().getColumn(1).setPreferredWidth(180);
        tableUI.getColumnModel().getColumn(2).setPreferredWidth(80);
        tableUI.getColumnModel().getColumn(3).setPreferredWidth(120);
        tableUI.getColumnModel().getColumn(4).setPreferredWidth(120);
        tableUI.getColumnModel().getColumn(5).setPreferredWidth(120);

        JScrollPane scrollPane = TaoUI.taoScrollPane(tableUI);
        
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(new Color(238, 238, 238));
        tableContainer = TaoUI.suaBorderChoPanel(tableContainer, 10, 10, 10, 10);
        tableContainer.add(scrollPane, BorderLayout.CENTER);

        add(tableContainer, BorderLayout.CENTER);
    }

    public JButton getBtnTao() { return btnTao; }
    public JButton getBtnSua() { return btnSua; }
    public JButton getBtnXoa() { return btnXoa; }
    public JComboBox<String> getCbHangThanhVien() { return cbHangThanhVien; }
    public Search_Item getSearch_Item() { return search_Item; }
    public JTable getTableUI() { return tableUI; }
    public DefaultTableModel getModel() { return model; }
}