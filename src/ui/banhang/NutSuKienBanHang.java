package ui.banhang;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import util.TaoUI;

import java.awt.Component;
import java.net.URL;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class NutSuKienBanHang extends DefaultCellEditor {
    protected JButton button;
    private int currentRow;
    private JTable currentTable;
    private int loaiNut;

    public NutSuKienBanHang(JCheckBox checkBox) {
        super(checkBox);

        button = new JButton();

        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);

        button.addActionListener(e -> {
            fireEditingStopped();
            int soLuong = Integer.parseInt(String.valueOf(currentTable.getModel().getValueAt(currentRow, 2)));
       
            if (loaiNut == 1) {// trừ
                if (soLuong > 1) {
                    soLuong--;
                }
                currentTable.getModel().setValueAt(soLuong, currentRow, 2);
            } else { // cộng
                soLuong++;
                currentTable.getModel().setValueAt(soLuong, currentRow, 2);
            }
        });
    }

    public void setLoaiNut(String url, int loai) {
        loaiNut = loai;
        URL urL = TaoUI.class.getResource(url);
        FlatSVGIcon icon = new FlatSVGIcon(urL).derive(15, 15);
        button.setIcon(icon);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.currentRow = row;
        this.currentTable = table;

        button.setBackground(table.getSelectionBackground());
        button.setOpaque(true);
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }

}