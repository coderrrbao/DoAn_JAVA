package ui.nhapkho.sanpham;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import bus.PhieuNhapSanPhamBUS;
import bus.SanPhamBUS;
import dto.LoSanPham;
import dto.PhieuNhapSanPham;
import dto.SanPham;
import util.TaoTinNhan;
import util.TaoUI;

import java.awt.*;

public class ChiTietPhieuNhapSanPhamDialog extends JDialog {

    private JTable tblSanPhamNhap;
    private DefaultTableModel modelSanPham;

    private JTextField txtMaPN, txtNgayNhap, txtMaNV, txtTongTien, txtMaNCC;
    private JTextArea txaGhiChu;
    private JComboBox<String> cbTrangThai;
    private JButton btnSua, btnLuu;

    private PhieuNhapSanPham phieuNhapSanPham;
    private NhapKhoSanPhamPanel nhapKhoSanPhamPanel;

    public ChiTietPhieuNhapSanPhamDialog(Frame parent, PhieuNhapSanPham phieuNhapSanPham,
            NhapKhoSanPhamPanel nhapKhoSanPhamPanel) {
        super(parent, "Quản lý Phiếu Nhập", true);
        setSize(550, 750);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));
        this.phieuNhapSanPham = phieuNhapSanPham;
        this.nhapKhoSanPhamPanel = nhapKhoSanPhamPanel;

        JPanel pnTop = new JPanel(new BorderLayout(0, 10));
        pnTop.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));

        JLabel lblTitle = new JLabel("Danh sách các sản phẩm nhập", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        pnTop.add(lblTitle, BorderLayout.NORTH);

        String[] columnNames = { "Mã", "Tên", "Giá", "Số lượng", "Ngày SX", "Hạn SD" };
        modelSanPham = new DefaultTableModel(columnNames, 0);

        JScrollPane scrollTable = TaoUI.taoTableScroll(modelSanPham);
        tblSanPhamNhap = (JTable) scrollTable.getViewport().getView();
        tblSanPhamNhap.getTableHeader()
                .setPreferredSize(new Dimension(tblSanPhamNhap.getColumnModel().getTotalColumnWidth(), 25));
        scrollTable.setPreferredSize(new Dimension(500, 180));
        pnTop.add(scrollTable, BorderLayout.CENTER);

        add(pnTop, BorderLayout.NORTH);

        JPanel pnForm = new JPanel();
        pnForm.setLayout(new BoxLayout(pnForm, BoxLayout.Y_AXIS));
        pnForm.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        txtMaPN = new JTextField();
        txtNgayNhap = new JTextField();
        txtMaNV = new JTextField();
        txtTongTien = new JTextField();
        txtMaNCC = new JTextField();

        txtMaPN.setEditable(false);
        txtNgayNhap.setEditable(false);
        txtMaNV.setEditable(false);
        txtTongTien.setEditable(false);
        txtMaNCC.setEditable(false);

        txtMaPN.setBackground(Color.WHITE);
        txtNgayNhap.setBackground(Color.WHITE);
        txtMaNV.setBackground(Color.WHITE);
        txtTongTien.setBackground(Color.WHITE);
        txtMaNCC.setBackground(Color.WHITE);

        txaGhiChu = new JTextArea(4, 20);
        txaGhiChu.setLineWrap(true);
        txaGhiChu.setWrapStyleWord(true);
        txaGhiChu.setEditable(false);
        txaGhiChu.setBackground(Color.white);
        JScrollPane scrollGhiChu = new JScrollPane(txaGhiChu);

        cbTrangThai = new JComboBox<>(new String[] { "Đang xử lý", "Đã xác nhận" });
        cbTrangThai.setEnabled(false);

        pnForm.add(taoDong(new JLabel("Mã Phiếu Nhập (PK):")));
        pnForm.add(taoDong(txtMaPN));

        pnForm.add(taoDong(new JLabel("Ngày Nhập (yyyy-mm-dd):")));
        pnForm.add(taoDong(txtNgayNhap));

        pnForm.add(taoDong(new JLabel("Mã Nhân Viên:")));
        pnForm.add(taoDong(txtMaNV));

        pnForm.add(taoDong(new JLabel("Mã Nhà Cung Cấp:")));
        pnForm.add(taoDong(txtMaNCC));

        pnForm.add(taoDong(new JLabel("Tổng Tiền:")));
        pnForm.add(taoDong(txtTongTien));

        pnForm.add(taoDong(new JLabel("Trạng Thái Xử Lý:")));
        pnForm.add(taoDong(cbTrangThai));

        pnForm.add(taoDong(new JLabel("Ghi Chú:")));
        pnForm.add(taoDongArea(scrollGhiChu));

        add(pnForm, BorderLayout.CENTER);

        JPanel pnBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnLuu = new JButton("Lưu Phiếu Nhập");
        btnSua = new JButton("Sửa");
        btnLuu.setEnabled(false);

        pnBottom.add(btnSua);
        pnBottom.add(btnLuu);

        if (!phieuNhapSanPham.getTrangThaiXuLy().equals("Đã xác nhận")) {
            add(pnBottom, BorderLayout.SOUTH);
        }

        ganSuKien();
        loadDuLieu();
        suaLaiGiaoDienTheoQuyen();
    }

    /**
     * Cập nhật hiển thị: Ẩn hoàn toàn các nút Sửa và Lưu nếu không có quyền NK_SUA
     */
    public void suaLaiGiaoDienTheoQuyen() {
        var listQuyen = ui.login.PhienDangNhap.getListQuyen();

        if (!listQuyen.contains("NK_SUA")) {

            btnSua.setVisible(false);
            btnLuu.setVisible(false);

            this.setTitle("Chi Tiết Phiếu Nhập Sản Phẩm (Chế độ chỉ đọc)");
        }
        this.revalidate();
        this.repaint();
    }

    public void loadDuLieu() {
        modelSanPham.setRowCount(0);
        if (phieuNhapSanPham == null) {
            return;
        }
        SanPhamBUS sanPhamBUS = SanPhamBUS.getSanPhamBUS();
        for (LoSanPham loSanPham : phieuNhapSanPham.getListLoSanPham()) {
            SanPham sanPham = sanPhamBUS.timSanPham(loSanPham.getMaSP());
            modelSanPham.addRow(new Object[] { loSanPham.getMaLoSP(), sanPham != null ? sanPham.getTenSP() : "",
                    loSanPham.getGiaNhap(), loSanPham.getSoLuong(), loSanPham.getNgaySanXuat(),
                    loSanPham.getHanSuDung() });
        }
        txtMaNCC.setText(phieuNhapSanPham.getMaNCC());
        txtMaNV.setText(phieuNhapSanPham.getMaNV());
        txtMaPN.setText(phieuNhapSanPham.getMaPN());
        txtNgayNhap.setText(phieuNhapSanPham.getNgayNhap());
        txtTongTien.setText(String.valueOf(phieuNhapSanPham.getTongTien()));
        cbTrangThai.setSelectedItem(phieuNhapSanPham.getTrangThaiXuLy());
        txaGhiChu.setText(phieuNhapSanPham.getGhiChu());

    }

    private JPanel taoDong(JComponent comp) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        panel.add(comp, BorderLayout.CENTER);

        JPanel marginPanel = new JPanel(new BorderLayout());
        marginPanel.add(panel, BorderLayout.CENTER);
        marginPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        return marginPanel;
    }

    private JPanel taoDongArea(JComponent comp) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(comp, BorderLayout.CENTER);

        JPanel marginPanel = new JPanel(new BorderLayout());
        marginPanel.add(panel, BorderLayout.CENTER);
        marginPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        return marginPanel;
    }

    private void ganSuKien() {
        btnSua.addActionListener(e -> {
            btnSua.setEnabled(false);
            btnLuu.setEnabled(true);
            txaGhiChu.setEditable(true);
            cbTrangThai.setEnabled(true);
        });

        btnLuu.addActionListener(e -> {
            String trangThaiMoi = cbTrangThai.getSelectedItem().toString();
            String trangThaiCu = phieuNhapSanPham.getTrangThaiXuLy();

            if (trangThaiCu.equals("Đang xử lý") && trangThaiMoi.equals("Đã xác nhận")) {
                int luaChon = JOptionPane.showConfirmDialog(this,
                        "Sau khi xác nhận, số lượng hàng sẽ được cộng vào kho và không thể sửa.",
                        "Xác nhận nhập kho",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (luaChon != JOptionPane.YES_OPTION)
                    return;
            }

            PhieuNhapSanPham pnh = dongGoiPhieuNhapSanPham();
            if (PhieuNhapSanPhamBUS.getPhieuNhapSanPhamBUS().capNhapPhieuNhapSanPham(pnh)) {
                TaoTinNhan.showAutoCloseMessage("Cập nhật thành công!", "Thông báo", 1);
                nhapKhoSanPhamPanel.loadDuLieu();
                dispose();
            } else {
                TaoTinNhan.showAutoCloseMessage("Cập nhật thất bại!", "Lỗi", 1);
            }
        });

    }

    private PhieuNhapSanPham dongGoiPhieuNhapSanPham() {
        phieuNhapSanPham.setGhiChu(txaGhiChu.getText());
        phieuNhapSanPham.setTrangThaiXuLy(cbTrangThai.getSelectedItem().toString());
        return phieuNhapSanPham;
    }
}