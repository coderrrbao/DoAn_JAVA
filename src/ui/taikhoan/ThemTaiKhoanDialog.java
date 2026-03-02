package ui.taikhoan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

    private JTextField txtUser;
    private JPasswordField txtPass;
    private JTextField txtTenTaiKhoan;
    private JComboBox<String> cbQuyen;
    private JComboBox<String> cbNVBox;
    private NhomQuyenBUS nhomQuyenBUS = NhomQuyenBUS.getNhomQuyenBUS();
    private TaiKhoanBUS taiKhoanBUS = TaiKhoanBUS.getTaiKhoanBUS();

    private TaiKhoanUI taiKhoanUI;
    private ArrayList<NhomQuyen> dsNhomQuyen;
    private ArrayList<NhanVien> dsNhanVien;

    public ThemTaiKhoanDialog(JFrame jFrame, TaiKhoanUI taiKhoanUI) {
        super(jFrame, "Thêm tài khoản", true);
        initUI();
        this.taiKhoanUI = taiKhoanUI;
        setSize(400, 300);
        setLocationRelativeTo(jFrame);
        setResizable(false);
    }

    private void initUI() {
        // PANEL CHÍNH
        JPanel mainPanel = TaoUI.taoPanelBoxLayoutDoc(400, 180);
        TaoUI.suaBorderChoPanel(mainPanel, 15, 15, 15, 15);

        // USER
        txtUser = new JTextField();
        JPanel userField = TaoUI.taoFieldText("Username", 100, 220, 30, 10, txtUser);
        // TÊN TÀI KHOẢN // conbobox nhan vien
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

        JButton btnThem = new JButton("Thêm");
        JButton btnHuy = new JButton("Hủy");

        TaoUI.addItem(buttonPanel, btnThem, 5, true);
        TaoUI.addItem(buttonPanel, btnHuy, 5, true);

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

        // ADD COMPONENT
        mainPanel.add(cbNVJPanel);
        mainPanel.add(javax.swing.Box.createVerticalStrut(10));
        mainPanel.add(userField);
        mainPanel.add(javax.swing.Box.createVerticalStrut(10));
        mainPanel.add(passField);
        mainPanel.add(javax.swing.Box.createVerticalStrut(20));
        mainPanel.add(cbJPanel);
        mainPanel.add(javax.swing.Box.createVerticalStrut(20));
        mainPanel.add(buttonPanel);
        add(mainPanel, BorderLayout.CENTER);

        // ===== EVENT =====
        btnHuy.addActionListener(e -> dispose());

        btnThem.addActionListener(e -> xuLyThemTaiKhoan());
    }

    private void xuLyThemTaiKhoan() {
        // lay du lieu tu dialog
        int indexNV = cbNVBox.getSelectedIndex();
        String tenNV = dsNhanVien.get(indexNV).getTenNV();
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword()).trim();
        int indexNQ = cbQuyen.getSelectedIndex();
        String maNQ = dsNhomQuyen.get(indexNQ).getMaNQ();

        // xu ly them vao database
        if (indexNQ == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn nhóm quyền!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (indexNV == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn Nhân viên!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập đầy đủ Username và Password!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (taiKhoanBUS.kiemTraUsernameTonTai(user)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Username đã tồn tại!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        NhomQuyen nhomQuyen = new NhomQuyen();
        nhomQuyen.setMaNQ(maNQ);
        TaiKhoan tk = new TaiKhoan("", tenNV, user, pass, nhomQuyen, "");
        if (taiKhoanBUS.themTaiKhoan(tk)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Thêm tài khoản thành công!");
            taiKhoanUI.hienThiDanhSachTaiKhoan();
            dispose();
            return;
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Thêm tài khoản thất bại!",
                    "Thông báo",
                    JOptionPane.ERROR_MESSAGE);
        }

    }
}
