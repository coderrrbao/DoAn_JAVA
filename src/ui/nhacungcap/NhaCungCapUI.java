package ui.nhacungcap;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ui.component.Search_Item;
import util.TaoUI;

public class NhaCungCapUI extends JPanel {
    private JButton btnTao, btnXoa, btnSua;
    private Search_Item search_Item;
    private JTable tableUI;
    private DefaultTableModel model;

    public NhaCungCapUI() {
        setLayout(new BorderLayout());

        JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 35);
        top.setBackground(Color.WHITE);
        top = TaoUI.suaBorderChoPanel(top, 0, 10, 0, 10);

        search_Item = new Search_Item(300, 30);

        btnTao = new JButton("Thêm");
        TaoUI.setHeightButton(btnTao, 27);

        btnSua = new JButton("Sửa");
        TaoUI.setHeightButton(btnSua, 27);

        btnXoa = new JButton("Xóa");
        TaoUI.setHeightButton(btnXoa, 27);

        top.add(search_Item);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnTao);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnSua);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnXoa);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(Box.createHorizontalGlue());

        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("Mã NCC");
        model.addColumn("Tên nhà cung cấp");
        model.addColumn("Loại cung cấp");
        model.addColumn("Số điện thoại");
        model.addColumn("Địa chỉ");

        model.addRow(new Object[] { "NCC001", "Pepsi Việt Nam", "Sản phẩm", "0123456789", "TP. Hồ Chí Minh" });
        model.addRow(new Object[] { "NCC002", "Coca-Cola", "Sản phẩm", "0987654321", "Bình Dương" });
        model.addRow(new Object[] { "NCC003", "Lavie", "Sản phẩm", "0333444555", "Long An" });
        model.addRow(new Object[] { "NCC004", "Nông trại Việt", "Nguyên liệu", "0944555666", "Đà Lạt" });

        JScrollPane scrollPane = TaoUI.taoTableScroll(model);
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(new Color(238, 238, 238));
        tableContainer.add(scrollPane, BorderLayout.CENTER);

        add(tableContainer, BorderLayout.CENTER);
    }

    public JButton getBtnTao() {
        return btnTao;
    }

    public JButton getBtnXoa() {
        return btnXoa;
    }

    public JButton getBtnSua() {
        return btnSua;
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