package ui.tonkho;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import bus.LoNguyenLieuBUS;
import bus.NguyenLieuBUS;
import dto.NguyenLieu;
import util.TaoUI;

public class TonKhoNguyenLieuPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    private JButton btnXuatEx;
    private JButton btnXemLo;
    private JButton btnSua;

    // Giả sử bạn có class ThongKeTonKhoNL tương tự ThongKeTonKhoSP
    private ThongKeTonKhoNL thongKeTonKho;

    public TonKhoNguyenLieuPanel() {

        setLayout(new BorderLayout());

        // --- Phần Top (Thống kê) ---
        thongKeTonKho = new ThongKeTonKhoNL();
        add(thongKeTonKho, BorderLayout.NORTH);

        // --- Phần Center (Chứa Nút bấm và Bảng) ---
        JPanel topContent = new JPanel();
        topContent.setPreferredSize(new Dimension(100, 45));
        topContent.setLayout(new FlowLayout(FlowLayout.LEFT));
        topContent.setBackground(Color.WHITE);

        btnXuatEx = new JButton("Xuất excel");
        btnXuatEx.setPreferredSize(new Dimension(120, 35));
        topContent.add(btnXuatEx);

        btnXemLo = new JButton("Xem lô");
        btnXemLo.setPreferredSize(new Dimension(80, 35));
        topContent.add(btnXemLo);

        btnSua = new JButton("Sửa cảnh báo");
        btnSua.setPreferredSize(new Dimension(120, 35));
        topContent.add(btnSua);

        // Cấu trúc cột giống bên Sản Phẩm (Tùy chỉnh lại theo Nguyên liệu của bạn)
        String[] columns = { "Mã Nguyên liệu", "Tên nguyên liệu", "Đơn vị tính", "Số lượng", "Tổng lô",
                "Lô hết hạn sd", "Mức cảnh báo" };
        model = new DefaultTableModel(columns, 0);

        JPanel center = new JPanel(new BorderLayout());
        center.add(topContent, BorderLayout.NORTH);

        JScrollPane scrollPaneTable = TaoUI.taoTableScroll(model);
        table = (JTable) scrollPaneTable.getViewport().getView();
        center.add(scrollPaneTable, BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);

        // Gọi 2 hàm khởi tạo chức năng
        ganSuKien();
        loadDuLieu();
    }

    public void loadDuLieu() {
        model.setRowCount(0); // Xóa dữ liệu cũ trên bảng

        NguyenLieuBUS nguyenLieuBUS = NguyenLieuBUS.getNguyenLieuBUS();
        LoNguyenLieuBUS loNguyenLieuBUS = LoNguyenLieuBUS.getLoNguyenLieuBUS();

        ArrayList<NguyenLieu> listNguyenLieu = nguyenLieuBUS.layListNguyenLieu();

        if (listNguyenLieu != null) {
            for (NguyenLieu nl : listNguyenLieu) {
                model.addRow(new Object[] {
                        nl.getMaNL(),
                        nl.getTenNL(),
                        nl.getDonVi(),
                        loNguyenLieuBUS.laySoLuongNguyenLieuTrongKho(nl.getMaNL()),
                        loNguyenLieuBUS.layTongLoChoNguyenLieu(nl.getMaNL()),
                        loNguyenLieuBUS.layTongLoHetHangChoNguyenLieu(nl.getMaNL()),
                        nl.getMucCanhBao()
                });
            }
        }
        if (thongKeTonKho != null) {
            thongKeTonKho.loadDuLieu();
        }
    }

    private void ganSuKien() {
        btnXemLo.addActionListener(e -> {
    
            int dongChon = table.getSelectedRow();
            if (dongChon < 0) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn một nguyên liệu từ danh sách để xem chi tiết!",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            String maNL = model.getValueAt(dongChon, 0).toString();
            NguyenLieu nguyenLieu = NguyenLieuBUS.getNguyenLieuBUS().timNguyenLieu(maNL);
            if (nguyenLieu == null) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy thông tin nguyên liệu: " + maNL,
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                ChiTietTonKhoNLDialog chiTietTonKhoNLDialog = new ChiTietTonKhoNLDialog(null, nguyenLieu);
                chiTietTonKhoNLDialog.setVisible(true);
            }
        });

 
        btnSua.addActionListener(e -> {
            int dongChon = table.getSelectedRow();

            if (dongChon < 0) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn một nguyên liệu từ danh sách để sửa cảnh báo!",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String maNL = model.getValueAt(dongChon, 0).toString();
            NguyenLieu nguyenLieu = NguyenLieuBUS.getNguyenLieuBUS().timNguyenLieu(maNL);

            if (nguyenLieu == null) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy thông tin nguyên liệu: " + maNL,
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                SuaCanhBaoDialogNL dialog = new SuaCanhBaoDialogNL(this, nguyenLieu);
                dialog.setVisible(true);
            }
        });
    }
}