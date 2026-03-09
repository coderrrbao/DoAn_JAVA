package ui.tonkho;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import bus.SanPhamBUS;
import dto.SanPham;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SuaCanhBaoDialogSP extends JDialog {

    private JTextField txtMaSP;
    private JTextField txtTenSP;
    private JTextField txtMucCanhBao;
    private JButton btnSua;
    private JButton btnLuu;

    private SanPham sanPham;
    private TonKhoSanPhamPanel tonKhoSanPhamPanel;

    public SuaCanhBaoDialogSP(TonKhoSanPhamPanel tonKhoSanPhamPanel, SanPham sp) {
        super((JFrame) null, "Sửa Mức Cảnh Báo Tồn Kho", true);
        this.sanPham = sp;
        this.tonKhoSanPhamPanel = tonKhoSanPhamPanel;
        initComponents();
        loadData();
        setupEvents();

        setSize(400, 250);
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        JPanel panelForm = new JPanel(new GridLayout(3, 2, 10, 15));
        panelForm.setBorder(new EmptyBorder(20, 20, 20, 20));

        panelForm.add(new JLabel("Mã sản phẩm:"));
        txtMaSP = new JTextField();
        txtMaSP.setEnabled(false);
        panelForm.add(txtMaSP);

        panelForm.add(new JLabel("Tên sản phẩm:"));
        txtTenSP = new JTextField();
        txtTenSP.setEnabled(false);
        panelForm.add(txtTenSP);

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
        if (sanPham != null) {
            txtMaSP.setText(sanPham.getMaSP());
            txtTenSP.setText(sanPham.getTenSP());

            txtMucCanhBao.setText(String.valueOf(sanPham.getMucCanhBao()));
        }
    }

    private void setupEvents() {

        btnSua.addActionListener(e -> {
            txtMucCanhBao.setEnabled(true);
            txtMucCanhBao.requestFocus();

            btnSua.setEnabled(false);
            btnLuu.setEnabled(true);
        });

        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    int mucCanhBaoMoi = Integer.parseInt(txtMucCanhBao.getText().trim());

                    sanPham.setMucCanhBao(mucCanhBaoMoi);

                    boolean thanhCong = SanPhamBUS.getSanPhamBUS().suaCanhBao(sanPham);
                    if (thanhCong) {
                        JOptionPane.showMessageDialog(null, "Cập nhật thành công!", "Thông báo",
                                JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                    tonKhoSanPhamPanel.loadDuLieu();
                    dispose();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SuaCanhBaoDialogSP.this,
                            "Vui lòng nhập số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    txtMucCanhBao.requestFocus();
                }
            }
        });
    }
}