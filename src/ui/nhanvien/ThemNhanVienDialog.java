package ui.nhanvien;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

import com.toedter.calendar.JDateChooser;

import bus.NhanVienBUS;
import dto.NhanVien;
import util.TaoUI;

public class ThemNhanVienDialog extends JDialog {
    private JTextField txtName;
    private JComboBox<String> cbChucVu;
    private JTextField txtPhone;
    private JComboBox<String> cbGioiTinh;
    private JTextField txtAddress;
    private JDateChooser date;
    // Đã loại bỏ cbTrangThaiNV vì chỉ quản lý NV đang làm việc
    private NhanVienUI nvUI;
    private NhanVienBUS bus = new NhanVienBUS();
    private boolean isEditMode = false;
    private NhanVien nhanVien;
    private JButton btnLuu;
    private JButton btnHuy;

    public ThemNhanVienDialog(JFrame jFrame, NhanVienUI nvUI) {
        super(jFrame, "Thêm nhân viên", true);
        this.nvUI = nvUI;
        this.isEditMode = false;
        this.nhanVien = null;
        initUI();
        setSize(400, 450); // Điều chỉnh lại chiều cao cho phù hợp
        setLocationRelativeTo(jFrame);
        setResizable(false);
    }

    public ThemNhanVienDialog(JFrame jFrame, NhanVienUI nvUI, NhanVien nhanVien) {
        super(jFrame, "Sửa nhân viên", true);
        this.nvUI = nvUI;
        this.isEditMode = true;
        this.nhanVien = nhanVien;
        initUI();
        dienThongTinNhanVien();
        setSize(400, 450);
        setLocationRelativeTo(jFrame);
        setResizable(false);
    }

    public void initUI() {
        // PANEL CHÍNH
        JPanel mainPanel = TaoUI.taoPanelBoxLayoutDoc(400, 400);
        TaoUI.suaBorderChoPanel(mainPanel, 15, 15, 15, 15);

        // NAME
        txtName = new JTextField();
        JPanel nameField = TaoUI.taoFieldText("Tên nhân viên", 100, 220, 30, 10, txtName);
        
        // PHONE
        txtPhone = new JTextField();
        JPanel phoneField = TaoUI.taoFieldText("Số điện thoại", 100, 220, 30, 10, txtPhone);
        
        // ADDRESS
        txtAddress = new JTextField();
        JPanel addressField = TaoUI.taoFieldText("Địa chỉ", 100, 220, 30, 10, txtAddress);
        
        // DATE (NGÀY SINH)
        date = new JDateChooser();
        date.setDateFormatString("dd/MM/yyyy");
        JPanel dateField = new JPanel();
        dateField.setLayout(new BoxLayout(dateField, BoxLayout.X_AXIS));
        dateField.setPreferredSize(new Dimension(320, 30));

        JLabel dateLabel = new JLabel("Ngày sinh");
        dateLabel.setPreferredSize(new Dimension(100, 30));
        dateLabel.setMaximumSize(new Dimension(100, 30));
        
        date.setPreferredSize(new Dimension(215, 30));
        date.setMaximumSize(new Dimension(215, 30));

        dateField.add(dateLabel);
        dateField.add(date);

        // GIOI TINH
        JPanel cbGTJPanel = new JPanel();
        cbGTJPanel.setLayout(new BoxLayout(cbGTJPanel, BoxLayout.X_AXIS));
        TaoUI.setFixSize(cbGTJPanel, 330, 30);

        String[] dsGioiTinh = { "Nam", "Nữ" };
        cbGioiTinh = new JComboBox<>(dsGioiTinh);

        JLabel cbGTLabel = new JLabel("Giới tính");
        cbGTLabel.setPreferredSize(new Dimension(110, 30));
        cbGTLabel.setMaximumSize(new Dimension(110, 30));

        cbGTJPanel.add(cbGTLabel);
        cbGTJPanel.add(cbGioiTinh);

        // CHUC VU
        JPanel cbCVPanel = new JPanel();
        cbCVPanel.setLayout(new BoxLayout(cbCVPanel, BoxLayout.X_AXIS));
        TaoUI.setFixSize(cbCVPanel, 330, 30);

        cbChucVu = new JComboBox<>();
        List<String> dsChucVu = bus.layDanhSachChucVu();
        for (String cv : dsChucVu) {
            cbChucVu.addItem(cv);
        }

        JLabel cbCVLabel = new JLabel("Chức vụ");
        cbCVLabel.setPreferredSize(new Dimension(110, 30));
        cbCVLabel.setMaximumSize(new Dimension(110, 30));

        cbCVPanel.add(cbCVLabel);
        cbCVPanel.add(cbChucVu);

        // BUTTON PANEL
        JPanel buttonPanel = TaoUI.taoPanelCanGiua(330, 30);
        btnLuu = new JButton(isEditMode ? "Lưu" : "Thêm");
        btnHuy = new JButton("Hủy");

        TaoUI.addItem(buttonPanel, btnLuu, 5, true);
        TaoUI.addItem(buttonPanel, btnHuy, 5, true);

        // ADD COMPONENTS TO MAIN PANEL
        mainPanel.add(nameField);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(phoneField);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(addressField);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(dateField);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(cbGTJPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(cbCVPanel);
        // Đã xóa phần hiển thị cbTTPanel (Trạng thái)
        mainPanel.add(Box.createVerticalStrut(25));
        mainPanel.add(buttonPanel);
        
        add(mainPanel, BorderLayout.CENTER);

        // ===== EVENT =====
        btnHuy.addActionListener(e -> dispose());
        btnLuu.addActionListener(e -> xuLyLuuNhanVien());
    }

    private void dienThongTinNhanVien() {
        if (nhanVien == null) return;
        
        txtName.setText(nhanVien.getTenNV());
        txtPhone.setText(nhanVien.getSdt());
        txtAddress.setText(nhanVien.getDiaChi());
        cbGioiTinh.setSelectedItem(nhanVien.getGioiTinh());
        cbChucVu.setSelectedItem(nhanVien.getChucVu());
        
        try {
            if (nhanVien.getNgaySinh() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date d = sdf.parse(nhanVien.getNgaySinh());
                date.setDate(d);
            }
        } catch (Exception e) {
            date.setDate(new Date());
        }
    }

    public void xuLyLuuNhanVien() {
        // Kiểm tra dữ liệu cơ bản
        if (txtName.getText().trim().isEmpty() || date.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin bắt buộc!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String name = txtName.getText().trim();
        String phone = txtPhone.getText().trim();
        String address = txtAddress.getText().trim();
        String gt = (String) cbGioiTinh.getSelectedItem();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nsString = sdf.format(date.getDate());
        String cv = (String) cbChucVu.getSelectedItem();

        if (!isEditMode) {
            // CHẾ ĐỘ THÊM MỚI
            NhanVien nv = new NhanVien();
            nv.setTenNV(name);
            nv.setSdt(phone);
            nv.setDiaChi(address);
            nv.setGioiTinh(gt);
            nv.setNgaySinh(nsString);
            // Đã xóa nv.setNgayVaoLam() vì database và DTO không còn cột này
            nv.setChucVu(cv);
            nv.setTrangThai(true); // Mặc định là đang làm việc

            String result = bus.themNhanVien(nv);
            if (result != null) {
                JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                if (nvUI != null) nvUI.hienThiDanhSachNhanVien();
                dispose();
            }
        } else {
            // CHẾ ĐỘ SỬA
            if (nhanVien == null) return;
            
            nhanVien.setTenNV(name);
            nhanVien.setSdt(phone);
            nhanVien.setDiaChi(address);
            nhanVien.setGioiTinh(gt);
            nhanVien.setNgaySinh(nsString);
            nhanVien.setChucVu(cv);
            // Giữ nguyên trạng thái true vì họ vẫn đang xuất hiện trong danh sách để sửa
            nhanVien.setTrangThai(true); 

            String result = bus.capNhatNhanVien(nhanVien);
            if (result != null) {
                JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                if (nvUI != null) nvUI.hienThiDanhSachNhanVien();
                dispose();
            }
        }
    }
}