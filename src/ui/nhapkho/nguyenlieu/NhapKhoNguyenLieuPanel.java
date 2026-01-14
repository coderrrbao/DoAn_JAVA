package ui.nhapkho.nguyenlieu;

import java.awt.BorderLayout;
import java.awt.Frame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import ui.component.Search_Item;
import util.TaoUI;

public class NhapKhoNguyenLieuPanel extends JPanel {
    private JButton nhapHangBtn, xemChiTietBtn;
    private Search_Item search_Item;

    public NhapKhoNguyenLieuPanel(Frame owner) {
        setLayout(new BorderLayout());

        JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 30);
        
        search_Item = new Search_Item(300, 30);
        
        nhapHangBtn = new JButton("Nhập nguyên liệu");
        TaoUI.setHeightButton(nhapHangBtn, 27);
        
        xemChiTietBtn = new JButton("Xem chi tiết");
        TaoUI.setHeightButton(xemChiTietBtn, 27);

        nhapHangBtn.addActionListener(e -> {
            System.out.println("Mở Dialog Nhập Nguyên Liệu");
        });

        top.add(search_Item);
        top.add(nhapHangBtn);
        top.add(xemChiTietBtn);

        add(top, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Mã Phiếu nhập");
        model.addColumn("Ngày nhập");
        model.addColumn("Nhân viên");
        model.addColumn("Loại nguyên liệu");
        model.addColumn("Nhà cung cấp");
        model.addColumn("Tổng tiền");

        Object[][] dataPhieuNhap = {
                { "PNNL001", "10/01/2026", "Nguyễn Văn A", "Hương liệu", "NCC Kim Biên", "5.000.000" },
                { "PNNL002", "11/01/2026", "Trần Thị B", "Đường tinh luyện", "Đường Biên Hòa", "12.500.000" },
                { "PNNL003", "11/01/2026", "Lê Văn C", "Phụ gia thực phẩm", "Công ty hóa chất ABC", "3.200.000" },
                { "PNNL004", "12/01/2026", "Nguyễn Văn A", "Bao bì nhãn mác", "In ấn Gia Huy", "7.800.000" },
                { "PNNL005", "12/01/2026", "Trần Thị B", "Nước tinh khiết", "Lavie Supplier", "2.000.000" }
        };

        for (Object[] row : dataPhieuNhap) {
            model.addRow(row);
        }

        JScrollPane scrollPane = TaoUI.taoTableScroll(model);

        add(scrollPane, BorderLayout.CENTER);
    }
}