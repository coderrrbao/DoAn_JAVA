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

    // Tách riêng 4 nút rõ ràng
    private JButton btnLuu;
    private JButton btnHuy; // Đóng
    private JButton btnSua;
    private JButton btnThem;

    private KhachHang khachHang;
    private KhachHangUI khUI;
    private KhachHangBUS bus = new KhachHangBUS();

    public ThemKhachHangDialog(JFrame owner, KhachHangUI khUI) {
        super(owner, "Thêm khách hàng", true);
        this.khUI = khUI;
        this.khachHang = null;
        initUI();
        setSize(400, 280);
        setLocationRelativeTo(owner);
        setResizable(false);
    }

    public ThemKhachHangDialog(JFrame owner, KhachHangUI khUI, KhachHang khachHang) {
        super(owner, "Chi tiết khách hàng", true);
        this.khUI = khUI;
        this.khachHang = khachHang;
        initUI();
        dienThongTinKhachHang();
        setSize(400, 280);
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
        lblHangThanhVien = new JLabel(khachHang != null ? "" : "Thành Viên Mới");
        hangPanel.add(hangLabel);
        hangPanel.add(lblHangThanhVien);

        JPanel buttonPanel = TaoUI.taoPanelCanGiua(330, 30);
        
        // Khởi tạo 4 nút
        btnSua = new JButton("Sửa");
        btnThem = new JButton("Thêm");
        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Đóng");

        TaoUI.addItem(buttonPanel, btnSua, 5, true);
        TaoUI.addItem(buttonPanel, btnThem, 5, true);
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

        // Khởi tạo trạng thái ẩn/hiện ban đầu
        initLoaiDialog();

        // Gán sự kiện cho các nút
        ganSuKien();
    }

    private void initLoaiDialog() {
        if (khachHang != null) { 
            // Đang xem chi tiết -> Ẩn nút Thêm, khoá nhập liệu
            btnThem.setVisible(false);
            anThaoTacSua();
        } else { 
            // Đang mở để thêm mới -> Ẩn Sửa/Lưu, hiện nút Thêm
            btnLuu.setVisible(false);
            btnSua.setVisible(false);
            txtName.setEditable(true);
            txtPhone.setEditable(true);
            cbGioiTinh.setEnabled(true);
        }
    }

    private void anThaoTacSua() {
        btnSua.setVisible(true);
        btnLuu.setVisible(false);
        txtName.setEditable(false);
        txtPhone.setEditable(false);
        cbGioiTinh.setEnabled(false);
    }

    private void batThaoTacSua() {
        btnSua.setVisible(false);
        btnLuu.setVisible(true);
        txtName.setEditable(true);
        txtPhone.setEditable(true);
        cbGioiTinh.setEnabled(true);
    }

    private void ganSuKien() {
        btnHuy.addActionListener(e -> dispose());
        
        btnSua.addActionListener(e -> batThaoTacSua());

        // Sự kiện riêng biệt cho nút Thêm
        btnThem.addActionListener(e -> {
            String name = txtName.getText().trim();
            String phone = txtPhone.getText().trim();
            String gt = (String) cbGioiTinh.getSelectedItem();

            KhachHang kh = new KhachHang();
            kh.setTenKH(name);
            kh.setSdt(phone);
            kh.setGioiTinh(gt);
            kh.setTenDaMua(0);
            kh.setMaHang("HTV01");

            try {
                if (!bus.themKhachHang(kh)) {
                    JOptionPane.showMessageDialog(this, "Thêm thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    if (khUI != null) {
                        khUI.hienThiDanhSachKhachHang();
                    }
                    dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Sự kiện riêng biệt cho nút Lưu (khi sửa)
        btnLuu.addActionListener(e -> {
            if (khachHang == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng để sửa", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String name = txtName.getText().trim();
            String phone = txtPhone.getText().trim();
            String gt = (String) cbGioiTinh.getSelectedItem();

            khachHang.setTenKH(name);
            khachHang.setSdt(phone);
            khachHang.setGioiTinh(gt);

            String result = bus.capNhatKhachHang(khachHang);
            if (result != null) {
                JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                if (khUI != null) {
                    khUI.hienThiDanhSachKhachHang();
                }
                dispose();
            }
        });
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
        if (maHang == null)
            return "Thành Viên Mới";
        return switch (maHang) {
            case "HTV01" -> "Thành Viên Mới";
            case "HTV02" -> "Thành Viên Bạc";
            case "HTV03" -> "Thành Viên Vàng";
            case "HTV04" -> "Thành Viên Bạch Kim";
            case "HTV05" -> "Thành Viên Kim Cương";
            default -> "Thành Viên Mới";
        };
    }
}