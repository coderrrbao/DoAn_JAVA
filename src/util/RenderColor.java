package util;

import java.awt.*;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class RenderColor extends DefaultTableCellRenderer {
    private int columnSoLuong;
    private int columnMucCanhBao;
    private int columnLoHetHan;
    private Color colorWarning;

    public RenderColor(int columnSoLuong, int columnMucCanhBao, Color colorWarning) {
        this(columnSoLuong, columnMucCanhBao, -1, colorWarning);
    }

    public RenderColor(int columnSoLuong, int columnMucCanhBao, int columnLoHetHan, Color colorWarning) {
        this.columnSoLuong = columnSoLuong;
        this.columnMucCanhBao = columnMucCanhBao;
        this.columnLoHetHan = columnLoHetHan;
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
            c.setForeground(table.getSelectionForeground());
            return c;
        }

        // reset màu mặc định
        c.setBackground(Color.WHITE);
        c.setForeground(table.getForeground());

        try {
            int modelRow = table.convertRowIndexToModel(row);
            double soLuong = parseNumberAsDouble(table.getModel().getValueAt(modelRow, columnSoLuong));
            double mucCanhBao = parseNumberAsDouble(table.getModel().getValueAt(modelRow, columnMucCanhBao));

            // chỉ cảnh báo khi đã đặt mức cảnh báo (>0) và tồn kho < mức cảnh báo
            if (column == columnSoLuong && mucCanhBao > 0 && soLuong < mucCanhBao) {
                c.setForeground(colorWarning);
            }

            if (columnLoHetHan >= 0 && column == columnLoHetHan) {
                double loHetHan = parseNumberAsDouble(table.getModel().getValueAt(modelRow, columnLoHetHan));
                if (loHetHan > 0) {
                    c.setForeground(colorWarning);
                }
            }
        } catch (Exception e) {
        }

        return c;
    }

    private double parseNumberAsDouble(Object val) {
        if (val == null)
            return 0;
        String s = val.toString().trim().replace(",", "");
        if (s.isEmpty())
            return 0;
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
