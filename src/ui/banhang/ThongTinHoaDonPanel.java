package ui.banhang;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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
                return false;
            }
        };

        model.addColumn("Tên");
        model.addColumn("Giá");
        model.addColumn("SL");
        model.addColumn("Tổng tiền");
        model.addColumn("");
        model.addColumn("");

        model.addRow(new Object[] { "Cocacola", 10000, 2, 20000, "+", "-" });
        model.addRow(new Object[] { "Pepsi", 10000, 2, 20000, "+", "-" });

        JTable table = new JTable(model);

        table.getColumnModel().getColumn(0).setPreferredWidth(200); 
        table.getColumnModel().getColumn(1).setPreferredWidth(80); 
        table.getColumnModel().getColumn(2).setPreferredWidth(40); 
        table.getColumnModel().getColumn(3).setPreferredWidth(100); 
        table.getColumnModel().getColumn(4).setPreferredWidth(20); 
        table.getColumnModel().getColumn(5).setPreferredWidth(20); 
        JScrollPane scrollPane = TaoUI.taoScrollPane(table);
        add(scrollPane);
        TaoUI.suaBorderChoPanel(this,0,0,0,10);
    }

}
