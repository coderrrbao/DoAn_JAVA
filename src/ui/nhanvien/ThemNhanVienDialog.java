package ui.nhanvien;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.toedter.calendar.JDateChooser;
import bus.NhanVienBUS;
import dto.NhanVien;
import util.TaoUI;

public class ThemNhanVienDialog extends JDialog {
    private JTextField txtName;
    private JTextField txtPhone;
    private JComboBox<String> cbGioiTinh;
    private JTextField txtAddress;
    private JDateChooser date;
    
    private JButton btnThem;
    private JButton btnSua;
    private JButton btnLuu;
    private JButton btnDong; 

    private NhanVienUI nvUI;
    private NhanVienBUS bus = NhanVienBUS.getNhanVienBUS();
    private NhanVien nhanVien;

    public ThemNhanVienDialog(JFrame jFrame, NhanVienUI nvUI) {
        super(jFrame, "Thêm nhân viên", true);
        this.nvUI = nvUI;
        this.nhanVien = null;
        initUI();
        setSize(400, 400); 
        setLocationRelativeTo(jFrame);
        setResizable(false);
    }

    public ThemNhanVienDialog(JFrame jFrame, NhanVienUI nvUI, NhanVien nhanVien) {
        super(jFrame, "Chi tiết nhân viên", true); 
        this.nvUI = nvUI;
        this.nhanVien = nhanVien;
        initUI();
        dienThongTinNhanVien();
        setSize(400, 400); 
        setLocationRelativeTo(jFrame);
        setResizable(false);
    }

    public void initUI() {
        JPanel mainPanel = TaoUI.taoPanelBoxLayoutDoc(400, 400);
        TaoUI.suaBorderChoPanel(mainPanel, 15, 15, 15, 15);

        txtName = new JTextField();
        JPanel nameField = TaoUI.taoFieldText("Tên nhân viên", 100, 220, 30, 10, txtName);
        
        txtPhone = new JTextField();
        JPanel phoneField = TaoUI.taoFieldText("Số điện thoại", 100, 220, 30, 10, txtPhone);
        
        txtAddress = new JTextField();
        JPanel addressField = TaoUI.taoFieldText("Địa chỉ", 100, 220, 30, 10, txtAddress);
        
        date = new JDateChooser();
        date.setDateFormatString("dd/MM/yyyy");
        JPanel dateField = new JPanel();
        dateField.setLayout(new BoxLayout(dateField, BoxLayout.X_AXIS));
        dateField.setPreferredSize(new Dimension(320, 30));

        JLabel dateLabel = new JLabel("Ngày sinh");
        dateLabel.setPreferredSize(new Dimension(100, 30));
        
        date.setPreferredSize(new Dimension(215, 30));
        dateField.add(dateLabel);
        dateField.add(date);

        JPanel cbGTJPanel = new JPanel();
        cbGTJPanel.setLayout(new BoxLayout(cbGTJPanel, BoxLayout.X_AXIS));
        TaoUI.setFixSize(cbGTJPanel, 330, 30);

        String[] dsGioiTinh = { "Nam", "Nữ" };
        cbGioiTinh = new JComboBox<>(dsGioiTinh);
        JLabel cbGTLabel = new JLabel("Giới tính");
        cbGTLabel.setPreferredSize(new Dimension(110, 30));

        cbGTJPanel.add(cbGTLabel);
        cbGTJPanel.add(cbGioiTinh);

        JPanel buttonPanel = TaoUI.taoPanelCanGiua(330, 30);
        btnSua = new JButton("Sửa");
        btnThem = new JButton("Thêm");
        btnLuu = new JButton("Lưu");
        btnDong = new JButton("Đóng");

        TaoUI.addItem(buttonPanel, btnSua, 5, true);
        TaoUI.addItem(buttonPanel, btnThem, 5, true);
        TaoUI.addItem(buttonPanel, btnLuu, 5, true);
        TaoUI.addItem(buttonPanel, btnDong, 5, true);

        mainPanel.add(nameField);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(phoneField);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(addressField);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(dateField);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(cbGTJPanel);
        mainPanel.add(Box.createVerticalStrut(25));
        mainPanel.add(buttonPanel);
        
        add(mainPanel, BorderLayout.CENTER);

        initLoaiDialog();
        ganSuKien();
    }

    private void initLoaiDialog() {
        if (nhanVien != null) {
            btnThem.setVisible(false);
            anThaoTacSua();
        } else {
            btnLuu.setVisible(false);
            btnSua.setVisible(false);
            setEditableForm(true);
        }
    }

    private void setEditableForm(boolean status) {
        txtName.setEditable(status);
        txtPhone.setEditable(status);
        txtAddress.setEditable(status);
        cbGioiTinh.setEnabled(status);
        date.setEnabled(status);
    }

    private void anThaoTacSua() {
        btnSua.setVisible(true);
        btnLuu.setVisible(false);
        setEditableForm(false);
    }

    private void batThaoTacSua() {
        btnSua.setVisible(false);
        btnLuu.setVisible(true);
        setEditableForm(true);
    }

    private void ganSuKien() {
        btnDong.addActionListener(e -> dispose());
        btnSua.addActionListener(e -> batThaoTacSua());

        // XỬ LÝ THÊM NHÂN VIÊN
        btnThem.addActionListener(e -> {
            NhanVien nv = getFormDinhDang();
            if (nv == null) return;

            // Đồng bộ: BUS trả về String (null = success)
            String thongBaoLoi = bus.themNhanVien(nv);
            
            if (thongBaoLoi != null) {
                JOptionPane.showMessageDialog(this, thongBaoLoi, "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                if (nvUI != null) nvUI.hienThiDanhSachNhanVien();
                dispose();
            }
        });

        // XỬ LÝ LƯU (CẬP NHẬT)
        btnLuu.addActionListener(e -> {
            if (nhanVien == null) return;
            
            NhanVien nvMoi = getFormDinhDang();
            if (nvMoi == null) return;
            
            // Giữ lại mã nhân viên cũ để cập nhật
            nvMoi.setMaNV(nhanVien.getMaNV());

            String thongBaoLoi = bus.capNhatNhanVien(nvMoi);
            
            if (thongBaoLoi != null) {
                JOptionPane.showMessageDialog(this, thongBaoLoi, "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                if (nvUI != null) nvUI.hienThiDanhSachNhanVien();
                dispose();
            }
        });
    }

    // Hàm lấy dữ liệu từ Form và đóng gói vào Object DTO
    private NhanVien getFormDinhDang() {
        if (date.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày sinh!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        
        NhanVien nv = new NhanVien();
        nv.setTenNV(txtName.getText().trim());
        nv.setSdt(txtPhone.getText().trim());
        nv.setDiaChi(txtAddress.getText().trim());
        nv.setGioiTinh((String) cbGioiTinh.getSelectedItem());
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        nv.setNgaySinh(sdf.format(date.getDate()));
        
        return nv;
    }

    private void dienThongTinNhanVien() {
        if (nhanVien == null) return;
        txtName.setText(nhanVien.getTenNV());
        txtPhone.setText(nhanVien.getSdt());
        txtAddress.setText(nhanVien.getDiaChi());
        cbGioiTinh.setSelectedItem(nhanVien.getGioiTinh());
        
        try {
            if (nhanVien.getNgaySinh() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                date.setDate(sdf.parse(nhanVien.getNgaySinh()));
            }
        } catch (Exception e) {
            date.setDate(new Date());
        }
    }
}