package ui.phanquyen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import bus.NhomQuyenBUS;
import bus.PhanQuyenBUS;
import dto.NhomQuyen;
import ui.component.Search_Item;
import ui.login.PhienDangNhap;
import util.TaoTinNhan;
import util.TaoUI;
import util.XuLyExcel;

public class NhomQuyenUI extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private Search_Item search_Item;
    private JButton btnThem, btnXemChiTiet, btnXoa, btnNhapExc, btnXuatExc;

    private ArrayList<NhomQuyen> listNhomQuyen;

    public NhomQuyenUI() {
        setLayout(new BorderLayout());

        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.add(taoTopPanel(), BorderLayout.NORTH);
        centerContainer.add(taoPanelTable(), BorderLayout.CENTER);

        add(centerContainer, BorderLayout.CENTER);
        ganSuKien();
        loadDuLieu();
    }

    public void suaLaiGiaoDienTheoQuyen() {
        var listQuyen = PhienDangNhap.getListQuyen();

        if (!listQuyen.contains("PQ_TAO")) {
            btnThem.setVisible(false);
        }

        if (!listQuyen.contains("PQ_XOA")) {
            btnXoa.setVisible(false);
        }
        this.revalidate();
        this.repaint();
    }

    private JPanel taoTopPanel() {
        JPanel top = new JPanel();
        top.setPreferredSize(new Dimension(100, 45));
        top.setLayout(new FlowLayout(FlowLayout.LEFT));
        top.setBackground(Color.WHITE);

        search_Item = new Search_Item(300, 32);
        top.add(search_Item);

        btnThem = new JButton("Thêm");
        btnThem.setPreferredSize(new Dimension(80, 32));
        top.add(btnThem);

        btnXoa = new JButton("Xóa");
        btnXoa.setPreferredSize(new Dimension(80, 32));
        top.add(btnXoa);

        btnXemChiTiet = new JButton("Xem chi tiết quyền");
        btnXemChiTiet.setPreferredSize(new Dimension(150, 32));
        top.add(btnXemChiTiet);

        btnXuatExc = new JButton("Xuất Exc");
        btnXuatExc.setPreferredSize(new Dimension(80, 32));
        top.add(btnXuatExc);

        btnNhapExc = new JButton("Nhập Exc");
        btnNhapExc.setPreferredSize(new Dimension(80, 32));
        top.add(btnNhapExc);

        return top;
    }

    private JPanel taoPanelTable() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columns = { "Mã nhóm quyền", "Tên nhóm quyền", "Tổng số quyền" };
        model = new DefaultTableModel(columns, 0);

        JScrollPane scrollPane = TaoUI.taoTableScroll(model);
        table = (JTable) scrollPane.getViewport().getView();
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    public void loadDuLieu() {
        NhomQuyenBUS nhomQuyenBUS = NhomQuyenBUS.getNhomQuyenBUS();
        model.setRowCount(0);
        listNhomQuyen = nhomQuyenBUS.layDanhSachNhomQuyen();
        for (NhomQuyen nhomQuyen : listNhomQuyen) {
            if (nhomQuyen.getTenNhomQuyen().contains(search_Item.getTextSearch().toString())) {
                model.addRow(
                        new Object[] { nhomQuyen.getMaNQ(), nhomQuyen.getTenNhomQuyen(),
                                nhomQuyen.getListQuyen().size() });
            }

        }
    }

    private void ganSuKien() {
        search_Item.setEvent(this::loadDuLieu);

        btnXemChiTiet.addActionListener(e -> {
            int dongChon = table.getSelectedRow();
            if (dongChon >= 0) {
                NhomQuyenBUS nhomQuyenBUS = NhomQuyenBUS.getNhomQuyenBUS();
                NhomQuyen nhomQuyen = nhomQuyenBUS.timNhomQuyen(model.getValueAt(dongChon, 0).toString());
                if (nhomQuyen != null) {
                    new NhomQuyenDialog(nhomQuyen, this);
                }
            } else {
                TaoTinNhan.showAutoCloseMessage("Vui lòng chọn nhóm quyền để xem chi tiết", "Thông báo", 1);
            }

        });
        btnThem.addActionListener(e -> {
            new NhomQuyenDialog(null, this);
        });

        btnXoa.addActionListener(e -> {
            int dongChon = table.getSelectedRow();
            if (dongChon >= 0) {
                NhomQuyenBUS nhomQuyenBUS = NhomQuyenBUS.getNhomQuyenBUS();
                NhomQuyen nhomQuyen = nhomQuyenBUS.timNhomQuyen(model.getValueAt(dongChon, 0).toString());
                if (nhomQuyenBUS.xoaNhomQuyen(nhomQuyen)) {
                    TaoTinNhan.showAutoCloseMessage("Xóa nhóm quyền thành công", "Thông báo", 1);
                    loadDuLieu();
                } else {
                    TaoTinNhan.showAutoCloseMessage("Xóa nhóm quyền thất bại", "Thông báo", 1);
                }
            } else {
                TaoTinNhan.showAutoCloseMessage("Vui lòng chọn nhóm quyền để xóa", "Thông báo", 1);
            }
        });
        btnXuatExc.addActionListener(e -> {
            NhomQuyenBUS.getNhomQuyenBUS().XuatExc();
        });
        btnNhapExc.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Chọn file Excel để nhập");
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();

                NhomQuyenBUS.getNhomQuyenBUS().nhapExcelPhanQuyen(selectedFile);
            }
        });
    }
}