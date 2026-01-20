package ui.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import util.TaoUI;

public class Search_Item extends JPanel {
    private JTextField searchText;
    private JButton searchButton;
    private Runnable event = () -> {
    };

    public Search_Item(int width, int height) {
        setBackground(Color.white);

        setLayout(new BorderLayout(0, 0));
        TaoUI.suaBorderChoPanel(this, 3, 3, 0, 3);
        searchText = new JTextField();
        TaoUI.taoPanelBorderLayout(this, width, height);

        searchButton = TaoUI.taoJButton_Svg("../assets/icon/search.svg", height, height);

        add(searchText, BorderLayout.CENTER);
        add(searchButton, BorderLayout.EAST);

        searchButton.addActionListener(e -> {
            sukien();
        });
        setBorder(BorderFactory.createLineBorder(null, 1));
        searchText.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0)));
    }

    public String getTextSearch() {
        return searchText.getText();
    }

    private void sukien() {
        event.run();
    }

    public void setEvent(Runnable event) {
        this.event = event;
    }

}
