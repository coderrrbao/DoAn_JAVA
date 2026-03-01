package ui.nhanvien;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import java.awt.*;

public class IconButtonRender extends JButton implements TableCellRenderer {
    public IconButtonRender{
        setIcon(new ImageIcon("src/"));
    }
}
