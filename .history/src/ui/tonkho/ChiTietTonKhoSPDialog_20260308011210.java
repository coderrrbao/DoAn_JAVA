package ui.tonkho;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.time.LocalDate;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import bus.LoNguyenLieuBUS;
import bus.LoSanPhamBUS;
import dto.ChiTietCongThuc;
import dto.LoSanPham;
import dto.NguyenLieu;
import dto.SanPham;
import util.TaoUI;

public class ChiTietTonKhoSPDialog extends JDialog {
    private DefaultTableModel modelCoSan, modelPhaChe;
    private SanPham sanPham;
    private JPanel center;
    private JScrollPane scrollPaneCoSan, scrollPanePhaChe;
    priv
    public ChiTietTonKhoSPDialog(JFrame owner, SanPham sanPham) {
        super(owner, "Chi tiết lô hàng - " + sanPham.getTenSP(), true);
        setSize(600, 400);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());
        this.sanPham = sanPham;
        JLabel lblTitle = new JLabel("Danh sách lô hàng: " + sanPham.getTenSP(), SwingConstants.CENTER);
        JPanel titlePanel = TaoUI.taoPanelCanGiua(3000, 35);
        titlePanel.setBorder(new MatteBorder(1, 0, 0, 0, Color.BLACK));
        titlePanel.add(lblTitle);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));

        String[] columnsSP = { "Mã Lô", "HSD", "Ngày SX", "Số Lượng", "Ngày Nhập", "Trạng thái" };
        modelCoSan = new DefaultTableModel(columnsSP, 0);

        String[] columnsNL = { "Mã NL", "Tên NL", "Số Lượng", "Đơn vị", "Trạng thái" };
        modelPhaChe = new DefaultTableModel(columnsNL, 0);

        center = new JPanel(new BorderLayout());
        center.add(titlePanel, BorderLayout.NORTH);
        scrollPaneCoSan = TaoUI.taoTableScroll(modelCoSan);
        scrollPanePhaChe = TaoUI.taoTableScroll(modelPhaChe);
        center.add(scrollPaneCoSan, BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);
        loadDuLieu();
    }

    public void loadDuLieu() {
        if (sanPham == null) {
            return;
        }
        if (sanPham.getLoaiNuoc().equals("Có sẵn")) {
            center.add(scrollPaneCoSan, BorderLayout.CENTER);
            modelCoSan.setRowCount(0);
            LoSanPhamBUS loSanPhamBUS = LoSanPhamBUS.getLoSanPhamBUS();
            for (LoSanPham loSanPham : loSanPhamBUS.layLoChoSanPham(sanPham.getMaSP())) {
                String trangThai;
                try {
                    trangThai = LocalDate.parse(loSanPham.getHanSuDung()).isAfter(LocalDate.now()) ? "Còn hạn"
                            : "Hết hạn";
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }

                modelCoSan.addRow(new Object[] { loSanPham.getMaLoSP(),
                        loSanPham.getHanSuDung(), loSanPham.getNgaySanXuat(), loSanPham.getSoLuong(),
                        loSanPham.getNgayNhap(), trangThai });
            }
        }
        if (sanPham.getLoaiNuoc().equals("Pha chế")) {
            setTitle("Danh sách tồn kho nguyên liệu cho sản phẩm : "+sanPham.getTenSP());
            center.add(scrollPanePhaChe, BorderLayout.CENTER);
            modelPhaChe.setRowCount(0);
            LoNguyenLieuBUS loNguyenLieuBUS = LoNguyenLieuBUS.getLoNguyenLieuBUS();
            for (ChiTietCongThuc chiTietCongThuc : sanPham.getCongThuc().getListChiTietCongThuc()) {
                NguyenLieu nguyenLieu = chiTietCongThuc.getNguyenLieu();
                double soLuong = loNguyenLieuBUS.laySoLuongNguyenLieuTrongKho(nguyenLieu.getMaNL());
                String trangThai = soLuong <= 0 ? "Hết hàng" : "Còn hàng";
                modelPhaChe.addRow(new Object[] { nguyenLieu.getMaNL(), nguyenLieu.getTenNL(),
                        soLuong, nguyenLieu.getDonVi(), trangThai });
            }
        }

    }
}