package ui.nhacungcap;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import bus.NhaCungCapBUS;
import dto.NhaCungCap;
import ui.component.Search_Item;
import ui.login.PhienDangNhap;
import util.TaoTinNhan;
import util.TaoUI;

public class NhaCungCapUI extends JPanel {
    private JButton btnTao, btnXoa, btnXuatExcel, btnNhapExcel, btnXemChiTiet;
    private Search_Item search_Item;
    private JTable tableUI;
    private DefaultTableModel model;

    public NhaCungCapUI() {
        setLayout(new BorderLayout());

        JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 45);
        top.setBackground(Color.WHITE);
        top = TaoUI.suaBorderChoPanel(top, 0, 10, 0, 10);

        search_Item = new Search_Item(300, 32);

        btnTao = new JButton("Thêm");

        btnXemChiTiet = new JButton("Xem chi tiết");

        btnXoa = new JButton("Xóa");

        TaoUI.setFixSize(btnTao, 100, 32);
        TaoUI.setFixSize(btnXemChiTiet, 150, 32);
        TaoUI.setFixSize(btnXoa, 100, 32);

        top.add(search_Item);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnTao);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnXemChiTiet);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnXoa);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(Box.createHorizontalGlue());

        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("Mã NCC");
        model.addColumn("Tên nhà cung cấp");
        model.addColumn("Loại cung cấp");
        model.addColumn("Số điện thoại");
        model.addColumn("Địa chỉ");

        JScrollPane scrollPane = TaoUI.taoTableScroll(model);
        tableUI = (JTable) scrollPane.getViewport().getView();
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(new Color(238, 238, 238));
        tableContainer.add(scrollPane, BorderLayout.CENTER);

        add(tableContainer, BorderLayout.CENTER);
        ganSuKien();
        loadDuLieu();
    }

    public void loadDuLieu() {
        model.setRowCount(0);
        NhaCungCapBUS nhaCungCapBUS = NhaCungCapBUS.getNhaCungCapBUS();
        for (NhaCungCap nhaCungCap : nhaCungCapBUS.laylistNhaCungCap()) {
            if (nhaCungCap.getTenNCC().contains(search_Item.getTextSearch())) {
                String loaiCungCap = "";
                if (nhaCungCap.getCungCapSP()) {
                    loaiCungCap = "Sản phẩm";
                }
                if (nhaCungCap.getCungCapNL()) {
                    if (!loaiCungCap.equals("")) {
                        loaiCungCap += " & ";
                    }
                    loaiCungCap += "Nguyên liệu";
                }
                if (loaiCungCap.equals("")) {
                    loaiCungCap = "Không có";
                }
                model.addRow(new Object[] { nhaCungCap.getMaNCC(), nhaCungCap.getTenNCC(),
                        loaiCungCap, nhaCungCap.getSoDienThoai(), nhaCungCap.getDiaChi() });
            }
        }
    }

    public void suaLaiGiaoDienTheoQuyen() {
        var listQuyen = PhienDangNhap.getListQuyen();

        if (!listQuyen.contains("NCC_TAO")) {
            btnTao.setVisible(false);
        }

        if (!listQuyen.contains("NCC_XOA")) {
            btnXoa.setVisible(false);
        }
        this.revalidate();
        this.repaint();
    }

    public void ganSuKien() {
        search_Item.setEvent(() -> {
            loadDuLieu();
        });
        btnTao.addActionListener(e -> {
            ChiTietNhaCungCapDialog chiTietNhaCungCapDialog = new ChiTietNhaCungCapDialog(null, null, this);
            chiTietNhaCungCapDialog.setVisible(true);

        });
        btnXemChiTiet.addActionListener(e -> {
            int dongChon = tableUI.getSelectedRow();
            if (dongChon >= 0) {
                NhaCungCapBUS nhaCungCapBUS = NhaCungCapBUS.getNhaCungCapBUS();
                NhaCungCap nhaCungCap = nhaCungCapBUS.timNhaCungCap(model.getValueAt(dongChon, 0).toString());
                ChiTietNhaCungCapDialog chiTietNhaCungCapDialog = new ChiTietNhaCungCapDialog(null, nhaCungCap, this);
                chiTietNhaCungCapDialog.setVisible(true);
            } else {
                TaoTinNhan.showAutoCloseMessage("Vui lòng chọn nhà cung cấp để xem chi tiết", "Thông báo", 1);
            }
        });

        btnXoa.addActionListener(e -> {
            int dongChon = tableUI.getSelectedRow();
            if (dongChon >= 0) {
                NhaCungCapBUS nhaCungCapBUS = NhaCungCapBUS.getNhaCungCapBUS();
                NhaCungCap nhaCungCap = nhaCungCapBUS.timNhaCungCap(model.getValueAt(dongChon, 0).toString());
                if (JOptionPane.showConfirmDialog(null, "Xóa nhà cung cấp " + nhaCungCap.getMaNCC() + " ?", "Thông báo",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
                    if (nhaCungCapBUS.xoaNhaCungCap(nhaCungCap)) {
                        TaoTinNhan.showAutoCloseMessage("Xóa nhà cung cấp thành công", "Thông báo", 1);
                        loadDuLieu();
                    } else {
                        TaoTinNhan.showAutoCloseMessage("Xóa nhà cung cấp thất bại", "Thông báo", 1);
                    }
                }

            } else {
                TaoTinNhan.showAutoCloseMessage("Vui lòng chọn nhà cung cấp để xóa", "Thông báo", 1);
            }
        });
    }
}