package ui.nhapkho.sanpham;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import bus.NhaCungCapBUS;
import bus.NhanVienBUS;
import bus.PhieuNhapSanPhamBUS;
import dao.NhanVienDAO;
import dao.conection.DBConnection;
import dto.NhaCungCap;
import dto.NhanVien;
import dto.PhieuNhapNguyenLieu;
import dto.PhieuNhapSanPham;
import ui.component.LocNgay_Item;
import ui.login.PhienDangNhap;
import util.ExcelUtil;
import util.TaoTinNhan;
import util.TaoUI;

public class NhapKhoSanPhamPanel extends JPanel {
    private JButton nhapHangBtn, xemChiTietBtn, xoaBtn, NhapExcelBtn, XuatExcelBtn;
    private LocNgay_Item locNgay_Item;
    private JTable table;
    private DefaultTableModel model;
    private PhieuNhapSanPhamBUS bus = new PhieuNhapSanPhamBUS();

    public NhapKhoSanPhamPanel() {
        setLayout(new BorderLayout());
        JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 45);
        top.setBackground(Color.WHITE);
        nhapHangBtn = new JButton("Thêm");
        xemChiTietBtn = new JButton("Xem Chi tiết");
        xoaBtn = new JButton("Xóa");
        NhapExcelBtn = new JButton("Nhập Excel");
        XuatExcelBtn = new JButton("Xuất Excel");

        TaoUI.setFixSize(nhapHangBtn, 100, 32);
        TaoUI.setFixSize(xemChiTietBtn, 120, 32);
        TaoUI.setFixSize(xoaBtn, 100, 32);
        TaoUI.setFixSize(NhapExcelBtn, 120, 32);
        TaoUI.setFixSize(XuatExcelBtn, 120, 32);

        locNgay_Item = new LocNgay_Item(400, 32);
        top.add(locNgay_Item);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(nhapHangBtn);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(xemChiTietBtn);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(xoaBtn);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(NhapExcelBtn);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(XuatExcelBtn);
        top.add(Box.createHorizontalGlue());

        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel();
        model.addColumn("Mã Phiếu nhập");
        model.addColumn("Ngày nhập");
        model.addColumn("Nhân viên tạo phiếu");
        model.addColumn("Ghi chú");
        model.addColumn("Nhà cung cấp");
        model.addColumn("Trạng thái");

        JScrollPane scrollPane = TaoUI.taoTableScroll(model);
        table = (JTable) scrollPane.getViewport().getView();

        add(scrollPane, BorderLayout.CENTER);

        loadDuLieu();
        ganSuKien();
    }

    /**
     * Cập nhật hiển thị: Ẩn các nút Thêm, Xóa, Xem dựa trên danh sách quyền của
     * user
     */
    public void suaLaiGiaoDienTheoQuyen() {
        var listQuyen = PhienDangNhap.getListQuyen();

        // 1. Quyền Thêm mới phiếu nhập (NK_TAO)
        if (!listQuyen.contains("NK_TAO")) {
            nhapHangBtn.setVisible(false);
        }

        // 2. Quyền Xóa phiếu nhập (NK_XOA)
        if (!listQuyen.contains("NK_XOA")) {
            xoaBtn.setVisible(false);
        }
    }

    private void ganSuKien() {
        nhapHangBtn.addActionListener(e -> {
            JDialog dialogNhapHang = new NhapKhoSanPhamDialog(this);
            dialogNhapHang.setVisible(true);
        });

        xemChiTietBtn.addActionListener(e -> {
            int dongChon = table.getSelectedRow();
            PhieuNhapSanPhamBUS phieuNhapSanPhamBUS = PhieuNhapSanPhamBUS.getPhieuNhapSanPhamBUS();
            if (dongChon >= 0) {
                PhieuNhapSanPham phieuNhapSanPham = phieuNhapSanPhamBUS
                        .timPhieuNhapSanPham(model.getValueAt(dongChon, 0).toString());
                ChiTietPhieuNhapSanPhamDialog chiTietPhieuNhapSanPhamDialog = new ChiTietPhieuNhapSanPhamDialog(null,
                        phieuNhapSanPham, this);
                chiTietPhieuNhapSanPhamDialog.setVisible(true);
            } else {
                TaoTinNhan.showAutoCloseMessage("Vui lòng chọn phiếu nhập để xem chi tiết", "Thông báo", dongChon);
            }
        });

        xoaBtn.addActionListener(e -> {
            int dongChon = table.getSelectedRow();
            PhieuNhapSanPhamBUS phieuNhapSanPhamBUS = PhieuNhapSanPhamBUS.getPhieuNhapSanPhamBUS();
            if (dongChon >= 0) {
                if (model.getValueAt(dongChon, 5).toString().equals("Đang xử lý")) {
                    if (phieuNhapSanPhamBUS.xoaPhieuNhapSanPham(
                            phieuNhapSanPhamBUS.timPhieuNhapSanPham(model.getValueAt(dongChon, 0).toString()))) {
                        TaoTinNhan.showAutoCloseMessage("Xóa phiếu nhập sản phẩm thành công", "Thông báo",
                                dongChon);
                        loadDuLieu();
                    }
                } else {
                    TaoTinNhan.showAutoCloseMessage("Phiếu nhập sản phẩm đã xác nhận, không thể xóa", "Thông báo",
                            dongChon);
                }
            } else {
                TaoTinNhan.showAutoCloseMessage("Vui lòng chọn phiếu nhập để xóa", "Thông báo", dongChon);
            }
        });

        locNgay_Item.setEvent(() -> {
            loadDuLieu();
        });

        NhapExcelBtn.addActionListener(e -> importFile());
        XuatExcelBtn.addActionListener(e -> ExcelUtil.export(bus.layListPhieuNhapSanPham(), "DanhSachNhapKhoSanPham"));
    }

    public void loadDuLieu() {
        model.setRowCount(0);
        PhieuNhapSanPhamBUS phieuNhapSanPhamBUS = PhieuNhapSanPhamBUS.getPhieuNhapSanPhamBUS();

        for (PhieuNhapSanPham phieuNhapSanPham : phieuNhapSanPhamBUS.layListPhieuNhapSanPham()) {
            if (locNgay_Item.ngayTrongKhoan(phieuNhapSanPham.getNgayNhap())) {
                NhaCungCap nhaCungCap = NhaCungCapBUS.getNhaCungCapBUS().timNhaCungCap(phieuNhapSanPham.getMaNCC());
                model.addRow(new Object[] { phieuNhapSanPham.getMaPN(), phieuNhapSanPham.getNgayNhap(),
                        phieuNhapSanPham.getMaNV(), phieuNhapSanPham.getGhiChu(),
                        nhaCungCap != null ? nhaCungCap.getTenNCC() : "",
                        phieuNhapSanPham.getTrangThaiXuLy() });
            }
        }
    }

    private void importFile() {
    JFileChooser fileChooser = new JFileChooser();
    int result = fileChooser.showOpenDialog(this);

    if(result!=JFileChooser.APPROVE_OPTION)
    {
        return;
    }

    File selectedFile = fileChooser.getSelectedFile();

    if(!selectedFile.getName().toLowerCase().endsWith(".xlsx"))
    {
        JOptionPane.showMessageDialog(
                this,
                "Định dạng file không hợp lệ (.xlsx)",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        return;
    }

    List<PhieuNhapSanPham> list;

    try
    {
        list = ExcelUtil.importFile(selectedFile, row -> {

            String maNV = ExcelUtil.getNullableString(row, 0);
            String tenNV = ExcelUtil.getNullableString(row, 1);
            String gioiTinh = ExcelUtil.getNullableString(row, 2);
            String ngaySinh = ExcelUtil.getNullableString(row, 3);
            String ngayVaoLam = ExcelUtil.getNullableString(row, 4);
            String sdt = ExcelUtil.getNullableString(row, 5);
            String diaChi = ExcelUtil.getNullableString(row, 6);
            String chucVu = ExcelUtil.getNullableString(row, 7);
            String taiKhoan = ExcelUtil.getNullableString(row, 8);

            boolean trangThai = ExcelUtil.getBooleanCell(row, 9);

            return new NhanVien(
                    maNV, tenNV, gioiTinh,
                    ngaySinh, ngayVaoLam,
                    sdt, diaChi, chucVu,
                    taiKhoan, null,
                    trangThai);
        });

    }catch(
    Exception e)
    {
        JOptionPane.showMessageDialog(
                this,
                "Lỗi đọc file Excel!",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        return;
    }

    Connection conn = null;

    try
    {
        conn = DBConnection.getConnection();
        conn.setAutoCommit(false);

        NhanVienDAO dao = new NhanVienDAO();

        for (NhanVien nv : list) {
            Boolean exist = dao.exist(nv.getMaNV());
            if (!exist) {
                dao.insert(conn, nv);
            }
        }

        conn.commit();

        NhanVienBUS.getNhanVienBUS().yeuCauCapNhat();
        layDanhSachNhanVien();

        JOptionPane.showMessageDialog(
                this,
                "Import Thành công:",
                "Thông báo",
                JOptionPane.INFORMATION_MESSAGE);

    }catch(
    Exception e)
    {
        try {
            if (conn != null)
                conn.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        JOptionPane.showMessageDialog(this,
                "Import thất bại!\nCó dữ liệu trùng hoặc sai.\nĐã rollback toàn bộ.",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
    }finally
    {
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
