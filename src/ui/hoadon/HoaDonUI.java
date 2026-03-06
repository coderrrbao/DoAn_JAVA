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
    private JButton btnXemChiTiet, btnXoa, btnXuatExcel;
    private HoaDonBUS hoaDonBUS = new HoaDonBUS();
    private Search_Item search_Item;
    private JTable table;
    private DefaultTableModel model;
    private LocNgay_Item locNgay;

    public HoaDonUI() {
        setLayout(new BorderLayout());

        JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 45);
        top.setBackground(Color.WHITE);

        locNgay = new LocNgay_Item(400, 32);
        search_Item = new Search_Item(300, 32);
        locNgay.setEvent(() -> {
            loadData();
        });

        btnXemChiTiet = new JButton("Chi tiết");
        TaoUI.setFixSize(btnXemChiTiet, 100, 32);


        btnXoa = new JButton("Xóa");
        TaoUI.setFixSize(btnXoa, 80, 32);

        btnXuatExcel = new JButton("Xuất Exc");

        top.add(locNgay);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnXemChiTiet);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnXoa);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnXuatExcel);
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

        btnXoa.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 hóa đơn để xóa!", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String maHD = model.getValueAt(row, 0).toString();

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn xóa hóa đơn [" + maHD + "] không?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                if (hoaDonBUS.xoaHoaDon(maHD)) {
                    JOptionPane.showMessageDialog(this, "Đã xóa hóa đơn thành công!", "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi: Không thể xóa hóa đơn này!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loadData();

        btnXuatExcel.addActionListener(e -> {
            ArrayList<HoaDon> dsXuat = layDuLieuTuBang();

            if (dsXuat.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không có dữ liệu hóa đơn nào để xuất!");
                return;
            }

            hoaDonBUS.xuatExcel(dsXuat);
        });
    }

    public void loadData() {
        model.setRowCount(0);
        ArrayList<HoaDon> list = hoaDonBUS.layDanhSachHoaDon();
        if (list != null) {
            SimpleDateFormat sdfDisplay = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdfCheck = new SimpleDateFormat("yyyy-MM-dd");
            DecimalFormat df = new DecimalFormat("#,### VNĐ");

            for (HoaDon hd : list) {
                if (hd.getNgayBan() != null) {
                    String ngayCheck = sdfCheck.format(hd.getNgayBan());

                    if (locNgay.ngayTrongKhoan(ngayCheck)) {
                        String maNV = (hd.getNhanVien() != null) && hd.getNhanVien().getMaNV() != null
                                ? hd.getNhanVien().getMaNV()
                                : "";
                        String maKH = (hd.getMaKH() != null) ? hd.getMaKH() : "Khách vãng lai";
                        String NgayTao = sdfDisplay.format(hd.getNgayBan());
                        String TongTien = df.format(hd.getTongTien());

                        model.addRow(new Object[] { hd.getMaHD(), NgayTao, maNV, maKH, TongTien });
                    }
                }
            }
        }
    }

    public JTable getTable() {
        return table;
    }

    public JButton getBtnXoa() {
        return btnXoa;
    }
    private ArrayList<HoaDon> layDuLieuTuBang() {
        ArrayList<HoaDon> list = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            String maHD = model.getValueAt(i, 0).toString();
            HoaDon hd = hoaDonBUS.timHoaDonTheoMa(maHD);
            if (hd != null) {
                list.add(hd);
            }
        }
        return list;
    }


    public Search_Item getSearch_Item() {
        return search_Item;
    }

    public DefaultTableModel getModel() {
        return model;
    }
}