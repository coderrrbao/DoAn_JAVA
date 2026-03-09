package ui.nhanvien;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import bus.NhanVienBUS;
import ui.component.IconButtonEditor;
import ui.component.IconButtonRender;
import ui.component.Search_Item;
import ui.login.PhienDangNhap;
import util.TaoUI;
import dto.NhanVien;

public class NhanVienUI extends JPanel {
    private JButton btnTao, btnXuatExcel, btnNhapExcel, btnXoa;
    // ĐÃ XÓA cbChucVu
    private Search_Item search_Item;
    private JTable tableUI;
    private DefaultTableModel model;
    private List<NhanVien> listNhanVien = new ArrayList<>();
    private List<NhanVien> listNhanVienLoc = new ArrayList<>();

    public NhanVienUI() {
        setLayout(new BorderLayout());

        JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 45);
        top.setBackground(Color.WHITE);
        top = TaoUI.suaBorderChoPanel(top, 0, 10, 0, 10);

        // ĐÃ XÓA logic khởi tạo dsChucVu và cbChucVu

        search_Item = new Search_Item(300, 32);

        btnTao = new JButton("Thêm");
        TaoUI.setFixSize(btnTao, 80, 32);
        btnTao.addActionListener(e -> openThemNhanVienDialog());

        btnXoa = new JButton("Xóa");
        TaoUI.setFixSize(btnXoa, 100, 32);

        btnXuatExcel = new JButton("Xuất Excel");
        TaoUI.setFixSize(btnXuatExcel, 100, 32);
        btnXuatExcel.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setSelectedFile(new File("DanhSachNhanVien.xlsx"));
            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                if (!file.getName().endsWith(".xlsx"))
                    file = new File(file.getAbsolutePath() + ".xlsx");

                if (NhanVienBUS.getNhanVienBUS().xuatExcel(file)) {
                    JOptionPane.showMessageDialog(this, "Xuất thành công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi xuất file!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnNhapExcel = new JButton("Nhập Excel");
        TaoUI.setFixSize(btnNhapExcel, 80, 32);
        btnNhapExcel.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();

                if (NhanVienBUS.getNhanVienBUS().nhapExcel(file)) {
                    layDanhSachNhanVien(); // Refresh bảng
                    JOptionPane.showMessageDialog(this, "Nhập thành công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Nhập thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Cập nhật thanh công cụ: Bỏ cbChucVu
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
                // Nút sửa giờ chuyển sang cột số 4 (do mất cột Chức vụ)
                if (columnIndex == 4) {
                    return JButton.class;
                }
                return Object.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        model.addColumn("Mã NV");
        model.addColumn("Họ và tên");
        model.addColumn("Giới tính");
        model.addColumn("Số điện thoại");
        model.addColumn(""); // Cột nút sửa (Index 4)

        HashSet<Integer> set = new HashSet<>();
        set.add(4); // Cột chứa component (Nút sửa)
        JScrollPane scrollPane = TaoUI.taoTableScroll(model, set);
        tableUI = (JTable) scrollPane.getViewport().getView();

        // Thiết lập Renderer và Editor cho cột số 4
        tableUI.getColumnModel().getColumn(4).setCellRenderer(new IconButtonRender("/assets/icon/sua.svg"));
        tableUI.setAutoCreateColumnsFromModel(false);
        tableUI.getColumnModel().getColumn(4).setMinWidth(80);
        tableUI.getColumnModel().getColumn(4).setMaxWidth(80);
        tableUI.getColumnModel().getColumn(4).setPreferredWidth(80);
        tableUI.getColumnModel().getColumn(4).setCellEditor(new IconButtonEditor("/assets/icon/sua.svg", row -> {
            String maNV = (String) model.getValueAt(row, 0);
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            NhanVienBUS bus = NhanVienBUS.getNhanVienBUS();
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

            // Đã xóa matchChucVu, chỉ lọc theo keyword
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
                    nv.getSdt(),
                    null // Nút sửa (cột 4)
            });
        }
    }

    private void ganSuKienLocVaXoa() {
        search_Item.setEvent(this::locNhanVien);
        // Đã xóa ActionListener cho cbChucVu

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