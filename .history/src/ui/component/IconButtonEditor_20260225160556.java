package ui.component;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import java.awt.Component;
import java.awt.Cursor;
import java.net.URL;
import java.util.function.Consumer;

public class IconButtonEditor extends DefaultCellEditor {
    private JButton button;
    private int row;
    private boolean clicked;
    private Consumer<Integer> action;

    public IconButtonEditor(JCheckBox checkBox) {
        
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
