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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ui.login.LoginUI;
import ui.login.PhienDangNhap;
import ui.thongtinuser.ThongTinDialog;

public class MenuPanel extends JPanel {
  private ArrayList<MenuPanelItem> menuItems;

  public MenuPanel(CardLayout cardLayout, JPanel cardPanel) {
    menuItems = new ArrayList<>();
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
    add(Box.createVerticalGlue());
    addMenuItem(
        "Thông tin",
        () -> {
          NhanVien nv = new NhanVien();
          JDialog thongTin = new ThongTinDialog(null, nv);
          thongTin.setVisible(true);
        });
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

  public void suaLaiGiaoDienTheoQuyen() {
    var listQuyen = PhienDangNhap.getListQuyen();

    for (int i = menuItems.size() - 1; i >= 0; i--) {
      MenuPanelItem item = menuItems.get(i);
      String title = item.getText();

      boolean coQuyen = true;

      switch (title) {
        case "Quản lý sản phẩm":
          if (!listQuyen.contains("QLSP_XEM"))
            coQuyen = false;
          break;
        case "Nguyên liệu":
          if (!listQuyen.contains("NL_XEM"))
            coQuyen = false;
          break;
        case "Nhà cung cấp":
          if (!listQuyen.contains("NCC_XEM"))
            coQuyen = false;
          break;
        case "Nhập kho":
          if (!listQuyen.contains("NK_XEM"))
            coQuyen = false;
          break;
        case "Tồn kho":
          if (!listQuyen.contains("TKHO_XEM"))
            coQuyen = false;
          break;
        case "Xuất kho":
          if (!listQuyen.contains("XK_XEM"))
            coQuyen = false;
          break;
        case "Kiểm kê":
          if (!listQuyen.contains("KK_XEM"))
            coQuyen = false;
          break;
        case "Bán hàng":
          if (!listQuyen.contains("HD_TAO"))
            coQuyen = false;
          break;
        case "Hóa đơn":
          if (!listQuyen.contains("HD_XEM"))
            coQuyen = false;
          break;
        case "Khách hàng":
          if (!listQuyen.contains("KH_XEM"))
            coQuyen = false;
          break;
        case "Hạng thành viên":
          if (!listQuyen.contains("HTV_XEM"))
            coQuyen = false;
          break;
        case "Nhân viên":
          if (!listQuyen.contains("NV_XEM"))
            coQuyen = false;
          break;
        case "Tài khoản":
          if (!listQuyen.contains("TK_XEM"))
            coQuyen = false;
          break;
        case "Phân quyền":
          if (!listQuyen.contains("PQ_XEM"))
            coQuyen = false;
          break;
        case "Thống kê":
          if (!listQuyen.contains("TKE_XEM"))
            coQuyen = false;
          break;
        case "Khuyến mãi":
          if (!listQuyen.contains("KM_XEM"))
            coQuyen = false;
          break;
      }

      if (!coQuyen) {
        this.remove(item);
        menuItems.remove(i);
      }
    }

    this.revalidate();
    this.repaint();
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
      LoginUI loginUI = LoginUI.getLoginUI();
      loginUI.lamMoi();
      // đóng MainFrame
      loginUI.getMainFrame().setVisible(false);
      // mở lại màn hình đăng nhập
      loginUI.setVisible(true);
    }
  }
}
