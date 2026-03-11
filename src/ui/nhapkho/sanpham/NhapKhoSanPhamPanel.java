package ui.nhapkho.sanpham;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
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
import bus.PhieuNhapSanPhamBUS;
import dto.NhaCungCap;
import dto.PhieuNhapSanPham;
import ui.component.LocNgay_Item;
import ui.login.PhienDangNhap;
import util.TaoTinNhan;
import util.TaoUI;

public class NhapKhoSanPhamPanel extends JPanel {
    private JButton nhapHangBtn, xemChiTietBtn, xoaBtn, NhapExcelBtn, XuatExcelBtn;
    private LocNgay_Item locNgay_Item;
    private JTable table;
    private DefaultTableModel model;
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

        if (!listQuyen.contains("NK_TAO")) {
            nhapHangBtn.setVisible(false);
        }

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
            if (dongChon < 0) {
                TaoTinNhan.showAutoCloseMessage("Vui lòng chọn phiếu nhập để xóa", "Thông báo", 1);
                return;
            }
            if (!model.getValueAt(dongChon, 5).toString().equals("Đang xử lý")) {
                TaoTinNhan.showAutoCloseMessage("Phiếu nhập đã xác nhận, không thể xóa", "Thông báo", 1);
                return;
            }

            int confirm = javax.swing.JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn xóa phiếu nhập này?", "Xác nhận xóa",
                    javax.swing.JOptionPane.YES_NO_OPTION);

            if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                PhieuNhapSanPhamBUS phieuNhapSanPhamBUS = PhieuNhapSanPhamBUS.getPhieuNhapSanPhamBUS();
                String maPN = model.getValueAt(dongChon, 0).toString();
                if (phieuNhapSanPhamBUS.xoaPhieuNhapSanPham(phieuNhapSanPhamBUS.timPhieuNhapSanPham(maPN))) {
                    TaoTinNhan.showAutoCloseMessage("Xóa thành công!", "Thông báo", 1);
                    loadDuLieu();
                }
            }
        });

        locNgay_Item.setEvent(() -> {
            loadDuLieu();
        });

        XuatExcelBtn.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Chọn nơi lưu file");
            fc.setSelectedFile(new File("DanhSachPhieuNhapSP.xlsx"));

            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();

                if (!file.getName().endsWith(".xlsx")) {
                    file = new File(file.getAbsolutePath() + ".xlsx");
                }

                if (PhieuNhapSanPhamBUS.getPhieuNhapSanPhamBUS().xuatExcel(file)) {
                    JOptionPane.showMessageDialog(this, "Xuất file thành công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi ghi file!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        NhapExcelBtn.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Chọn file Excel để nhập");

            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();

                if (PhieuNhapSanPhamBUS.getPhieuNhapSanPhamBUS().nhapExcel(file)) {
                    loadDuLieu();
                    JOptionPane.showMessageDialog(this, "Nhập dữ liệu thành công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Nhập thất bại! Kiểm tra file hoặc dữ liệu trùng.",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
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
}
