package ui.nhanvien;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class IconButtonRender extends JButton implements TableCellRenderer {
    public IconButtonRender(String iconPath) {
        setIcon(new FlatSVGIcon("src/assets/icon/sua.svg", 18, 18));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public Component getTableCellRendererComponent(
        JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        return this;
    }
}
