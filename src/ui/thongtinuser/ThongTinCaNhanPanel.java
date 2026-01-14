package ui.thongtinuser;

import java.awt.Dimension;
import javax.swing.*;
import dto.NhanVien;
import util.TaoUI;

public class ThongTinCaNhanPanel extends JPanel {
    private JTextField tfMaNV, tfTenNV, tfGioiTinh, tfNgaySinh, tfSDT, tfDiaChi, tfChucVu;
    private JTextField tfTenTaiKhoan, tfPhanQuyen, tfTrangThai;
    private JButton btnLuuThongTin, btnSua, btnChonAnh;
    private JLabel lblAnhDaiDien;

    public ThongTinCaNhanPanel() {
        TaoUI.taoPanelBoxLayoutDoc(this, 400, 780);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initGUI();
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
        TaoUI.addItem(buttonSuaPanel, btnSua, 10, true);TaoUI.addItem(buttonSuaPanel, btnLuuThongTin, 10, true);
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

    public void setDuLieu(NhanVien nv) {
        tfMaNV.setText(nv.getMaNV());
        tfTenNV.setText(nv.getTenNV());
        tfGioiTinh.setText(nv.getGioiTinh());
        tfNgaySinh.setText(nv.getNgaySinh() != null ? nv.getNgaySinh().toString() : "");
        tfSDT.setText(nv.getSdt());
        tfDiaChi.setText(nv.getDiaChi());
        tfChucVu.setText(nv.getChucVu());

        if (nv.getTaiKhoan() != null) {
            tfTenTaiKhoan.setText(nv.getTaiKhoan().getTenTaiKhoan());
            tfPhanQuyen.setText(nv.getTaiKhoan().getPhanQuyen());
        }
    }
}