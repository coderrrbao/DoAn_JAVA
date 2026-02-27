package ui.khachhang;

import javax.swing.*;
import java.awt.*;

import bus.KhachHangBUS;
import dto.KhachHang;
import util.TaoUI;

public class ThemKhachHangDialog extends JDialog {
    private JTextField txtName;
    private JTextField txtPhone;
    private JComboBox<String> cbGioiTinh;
    private JLabel lblHangThanhVien;

    private JButton btnLuu;
    private JButton btnHuy;

    private boolean isEditMode = false;
    private KhachHang khachHang;
    private KhachHangUI khUI;
    private KhachHangBUS bus = new KhachHangBUS();

    public ThemKhachHangDialog(JFrame owner, KhachHangUI khUI) {
        super(owner, "Thêm khách hàng", true);
        this.khUI = khUI;
        this.isEditMode = false;
        this.khachHang = null;
        initUI();
        setSize(400, 250);
        setLocationRelativeTo(owner);
        setResizable(false);
    }

    public ThemKhachHangDialog(JFrame owner, KhachHangUI khUI, KhachHang khachHang) {
        super(owner, "Sửa khách hàng", true);
        this.khUI = khUI;
        this.isEditMode = true;
        this.khachHang = khachHang;
        initUI();
        dienThongTinKhachHang();
        setSize(400, 250);
        setLocationRelativeTo(owner);
        setResizable(false);
    }

    private void initUI() {
        JPanel mainPanel = TaoUI.taoPanelBoxLayoutDoc(400, 180);
        TaoUI.suaBorderChoPanel(mainPanel, 15, 15, 15, 15);

        txtName = new JTextField();
        JPanel nameField = TaoUI.taoFieldText("Tên khách hàng", 110, 210, 30, 10, txtName);

        txtPhone = new JTextField();
        JPanel phoneField = TaoUI.taoFieldText("Số điện thoại", 110, 210, 30, 10, txtPhone);

        JPanel cbGTPanel = new JPanel();
        cbGTPanel.setLayout(new BoxLayout(cbGTPanel, BoxLayout.X_AXIS));
        TaoUI.setFixSize(cbGTPanel, 330, 30);

        String[] dsGioiTinh = { "Nam", "Nữ" };
        cbGioiTinh = new JComboBox<>(dsGioiTinh);

        JLabel cbGTLabel = new JLabel("Giới tính");
        cbGTLabel.setPreferredSize(new Dimension(110, 30));
        cbGTLabel.setMinimumSize(new Dimension(110, 30));
        cbGTLabel.setMaximumSize(new Dimension(110, 30));

        cbGTPanel.add(cbGTLabel);
        cbGTPanel.add(cbGioiTinh);

        JPanel hangPanel = new JPanel();
        hangPanel.setLayout(new BoxLayout(hangPanel, BoxLayout.X_AXIS));
        TaoUI.setFixSize(hangPanel, 330, 30);
        JLabel hangLabel = new JLabel("Hạng thành viên");
        hangLabel.setPreferredSize(new Dimension(110, 30));
        hangLabel.setMinimumSize(new Dimension(110, 30));
        hangLabel.setMaximumSize(new Dimension(110, 30));
        lblHangThanhVien = new JLabel(isEditMode ? "" : "Thành Viên Mới");
        hangPanel.add(hangLabel);
        hangPanel.add(lblHangThanhVien);

        JPanel buttonPanel = TaoUI.taoPanelCanGiua(330, 30);
        btnLuu = new JButton(isEditMode ? "Lưu" : "Thêm");
        btnHuy = new JButton("Hủy");

        TaoUI.addItem(buttonPanel, btnLuu, 5, true);
        TaoUI.addItem(buttonPanel, btnHuy, 5, true);

        mainPanel.add(nameField);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(phoneField);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(cbGTPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(hangPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(buttonPanel);

        add(mainPanel, BorderLayout.CENTER);

        btnHuy.addActionListener(e -> dispose());
        btnLuu.addActionListener(e -> xuLyLuuKhachHang());
    }

    private void dienThongTinKhachHang() {
        if (khachHang == null) {
            return;
        }
        txtName.setText(khachHang.getTenKH());
        txtPhone.setText(khachHang.getSdt());
        cbGioiTinh.setSelectedItem(khachHang.getGioiTinh());
        lblHangThanhVien.setText(tenHangTuMa(khachHang.getMaHang()));
    }

    private String tenHangTuMa(String maHang) {
        if (maHang == null) return "Thành Viên Mới";
        return switch (maHang) {
            case "HTV01" -> "Thành Viên Mới";
            case "HTV02" -> "Thành Viên Bạc";
            case "HTV03" -> "Thành Viên Vàng";
            case "HTV04" -> "Thành Viên Bạch Kim";
            case "HTV05" -> "Thành Viên Kim Cương";
            default -> "Thành Viên Mới";
        };
    }

    private void xuLyLuuKhachHang() {
        String name = txtName.getText().trim();
        String phone = txtPhone.getText().trim();
        String gt = (String) cbGioiTinh.getSelectedItem();

        if (!isEditMode) {
            KhachHang kh = new KhachHang();
            kh.setTenKH(name);
            kh.setSdt(phone);
            kh.setGioiTinh(gt);
            kh.setTenDaMua(0);
            kh.setMaHang("HTV01");

            String result = bus.themKhachHang(kh);
            if (result != null) {
                JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                if (khUI != null) {
                    khUI.hienThiDanhSachKhachHang();
                }
                dispose();
            }
        } else {
            if (khachHang == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng để sửa", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            khachHang.setTenKH(name);
            khachHang.setSdt(phone);
            khachHang.setGioiTinh(gt);

            String result = bus.capNhatKhachHang(khachHang);
            if (result != null) {
                JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thành công", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                if (khUI != null) {
                    khUI.hienThiDanhSachKhachHang();
                }
                dispose();
            }
        }
    }
}

