package ui.main;

import javax.swing.JFrame;

import ui.login.LoginUI;

import java.awt.*;

public class MainFrame extends JFrame {
    private TopPaner topPaner;
    private ContentPaner contentPaner;

    public MainFrame(LoginUI loginUI) {
        setSize(1400, 800);
        setTitle("Quản lý cửa hàng nước giải khát");
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setBackground(Color.white);
        contentPaner = new ContentPaner(this);
        topPaner = new TopPaner();

        add(topPaner, BorderLayout.NORTH);
        add(contentPaner, BorderLayout.CENTER);
        CardLayout cardLayout = (CardLayout) contentPaner.getLayout();
        cardLayout.show(contentPaner, "Quản lý sản phẩm");
        MenuPanel menuPanel = new MenuPanel(cardLayout, contentPaner, loginUI);
        add(menuPanel, BorderLayout.WEST);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false);
    }

    public TopPaner getTopPaner() {
        return topPaner;
    }

    public ContentPaner getContentPaner() {
        return contentPaner;
    }
}