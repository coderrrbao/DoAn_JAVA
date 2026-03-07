package ui.tonkho;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.image.SampleModel;
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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import bus.LoSanPhamBUS;
import bus.SanPhamBUS;
import dao.DanhMucDao;
import dao.SanPhamDAO;
import dao.conection.DBConnection;
import dto.DanhMuc;
import dto.LoSanPham;
import dto.SanPham;
import ui.component.Search_Item;
import util.ExcelUtil;
import util.RenderColor;
import util.TaoUI;

public class TonKhoSanPhamPanel extends JPanel {
    private JTable table;

    private JButton btnXuatEx;
    private JButton btnNhapExcel;
    private JButton btnSua;
    private JButton btnXemLo;
    private JTable tableUI;
    private DefaultTableModel model;
    private ThongKeTonKhoSP thongKeTonKho;

    public TonKhoSanPhamPanel() {

        setLayout(new BorderLayout());
        thongKeTonKho = new ThongKeTonKhoSP();
        add(thongKeTonKho, BorderLayout.NORTH);

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

        String[] columns = { "Mã Sản phẩm", "Tên sản phẩm", "Loại sản phẩm", "Số lượng", "Tổng lô",
                "Lô hết hạn sd", "Mức cảnh báo" };
        model = new DefaultTableModel(columns, 0);

        JPanel center = new JPanel(new BorderLayout());
        center.add(topContent, BorderLayout.NORTH);
        JScrollPane scrollPaneTable = TaoUI.taoTableScroll(model);
        table = (JTable) scrollPaneTable.getViewport().getView();
        RenderColor render = new RenderColor(3, 6, 5, new Color(255, 205, 210));
        table.getColumnModel().getColumn(3).setCellRenderer(render);
        center.add(scrollPaneTable, BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        ganSuKien();
        loadDuLieu();
    }

    public void loadDuLieu() {
        model.setRowCount(0);
        SanPhamBUS sanPhamBUS = SanPhamBUS.getSanPhamBUS();
        LoSanPhamBUS loSanPhamBUS = LoSanPhamBUS.getLoSanPhamBUS();
        ArrayList<SanPham> listSanPham = sanPhamBUS.layListSanPham();
        for (SanPham sanPham : listSanPham) {
            model.addRow(new Object[] { sanPham.getMaSP(), sanPham.getTenSP(), sanPham.getLoaiNuoc(),
                    loSanPhamBUS.laySoLuongSanPhamTrongKho(sanPham.getMaSP()),
                    loSanPhamBUS.layTongLoChoSanPham(sanPham.getMaSP()),
                    loSanPhamBUS.layTongLoHetHangChoSanPham(sanPham.getMaSP()), sanPham.getMucCanhBao() });
        }
        thongKeTonKho.loadDuLieu();
    }

    private void ganSuKien() {
        btnXemLo.addActionListener(e -> {
            int dongChon = table.getSelectedRow();
            if (dongChon < 0) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn một sản phẩm từ danh sách để xem chi tiết!",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            String maSP = model.getValueAt(dongChon, 0).toString();
            SanPham sanPham = SanPhamBUS.getSanPhamBUS().timSanPham(maSP);
            if (sanPham == null) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy thông tin sản phẩm: " + maSP,
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                ChiTietTonKhoSPDialog chiTietTonKhoSPDialog = new ChiTietTonKhoSPDialog(null, sanPham);
                chiTietTonKhoSPDialog.setVisible(true);
            }
        });

        btnSua.addActionListener(e -> {
            int dongChon = table.getSelectedRow();
            if (dongChon < 0) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn một sản phẩm từ danh sách để sửa cảnh báo!",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            String maSP = model.getValueAt(dongChon, 0).toString();
            SanPham sanPham = SanPhamBUS.getSanPhamBUS().timSanPham(maSP);

            if (sanPham == null) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy thông tin sản phẩm: " + maSP,
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                SuaCanhBaoDialogSP suaCanhBaoDialog = new SuaCanhBaoDialogSP(this, sanPham);
                suaCanhBaoDialog.setVisible(true);
            }
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

        List<SanPham> list;
        try {
            DanhMucDao danhMucDao = new DanhMucDao();
            list = ExcelUtil.importFile(selectedFile, row -> {
                String maSP = ExcelUtil.getNullableString(row, 0);
                String tenSP = ExcelUtil.getNullableString(row, 1);
                String tenDM = ExcelUtil.getNullableString(row, 2);
                Long giaBanVal = ExcelUtil.getDoubleCell(row, 3) != null ? ExcelUtil.getDoubleCell(row, 3).longValue()
                        : null;
                String loaiNuoc = ExcelUtil.getNullableString(row, 4);
                Integer theTichVal = ExcelUtil.getIntCell(row, 5);
                String trangThaiXuLy = ExcelUtil.getNullableString(row, 6);
                String anh = ExcelUtil.getNullableString(row, 7);
                Integer mucCanhBaoVal = ExcelUtil.getIntCell(row, 8);

                DanhMuc dm = tenDM != null ? danhMucDao.timDanhMucTheoTen(tenDM) : null;
                SanPham sp = new SanPham();
                sp.setMaSP(maSP);
                sp.setTenSP(tenSP);
                sp.setDanhMuc(dm);
                sp.setGiaBan(giaBanVal != null ? giaBanVal : 0);
                sp.setLoaiNuoc(loaiNuoc != null ? loaiNuoc : "");
                sp.setTheTich(theTichVal != null ? theTichVal : 0);
                sp.setTrangThaiXuLy(trangThaiXuLy != null ? trangThaiXuLy : "Chờ xử lý");
                sp.setAnh(anh);
                sp.setMucCanhBao(mucCanhBaoVal != null ? mucCanhBaoVal : 0);
                return sp;
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi đọc file Excel!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            SanPhamDAO dao = new SanPhamDAO();

            for (SanPham sp : list) {
                if (sp.getMaSP() == null || sp.getMaSP().trim().isEmpty() || !dao.exists(conn, sp.getMaSP())) {
                    if (sp.getDanhMuc() != null) {
                        dao.themSanPham(sp, conn);
                    }
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