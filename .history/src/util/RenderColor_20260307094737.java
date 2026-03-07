package util;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.component.Component;

public class RenderColor extends DefaultTableCellRenderer {
    private int columnCheck;
    private int warnning;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        Component c = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);
    }
}
