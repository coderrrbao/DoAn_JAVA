package ui.banhang;

import java.awt.BorderLayout;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class BanHangUI extends JPanel {
    public BanHangUI() {
        setLayout(new GridLayout(1, 2, 0, 0));

        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel rightPanel = new JPanel(new BorderLayout());

        add(leftPanel);
        add(rightPanel);

        ThongTinKhachHangPanel thongTinKhachHangPanel = new ThongTinKhachHangPanel();
        leftPanel.add(thongTinKhachHangPanel, BorderLayout.NORTH);

        ThanhToanPanel thanhToanPanel = new ThanhToanPanel();
        leftPanel.add(thanhToanPanel, BorderLayout.SOUTH);

        ThongTinHoaDonPanel thongTinHoaDonPanel = new ThongTinHoaDonPanel();
        leftPanel.add(thongTinHoaDonPanel, BorderLayout.CENTER);

        BoLocPanel boLocPanel = new BoLocPanel();
        rightPanel.add(boLocPanel, BorderLayout.NORTH);

        ListSanPhamPanel listSanPhamPanel = new ListSanPhamPanel();
        rightPanel.add(listSanPhamPanel, BorderLayout.CENTER);
    }
}
