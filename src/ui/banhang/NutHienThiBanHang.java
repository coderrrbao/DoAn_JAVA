package ui.banhang;


import com.formdev.flatlaf.extras.FlatSVGIcon;

import util.TaoUI;

import java.awt.Component;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class NutHienThiBanHang extends JButton implements TableCellRenderer {
    private String urlString;
    public NutHienThiBanHang(String url) {
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
        this.urlString = url;
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        URL url = TaoUI.class.getResource(urlString);
        FlatSVGIcon icon = new FlatSVGIcon(url).derive(15, 15);
        setIcon(icon);
        setText("");
        if (isSelected) {
            setBackground(table.getSelectionBackground());
        } else {
            setBackground(table.getBackground());
        }

        return this;
    }
}