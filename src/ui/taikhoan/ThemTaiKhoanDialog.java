package ui.taikhoan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import bus.NhanVienBUS;
import bus.NhomQuyenBUS;
import bus.TaiKhoanBUS;
import dto.NhanVien;
import dto.NhomQuyen;
import dto.TaiKhoan;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import util.TaoUI;

public class ThemTaiKhoanDialog extends JDialog {
    private JButton btnThem, btnLuu, btnSua, btnHuy;
    private JTextField txtUser;
    private JPasswordField txtPass;

    private JComboBox<String> cbQuyen;
    private JComboBox<String> cbNVBox;
    private JComboBox<String> cbTrangThai;
    private NhomQuyenBUS nhomQuyenBUS = NhomQuyenBUS.getNhomQuyenBUS();
    private TaiKhoanBUS taiKhoanBUS = TaiKhoanBUS.getTaiKhoanBUS();
    private TaiKhoanUI taiKhoanUI;
    private TaiKhoan taiKhoan;
    private ArrayList<NhomQuyen> dsNhomQuyen;
    private ArrayList<NhanVien> dsNhanVien;
    private JPanel cbTrangThaiPanel;

    public ThemTaiKhoanDialog(JFrame jFrame, TaiKhoanUI taiKhoanUI, TaiKhoan taiKhoan) {
        super(jFrame, true);
        initUI();
        this.taiKhoanUI = taiKhoanUI;
        this.taiKhoan = taiKhoan;
        setSize(400, 330);
        setLocationRelativeTo(jFrame);
        setResizable(false);
        ganDuLieu();
        ganSuKien();
    }

    private void initUI() {
        // PANEL CHÍNH
        JPanel mainPanel = TaoUI.taoPanelBoxLayoutDoc(400, 180);
        TaoUI.suaBorderChoPanel(mainPanel, 15, 15, 15, 15);

        // USER
        txtUser = new JTextField();
        JPanel userField = TaoUI.taoFieldText("Username", 100, 220, 30, 10, txtUser);
        // conbobox nhan vien
        JPanel cbNVJPanel = new JPanel();
        cbNVJPanel.setLayout(new BoxLayout(cbNVJPanel, BoxLayout.X_AXIS));
        TaoUI.setFixSize(cbNVJPanel, 330, 30);

        JLabel cbNVJLabel = new JLabel("Nhân Viên");
        cbNVJLabel.setPreferredSize(new Dimension(110, 30));
        cbNVJLabel.setMinimumSize(new Dimension(110, 30));
        cbNVJLabel.setMaximumSize(new Dimension(110, 30));

        NhanVienBUS nhanVienBUS = NhanVienBUS.getNhanVienBUS();
        dsNhanVien = nhanVienBUS.layDanhSachNhanVien();
        cbNVBox = new JComboBox<>();
        for (NhanVien nv : dsNhanVien) {
            cbNVBox.addItem(nv.getTenNV() + " (" + nv.getMaNV() + ")");
        }
        cbNVJPanel.add(cbNVJLabel);
        cbNVJPanel.add(cbNVBox);
        // PASSWORD
        txtPass = new JPasswordField();
        JPanel passField = TaoUI.taoFieldText("Password", 100, 220, 30, 10, txtPass);

        // BUTTON PANEL
        JPanel buttonPanel = TaoUI.taoPanelCanGiua(330, 30);

        btnThem = new JButton("Thêm");
        btnHuy = new JButton("Hủy");
        btnSua = new JButton("Sửa");//
        btnLuu = new JButton("Lưu");//

        TaoUI.addItem(buttonPanel, btnThem, 5, true);
        TaoUI.addItem(buttonPanel, btnHuy, 5, true);
        TaoUI.addItem(buttonPanel, btnSua, 5, true);
        TaoUI.addItem(buttonPanel, btnLuu, 5, true);
        

        // COMBOBOX quyen
        JPanel cbJPanel = new JPanel();

        cbJPanel.setLayout(new BoxLayout(cbJPanel, BoxLayout.X_AXIS));
        TaoUI.setFixSize(cbJPanel, 330, 30);

        JLabel cbJLabel = new JLabel("Quyền");
        cbJLabel.setPreferredSize(new Dimension(110, 30));
        cbJLabel.setMinimumSize(new Dimension(110, 30));
        cbJLabel.setMaximumSize(new Dimension(110, 30));

        dsNhomQuyen = nhomQuyenBUS.layDanhSachNhomQuyen();
        cbQuyen = new JComboBox<>();
        for (NhomQuyen nq : dsNhomQuyen) {
            cbQuyen.addItem(nq.getTenNhomQuyen());
        }

        cbJPanel.add(cbJLabel);
        cbJPanel.add(cbQuyen);

        // COMBOBOX trạng thái
        cbTrangThaiPanel = new JPanel();
        cbTrangThaiPanel.setLayout(new BoxLayout(cbTrangThaiPanel, BoxLayout.X_AXIS));
        TaoUI.setFixSize(cbTrangThaiPanel, 330, 30);

        JLabel lbTrangThai = new JLabel("Trạng thái");
        lbTrangThai.setPreferredSize(new Dimension(110, 30));
        lbTrangThai.setMinimumSize(new Dimension(110, 30));
        lbTrangThai.setMaximumSize(new Dimension(110, 30));

        String[] trangThaiArr = {"Đang hoạt động", "Đã khóa"};
        cbTrangThai = new JComboBox<>(trangThaiArr);

        cbTrangThaiPanel.add(lbTrangThai);
        cbTrangThaiPanel.add(cbTrangThai);

        // ADD COMPONENT
        mainPanel.add(cbNVJPanel);
        mainPanel.add(javax.swing.Box.createVerticalStrut(10));
        mainPanel.add(userField);
        mainPanel.add(javax.swing.Box.createVerticalStrut(10));
        mainPanel.add(passField);
        mainPanel.add(javax.swing.Box.createVerticalStrut(20));
        mainPanel.add(cbJPanel);
        mainPanel.add(javax.swing.Box.createVerticalStrut(20));
        mainPanel.add(cbTrangThaiPanel);
        mainPanel.add(javax.swing.Box.createVerticalStrut(20));
        mainPanel.add(buttonPanel);
        add(mainPanel, BorderLayout.CENTER);

        // ===== EVENT =====
        btnHuy.addActionListener(e -> dispose());
    }

    private void ganDuLieu() {
        if (taiKhoan != null) { // CHẾ ĐỘ SỬA
            setTitle("Chi tiết tài khoản");

            txtUser.setText(taiKhoan.getTenDangNhap());
            txtPass.setText(taiKhoan.getMatKhau());

            // set nhân viên
            for (int i = 0; i < dsNhanVien.size(); i++) {
                if (dsNhanVien.get(i).getMaNV().equals(taiKhoan.getMaNV())) {
                    cbNVBox.setSelectedIndex(i);
                    break;
                }
            }

            // set quyền
            for (int i = 0; i < dsNhomQuyen.size(); i++) {
                if (dsNhomQuyen.get(i).getMaNQ()
                        .equals(taiKhoan.getNhomQuyen().getMaNQ())) {
                    cbQuyen.setSelectedIndex(i);
                    break;
                }
            }

            // set trạng thái xử lý
            if ("Đang hoạt động".equals(taiKhoan.getTrangThaiXuLy())) {
                cbTrangThai.setSelectedIndex(0);
            } else {
                cbTrangThai.setSelectedIndex(1);
            }

            txtUser.setEditable(false);
            txtPass.setEditable(false);
            cbNVBox.setEnabled(false);
            cbQuyen.setEnabled(false);

            btnThem.setVisible(false);
            btnLuu.setEnabled(false);
            btnLuu.setVisible(true);
            cbTrangThai.setEnabled(false);
            btnSua.setVisible(true);
            btnHuy.setVisible(false);
        } else {
            setTitle("Thêm tài khoản");
            btnSua.setVisible(false);
            btnLuu.setVisible(false);
            cbTrangThaiPanel.setVisible(false);
            setSize(new Dimension(400,300));
        }
    }

    private void ganSuKien() {
        btnThem.addActionListener(e -> {
            if (txtUser.getText().trim().isEmpty()
                || txtPass.getPassword().length == 0) {

                JOptionPane.showMessageDialog(this,
                        "Vui lòng nhập đầy đủ Username và Password!");
                return;
            }

            if (taiKhoanBUS.kiemTraUsernameTonTai(txtUser.getText().trim())) {
                JOptionPane.showMessageDialog(this,
                        "Username đã tồn tại!");
                return;
            }

            TaiKhoan tk = dongGoiTaiKhoan();

            if (taiKhoanBUS.themTaiKhoan(tk)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                taiKhoanUI.hienThiDanhSachTaiKhoan();
                dispose();
            }
        });

        btnSua.addActionListener(e -> {
            if (txtUser.getText().trim().isEmpty()
                || txtPass.getPassword().length == 0)
            txtPass.setEditable(true);
            txtUser.setEditable(true);
            cbNVBox.setEnabled(true);
            cbQuyen.setEnabled(true);
            cbTrangThai.setEnabled(true);

            btnSua.setEnabled(false);
            btnLuu.setEnabled(true);
        });

        btnLuu.addActionListener(e -> {
            TaiKhoan tk = dongGoiTaiKhoan();
            tk.setMaTK(taiKhoan.getMaTK());

            if (TaiKhoanBUS.getTaiKhoanBUS().suaTaiKhoan(tk)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                taiKhoanUI.hienThiDanhSachTaiKhoan();
                dispose();
            }
        });
    }

    private TaiKhoan dongGoiTaiKhoan() {
        TaiKhoan tk = new TaiKhoan();
        //set tk mk
        tk.setTenDangNhap(txtUser.getText());
        tk.setMatKhau(new String(txtPass.getPassword()));

        int indexNV = cbNVBox.getSelectedIndex();
        int indexNQ = cbQuyen.getSelectedIndex();

        //set manv
        tk.setMaNV(dsNhanVien.get(indexNV).getMaNV());
        //set nhom quyen
        NhomQuyen nq = new NhomQuyen();
        nq.setMaNQ(dsNhomQuyen.get(indexNQ).getMaNQ());
        tk.setNhomQuyen(nq);

        if (taiKhoan == null) {
            // chế độ thêm
            tk.setTrangThaiXuLy("Đang hoạt động");
        } else {
            // chế độ sửa
            tk.setTrangThaiXuLy(cbTrangThai.getSelectedItem().toString());
        }
        return tk;
    }
}
