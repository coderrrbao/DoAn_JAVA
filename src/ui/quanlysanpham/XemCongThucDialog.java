package ui.quanlysanpham;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import dto.ChiTietCongThuc;
import dto.SanPham;
import util.TaoUI;

public class XemCongThucDialog extends JDialog {
    private JButton btnXoa, btnThem, btnSua;
    private SanPham sanPham;

    public XemCongThucDialog(JDialog ouner, SanPham sanPham) {
        super(ouner, "Xem chi tiết");
        this.sanPham = sanPham;
        setSize(600, 300);
        setLocationRelativeTo(ouner);
        setLayout(new BorderLayout());
        initGUI();
        setVisible(true);
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
        JPanel titlePanel = TaoUI.taoPanelCanGiua(600, 30);
        titlePanel.add(new JLabel("Công thức pha chế"));
        JPanel top = TaoUI.taoPanelBorderLayout(600, 60);
        top.add(titlePanel, BorderLayout.CENTER);

        JPanel buttons = TaoUI.taoPanelBoxLayoutNgang(600, 30);
        btnSua = new JButton("Sửa");
        btnThem = new JButton("Thêm");
        btnXoa = new JButton("Xóa");
        buttons.add(btnThem);
        buttons.add(btnSua);
        buttons.add(btnXoa);
        top.add(buttons, BorderLayout.NORTH);
        return top;
    }
}
