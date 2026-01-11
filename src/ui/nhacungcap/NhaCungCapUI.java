package ui.nhacungcap;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import util.TaoUI;

public class NhaCungCapUI extends JPanel {
    public NhaCungCapUI() {
        setLayout(new BorderLayout());
        JPanel topContent = new JPanel();
        topContent.setPreferredSize(new Dimension(100, 45));
        topContent.setLayout(new FlowLayout(FlowLayout.LEFT));
        topContent.setBackground(Color.WHITE);
        JButton loc = new JButton("Lọc");
        loc.setPreferredSize(new Dimension(loc.getPreferredSize().width, 35));
        topContent.add(loc);
        JTextField timKiem = new JTextField();

        timKiem.setToolTipText("Nhấn vào đây để tìm kiếm sản phẩm");
        timKiem.setPreferredSize(new Dimension(300, 35));
        timKiem.setFont(new Font("Arial", Font.PLAIN, 17));
        topContent.add(timKiem);
        JButton lamMoi = new JButton("làm mới");
        lamMoi.setPreferredSize(new Dimension(lamMoi.getPreferredSize().width, 35));
        topContent.add(lamMoi);
        JButton tao = new JButton("Tạo mới");
        tao.setPreferredSize(new Dimension(tao.getPreferredSize().width, 35));
        topContent.add(tao);
        JButton xoa = new JButton("Xóa");
        xoa.setPreferredSize(new Dimension(xoa.getPreferredSize().width, 35));
        topContent.add(xoa);
        add(topContent, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("maNCC");
        model.addColumn("tenNCC");
        model.addColumn("SDT");
        model.addColumn("DiaChi");

        model.addRow(new Object[] { "1", "Pepsi", "11111", "Trái Đất" });
        model.addRow(new Object[] { "2", "Coca Cola", "22222", "Sao Hỏa" });
        model.addRow(new Object[] { "3", "Lavie", "33333", "Sao Kim" });
        model.addRow(new Object[] { "4", "Heineken", "44444", "Mặt Trăng" });

        JTable tableUI = new JTable(model);

        tableUI.getColumnModel().getColumn(0).setPreferredWidth(60); // maNCC
        tableUI.getColumnModel().getColumn(1).setPreferredWidth(150); // tenNCC
        tableUI.getColumnModel().getColumn(2).setPreferredWidth(100); // SDT
        tableUI.getColumnModel().getColumn(3).setPreferredWidth(250); // DiaChi
        tableUI.setRowHeight(40);
        tableUI.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tableUI.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        JScrollPane scrollPane = new JScrollPane(tableUI);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel = TaoUI.suaBorderChoPanel(tablePanel, 5, 5, 0, 5);
        add(tablePanel, BorderLayout.CENTER);
    }
}
