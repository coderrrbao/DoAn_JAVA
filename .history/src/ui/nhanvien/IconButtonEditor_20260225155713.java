package ui.nhanvien;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import java.awt.Component;
import java.awt.Cursor;
import java.net.URL;

public class IconButtonEditor extends Defa {
    private final JButton button;
    private int row;
    private boolean clicked;

    public IconButtonEditor(JCheckBox checkBox) {
        URL url = getClass().getResource("/assets/icon/delete.svg");
        if (url != null) {
            button = new JButton(new FlatSVGIcon(url, 18, 18));
        } else {
            button = new JButton("X");
        }
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addActionListener(e -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected,
            int row, int column) {

        this.row = row;
        clicked = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (clicked) {
            System.out.println("Click delete row: " + row);
            // TODO: xử lý xóa nhân viên ở hàng `row` nếu bạn muốn
        }
        clicked = false;
        return null;
    }
}
