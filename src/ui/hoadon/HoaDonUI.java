package ui.hoadon;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import bus.HoaDonBUS;
import dto.ChiTietHoaDon;
import dto.HoaDon;
import ui.component.LocNgay_Item;
import ui.component.Search_Item;
import util.TaoUI;

public class HoaDonUI extends JPanel {
    private JButton btnXemChiTiet, btnXoa;
    private HoaDonBUS hoaDonBUS = new HoaDonBUS();
    private Search_Item search_Item;
    private JTable table;
    private DefaultTableModel model;
    private LocNgay_Item locNgay;

    public HoaDonUI() {
        setLayout(new BorderLayout());

        JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 35);
        top.setBackground(Color.WHITE);

        locNgay = new LocNgay_Item(350, 27);
        search_Item = new Search_Item(300, 30);
        
        btnXemChiTiet = new JButton("Chi tiết");
        
        btnXoa = new JButton("Xóa");

        top.add(locNgay);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnXemChiTiet);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnXoa);
        top.add(Box.createHorizontalGlue());

        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.addColumn("Mã Hóa Đơn");
        model.addColumn("Thời Gian Tạo");
        model.addColumn("Thu Ngân");
        model.addColumn("Khách Hàng");
        model.addColumn("Tổng Thanh Toán");

        loadData();

        JScrollPane scrollPane = TaoUI.taoTableScroll(model);
        table = (JTable) scrollPane.getViewport().getView();

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(230, 240, 250));
        table.getTableHeader().setOpaque(false);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        javax.swing.table.DefaultTableCellRenderer rightRenderer = new javax.swing.table.DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);

        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(new Color(238, 238, 238));
        tableContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tableContainer.add(scrollPane, BorderLayout.CENTER);

        add(tableContainer, BorderLayout.CENTER);

        btnXemChiTiet.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 hóa đơn để xem chi tiết!");
                return;
            }

            String maHD = model.getValueAt(row, 0).toString();

            HoaDon hdFull = hoaDonBUS.timHoaDonTheoMa(maHD);
            if (hdFull == null) {
                hdFull = new HoaDon();
                hdFull.setMaHD(maHD);
            }

            bus.ChiTietHoaDonBUS ctBus = new bus.ChiTietHoaDonBUS();
            ArrayList<ChiTietHoaDon> dsChiTiet = ctBus.layChiTietTheoMaHD(maHD);

            Frame parent = (Frame) SwingUtilities.getWindowAncestor(this);
            ChiTietHoaDonDialog dialog = new ChiTietHoaDonDialog(parent, hdFull, dsChiTiet);
            dialog.setVisible(true);
        });
    }

    public void loadData() {
        model.setRowCount(0);
        ArrayList<HoaDon> list = hoaDonBUS.layDanhSachHoaDon();
         if (list != null) {
             SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
             DecimalFormat df = new DecimalFormat("#,### VNĐ");

             for (HoaDon hd : list) {
                 String maNV = (hd.getNhanVien() != null) && hd.getNhanVien().getMaNV() != null ? hd.getNhanVien().getMaNV() : "";
                 String maKH = (hd.getMaKH() != null) ? hd.getMaKH() : "Khách vãng lai";
                 String NgayTao = (hd.getNgayBan() != null) ? sdf.format(hd.getNgayBan()) : "";
                 String TongTien = df.format(hd.getTongTien());

                 model.addRow(new Object[] { hd.getMaHD(), NgayTao, maNV, maKH, TongTien });
             }
         }
    }


    public JTable getTable() { return table; }
    public JButton getBtnXoa() { return btnXoa; }
    public Search_Item getSearch_Item() { return search_Item; }
    public DefaultTableModel getModel() { return model; }
}