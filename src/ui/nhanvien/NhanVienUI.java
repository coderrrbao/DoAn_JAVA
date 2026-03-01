package ui.nhanvien;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import bus.NhanVienBUS;
import ui.component.IconButtonEditor;
import ui.component.IconButtonRender;
import ui.component.Search_Item;
import util.TaoUI;
import dto.NhanVien;

public class NhanVienUI extends JPanel {
    private JButton btnTao, btnSua, btnXoa;
    private JComboBox<String> cbChucVu;
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

        search_Item = new Search_Item(300, 30);

        btnTao = new JButton("Thêm");
        btnTao.addActionListener(e -> openThemNhanVienDialog());

        btnXoa = new JButton("Xóa");

        top.add(cbChucVu);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(search_Item);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnTao);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnXoa);
        top.add(Box.createHorizontalGlue());

        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // Nút sửa lùi xuống vị trí index 5
                if (columnIndex == 5) {
                    return JButton.class;
                }
                return Object.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Chỉ cho phép click vào cột Nút (index 5)
            }
        };
        model.addColumn("Mã NV"); // 0
        model.addColumn("Họ và tên"); // 1
        model.addColumn("Giới tính"); // 2
        model.addColumn("Chức vụ"); // 3
        model.addColumn("Số điện thoại"); // 4
        // Đã xóa cột "Trạng thái"
        model.addColumn(""); // 5 (Cột chứa nút Sửa)

        HashSet<Integer> set = new HashSet<>();
        set.add(5); // Set index 5 cho cột Nút
        JScrollPane scrollPane = TaoUI.taoTableScroll(model, set);
        tableUI = (JTable) scrollPane.getViewport().getView();

        // Cập nhật index 5 cho Render và Editor
        tableUI.getColumnModel().getColumn(5).setCellRenderer(new IconButtonRender("/assets/icon/sua.svg"));
        tableUI.getColumnModel().getColumn(5).setCellEditor(new IconButtonEditor("/assets/icon/sua.svg", row -> {
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

        String chucVuFilter = (String) cbChucVu.getSelectedItem();
        String keyword = search_Item.getTextSearch() != null ? search_Item.getTextSearch().trim().toUpperCase() : "";

        for (NhanVien nv : listNhanVien) {
            boolean matchChucVu = "Tất cả".equals(chucVuFilter)
                    || (nv.getTaiKhoan().getNhomQuyen().getTenNhomQuyen() != null
                            && nv.getTaiKhoan().getNhomQuyen().getTenNhomQuyen().equals(chucVuFilter));

            boolean matchSearch = true;
            if (!keyword.isEmpty()) {
                String ma = nv.getMaNV() != null ? nv.getMaNV() : "";
                String ten = nv.getTenNV() != null ? nv.getTenNV() : "";
                String sdt = nv.getSdt() != null ? nv.getSdt() : "";
                matchSearch = ma.toUpperCase().contains(keyword)
                        || ten.toUpperCase().contains(keyword)
                        || sdt.toUpperCase().contains(keyword);
            }

            if (matchChucVu && matchSearch) {
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
                    nv.getTaiKhoan().getNhomQuyen().getTenNhomQuyen(),
                    nv.getSdt(),
                    null // Dành cho nút sửa ở index 5
            });
        }
        tableUI.revalidate();
        tableUI.repaint();
    }

    private void ganSuKienLocVaXoa() {
        cbChucVu.addActionListener(e -> locNhanVien());
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

    public JButton getBtnSua() {
        return btnSua;
    }

    public JButton getBtnXoa() {
        return btnXoa;
    }

    public JComboBox<String> getCbChucVu() {
        return cbChucVu;
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