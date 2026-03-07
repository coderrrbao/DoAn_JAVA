package ui.tonkho;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import bus.LoNguyenLieuBUS;
import bus.NguyenLieuBUS;
import dao.NguyenLieuDAO;
import dao.conection.DBConnection;
import dto.NguyenLieu;
import util.ExcelUtil;
import util.RenderColor;
import util.TaoUI;

public class TonKhoNguyenLieuPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    private JButton btnXuatEx;
    private JButton btnNhapExcel;
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

        btnNhapExcel = new JButton("Nhập Excel");
        btnNhapExcel.setPreferredSize(new Dimension(120, 35));
        topContent.add(btnNhapExcel);

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
        RenderColor render1 = new RenderColor(3, 6, new Color(255, 205, 210));
        RenderColor render2 = new RenderColor(5, -1, new Color(255, 205, 210));
        table.getColumnModel().getColumn(3).setCellRenderer(render1);
        table.getColumnModel().getColumn(5).setCellRenderer(render2);
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
        btnXuatEx.addActionListener(e -> {
            NguyenLieuBUS bus = NguyenLieuBUS.getNguyenLieuBUS();
            ExcelUtil.export(bus.layListNguyenLieu(), "TonKhoNguyenLieu");
        });
        btnNhapExcel.addActionListener(e -> importFile());
    }

    private void importFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result != JFileChooser.APPROVE_OPTION)
            return;

        File selectedFile = fileChooser.getSelectedFile();
        if (!selectedFile.getName().toLowerCase().endsWith(".xlsx")) {
            JOptionPane.showMessageDialog(this, "Định dạng file không hợp lệ (.xlsx)", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<NguyenLieu> list;
        try {
            list = ExcelUtil.importFile(selectedFile, row -> {
                String maNL = ExcelUtil.getNullableString(row, 0);
                String tenNL = ExcelUtil.getNullableString(row, 1);
                Double giaVal = ExcelUtil.getDoubleCell(row, 2);
                String donVi = ExcelUtil.getNullableString(row, 3);
                Integer mucCanhBaoVal = ExcelUtil.getIntCell(row, 4);

                NguyenLieu nl = new NguyenLieu();
                nl.setMaNL(maNL);
                nl.setTenNL(tenNL);
                nl.setGia(giaVal != null ? giaVal : 0);
                nl.setDonVi(donVi);
                nl.setMucCanhBao(mucCanhBaoVal != null ? mucCanhBaoVal : 0);
                return nl;
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi đọc file Excel!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            NguyenLieuDAO dao = new NguyenLieuDAO();

            for (NguyenLieu nl : list) {
                if (nl.getMaNL() == null || nl.getMaNL().trim().isEmpty() || !dao.exists(conn, nl.getMaNL())) {
                    dao.themNguyenLieu(nl, conn);
                }
            }
            conn.commit();
            loadDuLieu();
            JOptionPane.showMessageDialog(this, "Import thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            try {
                if (conn != null)
                    conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(this, "Import thất bại! Có dữ liệu trùng hoặc sai. Đã rollback.", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}