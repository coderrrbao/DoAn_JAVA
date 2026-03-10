package ui.khachhang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import bus.KhachHangBUS;
import dto.KhachHang;
import ui.login.LoginUI;
import util.TaoUI;

public class ThemKhachHangDialog extends JDialog {
    private JTextField txtName;
    private JTextField txtPhone;
    private JComboBox<String> cbGioiTinh;
    private JLabel lblHangThanhVien;

    private JButton btnLuu;
    private JButton btnHuy;
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
        setSize(400, 260);
        setLocationRelativeTo(owner);
        setResizable(false);
        suaLaiGiaoDienTheoQuyen();
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
        suaLaiGiaoDienTheoQuyen();
    }

    public void suaLaiGiaoDienTheoQuyen() {
        var listQuyen = ui.login.PhienDangNhap.getListQuyen();

        if (khachHang == null) {
            if (!listQuyen.contains("KH_TAO")) {
                btnThem.setVisible(false);
                setFieldsEnabled(false);
                this.setTitle("Thông tin khách hàng (Chỉ xem)");
            }
        } else {
            if (!listQuyen.contains("KH_SUA")) {
                btnSua.setVisible(false);
                btnLuu.setVisible(false);
                this.setTitle("Chi tiết khách hàng (Chế độ chỉ đọc)");
            }
        }
    }

    private void initUI() {
        JPanel mainPanel = TaoUI.taoPanelBoxLayoutDoc(400, 180);
        TaoUI.suaBorderChoPanel(mainPanel, 15, 15, 15, 15);

        txtName = new JTextField();
        JPanel nameField = TaoUI.taoFieldText("Tên khách hàng", 110, 210, 30, 10, txtName);

        txtPhone = new JTextField();
        JPanel phoneField = TaoUI.taoFieldText("Số điện thoại", 110, 210, 30, 10, txtPhone);

        // Chỉ cho phép nhập số
        txtPhone.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                    e.consume();
                }
            }
        });

        JPanel cbGTPanel = new JPanel();
        cbGTPanel.setLayout(new BoxLayout(cbGTPanel, BoxLayout.X_AXIS));
        TaoUI.setFixSize(cbGTPanel, 330, 30);

        String[] dsGioiTinh = { "Nam", "Nữ" };
        cbGioiTinh = new JComboBox<>(dsGioiTinh);

        JLabel cbGTLabel = new JLabel("Giới tính");
        cbGTLabel.setPreferredSize(new Dimension(120, 30));
        cbGTLabel.setMinimumSize(new Dimension(120, 30));
        cbGTLabel.setMaximumSize(new Dimension(120, 30));

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

        btnSua = new JButton("Sửa");
        btnThem = new JButton("Thêm");
        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Đóng");

        TaoUI.addItem(buttonPanel, btnThem, 5, true);
        TaoUI.addItem(buttonPanel, btnSua, 5, true);
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

        initLoaiDialog();
        ganSuKien();
    }

    private void initLoaiDialog() {
        if (khachHang != null) {
            btnHuy.setVisible(false);
            btnThem.setVisible(false);
            anThaoTacSua();
        } else {
            btnSua.setVisible(false);
            btnLuu.setVisible(false);
            btnThem.setVisible(true);
            setFieldsEnabled(true);
        }
    }

    private void anThaoTacSua() {
        btnSua.setEnabled(true);
        btnLuu.setEnabled(false);
        setFieldsEnabled(false);
    }

    private void batThaoTacSua() {
        btnSua.setEnabled(false);
        btnLuu.setEnabled(true);
        setFieldsEnabled(true);
    }

    private void setFieldsEnabled(boolean status) {
        txtName.setEditable(status);
        txtPhone.setEditable(status);
        cbGioiTinh.setEnabled(status);
    }

    private void ganSuKien() {
        btnHuy.addActionListener(e -> dispose());

        btnSua.addActionListener(e -> batThaoTacSua());

        // Xử lý nút Thêm: Bắt lỗi trực tiếp từ Exception của hàm themKhachHang
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
                // Đẩy hẳn đối tượng xuống BUS. Nếu lỗi, BUS sẽ ném Exception
                bus.themKhachHang(kh);
                
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                if (khUI != null) {
                    LoginUI.getLoginUI().getMainFrame().loadAllData();
                }
                dispose();
            } catch (Exception ex) {
                // In ra thông báo lỗi mà hàm themKhachHang đã thiết lập
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Xử lý nút Lưu (Cập nhật): Bắt lỗi bằng kết quả String trả về từ hàm capNhatKhachHang
        btnLuu.addActionListener(e -> {
            if (khachHang == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng để sửa", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            khachHang.setTenKH(txtName.getText().trim());
            khachHang.setSdt(txtPhone.getText().trim());
            khachHang.setGioiTinh((String) cbGioiTinh.getSelectedItem());

            // Đẩy xuống BUS
            String errorMsg = bus.capNhatKhachHang(khachHang);
            
            if (errorMsg != null) {
                // Nếu errorMsg khác null nghĩa là có lỗi
                JOptionPane.showMessageDialog(this, errorMsg, "Lỗi cập nhật", JOptionPane.ERROR_MESSAGE);
            } else {
                // Nếu errorMsg là null nghĩa là thành công
                JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                if (khUI != null) {
                    LoginUI.getLoginUI().getMainFrame().loadAllData();
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
    
        lblHangThanhVien.setText(bus.layTenHangTuMa(khachHang.getMaHang()));
    }
}