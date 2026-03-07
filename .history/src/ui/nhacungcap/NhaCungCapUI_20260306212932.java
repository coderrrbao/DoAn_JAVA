package ui.nhacungcap;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import bus.NhaCungCapBUS;
import dao.NhaCungCapDAO;
import dao.conection.DBConnection;
import dto.NhaCungCap;
import ui.component.Search_Item;
import ui.login.PhienDangNhap;
import util.ExcelUtil;
import util.TaoTinNhan;
import util.TaoUI;
import java.util.*;

public class NhaCungCapUI extends JPanel {
    private JButton btnTao, btnXoa, btnXuatExcel, btnNhapExcel, btnXemChiTiet;
    private Search_Item search_Item;
    private JTable tableUI;
    private DefaultTableModel model;
    private List<NhaCungCap> ds;

    public NhaCungCapUI() {
        setLayout(new BorderLayout());

        JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 45);
        top.setBackground(Color.WHITE);
        top = TaoUI.suaBorderChoPanel(top, 0, 10, 0, 10);

        search_Item = new Search_Item(300, 32);

        btnTao = new JButton("Thêm");

        btnXemChiTiet = new JButton("Xem chi tiết");

        btnXoa = new JButton("Xóa");

        btnNhapExcel = new JButton("Nhập Excel");

        btnXuatExcel = new JButton("Xuất Excel");

        TaoUI.setFixSize(btnTao, 100, 32);
        TaoUI.setFixSize(btnXemChiTiet, 150, 32);
        TaoUI.setFixSize(btnXoa, 100, 32);
        TaoUI.setFixSize(btnNhapExcel, 100, 32);
        TaoUI.setFixSize(btnXuatExcel, 100, 32);

        top.add(search_Item);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnTao);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnXemChiTiet);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnXoa);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnNhapExcel);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(btnXuatExcel);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(Box.createHorizontalGlue());

        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("Mã NCC");
        model.addColumn("Tên nhà cung cấp");
        model.addColumn("Loại cung cấp");
        model.addColumn("Số điện thoại");
        model.addColumn("Địa chỉ");

        JScrollPane scrollPane = TaoUI.taoTableScroll(model);
        tableUI = (JTable) scrollPane.getViewport().getView();
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(new Color(238, 238, 238));
        tableContainer.add(scrollPane, BorderLayout.CENTER);

        add(tableContainer, BorderLayout.CENTER);
        ganSuKien();
        loadDuLieu();
    }

    public void loadDuLieu() {
        model.setRowCount(0);
        NhaCungCapBUS nhaCungCapBUS = NhaCungCapBUS.getNhaCungCapBUS();
        ds = nhaCungCapBUS.laylistNhaCungCap();
        for (NhaCungCap nhaCungCap : ds) {
            if (nhaCungCap.getTenNCC().contains(search_Item.getTextSearch())) {
                String loaiCungCap = "";
                if (nhaCungCap.getCungCapSP()) {
                    loaiCungCap = "Sản phẩm";
                }
                if (nhaCungCap.getCungCapNL()) {
                    if (!loaiCungCap.equals("")) {
                        loaiCungCap += " & ";
                    }
                    loaiCungCap += "Nguyên liệu";
                }
                if (loaiCungCap.equals("")) {
                    loaiCungCap = "Không có";
                }
                model.addRow(new Object[] { nhaCungCap.getMaNCC(), nhaCungCap.getTenNCC(),
                        loaiCungCap, nhaCungCap.getSoDienThoai(), nhaCungCap.getDiaChi() });
            }
        }
    }

    public void suaLaiGiaoDienTheoQuyen() {
        var listQuyen = PhienDangNhap.getListQuyen();

        if (!listQuyen.contains("NCC_TAO")) {
            btnTao.setVisible(false);
        }

        if (!listQuyen.contains("NCC_XOA")) {
            btnXoa.setVisible(false);
        }
        this.revalidate();
        this.repaint();
    }

    public void ganSuKien() {
        search_Item.setEvent(() -> {
            loadDuLieu();
        });
        btnTao.addActionListener(e -> {
            ChiTietNhaCungCapDialog chiTietNhaCungCapDialog = new ChiTietNhaCungCapDialog(null, null, this);
            chiTietNhaCungCapDialog.setVisible(true);

        });
        btnXemChiTiet.addActionListener(e -> {
            int dongChon = tableUI.getSelectedRow();
            if (dongChon >= 0) {
                NhaCungCapBUS nhaCungCapBUS = NhaCungCapBUS.getNhaCungCapBUS();
                NhaCungCap nhaCungCap = nhaCungCapBUS.timNhaCungCap(model.getValueAt(dongChon, 0).toString());
                ChiTietNhaCungCapDialog chiTietNhaCungCapDialog = new ChiTietNhaCungCapDialog(null, nhaCungCap, this);
                chiTietNhaCungCapDialog.setVisible(true);
            } else {
                TaoTinNhan.showAutoCloseMessage("Vui lòng chọn nhà cung cấp để xem chi tiết", "Thông báo", 1);
            }
        });

        btnXoa.addActionListener(e -> {
            int dongChon = tableUI.getSelectedRow();
            if (dongChon >= 0) {
                NhaCungCapBUS nhaCungCapBUS = NhaCungCapBUS.getNhaCungCapBUS();
                NhaCungCap nhaCungCap = nhaCungCapBUS.timNhaCungCap(model.getValueAt(dongChon, 0).toString());
                if (JOptionPane.showConfirmDialog(null, "Xóa nhà cung cấp " + nhaCungCap.getMaNCC() + " ?", "Thông báo",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
                    if (nhaCungCapBUS.xoaNhaCungCap(nhaCungCap)) {
                        TaoTinNhan.showAutoCloseMessage("Xóa nhà cung cấp thành công", "Thông báo", 1);
                        loadDuLieu();
                    } else {
                        TaoTinNhan.showAutoCloseMessage("Xóa nhà cung cấp thất bại", "Thông báo", 1);
                    }
                }

            } else {
                TaoTinNhan.showAutoCloseMessage("Vui lòng chọn nhà cung cấp để xóa", "Thông báo", 1);
            }
        });

        btnXuatExcel.addActionListener(e -> ExcelUtil.export(ds, "DanhSachNhaCungCap"));

        btnNhapExcel.addActionListener(e -> importFile());
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

        List<NhaCungCap> list;

        try {
            list = ExcelUtil.importFile(selectedFile, row -> {

                String maNCC = ExcelUtil.getNullableString(row, 0);
                String tenNCC = ExcelUtil.getNullableString(row, 1);
                String loaiCungCap = ExcelUtil.getNullableString(row, 2);
                String sdt = ExcelUtil.getNullableString(row, 3);
                String diaChi = ExcelUtil.getNullableString(row, 4);
                NhaCungCap ncc = new NhaCungCap(maNCC, tenNCC, sdt, diaChi);
                if(loaiCungCap == "Sản phẩm") ncc.setCungCapSP(true);
                else if(loaiCungCap == "Nguyên liệu") ncc.setCungCapNL(true);
                return ncc;
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

            NhaCungCapDAO dao = new NhaCungCapDAO();

            for (NhaCungCap ncc : list) {
                Boolean exist = dao.exist(ncc.getMaNCC());
                if (!exist) {
                    dao.insert(conn, ncc);
                }
            }

            conn.commit();

            loadDuLieu();

            JOptionPane.showMessageDialog(
                    this,
                    "Import Thành công:",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);

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
}