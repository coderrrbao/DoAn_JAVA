package ui.hoadon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import bus.HoaDonBUS;
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
        top.add(search_Item);
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
        model.addColumn("Mã HD");
        model.addColumn("Ngày tạo");
        model.addColumn("Mã nhân viên");
        model.addColumn("Mã khách hàng");
        model.addColumn("Tổng tiền");

        loadData();;

        JScrollPane scrollPane = TaoUI.taoTableScroll(model);
        table  = (JTable) scrollPane.getViewport().getView();
        
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(new Color(238, 238, 238));
        tableContainer.add(scrollPane, BorderLayout.CENTER);

        add(tableContainer, BorderLayout.CENTER);
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

    public JButton getBtnXemChiTiet() { return btnXemChiTiet; }
    public JButton getBtnXoa() { return btnXoa; }
    public Search_Item getSearch_Item() { return search_Item; }
    public DefaultTableModel getModel() { return model; }
}