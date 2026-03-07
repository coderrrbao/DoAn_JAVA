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
        super((JFrame) null, "Sửa Mức Cảnh Báo Tồn Kho", true); // true = Modal dialog
        this.sanPham = sp;
        this.tonKhoSanPhamPanel = tonKhoSanPhamPanel;
        initComponents();
        loadData();
        setupEvents();

        setSize(400, 250);
        setLocationRelativeTo(null); // Hiển thị ở giữa màn hình
    }

    private void initComponents() {
        // --- Phần Form Nhập Liệu ---
        JPanel panelForm = new JPanel(new GridLayout(3, 2, 10, 15));
        panelForm.setBorder(new EmptyBorder(20, 20, 20, 20));

        panelForm.add(new JLabel("Mã sản phẩm:"));
        txtMaSP = new JTextField();
        txtMaSP.setEnabled(false); // Không cho phép sửa
        panelForm.add(txtMaSP);

        panelForm.add(new JLabel("Tên sản phẩm:"));
        txtTenSP = new JTextField();
        txtTenSP.setEnabled(false); // Không cho phép sửa
        panelForm.add(txtTenSP);

        panelForm.add(new JLabel("Mức cảnh báo (số lượng):"));
        txtMucCanhBao = new JTextField();
        txtMucCanhBao.setEnabled(false); // Khóa lúc đầu, chờ nhấn nút Sửa
        panelForm.add(txtMucCanhBao);

        // --- Phần Nút Bấm ---
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnSua = new JButton("Sửa");
        btnLuu = new JButton("Lưu");
        btnLuu.setEnabled(false);

        panelButtons.add(btnSua);
        panelButtons.add(btnLuu);

        // --- Thêm vào Dialog ---
        setLayout(new BorderLayout());
        add(panelForm, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);
    }

    private void loadData() {
        if (sanPham != null) {
            txtMaSP.setText(sanPham.getMaSP());
            txtTenSP.setText(sanPham.getTenSP());
            // Ép kiểu số sang chuỗi để hiển thị
            txtMucCanhBao.setText(String.valueOf(sanPham.getMucCanhBao()));
        }
    }

    private void setupEvents() {
        // Sự kiện khi nhấn nút Sửa
        btnSua.addActionListener(e -> {
            txtMucCanhBao.setEnabled(true); // Mở khóa ô text
            txtMucCanhBao.requestFocus(); // Đưa con trỏ chuột vào ô này

            btnSua.setEnabled(false); // Khóa nút Sửa
            btnLuu.setEnabled(true); // Mở khóa nút Lưu
        });

        // Sự kiện khi nhấn nút Lưu
        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Lấy dữ liệu và ép kiểu
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
                    dispose(); // Đóng Dialog sau khi lưu thành công

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SuaCanhBaoDialogSP.this,
                            "Vui lòng nhập số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    txtMucCanhBao.requestFocus();
                }
            }
        });
    }
}