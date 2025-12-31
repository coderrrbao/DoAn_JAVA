package ui.main;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {
    public MenuPanel(CardLayout cardLayout, JPanel cardPanel) {
        setPreferredSize(new Dimension(200, 700));
        setMaximumSize(new Dimension(200, Integer.MAX_VALUE));
        setMinimumSize(new Dimension(200, 700));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setOpaque(true);
        setBorder(BorderFactory.createEmptyBorder(0, 5, 10, 0));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalStrut(2));
        add(new MenuPanelItem("Quản lý sản phẩm", cardLayout, cardPanel));
        add(Box.createVerticalStrut(2));

        add(new MenuPanelItem("Nhà cung cấp", cardLayout, cardPanel));
        add(Box.createVerticalStrut(2));

        add(new MenuPanelItem("Nhập kho", cardLayout, cardPanel));
        add(Box.createVerticalStrut(2));

        add(new MenuPanelItem("Tồn kho", cardLayout, cardPanel));
        add(Box.createVerticalStrut(2));

        add(new MenuPanelItem("Bán hàng", cardLayout, cardPanel));
        add(Box.createVerticalStrut(2));

        add(new MenuPanelItem("Hóa đơn", cardLayout, cardPanel));
        add(Box.createVerticalStrut(2));

        add(new MenuPanelItem("Nhân viên", cardLayout, cardPanel));
        add(Box.createVerticalStrut(2));

        add(new MenuPanelItem("Thống kê", cardLayout, cardPanel));

        add(Box.createVerticalGlue());

        add(new MenuPanelItem("Đăng xuất", cardLayout, cardPanel));

    }
}
