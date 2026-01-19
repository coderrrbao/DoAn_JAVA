package ui.component;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;

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
