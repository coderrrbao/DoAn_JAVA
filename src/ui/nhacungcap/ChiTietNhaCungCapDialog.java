package ui.nhacungcap;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import bus.NguyenLieuBUS;
import bus.NhaCungCapBUS;
import bus.SanPhamBUS;
import dto.ChiTietNhaCungCap;
import dto.NguyenLieu;
import dto.NhaCungCap;
import dto.SanPham;
import util.TaoTinNhan;
import util.TaoUI;

import java.awt.*;
import java.util.ArrayList;

public class ChiTietNhaCungCapDialog extends JDialog {

    private JComboBox<String> cbLoaiHang;
    private JTextField txtMaNCC, txtTenNCC, txtSoDienThoai, txtDiaChi;
    private JButton btnLuu, btnDong, btnThem, btnSua;

    // Khai báo thêm các component cho phần Top
    private JButton btnThemHang, btnXoaHang, btnSuaHang;
    private JTable tblHangHoa;
    private DefaultTableModel modelSP, modelNL;

    private NhaCungCapUI nhaCungCapUI;
    private NhaCungCap nhaCungCap;
    private ThemHangHoaChoNCCDialog themHangHoaChoNCCDialog = new ThemHangHoaChoNCCDialog(null, this);

    private ArrayList<String> listCTNCCCanXoa = new ArrayList<>();

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
        btnSuaHang = new JButton("Sửa");
        btnThem = new JButton("Thêm");
        cbLoaiHang = new JComboBox<>(new String[] { "Sản phẩm", "Nguyên liệu" });

        TaoUI.setFixSize(cbLoaiHang, 150, 30);
        pnRow1.add(btnThemHang);
        pnRow1.add(Box.createRigidArea(new Dimension(10, 0)));
        pnRow1.add(btnXoaHang);
        pnRow1.add(Box.createRigidArea(new Dimension(10, 0)));
        pnRow1.add(btnSuaHang);
        pnRow1.add(Box.createHorizontalGlue());
        pnRow1.add(cbLoaiHang);

        // --- Dòng 2: JTable ---
        String[] columnNames = { "MaCTNCC", "Mã", "Tên", "Loại", "Giá nhập" };

        modelSP = new DefaultTableModel(columnNames, 0);
        modelNL = new DefaultTableModel(columnNames, 0);

        JScrollPane scrollHangHoa = TaoUI.taoTableScroll(modelSP);

        tblHangHoa = (JTable) scrollHangHoa.getViewport().getView();
        tblHangHoa.getColumnModel().removeColumn(tblHangHoa.getColumnModel().getColumn(0));
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
        txtMaNCC.setEnabled(false);

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
        btnSua = new JButton("Sửa");

        btnDong.addActionListener(e -> dispose());

        pnBottom.add(btnSua);
        pnBottom.add(btnThem);
        pnBottom.add(btnLuu);
        pnBottom.add(btnDong);

        add(pnBottom, BorderLayout.SOUTH);

        initLoaiDialog();
        loadDuLieu();
        ganSuKien();

    }

    private void initLoaiDialog() {
        if (nhaCungCap != null) {
            if (nhaCungCap.getCungCapNL()) {
                cbLoaiHang.setSelectedItem("Nguyên liệu");
            } else {
                cbLoaiHang.setSelectedItem("Sản phẩm");
            }
            btnThem.setVisible(false);
            btnDong.setVisible(false);

            anThaoTacSua();
        } else {
            btnLuu.setVisible(false);
            btnSua.setVisible(false);

        }

    }

    public void loadDuLieu() {
        if (nhaCungCap != null) {
            txtMaNCC.setText(nhaCungCap.getMaNCC());
            txtTenNCC.setText(nhaCungCap.getTenNCC());
            txtDiaChi.setText(nhaCungCap.getDiaChi());
            txtSoDienThoai.setText(nhaCungCap.getSoDienThoai());
        }

        if (cbLoaiHang.getSelectedItem().toString().equals("Sản phẩm")) {

            tblHangHoa.setModel(modelSP);
            if (tblHangHoa.getColumnModel().getColumnCount()==5) {
                tblHangHoa.getColumnModel().removeColumn(tblHangHoa.getColumnModel().getColumn(0));
            }

            if (modelSP.getRowCount() == 0 && nhaCungCap != null) {
                SanPhamBUS sanPhamBUS = SanPhamBUS.getSanPhamBUS();
                for (ChiTietNhaCungCap ct : nhaCungCap.getListChiTietNhaCungCap()) {
                    if (ct.getLoaiDoiTuong().equals("Sản phẩm")) {
                        SanPham sp = sanPhamBUS.timSanPham(ct.getMaDoiTuong());
                        if (sp != null && sp.getLoaiNuoc().equals("Có sẵn")) {
                            modelSP.addRow(new Object[] { ct.getMaCTNCC(), sp.getMaSP(), sp.getTenSP(), "Sản phẩm",
                                    ct.getGiaNhap() });
                        }
                    }
                }
            }

        } else if (cbLoaiHang.getSelectedItem().toString().equals("Nguyên liệu")) {
            tblHangHoa.setModel(modelNL);
            if (tblHangHoa.getColumnModel().getColumnCount()==5) {
                tblHangHoa.getColumnModel().removeColumn(tblHangHoa.getColumnModel().getColumn(0));
            }

            if (modelNL.getRowCount() == 0 && nhaCungCap != null) {
                NguyenLieuBUS nguyenLieuBUS = NguyenLieuBUS.getNguyenLieuBUS();
                for (ChiTietNhaCungCap ct : nhaCungCap.getListChiTietNhaCungCap()) {
                    if (ct.getLoaiDoiTuong().equals("Nguyên liệu")) {
                        NguyenLieu nl = nguyenLieuBUS.timNguyenLieu(ct.getMaDoiTuong());
                        if (nl != null) {
                            modelNL
                                    .addRow(new Object[] { ct.getMaCTNCC(), nl.getMaNL(), nl.getTenNL(), "Nguyên liệu",
                                            ct.getGiaNhap() });
                        }
                    }
                }

            }

        }

    }

    private void anThaoTacSua() {
        btnSua.setEnabled(true);
        btnLuu.setEnabled(false);
        txtDiaChi.setEditable(false);
        txtMaNCC.setEditable(false);
        txtSoDienThoai.setEditable(false);
        txtTenNCC.setEditable(false);
        tblHangHoa.setEnabled(false);
        btnLuu.setEnabled(false);
        btnThemHang.setEnabled(false);
        btnXoaHang.setEnabled(false);
        btnSuaHang.setEnabled(false);
    }

    private void batThaoTacSua() {
        btnSua.setEnabled(false);
        btnLuu.setEnabled(true);
        txtDiaChi.setEditable(true);
        txtMaNCC.setEditable(true);
        txtSoDienThoai.setEditable(true);
        txtTenNCC.setEditable(true);
        tblHangHoa.setEnabled(true);
        btnLuu.setEnabled(true);
        btnThemHang.setEnabled(true);
        btnXoaHang.setEnabled(true);
        btnSuaHang.setEnabled(true);
    }

    public void ganSuKien() {

        cbLoaiHang.addActionListener(e -> {
            loadDuLieu();
        });

        btnSua.addActionListener(e -> {
            batThaoTacSua();
        });

        btnThemHang.addActionListener(e -> {
            themHangHoaChoNCCDialog.lamMoi();
            themHangHoaChoNCCDialog.setVisible(true);
        });

        btnXoaHang.addActionListener(e -> {
            int row = tblHangHoa.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn hàng hóa cần xóa!");
                return;
            }

            DefaultTableModel currentModel = (DefaultTableModel) tblHangHoa.getModel();
            if (nhaCungCap != null && !currentModel.getValueAt(row, 0).toString().equals("")) {
                listCTNCCCanXoa.add(currentModel.getValueAt(row, 0).toString());
            }
            currentModel.removeRow(row);
        });

        btnLuu.addActionListener(e -> {
            NhaCungCap nccMoi = dongGoiNhaCungCap();
            if (nccMoi != null) {
                NhaCungCapBUS nhaCungCapBUS = NhaCungCapBUS.getNhaCungCapBUS();
                if (nhaCungCapBUS.capNhapNhaCungCap(nccMoi, listCTNCCCanXoa)) {
                    TaoTinNhan.showAutoCloseMessage("Cập nhật thành công!", "Thông báo", 1);
                    nhaCungCapUI.loadDuLieu();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
                }
            }
        });

        btnThem.addActionListener(e -> {
            NhaCungCap nhaCungCap = dongGoiNhaCungCap();
            NhaCungCapBUS nhaCungCapBUS = NhaCungCapBUS.getNhaCungCapBUS();
            if (nhaCungCapBUS.themNhaCungCap(nhaCungCap)) {
                TaoTinNhan.showAutoCloseMessage("Thêm nhà cung cấp thành công", "Thông báo", 1);
                nhaCungCapUI.loadDuLieu();
            } else {
                TaoTinNhan.showAutoCloseMessage("Thêm nhà cung cấp thất bại", "Thông báo", 1);
            }
            dispose();
        });

        btnSuaHang.addActionListener(e -> {
            int row = tblHangHoa.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn hàng để sửa!");
                return;
            }
            DefaultTableModel modelHienTai = (DefaultTableModel) tblHangHoa.getModel();
            ChiTietNhaCungCap chiTietNhaCungCap = new ChiTietNhaCungCap(modelHienTai.getValueAt(row, 0).toString(),
                    modelHienTai.getValueAt(row, 3).toString(), modelHienTai.getValueAt(row, 1).toString(),
                    Double.parseDouble(modelHienTai.getValueAt(row, 4).toString()));

            SuaChiTietNhaCungCapDialog suaChiTietNhaCungCapDialog = new SuaChiTietNhaCungCapDialog(this,
                    chiTietNhaCungCap, tblHangHoa.getValueAt(row, 2).toString(), row);
            suaChiTietNhaCungCapDialog.setVisible(true);

        });

    }

    public void suaChiTietNhaCungCap(ChiTietNhaCungCap chiTietNhaCungCap, int dong) {
        DefaultTableModel modelHienTai = (DefaultTableModel) tblHangHoa.getModel();
        modelHienTai.setValueAt(String.valueOf(chiTietNhaCungCap.getGiaNhap()), dong, 4);
    }

    private boolean tonTaiTrongModel(String maHH, DefaultTableModel model) {
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 1).toString().equals(maHH)) {
                return true;
            }
        }
        return false;
    }

    public void themDoiTuongVaoTable(ChiTietNhaCungCap chiTietNhaCungCap) {
        if (chiTietNhaCungCap.getLoaiDoiTuong().equals("Sản phẩm")) {
            if (!tonTaiTrongModel(chiTietNhaCungCap.getMaDoiTuong(), modelSP)) {
                SanPhamBUS sanPhamBUS = SanPhamBUS.getSanPhamBUS();
                SanPham sanPham = sanPhamBUS.timSanPham(chiTietNhaCungCap.getMaDoiTuong());
                modelSP.addRow(new Object[] { "", sanPham.getMaSP(), sanPham.getTenSP(),
                        chiTietNhaCungCap.getLoaiDoiTuong(), chiTietNhaCungCap.getGiaNhap() });
            }
            cbLoaiHang.setSelectedItem("Sản phẩm");

        } else if (chiTietNhaCungCap.getLoaiDoiTuong().equals("Nguyên liệu")) {
            if (!tonTaiTrongModel(chiTietNhaCungCap.getMaDoiTuong(), modelNL)) {
                NguyenLieuBUS nguyenLieuBUS = NguyenLieuBUS.getNguyenLieuBUS();
                NguyenLieu nguyenLieu = nguyenLieuBUS.timNguyenLieu(chiTietNhaCungCap.getMaDoiTuong());
                modelNL.addRow(new Object[] { "", nguyenLieu.getMaNL(), nguyenLieu.getTenNL(),
                        chiTietNhaCungCap.getLoaiDoiTuong(), chiTietNhaCungCap.getGiaNhap() });
            }
            cbLoaiHang.setSelectedItem("Nguyên liệu");
        }
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

    public NhaCungCap dongGoiNhaCungCap() {

        String maNCC = txtMaNCC.getText().trim();
        String tenNCC = txtTenNCC.getText().trim();
        String sdt = txtSoDienThoai.getText().trim();
        String diaChi = txtDiaChi.getText().trim();

        NhaCungCap nhaCungCap = new NhaCungCap(maNCC, tenNCC, sdt, diaChi);
        if (tenNCC.isEmpty() || sdt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên và SDT không được để trống!");
            return null;
        }

        ArrayList<ChiTietNhaCungCap> dsChiTiet = new ArrayList<>();

        for (int i = 0; i < modelSP.getRowCount(); i++) {
            dsChiTiet.add(new ChiTietNhaCungCap(modelSP.getValueAt(i, 0).toString(),
                    nhaCungCap != null ? nhaCungCap.getMaNCC() : "", "Sản phẩm",
                    modelSP.getValueAt(i, 1).toString(), Double.parseDouble(modelSP.getValueAt(i, 4).toString())));
        }

        for (int i = 0; i < modelNL.getRowCount(); i++) {
            dsChiTiet.add(new ChiTietNhaCungCap(modelNL.getValueAt(i, 0).toString(),
                    nhaCungCap != null ? nhaCungCap.getMaNCC() : "", "Nguyên liệu",
                    modelNL.getValueAt(i, 1).toString(), Double.parseDouble(modelNL.getValueAt(i, 4).toString())));
        }
        nhaCungCap.setListChiTietNhaCungCap(dsChiTiet);
        return nhaCungCap;
    }

    public static void main(String[] args) {

        ChiTietNhaCungCapDialog nhaCungCapDialog = new ChiTietNhaCungCapDialog(null, null, null);
        nhaCungCapDialog.setVisible(true);
    }
}