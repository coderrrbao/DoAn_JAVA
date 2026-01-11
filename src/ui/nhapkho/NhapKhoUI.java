package ui.nhapkho;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import ui.component.Search_Item;
import util.TaoUI;

public class NhapKhoUI extends JPanel {
    private JButton nhapHangBtn, xemChiTietBtn;
    private Search_Item search_Item;

    public NhapKhoUI(Frame ouner) {
        setLayout(new BorderLayout());
        JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 30);
        nhapHangBtn = new JButton("Thêm");
        TaoUI.setHeightButton(nhapHangBtn, 27);
        xemChiTietBtn = new JButton("Xem Chi tiết");
        TaoUI.setHeightButton(xemChiTietBtn, 27);
        nhapHangBtn.addActionListener(e -> {
            JDialog dialogNhapHang = new NhapKhoDialog(ouner);
            dialogNhapHang.setVisible(true);
        });

        search_Item = new Search_Item(300, 30);
        top.add(search_Item);
        top.add(nhapHangBtn);
        top.add(xemChiTietBtn);

        add(top, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Mã Phiếu nhập");
        model.addColumn("Ngày nhập");
        model.addColumn("Nhân viên tạo phiếu");
        model.addColumn("Ghi chú");
        model.addColumn("Nhà cung cấp");
        Object[][] dataPhieuNhap = {
                { "PN001", "10/01/2026", "Nguyễn Văn A", "Nhập hàng định kỳ", "Công ty Coca-Cola" },
                { "PN002", "11/01/2026", "Trần Thị B", "Nhập bổ sung Tết", "Suntory Pepsico" },
                { "PN003", "11/01/2026", "Lê Văn C", "Hàng khuyến mãi", "Nhà máy Bia Sài Gòn" },
                { "PN004", "12/01/2026", "Nguyễn Văn A", "Nhập gấp", "Công ty Tân Hiệp Phát" },
                { "PN005", "12/01/2026", "Trần Thị B", "Kiểm kho nhập bù", "Nước khoáng Vĩnh Hảo" },
                { "PN006", "13/01/2026", "Lê Văn C", "Nhập hàng mới", "Công ty TH True Milk" },
                { "PN007", "13/01/2026", "Nguyễn Văn A", "Nhập nước suối", "Lavie Việt Nam" },
                { "PN008", "14/01/2026", "Trần Thị B", "Nhập nước tăng lực", "Red Bull Việt Nam" },
                { "PN009", "14/01/2026", "Lê Văn C", "Nhập bổ sung", "Công ty Nestle" },
                { "PN010", "15/01/2026", "Nguyễn Văn A", "Hàng về trễ", "Công ty Masan" }
        };

        for (Object[] row : dataPhieuNhap) {
            model.addRow(row);
        }
        JScrollPane scrollPane = TaoUI.taoTableScroll(model);

        add(scrollPane, BorderLayout.CENTER);
    }
}
