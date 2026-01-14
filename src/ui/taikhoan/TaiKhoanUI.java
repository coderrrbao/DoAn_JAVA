package ui.taikhoan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ui.component.Search_Item;
import util.TaoUI;

public class TaiKhoanUI extends JPanel {
    private JButton btnTao, btnXoa, btnResetMatKhau;
    private JComboBox<String> cbNhomQuyen;
    private Search_Item search_Item;
    private JTable tableUI;
    private DefaultTableModel model;

    public TaiKhoanUI() {
        setLayout(new BorderLayout());

        JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 35);
        top.setBackground(Color.WHITE);
        top = TaoUI.suaBorderChoPanel(top, 0, 10, 0, 10);
        
        String[] quyen = { "Tất cả nhóm quyền", "Quản lý", "Nhân viên bán hàng", "Nhân viên kho" };
        cbNhomQuyen = new JComboBox<>(quyen);
        cbNhomQuyen.setPreferredSize(new Dimension(150, 30));
        cbNhomQuyen.setMaximumSize(new Dimension(150, 30));

        search_Item = new Search_Item(300, 30);
        
        btnTao = new JButton("Thêm");
        TaoUI.setHeightButton(btnTao, 27);
        

        btnResetMatKhau = new JButton("Đặt lại mật khẩu");
        TaoUI.setHeightButton(btnResetMatKhau, 27);
        
        btnXoa = new JButton("Xóa");
        TaoUI.setHeightButton(btnXoa, 27);

        top.add(cbNhomQuyen);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(search_Item);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnTao);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnResetMatKhau);
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
        model.addColumn("Tên tài khoản");
        model.addColumn("Tên đăng nhập");
        model.addColumn("Nhóm quyền");
        model.addColumn("Trạng thái");

        model.addRow(new Object[] { "Nguyễn Văn A", "admin", "Quản lý", "Đang hoạt động" });
        model.addRow(new Object[] { "Trần Thị B", "nv_banhang1", "Nhân viên bán hàng", "Đang hoạt động" });
        model.addRow(new Object[] { "Lê Văn C", "nv_kho1", "Nhân viên kho", "Đang khóa" });

        tableUI = new JTable(model);
        tableUI.setRowHeight(35);
        tableUI.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tableUI.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        tableUI.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableUI.getColumnModel().getColumn(1).setPreferredWidth(120);
        tableUI.getColumnModel().getColumn(2).setPreferredWidth(150);
        tableUI.getColumnModel().getColumn(3).setPreferredWidth(120);

        JScrollPane scrollPane = TaoUI.taoScrollPane(tableUI);
        
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(new Color(238, 238, 238));
        tableContainer = TaoUI.suaBorderChoPanel(tableContainer, 10, 10, 10, 10);
        tableContainer.add(scrollPane, BorderLayout.CENTER);

        add(tableContainer, BorderLayout.CENTER);
    }

    public JButton getBtnTao() { return btnTao; }
    public JButton getBtnXoa() { return btnXoa; }
    public JButton getBtnResetMatKhau() { return btnResetMatKhau; }
    public JComboBox<String> getCbNhomQuyen() { return cbNhomQuyen; }
    public Search_Item getSearch_Item() { return search_Item; }
    public JTable getTableUI() { return tableUI; }
    public DefaultTableModel getModel() { return model; }
}