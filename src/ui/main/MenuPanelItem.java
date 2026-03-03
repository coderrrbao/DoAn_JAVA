package ui.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MenuPanelItem extends JPanel {
    private final Color normalBg = Color.WHITE;
    private final Color hoverBg = new Color(220, 220, 220);
    private ArrayList<MenuPanelItem> listMenuPanelItems;
    private boolean daChon = false;
    private String text;

    public MenuPanelItem(String text, CardLayout cardLayout, JPanel cardPanel,
            ArrayList<MenuPanelItem> lisMenuPanelItems) {
        this(text, () -> {
            cardLayout.show(cardPanel, text);

        });
        this.listMenuPanelItems = lisMenuPanelItems;
        this.text = text;
    }

    public MenuPanelItem(String text, Runnable customAction) {
        initUI(text);
        this.text = text;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (daChon) {
                    return;
                }
                setBackground(hoverBg);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!daChon) {
                    setBackground(normalBg);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (customAction != null) {
                    customAction.run();
                    if (getText().equals("Thông tin")
                            || getText().equals("Đăng xuất")) {
                        return;
                    }
                    daChon = true;
                    for (MenuPanelItem menuPanelItem : listMenuPanelItems) {
                        if (!menuPanelItem.getText().equals(getText())) {

                            menuPanelItem.setBackground(normalBg);
                            menuPanelItem.setDaChon(false);
                        }
                    }
                    setBackground(hoverBg);
                }
            }
        });
    }

    private void initUI(String text) {
        Dimension size = new Dimension(200, 35);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setOpaque(true);
        setBackground(normalBg);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220, 220, 220)));

        JLabel jLabel = new JLabel(text);
        jLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        jLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        jLabel.setForeground(new Color(60, 60, 60));
        add(jLabel, BorderLayout.CENTER);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public String getText() {
        return text;
    }

    public void setDaChon(boolean daChon) {
        this.daChon = daChon;
    }
}