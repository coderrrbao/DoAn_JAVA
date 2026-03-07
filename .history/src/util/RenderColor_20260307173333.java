package util;

import java.awt.*;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class RenderColor extends DefaultTableCellRenderer {
    private int columnCheck;
    private int columnWarning;
    private int columnExpired;
    private Color colorWarning;

    public RenderColor(int columnCheck, int columnWarning, int columnExpired, Color colorWarning) {
        this.columnCheck = columnCheck;
        this.columnWarning = columnWarning;
        this.columnExpired = columnExpired;
        this.colorWarning = colorWarning;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {

        Component c = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);

        if (isSelected) {
            c.setBackground(table.getSelectionBackground());
            return c;
        }

        try {
            int modelRow = table.convertRowIndexToModel(row);

            int soLuong = Integer.parseInt(table.getModel().getValueAt(modelRow, columnCheck).toString());
            int mucCanhBao = Integer.parseInt(table.getModel().getValueAt(modelRow, columnWarning).toString());
            int loHetHan = Integer.parseInt(table.getModel().getValueAt(modelRow, columnExpired).toString());

            if (soLuong <= mucCanhBao || loHetHan > 0) {
                c.setBackground(colorWarning);
            } else {
                c.setBackground(Color.WHITE);
            }

        } catch (Exception e) {
            c.setBackground(Color.WHITE);
        }

        return c;
    }
}
