package ui.login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import util.TaoUI;

public class loginUI extends JFrame{
    private JTextField txtuser;
    private JPasswordField txtpass;

    public loginUI(){
        setSize(700,400);
        setTitle("Đăng nhập");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initUI();
        
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initUI(){
        setLayout(new BorderLayout());

        //CENTER
        JPanel centerPanel = TaoUI.taoPanelBoxLayoutDoc(400,400);
        centerPanel = TaoUI.suaBorderChoPanel(centerPanel, 15, 15, 15, 15);
        //text
        JLabel thongbaoField = new JLabel("Đăng nhập để tiếp tục");
        thongbaoField.setHorizontalAlignment(JLabel.CENTER);
        thongbaoField.setAlignmentX(JComponent.CENTER_ALIGNMENT);//
        TaoUI.setFixSize(thongbaoField, 400, 40);
        thongbaoField.setFont(new Font("Segoe UI", Font.BOLD, 18));
        //user 
        txtuser = new JTextField();
        JPanel userJPanel = TaoUI.taoFieldText("Tên Đăng Nhập",90, 230, 30, 10, txtuser);
        
        //pass
        txtpass = new JPasswordField();
        JPanel passJPanel = TaoUI.taoFieldText("Mật Khẩu", 90, 230, 30, 10, txtpass);

        //quen mat khau
        JPanel quenMKPanel = new JPanel();
        JLabel quenMKJLabel = new JLabel("Quên mật khẩu?");
        quenMKPanel.setLayout(new BoxLayout(quenMKPanel, BoxLayout.X_AXIS));
        quenMKPanel.setMaximumSize(new Dimension(320, 25));
        quenMKPanel.add(javax.swing.Box.createHorizontalGlue());
        quenMKPanel.add(quenMKJLabel);
        
        //button
        JPanel buttonPanel = TaoUI.taoPanelCanGiua(330, 30);
        
        JButton btnThem = new JButton("Đăng nhập");
        JButton btnHuy = new JButton("Thoát");

        TaoUI.addItem(buttonPanel, btnThem, 10, true);
        TaoUI.addItem(buttonPanel, btnHuy, 10, true);
        //add vao centerpanel
        centerPanel.add(javax.swing.Box.createVerticalStrut(30));
        centerPanel.add(thongbaoField);
        centerPanel.add(javax.swing.Box.createVerticalStrut(30));
        centerPanel.add(userJPanel);
        centerPanel.add(javax.swing.Box.createVerticalStrut(10));
        centerPanel.add(passJPanel);
        centerPanel.add(javax.swing.Box.createVerticalStrut(10));
        centerPanel.add(quenMKPanel);
        centerPanel.add(javax.swing.Box.createVerticalStrut(30));
        centerPanel.add(buttonPanel);

        //left panel
        JPanel leftJPanel = TaoUI.taoPanelBoxLayoutDoc(300, 400);
        JLabel anh = TaoUI.taoJlabelAnh("/assets/img/login.png",300,400);
        leftJPanel.add(anh);
        getContentPane().setBackground(new Color(245, 247, 250));
        //add vao frame chinh
        add(centerPanel, BorderLayout.CENTER);
        add(leftJPanel, BorderLayout.WEST);
    }   

}
