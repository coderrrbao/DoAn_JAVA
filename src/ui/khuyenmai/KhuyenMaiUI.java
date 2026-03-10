package ui.khuyenmai;

import bus.KhuyenMaiBUS;
import dto.KhuyenMai;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import ui.component.LocNgay_Item;
import ui.login.LoginUI;
import ui.login.PhienDangNhap;
import util.TaoUI;

public class KhuyenMaiUI extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private LocNgay_Item locNgay;
    private JButton btnThem, btnSua, btnXoa, btnNhapExc, btnXuatExc;

    private KhuyenMaiBUS kmBUS = KhuyenMaiBUS.getKhuyenMaiBUS();

    public KhuyenMaiUI() {
        setLayout(new BorderLayout());

        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.add(taoTopPanel(), BorderLayout.NORTH);
        centerContainer.add(taoPanelTable(), BorderLayout.CENTER);

        add(centerContainer, BorderLayout.CENTER);

        loadDataToTable();
        addEvents();
    }

    public void suaLaiGiaoDienTheoQuyen() {
        var listQuyen = PhienDangNhap.getListQuyen();

        if (!listQuyen.contains("KM_TAO")) {
            btnThem.setVisible(false);
        }

        if (!listQuyen.contains("KM_SUA")) {
            btnSua.setVisible(false);
        }

        if (!listQuyen.contains("KM_XOA")) {
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

        locNgay = new LocNgay_Item(400, 32);
        top.add(locNgay);

        btnThem = new JButton("Thêm");
        btnThem.setPreferredSize(new Dimension(80, 32));
        top.add(btnThem);

        btnSua = new JButton("Sửa");
        btnSua.setPreferredSize(new Dimension(80, 32));
        top.add(btnSua);

        btnXoa = new JButton("Xóa");
        btnXoa.setPreferredSize(new Dimension(80, 32));
        top.add(btnXoa);

        btnNhapExc = new JButton("Nhập Excel");
        btnNhapExc.setPreferredSize(new Dimension(100, 32));
        top.add(btnNhapExc);

        btnXuatExc = new JButton("Xuất Excel");
        btnXuatExc.setPreferredSize(new Dimension(100, 32));
        top.add(btnXuatExc);

        return top;
    }

    private JPanel taoPanelTable() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columns = { "Mã KM", "Phần trăm giảm", "Từ ngày", "Đến ngày", "Trạng thái" };
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JScrollPane scrollPane = TaoUI.taoTableScroll(model);
        table = (JTable) scrollPane.getViewport().getView();
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    public void loadDataToTable() {
        model.setRowCount(0);
        ArrayList<KhuyenMai> list = kmBUS.layListKhuyenMai();

        for (KhuyenMai km : list) {

            if (locNgay.ngayTrongKhoan(km.getTuNgay())) {
                model.addRow(new Object[] {
                        km.getMaKM(),
                        km.getPhanTramGiam() + "%",
                        km.getTuNgay(),
                        km.getDenNgay(),
                        kmBUS.xacDinhTrangThai(km)
                });
            }
        }
    }

    private void addEvents() {
        btnThem.addActionListener(e -> {
            FormKhuyenMai form = new FormKhuyenMai((Frame) SwingUtilities.getWindowAncestor(this), null);
            form.setVisible(true);
            if (form.getKetQua() != null) {
                if (kmBUS.themKhuyenMai(form.getKetQua())) {
                    JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thành công!");
                    LoginUI.getLoginUI().getMainFrame().loadAllData();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm thất bại!");
                }
            }
        });

        btnXoa.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!");
                return;
            }
            String maKM = model.getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận xóa mã " + maKM + "?", "Xác nhận",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (kmBUS.xoaKhuyenMai(maKM)) {
                    JOptionPane.showMessageDialog(this, "Đã xóa thành công!");
                    LoginUI.getLoginUI().getMainFrame().loadAllData();
                }
            }
        });

        btnSua.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần sửa!");
                return;
            }
            String maKM = model.getValueAt(row, 0).toString();
            KhuyenMai kmCanSua = kmBUS.timKhuyenMai(maKM);

            FormKhuyenMai form = new FormKhuyenMai((Frame) SwingUtilities.getWindowAncestor(this), kmCanSua);
            form.setVisible(true);
            if (form.getKetQua() != null) {
                if (kmBUS.capNhatKhuyenMai(form.getKetQua())) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                    LoginUI.getLoginUI().getMainFrame().loadAllData();
                }
            }
        });

        btnXuatExc.addActionListener(e -> {

            if (table.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Bảng dữ liệu trống, không có gì để xuất!");
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file Khuyến mãi");
            fileChooser.setSelectedFile(new File("DanhSachKhuyenMai.xlsx"));

            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                if (!file.getAbsolutePath().toLowerCase().endsWith(".xlsx")) {
                    file = new File(file.getAbsolutePath() + ".xlsx");
                }

                if (kmBUS.xuatExcel(file)) {
                    JOptionPane.showMessageDialog(this, "Xuất file Excel thành công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi: Không thể xuất file Excel!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        locNgay.setEvent(() -> {
            loadDataToTable();
        });
    }
}