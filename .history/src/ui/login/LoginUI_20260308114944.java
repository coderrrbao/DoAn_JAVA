package ui.login;

import bus.NhanVienBUS;
import bus.TaiKhoanBUS;
import dto.NhanVien;
import dto.Quyen;
import dto.TaiKhoan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import ui.main.MainFrame;
import util.TaoTinNhan;
import util.TaoUI;

public class LoginUI extends JFrame {

  private static LoginUI loginUI = null;
  private JTextField txtuser;
  private JPasswordField txtpass;
  private TaiKhoanBUS taiKhoanBUS = TaiKhoanBUS.getTaiKhoanBUS();
  private MainFrame mainFrame = new MainFrame();

  public LoginUI() {
    setSize(700, 400);
    setTitle("Đăng nhập");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    initUI(mainFrame);

    setLocationRelativeTo(null);

    setVisible(true);
    LoginUI.setLoginUI(this);
  }

  private void initUI(JFrame mainFrame) {
    setLayout(new BorderLayout());

    // CENTER
    ImageIcon icon = new ImageIcon(getClass().getResource("/assets/img/bglogin.jpg"));
    Image backgroundImage = icon.getImage();
    JPanel centerPanel = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        int imgWidth = backgroundImage.getWidth(this);
        int imgHeight = backgroundImage.getHeight(this);

        // Tính scale giữ nguyên tỉ lệ
        double scale = Math.max(
            (double) panelWidth / imgWidth,
            (double) panelHeight / imgHeight);

        int newWidth = (int) (imgWidth * scale);
        int newHeight = (int) (imgHeight * scale);

        // Căn giữa ảnh
        int x = (panelWidth - newWidth) / 2;
        int y = (panelHeight - newHeight) / 2;

        g.drawImage(backgroundImage, x, y, newWidth, newHeight, this);
      }
    };

    TaoUI.taoPanelBoxLayoutDoc(centerPanel, 400, 400);
    centerPanel = TaoUI.suaBorderChoPanel(centerPanel, 15, 15, 15, 15);
    // text
    JLabel thongbaoField = new JLabel("Đăng nhập để tiếp tục");
    thongbaoField.setHorizontalAlignment(JLabel.CENTER);
    thongbaoField.setAlignmentX(JComponent.CENTER_ALIGNMENT); //
    TaoUI.setFixSize(thongbaoField, 400, 40);
    thongbaoField.setFont(new Font("Segoe UI", Font.BOLD, 18));
    // user
    txtuser = new JTextField();
    JPanel userJPanel = TaoUI.taoFieldText("Tên Đăng Nhập", 90, 230, 30, 10, txtuser);

    // pass
    txtpass = new JPasswordField();
    JPanel passJPanel = TaoUI.taoFieldText("Mật Khẩu", 90, 230, 30, 10, txtpass);

    // button
    JPanel buttonPanel = TaoUI.taoPanelCanGiua(330, 30);

    JButton btnThem = new JButton("Đăng nhập");
    btnThem.addActionListener(e -> xuLyDangNhap());
    JButton btnHuy = new JButton("Thoát");
    btnHuy.addActionListener(e -> System.exit(0));

    TaoUI.addItem(buttonPanel, btnThem, 10, true);
    TaoUI.addItem(buttonPanel, btnHuy, 10, true);
    // add vao centerpanel
    centerPanel.add(javax.swing.Box.createVerticalStrut(30));
    centerPanel.add(thongbaoField);
    centerPanel.add(javax.swing.Box.createVerticalStrut(30));
    centerPanel.add(userJPanel);
    centerPanel.add(javax.swing.Box.createVerticalStrut(10));
    centerPanel.add(passJPanel);
    centerPanel.add(javax.swing.Box.createVerticalStrut(30));
    centerPanel.add(buttonPanel);

    // left panel
    JPanel leftJPanel = TaoUI.taoPanelBoxLayoutDoc(300, 400);
    JLabel anh = TaoUI.taoJlabelAnh("/assets/img/login.png", 300, 400);
    Color customBlue = new Color(31, 177, 190);
    leftJPanel.setBackground(customBlue);
    leftJPanel.add(anh);
    getContentPane().setBackground(new Color(245, 247, 250));
    // add vao frame chinh
    add(centerPanel, BorderLayout.CENTER);
    add(leftJPanel, BorderLayout.WEST);

    userJPanel.setOpaque(false);
    passJPanel.setOpaque(false);
    buttonPanel.setOpaque(false);
  }

  // logic dang nhap
  public void xuLyDangNhap() {
    // String user = txtuser.getText().trim();
    // String pass = new String(txtpass.getPassword()).trim();

    String user = "admin";
    String pass = "123456";

    // kiem tra du lieu lay tu form
    if (user.isEmpty()) {
      JOptionPane.showMessageDialog(
          this, "tài khoản không được rỗng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
      return;
    }
    if (pass.isEmpty()) {
      JOptionPane.showMessageDialog(
          this, "Mật khẩu không được rỗng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
      return;
    }
    // xu ly dang nhap
    TaiKhoan taiKhoan = taiKhoanBUS.dangNhap(user, pass);
    if (taiKhoan != null) {
      if (taiKhoan.getTrangThaiXuLy().equals("Đã khóa")) {
        JOptionPane.showMessageDialog(
            this, "Tài khoản đã bị khóa", "Thông báo", JOptionPane.ERROR_MESSAGE);
        return;
      }
      NhanVienBUS nhanVienBUS = NhanVienBUS.getNhanVienBUS();
      NhanVien nv = nhanVienBUS.timNhanVien(taiKhoan.getMaNV());
      
      PhienDangNhap.setUser(nv);
      PhienDangNhap.setTaiKhoan(taiKhoan);
      for (Quyen quyen : PhienDangNhap.getTaiKhoan().getNhomQuyen().getListQuyen()) {
        PhienDangNhap.themQuyen(quyen.getTenQuyen());
      }
      TaoTinNhan.showAutoCloseMessage("Đăng nhập thành công", "thông báo", 1);
      mainFrame.getTopPaner().capNhapThongTin(nv);
      mainFrame.getContentPaner().suaLaiGiaoDienTheoQuyen();
      mainFrame.getMenuPanel().suaLaiGiaoDienTheoQuyen();

      mainFrame.setVisible(true);
      this.dispose();

    } else {
      JOptionPane.showMessageDialog(
          this, "Tài khoản hoặc mật khẩu không chính xác", "Thông báo", JOptionPane.ERROR_MESSAGE);
    }
  }

  public MainFrame getMainFrame() {
    return mainFrame;
  }

  public void lamMoi() {
    txtpass.setText("");
    txtuser.setText("");
  }

  public static LoginUI getLoginUI() {
    return loginUI;
  }

  public static void setLoginUI(LoginUI loginUI) {
    LoginUI.loginUI = loginUI;
  }

  public static void main(String[] args) {
    LoginUI ui = new LoginUI();
    ui.setVisible(true);
  }
}
