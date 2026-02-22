package ui.nhacungcap;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import bus.NguyenLieuBUS;
import bus.SanPhamBUS;
import dto.ChiTietNhaCungCap;
import dto.NguyenLieu;
import dto.NhaCungCap;
import dto.SanPham;
import util.TaoUI;

import java.awt.*;
import java.util.ArrayList;

public class ChiTietNhaCungCapDialog extends JDialog {

    private JComboBox<String> cbLoaiHang;
    private JTextField txtMaNCC, txtTenNCC, txtSoDienThoai, txtDiaChi;
    private JButton btnLuu, btnDong;

    // Khai báo thêm các component cho phần Top
    private JButton btnThemHang, btnXoaHang;
    private JTable tblHangHoa;
    private DefaultTableModel modelHangHoa;

    private NhaCungCapUI nhaCungCapUI;
    private NhaCungCap nhaCungCap;

    public ChiTietNhaCungCapDialog(Frame parent, NhaCungCap nhaCungCap, NhaCungCapUI nhaCungCapUI) {
        super(parent, "Quản lý Nhà Cung Cấp", true);
        setSize(480, 600); // Tăng kích thước để có không gian chứa JTable
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));
        this.nhaCungCapUI = nhaCungCapUI;
        this.nhaCungCap = nhaCungCap;
        // ==================== PHẦN TOP ====================
        JPanel pnTop = new JPanel(new BorderLayout(5, 10));
        pnTop.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));

        // --- Dòng 1: Thêm, Xóa, ComboBox ---
        JPanel pnRow1 = TaoUI.taoPanelBoxLayoutNgang(480, 30);
        btnThemHang = new JButton("Thêm");
        btnXoaHang = new JButton("Xóa");
        cbLoaiHang = new JComboBox<>(new String[] { "Sản phẩm", "Nguyên liệu" });

        TaoUI.setFixSize(cbLoaiHang, 150, 30);

        pnRow1.add(btnThemHang);
        pnRow1.add(Box.createRigidArea(new Dimension(10, 0)));
        pnRow1.add(btnXoaHang);
        pnRow1.add(Box.createHorizontalGlue());
        pnRow1.add(cbLoaiHang);

        // --- Dòng 2: JTable ---
        String[] columnNames = { "Mã", "Tên", "Loại", "Giá nhập" };
        modelHangHoa = new DefaultTableModel(columnNames, 0);

        JScrollPane scrollHangHoa = TaoUI.taoTableScroll(modelHangHoa);
        tblHangHoa = (JTable) scrollHangHoa.getViewport().getView();
        tblHangHoa.getTableHeader()
                .setPreferredSize(new Dimension(tblHangHoa.getColumnModel().getTotalColumnWidth(), 25));

        scrollHangHoa.setPreferredSize(new Dimension(400, 150));

        pnTop.add(pnRow1, BorderLayout.NORTH);
        pnTop.add(scrollHangHoa, BorderLayout.CENTER);

        add(pnTop, BorderLayout.NORTH);

        // ==================== PHẦN FORM (CENTER) ====================
        JPanel pnForm = new JPanel();
        pnForm.setLayout(new BoxLayout(pnForm, BoxLayout.Y_AXIS));
        pnForm.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        // Khởi tạo TextFields
        txtMaNCC = new JTextField();
        txtTenNCC = new JTextField();
        txtSoDienThoai = new JTextField();
        txtDiaChi = new JTextField();

        // Thêm các thành phần theo cấu trúc: 1 dòng Label - 1 dòng TextField
        pnForm.add(taoDong(new JLabel("Mã Nhà Cung Cấp:")));
        pnForm.add(taoDong(txtMaNCC));

        pnForm.add(taoDong(new JLabel("Tên Nhà Cung Cấp:")));
        pnForm.add(taoDong(txtTenNCC));

        pnForm.add(taoDong(new JLabel("Số Điện Thoại:")));
        pnForm.add(taoDong(txtSoDienThoai));

        pnForm.add(taoDong(new JLabel("Địa Chỉ:")));
        pnForm.add(taoDong(txtDiaChi));

        add(pnForm, BorderLayout.CENTER);

        // ==================== PHẦN BOTTOM (NÚT BẤM) ====================
        JPanel pnBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnLuu = new JButton("Lưu");
        btnDong = new JButton("Đóng");

        btnDong.addActionListener(e -> dispose());

        if (nhaCungCap != null) {
            cbLoaiHang.removeAllItems();
            if (!nhaCungCap.getCungCapNL() && !nhaCungCap.getCungCapSP()) {
                cbLoaiHang.addItem("Chưa cập nhập");
                cbLoaiHang.setEnabled(false);
            }

            else if (nhaCungCap.getCungCapNL() && nhaCungCap.getCungCapSP()) {
                cbLoaiHang.addItem("Sản phẩm");
                cbLoaiHang.addItem("Nguyên liệu");
            } else if (nhaCungCap.getCungCapSP()) {
                cbLoaiHang.addItem("Sản phẩm");
                cbLoaiHang.setEnabled(false);
            } else if (nhaCungCap.getCungCapNL()) {
                cbLoaiHang.addItem("Nguyên liệu");
                cbLoaiHang.setEnabled(false);
            }

        }
        cbLoaiHang.setSelectedIndex(0);

        pnBottom.add(btnLuu);
        pnBottom.add(btnDong);
        add(pnBottom, BorderLayout.SOUTH);

        loadDuLieu();
        ganSuKien();

    }

    public void loadDuLieu() {
        if (nhaCungCap == null) {
            return;
        }

        txtMaNCC.setText(nhaCungCap.getMaNCC());
        txtTenNCC.setText(nhaCungCap.getTenNCC());
        txtDiaChi.setText(nhaCungCap.getDiaChi());
        txtSoDienThoai.setText(nhaCungCap.getSoDienThoai());

        modelHangHoa.setRowCount(0);
        if (cbLoaiHang.getSelectedItem().toString().equals("Sản phẩm")) {
            SanPhamBUS sanPhamBUS = SanPhamBUS.getSanPhamBUS();
            for (ChiTietNhaCungCap ct : nhaCungCap.getListChiTietNhaCungCap()) {
                if (ct.getLoaiDoiTuong().equals("Sản phẩm")) {
                    SanPham sp = sanPhamBUS.timSanPham(ct.getMaDoiTuong());
                    if (sp != null) {
                        modelHangHoa.addRow(new Object[] { sp.getMaSP(), sp.getTenSP(), "Sản phẩm", ct.getGiaNhap() });
                    }
                }
            }
        } else if (cbLoaiHang.getSelectedItem().toString().equals("Nguyên liệu")) {
            NguyenLieuBUS nguyenLieuBUS = NguyenLieuBUS.getNguyenLieuBUS();
            for (ChiTietNhaCungCap ct : nhaCungCap.getListChiTietNhaCungCap()) {
                if (ct.getLoaiDoiTuong().equals("Nguyên liệu")) {
                    NguyenLieu nl = nguyenLieuBUS.timNguyenLieu(ct.getMaDoiTuong());
                    if (nl != null) {
                        modelHangHoa
                                .addRow(new Object[] { nl.getMaNL(), nl.getTenNL(), "Nguyên liệu", ct.getGiaNhap() });
                    }
                }
            }
        }

    }

    public void ganSuKien() {
        cbLoaiHang.addActionListener(e -> {
            loadDuLieu();
        });
    }

    // Hàm hỗ trợ tạo 1 dòng (Panel) với chiều cao cố định
    private JPanel taoDong(JComponent comp) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        panel.add(comp, BorderLayout.CENTER);

        JPanel marginPanel = new JPanel(new BorderLayout());
        marginPanel.add(panel, BorderLayout.CENTER);
        marginPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        return marginPanel;
    }

    public static void main(String[] args) {

        ChiTietNhaCungCapDialog nhaCungCapDialog = new ChiTietNhaCungCapDialog(null, null, null);
        nhaCungCapDialog.setVisible(true);
    }
}