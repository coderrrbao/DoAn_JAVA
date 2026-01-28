package ui.quanlysanpham;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import bus.NguyenLieuBUS;
import dto.ChiTietCongThuc;
import dto.CongThuc;
import dto.NguyenLieu;
import dto.SanPham;
import util.TaoUI;

public class XemCongThucDialog extends JDialog {
    private JButton btnXoa, btnThem, btnSua;
    private SanPham sanPham;
    DefaultTableModel model;
    private CongThuc congThuc;
    private JTable table;

    public XemCongThucDialog(JDialog ouner, SanPham sanPham) {
        super(ouner, "Xem chi tiết");
        this.sanPham = sanPham;
        if (sanPham != null) {
            this.congThuc = sanPham.getCongThuc();
        }
        setSize(600, 300);
        setLocationRelativeTo(ouner);
        setLayout(new BorderLayout());
        initGUI();
        capNhapDuLieu(sanPham);
        ganSuKien();
    }

    private void initGUI() {
        model = new DefaultTableModel();
        model.addColumn("Mã CTCT");
        model.addColumn("Mã nguyên liệu");
        model.addColumn("Tên nguyên liệu");
        model.addColumn("Định lượng");
        model.addColumn("Đơn vị");

        add(taoTopPanel(), BorderLayout.NORTH);
        JScrollPane scrollPane = TaoUI.taoTableScroll(model);
        table = (JTable) scrollPane.getViewport().getView();
        table.removeColumn(table.getColumnModel().getColumn(0));
        add(scrollPane, BorderLayout.CENTER);
    }

    public void capNhapDuLieu(SanPham sanPham) {
        if (sanPham == null) {
            return;
        }
        model.setRowCount(0);
        if (sanPham.getCongThuc() != null) {

            for (ChiTietCongThuc chiTietCongThuc : sanPham.getCongThuc().getListChiTietCongThuc()) {
                model.addRow(
                        new Object[] { chiTietCongThuc.getMaCTCT(), chiTietCongThuc.getNguyenLieu().getMaNL(),
                                chiTietCongThuc.getNguyenLieu().getTenNL(), chiTietCongThuc.getSoLuong(),
                                chiTietCongThuc.getNguyenLieu().getDonVi() });
            }
        }
    }

    private JPanel taoTopPanel() {
        JPanel top = TaoUI.taoPanelBoxLayoutNgang(600, 30);
        JLabel lbTable = new JLabel("   Bảng công thức");
        lbTable.setFont(new Font(null, Font.BOLD, 18));
        top.add(lbTable);
        top.add(Box.createHorizontalGlue());
        btnSua = new JButton("Sửa");
        btnThem = new JButton("Thêm");
        btnXoa = new JButton("Xóa");
        top.add(btnThem);
        top.add(Box.createRigidArea(new Dimension(3, 0)));
        top.add(btnXoa);
        top.add(Box.createRigidArea(new Dimension(3, 0)));
        top.add(btnSua);
        top.add(Box.createRigidArea(new Dimension(3, 0)));
        return top;
    }

    public void themNLVaoBang(String maCTCT, String maNL, String tenNL, double dinhLuong, String donVi) {
        model.addRow(
                new Object[] { maCTCT, maNL, tenNL, dinhLuong, donVi });
    }

    public void suaNL(String maCTCT, String maNL, String tenNL, double dinhLuong, String donVi, int dong) {
        if (dong >= 0 && dong < model.getRowCount()) {
            model.setValueAt(maCTCT, dong, 0);
            model.setValueAt(maNL, dong, 1);
            model.setValueAt(tenNL, dong, 2);
            model.setValueAt(dinhLuong, dong, 3);
            model.setValueAt(donVi, dong, 4);
        }
    }

    private void ganSuKien() {
        btnThem.addActionListener(e -> {
            new ChiTietCTDialog(this, null);
        });

        btnSua.addActionListener(e -> {
            int dong = table.getSelectedRow();
            if (dong >= 0) {
                double soLuong = Double.parseDouble(table.getModel().getValueAt(dong, 3).toString());
                NguyenLieu nguyenLieu = new NguyenLieu();
                nguyenLieu.setMaNL(table.getModel().getValueAt(dong, 1).toString());
                nguyenLieu.setTenNL(table.getModel().getValueAt(dong, 2).toString());
                ChiTietCongThuc chiTietCongThuc = new ChiTietCongThuc(table.getModel().getValueAt(dong, 0).toString(),
                        "",
                        nguyenLieu, soLuong);
                new ChiTietCTDialog(this, chiTietCongThuc, dong);
            }

            else {
                JOptionPane.showMessageDialog(null, "Vui chọn chi tiết công thức để sửa", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        btnXoa.addActionListener(e -> {
            int dong = table.getSelectedRow();
            if (dong >= 0) {
                model.removeRow(dong);

            } else {
                JOptionPane.showMessageDialog(null, "Vui chọn chi tiết công thức để xóa", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    public CongThuc dongGoiCongThuc() {
        ArrayList<ChiTietCongThuc> listChiTietCongThuc = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            NguyenLieuBUS nguyenLieuBUS = new NguyenLieuBUS();
            NguyenLieu nguyenLieu = nguyenLieuBUS.timNguyenLieu(model.getValueAt(i, 1).toString());
            String maCT = congThuc == null ? "" : congThuc.getMaCT();
            String maCTCT = model.getValueAt(i, 0).toString();
            ChiTietCongThuc chiTietCongThuc = new ChiTietCongThuc(maCT, maCTCT, nguyenLieu,
                    Double.parseDouble(model.getValueAt(i, 3).toString()));
            listChiTietCongThuc.add(chiTietCongThuc);
        }
        return new CongThuc(congThuc == null ? "" : congThuc.getMaCT(), listChiTietCongThuc);
    }

}
