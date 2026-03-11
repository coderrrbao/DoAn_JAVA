package ui.khuyenmai;

import dto.KhuyenMai;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import com.toedter.calendar.JDateChooser;

public class FormKhuyenMai extends JDialog {
    private JTextField txtMa, txtPhanTram;
    private JDateChooser dateTuNgay, dateDenNgay;
    private JButton btnThem, btnSua, btnLuu, btnHuy;

    private KhuyenMai ketQua = null;
    private boolean isEdit = false;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public FormKhuyenMai(Frame owner, KhuyenMai editKM) {
        super(owner, editKM == null ? "Thêm Khuyến Mãi" : "Chi tiết Khuyến Mãi", true);
        this.isEdit = (editKM != null);

        setSize(new Dimension(500, 300));
        initUI();

        if (isEdit) {
            duLieuCu(editKM);
        }

        initLoaiDialog();
        ganSuKien(editKM);

        setLocationRelativeTo(owner);
        suaLaiGiaoDienTheoQuyen();
    }

    public void suaLaiGiaoDienTheoQuyen() {
        var listQuyen = ui.login.PhienDangNhap.getListQuyen();

        if (!isEdit) {
            if (!listQuyen.contains("KM_TAO")) {
                btnThem.setEnabled(false);
                setEditableForm(false);
                setTitle("Thông báo: Bạn không có quyền thêm khuyến mãi");
            }
        }

        else {
            if (!listQuyen.contains("KM_SUA")) {
                btnSua.setVisible(false);
                btnLuu.setVisible(false);
                setTitle("Chi tiết khuyến mãi (Chỉ xem)");

                anThaoTacSua();
            }
        }
    }

    private void initUI() {
        setLayout(new BorderLayout());
        JPanel pnlMain = new JPanel();
        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
        pnlMain.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        txtMa = new JTextField("Tự động");
        txtMa.setEditable(false);
        txtPhanTram = new JTextField();

        dateTuNgay = new JDateChooser();
        dateTuNgay.setDateFormatString("yyyy-MM-dd");

        dateDenNgay = new JDateChooser();
        dateDenNgay.setDateFormatString("yyyy-MM-dd");

        String[] labels = { "Mã khuyến mãi:", "Phần trăm giảm (%):", "Từ ngày:", "Đến ngày:" };
        Component[] fields = { txtMa, txtPhanTram, dateTuNgay, dateDenNgay };

        for (int i = 0; i < labels.length; i++) {
            Box row = Box.createHorizontalBox();

            JLabel lbl = new JLabel(labels[i]);
            lbl.setPreferredSize(new Dimension(150, 30));
            lbl.setMaximumSize(new Dimension(150, 30));

            row.add(lbl);
            row.add(Box.createHorizontalStrut(10));

            fields[i].setPreferredSize(new Dimension(250, 30));
            fields[i].setMaximumSize(new Dimension(250, 30));

            row.add(fields[i]);

            pnlMain.add(row);
            pnlMain.add(Box.createVerticalStrut(15));
        }

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Đóng");

        pnlButtons.add(btnThem);
        pnlButtons.add(btnSua);
        pnlButtons.add(btnLuu);
        pnlButtons.add(btnHuy);

        add(pnlMain, BorderLayout.CENTER);
        add(pnlButtons, BorderLayout.SOUTH);
    }

    private void initLoaiDialog() {
        if (isEdit) {
            btnHuy.setVisible(false);
            btnThem.setVisible(false);
            anThaoTacSua();
        } else {
            btnSua.setVisible(false);
            btnLuu.setVisible(false);
            btnThem.setVisible(true);
            setEditableForm(true);
        }
    }

    private void setEditableForm(boolean status) {
        txtPhanTram.setEditable(status);
        dateTuNgay.setEnabled(status);
        dateDenNgay.setEnabled(status);
    }

    private void anThaoTacSua() {
        btnSua.setEnabled(true);
        btnLuu.setEnabled(false);
        setEditableForm(false);
    }

    private void batThaoTacSua() {
        btnSua.setEnabled(false);
        btnLuu.setEnabled(true);
        setEditableForm(true);
    }

    private void ganSuKien(KhuyenMai editKM) {
        btnHuy.addActionListener(e -> dispose());
        btnSua.addActionListener(e -> batThaoTacSua());

        btnThem.addActionListener(e -> {
            if (kiemTraDuLieu()) {
                ketQua = new KhuyenMai();
                ganDuLieu(ketQua);
                dispose();
            }
        });

        btnLuu.addActionListener(e -> {
            if (kiemTraDuLieu()) {
                ketQua = editKM;
                ganDuLieu(ketQua);
                dispose();
            }
        });
    }

    private void duLieuCu(KhuyenMai km) {
        txtMa.setText(km.getMaKM());
        txtPhanTram.setText(String.valueOf(km.getPhanTramGiam()));

        try {

            if (km.getTuNgay() != null && !km.getTuNgay().isEmpty()) {
                dateTuNgay.setDate(sdf.parse(km.getTuNgay()));
            }
            if (km.getDenNgay() != null && !km.getDenNgay().isEmpty()) {
                dateDenNgay.setDate(sdf.parse(km.getDenNgay()));
            }
        } catch (Exception e) {

            dateTuNgay.setDate(new Date());
            dateDenNgay.setDate(new Date());
        }
    }

    private boolean kiemTraDuLieu() {

        if (txtPhanTram.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập phần trăm giảm!");
            return false;
        }

        if (dateTuNgay.getDate() == null || dateDenNgay.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn Từ ngày và Đến ngày!");
            return false;
        }

        try {
            int pt = Integer.parseInt(txtPhanTram.getText().trim());
            if (pt <= 0 || pt > 100) {
                JOptionPane.showMessageDialog(this, "Phần trăm giảm phải từ 1 đến 100");
                return false;
            }

            Date dTuNgay = dateTuNgay.getDate();
            Date dDenNgay = dateDenNgay.getDate();

            if (dDenNgay.before(dTuNgay)) {
                JOptionPane.showMessageDialog(this, "Đến ngày không được nhỏ hơn Từ ngày!");
                return false;
            }
            return true;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Phần trăm giảm phải là số hợp lệ!");
            return false;
        }
    }

    private void ganDuLieu(KhuyenMai km) {
        km.setPhanTramGiam(Integer.parseInt(txtPhanTram.getText().trim()));

        km.setTuNgay(sdf.format(dateTuNgay.getDate()));
        km.setDenNgay(sdf.format(dateDenNgay.getDate()));
    }

    public KhuyenMai getKetQua() {
        return ketQua;
    }
}