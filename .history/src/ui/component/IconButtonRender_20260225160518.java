package ui.component;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class IconButtonRender extends JButton implements TableCellRenderer {
    public IconButtonRender(String iconPath) {
        setIcon(new FlatSVGIcon(getClass().getResource(iconPath))));
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        return this;
    }
}
