package ui.banhang;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class NutSuKienBanHang extends DefaultCellEditor {
    private JButton btn;
    private int type;
    private JTable table;

    public NutSuKienBanHang(JCheckBox checkBox) {
        super(checkBox);
        btn = new JButton();
        btn.setOpaque(true);


        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table == null) return;


                fireEditingStopped();

                int row = table.getSelectedRow();

                if (row == -1) return;

                DefaultTableModel model = (DefaultTableModel) table.getModel();

                int soLuong = Integer.parseInt(model.getValueAt(row, 2).toString());
                double donGia = Double.parseDouble(model.getValueAt(row, 1).toString());


                if (type == 1) {
                    if (soLuong > 1) {
                        soLuong--;
                        capNhatHang(model, row, soLuong, donGia);
                    } else {
                        int confirm = JOptionPane.showConfirmDialog(
                                null,
                                "Bạn có chắc chắn muốn xóa sản phẩm này khỏi hóa đơn?",
                                "Xác nhận xóa",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE
                        );

                        if (confirm == JOptionPane.YES_OPTION) {
                            model.removeRow(row);
                        }
                    }
                } else if (type == 2) { // Nút Cộng
                    soLuong++;
                    capNhatHang(model, row, soLuong, donGia);
                } else if(type == 3) {
                    xacNhanXoa(model, row);
                }

            }
        });
    }

    private void capNhatHang(DefaultTableModel model, int row, int soLuongMoi, double donGia) {
        model.setValueAt(soLuongMoi, row, 2);
        model.setValueAt(donGia * soLuongMoi, row, 3);
    }

    private void xacNhanXoa(DefaultTableModel model, int row) {
        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Bạn có chắc chắn muốn xóa sản phẩm này khỏi hóa đơn?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            model.removeRow(row);
        }
    }

    public void setLoaiNut(String path, int type) {
        this.type = type;
        // Dùng getResource để lấy ảnh từ classpath an toàn hơn
        if (getClass().getResource(path) != null) {
            btn.setIcon(new ImageIcon(getClass().getResource(path)));
        } else {
            if (type == 1) btn.setText("-");
            else if (type == 2) btn.setText("+");
            else if (type == 3) btn.setText("X");
        }
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.table = table;
        return btn;
    }
}