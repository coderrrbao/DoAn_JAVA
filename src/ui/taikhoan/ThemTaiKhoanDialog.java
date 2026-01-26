package ui.taikhoan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import util.TaoUI;

public class ThemTaiKhoanDialog extends JDialog {

    private JTextField txtUser;
    private JPasswordField txtPass;
    private JComboBox<String> cbQuyen;

    public ThemTaiKhoanDialog(JFrame PFrame) {
        super(PFrame, "Thêm tài khoản", true);

        initUI();

        setSize(400, 240);
        setLocationRelativeTo(PFrame);
        setResizable(false);
    }

    private void initUI() {
        //PANEL CHÍNH
        JPanel mainPanel = TaoUI.taoPanelBoxLayoutDoc(380, 180);
        TaoUI.suaBorderChoPanel(mainPanel, 15, 15, 15, 15);

        //USER
        txtUser = new JTextField();
        JPanel userField = TaoUI.taoFieldText("Username",100,220,30,10,txtUser
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
        JComboBox cbBox = new JComboBox<>(new String[]{"Nhân viên kho" , "Nhân viên bán hàng", "Quản lí", "Admin"});
        cbJPanel.add(cbJLabel);
        cbJPanel.add(cbBox);
        
        //ADD COMPONENT
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
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword()).trim();
        String quyen = cbQuyen.getSelectedItem().toString();
        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập đầy đủ Username và Password!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

       
    }
}

