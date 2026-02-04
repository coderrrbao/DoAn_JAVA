package ui.kiemke;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import bus.PhieuKiemKeBUS;
import dto.PhieuKiemKe;
import ui.component.LocNgay_Item;
import ui.component.Search_Item;
import util.TaoUI;

public class KiemKeUI extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private LocNgay_Item locNgay;
    private Search_Item search_Item;
    private JButton btnThem;
    private JButton btnSua;

    public KiemKeUI() {
        setLayout(new BorderLayout());

        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.add(taoTopPanel(), BorderLayout.NORTH);
        centerContainer.add(taoPanelTable(), BorderLayout.CENTER);

        add(centerContainer, BorderLayout.CENTER);
        loaiDuLieu();
        ganSuKien();
    }

    private void ganSuKien() {
        btnThem.addActionListener(e -> {
            ThemPhieuKiemDialog them = new ThemPhieuKiemDialog(this);
            them.setVisible(true);
        });
    }

    public void loaiDuLieu() {
        model.setRowCount(0);
        PhieuKiemKeBUS phieuKiemKeBUS = new PhieuKiemKeBUS();
        ArrayList<PhieuKiemKe> listPhieuKiemKe = phieuKiemKeBUS.layListKiemKe();
        int stt = 1;
        for (PhieuKiemKe phieuKiemKe : listPhieuKiemKe) {
            model.addRow(new Object[] { stt++, phieuKiemKe.getMaKK(), phieuKiemKe.getMaNV(), phieuKiemKe.getNgayKiem(),
                    phieuKiemKe.getMaLo(), phieuKiemKe.getLoaiLo(), phieuKiemKe.getSoLuongSoSach(),
                    phieuKiemKe.getSoLuongThuc(), phieuKiemKe.getSoLuongThuc() - phieuKiemKe.getSoLuongSoSach(),
                    phieuKiemKe.getGhiChu(), phieuKiemKe.getTrangThaiXuLy()});
        }
    }

    private JPanel taoTopPanel() {
        JPanel top = new JPanel();
        top.setPreferredSize(new Dimension(100, 35));
        top.setLayout(new FlowLayout(FlowLayout.LEFT));
        top.setBackground(Color.WHITE);

        locNgay = new LocNgay_Item(350, 35);
        top.add(locNgay);

        search_Item = new Search_Item(300, 35);
        top.add(search_Item);

        btnThem = new JButton("Thêm");
        btnThem.setPreferredSize(new Dimension(80, 35));
        top.add(btnThem);

        btnSua = new JButton("Sửa");
        btnSua.setPreferredSize(new Dimension(btnSua.getPreferredSize().width, 35));
        top.add(btnSua);

        return top;
    }

    private JPanel taoPanelTable() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = { "STT", "Mã Phiếu Kiểm", "Mã NV", "Ngày kiểm", "Mã lô", "Loại lô", "SL sổ sách",
                "SL thực tế", "Chênh lệch",
                "Ghi chú", "Trạng thái" };
        model = new DefaultTableModel(columns, 0);
        JScrollPane scrollPane = TaoUI.taoTableScroll(model);
        table  = (JTable) scrollPane.getViewport().getView();
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

}