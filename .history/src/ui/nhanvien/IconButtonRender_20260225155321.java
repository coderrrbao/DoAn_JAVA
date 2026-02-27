package ui.nhanvien;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;
import java.awt.Cursor;
import java.net.URL;

import com.formdev.flatlaf.extras.FlatSVGIcon;

public class IconButtonRender extends JButton implements TableCellRenderer {
    public IconButtonRender() {
        setIcon(new FlatSVGIcon("src/assets/icon/sua.svg", 18, 18));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public Component getTableCellRenderComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        return this;
    }
}
