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

import dto.SanPham;
import ui.quanlysanpham.NutHienThiSP;
import util.TaoUI;

public class ThongTinHoaDonPanel extends JPanel {

    private DefaultTableModel model;
    private JTable table;

    public ThongTinHoaDonPanel() {
        TaoUI.taoPanelBoxLayoutDoc(this, Integer.MAX_VALUE, Integer.MAX_VALUE);
        JPanel title = TaoUI.taoPanelCanGiua(Integer.MAX_VALUE, 40);
        title.setBackground(new Color(225, 235, 245));
        title.add(new JLabel("Thông tin hóa đơn"));
        add(title);

        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4 || column == 5 || column == 6;
            }

            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4 || columnIndex == 5 || columnIndex ==6)
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
        model.addColumn("");



        HashSet<Integer> set = new HashSet<>();
        set.add(4);
        set.add(5);
        set.add(6);



        NutSuKienBanHang nutTru = new NutSuKienBanHang(new JCheckBox());
        nutTru.setLoaiNut("../assets/icon/tru.svg", 1);
        NutSuKienBanHang nutCong = new NutSuKienBanHang(new JCheckBox());
        nutCong.setLoaiNut("../assets/icon/cong.svg", 2);
        JScrollPane scrollPane = TaoUI.taoTableScroll(model, set);
        NutSuKienBanHang nutXoa = new NutSuKienBanHang(new JCheckBox());
        nutXoa.setLoaiNut("../assets/icon/xoa.svg", 3);


        table = (JTable) scrollPane.getViewport().getView();

        table.getColumnModel().getColumn(4).setCellRenderer(new NutHienThiBanHang("../assets/icon/tru.svg", 15, 15));
        table.getColumnModel().getColumn(4).setCellEditor(nutTru);
        table.getColumnModel().getColumn(4).setPreferredWidth(15);

        table.getColumnModel().getColumn(5).setCellRenderer(new NutHienThiBanHang("../assets/icon/cong.svg", 15, 15));
        table.getColumnModel().getColumn(5).setCellEditor(nutCong);
        table.getColumnModel().getColumn(5).setPreferredWidth(15);

        table.getColumnModel().getColumn(6).setCellRenderer(new NutHienThiBanHang("../assets/icon/xoa.svg", 50, 50));
        table.getColumnModel().getColumn(6).setCellEditor(nutXoa);
        table.getColumnModel().getColumn(6).setPreferredWidth(15);

        add(scrollPane);
        TaoUI.suaBorderChoPanel(this, 0, 0, 0, 10);
    }

    public void themSanPham(SanPham sp) {
        int rowCount = model.getRowCount();
        boolean Daco = false;
        int indexRow = -1;

        for (int i = 0; i < rowCount; i++) {
            if (model.getValueAt(i, 0).toString().equals(sp.getTenSP())) {
                Daco = true;
                indexRow = i;
                break;
            }
        }

        if (Daco) {
            int slCu = Integer.parseInt(model.getValueAt(indexRow, 2).toString());
            int slMoi = slCu + 1;
            double donGia = Double.parseDouble(model.getValueAt(indexRow, 1).toString());

            model.setValueAt(slMoi, indexRow, 2);
            model.setValueAt(donGia * slMoi, indexRow, 3);
        }else {
            model.addRow(new Object[]{
                    sp.getTenSP(),
                    sp.getGiaBan(),
                    1,
                    sp.getGiaBan(),
                    new JButton(),
                    new JButton(),
                    new JButton()
            });
        }

    }

    public DefaultTableModel getModel() {
        return model;
    }

}
