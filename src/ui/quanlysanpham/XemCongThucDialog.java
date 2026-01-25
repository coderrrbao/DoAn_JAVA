package ui.quanlysanpham;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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

    public XemCongThucDialog(JDialog ouner, SanPham sanPham) {
        super(ouner, "Xem chi tiết");
        this.sanPham = sanPham;
        setSize(600, 300);
        setLocationRelativeTo(ouner);
        setLayout(new BorderLayout());
        initGUI();
        capNhapDuLieu();
        ganSuKien();
    }

    private void initGUI() {
        model = new DefaultTableModel();
        model.addColumn("Mã nguyên liệu");
        model.addColumn("Tên nguyên liệu");
        model.addColumn("Định lượng");
        model.addColumn("Đơn vị");

        add(taoTopPanel(), BorderLayout.NORTH);
        JScrollPane scrollPane = TaoUI.taoTableScroll(model);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void capNhapDuLieu() {
        if (sanPham == null) {
            return;
        }
        if (sanPham.getCongThuc() != null) {
            for (ChiTietCongThuc chiTietCongThuc : sanPham.getCongThuc().getListChiTietCongThuc()) {
                model.addRow(
                        new Object[] { chiTietCongThuc.getNguyenLieu().getMaNL(),chiTietCongThuc.getNguyenLieu().getTenNL(), chiTietCongThuc.getSoLuong(),
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

    public void themNLVaoBang(NguyenLieu nguyenLieu,int  dinhLuong){
        model.addRow(new Object[]{nguyenLieu.getMaNL(),nguyenLieu.getTenNL(),dinhLuong,nguyenLieu.getDonVi()});
    }

    private void ganSuKien() {
        btnThem.addActionListener(e -> {
            new ThemNLDialog(this);
        });
    }

    public CongThuc dongGoiCongThuc() {
        ArrayList <ChiTietCongThuc> listChiTietCongThuc  =  new ArrayList<>();
        for (int i=0;i<model.getRowCount();i++){
            NguyenLieuBUS  nguyenLieuBUS =  new NguyenLieuBUS();
            NguyenLieu nguyenLieu = nguyenLieuBUS.timNguyenLieu(model.getValueAt(i, 0).toString());
            ChiTietCongThuc chiTietCongThuc  = new ChiTietCongThuc("","", nguyenLieu,Integer.parseInt(model.getValueAt(i, 2).toString()));
            listChiTietCongThuc.add(chiTietCongThuc);
        }
        return new CongThuc("",listChiTietCongThuc);
    }

    public CongThuc getCongThuc() {
        return congThuc;
    }
}
