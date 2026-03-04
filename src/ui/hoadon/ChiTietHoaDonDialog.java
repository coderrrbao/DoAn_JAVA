package ui.hoadon;

import dto.ChiTietHoaDon;
import dto.HoaDon;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ChiTietHoaDonDialog extends JDialog {
    private DefaultTableModel model;
    private DecimalFormat df = new DecimalFormat("#,### VNĐ");
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    private Font fontTitle = new Font("Segoe UI", Font.BOLD, 22);
    private Font fontLabel = new Font("Segoe UI", Font.ITALIC, 14);
    private Font fontValue = new Font("Segoe UI", Font.BOLD, 15);
    private Font fontTable = new Font("Segoe UI", Font.PLAIN, 14);

    public ChiTietHoaDonDialog(Frame owner, HoaDon hd, ArrayList<ChiTietHoaDon> dsChiTiet) {
        super(owner, "Chi Tiết Hóa Đơn - " + hd.getMaHD(), true);
        setSize(550, 700);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JPanel pnlHeader = new JPanel();
        pnlHeader.setLayout(new BoxLayout(pnlHeader, BoxLayout.Y_AXIS));
        pnlHeader.setBackground(Color.WHITE);
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel lblTitle = new JLabel("HÓA ĐƠN BÁN HÀNG");
        lblTitle.setFont(fontTitle);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlHeader.add(lblTitle);

        pnlHeader.add(Box.createVerticalStrut(15));
        JSeparator sep1 = new JSeparator();
        sep1.setMaximumSize(new Dimension(500, 5));
        pnlHeader.add(sep1);
        pnlHeader.add(Box.createVerticalStrut(15));

        JPanel pnlInfo = new JPanel(new GridLayout(4, 2, 10, 8));
        pnlInfo.setBackground(Color.WHITE);

        String maNV = (hd.getNhanVien() != null && hd.getNhanVien().getMaNV() != null) ? hd.getNhanVien().getMaNV() : "Không xác định";
        String maKH = (hd.getMaKH() != null) ? hd.getMaKH() : "Khách vãng lai";
        String ngayBan = (hd.getNgayBan() != null) ? sdf.format(hd.getNgayBan()) : "Không xác định";

        pnlInfo.add(taoTieuDe("Mã hóa đơn:"));
        pnlInfo.add(taoGiaTri(hd.getMaHD()));

        pnlInfo.add(taoTieuDe("Ngày giao dịch:"));
        pnlInfo.add(taoGiaTri(ngayBan));

        pnlInfo.add(taoTieuDe("Thu ngân trực ca:"));
        pnlInfo.add(taoGiaTri(maNV));

        pnlInfo.add(taoTieuDe("Khách hàng:"));
        pnlInfo.add(taoGiaTri(maKH));

        pnlHeader.add(pnlInfo);
        add(pnlHeader, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"Tên món", "SL", "Đơn giá", "Thành tiền"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable table = new JTable(model);
        table.setRowHeight(35);
        table.setFont(fontTable);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(245, 245, 245));
        table.setShowVerticalLines(false);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        table.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);

        table.getColumnModel().getColumn(0).setPreferredWidth(200);
        table.getColumnModel().getColumn(1).setPreferredWidth(40);

        for (ChiTietHoaDon ct : dsChiTiet) {
            String tenSP = ct.getSanPham().getTenSP();
            if (ct.getSize() != null) {
                tenSP += " (" + ct.getSize().getTenSize() + ")";
            }
            double thanhTien = ct.getGia() * ct.getSoLuong();
            model.addRow(new Object[]{
                    tenSP, ct.getSoLuong(), df.format(ct.getGia()), df.format(thanhTien)
            });
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        JPanel pnlFooter = new JPanel();
        pnlFooter.setLayout(new BoxLayout(pnlFooter, BoxLayout.Y_AXIS));
        pnlFooter.setBackground(Color.WHITE);
        pnlFooter.setBorder(BorderFactory.createEmptyBorder(15, 30, 30, 30));

        JSeparator sep2 = new JSeparator();
        sep2.setMaximumSize(new Dimension(500, 5));
        pnlFooter.add(sep2);
        pnlFooter.add(Box.createVerticalStrut(15));

        JPanel pnlTien = new JPanel(new GridLayout(2, 2, 10, 8));
        pnlTien.setBackground(Color.WHITE);

        double tienGiam = hd.getTienKhuyenMai();
        double tongTienHang = hd.getTongTien() + tienGiam;

        pnlTien.add(taoTieuDe("Tổng tiền hàng:"));
        pnlTien.add(taoGiaTriPhai(df.format(tongTienHang)));

        if (tienGiam > 0) {
            pnlTien.add(taoTieuDe("Tiền khuyến mãi:"));
            JLabel lblGiam = taoGiaTriPhai("- " + df.format(tienGiam));
            lblGiam.setForeground(Color.RED);
            pnlTien.add(lblGiam);
        }

        pnlFooter.add(pnlTien);
        pnlFooter.add(Box.createVerticalStrut(15));

        JPanel pnlTong = new JPanel(new BorderLayout());
        pnlTong.setBackground(Color.WHITE);
        JLabel lblLeft = new JLabel("TỔNG THANH TOÁN:");
        lblLeft.setFont(new Font("Segoe UI", Font.BOLD, 18));
        JLabel lblRight = new JLabel(df.format(hd.getTongTien()));
        lblRight.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblRight.setForeground(new Color(0, 153, 51));
        pnlTong.add(lblLeft, BorderLayout.WEST);
        pnlTong.add(lblRight, BorderLayout.EAST);
        pnlFooter.add(pnlTong);

        add(pnlFooter, BorderLayout.SOUTH);
    }

    private JLabel taoTieuDe(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(fontLabel);
        lbl.setForeground(Color.GRAY);
        return lbl;
    }

    private JLabel taoGiaTri(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(fontValue);
        lbl.setForeground(Color.BLACK);
        return lbl;
    }

    private JLabel taoGiaTriPhai(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(fontValue);
        lbl.setForeground(Color.BLACK);
        lbl.setHorizontalAlignment(SwingConstants.RIGHT);
        return lbl;
    }
}