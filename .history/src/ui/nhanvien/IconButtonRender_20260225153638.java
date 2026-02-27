package ui.nhanvien;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import java.awt.*;

public class IconButtonRender extends JButton implements TableCellRenderer {
    public IconButtonRender{
        setIcon(new FlatSVGIcon("src/assets/icon/sua.svg", 18, 18));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public component
}
