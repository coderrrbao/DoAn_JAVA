package ui.banhang;

import bus.ThongtinKhachHangBUS;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class BanHangUI extends JPanel {
    public BanHangUI() {
        setLayout(new GridLayout(1, 2, 0, 0));
        setBackground(Color.white);
        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel rightPanel = new JPanel(new BorderLayout());

        add(leftPanel);
        add(rightPanel);

        ThongTinKhachHangPanel thongTinKhachHangPanel = new ThongTinKhachHangPanel();
        leftPanel.add(thongTinKhachHangPanel, BorderLayout.NORTH);

        ThongtinKhachHangBUS khachHangBUS = new ThongtinKhachHangBUS();

        thongTinKhachHangPanel.getTxtSdt().getDocument().addDocumentListener(
                new javax.swing.event.DocumentListener() {

                    private void xuLy() {
                        String sdt = thongTinKhachHangPanel.getTxtSdt().getText().trim();

                        var kh = khachHangBUS.timTheoSDT(sdt);

                        if (kh != null) {
                            thongTinKhachHangPanel.getTxtTenKh().setText(kh.getTenKH());
                            thongTinKhachHangPanel.getTxtTenKh().setEditable(false);
                        } else {
                            thongTinKhachHangPanel.getTxtTenKh().setText("");
                            thongTinKhachHangPanel.getTxtTenKh().setEditable(true);
                        }
                    }

                    public void insertUpdate(javax.swing.event.DocumentEvent e) {
                        xuLy();
                    }

                    public void removeUpdate(javax.swing.event.DocumentEvent e) {
                        xuLy();
                    }

                    public void changedUpdate(javax.swing.event.DocumentEvent e) {
                        xuLy();
                    }
                }
        );

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
