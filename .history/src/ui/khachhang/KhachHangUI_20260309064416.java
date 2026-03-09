package ui.khachhang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import bus.KhachHangBUS;
import dto.KhachHang;
import ui.component.IconButtonEditor;
import ui.component.IconButtonRender;
import ui.component.Search_Item;
import ui.login.PhienDangNhap;
import util.TaoUI;

public class KhachHangUI extends JPanel {
    private JButton btnTao, btnSua, btnXoa;
    private JComboBox<String> cbHangThanhVien;
    private Search_Item search_Item;
    private JTable tableUI;
    private DefaultTableModel model;

    private List<KhachHang> listKhachHang = new ArrayList<>();
    private List<KhachHang> listKhachHangLoc = new ArrayList<>();

    public KhachHangUI() {
        setLayout(new BorderLayout());

        JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 45);
        top.setBackground(Color.WHITE);
        top = TaoUI.suaBorderChoPanel(top, 0, 10, 0, 10);
        
        String[] hang = {
                "Tất cả hạng",
                "Thành Viên Mới",
                "Thành Viên Bạc",
                "Thành Viên Vàng",
                "Thành Viên Bạch Kim",
                "Thành Viên Kim Cương"
        };
        cbHangThanhVien = new JComboBox<>(hang);
        cbHangThanhVien.setPreferredSize(new Dimension(150, 30));
        cbHangThanhVien.setMaximumSize(new Dimension(150, 30));

        search_Item = new Search_Item(300, 32);

        btnTao = new JButton("Thêm");
        TaoUI.setFixSize(btnTao, 80, 32);
        btnSua = new JButton("Sửa");
        TaoUI.setFixSize(btnSua, 80, 32);
        btnXoa = new JButton("Xóa");
        TaoUI.setFixSize(btnXoa, 80, 32);

        top.add(cbHangThanhVien);
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
                if (columnIndex == 6) {
                    return JButton.class;
                }
                return Object.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6;
            }
        };
        model.addColumn("Mã khách hàng");
        model.addColumn("Tên khách hàng");
        model.addColumn("Giới tính");
        model.addColumn("Số điện thoại");
        model.addColumn("Tổng chi tiêu");
        model.addColumn("Hạng thành viên");
        model.addColumn("");

        HashSet<Integer> set = new HashSet<>();
        set.add(6); // cột icon, không căn giữa hết
        JScrollPane scrollPane = TaoUI.taoTableScroll(model, set);
        tableUI = (JTable) scrollPane.getViewport().getView();
        tableUI.setAutoCreateColumnsFromModel(false);
        tableUI.getColumnModel().getColumn(6).setMinWidth(80);
        tableUI.getColumnModel().getColumn(6).setMaxWidth(80);
        tableUI.getColumnModel().getColumn(6).setPreferredWidth(80);
        tableUI.getColumnModel().getColumn(6).setCellRenderer(new IconButtonRender("/assets/icon/sua.svg"));
        tableUI.getColumnModel().getColumn(6).setCellEditor(new IconButtonEditor("/assets/icon/sua.svg", row -> {
            String maKH = (String) model.getValueAt(row, 0);
            KhachHangBUS bus = new KhachHangBUS();
            KhachHang kh = bus.timKhachHangTheoMa(maKH);
            if (kh != null) {
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                ThemKhachHangDialog dialog = new ThemKhachHangDialog(parentFrame, this, kh);
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng để sửa", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }));

        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(new Color(238, 238, 238));
        tableContainer.add(scrollPane, BorderLayout.CENTER);

        add(tableContainer, BorderLayout.CENTER);

        ganSuKien();
        loadDataFromDatabase();
    }

    public void suaLaiGiaoDienTheoQuyen() {
        var listQuyen = PhienDangNhap.getListQuyen();

        // 1. Quyền Thêm khách hàng (KH_TAO)
        if (!listQuyen.contains("KH_TAO")) {
            btnTao.setVisible(false);
        }

        // 2. Quyền Xóa khách hàng (KH_XOA)
        if (!listQuyen.contains("KH_XOA")) {
            btnXoa.setVisible(false);
        }

        // 3. Quyền Sửa khách hàng (KH_SUA)
        if (!listQuyen.contains("KH_SUA")) {
            btnSua.setVisible(false);
        }
    }

    private void ganSuKien() {
        btnTao.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            ThemKhachHangDialog dialog = new ThemKhachHangDialog(parentFrame, this);
            dialog.setVisible(true);
        });

        btnSua.addActionListener(e -> {
            int row = tableUI.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần sửa", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            row = tableUI.convertRowIndexToModel(row);
            String maKH = (String) model.getValueAt(row, 0);
            KhachHangBUS bus = new KhachHangBUS();
            KhachHang kh = bus.timKhachHangTheoMa(maKH);
            if (kh != null) {
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                ThemKhachHangDialog dialog = new ThemKhachHangDialog(parentFrame, this, kh);
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng để sửa", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        cbHangThanhVien.addActionListener(e -> locKhachHang());
        search_Item.setEvent(this::locKhachHang);

        btnXoa.addActionListener(e -> {
            int row = tableUI.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần xóa", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            row = tableUI.convertRowIndexToModel(row);
            String maKH = (String) model.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có muốn xóa khách hàng " + maKH + "?", "Xác nhận",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                KhachHangBUS bus = new KhachHangBUS();
                boolean ok = bus.xoaKhachHang(maKH);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công", "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadDataFromDatabase();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa khách hàng thất bại", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void hienThiDanhSachKhachHang() {
        loadDataFromDatabase();
    }

    private void loadDataFromDatabase() {
        KhachHangBUS bus = new KhachHangBUS();
        listKhachHang = bus.layDanhSachKhachHang();
        locKhachHang();
    }

    

    private String tenHangTuMa(String maHang) {
        if (maHang == null)
            return "Thành Viên Mới";
        return switch (maHang) {
            case "HTV01" -> "Thành Viên Mới";
            case "HTV02" -> "Thành Viên Bạc";
            case "HTV03" -> "Thành Viên Vàng";
            case "HTV04" -> "Thành Viên Bạch Kim";
            case "HTV05" -> "Thành Viên Kim Cương";
            default -> "Thành Viên Mới";
        };
    }

    private void locKhachHang() {
        listKhachHangLoc.clear();
        String hangFilter = (String) cbHangThanhVien.getSelectedItem();
        String keyword = search_Item.getTextSearch() != null ? search_Item.getTextSearch().trim().toUpperCase() : "";

        for (KhachHang kh : listKhachHang) {
            String tenHang = tenHangTuMa(kh.getMaHang());

            boolean matchHang = "Tất cả hạng".equals(hangFilter) || tenHang.equals(hangFilter);

            boolean matchSearch = true;
            if (!keyword.isEmpty()) {
                String ma = kh.getMaKH() != null ? kh.getMaKH() : "";
                String ten = kh.getTenKH() != null ? kh.getTenKH() : "";
                String sdt = kh.getSdt() != null ? kh.getSdt() : "";
                matchSearch = ma.toUpperCase().contains(keyword)
                        || ten.toUpperCase().contains(keyword)
                        || sdt.toUpperCase().contains(keyword);
            }

            if (matchHang && matchSearch) {
                listKhachHangLoc.add(kh);
            }
        }

        veLaiDanhSach(listKhachHangLoc);
    }

    private void veLaiDanhSach(List<KhachHang> list) {
        model.setRowCount(0);
        for (KhachHang kh : list) {
            model.addRow(new Object[] {
                    kh.getMaKH(),
                    kh.getTenKH(),
                    kh.getGioiTinh(),
                    kh.getSdt(),
                    dinhDangTien(kh.getTenDaMua()),
                    tenHangTuMa(kh.getMaHang()),
                    null
            });
        }
        tableUI.revalidate();
        tableUI.repaint();
    }

    private String dinhDangTien(double value) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(value);
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

    public JComboBox<String> getCbHangThanhVien() {
        return cbHangThanhVien;
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
