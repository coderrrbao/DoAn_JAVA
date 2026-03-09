package ui.xuatkho.nguyenlieu;

import bus.NguyenLieuBUS;
import bus.PhieuHuyNguyenLieuBUS;
import dto.LoNguyenLieu;
import dto.NguyenLieu;
import dto.PhieuHuyNguyenLieu;
import util.TaoUI;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ChiTietPhieuXuatNguyenLieuDialog extends JDialog {
    private JTable tblChiTiet;
    private DefaultTableModel modelChiTiet;
    private JTextField txtMaPH, txtNgay, txtNV, txtLyDo, txtTong;
    private JComboBox<String> cbTrangThai;
    private JButton btnLuu, btnSua, btnXoa;
    private PhieuHuyNguyenLieu phieuHuy;
    private XuatKhoNguyenLieuPanel parent;

    public ChiTietPhieuXuatNguyenLieuDialog(Frame owner, PhieuHuyNguyenLieu ph, XuatKhoNguyenLieuPanel parent) {
        super(owner, "Chi Tiết Phiếu Hủy Nguyên Liệu", true);
        this.phieuHuy = ph;
        this.parent = parent;
        setSize(480, 650); // Thu gọn kích thước để đồng bộ form
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        // ==================== PHẦN TOP (TABLE) ====================
        JPanel pnTop = new JPanel(new BorderLayout(5, 10));
        pnTop.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));

        // CHỈNH SỬA: Chặn edit table cell
        modelChiTiet = new DefaultTableModel(new String[] { "Mã Lô", "Tên NL", "Số lượng", "Giá" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Sử dụng TaoUI để tạo scroll bảng đồng bộ
        JScrollPane scrollChiTiet = TaoUI.taoTableScroll(modelChiTiet);
        tblChiTiet = (JTable) scrollChiTiet.getViewport().getView();
        tblChiTiet.getTableHeader().setReorderingAllowed(false);
        scrollChiTiet.setPreferredSize(new Dimension(400, 150));

        pnTop.add(scrollChiTiet, BorderLayout.CENTER);
        add(pnTop, BorderLayout.NORTH);

        // ==================== PHẦN FORM (CENTER) ====================
        JPanel pnForm = new JPanel();
        pnForm.setLayout(new BoxLayout(pnForm, BoxLayout.Y_AXIS));
        pnForm.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        txtMaPH = new JTextField(ph.getMaPH());
        txtNgay = new JTextField(ph.getNgayHuy().toString());
        txtNV = new JTextField(ph.getMaNV());
        txtLyDo = new JTextField(ph.getLyDo());
        txtTong = new JTextField(String.format("%,.0f VNĐ", ph.getTongTien()));
        cbTrangThai = new JComboBox<>(new String[] { "Đang xử lý", "Đã xác nhận" });
        cbTrangThai.setSelectedItem(ph.getTrangThaiXuLy());

        // CHỈNH SỬA: Chặn focus các ô text
        JTextField[] fields = { txtMaPH, txtNgay, txtNV, txtLyDo, txtTong };
        for (JTextField f : fields) {
            f.setEditable(false);
            f.setBackground(Color.WHITE);
            f.setFocusable(false);
        }
        cbTrangThai.setEnabled(false);

        // Thêm các thành phần theo cấu trúc: 1 dòng Label - 1 dòng Input
        pnForm.add(taoDong(new JLabel("Mã Phiếu:")));
        pnForm.add(taoDong(txtMaPH));

        pnForm.add(taoDong(new JLabel("Ngày Hủy:")));
        pnForm.add(taoDong(txtNgay));

        pnForm.add(taoDong(new JLabel("Nhân Viên:")));
        pnForm.add(taoDong(txtNV));

        pnForm.add(taoDong(new JLabel("Lý Do:")));
        pnForm.add(taoDong(txtLyDo));

        pnForm.add(taoDong(new JLabel("Tổng Tiền:")));
        pnForm.add(taoDong(txtTong));

        pnForm.add(taoDong(new JLabel("Trạng Thái:")));
        pnForm.add(taoDong(cbTrangThai));

        add(pnForm, BorderLayout.CENTER);

        // ==================== PHẦN BOTTOM (NÚT BẤM) ====================
        JPanel pnBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnSua = new JButton("Sửa");
        btnLuu = new JButton("Lưu");
        btnXoa = new JButton("Xóa");
        btnLuu.setEnabled(false);

        pnBtn.add(btnSua);
        pnBtn.add(btnLuu);

        add(pnBtn, BorderLayout.SOUTH);

        // Ẩn nút nếu phiếu đã được xác nhận
        if ("Đã xác nhận".equals(ph.getTrangThaiXuLy())) {
            btnSua.setVisible(false);
            btnLuu.setVisible(false);
        }

        loadData();
        ganSuKien();
        suaLaiGiaoDienTheoQuyen();
    }

    // Hàm tạo khoảng cách đều đặn cho form nhập liệu
    private JPanel taoDong(JComponent comp) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        panel.add(comp, BorderLayout.CENTER);

        JPanel marginPanel = new JPanel(new BorderLayout());
        marginPanel.add(panel, BorderLayout.CENTER);
        marginPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        return marginPanel;
    }

    public void suaLaiGiaoDienTheoQuyen() {
        var listQuyen = ui.login.PhienDangNhap.getListQuyen();

        // Kiểm tra quyền SỬA (XK_SUA)
        if (!listQuyen.contains("XK_SUA")) {
            // Ẩn các nút thao tác chỉnh sửa
            btnSua.setVisible(false);
            btnLuu.setVisible(false);

            // Cập nhật tiêu đề để thông báo đây là chế độ chỉ đọc
            this.setTitle("Chi Tiết Phiếu Hủy Nguyên Liệu (Chế độ chỉ đọc)");
        }
        this.revalidate();
        this.repaint();
    }

    private void loadData() {
        modelChiTiet.setRowCount(0);
        ArrayList<LoNguyenLieu> list = phieuHuy.getListLoNguyenLieuHuy();
        if (list != null) {
            for (LoNguyenLieu lo : list) {
                NguyenLieu nl = NguyenLieuBUS.getNguyenLieuBUS().timNguyenLieu(lo.getMaNL());
                modelChiTiet.addRow(
                        new Object[] {
                                lo.getMaLoNL(), (nl != null ? nl.getTenNL() : "N/A"), lo.getSoLuong(), lo.getGiaNhap()
                        });
            }
        }
    }

    private void ganSuKien() {
        btnSua.addActionListener(e -> {
            txtLyDo.setEditable(true);
            txtLyDo.setFocusable(true);
            txtLyDo.requestFocus();
            cbTrangThai.setEnabled(true);
            btnLuu.setEnabled(true);
            btnSua.setEnabled(false);
        });

        btnLuu.addActionListener(e -> {
            phieuHuy.setLyDo(txtLyDo.getText());
            phieuHuy.setTrangThaiXuLy(cbTrangThai.getSelectedItem().toString());
            if (PhieuHuyNguyenLieuBUS.getPhieuHuyNguyenLieuBUS().capNhatPhieuHuy(phieuHuy)) {
                parent.loadDuLieu();
                dispose();
            }
        });
    }
}