package ui.nhanvien;

import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;
import java.

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

    public void initUI(){
                //PANEL CHÍNH
        JPanel mainPanel = TaoUI.taoPanelBoxLayoutDoc(400, 180);
        TaoUI.suaBorderChoPanel(mainPanel, 15, 15, 15, 15);

        //USER
        txtUser = new JTextField();
        JPanel userField = TaoUI.taoFieldText("Username",100,220,30,10,txtUser
        );
        // TÊN TÀI KHOẢN // conbobox nhan vien
        JPanel cbNVJPanel = new JPanel();
        cbNVJPanel.setLayout(new BoxLayout(cbNVJPanel, BoxLayout.X_AXIS));
        TaoUI.setFixSize(cbNVJPanel, 330, 30);

        JLabel cbNVJLabel = new JLabel("Nhân Viên");
        cbNVJLabel.setPreferredSize(new Dimension(110,30));
        cbNVJLabel.setMinimumSize(new Dimension(110,30));
        cbNVJLabel.setMaximumSize(new Dimension(110,30));

        dsNhanVien = taiKhoanBUS.layDanhSachNhanVien_BUS();
        cbNVBox = new JComboBox<>();
        for(NhanVien nv : dsNhanVien){
            cbNVBox.addItem(nv.getTenNV() + " (" + nv.getMaNV() + ")");
        }
        cbNVJPanel.add(cbNVJLabel);
        cbNVJPanel.add(cbNVBox);
        //PASSWORD
        txtPass = new JPasswordField();
        JPanel passField = TaoUI.taoFieldText("Password",100,220,30,10,txtPass
        );

        //BUTTON PANEL
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
        cbJLabel.setPreferredSize(new Dimension(110,30));
        cbJLabel.setMinimumSize(new Dimension(110,30));
        cbJLabel.setMaximumSize(new Dimension(110,30));

        dsNhomQuyen = nhomQuyenBUS.layDanhSachNhomQuyen_BUS();
        cbQuyen = new JComboBox<>();
        for (NhomQuyen nq : dsNhomQuyen) {
            cbQuyen.addItem(nq.getTenNhomQuyen());
        }
        
        cbJPanel.add(cbJLabel);
        cbJPanel.add(cbQuyen);
      
        //ADD COMPONENT
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

    public void xuLyThemNhanVien(){

    }
}
