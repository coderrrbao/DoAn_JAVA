package util;

import java.awt.*;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class RenderColor extends DefaultTableCellRenderer {
    private int columnCheck;
    private int warnning;
    private Color colorWarning;

    public RenderColor(int columnCheck, int warningValue, Color colorWarning) {
        this.columnCheck = columnCheck;
        this.warnning = warningValue;
        this.colorWarning = colorWarning;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {
        Component c = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);
        int soLuong = Integer.parseInt(table.getValueAt(row, columnCheck).toString());

        if (soLuong <= warnning) {
            c.setBackground(colorWarning);
        } else {
            c.setBackground(Color.WHITE);
        }
        return c;
    }
}
