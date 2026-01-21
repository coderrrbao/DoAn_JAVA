package ui.banhang;

import java.awt.Color;
import java.util.HashSet;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ui.quanlysanpham.NutHienThiSP;
import util.TaoUI;

public class ThongTinHoaDonPanel extends JPanel {
    public ThongTinHoaDonPanel() {
        TaoUI.taoPanelBoxLayoutDoc(this, Integer.MAX_VALUE, Integer.MAX_VALUE);
        JPanel title = TaoUI.taoPanelCanGiua(Integer.MAX_VALUE, 40);
        title.setBackground(new Color(225, 235, 245));
        title.add(new JLabel("Thông tin hóa đơn"));
        add(title);

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4 || column == 5;
            }

            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4 || columnIndex == 3)
                    return JButton.class;
                return Object.class;
            }
        };

        model.addColumn("Tên");
        model.addColumn("Giá");
        model.addColumn("SL");
        model.addColumn("Tổng tiền");
        model.addColumn("");
        model.addColumn("");

        model.addRow(new Object[] { "Cocacola", 10000, 2, 20000, new JButton(), new JButton() });
        model.addRow(new Object[] { "Pepsi", 10000, 2, 20000, new JButton(), new JButton() });

        HashSet<Integer> set = new HashSet<>();
        set.add(5);
        set.add(4);
        NutSuKienBanHang nutTru = new NutSuKienBanHang(new JCheckBox());
        nutTru.setLoaiNut("../assets/icon/tru.svg", 1);
        NutSuKienBanHang nutCong = new NutSuKienBanHang(new JCheckBox());
        nutCong.setLoaiNut("../assets/icon/cong.svg", 2);
        JScrollPane scrollPane = TaoUI.taoTableScroll(model, set);
        JTable table = (JTable) scrollPane.getViewport().getView();
        table.getColumnModel().getColumn(4).setCellRenderer(new NutHienThiBanHang("../assets/icon/tru.svg"));
        table.getColumnModel().getColumn(4).setCellEditor(nutTru);
        table.getColumnModel().getColumn(4).setPreferredWidth(15);

        table.getColumnModel().getColumn(5).setCellRenderer(new NutHienThiBanHang("../assets/icon/cong.svg"));
        table.getColumnModel().getColumn(5).setCellEditor(nutCong);
        table.getColumnModel().getColumn(5).setPreferredWidth(15);
        add(scrollPane);
        TaoUI.suaBorderChoPanel(this, 0, 0, 0, 10);
    }

}
