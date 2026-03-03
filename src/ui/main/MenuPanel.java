package ui.main;

import dto.NhanVien;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ui.login.LoginUI;
import ui.thongtinuser.ThongTinDialog;

public class MenuPanel extends JPanel {
  private ArrayList<MenuPanelItem> menuItems;
  private LoginUI loginUI;

  public MenuPanel(CardLayout cardLayout, JPanel cardPanel, LoginUI loginUI) {
    menuItems = new ArrayList<>();
    this.loginUI = loginUI;
    setPreferredSize(new Dimension(200, 700));
    setMaximumSize(new Dimension(200, Integer.MAX_VALUE));
    setMinimumSize(new Dimension(200, 700));
    setAlignmentX(Component.LEFT_ALIGNMENT);
    setOpaque(true);
    setBorder(BorderFactory.createEmptyBorder(0, 5, 10, 0));
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    add(Box.createVerticalStrut(2));
    addMenuItem("Quản lý sản phẩm", cardLayout, cardPanel);
    addMenuItem("Nguyên liệu", cardLayout, cardPanel);
    addMenuItem("Nhà cung cấp", cardLayout, cardPanel);
    addMenuItem("Nhập kho", cardLayout, cardPanel);
    addMenuItem("Tồn kho", cardLayout, cardPanel);
    addMenuItem("Xuất kho", cardLayout, cardPanel);
    addMenuItem("Kiểm kê", cardLayout, cardPanel);
    addMenuItem("Bán hàng", cardLayout, cardPanel);
    addMenuItem("Hóa đơn", cardLayout, cardPanel);
    addMenuItem("Khách hàng", cardLayout, cardPanel);
    addMenuItem("Hạng thành viên", cardLayout, cardPanel);
    addMenuItem("Nhân viên", cardLayout, cardPanel);
    addMenuItem("Tài khoản", cardLayout, cardPanel);
    addMenuItem("Phân quyền", cardLayout, cardPanel);
    addMenuItem("Thống kê", cardLayout, cardPanel);
    addMenuItem("Khuyến mãi", cardLayout, cardPanel);
    addMenuItem(
        "Thông tin",
        () -> {
          NhanVien nv = new NhanVien();
          JDialog thongTin = new ThongTinDialog(null, nv);
          thongTin.setVisible(true);
        });

    add(Box.createVerticalGlue());

    addMenuItem("Đăng xuất", () -> {
      dangXuat();
    });

  }

  private void addMenuItem(String title, CardLayout cardLayout, JPanel cardPanel) {
    MenuPanelItem item = new MenuPanelItem(title, cardLayout, cardPanel, menuItems);
    menuItems.add(item);
    if (title.equals("Quản lý sản phẩm")) {
      item.setMauChon();
    }
    add(item);
    add(Box.createVerticalStrut(2));
  }

  private void addMenuItem(String title, Runnable sukien) {
    MenuPanelItem item = new MenuPanelItem(title, sukien);
    menuItems.add(item);
    add(item);
    add(Box.createVerticalStrut(2));
  }

  public List<MenuPanelItem> getMenuItems() {
    return menuItems;
  }

  private void dangXuat() {
    int confirm = JOptionPane.showConfirmDialog(
        null,
        "Bạn có chắc muốn đăng xuất?",
        "Xác nhận",
        JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
      // xoa du lieu old
      loginUI.lamMoi();
      ;
      // đóng MainFrame
      loginUI.getMainFrame().setVisible(false);
      // mở lại màn hình đăng nhập
      loginUI.setVisible(true);
    }
  }
}
