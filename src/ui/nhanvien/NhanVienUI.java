package ui.nhanvien;

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

public class NhanVienUI extends JPanel {
    public NhanVienUI() {
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
        model.addColumn("maNV");
        model.addColumn("tenNV");
        model.addColumn("GioiTinh");
        model.addColumn("NgaySinh");
        model.addColumn("ChucVu");
        model.addColumn("Sdt");

        model.addRow(new Object[] { "1", "Nguyen Van A", "Nam", "01/01/1990", "Nhân viên", "0901234567" });
        model.addRow(new Object[] { "2", "Tran Thi B", "Nữ", "02/02/1992", "Quản lý", "0912345678" });
        model.addRow(new Object[] { "3", "Le Van C", "Nam", "03/03/1993", "Nhân viên", "0923456789" });
        model.addRow(new Object[] { "4", "Pham Thi D", "Nữ", "04/04/1994", "Kế toán", "0934567890" });

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
