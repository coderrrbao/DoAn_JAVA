package ui.nhanvien;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import bus.NhanVienBUS;
import dao.NhanVienDAO;
import dao.conection.DBConnection;
import ui.component.IconButtonEditor;
import ui.component.IconButtonRender;
import ui.component.Search_Item;
import ui.login.PhienDangNhap;
import util.ExcelUtil;
import util.TaoUI;
import dto.NhanVien;

public class NhanVienUI extends JPanel {
    private JButton btnTao, btnXuatExcel, btnNhapExcel, btnXoa;
    private JComboBox<String> cbChucVu, cbTrangThai;
    private Search_Item search_Item;
    private JTable tableUI;
    private DefaultTableModel model;
    private List<NhanVien> listNhanVien = new ArrayList<>();
    private List<NhanVien> listNhanVienLoc = new ArrayList<>();

    public NhanVienUI() {
        setLayout(new BorderLayout());

        JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 35);
        top.setBackground(Color.WHITE);
        top = TaoUI.suaBorderChoPanel(top, 0, 10, 0, 10);

        String[] dsChucVu = { "Tất cả", "Quản lý", "Nhân viên" };
        cbChucVu = new JComboBox<>(dsChucVu);
        cbChucVu.setPreferredSize(new Dimension(150, 30));
        cbChucVu.setMaximumSize(new Dimension(150, 30));

        String[] dsTrangThai = { "Tất cả trạng thái", "Đang làm việc", "Đã nghỉ việc" };
        cbTrangThai = new JComboBox<>(dsTrangThai);
        cbTrangThai.setPreferredSize(new Dimension(140, 30));
        cbTrangThai.setMaximumSize(new Dimension(140, 30));

        search_Item = new Search_Item(300, 30);

        btnTao = new JButton("Thêm");
        btnTao.addActionListener(e -> openThemNhanVienDialog());

        btnXoa = new JButton("Xóa");

        btnXuatExcel = new JButton("Xuất Excel");
        btnXuatExcel.addActionListener((e -> ExcelUtil.export(listNhanVien, "DanhSachNhanVien")));

        btnNhapExcel = new JButton("Nhập Excel");
        btnNhapExcel.addActionListener(e -> importFile());

        top.add(cbChucVu);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(cbTrangThai);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(search_Item);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnTao);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnXoa);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnNhapExcel);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnXuatExcel);
        top.add(Box.createHorizontalGlue());

        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 7) {
                    return JButton.class;
                }
                return Object.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7;
            }
        };
        model.addColumn("Mã NV");
        model.addColumn("Họ và tên");
        model.addColumn("Giới tính");
        model.addColumn("Chức vụ");
        model.addColumn("Số điện thoại");
        model.addColumn("Ngày vào làm");
        model.addColumn("Trạng thái");
        model.addColumn("");

        HashSet<Integer> set = new HashSet<>();
        set.add(7);
        JScrollPane scrollPane = TaoUI.taoTableScroll(model, set);
        tableUI = (JTable) scrollPane.getViewport().getView();

        tableUI.getColumnModel().getColumn(7).setCellRenderer(new IconButtonRender("/assets/icon/sua.svg"));
        tableUI.getColumnModel().getColumn(7).setCellEditor(new IconButtonEditor("/assets/icon/sua.svg", row -> {
            String maNV = (String) model.getValueAt(row, 0);
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            NhanVienBUS bus = new NhanVienBUS();
            NhanVien nv = bus.timNhanVien(maNV);
            if (nv != null) {
                ThemNhanVienDialog dia = new ThemNhanVienDialog(parentFrame, this, nv);
                dia.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên để sửa", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }));

        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(new Color(238, 238, 238));
        tableContainer.add(scrollPane, BorderLayout.CENTER);

        add(tableContainer, BorderLayout.CENTER);

        ganSuKienLocVaXoa();
        layDanhSachNhanVien();
    }

    public void suaLaiGiaoDienTheoQuyen() {
        var listQuyen = PhienDangNhap.getListQuyen();

        // 1. Quyền Thêm nhân viên (NV_TAO)
        if (!listQuyen.contains("NV_TAO")) {
            btnTao.setVisible(false);
        }

        // 2. Quyền Xóa nhân viên (NV_XOA)
        if (!listQuyen.contains("NV_XOA")) {
            btnXoa.setVisible(false);
        }
        this.revalidate();
        this.repaint();
    }

    private void openThemNhanVienDialog() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        ThemNhanVienDialog dia = new ThemNhanVienDialog(parentFrame, this);
        dia.setVisible(true);
    }

    public void hienThiDanhSachNhanVien() {
        layDanhSachNhanVien();
    }

    private void layDanhSachNhanVien() {
        NhanVienBUS bus = NhanVienBUS.getNhanVienBUS();
        listNhanVien = bus.layDanhSachNhanVien();
        locNhanVien();
    }

    private void importFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File selectedFile = fileChooser.getSelectedFile();

        if (!selectedFile.getName().toLowerCase().endsWith(".xlsx")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Định dạng file không hợp lệ (.xlsx)",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<NhanVien> list;

        try {
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

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi đọc file Excel!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connection conn = null;

        try {
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

            JOptionPane.showMessageDialog(
                    this,
                    "Import Thành công:",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);

            layDanhSachNhanVien();
            locNhanVien();
        } catch (Exception e) {
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

    private void locNhanVien() {
        listNhanVienLoc.clear();

        String keyword = search_Item.getTextSearch() != null ? search_Item.getTextSearch().trim().toUpperCase() : "";

        for (NhanVien nv : listNhanVien) {

            boolean matchSearch = true;
            if (!keyword.isEmpty()) {
                String ma = nv.getMaNV() != null ? nv.getMaNV() : "";
                String ten = nv.getTenNV() != null ? nv.getTenNV() : "";
                String sdt = nv.getSdt() != null ? nv.getSdt() : "";
                matchSearch = ma.toUpperCase().contains(keyword)
                        || ten.toUpperCase().contains(keyword)
                        || sdt.toUpperCase().contains(keyword);
            }
            if (matchSearch) {
                listNhanVienLoc.add(nv);
            }
        }
        veLaiDanhSach(listNhanVienLoc);
    }

    private void veLaiDanhSach(List<NhanVien> list) {
        model.setRowCount(0);
        for (NhanVien nv : list) {
            model.addRow(new Object[] {
                    nv.getMaNV(),
                    nv.getTenNV(),
                    nv.getGioiTinh(),
                    nv.getChucVu(),
                    nv.getSdt(),
                    nv.getNgayVaoLam(),
                    nv.getTrangThai() ? "Đang làm việc" : "Đã nghỉ việc",
                    null
            });
        }
    }

    private void ganSuKienLocVaXoa() {
        search_Item.setEvent(this::locNhanVien);

        btnXoa.addActionListener(e -> {
            int row = tableUI.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            row = tableUI.convertRowIndexToModel(row);
            String maNV = (String) model.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có muốn xóa nhân viên " + maNV + "?", "Xác nhận",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                NhanVienBUS bus = NhanVienBUS.getNhanVienBUS();
                boolean ok = bus.xoaNhanVien(maNV);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công", "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                    layDanhSachNhanVien();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa nhân viên thất bại", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public JButton getBtnTao() {
        return btnTao;
    }

    public JButton getBtnXoa() {
        return btnXoa;
    }

    public Search_Item getSearch_Item() {
        return search_Item;
    }

    public JTable getTableUI() {
        return tableUI;
    }

    public DefaultTableModel getModel() {
        return model;
    }
}