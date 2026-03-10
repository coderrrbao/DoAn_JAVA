package ui.main;

import javax.swing.JFrame;

import java.awt.*;

public class MainFrame extends JFrame {
    private TopPaner topPaner;
    private ContentPaner contentPaner;
    private MenuPanel menuPanel;

    public MainFrame() {
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
        menuPanel = new MenuPanel(cardLayout, contentPaner);
        add(menuPanel, BorderLayout.WEST);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false);
    }

    public TopPaner getTopPaner() {
        return topPaner;
    }

    public MenuPanel getMenuPanel() {
        return menuPanel;
    }

    public ContentPaner getContentPaner() {
        return contentPaner;
    }

    public void loadAllData() {
        contentPaner.loadAllData();
    }

    public void chonItemBanDau() {
        menuPanel.chonItemBanDau();
    }
}