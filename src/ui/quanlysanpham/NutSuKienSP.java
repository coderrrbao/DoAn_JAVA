package ui.quanlysanpham;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import bus.SanPhamBUS;
import dto.SanPham;
import util.TaoUI;

import java.awt.Component;
import java.net.URL;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class NutSuKienSP extends DefaultCellEditor {
    protected JButton button;
    private int currentRow;
    private JTable currentTable;

    public NutSuKienSP(JCheckBox checkBox, QuanLySanPhamUI quanLySanPhamUI) {
        super(checkBox);
        button = new JButton();

        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);

        button.addActionListener(e -> {
            fireEditingStopped();

            SanPhamBUS quanLySanPhamBUS = new SanPhamBUS();
            SanPham sanPham = quanLySanPhamBUS
                    .timSanPham(String.valueOf(currentTable.getModel().getValueAt(currentRow, 2)));
            if (sanPham != null) {
                quanLySanPhamUI.layXemChiTietSanPhamDialog().capNhapDuLieu(sanPham);
                quanLySanPhamUI.layXemChiTietSanPhamDialog().setVisible(true);
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