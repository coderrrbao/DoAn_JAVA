package ui.nhanvien;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

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
    private JComboBox<String> cbChucVu, cbTrangThai;
    private Search_Item search_Item;
    private JTable tableUI;
    private DefaultTableModel model;

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

        btnSua = new JButton("Sửa");

        btnXoa = new JButton("Xóa");

        top.add(cbChucVu);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(cbTrangThai);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(search_Item);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnTao);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnSua);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnXoa);
        top.add(Box.createHorizontalGlue());

        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
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

        JScrollPane scrollPane = TaoUI.taoTableScroll(model);
        tableUI = (JTable) scrollPane.getViewport().getView();

        tableUI.getColumn("").setCellRenderer(new IconButtonRender("/assets/icon/sua.svg"));
        tableUI.getColumn("").setCellEditor(new IconButtonEditor("src/assets/icon/sua.svg", row -> {
            System.out.println("Edit row: " + row);
        }));

        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(new Color(238, 238, 238));
        tableContainer.add(scrollPane, BorderLayout.CENTER);

        add(tableContainer, BorderLayout.CENTER);

        hienThiDanhSachNhanVien();
    }

    private void openThemNhanVienDialog() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        ThemNhanVienDialog dia = new ThemNhanVienDialog(parentFrame, this);
        dia.setVisible(true);
    }

    public void hienThiDanhSachNhanVien() {
        NhanVienBUS bus = new NhanVienBUS();
        List<NhanVien> dsNV = bus.layDanhSachNhanVien();
        model.setRowCount(0);
        for (NhanVien nv : dsNV) {
            model.addRow(new Object[] {
                    nv.getMaNV(),
                    nv.getTenNV(),
                    nv.getGioiTinh(),
                    nv.getChucVu(),
                    nv.getSdt(),
                    nv.getNgayVaoLam(),
                    nv.getTrangThai() ? "Đang làm việc" : "Đã nghỉ việc"
            });
        }
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

    public JComboBox<String> getCbTrangThai() {
        return cbTrangThai;
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