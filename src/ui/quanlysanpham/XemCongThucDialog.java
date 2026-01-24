package ui.quanlysanpham;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import dto.ChiTietCongThuc;
import dto.CongThuc;
import dto.SanPham;
import util.TaoUI;

public class XemCongThucDialog extends JDialog {
    private JButton btnXoa, btnThem, btnSua;
    private SanPham sanPham;

    private CongThuc congThuc;

    public XemCongThucDialog(JDialog ouner, SanPham sanPham) {
        super(ouner, "Xem chi tiết");
        this.sanPham = sanPham;
        setSize(600, 300);
        setLocationRelativeTo(ouner);
        setLayout(new BorderLayout());
        initGUI();
    }

    private void initGUI() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("STT");
        model.addColumn("Tên nguyên liệu");
        model.addColumn("Định lượng");
        model.addColumn("Đơn vị");

        if (sanPham == null) {
            return;
        }
        int stt = 1;
        for (ChiTietCongThuc chiTietCongThuc : sanPham.getCongThuc().getListChiTietCongThuc()) {
            model.addRow(new Object[] { stt++, chiTietCongThuc.getNguyenLieu().getTenNL(), chiTietCongThuc.getSoLuong(),
                    chiTietCongThuc.getNguyenLieu().getDonVi() });
        }

        add(taoTopPanel(), BorderLayout.NORTH);
        JScrollPane scrollPane = TaoUI.taoTableScroll(model);
        add(scrollPane, BorderLayout.CENTER);
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
    public CongThuc dongGoiCongThuc(){
        return new CongThuc();
    }
    public CongThuc getCongThuc() {
        return congThuc;
    }
}
