package ui.thongke;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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

    public ThongKeSanPhamPanel() {
        setLayout(new BorderLayout());
        JPanel thongKeSp = new JPanel();
        thongKeSp.setLayout(new BoxLayout(thongKeSp, BoxLayout.Y_AXIS));

        JPanel top = TaoUI.taoPanelBoxLayoutNgang(1200, 100);
        top.add(new ThongKeChungSpPanel());
        thongKeSp.add(top);

        JPanel mid = TaoUI.taoPanelBoxLayoutNgang(1200, 400);
        thongKeSp.add(mid);
        JPanel ctnMid = TaoUI.taoPanelBorderLayout(1200, 400);
        mid.add(ctnMid);
        JPanel bieuDoTron = TaoUI.taoPanelBorderLayout(350, 400);
        JPanel bieuDoCot = TaoUI.taoPanelBorderLayout(800, 400);
        ctnMid.add(bieuDoTron, BorderLayout.WEST);
        ctnMid.add(bieuDoCot, BorderLayout.CENTER);
        bieuDoCot.setBackground(Color.red);
        bieuDoTron.setBackground(Color.ORANGE);

        datasetCot = new DefaultCategoryDataset();
        bieuDoCot.add(TaoUI.taoBieuDoCot("Top 5 sản phẩm bán chạy", "Số lượng", "Sản phẩm", datasetCot),
                BorderLayout.CENTER);

        datasetTron = new DefaultPieDataset();

        bieuDoTron.add(TaoUI.taoBieuDoTron("Số sản phẩm bán ra theo danh mục", datasetTron), BorderLayout.CENTER);

        JPanel bottom = TaoUI.taoPanelBorderLayout(1200, 200);
        bottom.setBackground(Color.cyan);
        thongKeSp.add(bottom);

        model = new DefaultTableModel();
        model.addColumn("Mã sản phẩm");
        model.addColumn("Tên sản phẩm");
        model.addColumn("Số lượng mua");
        JScrollPane scrollPaneTb = TaoUI.taoTableScroll(model);
        bottom.add(scrollPaneTb);

        JScrollPane scrollPane = TaoUI.taoScrollPane(thongKeSp);
        add(scrollPane, BorderLayout.CENTER);

        loaiDuLieu();
        ganSuKien();
    }

    private void themPhanTuVaoBieuDoTron(String tenDanhMuc, int soLuong) {
        datasetTron.setValue(tenDanhMuc, soLuong);
    }

    private void themPhanTuVaoBieuDoCot(String tenSanPham, int soLuong) {
        datasetCot.addValue(soLuong, "Sản phẩm", tenSanPham);
    }

    private void themPhanTuVaoTable(SanPham sanPham, int soLuong) {
        model.addRow(new Object[] { sanPham.getMaSP(), sanPham.getTenSP(), 1 });
    }

    private void loadBieuDoTron() {
        Map<DanhMuc, Integer> spBanRaTheoDM = thongKeBUS.laySL_SP_BanRaTheoDanhMuc();
        spBanRaTheoDM.forEach((danhMuc, soLuong) -> {
            themPhanTuVaoBieuDoTron(danhMuc.getTenDM(), soLuong);
        });
    }

    private void loadBieuDoCot() {
        Map<SanPham, Integer> top5SanPham = thongKeBUS.layTop5_SanPhamBanChay();
        for (Map.Entry<SanPham, Integer> entry : top5SanPham.entrySet()) {
            themPhanTuVaoBieuDoCot(entry.getKey().getTenSP(), entry.getValue());
        }
    }
    private void loadTable(){
        Map<SanPham,Integer> laySpBanChay = thongKeBUS.laySL_SP_BanRaGiamDan();
         for (Map.Entry<SanPham, Integer> entry : laySpBanChay.entrySet()) {
            themPhanTuVaoTable(entry.getKey(), entry.getValue());
         }
    }

    private void ganSuKien() {

    }

    private void loaiDuLieu() {
        loadBieuDoTron();
        loadBieuDoCot();
        loadTable();
    }

}
