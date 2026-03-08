package util;

import java.awt.*;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class RenderColor extends DefaultTableCellRenderer {
    private int columnSoLuong;
    private int columnMucCanhBao;
    private int columnLoHetHan; // -1 nếu không dùng
    private Color colorWarning;

    /** Cột Số lượng, Mức cảnh báo; tô đỏ khi Số lượng <= Mức cảnh báo. */
    public RenderColor(int columnSoLuong, int columnMucCanhBao, Color colorWarning) {
        this(columnSoLuong, columnMucCanhBao, -1, colorWarning);
    }

    /** Thêm cột Lô hết hạn: tô đỏ khi Lô hết hạn > 0. */
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
            return c;
        }

        c.setBackground(Color.WHITE);
        try {
            int modelRow = table.convertRowIndexToModel(row);
            int soLuong = parseNumber(table.getModel().getValueAt(modelRow, columnSoLuong));
            int mucCanhBao = parseNumber(table.getModel().getValueAt(modelRow, columnMucCanhBao));
            if (column == columnSoLuong && soLuong <= mucCanhBao) {
                c.setBackground(colorWarning);
            }
            if (columnLoHetHan >= 0 && column == columnLoHetHan) {
                int loHetHan = parseNumber(table.getModel().getValueAt(modelRow, columnLoHetHan));
                if (loHetHan > 0) {
                    c.setBackground(colorWarning);
                }
            }
        } catch (Exception e) {
            // giữ nền trắng
        }

        return c;
    }

    private int parseNumber(Object val) {
        if (val == null) return 0;
        String s = val.toString().trim().replace(",", "");
        if (s.isEmpty()) return 0;
        try {
            if (s.contains(".")) return (int) Double.parseDouble(s);
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
