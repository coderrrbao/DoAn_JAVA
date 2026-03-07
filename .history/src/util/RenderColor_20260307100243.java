package util;

import java.awt.*;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class RenderColor extends DefaultTableCellRenderer {
    private int columnCheck;
    private int columWarning;
    private Color colorWarning;

    public RenderColor(int columnCheck, Integer columWarning, Color colorWarning) {
        this.columnCheck = columnCheck;
        this.columWarning = columWarning;
        this.colorWarning = colorWarning;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {
        Component c = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);
        int soLuong = Integer.parseInt(table.getValueAt(row, columnCheck).toString());
        int mucCanhBao = columWarning == null ? 0 :Integer.parseInt(table.getValueAt(row, columWarning).toString());

        if (!isSelected) {
            if (soLuong <= mucCanhBao) {
                c.setBackground(colorWarning);
            } else {
                c.setBackground(Color.WHITE);
            }
        }
        return c;
    }
}
