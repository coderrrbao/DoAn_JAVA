package ui.component;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;
import java.awt.Cursor;
import java.net.URL;

public class IconButtonRender extends JButton implements TableCellRenderer {
    public IconButtonRender(String iconPath) {
        URL url = getClass().getResource(iconPath);
        if (url != null) {
            setIcon(new FlatSVGIcon(url).derive(18, 18));
        } else {
            System.err.println("Không tìm thấy icon: " + iconPath);
            setText("...");
        }
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        if (isSelected) {
            setBackground(table.getSelectionBackground());
        } else {
            setBackground(table.getBackground());
        }
        return this;
    }
}
