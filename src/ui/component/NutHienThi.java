package ui.component;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import util.TaoUI;

import java.awt.Component;
import java.net.URL;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class NutHienThi extends JButton implements TableCellRenderer {
    public NutHienThi() {
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        URL url = TaoUI.class.getResource("../assets/icon/sua.svg");
        FlatSVGIcon icon = new FlatSVGIcon(url).derive(30, 30);
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