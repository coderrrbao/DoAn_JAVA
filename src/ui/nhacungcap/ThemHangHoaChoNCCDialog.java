package ui.nhacungcap;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import bus.NguyenLieuBUS;
import bus.SanPhamBUS;
import dto.ChiTietNhaCungCap;
import dto.NguyenLieu;
import dto.SanPham;
import ui.component.Search_Item;
import util.TaoTinNhan;
import util.TaoUI;

public class ThemHangHoaChoNCCDialog extends JDialog {

    private JComboBox<String> cbLoaiHang;
    private JTable tblHangHoa;
    private DefaultTableModel modelHangHoa;
    private TableRowSorter<DefaultTableModel> rowSorter;
    private Search_Item search_Item;

    private JTextField txtGiaNhap;
    private JButton btnThem, btnHuy;

    private ChiTietNhaCungCap ketQuaDoiTuongChon = null;

    public ThemHangHoaChoNCCDialog(Frame owner) {
        super(owner, "Thêm Hàng Hóa Cho Nhà Cung Cấp", true);
        setSize(600, 500);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        khoiTaoUI();
        loadDuLieu();
        ganSuKien();
    }

    private void khoiTaoUI() {
        JPanel pnTop = TaoUI.taoPanelBoxLayoutNgang(600, 30);
        search_Item = new Search_Item(300, 29);

        String[] loaiHang = { "Sản phẩm", "Nguyên liệu" };
        cbLoaiHang = new JComboBox<>(loaiHang);
        TaoUI.setFixSize(cbLoaiHang, 150, 30);

        pnTop.add(search_Item);
        pnTop.add(Box.createHorizontalGlue());
        pnTop.add(cbLoaiHang);

        add(pnTop, BorderLayout.NORTH);

        JPanel pnCenter = new JPanel(new BorderLayout());

        modelHangHoa = new DefaultTableModel(new Object[] { "Mã", "Tên Hàng Hóa", "Loại hàng hóa" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        rowSorter = new TableRowSorter<>(modelHangHoa);

        JScrollPane scrollPane = TaoUI.taoTableScroll(modelHangHoa);
        tblHangHoa = (JTable) scrollPane.getViewport().getView();
        tblHangHoa.setRowSorter(rowSorter);
        tblHangHoa.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblHangHoa.setRowHeight(30);

        pnCenter.add(scrollPane, BorderLayout.CENTER);
        add(pnCenter, BorderLayout.CENTER);

        JPanel pnBottom = TaoUI.taoPanelBoxLayoutDoc(3000, 100);
        pnBottom.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        JPanel pnGiaNhap = TaoUI.taoPanelCanGiua(3000, 30);
        TaoUI.addItem(pnGiaNhap, new JLabel("Giá nhập (VNĐ):"), 10, true);
        txtGiaNhap = new JTextField();
        TaoUI.setFixSize(txtGiaNhap, 200, 30);

        TaoUI.addItem(pnGiaNhap, txtGiaNhap, 10, true);

        JPanel pnButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        TaoUI.setFixSize(pnButtons, 3000, 30);
        btnHuy = new JButton("Hủy");
        btnThem = new JButton("Thêm");
        TaoUI.setFixSize(btnHuy, 100, 30);
        TaoUI.setFixSize(btnThem, 100, 30);
        
        pnButtons.add(btnThem);
        pnButtons.add(btnHuy);

        pnBottom.add(pnGiaNhap);
        pnBottom.add(Box.createVerticalStrut(15));
        pnBottom.add(pnButtons);

        add(pnBottom, BorderLayout.SOUTH);
    }

    private void loadDuLieu() {
        modelHangHoa.setRowCount(0);

        String loaiChon = cbLoaiHang.getSelectedItem().toString();

        if (loaiChon.equals("Sản phẩm")) {
            SanPhamBUS sanPhamBUS = SanPhamBUS.getSanPhamBUS();
            ArrayList<SanPham> dsSanPham = sanPhamBUS.layListSanPham();
            if (dsSanPham != null) {
                for (SanPham sp : dsSanPham) {
                    if (sp.getTenSP().contains(search_Item.getTextSearch())) {
                        modelHangHoa.addRow(new Object[] { sp.getMaSP(), sp.getTenSP(), "Sản phẩm" });
                    }
                }
            }
        } else if (loaiChon.equals("Nguyên liệu")) {
            NguyenLieuBUS nguyenLieuBUS = NguyenLieuBUS.getNguyenLieuBUS();
            ArrayList<NguyenLieu> dsNguyenLieu = nguyenLieuBUS.layListNguyenLieu();
            if (dsNguyenLieu != null) {
                for (NguyenLieu nl : dsNguyenLieu) {
                    if (nl.getTenNL().contains(search_Item.getTextSearch())) {
                        modelHangHoa.addRow(new Object[] { nl.getMaNL(), nl.getTenNL(), "Nguyên liệu" });
                    }

                }
            }
        }
    }

    private void ganSuKien() {
        cbLoaiHang.addActionListener(e -> {
            loadDuLieu();
            search_Item.setSearchText("");
        });

        search_Item.setEvent(() -> {
            loadDuLieu();
        });

        btnHuy.addActionListener(e -> {
            ketQuaDoiTuongChon = null;
            dispose();
        });

        btnThem.addActionListener(e -> {
            int row = tblHangHoa.getSelectedRow();

            if (row == -1) {
                TaoTinNhan.showAutoCloseMessage("Vui lòng chọn một mặt hàng!", "Cảnh báo", 1);
                return;
            }

            String giaNhapStr = txtGiaNhap.getText().trim();
            if (giaNhapStr.isEmpty()) {
                TaoTinNhan.showAutoCloseMessage("Vui lòng nhập giá!", "Cảnh báo", 1);
                txtGiaNhap.requestFocus();
                return;
            }

            try {
                double giaNhap = Double.parseDouble(giaNhapStr);
                if (giaNhap <= 0) {
                    TaoTinNhan.showAutoCloseMessage("Giá phải > 0!", "Cảnh báo", 1);
                    return;
                }

                int modelRow = tblHangHoa.convertRowIndexToModel(row);
                String maHang = modelHangHoa.getValueAt(modelRow, 0).toString();
                String loaiHang = cbLoaiHang.getSelectedItem().toString();

                ketQuaDoiTuongChon = new ChiTietNhaCungCap();
                ketQuaDoiTuongChon.setLoaiDoiTuong(loaiHang);
                ketQuaDoiTuongChon.setMaDoiTuong(maHang);
                ketQuaDoiTuongChon.setGiaNhap(giaNhap);

                dispose();
            } catch (NumberFormatException ex) {
                TaoTinNhan.showAutoCloseMessage("Giá nhập không hợp lệ!", "Lỗi", 1);
            }
        });
    }

    public ChiTietNhaCungCap getHangHoaDuocChon() {
        return ketQuaDoiTuongChon;
    }

    public static void main(String[] args) {
        ThemHangHoaChoNCCDialog themHangHoaChoNCCDialog = new ThemHangHoaChoNCCDialog(null);
        themHangHoaChoNCCDialog.setVisible(true);
    }
}