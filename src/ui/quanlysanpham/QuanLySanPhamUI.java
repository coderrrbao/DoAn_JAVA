package ui.quanlysanpham;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import bus.NhaCungCapBUS;
import bus.SanPhamBUS;
import dto.NhaCungCap;
import dto.SanPham;
import ui.component.Search_Item;
import util.TaoUI;

public class QuanLySanPhamUI extends JPanel {
    private JButton themSpBtn, xuaFileBtn, nhapFileBtn, xoaBtn;
    private Search_Item search_Item;
    private JComboBox<String> cbLoaiNuoc, cbNhaCungCap, cbDanhMuc;
    private JComboBox<String> cbTrangThai;
    private JFrame owner;
    private ArrayList<SanPham> listSanPham;
    private ArrayList<SanPham> listSanPhamLoc;
    private int stt = 0;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private String[] loai = { "Loại nước", "Có sẵn", "Pha chế" };
    private String[] ncc = new String[0];
    private String[] danhmuc = new String[0];
    private String[] trangThaiOptions = { "Trạng thái", "Đã xác nhận", "Chờ xử lý", "Ẩn" };

    private SanPhamBUS quanLySanPhamBUS;

    public QuanLySanPhamUI(JFrame owner) {
        this.owner = owner;
        this.listSanPham = new ArrayList<>();
        this.listSanPhamLoc = new ArrayList<>();
        quanLySanPhamBUS = new SanPhamBUS();

        setLayout(new BorderLayout());

        initTopBar();
        initMainContent();
        ganSuKienChoNut();

        loadDataFromDatabase();
    }

    private void initTopBar() {
        JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 45);
        top.setBackground(Color.WHITE);
        top.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        cbLoaiNuoc = new JComboBox<>(loai);
        cbLoaiNuoc.setMaximumSize(new Dimension(90, 32));

        cbNhaCungCap = new JComboBox<>(ncc);
        cbNhaCungCap.setMaximumSize(new Dimension(90, 32));

        cbDanhMuc = new JComboBox<>(danhmuc);
        cbDanhMuc.setMaximumSize(new Dimension(90, 32));

        search_Item = new Search_Item(280, 32);

        themSpBtn = new JButton("Thêm");
        themSpBtn.setBackground(new Color(40, 167, 69));
        themSpBtn.setForeground(Color.WHITE);
        TaoUI.setHeightButton(themSpBtn, 32);

        xoaBtn = new JButton("Xóa");
        TaoUI.setHeightButton(xoaBtn, 32);

        xuaFileBtn = new JButton("Xuất Exc");
        TaoUI.setFixSize(xuaFileBtn, 65, 32);
        xuaFileBtn.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        nhapFileBtn = new JButton("Nhập Exc");
        TaoUI.setFixSize(nhapFileBtn, 65, 32);

        cbTrangThai = new JComboBox<>(trangThaiOptions);
        cbTrangThai.setMaximumSize(new Dimension(160, 32));

        top.add(cbLoaiNuoc);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(cbNhaCungCap);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(cbDanhMuc);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(search_Item);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(themSpBtn);
        top.add(Box.createRigidArea(new Dimension(5, 0)));
        top.add(xoaBtn);
        top.add(Box.createRigidArea(new Dimension(5, 0)));
        top.add(xuaFileBtn);
        top.add(Box.createRigidArea(new Dimension(5, 0)));
        top.add(nhapFileBtn);
        top.add(Box.createRigidArea(new Dimension(5, 0)));
        top.add(cbTrangThai);
        nhapFileBtn.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        top.add(Box.createHorizontalGlue());

        add(top, BorderLayout.NORTH);
    }

    private void initMainContent() {
        JPanel content = new JPanel(new BorderLayout());
        scrollPane = taoTable();
        content.add(scrollPane, BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);
        JTable table = (JTable) scrollPane.getViewport().getView();
        table.setRowHeight(70);
        setKichThuocCot();
    }

    private JScrollPane taoTable() {
        String[] cots = { "STT", "Ảnh", "Mã SP", "Tên sản phẩm", "Loại", "Danh mục", "Giá bán", "Trạng thái", "" };

        model = new DefaultTableModel(null, cots) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 1)
                    return Icon.class;
                if (columnIndex == 0 || columnIndex == 6)
                    return Integer.class;
                if (columnIndex == 7)
                    return Boolean.class;
                if (columnIndex == 8)
                    return JButton.class;
                return Object.class;
            }

            public boolean isCellEditable(int row, int column) {
                return column == 8;
            }
        };

        HashSet<Integer> set = new HashSet<>();
        set.add(1);
        set.add(8);
        JScrollPane sc = TaoUI.taoTableScroll(model, set);
        JTable table = (JTable) sc.getViewport().getView();

        NutSuKienSP nutSuKien = new NutSuKienSP(new JCheckBox());
        table.getColumnModel().getColumn(8).setCellRenderer(new NutHienThiSP("../assets/icon/sua.svg"));
        table.getColumnModel().getColumn(8).setCellEditor(nutSuKien);

        return sc;
    }

    private void setKichThuocCot() {
        JTable table = (JTable) scrollPane.getViewport().getView();
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50); // STT
        columnModel.getColumn(2).setPreferredWidth(80); // Mã SP
        columnModel.getColumn(3).setPreferredWidth(200); // Tên sản phẩm (Cần rộng nhất)
        columnModel.getColumn(4).setPreferredWidth(100); // Loại
        columnModel.getColumn(5).setPreferredWidth(100); // Danh mục
        columnModel.getColumn(6).setPreferredWidth(90); // Giá bán
        columnModel.getColumn(7).setPreferredWidth(90); // Giá nhập
        columnModel.getColumn(8).setPreferredWidth(40); // Số lượng
    }

    private void themSanPhamVaoTable(SanPham sanPham) {
        ImageIcon icon = TaoUI.taoImageIcon(sanPham.getAnh(), 70, 70);
        JButton btn = new JButton();
        model.addRow(new Object[] { stt++, icon, sanPham.getMaSP(), sanPham.getTenSP(), sanPham.getLoaiNuoc(),
                sanPham.getDanhMuc().getTenDM(), sanPham.getGiaBan(), sanPham.getTrangThaiXuLy(), btn });
    }

    private void ganSuKienChoNut() {
        themSpBtn.addActionListener(e -> {
            new ChiTietSanPhamDialog(null, this);
        });

        cbTrangThai.addActionListener(e -> locSanPham());

        cbLoaiNuoc.addActionListener(e -> locSanPham());
        cbNhaCungCap.addActionListener(e -> locSanPham());
        cbDanhMuc.addActionListener(e -> locSanPham());
        search_Item.setEvent(this::locSanPham);

        xuaFileBtn.addActionListener(e -> {
            quanLySanPhamBUS.xuatFileExcel();
        });
    }

    public void loadDataFromDatabase() {
        listSanPham = quanLySanPhamBUS.layListSanPham();
        NhaCungCapBUS nhaCungCapBUS = new NhaCungCapBUS();
        ArrayList<String> luaChonNCC = nhaCungCapBUS.layLuaChonNCC();
        luaChonNCC.add(0, "Nhà cung cấp");
        ncc = luaChonNCC.toArray(new String[0]);
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(ncc);

        ArrayList<String> luaChonDM = quanLySanPhamBUS.layLuaChonDanhMuc();
        luaChonDM.add(0, "Danh mục");
        danhmuc = luaChonDM.toArray(new String[0]);
        DefaultComboBoxModel<String> model1 = new DefaultComboBoxModel<>(danhmuc);

        cbDanhMuc.setModel(model1);
        cbNhaCungCap.setModel(model);
        veLaiDanhSach(listSanPham);
        this.revalidate();
        this.repaint();
    }

    private void locSanPham() {
        listSanPhamLoc.clear();
        String luaChonLoaiNuoc = cbLoaiNuoc.getSelectedItem().toString();
        String luaChonNCC = cbNhaCungCap.getSelectedItem().toString();
        String luaChonDM = cbDanhMuc.getSelectedItem().toString();
        String luaChonTrangThai = cbTrangThai.getSelectedItem().toString();
        for (SanPham sanPham : listSanPham) {
            boolean matchNCC = sanPham.getNhaCungCap().getTenNCC().equals(luaChonNCC)
                    || luaChonNCC.equals("Nhà cung cấp");
            boolean matchLoai = sanPham.getLoaiNuoc().equals(luaChonLoaiNuoc) || luaChonLoaiNuoc.equals("Loại nước");
            boolean matchDM = sanPham.getDanhMuc().getTenDM().equals(luaChonDM) || luaChonDM.equals("Danh mục");
            boolean matchSearch = sanPham.getTenSP().toUpperCase()
                    .contains(search_Item.getTextSearch().trim().toUpperCase());
            boolean matchTrangThai = false;
            if (luaChonTrangThai.equals("Trạng thái") || sanPham.getTrangThaiXuLy().equals(luaChonTrangThai)) {
                matchTrangThai = true;
            }

            if (matchNCC && matchLoai && matchDM && matchTrangThai && matchSearch) {
                listSanPhamLoc.add(sanPham);
            }
        }
        veLaiDanhSach(listSanPhamLoc);
    }

    private void veLaiDanhSach(ArrayList<SanPham> list) {
        stt = 1;
        model.setRowCount(0);
        for (SanPham sanPham : list) {
            themSanPhamVaoTable(sanPham);
        }
    }
}