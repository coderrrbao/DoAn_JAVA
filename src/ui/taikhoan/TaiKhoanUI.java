package ui.taikhoan;
import bus.NhomQuyenBUS;
import bus.TaiKhoanBUS;
import dto.TaiKhoan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
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
    private TaiKhoanBUS taiKhoanBUS = new TaiKhoanBUS();

    public TaiKhoanUI() {
        setLayout(new BorderLayout());

        JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 35);
        top.setBackground(Color.WHITE);
        top = TaoUI.suaBorderChoPanel(top, 0, 10, 0, 10);
        
        String[] quyen = {"Quản lý", "Nhân viên bán hàng"};
        cbNhomQuyen = new JComboBox<>(quyen);
        cbNhomQuyen.setPreferredSize(new Dimension(150, 30));
        cbNhomQuyen.setMaximumSize(new Dimension(150, 30));

        search_Item = new Search_Item(300, 30);
        
        btnTao = new JButton("Thêm");
        btnTao.addActionListener(e -> openThemTaiKhoanDialog());
        TaoUI.setHeightButton(btnTao, 27);
        

        btnResetMatKhau = new JButton("Đặt lại mật khẩu");
        TaoUI.setHeightButton(btnResetMatKhau, 27);
        
        btnXoa = new JButton("Xóa");
        btnXoa.addActionListener(e-> XoaTaiKhoan_Ui());
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

        tableUI = new JTable(model);
        hienThiDanhSachTaiKhoan();

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

    //open them tai khoan dialog
    private void openThemTaiKhoanDialog(){
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        ThemTaiKhoanDialog dia = new ThemTaiKhoanDialog(parentFrame,this);
        dia.setVisible(true);
    }
    // load du lieu hien thi sau khi them xoa 
    public void hienThiDanhSachTaiKhoan() {
        TaiKhoanBUS taiKhoanBUS = new TaiKhoanBUS();
        model.setRowCount(0);

        for (TaiKhoan tk : taiKhoanBUS.layDanhSachTaiKhoan_BUS()) {
            if(tk.getTrangThai() == true){
                model.addRow(new Object[]{
                    tk.getTenTaiKhoan(),
                    tk.getTenDangNhap(),
                    tk.getMaNQ(),
                    tk.getTrangThai()
                });
            }
        }
    }

    //xoa tai khoan
    private void XoaTaiKhoan_Ui(){
        //lay dong dang chon 
        int chonDong = tableUI.getSelectedRow();
        //kiem tra da chon dong hay chua
        if(chonDong == -1){
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xóa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        //lay user de xoa
        String tenDangNhap = model.getValueAt(chonDong, 1).toString();
        String tenHienThi = model.getValueAt(chonDong, 0).toString();
        //xac nhan
        int choice = JOptionPane.showConfirmDialog(
            this, 
            "Bạn có chắc chắn muốn xóa tài khoản:\n" + 
            "Tên: " + tenHienThi + "\n" +
            "Username: " + tenDangNhap + "?", 
            "Xác nhận xóa", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE
        );
        //xu ly xoa 
        if (choice == JOptionPane.YES_OPTION) {
            if (taiKhoanBUS.xoaTaiKhoan_BUS(tenDangNhap)) {
                JOptionPane.showMessageDialog(this, "Đã xóa tài khoản thành công!");
                hienThiDanhSachTaiKhoan();
            } 
            else {
                JOptionPane.showMessageDialog(this, 
                    "Xóa thất bại! Có thể tài khoản không tồn tại hoặc lỗi kết nối.", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    //

    public JButton getBtnTao() { return btnTao; }
    public JButton getBtnXoa() { return btnXoa; }
    public JButton getBtnResetMatKhau() { return btnResetMatKhau; }
    public JComboBox<String> getCbNhomQuyen() { return cbNhomQuyen; }
    public Search_Item getSearch_Item() { return search_Item; }
    public JTable getTableUI() { return tableUI; }
    public DefaultTableModel getModel() { return model; }
}