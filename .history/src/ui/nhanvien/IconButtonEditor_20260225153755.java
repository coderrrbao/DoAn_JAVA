package ui.nhanvien;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import java.awt.*;

public class IconButtonEditor {
    private JButton button;
    private int row;
    private boolean clicked;

    public SvgButtonEditor(JCheckBox checkBox) {
        super(checkBox);

        button = new JButton(new FlatSVGIcon("icons/delete.svg", 18, 18));
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
        }
        clicked = false;
        return null;
    }
}
