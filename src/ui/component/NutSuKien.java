package ui.component;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import bus.QuanLySanPhamBUS;
import dto.SanPham;
import ui.quanlysanpham.ChiTietSanPhamDialog;
import util.TaoUI;

import java.awt.Component;
import java.net.URL;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class NutSuKien extends DefaultCellEditor {
    protected JButton button;
    private int currentRow;
    private JTable currentTable;

    public NutSuKien(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();

        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);

        button.addActionListener(e -> {
            fireEditingStopped();

            QuanLySanPhamBUS quanLySanPhamBUS = new QuanLySanPhamBUS();
            SanPham sanPham = quanLySanPhamBUS
                    .timSanPham(String.valueOf(currentTable.getModel().getValueAt(currentRow, 2)));
            if (sanPham != null) {
                new ChiTietSanPhamDialog(sanPham);
            } else {
                JOptionPane.showMessageDialog(null, "Bruhh");
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.currentRow = row;
        this.currentTable = table;
        URL url = TaoUI.class.getResource("../assets/icon/sua.svg");
        FlatSVGIcon icon = new FlatSVGIcon(url).derive(30, 30);
        button.setIcon(icon);
        button.setBackground(table.getSelectionBackground());
        button.setOpaque(true);
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }

}