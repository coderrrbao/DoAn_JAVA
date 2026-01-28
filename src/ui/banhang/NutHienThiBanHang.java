package ui.banhang;


import com.formdev.flatlaf.extras.FlatSVGIcon;

import util.TaoUI;

import java.awt.Component;
import java.net.URL;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class NutHienThiBanHang extends JButton implements TableCellRenderer {
    private String urlString;
    private int width;
    private int height;

    public NutHienThiBanHang(String url, int w, int h) {
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);

        setMargin(new Insets(0, 0, 0, 0));

        this.urlString = url;
        this.width = w;
        this.height = h;

    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        URL url = TaoUI.class.getResource(urlString);

        if (url != null) {
            FlatSVGIcon icon = new FlatSVGIcon(url).derive(width, height);
            setIcon(icon);
        } else {
            setText("X");
        }

        if (isSelected) {
            setBackground(table.getSelectionBackground());
        } else {
            setBackground(table.getBackground());
        }

        return this;
    }
}