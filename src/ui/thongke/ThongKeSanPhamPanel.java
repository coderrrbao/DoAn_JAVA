package ui.thongke;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import bus.ThongKeBUS;
import dto.DanhMuc;
import dto.SanPham;
import ui.thongke.thongkechung.ThongKeChungSpPanel;
import util.TaoUI;

public class ThongKeSanPhamPanel extends JPanel {
    private DefaultCategoryDataset datasetCot;
    DefaultTableModel model;
    DefaultPieDataset datasetTron;

    private ThongKeBUS thongKeBUS = new ThongKeBUS();
    private ThongKeChungSpPanel thongKeChungSpPanel;

    public ThongKeSanPhamPanel() {
        setLayout(new BorderLayout());

        JPanel top = TaoUI.taoPanelBorderLayout(3000, 450);
        thongKeChungSpPanel = new ThongKeChungSpPanel();
        top.add(thongKeChungSpPanel, BorderLayout.NORTH);
        add(top, BorderLayout.NORTH);

        JPanel bieuDoPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JPanel bieuDoTron = TaoUI.taoPanelBorderLayout(570, 320);
        TaoUI.suaBorderChoPanel(top, 10, 0, 10, 0);
        JPanel bieuDoCot = TaoUI.taoPanelBorderLayout(700, 320);

        bieuDoPanel.add(bieuDoTron);
        bieuDoPanel.add(bieuDoCot);
        top.add(bieuDoPanel, BorderLayout.CENTER);
        datasetCot = new DefaultCategoryDataset();
        bieuDoCot.add(TaoUI.taoBieuDoCot("Top 5 sản phẩm bán chạy", "Số lượng", "Sản phẩm", datasetCot),
                BorderLayout.CENTER);

        datasetTron = new DefaultPieDataset();

        bieuDoTron.add(TaoUI.taoBieuDoTron("Số sản phẩm bán ra theo danh mục", datasetTron), BorderLayout.CENTER);
        model = new DefaultTableModel();
        model.addColumn("Mã sản phẩm");
        model.addColumn("Tên sản phẩm");
        model.addColumn("Số lượng mua");
        JScrollPane scrollPane = TaoUI.taoTableScroll(model);
        JTable table = (JTable) scrollPane.getViewport().getView();
        table.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        add(scrollPane, BorderLayout.CENTER);
        TaoUI.suaBorderChoPanel(this, 0, 10, 0, 10);
        loadDuLieu();
    }

    private void themPhanTuVaoBieuDoTron(String tenDanhMuc, int soLuong) {
        datasetTron.setValue(tenDanhMuc, soLuong);
    }

    private void themPhanTuVaoBieuDoCot(String tenSanPham, int soLuong) {
        datasetCot.addValue(soLuong, "Sản phẩm", tenSanPham);
    }

    private void themPhanTuVaoTable(SanPham sanPham, int soLuong) {
        model.addRow(new Object[] { sanPham.getMaSP(), sanPham.getTenSP(), soLuong });
    }

    private void loadBieuDoTron() {
        datasetCot.clear();
        Map<DanhMuc, Integer> spBanRaTheoDM = thongKeBUS.laySL_SP_BanRaTheoDanhMuc();
        spBanRaTheoDM.forEach((danhMuc, soLuong) -> {
            themPhanTuVaoBieuDoTron(danhMuc.getTenDM(), soLuong);
        });
    }

    private void loadBieuDoCot() {
        datasetCot.clear();
        Map<SanPham, Integer> top5SanPham = thongKeBUS.layTop5_SanPhamBanChay();
        for (Map.Entry<SanPham, Integer> entry : top5SanPham.entrySet()) {
            themPhanTuVaoBieuDoCot(entry.getKey().getTenSP(), entry.getValue());
        }
    }

    private void loadTable() {
        model.setRowCount(0);
        Map<SanPham, Integer> laySpBanChay = thongKeBUS.laySL_SP_BanRaGiamDan();
        for (Map.Entry<SanPham, Integer> entry : laySpBanChay.entrySet()) {
            themPhanTuVaoTable(entry.getKey(), entry.getValue());
        }
    }

    public void loadDuLieu() {
        loadBieuDoTron();
        loadBieuDoCot();
        loadTable();
        thongKeChungSpPanel.loadDuLieu();
    }

}
