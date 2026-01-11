package ui.hoadon;

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

public class HoaDonUI extends JPanel {
    public HoaDonUI() {
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
        JButton tao = new JButton("Tạo mới");
        tao.setPreferredSize(new Dimension(tao.getPreferredSize().width, 35));
        topContent.add(tao);
        JButton xoa = new JButton("Xóa");
        xoa.setPreferredSize(new Dimension(xoa.getPreferredSize().width, 35));
        topContent.add(xoa);

        JButton xemChiTiet = new JButton("Xem chi tiết");
        xemChiTiet.setPreferredSize(new Dimension(xemChiTiet.getPreferredSize().width, 35));
        topContent.add(xemChiTiet);

        add(topContent, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("MaHD");
        model.addColumn("NgayTao");
        model.addColumn("MaNV");
        model.addColumn("MaKH");
        model.addColumn("TongTien");

        model.addRow(new Object[] { "1", "2026-01-06", "NV01", "KH01", "100000" });
        model.addRow(new Object[] { "2", "2026-01-06", "NV02", "KH02", "200000" });
        model.addRow(new Object[] { "3", "2026-01-06", "NV03", "KH03", "300000" });
        model.addRow(new Object[] { "4", "2026-01-06", "NV04", "KH04", "400000" });

        JTable tableUI = new JTable(model);

        tableUI.getColumnModel().getColumn(0).setPreferredWidth(60); // maNCC
        tableUI.getColumnModel().getColumn(1).setPreferredWidth(150); // tenNCC
        tableUI.getColumnModel().getColumn(2).setPreferredWidth(100); // SDT
        tableUI.getColumnModel().getColumn(3).setPreferredWidth(100); // DiaChi
        tableUI.getColumnModel().getColumn(4).setPreferredWidth(100); // DiaChi
        tableUI.setRowHeight(40);
        tableUI.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tableUI.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        JScrollPane scrollPane = new JScrollPane(tableUI);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel = TaoUI.suaBorderChoPanel(tablePanel, 5, 5, 0, 5);
        add(tablePanel, BorderLayout.CENTER);
    }
}
