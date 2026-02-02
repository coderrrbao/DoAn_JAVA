package ui.kiemke;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import bus.LoNguyenLieuBUS;
import bus.LoSanPhamBUS;
import bus.PhieuKiemKeBUS;
import dto.LoNguyenLieu;
import dto.LoSanPham;
import dto.PhieuKiemKe;
import ui.component.LocNgay_Item;
import util.TaoTinNhan;
import util.TaoUI;

public class ThemPhieuKiemDialog extends JDialog {
    private KiemKeUI kiemKeUI;
    private DefaultTableModel model;
    private JTable table;
    private LocNgay_Item locNgay_Item;
    private JComboBox<String> cbLoaiLo;
    private JTextField tfMaNv, tfSoLuong;
    private JButton btnThem, btnLamMoi;
    private JTextArea textArea;

    public ThemPhieuKiemDialog(KiemKeUI kiemKeUI) {
        super((JDialog) null, true);
        setSize(500, 540);
        setLocationRelativeTo(null);

        this.kiemKeUI = kiemKeUI;

        setLayout(new BorderLayout());
        initGUI();
        ganSuKien();
        loaiDuLieu();
    }

    private void loaiDuLieu() {
        model.setRowCount(0);
        LoSanPhamBUS loSanPhamBUS = new LoSanPhamBUS();
        LoNguyenLieuBUS loNguyenLieuBUS = new LoNguyenLieuBUS();

        String loai = cbLoaiLo.getSelectedItem().toString();
        if (loai.equals("Sản phẩm")) {
            ArrayList<LoSanPham> listLoSanPham = loSanPhamBUS.layListLoSanPham();
            for (LoSanPham loSanPham : listLoSanPham) {
                model.addRow(new Object[] { loSanPham.getMaLoSP(), "Sản phẩm", loSanPham.getMaSP(),
                        loSanPham.getSoLuong(), loSanPham.getNgayNhap() });
            }
        } else if (loai.equals("Nguyên liệu")) {
            ArrayList<LoNguyenLieu> listLoNguyenLieu = loNguyenLieuBUS.layListLoNguyenLieu();
            for (LoNguyenLieu loNguyenLieu : listLoNguyenLieu) {
                model.addRow(new Object[] { loNguyenLieu.getMaLoNL(), "Nguyên liệu", loNguyenLieu.getMaNL(),
                        loNguyenLieu.getSoLuong(), loNguyenLieu.getNgayNhap() });
            }
        }

    }

    private void ganSuKien() {
        cbLoaiLo.addActionListener(e -> {
            loaiDuLieu();
        });
        btnThem.addActionListener(e -> {

            PhieuKiemKeBUS phieuKiemKeBUS = new PhieuKiemKeBUS();
            PhieuKiemKe phieuKiemKe = dongGoPhieuKiemKe();
            if (phieuKiemKeBUS.themPhieuKiemKe(phieuKiemKe)) {
                TaoTinNhan.showAutoCloseMessage("Thêm phiếu kiểm kê thành công", "Thông báo", 2);
                dispose();
            } else {
                TaoTinNhan.showAutoCloseMessage("Thêm phiếu kiểm kê thất bại", "Thông báo", 2);
            }

            kiemKeUI.loaiDuLieu();
        });

    }

    private void initGUI() {
        add(taoTop(), BorderLayout.NORTH);
        add(taoTable(), BorderLayout.CENTER);
        add(taoBottom(), BorderLayout.SOUTH);
    }

    private JPanel taoTop() {
        JPanel top = TaoUI.taoPanelBoxLayoutNgang(450, 30);
        locNgay_Item = new LocNgay_Item(350, 28);
        top.add(locNgay_Item);

        String[] loaiLo = { "Sản phẩm", "Nguyên liệu" };
        cbLoaiLo = new JComboBox<>(loaiLo);
        cbLoaiLo.setSelectedIndex(0);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(cbLoaiLo);
        return top;
    }

    private JPanel taoBottom() {
        JPanel bottom = TaoUI.taoPanelCanGiua(450, 185);

        tfMaNv = new JTextField();
        tfMaNv.setEditable(false);
        tfSoLuong = new JTextField();

        JPanel input1 = TaoUI.taoFieldText("Mã Người kiểm", 100, 250, 30, 5, tfMaNv);
        JPanel soLuongPanel = TaoUI.taoFieldText("Số lượng", 100, 100, 30, 5, tfSoLuong);
        JPanel input2 = TaoUI.taoPanelBoxLayoutNgang(355, 30);
        textArea = new JTextArea();
        JPanel input3 = TaoUI.taoFieldArea("Ghi chú", 355, 30, 70, 5, textArea);

        tfMaNv.setText("NV01");

        input2.add(soLuongPanel);

        TaoUI.addItem(bottom, input1, 5, false);
        TaoUI.addItem(bottom, input2, 5, false);
        TaoUI.addItem(bottom, input3, 5, false);

        JPanel ctn = TaoUI.taoPanelBorderLayout(450, 185 + 40);
        ctn.add(bottom, BorderLayout.CENTER);

        JPanel button = TaoUI.taoPanelCanGiua(450, 40);

        btnThem = new JButton("Thêm");
        btnLamMoi = new JButton("Làm mới");
        TaoUI.addItem(button, btnThem, 5, true);
        TaoUI.addItem(button, btnLamMoi, 5, true);

        ctn.add(button, BorderLayout.SOUTH);

        return ctn;
    }

    private JScrollPane taoTable() {
        model = new DefaultTableModel();
        model.addColumn("Mã lô");
        model.addColumn("Loại lô");
        model.addColumn("Mã đối tượng");
        model.addColumn("Số lượng");
        model.addColumn("Ngày nhập");
        JScrollPane scrollPane = TaoUI.taoTableScroll(model);
        table = (JTable) scrollPane.getViewport().getView();
        return scrollPane;
    }

    private PhieuKiemKe dongGoPhieuKiemKe() {
        PhieuKiemKe phieuKiemKe = new PhieuKiemKe();
        int row = table.getSelectedRow();
        if (row > 0) {
            phieuKiemKe.setNgayKiem(LocalDate.now().toString());
            phieuKiemKe.setMaLo(model.getValueAt(row, 0).toString());
            phieuKiemKe.setLoaiLo(model.getValueAt(row, 1).toString());
            phieuKiemKe.setSoLuongSoSach(Integer.parseInt(model.getValueAt(row, 3).toString()));
            phieuKiemKe.setSoLuongThuc(Integer.parseInt(tfSoLuong.getText()));
            phieuKiemKe.setGhiChu(textArea.getText());
            phieuKiemKe.setMaNV(tfMaNv.getText());
            phieuKiemKe.setTrangThaiXuLy("Chưa xử lý");
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lô để kiểm kê", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return phieuKiemKe;
    }

    public static void main(String[] args) {
        JDialog dialog = new ThemPhieuKiemDialog(null);
        dialog.setVisible(true);
    }
}
