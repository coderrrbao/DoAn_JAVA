package ui.tonkho;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import bus.NguyenLieuBUS;
import dto.NguyenLieu;

import java.awt.*;

public class SuaCanhBaoDialogNL extends JDialog {

    private JTextField txtMaNL;
    private JTextField txtTenNL;
    private JTextField txtMucCanhBao;
    private JButton btnSua;
    private JButton btnLuu;

    private NguyenLieu nguyenLieu;
    private TonKhoNguyenLieuPanel tonKhoNguyenLieuPanel;

    public SuaCanhBaoDialogNL(TonKhoNguyenLieuPanel tonKhoNguyenLieuPanel, NguyenLieu nl) {
        super((JFrame) null, "Sửa Mức Cảnh Báo Nguyên Liệu", true);
        this.nguyenLieu = nl;
        this.tonKhoNguyenLieuPanel = tonKhoNguyenLieuPanel;
        initComponents();
        loadData();
        setupEvents();

        setSize(400, 250);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel panelForm = new JPanel(new GridLayout(3, 2, 10, 15));
        panelForm.setBorder(new EmptyBorder(20, 20, 20, 20));

        panelForm.add(new JLabel("Mã nguyên liệu:"));
        txtMaNL = new JTextField();
        txtMaNL.setEnabled(false);
        panelForm.add(txtMaNL);

        panelForm.add(new JLabel("Tên nguyên liệu:"));
        txtTenNL = new JTextField();
        txtTenNL.setEnabled(false);
        panelForm.add(txtTenNL);

        panelForm.add(new JLabel("Mức cảnh báo (số lượng):"));
        txtMucCanhBao = new JTextField();
        txtMucCanhBao.setEnabled(false);
        panelForm.add(txtMucCanhBao);

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnSua = new JButton("Sửa");
        btnLuu = new JButton("Lưu");
        btnLuu.setEnabled(false);

        panelButtons.add(btnSua);
        panelButtons.add(btnLuu);

        setLayout(new BorderLayout());
        add(panelForm, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);
    }

    private void loadData() {
        if (nguyenLieu != null) {
            txtMaNL.setText(nguyenLieu.getMaNL());
            txtTenNL.setText(nguyenLieu.getTenNL());
            txtMucCanhBao.setText(String.valueOf(nguyenLieu.getMucCanhBao()));
        }
    }

    private void setupEvents() {
        btnSua.addActionListener(e -> {
            txtMucCanhBao.setEnabled(true);
            txtMucCanhBao.requestFocus();
            btnSua.setEnabled(false);
            btnLuu.setEnabled(true);
        });

        btnLuu.addActionListener(e -> {
            try {
                int mucCanhBaoMoi = Integer.parseInt(txtMucCanhBao.getText().trim());

                if (mucCanhBaoMoi < 0) {
                    JOptionPane.showMessageDialog(this, "Mức cảnh báo không được là số âm!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                nguyenLieu.setMucCanhBao(mucCanhBaoMoi);
                boolean thanhCong = NguyenLieuBUS.getNguyenLieuBUS().suaCanhBao(nguyenLieu);
                if (thanhCong) {
                    JOptionPane.showMessageDialog(null, "Cập nhật thành công!", "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                    tonKhoNguyenLieuPanel.loadDuLieu();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số nguyên hợp lệ!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                txtMucCanhBao.requestFocus();
            }
        });
    }
}