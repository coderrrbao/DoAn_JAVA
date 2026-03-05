package ui.taikhoan;

import bus.TaiKhoanBUS;
import dto.TaiKhoan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ui.component.Search_Item;
import ui.login.PhienDangNhap;
import util.TaoUI;

public class TaiKhoanUI extends JPanel {
    private JButton btnTao, btnXoa, btnResetMatKhau, btnXuatExcel, bntNhapExcel, btnSuaThongTin;
    private JComboBox<String> cbNhomQuyen;
    private Search_Item search_Item;
    private JTable tableUI;
    private DefaultTableModel model;
    private TaiKhoanBUS taiKhoanBUS = TaiKhoanBUS.getTaiKhoanBUS();

    public TaiKhoanUI() {
        setLayout(new BorderLayout());

        JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 35);
        top.setBackground(Color.WHITE);

        String[] quyen = { "Quản lý", "Nhân viên bán hàng" };
        cbNhomQuyen = new JComboBox<>(quyen);
        cbNhomQuyen.setPreferredSize(new Dimension(150, 30));
        cbNhomQuyen.setMaximumSize(new Dimension(150, 30));

        search_Item = new Search_Item(300, 30);

        btnTao = new JButton("Thêm");
        btnTao.addActionListener(e -> openThemTaiKhoanDialog());

        btnResetMatKhau = new JButton("Đặt lại mật khẩu");
        btnResetMatKhau.addActionListener(e -> openDoiMatKhauDialog());

        btnXoa = new JButton("Xóa");
        btnXoa.addActionListener(e -> XoaTaiKhoan_Ui());

        btnXuatExcel = new JButton("Xuất exc");
        btnXuatExcel.addActionListener(e -> taiKhoanBUS.xuatExc());

        bntNhapExcel = new JButton("Nhập exc");
    bntNhapExcel.addActionListener(e -> {taiKhoanBUS.nhapTuExcel();hienThiDanhSachTaiKhoan();});

        btnSuaThongTin = new JButton("Sửa thông tin");
        btnSuaThongTin.addActionListener(e -> openSuaTaiKhoanDialog());

        top.add(cbNhomQuyen);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(search_Item);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnTao);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnResetMatKhau);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnXoa);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnSuaThongTin);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnXuatExcel);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(bntNhapExcel);
        top.add(Box.createHorizontalGlue());

        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("Mã nhân viên");
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

        JScrollPane scrollPane = TaoUI.taoTableScroll(model);
        tableUI = (JTable) scrollPane.getViewport().getView();

        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(new Color(238, 238, 238));
        tableContainer.add(scrollPane, BorderLayout.CENTER);

        add(tableContainer, BorderLayout.CENTER);
    }

    public void suaLaiGiaoDienTheoQuyen() {
        var listQuyen = PhienDangNhap.getListQuyen();

        // 1. Quyền Thêm tài khoản
        if (!listQuyen.contains("TK_TAO")) {
            btnTao.setVisible(false);
        }

        // 2. Quyền Xóa tài khoản
        if (!listQuyen.contains("TK_XOA")) {
            btnXoa.setVisible(false);
        }

        // 3. Quyền Đặt lại mật khẩu (Sửa tài khoản)
        if (!listQuyen.contains("TK_SUA")) {
            btnResetMatKhau.setVisible(false);
        }
        this.revalidate();
        this.repaint();
    }

    // open them tai khoan dialog
    private void openThemTaiKhoanDialog() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        ThemTaiKhoanDialog dia = new ThemTaiKhoanDialog(parentFrame, this, null);
        dia.setVisible(true);
    }

    // open sua tai khoan dialog
    private void openSuaTaiKhoanDialog() {
        int chonDong = tableUI.getSelectedRow();
        if (chonDong == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn tài khoản để sửa!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy MaNV từ bảng
        String maNV = model.getValueAt(chonDong, 0).toString();

        // Tìm tài khoản tương ứng trong BUS
        String tenDangNhap = model.getValueAt(chonDong, 1).toString();

        TaiKhoan taiKhoanChon = null;
        for (TaiKhoan tk : taiKhoanBUS.layDanhSachTaiKhoan()) {
            if (tk.getTenDangNhap().equals(tenDangNhap)) {
                taiKhoanChon = tk;
                break;
            }
        }

        if (taiKhoanChon == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy tài khoản!");
            return;
        }

        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

        // GỌI CONSTRUCTOR SỬA
        ThemTaiKhoanDialog dialog = new ThemTaiKhoanDialog(parentFrame, this, taiKhoanChon);

        dialog.setVisible(true);
    }

    // open doi mat khau dialog
    private void openDoiMatKhauDialog() {
        int chonDong = tableUI.getSelectedRow();
        // kiem tra da chon dong hay chua
        if (chonDong == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản để đổi", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        DoiMatKhauDialog dia = new DoiMatKhauDialog(parentFrame, this);
        dia.setVisible(true);
    }

    // load du lieu hien thi sau khi them xoa
    public void hienThiDanhSachTaiKhoan() {
        TaiKhoanBUS taiKhoanBUS = TaiKhoanBUS.getTaiKhoanBUS();
        model.setRowCount(0);

        for (TaiKhoan tk : taiKhoanBUS.layDanhSachTaiKhoan()) {
            model.addRow(new Object[] {
                    tk.getMaNV(),
                    tk.getTenDangNhap(),
                    tk.getNhomQuyen().getTenNhomQuyen(),
                    tk.getTrangThaiXuLy()
            });
        }
    }

    // xoa tai khoan
    private void XoaTaiKhoan_Ui() {
        // lay dong dang chon
        int chonDong = tableUI.getSelectedRow();
        // kiem tra da chon dong hay chua
        if (chonDong == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xóa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // lay user de xoa
        String tenDangNhap = model.getValueAt(chonDong, 1).toString();
        String tenHienThi = model.getValueAt(chonDong, 0).toString();
        // xac nhan
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa tài khoản:\n" +
                        "Tên: " + tenHienThi + "\n" +
                        "Username: " + tenDangNhap + "?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        // xu ly xoa
        if (choice == JOptionPane.YES_OPTION) {
            if (taiKhoanBUS.xoaTaiKhoan(tenDangNhap)) {
                JOptionPane.showMessageDialog(this, "Đã xóa tài khoản thành công!");
                hienThiDanhSachTaiKhoan();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Xóa thất bại! Có thể tài khoản không tồn tại hoặc lỗi kết nối.",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    //

    public JButton getBtnTao() {
        return btnTao;
    }

    public JButton getBtnXoa() {
        return btnXoa;
    }

    public JButton getBtnResetMatKhau() {
        return btnResetMatKhau;
    }

    public JComboBox<String> getCbNhomQuyen() {
        return cbNhomQuyen;
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