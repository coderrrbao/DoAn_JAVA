package ui.hangthanhvien;

import bus.HangThanhVienBUS;
import dto.HangThanhVien;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import ui.component.Search_Item;
import ui.login.PhienDangNhap;
import util.ExcelUtil;
import util.TaoUI;

public class HangThanhVienUI extends JPanel {
    private JButton btnTao, btnXoa, btnSua, btnXuatExcel, btnNhapExcel;
    private Search_Item search_Item;
    private JTable tableUI;
    private DefaultTableModel model;
    private HangThanhVienBUS htvBUS = HangThanhVienBUS.getHangThanhVienBUS();

    public HangThanhVienUI() {
        setLayout(new BorderLayout());

        // --- GIAO DIỆN PHẦN TRÊN ---
        JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 45);
        top.setBackground(Color.WHITE);
        top = TaoUI.suaBorderChoPanel(top, 0, 10, 0, 10);

        search_Item = new Search_Item(300, 32);
        btnTao = new JButton("Thêm Hạng");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnNhapExcel = new JButton("Nhập Excel");
        btnXuatExcel = new JButton("Xuất Excel");

        TaoUI.setFixSize(btnTao, 120, 32);
        TaoUI.setFixSize(btnXoa, 80, 32);
        TaoUI.setFixSize(btnSua, 80, 32);
        TaoUI.setFixSize(btnNhapExcel, 80, 32);
        TaoUI.setFixSize(btnXuatExcel, 80, 32);

        top.add(search_Item);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnTao);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnSua);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnXoa);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnNhapExcel);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnXuatExcel);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(Box.createHorizontalGlue());

        add(top, BorderLayout.NORTH);

        // --- GIAO DIỆN BẢNG ---
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.addColumn("Mã Hạng");
        model.addColumn("Tên Hạng");
        model.addColumn("Phần Trăm Giảm (%)");
        model.addColumn("Điều Kiện (VNĐ)");

        JScrollPane scrollPane = TaoUI.taoTableScroll(model);
        tableUI = (JTable) scrollPane.getViewport().getView();

        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(new Color(238, 238, 238));
        tableContainer.add(scrollPane, BorderLayout.CENTER);

        add(tableContainer, BorderLayout.CENTER);

        loadDataToTable();
        addEvents();
        suaLaiGiaoDienTheoQuyen();
    }

    public void suaLaiGiaoDienTheoQuyen() {
        var listQuyen = PhienDangNhap.getListQuyen();

        // 1. Quyền Thêm hạng mới (HTV_TAO)
        if (!listQuyen.contains("HTV_TAO")) {
            btnTao.setVisible(false);
        }

        // 2. Quyền Sửa hạng (HTV_SUA)
        if (!listQuyen.contains("HTV_SUA")) {
            btnSua.setVisible(false);
        }

        // 3. Quyền Xóa hạng (HTV_XOA)
        if (!listQuyen.contains("HTV_XOA")) {
            btnXoa.setVisible(false);
        }
    }

    public void loadDataToTable() {
        thucHienTimKiem("");
    }

    private void addEvents() {
        btnTao.addActionListener(
                e -> {
                    FormHangThanhVien form = new FormHangThanhVien((Frame) SwingUtilities.getWindowAncestor(this),
                            null);
                    form.setVisible(true);
                    if (form.getKetQua() != null) {
                        if (htvBUS.themHangThanhVien(form.getKetQua())) {
                            JOptionPane.showMessageDialog(this, "Thêm thành công!");
                            loadDataToTable();
                        } else {
                            JOptionPane.showMessageDialog(this, "Thêm thất bại!");
                        }
                    }
                });

        btnXoa.addActionListener(
                e -> {
                    int row = tableUI.getSelectedRow();
                    if (row == -1) {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!");
                        return;
                    }
                    String maHang = model.getValueAt(row, 0).toString();
                    int confirm = JOptionPane.showConfirmDialog(
                            this, "Xác nhận xóa " + maHang + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        if (htvBUS.xoaHangThanhVien(maHang)) {
                            JOptionPane.showMessageDialog(this, "Đã xóa!");
                            loadDataToTable();
                        }
                    }
                });

        btnSua.addActionListener(
                e -> {
                    int row = tableUI.getSelectedRow();
                    if (row == -1) {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần sửa!");
                        return;
                    }
                    String maHang = model.getValueAt(row, 0).toString();
                    HangThanhVien htvCanSua = htvBUS.timHangThanhVien(maHang);
                    FormHangThanhVien form = new FormHangThanhVien((Frame) SwingUtilities.getWindowAncestor(this),
                            htvCanSua);
                    form.setVisible(true);
                    if (form.getKetQua() != null) {
                        if (htvBUS.capNhatHangThanhVien(form.getKetQua())) {
                            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                            loadDataToTable();
                        }
                    }
                });
        btnXuatExcel.addActionListener(e -> ExcelUtil.export(, TOOL_TIP_TEXT_KEY));

        search_Item.getSearchText().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                thucHienTimKiem(search_Item.getTextSearch());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                thucHienTimKiem(search_Item.getTextSearch());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                thucHienTimKiem(search_Item.getTextSearch());
            }
        });
    }

    private void thucHienTimKiem(String keyword) {
        ArrayList<HangThanhVien> list = htvBUS.timKiemHangThanhVien(keyword);
        model.setRowCount(0);
        for (HangThanhVien htv : list) {
            model.addRow(new Object[] {
                    htv.getMaHang(),
                    htv.getTenHang(),
                    htv.getPhanTramGiam(),
                    String.format("%,.0f", htv.getDieuKien()) // Định dạng tiền tệ cho đẹp mắt
            });
        }
    }

    public JButton getBtnTao() {
        return btnTao;
    }

    public JButton getBtnXoa() {
        return btnXoa;
    }

    public JButton getBtnSua() {
        return btnSua;
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