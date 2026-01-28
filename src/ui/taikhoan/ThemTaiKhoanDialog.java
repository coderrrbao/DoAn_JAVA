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

import bus.NhomQuyenBUS;
import bus.TaiKhoanBUS;
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
    private NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
    private TaiKhoanBUS taiKhoanBUS = new TaiKhoanBUS();
    private TaiKhoanUI taiKhoanUI;
    private ArrayList<NhomQuyen> dsNhomQuyen;


    public ThemTaiKhoanDialog(JFrame jFrame, TaiKhoanUI taiKhoanUI) {
        super(jFrame, "Thêm tài khoản", true);
        initUI();
        this.taiKhoanUI = taiKhoanUI;
        setSize(400, 300);
        setLocationRelativeTo(jFrame);
        setResizable(false);
    }

    private void initUI() {
        //PANEL CHÍNH
        JPanel mainPanel = TaoUI.taoPanelBoxLayoutDoc(400, 180);
        TaoUI.suaBorderChoPanel(mainPanel, 15, 15, 15, 15);

        //USER
        txtUser = new JTextField();
        JPanel userField = TaoUI.taoFieldText("Username",100,220,30,10,txtUser
        );
        // TÊN TÀI KHOẢN
        txtTenTaiKhoan = new JTextField();
        JPanel tenTKField = TaoUI.taoFieldText(
            "Tên tài khoản", 100, 220, 30, 10, txtTenTaiKhoan
        );
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
        
        // COMBOBOX
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
        mainPanel.add(tenTKField);
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
        String tenTK = txtTenTaiKhoan.getText();
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword()).trim();
        int indexNQ = cbQuyen.getSelectedIndex();
        String maNQ =  dsNhomQuyen.get(indexNQ).getMaNQ();
        
        // xu ly them vao database
        if (indexNQ == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Vui lòng chọn nhóm quyền!",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập đầy đủ Username và Password!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        TaiKhoan tk = new TaiKhoan(tenTK,user, pass, maNQ, true);
        if(taiKhoanBUS.themTaiKhoan_BUS(tk)){
            JOptionPane.showMessageDialog(
                this,
                "Thêm tài khoản thành công!"
                );
                taiKhoanUI.hienThiDanhSachTaiKhoan();
                dispose();
            return;
        }
        else{
        JOptionPane.showMessageDialog(
            this,
            "Thêm tài khoản thất bại!",
            "Thông báo",
            JOptionPane.ERROR_MESSAGE
            );
        }
    }
}

