package ui.thongke;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import ui.component.Search_Item;
import ui.thongke.thongkechung.ThongKeChungNhapPanel;
import util.TaoUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

public class ThongKeNhapHangPanel extends JPanel {
    private ThongKeChungNhapPanel thongKeChungNH;
    private JButton locBtn,xuatExbtn;
    public ThongKeNhapHangPanel() {
        setLayout(new BorderLayout());

        initGUI();

    }
    private JPanel buttonPanel(){
        JPanel buttonPanel = TaoUI.taoPanelBoxLayoutNgang( 880,30);
        locBtn = new JButton("Lọc ngày");
        xuatExbtn = new JButton("Xuất exel");
        buttonPanel.add(locBtn);
        buttonPanel.add(xuatExbtn);
        return buttonPanel;
    }
    private void initGUI() {
        JPanel thongKeNHPanel = new JPanel();
        thongKeNHPanel.setLayout(new BoxLayout(thongKeNHPanel, BoxLayout.Y_AXIS));
        thongKeChungNH = new ThongKeChungNhapPanel();
        thongKeNHPanel.add(thongKeChungNH);
        JPanel top = new JPanel(new BorderLayout());
        top.add(thongKeNHPanel,BorderLayout.NORTH);
        top.add(buttonPanel(),BorderLayout.CENTER);

        JPanel tablePanel = new JPanel(new GridLayout(0, 2,10,10));
        TaoUI.setFixSize(tablePanel, 880, 600);
        tablePanel.add(thongKeSpPanel());
        tablePanel.add(thongKeNguyenLieu());

        add(top, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(tongChungPanel(), BorderLayout.SOUTH);
    }

    private JPanel thongKeSpPanel() {
        JPanel thongKeLoSp = new JPanel(new BorderLayout());

        JPanel top = TaoUI.taoPanelCanGiua(880, 40);
        top.add(new JLabel("Danh sách nhập sản phẩm"));
        top.setBackground(Color.green);
        String[] columns = { "Mã Phiếu nhập", "Ngày nhập", "Nhân viên tạo phiếu", "Ghi chú", "Nhà cung cấp" };

        Object[][] data = {
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

        DefaultTableModel model = new DefaultTableModel(data, columns);
        JScrollPane table = TaoUI.taoTableScroll(model);

        // --- Cập nhật Bottom ---
        JPanel bottom = TaoUI.taoPanelBoxLayoutNgang(880, 40);
        JLabel lblSl = new JLabel("Số lượng: 10 lô");
        JLabel tongTienNhapTitle = new JLabel("Tiền nhập sản phẩm: ");
        JLabel tongTienNhap = new JLabel("999.999.999đ");
        tongTienNhap.setFont(new Font(null, Font.BOLD, 16));
        tongTienNhap.setForeground(Color.red);

        bottom.add(Box.createHorizontalStrut(10)); // Khoảng cách lề trái
        bottom.add(lblSl);
        bottom.add(Box.createHorizontalGlue()); // Đẩy phần tiền sang phải
        bottom.add(tongTienNhapTitle);
        bottom.add(tongTienNhap);
        bottom.add(Box.createHorizontalStrut(10)); // Khoảng cách lề phải

        thongKeLoSp.add(top, BorderLayout.NORTH);
        thongKeLoSp.add(table, BorderLayout.CENTER);
        thongKeLoSp.add(bottom, BorderLayout.SOUTH);
        return thongKeLoSp;
    }

    private JPanel thongKeNguyenLieu() {
        JPanel thongKeLoNL = new JPanel(new BorderLayout());

        JPanel top = TaoUI.taoPanelCanGiua(880, 40);
        top.add(new JLabel("Danh sách nhập nguyên liệu"));
        top.setBackground(Color.green);
        String[] columns = { "Mã Phiếu nhập", "Ngày nhập", "Nhân viên tạo phiếu", "Ghi chú", "Nhà cung cấp" };

        Object[][] data = {
            { "PNNL01", "10/01/2026", "Nguyễn Văn A", "Nhập đường tinh luyện", "Công ty Biên Hòa" },
            { "PNNL02", "11/01/2026", "Trần Thị B", "Nhập sữa tươi", "Vinamilk Việt Nam" },
            { "PNNL03", "11/01/2026", "Lê Văn C", "Bột mì bổ sung", "Công ty Meizan" },
            { "PNNL04", "12/01/2026", "Nguyễn Văn A", "Nhập trà đen", "Phúc Long Tea" },
            { "PNNL05", "12/01/2026", "Trần Thị B", "Hạt cà phê Robusta", "Trung Nguyên Coffee" },
            { "PNNL06", "13/01/2026", "Lê Văn C", "Syrup các loại", "Monin Việt Nam" },
            { "PNNL07", "13/01/2026", "Nguyễn Văn A", "Kem béo thực vật", "Rich's Products" },
            { "PNNL08", "14/01/2026", "Trần Thị B", "Trân châu đen", "Gia Thịnh Phát" },
            { "PNNL09", "14/01/2026", "Lê Văn C", "Hương liệu thực phẩm", "Công ty Nestle" },
            { "PNNL10", "15/01/2026", "Nguyễn Văn A", "Nhập bổ sung khẩn cấp", "Nông sản Việt" }
        };

        DefaultTableModel model = new DefaultTableModel(data, columns);
        JScrollPane table = TaoUI.taoTableScroll(model);

        // --- Cập nhật Bottom ---
        JPanel bottom = TaoUI.taoPanelBoxLayoutNgang(880, 40);
        JLabel lblSl = new JLabel("Số lượng: 10 lô");
        JLabel tongTienNhapTitle = new JLabel("Tiền nhập nguyên liệu: ");
        JLabel tongTienNhap = new JLabel("50.500.000đ");
        tongTienNhap.setFont(new Font(null, Font.BOLD, 16));
        tongTienNhap.setForeground(Color.red);

        bottom.add(javax.swing.Box.createHorizontalStrut(10));
        bottom.add(lblSl);
        bottom.add(javax.swing.Box.createHorizontalGlue());
        bottom.add(tongTienNhapTitle);
        bottom.add(tongTienNhap);
        bottom.add(javax.swing.Box.createHorizontalStrut(10));

        thongKeLoNL.add(top, BorderLayout.NORTH);
        thongKeLoNL.add(table, BorderLayout.CENTER);
        thongKeLoNL.add(bottom, BorderLayout.SOUTH);

        return thongKeLoNL;
    }

    public JPanel tongChungPanel() {
        JPanel tongChungPanel = TaoUI.taoPanelBoxLayoutNgang(880, 40);
        JLabel tongTienNhapTitle = new JLabel("TỔNG TIỀN NHẬP CHUNG: ");
        JLabel tongTienNhap = new JLabel("1.050.499.999đ");
        tongTienNhap.setFont(new Font(null, Font.BOLD, 18));
        tongTienNhap.setForeground(Color.red);

        tongChungPanel.add(javax.swing.Box.createHorizontalGlue());
        tongChungPanel.add(tongTienNhapTitle);
        tongChungPanel.add(tongTienNhap);
        tongChungPanel.add(javax.swing.Box.createHorizontalStrut(10));
        
        return tongChungPanel;
    }
}
