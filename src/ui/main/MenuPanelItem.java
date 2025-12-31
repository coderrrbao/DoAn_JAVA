package ui.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuPanelItem extends JPanel {
    private final Color normalBg = Color.WHITE; 
    private final Color hoverBg = new Color(220, 220, 220);

    public MenuPanelItem(String text, CardLayout cardLayout, JPanel cardPanel) {
        // ép kích thước cố định
        Dimension size = new Dimension(200, 50);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);

        // giữ alignment để BoxLayout không kéo giãn thêm
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setPreferredSize(new Dimension(200, 50));
        setMaximumSize(new Dimension(200, 50));
        setMinimumSize(new Dimension(200, 50));

        // panel phải opaque để vẽ nền
        setOpaque(true);
        setBackground(normalBg);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220, 220, 220)));

        JLabel jLabel = new JLabel(text);
        jLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        jLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        jLabel.setForeground(new Color(60, 60, 60));
        
        add(jLabel, BorderLayout.CENTER);

        // sự kiện hover + click
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverBg);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(normalBg);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(cardPanel, text);
            }
        });

        // hiển thị con trỏ tay khi hover
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}