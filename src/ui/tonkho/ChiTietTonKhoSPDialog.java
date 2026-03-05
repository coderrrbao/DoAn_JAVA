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
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import bus.LoSanPhamBUS;
import dto.LoSanPham;
import dto.SanPham;
import util.TaoUI;

public class ChiTietTonKhoSPDialog extends JDialog {
    private JTable tableSP;
    private DefaultTableModel modelCoSan;
    private SanPham sanPham;

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

        String[] columns = { "Mã Lô", "HSD", "Ngày SX", "Số Lượng", "Ngày Nhập", "Trạng thái" };
        modelCoSan = new DefaultTableModel(columns, 0);
        tableSP = new JTable(modelCoSan);

        JPanel center = new JPanel(new BorderLayout());
        center.add(titlePanel, BorderLayout.NORTH);
        JScrollPane scrollPane = TaoUI.taoTableScroll(modelCoSan);
        center.add(scrollPane, BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);
        loadDuLieu();
    }

    private void loadDuLieu() {
        if (sanPham == null) {
            return;
        }
        if (sanPham.getLoaiNuoc().equals("Có sẵn")) {
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
    }
}