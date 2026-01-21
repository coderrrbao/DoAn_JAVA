package ui.main;

import javax.swing.JFrame;

import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setSize(1300, 800);
        setTitle("Quản lý cửa hàng nước giải khát");
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setBackground(Color.white);
        ContentPaner contentPaner = new ContentPaner(this);
        TopPaner topConTentPaner = new TopPaner();

        add(topConTentPaner, BorderLayout.NORTH);
        add(contentPaner, BorderLayout.CENTER);
        CardLayout cardLayout = (CardLayout) contentPaner.getLayout();
        cardLayout.show(contentPaner, "Quản lý sản phẩm");
        MenuPanel menuPanel = new MenuPanel(cardLayout, contentPaner,this);
        add(menuPanel, BorderLayout.WEST);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void loadDuLieu() {
        // code
    }
}