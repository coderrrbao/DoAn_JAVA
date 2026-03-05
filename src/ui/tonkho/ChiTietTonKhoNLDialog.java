package ui.tonkho;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import bus.LoNguyenLieuBUS;
import dto.LoNguyenLieu;
import dto.NguyenLieu;
import util.TaoUI;

public class ChiTietTonKhoNLDialog extends JDialog {
    private JTable tableNL;
    private DefaultTableModel modelNL;
    private NguyenLieu nguyenLieu;

    public ChiTietTonKhoNLDialog(JFrame owner, NguyenLieu nguyenLieu) {
        super(owner, "Chi tiết lô hàng - " + nguyenLieu.getTenNL(), true);
        setSize(700, 450); // Tăng kích thước một chút để hiển thị đủ thông tin
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());
        
        this.nguyenLieu = nguyenLieu;

        // Tiêu đề Dialog
        JLabel lblTitle = new JLabel("Danh sách lô hàng: " + nguyenLieu.getTenNL(), SwingConstants.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        
        JPanel titlePanel = TaoUI.taoPanelCanGiua(3000, 40);
        titlePanel.setBorder(new MatteBorder(1, 0, 1, 0, Color.LIGHT_GRAY));
        titlePanel.add(lblTitle);

        // Định nghĩa các cột cho bảng Nguyên Liệu
        String[] columns = { "Mã Lô", "Hạn Sử Dụng", "Ngày SX", "Số Lượng", "Ngày Nhập", "Trạng thái" };
        modelNL = new DefaultTableModel(columns, 0);
        
        JPanel center = new JPanel(new BorderLayout());
        center.add(titlePanel, BorderLayout.NORTH);
        
        // Sử dụng hàm tạo Table của bạn
        JScrollPane scrollPane = TaoUI.taoTableScroll(modelNL);
        tableNL = (JTable) scrollPane.getViewport().getView(); 
        
        center.add(scrollPane, BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        loadDuLieu();
    }

    private void loadDuLieu() {
        if (nguyenLieu == null) {
            return;
        }

        modelNL.setRowCount(0);
        LoNguyenLieuBUS loNguyenLieuBUS = LoNguyenLieuBUS.getLoNguyenLieuBUS();
        
        // Giả sử bạn đã có hàm layLoChoNguyenLieu trong LoNguyenLieuBUS
        ArrayList<LoNguyenLieu> listLo = loNguyenLieuBUS.layLoChoNguyenLieu(nguyenLieu.getMaNL());
        
        if (listLo != null) {
            for (LoNguyenLieu lo : listLo) {
                String trangThai;
                try {
                    // Logic: Nếu ngày hết hạn nằm SAU ngày hôm nay => Còn hạn
                    trangThai = LocalDate.parse(lo.getHanSuDung()).isAfter(LocalDate.now()) 
                                ? "Còn hạn" 
                                : "Hết hạn";
                } catch (Exception e) {
                    trangThai = "Lỗi ngày";
                }

                modelNL.addRow(new Object[] { 
                    lo.getMaLoNL(),
                    lo.getHanSuDung(), 
                    lo.getNgaySanXuat(), 
                    lo.getSoLuong(),
                    lo.getNgayNhap(), 
                    trangThai 
                });
            }
        }
    }
}