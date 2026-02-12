package ui.thongke;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import bus.NhaCungCapBUS;
import bus.PhieuNhapNguyenLieuBUS;
import bus.PhieuNhapSanPhamBUS;
import dto.NhaCungCap;
import dto.PhieuNhapNguyenLieu;
import dto.PhieuNhapSanPham;
import ui.component.LocNgay_Item;
import ui.thongke.thongkechung.ThongKeChungNhapPanel;
import util.TaoUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class ThongKeNhapHangPanel extends JPanel {
    private ThongKeChungNhapPanel thongKeChungNH;
    private JButton xuatExbtn;
    private LocNgay_Item locNgay;
    private DefaultTableModel modelSP, modelNL;

    private ArrayList<PhieuNhapNguyenLieu> listPhieuNhapNguyenLieu = null;
    private ArrayList<PhieuNhapSanPham> listPhieuNhapSanPham = null;
    private PhieuNhapNguyenLieuBUS phieuNhapNguyenLieuBUS = PhieuNhapNguyenLieuBUS.getPhieuNhapNguyenLieuBUS();
    private PhieuNhapSanPhamBUS phieuNhapSanPhamBUS = PhieuNhapSanPhamBUS.getPhieuNhapSanPhamBUS();
    JLabel soLoSP, soLoNL, tongTienNhapNL, tongTienNhapSP, tongTienNhapChung;

    public ThongKeNhapHangPanel() {
        setLayout(new BorderLayout());

        initGUI();
        loadDuLieu();
    }

    private JPanel buttonPanel() {
        JPanel buttonPanel = TaoUI.taoPanelBoxLayoutNgang(880, 30);
        locNgay = new LocNgay_Item(400, 30);
        xuatExbtn = new JButton("Xuất exel");
        TaoUI.setFixSize(xuatExbtn, 100, 30);
        buttonPanel.add(locNgay);
        buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        buttonPanel.add(xuatExbtn);
        return buttonPanel;
    }

    private void initGUI() {
        JPanel thongKeNHPanel = new JPanel();
        thongKeNHPanel.setLayout(new BoxLayout(thongKeNHPanel, BoxLayout.Y_AXIS));
        thongKeChungNH = new ThongKeChungNhapPanel();
        thongKeNHPanel.add(thongKeChungNH);
        JPanel top = new JPanel(new BorderLayout());
        top.add(thongKeNHPanel, BorderLayout.NORTH);
        top.add(buttonPanel(), BorderLayout.CENTER);

        JPanel tablePanel = new JPanel(new GridLayout(0, 2, 10, 10));
        TaoUI.setFixSize(tablePanel, 880, 600);
        tablePanel.add(thongKeSpPanel());
        tablePanel.add(thongKeNguyenLieu());

        add(top, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(tongChungPanel(), BorderLayout.SOUTH);
    }

    public void loadDuLieu() {
        listPhieuNhapNguyenLieu = phieuNhapNguyenLieuBUS.layListPhieuNhapNguyenLieu();
        listPhieuNhapSanPham = phieuNhapSanPhamBUS.layListPhieuNhapSanPham();

        double giaNhapSP = 0;
        double giaNhapNL = 0;
        modelNL.setRowCount(0);
        modelSP.setRowCount(0);
        NhaCungCapBUS nhaCungCapBUS = new NhaCungCapBUS();
        for (PhieuNhapNguyenLieu phieuNhapNguyenLieu : listPhieuNhapNguyenLieu) {
            NhaCungCap nhaCungCap = nhaCungCapBUS.timNhaCungCap(phieuNhapNguyenLieu.getMaNCC());
            modelNL.addRow(new Object[] { phieuNhapNguyenLieu.getMaPN(), phieuNhapNguyenLieu.getNgayNhap(),
                    phieuNhapNguyenLieu.getMaNV(), phieuNhapNguyenLieu.getGhiChu(), nhaCungCap.getTenNCC() });
            giaNhapNL += phieuNhapNguyenLieu.getTongTien();
        }
        for (PhieuNhapSanPham phieuNhapSanPham : listPhieuNhapSanPham) {
            NhaCungCap nhaCungCap = nhaCungCapBUS.timNhaCungCap(phieuNhapSanPham.getMaNCC());
            modelSP.addRow(new Object[] { phieuNhapSanPham.getMaPN(), phieuNhapSanPham.getNgayNhap(),
                    phieuNhapSanPham.getMaNV(), phieuNhapSanPham.getGhiChu(), nhaCungCap.getTenNCC() });
            giaNhapSP += phieuNhapSanPham.getTongTien();
        }
        soLoNL.setText("Số lượng: " + listPhieuNhapNguyenLieu.size() + " lô");
        soLoSP.setText("Số lượng: " + listPhieuNhapSanPham.size() + " lô");
        tongTienNhapSP.setText(chuyenDinhDangTienTe(giaNhapSP));
        tongTienNhapNL.setText(chuyenDinhDangTienTe(giaNhapNL));
        tongTienNhapChung.setText(chuyenDinhDangTienTe(giaNhapNL + giaNhapSP));
        thongKeChungNH.setLoNguyenLieu(listPhieuNhapNguyenLieu.size());
        thongKeChungNH.setLoSanPham(listPhieuNhapSanPham.size());
        thongKeChungNH.setTongLo(listPhieuNhapNguyenLieu.size() + listPhieuNhapSanPham.size());

    }

    private JPanel thongKeSpPanel() {
        JPanel thongKeLoSp = new JPanel(new BorderLayout());

        JPanel top = TaoUI.taoPanelCanGiua(880, 40);
        JLabel jLabel = new JLabel("Danh sách nhập sản phẩm");
        jLabel.setFont(new Font(null, Font.BOLD, 16));
        top.add(jLabel);
        top.setBackground(new Color(225, 235, 245));
        String[] columns = { "Mã Phiếu nhập", "Ngày nhập", "Nhân viên tạo phiếu", "Ghi chú", "Nhà cung cấp" };

        modelSP = new DefaultTableModel(columns, 0);
        JScrollPane table = TaoUI.taoTableScroll(modelSP);

        // --- Cập nhật Bottom ---
        JPanel bottom = TaoUI.taoPanelBoxLayoutNgang(880, 40);
        soLoSP = new JLabel("Số lượng: 10 lô");
        JLabel tongTienNhapTitle = new JLabel("Tiền nhập sản phẩm: ");
        tongTienNhapSP = new JLabel("999.999.999đ");
        tongTienNhapSP.setFont(new Font(null, Font.BOLD, 16));
        tongTienNhapSP.setForeground(Color.red);

        bottom.add(Box.createHorizontalStrut(10));
        bottom.add(soLoSP);
        bottom.add(Box.createHorizontalGlue());
        bottom.add(tongTienNhapTitle);
        bottom.add(tongTienNhapSP);
        bottom.add(Box.createHorizontalStrut(10));

        thongKeLoSp.add(top, BorderLayout.NORTH);
        thongKeLoSp.add(table, BorderLayout.CENTER);
        thongKeLoSp.add(bottom, BorderLayout.SOUTH);
        return thongKeLoSp;
    }

    private String chuyenDinhDangTienTe(Double gia) {
        if (gia == null) {
            return "0 đ";
        }

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.of("vi", "VN"));
        symbols.setGroupingSeparator('.');

        DecimalFormat decimalFormat = new DecimalFormat("#,###", symbols);

        return decimalFormat.format(gia) + " đ";
    }

    private JPanel thongKeNguyenLieu() {
        JPanel thongKeLoNL = new JPanel(new BorderLayout());

        JPanel top = TaoUI.taoPanelCanGiua(880, 40);
        JLabel jLabel = new JLabel("Danh sách nhập nguyên liệu");
        jLabel.setFont(new Font(null, Font.BOLD, 16));
        top.add(jLabel);
        top.setBackground(new Color(225, 235, 245));
        String[] columns = { "Mã Phiếu nhập", "Ngày nhập", "Nhân viên tạo phiếu", "Ghi chú", "Nhà cung cấp" };

        modelNL = new DefaultTableModel(columns, 0);
        JScrollPane table = TaoUI.taoTableScroll(modelNL);

        // --- Cập nhật Bottom ---
        JPanel bottom = TaoUI.taoPanelBoxLayoutNgang(880, 40);
        soLoNL = new JLabel("Số lượng: 10 lô");
        JLabel tongTienNhapTitle = new JLabel("Tiền nhập nguyên liệu: ");
        tongTienNhapNL = new JLabel("50.500.000đ");
        tongTienNhapNL.setFont(new Font(null, Font.BOLD, 16));
        tongTienNhapNL.setForeground(Color.red);

        bottom.add(javax.swing.Box.createHorizontalStrut(10));
        bottom.add(soLoNL);
        bottom.add(javax.swing.Box.createHorizontalGlue());
        bottom.add(tongTienNhapTitle);
        bottom.add(tongTienNhapNL);
        bottom.add(javax.swing.Box.createHorizontalStrut(10));

        thongKeLoNL.add(top, BorderLayout.NORTH);
        thongKeLoNL.add(table, BorderLayout.CENTER);
        thongKeLoNL.add(bottom, BorderLayout.SOUTH);

        return thongKeLoNL;
    }

    public JPanel tongChungPanel() {
        JPanel tongChungPanel = TaoUI.taoPanelBoxLayoutNgang(880, 40);
        JLabel tongTienNhapTitle = new JLabel("TỔNG TIỀN NHẬP CHUNG: ");
        tongTienNhapChung = new JLabel("1.050.499.999đ");
        tongTienNhapChung.setFont(new Font(null, Font.BOLD, 18));
        tongTienNhapChung.setForeground(Color.red);

        tongChungPanel.add(javax.swing.Box.createHorizontalGlue());
        tongChungPanel.add(tongTienNhapTitle);
        tongChungPanel.add(tongTienNhapChung);
        tongChungPanel.add(javax.swing.Box.createHorizontalStrut(10));

        return tongChungPanel;
    }
}
