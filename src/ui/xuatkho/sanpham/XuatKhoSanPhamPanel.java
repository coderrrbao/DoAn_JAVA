package ui.xuatkho.sanpham;

import java.awt.BorderLayout;
import java.awt.Frame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import ui.component.Search_Item;
import util.TaoUI;

public class XuatKhoSanPhamPanel extends JPanel {
    private JButton xuatHangBtn;
    private JTable table;

    public XuatKhoSanPhamPanel(Frame owner) {
        setLayout(new BorderLayout());
        JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 35);
        
        xuatHangBtn = new JButton("Tạo Phiếu Xuất");
        xuatHangBtn.addActionListener(e -> {
            XuatKhoSanPhamDialog dialog = new XuatKhoSanPhamDialog(owner);
            dialog.setVisible(true);
        });

        top.add(new Search_Item(300, 30));
        top.add(xuatHangBtn);
        add(top, BorderLayout.NORTH);

        // Bảng danh sách phiếu xuất
        DefaultTableModel model = new DefaultTableModel(new String[]{"Mã Phiếu xuất", "Ngày xuất", "Nhân viên", "Loại"}, 0);
        JScrollPane scrollPane = TaoUI.taoTableScroll(model);
        add(scrollPane, BorderLayout.CENTER);
    }
}