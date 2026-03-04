package ui.nhanvien;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.toedter.calendar.JDateChooser;
import bus.NhanVienBUS;
import dto.NhanVien;
import util.Anh;
import util.TaoUI;

public class ThemNhanVienDialog extends JDialog {
    private JTextField txtName;
    private JTextField txtPhone;
    private JComboBox<String> cbGioiTinh;
    private JTextField txtAddress;
    private JDateChooser date;

    // Khai báo các biến liên quan đến hình ảnh
    private JLabel lblAnh;
    private JButton btnChonAnh;
    private JFileChooser fileChooser;
    private String hinhAnhPath = "";

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
        setSize(400, 600);
        setLocationRelativeTo(jFrame);
        setResizable(false);
    }

    public ThemNhanVienDialog(JFrame jFrame, NhanVienUI nvUI, NhanVien nhanVien) {
        super(jFrame, "Chi tiết nhân viên", true);
        this.nvUI = nvUI;
        this.nhanVien = nhanVien;
        initUI();
        dienThongTinNhanVien();
        setSize(400, 600);
        setLocationRelativeTo(jFrame);
        setResizable(false);
    }

    public void initUI() {
        JPanel mainPanel = TaoUI.taoPanelBoxLayoutDoc(400, 600);
        TaoUI.suaBorderChoPanel(mainPanel, 15, 15, 15, 15);

        // --- PHẦN HÌNH ẢNH VÀ NÚT CHỌN ẢNH ---
        JPanel pnlAnhMain = new JPanel();
        pnlAnhMain.setLayout(new BoxLayout(pnlAnhMain, BoxLayout.Y_AXIS));

        JPanel pnlAnh = new JPanel(new FlowLayout(FlowLayout.CENTER));
        lblAnh = new JLabel("Chưa có ảnh", SwingConstants.CENTER);
        lblAnh.setPreferredSize(new Dimension(200, 200));
        lblAnh.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        pnlAnh.add(lblAnh);

        JPanel pnlBtnAnh = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnChonAnh = new JButton("Chọn ảnh");
        pnlBtnAnh.add(btnChonAnh);

        pnlAnhMain.add(pnlAnh);
        pnlAnhMain.add(pnlBtnAnh);

        // --- CÁC TRƯỜNG NHẬP LIỆU ---
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
        dateField.setPreferredSize(new Dimension(330, 30));
        dateField.setMaximumSize(new Dimension(330, 20));

        JLabel dateLabel = new JLabel("Ngày sinh");
        dateLabel.setPreferredSize(new Dimension(110, 30));

        date.setPreferredSize(new Dimension(220, 30));
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

        // --- PHẦN BUTTON DƯỚI CÙNG ---
        JPanel buttonPanel = TaoUI.taoPanelCanGiua(330, 30);
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnLuu = new JButton("Lưu");
        btnDong = new JButton("Đóng");

        TaoUI.addItem(buttonPanel, btnThem, 5, true);
        TaoUI.addItem(buttonPanel, btnSua, 5, true);
        TaoUI.addItem(buttonPanel, btnLuu, 5, true);
        TaoUI.addItem(buttonPanel, btnDong, 5, true);

        // --- ADD VÀO MAIN PANEL ---
        mainPanel.add(pnlAnhMain);
        mainPanel.add(Box.createVerticalStrut(15));
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
        suaLaiGiaoDienTheoQuyen();
    }

    /**
     * Phân quyền cho Dialog Nhân viên:
     * - Nếu thêm mới: Ẩn nút "Thêm" và khóa Form nếu không có quyền NV_TAO.
     * - Nếu xem/sửa: Ẩn nút "Sửa" và nút "Chọn ảnh" nếu không có quyền NV_SUA.
     */
    public void suaLaiGiaoDienTheoQuyen() {
        var listQuyen = ui.login.PhienDangNhap.getListQuyen();

        // TRƯỜNG HỢP 1: Chế độ thêm mới (nhanVien == null)
        if (nhanVien == null) {
            if (!listQuyen.contains("NV_TAO")) {
                btnThem.setVisible(false);
                setEditableForm(false); // Khóa toàn bộ form nhập liệu
                this.setTitle("Thông tin nhân viên mới (Chỉ xem)");
            }
        }
        // TRƯỜNG HỢP 2: Chế độ xem hoặc sửa (nhanVien != null)
        else {
            if (!listQuyen.contains("NV_SUA")) {
                btnSua.setVisible(false);
                btnLuu.setVisible(false);
                btnChonAnh.setVisible(false); // Không cho phép thay đổi ảnh nếu không có quyền sửa
                this.setTitle("Chi tiết nhân viên (Chế độ chỉ đọc)");
            }
        }
        this.revalidate();
        this.repaint();
    }

    private void initLoaiDialog() {
        if (nhanVien != null) {
            btnDong.setVisible(false);
            btnThem.setVisible(false);
            anThaoTacSua();
        } else {
            btnSua.setVisible(false);
            btnLuu.setVisible(false);
            btnThem.setVisible(true);
            setEditableForm(true);
        }
    }

    private void setEditableForm(boolean status) {
        txtName.setEditable(status);
        txtPhone.setEditable(status);
        txtAddress.setEditable(status);
        cbGioiTinh.setEnabled(status);
        date.setEnabled(status);
        btnChonAnh.setEnabled(status);
    }

    private void anThaoTacSua() {
        btnSua.setEnabled(true);
        btnLuu.setEnabled(false);
        setEditableForm(false);
    }

    private void batThaoTacSua() {
        btnSua.setEnabled(false);
        btnLuu.setEnabled(true);
        setEditableForm(true);
    }

    private void ganSuKien() {
        btnDong.addActionListener(e -> dispose());
        btnSua.addActionListener(e -> batThaoTacSua());
        btnChonAnh.addActionListener(e -> chonAnh());

        // XỬ LÝ THÊM NHÂN VIÊN
        btnThem.addActionListener(e -> {
            NhanVien nv = getFormDinhDang();
            if (nv == null)
                return;

            // Xử lý lưu ảnh vào thư mục dự án
            if (fileChooser != null && fileChooser.getSelectedFile() != null) {
                String duongDanMoi = Anh.luuAnhNV(bus.layMaNVMoi(), fileChooser);
                if (duongDanMoi != null) {
                    nv.setAnh(duongDanMoi);
                }
            }

            String thongBaoLoi = bus.themNhanVien(nv);

            if (thongBaoLoi != null) {
                JOptionPane.showMessageDialog(this, thongBaoLoi, "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                if (nvUI != null)
                    nvUI.hienThiDanhSachNhanVien();
                dispose();
            }
        });

        // XỬ LÝ LƯU (CẬP NHẬT)
        btnLuu.addActionListener(e -> {
            if (nhanVien == null)
                return;

            NhanVien nvMoi = getFormDinhDang();
            if (nvMoi == null)
                return;

            nvMoi.setMaNV(nhanVien.getMaNV());

            // Xử lý lưu ảnh mới (nếu có chọn)
            if (fileChooser != null && fileChooser.getSelectedFile() != null) {
                String duongDanMoi = Anh.luuAnhNV(bus.layMaNVMoi(), fileChooser);
                if (duongDanMoi != null) {
                    nvMoi.setAnh(duongDanMoi);
                }
            } else {
                nvMoi.setAnh(nhanVien.getAnh());
            }

            String thongBaoLoi = bus.capNhatNhanVien(nvMoi);

            if (thongBaoLoi != null) {
                JOptionPane.showMessageDialog(this, thongBaoLoi, "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                if (nvUI != null)
                    nvUI.hienThiDanhSachNhanVien();
                dispose();
            }
        });
    }

    private void chonAnh() {
        if (fileChooser == null) {
            fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn ảnh nhân viên");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Hình ảnh (JPG, PNG, GIF)", "jpg", "jpeg",
                    "png", "gif");
            fileChooser.setFileFilter(filter);
        }

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            hinhAnhPath = selectedFile.getAbsolutePath();
            hienThiAnh(hinhAnhPath);
        }
    }

    private void hienThiAnh(String path) {
        if (path != null && !path.trim().isEmpty()) {
            try {
                ImageIcon icon = new ImageIcon(path);
                Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                lblAnh.setIcon(new ImageIcon(img));
                lblAnh.setText("");
            } catch (Exception ex) {
                lblAnh.setIcon(null);
                lblAnh.setText("Lỗi tải ảnh");
            }
        }
    }

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

        // Mặc định set đường dẫn ảnh đang hiện trên form (trước khi lưu đè)
        nv.setAnh(hinhAnhPath);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        nv.setNgaySinh(sdf.format(date.getDate()));

        return nv;
    }

    private void dienThongTinNhanVien() {
        if (nhanVien == null)
            return;

        txtName.setText(nhanVien.getTenNV());
        txtPhone.setText(nhanVien.getSdt());
        txtAddress.setText(nhanVien.getDiaChi());
        cbGioiTinh.setSelectedItem(nhanVien.getGioiTinh());

        hinhAnhPath = nhanVien.getAnh();
        if (hinhAnhPath != null && !hinhAnhPath.trim().isEmpty()) {
            hienThiAnh(hinhAnhPath);
        } else {
            lblAnh.setText("Chưa có ảnh");
        }

        try {
            if (nhanVien.getNgaySinh() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                date.setDate(sdf.parse(nhanVien.getNgaySinh()));
            }
        } catch (Exception e) {
            date.setDate(new Date());
        }
    }

    public static void main(String[] args) {
        ThemNhanVienDialog themNhanVienDialog = new ThemNhanVienDialog(null, null);
        themNhanVienDialog.setVisible(true);
    }
}