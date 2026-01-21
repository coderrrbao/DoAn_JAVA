package ui.nhanvien;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ui.component.Search_Item;
import util.TaoUI;

public class NhanVienUI extends JPanel {
    private JButton btnTao, btnSua, btnXoa;
    private JComboBox<String> cbChucVu, cbTrangThai;
    private Search_Item search_Item;
    private JTable tableUI;
    private DefaultTableModel model;

    public NhanVienUI() {
        setLayout(new BorderLayout());

        JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 35);
        top.setBackground(Color.WHITE);
        top = TaoUI.suaBorderChoPanel(top, 0, 10, 0, 10);

        String[] dsChucVu = { "Tất cả", "Quản lý", "Nhân viên" };
        cbChucVu = new JComboBox<>(dsChucVu);
        cbChucVu.setPreferredSize(new Dimension(150, 30));
        cbChucVu.setMaximumSize(new Dimension(150, 30));

        String[] dsTrangThai = { "Tất cả trạng thái", "Đang làm việc", "Đã nghỉ việc" };
        cbTrangThai = new JComboBox<>(dsTrangThai);
        cbTrangThai.setPreferredSize(new Dimension(140, 30));
        cbTrangThai.setMaximumSize(new Dimension(140, 30));

        search_Item = new Search_Item(300, 30);

        btnTao = new JButton("Thêm");
        TaoUI.setHeightButton(btnTao, 27);

        btnSua = new JButton("Sửa");
        TaoUI.setHeightButton(btnSua, 27);

        btnXoa = new JButton("Xóa");
        TaoUI.setHeightButton(btnXoa, 27);

        top.add(cbChucVu);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(cbTrangThai);
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
        model.addColumn("Mã NV");
        model.addColumn("Họ và tên");
        model.addColumn("Chức vụ");
        model.addColumn("Số điện thoại");
        model.addColumn("Ngày vào làm");
        model.addColumn("Trạng thái");

        model.addRow(new Object[] { "NV001", "Nguyễn Văn An", "Quản lý", "0912345678", "2023-01-15", "Đang làm việc" });
        model.addRow(new Object[] { "NV002", "Trần Thị Bình", "Nhân viên bán hàng", "0987654321", "2023-05-20",
                "Đang làm việc" });
        model.addRow(
                new Object[] { "NV003", "Lê Văn Cường", "Nhân viên kho", "0333444555", "2024-02-10", "Đang làm việc" });

        tableUI = new JTable(model);
        tableUI.setRowHeight(35);
        tableUI.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tableUI.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        tableUI.getColumnModel().getColumn(0).setPreferredWidth(80);
        tableUI.getColumnModel().getColumn(1).setPreferredWidth(180);
        tableUI.getColumnModel().getColumn(2).setPreferredWidth(130);
        tableUI.getColumnModel().getColumn(3).setPreferredWidth(120);
        tableUI.getColumnModel().getColumn(4).setPreferredWidth(120);
        tableUI.getColumnModel().getColumn(5).setPreferredWidth(130);

        JScrollPane scrollPane = TaoUI.taoScrollPane(tableUI);

        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(new Color(238, 238, 238));
        tableContainer.add(scrollPane, BorderLayout.CENTER);

        add(tableContainer, BorderLayout.CENTER);
    }

    public JButton getBtnTao() {
        return btnTao;
    }

    public JButton getBtnSua() {
        return btnSua;
    }

    public JButton getBtnXoa() {
        return btnXoa;
    }

    public JComboBox<String> getCbChucVu() {
        return cbChucVu;
    }

    public JComboBox<String> getCbTrangThai() {
        return cbTrangThai;
    }

    public Search_Item getSearch_Item() {
        return search_Item;
    }

    public JTable getTableUI() {
        return tableUI;
    }

    public DefaultTableModel getModel() {
        return model;
    }
}