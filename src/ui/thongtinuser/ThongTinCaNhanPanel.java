package ui.thongtinuser;

import java.awt.Dimension;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import bus.NhanVienBUS;
import dto.NhanVien;
import ui.login.PhienDangNhap;
import util.Anh;
import util.TaoUI;

public class ThongTinCaNhanPanel extends JPanel {
    private JTextField tfMaNV, tfTenNV, tfGioiTinh, tfNgaySinh, tfSDT, tfDiaChi, tfChucVu;
    private JButton btnLuuThongTin, btnSua, btnChonAnh;
    private JLabel lblAnhDaiDien;
    private JFileChooser fileChooser = new JFileChooser();
    private boolean daDoiAnh = false;
    private NhanVien nhanVien = null;

    public ThongTinCaNhanPanel() {
        TaoUI.taoPanelBoxLayoutDoc(this, 400, 780);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        initGUI();

        setDuLieu(PhienDangNhap.getUser());
        tacChucNangSua();
        ganSuKien();
    }

    private void ganSuKien() {
        btnSua.addActionListener(e -> {
            batChucNangSua();
        });

        btnLuuThongTin.addActionListener(e -> {
            NhanVienBUS nhanVienBUS = NhanVienBUS.getNhanVienBUS();
            NhanVien nv = dongGoiNhanVien();
            if (nhanVienBUS.capNhatNhanVien(nv) == null) {
                setDuLieu(nv);
                tacChucNangSua();
            }
        });

        btnChonAnh.addActionListener(e -> {
            fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Hình ảnh", "jpg", "png", "jpeg"));
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File fileAnh = fileChooser.getSelectedFile();
                ImageIcon icon = new ImageIcon(fileAnh.getAbsolutePath());
                lblAnhDaiDien.setIcon(icon);
                daDoiAnh = true;
            }
        });

    }

    private NhanVien dongGoiNhanVien() {
        if (daDoiAnh) {
            String duongDan = Anh.luuAnhNV(nhanVien.getMaNV(), fileChooser);
            nhanVien.setAnh(duongDan);
            daDoiAnh = false;
        }
        NhanVien nv = new NhanVien();
        nv.setMaNV(tfMaNV.getText());
        nv.setTenNV(tfTenNV.getText());
        nv.setGioiTinh(tfGioiTinh.getText());
        nv.setNgaySinh(tfNgaySinh.getText());
        nv.setSdt(tfSDT.getText());
        nv.setDiaChi(tfDiaChi.getText());

        nv.setAnh(nhanVien.getAnh());
        return nv;
    }

    private void initGUI() {
        lblAnhDaiDien = TaoUI.taoJlabelAnh("../assets/img/goku.png", 180, 180);
        lblAnhDaiDien.setAlignmentX(CENTER_ALIGNMENT);
        add(lblAnhDaiDien);
        add(Box.createRigidArea(new Dimension(0, 10)));
        JPanel buttonCAPanel = TaoUI.taoPanelCanGiua(400, 30);
        btnChonAnh = new JButton("Chọn ảnh");
        TaoUI.addItem(buttonCAPanel, btnChonAnh, 0, true);
        add(buttonCAPanel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        tfMaNV = new JTextField();
        tfTenNV = new JTextField();
        tfGioiTinh = new JTextField();
        tfNgaySinh = new JTextField();
        tfSDT = new JTextField();
        tfChucVu = new JTextField();
        tfDiaChi = new JTextField();

        add(taoPanelItemThongTin(400, 35, "Mã NV", "Tên NV", 60, 90, 70, 120, tfMaNV, tfTenNV));
        add(Box.createRigidArea(new Dimension(0, 10)));

        add(taoPanelItemThongTin(400, 35, "Giới tính", "Ngày sinh", 60, 90, 70, 120, tfGioiTinh, tfNgaySinh));
        add(Box.createRigidArea(new Dimension(0, 10)));

        add(taoPanelItemThongTin(400, 35, "SĐT", "Chức vụ", 60, 90, 70, 120, tfSDT, tfChucVu));
        add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel pnlDiaChi = TaoUI.taoFieldText("Địa chỉ", 60, 297, 35, 3, tfDiaChi);
        JPanel thongTinDC = TaoUI.taoPanelBoxLayoutNgang(400, 35);
        thongTinDC.add(pnlDiaChi);
        add(thongTinDC);

        add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel buttonSuaPanel = TaoUI.taoPanelCanGiua(400, 30);
        btnSua = new JButton("Sửa");
        btnLuuThongTin = new JButton("Lưu thông tin");
        btnLuuThongTin.setEnabled(false);
        TaoUI.addItem(buttonSuaPanel, btnSua, 10, true);
        TaoUI.addItem(buttonSuaPanel, btnLuuThongTin, 10, true);
        add(buttonSuaPanel);

    }

    private JPanel taoPanelItemThongTin(int width, int height, String titleInput1, String titleInput2, int wLb1,
            int wInput1, int wLb2, int wInput2, JTextField textField1, JTextField textField2) {

        JPanel thongTin = TaoUI.taoPanelBoxLayoutNgang(width, height);

        JPanel input1JPanel = TaoUI.taoFieldText(titleInput1, wLb1, wInput1, height, 3, textField1);
        JPanel input2JPanel = TaoUI.taoFieldText(titleInput2, wLb2, wInput2, height, 3, textField2);

        thongTin.add(input1JPanel);
        thongTin.add(Box.createHorizontalGlue());
        thongTin.add(input2JPanel);

        return thongTin;
    }

    private JPanel taoPanelItemThongTin(int width, int height, String titleInput1, String titleInput2, int wLb1,
            int wInput1, int wLb2, int wInput2, JTextField textField1, JComboBox<String> cb) {

        JPanel thongTin = TaoUI.taoPanelBoxLayoutNgang(width, height);

        JPanel input1JPanel = TaoUI.taoFieldText(titleInput1, wLb1, wInput1, height, 3, textField1);

        thongTin.add(input1JPanel);        thongTin.add(Box.createHorizontalGlue());
        thongTin.add(cb);

        return thongTin;
    }

    private void tacChucNangSua() {
        tfChucVu.setEditable(false);
        tfDiaChi.setEditable(false);
        tfGioiTinh.setEditable(false);
        tfMaNV.setEditable(false);
        tfNgaySinh.setEditable(false);
        tfSDT.setEditable(false);
        tfSDT.setEditable(false);
        btnChonAnh.setEnabled(false);
        tfTenNV.setEditable(false);

        btnSua.setEnabled(true);
        btnLuuThongTin.setEnabled(false);
    }

    private void batChucNangSua() {
        tfChucVu.setEditable(false);
        tfDiaChi.setEditable(true);
        tfGioiTinh.setEditable(true);
        tfNgaySinh.setEditable(true);
        tfSDT.setEditable(true);
        tfTenNV.setEditable(true);
        btnChonAnh.setEnabled(true);
        tfMaNV.setEditable(false);

        btnSua.setEnabled(false);
        btnLuuThongTin.setEnabled(true);
    }

    public void setDuLieu(NhanVien nv) {
        if (nv == null) {
            return;
        }
        nhanVien = nv;
        tfMaNV.setText(nv.getMaNV());
        tfTenNV.setText(nv.getTenNV());
        tfGioiTinh.setText(nv.getGioiTinh());
        tfNgaySinh.setText(nv.getNgaySinh() != null ? nv.getNgaySinh().toString() : "");
        tfSDT.setText(nv.getSdt());
        tfDiaChi.setText(nv.getDiaChi());
        tfChucVu.setText(PhienDangNhap.getTaiKhoan().getNhomQuyen().getTenNhomQuyen());

        ImageIcon icon = TaoUI.taoImageIcon(nhanVien.getAnh(), 180, 180);
        lblAnhDaiDien.setIcon(icon);
    }
}