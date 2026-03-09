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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import bus.LoNguyenLieuBUS;
import bus.LoSanPhamBUS;
import bus.SanPhamBUS;
import dao.DanhMucDao;
import dao.SanPhamDAO;
import dao.conection.DBConnection;
import dto.DanhMuc;
import dto.LoNguyenLieu;
import dto.SanPham;
import util.RenderColor;
import util.TaoTinNhan;
import util.TaoUI;

public class TonKhoSanPhamPanel extends JPanel {
    private JTable table;
    private JButton btnSua;
    private JButton btnXemLo;
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
        table.getColumnModel().getColumn(5).setCellRenderer(render);
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
            int soLuong = 0;
            if (sanPham.getLoaiNuoc().equals("Pha chế")) {
                soLuong = LoNguyenLieuBUS.getLoNguyenLieuBUS().laySoLuongSanPhamPhaCheTrongKho(sanPham);
            } else {
                soLuong = loSanPhamBUS.laySoLuongSanPhamTrongKho(sanPham.getMaSP());
            }

            model.addRow(new Object[] { sanPham.getMaSP(), sanPham.getTenSP(), sanPham.getLoaiNuoc(),
                    soLuong,
                    sanPham.getLoaiNuoc().equals("Pha chế") ? 0 : loSanPhamBUS.layTongLoChoSanPham(sanPham.getMaSP()),
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
                if (sanPham.getLoaiNuoc() != null && sanPham.getLoaiNuoc().equals("Pha chế")
                        && (sanPham.getCongThuc() == null
                                || sanPham.getCongThuc().getListChiTietCongThuc().isEmpty())) {
                    TaoTinNhan.showAutoCloseMessage("Sản phẩm chưa có công thức", "Thông báo", 1);
                } else {
                    ChiTietTonKhoSPDialog chiTietTonKhoSPDialog = new ChiTietTonKhoSPDialog(null, sanPham);
                    chiTietTonKhoSPDialog.setVisible(true);
                }
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
    }
}