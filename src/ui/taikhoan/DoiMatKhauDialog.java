 package ui.taikhoan;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import util.TaoUI;

public class DoiMatKhauDialog extends JDialog{
    JPasswordField txtNewPass;

    public DoiMatKhauDialog(JFrame jFrame, TaiKhoanUI taiKhoanUI){
        super(jFrame,"Đổi mật khẩu", true);

        DoiMatKhauInit();

        setSize(400, 300);
        setLocationRelativeTo(jFrame);
        setResizable(false);
    }
    private void DoiMatKhauInit(){
       
    }
    public static void main(String[] args) {
        DoiMatKhauDialog doi = new DoiMatKhauDialog(null, null);
        doi.setVisible(true);
    }
}