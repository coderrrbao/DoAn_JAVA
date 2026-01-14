package ui.tonkho;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import util.TaoUI;

public class TonKhoLoSanPhamDialog extends JDialog {
    private JTable table;
    private DefaultTableModel model;
    private JButton btnLoc;

    public TonKhoLoSanPhamDialog(JFrame owner, String maSP, String tenSP) {
        super(owner, "Chi tiết lô hàng - " + maSP, true);
        setSize(600, 400);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 45);
        // btnLoc = new JButton("Lọc");
        // top.add(btnLoc);
        add(top, BorderLayout.NORTH);
        JLabel lblTitle = new JLabel("Danh sách lô hàng: " + tenSP, SwingConstants.CENTER);
        JPanel titlePanel = TaoUI.taoPanelCanGiua(3000, 35);
        titlePanel.setBorder(new MatteBorder(1, 0, 0, 0, Color.BLACK));
        titlePanel.add(lblTitle);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));

        String[] columns = { "Mã Lô", "Ngày Nhập", "Ngày Hết Hạn", "Số Lượng", "Trạng Thái" };
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        loadDataDemo();

        JPanel center = new JPanel(new BorderLayout());
        center.add(titlePanel, BorderLayout.NORTH);
        JScrollPane scrollPane = TaoUI.taoTableScroll(model);
        center.add(scrollPane, BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

    }

    private void loadDataDemo() {
        model.addRow(new Object[] { "L001", "01/01/2023", "01/01/2024", 5, "Còn hạn" });
        model.addRow(new Object[] { "L002", "15/05/2023", "15/12/2023", 2, "Hết hạn" });
        model.addRow(new Object[] { "L005", "10/10/2023", "10/10/2024", 3, "Còn hạn" });
    }
}