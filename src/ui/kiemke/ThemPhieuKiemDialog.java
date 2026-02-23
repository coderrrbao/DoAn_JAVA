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
    private JButton btnThem, btnLamMoi, btnSua, btnLuu;
    private JTextArea textArea;
    private JComboBox<String> cbXacNhan;

    private PhieuKiemKe phieuKiemKe = null;

    private int dongDangChon;

    public ThemPhieuKiemDialog(KiemKeUI kiemKeUI, PhieuKiemKe pkk) {
        super((JDialog) null, true);
        setSize(500, 540);
        setLocationRelativeTo(null);
        phieuKiemKe = pkk;
        this.kiemKeUI = kiemKeUI;

        setLayout(new BorderLayout());
        initGUI();
        ganSuKien();
        loaiDuLieu();

        if (phieuKiemKe != null) {

            cbLoaiLo.setSelectedItem(phieuKiemKe.getLoaiLo());

            dongDangChon = layIndexLo(phieuKiemKe.getMaLo());
            table.setRowSelectionInterval(dongDangChon, dongDangChon);
            table.scrollRectToVisible(table.getCellRect(dongDangChon, 0, true));

            textArea.setText(phieuKiemKe.getGhiChu());
            tfMaNv.setText(phieuKiemKe.getMaNV());
            tfSoLuong.setText(String.valueOf(phieuKiemKe.getSoLuongThuc()));

            cbXacNhan.setSelectedItem(phieuKiemKe.getTrangThaiXuLy());

            btnLamMoi.setVisible(false);
            btnThem.setVisible(false);
            btnLuu.setEnabled(false);
            textArea.setEditable(false);
            tfSoLuong.setEditable(false);
            tfMaNv.setEditable(false);
            cbXacNhan.setEnabled(false);
            cbLoaiLo.setEnabled(false);

            table.setEnabled(false);

            table.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    int dong = table.getSelectedRow();
                    if (dong == dongDangChon) {
                        return;
                    }
                    if (dong != -1) {
                        int luaChon = JOptionPane.showConfirmDialog(null,
                                "Bạn có muốn đổi sang lô " + model.getValueAt(dong, 0) + " không", "Xác nhận",
                                JOptionPane.YES_NO_OPTION);
                        if (luaChon == JOptionPane.YES_OPTION) {
                            table.setRowSelectionInterval(dong, dong);
                            dongDangChon = dong;
                        } else {
                            table.setRowSelectionInterval(dongDangChon, dongDangChon);
                        }
                    }
                }
            });

        } else {
            btnSua.setVisible(false);
            btnLuu.setVisible(false);
            cbXacNhan.setVisible(false);
        }
    }

    private void loaiDuLieu() {
        model.setRowCount(0);
        LoSanPhamBUS loSanPhamBUS = new LoSanPhamBUS();
        LoNguyenLieuBUS loNguyenLieuBUS = new LoNguyenLieuBUS();

        String loai = cbLoaiLo.getSelectedItem().toString();
        if (loai.equals("Sản phẩm")) {
            ArrayList<LoSanPham> listLoSanPham = loSanPhamBUS.layListLoSanPham();
            for (LoSanPham loSanPham : listLoSanPham) {
                if (locNgay_Item.ngayTrongKhoan(loSanPham.getNgayNhap())) {
                    model.addRow(new Object[] { loSanPham.getMaLoSP(), "Sản phẩm", loSanPham.getMaSP(),
                            loSanPham.getSoLuong(), loSanPham.getNgayNhap() });
                }

            }
        } else if (loai.equals("Nguyên liệu")) {
            ArrayList<LoNguyenLieu> listLoNguyenLieu = loNguyenLieuBUS.layListLoNguyenLieu();
            for (LoNguyenLieu loNguyenLieu : listLoNguyenLieu) {
                if (locNgay_Item.ngayTrongKhoan(loNguyenLieu.getNgayNhap())) {
                    model.addRow(new Object[] { loNguyenLieu.getMaLoNL(), "Nguyên liệu", loNguyenLieu.getMaNL(),
                            loNguyenLieu.getSoLuong(), loNguyenLieu.getNgayNhap() });
                }

            }
        }

    }

    private int layIndexLo(String maLo) {
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).toString().equals(maLo)) {
                return i;
            }
        }
        return -1;
    }

    private void ganSuKien() {
        cbLoaiLo.addActionListener(e -> {
            loaiDuLieu();
        });
        btnThem.addActionListener(e -> {

            PhieuKiemKeBUS phieuKiemKeBUS = PhieuKiemKeBUS.getPhieuKiemKeBUS();
            PhieuKiemKe phieuKiemKe = dongGoiPhieuKiemKe();
            if (phieuKiemKeBUS.themPhieuKiemKe(phieuKiemKe)) {
                TaoTinNhan.showAutoCloseMessage("Thêm phiếu kiểm kê thành công", "Thông báo", 2);
                kiemKeUI.loaiDuLieu();
                dispose();
            } else {
                TaoTinNhan.showAutoCloseMessage("Thêm phiếu kiểm kê thất bại", "Thông báo", 2);
            }

            kiemKeUI.loaiDuLieu();
        });

        btnSua.addActionListener(e -> {
            btnSua.setEnabled(false);
            btnLuu.setEnabled(true);
            textArea.setEditable(true);
            tfSoLuong.setEditable(true);
            tfMaNv.setEditable(true);
            cbXacNhan.setEnabled(true);
            cbLoaiLo.setEnabled(true);
            table.setEnabled(true);
        });

        btnLuu.addActionListener(e -> {
            PhieuKiemKeBUS phieuKiemKeBUS = PhieuKiemKeBUS.getPhieuKiemKeBUS();
            PhieuKiemKe phieuKiemKe = dongGoiPhieuKiemKe();
            if (phieuKiemKeBUS.capNhapPhieuKiemKe(phieuKiemKe)) {
                TaoTinNhan.showAutoCloseMessage("Cập nhập phiếu kiểm kê thành công", "Thông báo", 1);
                kiemKeUI.loaiDuLieu();
                dispose();
            } else {
                TaoTinNhan.showAutoCloseMessage("Cập nhập phiếu kiểm kê thất bại", "Thông báo", 1);
            }
        });


        locNgay_Item.setEvent(() -> {
            loaiDuLieu();
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
        String[] luaChoncb = { "Đã xác nhận", "Chưa xử lý" };
        cbXacNhan = new JComboBox<>(luaChoncb);
        input2.add(Box.createRigidArea(new Dimension(5, 0)));
        input2.add(cbXacNhan);

        TaoUI.addItem(bottom, input1, 5, false);
        TaoUI.addItem(bottom, input2, 5, false);
        TaoUI.addItem(bottom, input3, 5, false);

        JPanel ctn = TaoUI.taoPanelBorderLayout(450, 185 + 40);
        ctn.add(bottom, BorderLayout.CENTER);

        JPanel button = TaoUI.taoPanelCanGiua(450, 40);

        btnThem = new JButton("Thêm");
        btnLamMoi = new JButton("Làm mới");
        btnLuu = new JButton("Lưu");
        btnSua = new JButton("Sửa");

        TaoUI.addItem(button, btnThem, 5, true);
        TaoUI.addItem(button, btnLamMoi, 5, true);
        TaoUI.addItem(button, btnSua, 5, true);
        TaoUI.addItem(button, btnLuu, 5, true);

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

    private PhieuKiemKe dongGoiPhieuKiemKe() {
        PhieuKiemKe pkk = new PhieuKiemKe();
        int row = table.getSelectedRow();
        if (row >= 0) {
            if (phieuKiemKe != null) {
                pkk.setMaKK(phieuKiemKe.getMaKK());
            }
            pkk.setNgayKiem(LocalDate.now().toString());
            pkk.setMaLo(model.getValueAt(row, 0).toString());
            pkk.setLoaiLo(model.getValueAt(row, 1).toString());
            pkk.setSoLuongSoSach(Double.parseDouble(model.getValueAt(row, 3).toString()));
            pkk.setSoLuongThuc(Double.parseDouble(tfSoLuong.getText()));
            pkk.setGhiChu(textArea.getText());
            pkk.setMaNV(tfMaNv.getText());
            if (phieuKiemKe == null) {
                pkk.setTrangThaiXuLy("Chưa xử lý");
            } else {
                pkk.setTrangThaiXuLy(cbXacNhan.getSelectedItem().toString());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lô để kiểm kê", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return pkk;
    }

    public static void main(String[] args) {
        PhieuKiemKe phieuKiemKe = new PhieuKiemKe();
        phieuKiemKe.setGhiChu("Hihdqidhqihdhq");
        phieuKiemKe.setMaNV("NV000001");
        phieuKiemKe.setLoaiLo("Nguyên liệu");
        phieuKiemKe.setMaLo("LONL03");
        ThemPhieuKiemDialog themPhieuKiemDialog = new ThemPhieuKiemDialog(null, phieuKiemKe);
        themPhieuKiemDialog.setVisible(true);
    }

}
