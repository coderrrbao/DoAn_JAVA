package ui.component;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class IconButtonEditor extends DefaultCellEditor {

    private JButton button;
    private int row;
    private boolean clicked;
    private Consumer<Integer> action;

    public IconButtonEditor(String iconPath, Consumer<Integer> action) {
        super(new JCheckBox());
        this.action = action;

        var url = getClass().getResource(iconPath);
        if (url == null) {
            throw new RuntimeException("Không tìm thấy icon: " + iconPath);
        }

        button = new JButton(new FlatSVGIcon(url).derive(18, 18));
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

        this.row = table.convertRowIndexToModel(row);
        clicked = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (clicked && action != null) {
            action.accept(row);
        }
        clicked = false;
        return null;
    }
}