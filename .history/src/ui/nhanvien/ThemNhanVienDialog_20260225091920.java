package ui.nhanvien;

import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import com.toedter.calendar.JDateChooser;

import dto.NhanVien;
import dto.NhomQuyen;
import util.TaoUI;

public class ThemNhanVienDialog extends JDialog {
    private JTextField txtName;
    private JComboBox<String> cbChucVu;
    private JTextField txtPhone;
    private JComboBox<String> cbGioiTinh;
    private JTextField txtAddress;
    private JTextField txtAccount;
    private JDateChooser date;
    private ArrayList<NhanVien> dsnv;
    private NhanVienUI nvUI;

    public ThemNhanVienDialog(JFrame jFrame, NhanVienUI nvUI) {
        super(jFrame, "Thêm nhân viên", true);
        initUI();
        this.nvUI = nvUI;
        setSize(400, 300);
        setLocationRelativeTo(jFrame);
        setResizable(false);
    }

    public void initUI() {
        // PANEL CHÍNH
        JPanel mainPanel = TaoUI.taoPanelBoxLayoutDoc(400, 180);
        TaoUI.suaBorderChoPanel(mainPanel, 15, 15, 15, 15);

        // NAME
        txtName = new JTextField();
        JPanel nameField = TaoUI.taoFieldText("Name", 100, 220, 30, 10, txtName);
        // PHONE
        txtPhone = new JTextField();
        JPanel phoneField = TaoUI.taoFieldText("Phone", 100, 220, 30, 10, txtPhone);
        // ADDRESS
        txtAddress = new JTextField();
        JPanel addressField = TaoUI.taoFieldText("Name", 100, 220, 30, 10, txtAddress);
        // GIOI TINH
        JPanel cbGTJPanel = new JPanel();
        cbGTJPanel.setLayout(new BoxLayout(cbGTJPanel, BoxLayout.X_AXIS));
        TaoUI.setFixSize(cbGTJPanel, 330, 30);
        cbGioiTinh = new JComboBox<>();
        ArrayList<String> dsGioiTinh = new ArrayList<>();
        JLabel cbGTLabel = new JLabel("Giới tính");
        cbGTLabel.setPreferredSize(new Dimension(110, 30));
        cbGTLabel.setMinimumSize(new Dimension(110, 30));
        cbGTLabel.setMaximumSize(new Dimension(110, 30));
        dsGioiTinh.add("Nam");
        dsGioiTinh.add("Nữ");

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

        dsNhomQuyen = nhomQuyenBUS.layDanhSachNhomQuyen_BUS();
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

        btnThem.addActionListener(e -> xuLyThemNhanVien());
    }

    public void xuLyThemNhanVien() {

    }
}
