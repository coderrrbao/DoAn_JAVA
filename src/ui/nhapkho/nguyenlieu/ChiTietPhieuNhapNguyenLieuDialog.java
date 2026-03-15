package ui.nhapkho.nguyenlieu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import bus.PhieuNhapNguyenLieuBUS;
import bus.NguyenLieuBUS;
import dto.LoNguyenLieu;
import dto.PhieuNhapNguyenLieu;
import ui.login.LoginUI;
import dto.NguyenLieu;
import util.TaoTinNhan;
import util.TaoUI;

import java.awt.*;

public class ChiTietPhieuNhapNguyenLieuDialog extends JDialog {

    private DefaultTableModel modelNguyenLieu;

    private JTextField txtMaPN, txtNgayNhap, txtMaNV, txtTongTien, txtMaNCC, txtMaNVXacNhan;
    private JTextArea txaGhiChu;
    private JComboBox<String> cbTrangThai;
    private JButton btnSua, btnLuu;

    private JPanel pnMaNVXacNhan;

    private PhieuNhapNguyenLieu phieuNhapNguyenLieu;

    public ChiTietPhieuNhapNguyenLieuDialog(Frame parent, PhieuNhapNguyenLieu phieuNhapNguyenLieu,
            NhapKhoNguyenLieuPanel nhapKhoNguyenLieuPanel) {
        super(parent, "Quản lý Phiếu Nhập", true);
        setSize(550, 800);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        this.phieuNhapNguyenLieu = phieuNhapNguyenLieu;
        JPanel pnTop = new JPanel(new BorderLayout(0, 10));
        pnTop.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));

        JLabel lblTitle = new JLabel("Danh sách các nguyên liệu nhập", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        pnTop.add(lblTitle, BorderLayout.NORTH);

        String[] columnNames = { "Mã", "Tên", "Giá", "Số lượng", "Ngày SX", "Hạn SD" };
        modelNguyenLieu = new DefaultTableModel(columnNames, 0);

        JScrollPane scrollTable = TaoUI.taoTableScroll(modelNguyenLieu);
        scrollTable.setPreferredSize(new Dimension(500, 180));
        pnTop.add(scrollTable, BorderLayout.CENTER);

        add(pnTop, BorderLayout.NORTH);

        JPanel pnForm = new JPanel();
        pnForm.setLayout(new BoxLayout(pnForm, BoxLayout.Y_AXIS));
        pnForm.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        txtMaPN = new JTextField();
        txtNgayNhap = new JTextField();
        txtMaNV = new JTextField();
        txtTongTien = new JTextField();
        txtMaNCC = new JTextField();
        txtMaNVXacNhan = new JTextField();

        txtMaPN.setEditable(false);
        txtNgayNhap.setEditable(false);
        txtMaNV.setEditable(false);
        txtTongTien.setEditable(false);
        txtMaNCC.setEditable(false);
        txtMaNVXacNhan.setEditable(false);

        txtMaPN.setBackground(Color.WHITE);
        txtNgayNhap.setBackground(Color.WHITE);
        txtMaNV.setBackground(Color.WHITE);
        txtTongTien.setBackground(Color.WHITE);
        txtMaNCC.setBackground(Color.WHITE);
        txtMaNVXacNhan.setBackground(Color.WHITE);

        txaGhiChu = new JTextArea(4, 20);
        txaGhiChu.setLineWrap(true);
        txaGhiChu.setWrapStyleWord(true);
        txaGhiChu.setEditable(false);
        txaGhiChu.setBackground(Color.white);
        JScrollPane scrollGhiChu = new JScrollPane(txaGhiChu);

        cbTrangThai = new JComboBox<>(new String[] { "Đang xử lý", "Đã xác nhận" });
        cbTrangThai.setEnabled(false);

        pnForm.add(taoDong(new JLabel("Mã Phiếu Nhập (PK):")));
        pnForm.add(taoDong(txtMaPN));

        pnForm.add(taoDong(new JLabel("Ngày Nhập (yyyy-mm-dd):")));
        pnForm.add(taoDong(txtNgayNhap));

        pnForm.add(taoDong(new JLabel("Mã Nhân Viên Lập:")));
        pnForm.add(taoDong(txtMaNV));

        pnForm.add(taoDong(new JLabel("Mã Nhà Cung Cấp:")));
        pnForm.add(taoDong(txtMaNCC));

        pnForm.add(taoDong(new JLabel("Tổng Tiền:")));
        pnForm.add(taoDong(txtTongTien));

        pnForm.add(taoDong(new JLabel("Trạng Thái Xử Lý:")));
        pnForm.add(taoDong(cbTrangThai));

        pnMaNVXacNhan = new JPanel();
        pnMaNVXacNhan.setLayout(new BoxLayout(pnMaNVXacNhan, BoxLayout.Y_AXIS));
        pnMaNVXacNhan.add(taoDong(new JLabel("Mã Nhân Viên Xác Nhận:")));
        pnMaNVXacNhan.add(taoDong(txtMaNVXacNhan));

        pnForm.add(pnMaNVXacNhan);

        pnForm.add(taoDong(new JLabel("Ghi Chú:")));
        pnForm.add(taoDongArea(scrollGhiChu));

        add(pnForm, BorderLayout.CENTER);

        JPanel pnBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnLuu = new JButton("Lưu Phiếu Nhập");
        btnSua = new JButton("Sửa");
        btnLuu.setEnabled(false);

        pnBottom.add(btnSua);
        pnBottom.add(btnLuu);

        if (!phieuNhapNguyenLieu.getTrangThaiXuLy().equals("Đã xác nhận")) {
            add(pnBottom, BorderLayout.SOUTH);
        }

        ganSuKien();
        loadDuLieu();
        suaLaiGiaoDienTheoQuyen();
    }

    public void suaLaiGiaoDienTheoQuyen() {
        var listQuyen = ui.login.PhienDangNhap.getListQuyen();

        if (!listQuyen.contains("NK_SUA")) {
            btnSua.setVisible(false);
            btnLuu.setVisible(false);
            this.setTitle("Chi Tiết Phiếu Nhập (Chế độ chỉ đọc)");
        }
    }

    public void loadDuLieu() {
        modelNguyenLieu.setRowCount(0);
        if (phieuNhapNguyenLieu == null) {
            return;
        }

        NguyenLieuBUS nguyenLieuBUS = NguyenLieuBUS.getNguyenLieuBUS();
        for (LoNguyenLieu loNguyenLieu : phieuNhapNguyenLieu.getListLoNguyenLieu()) {
            NguyenLieu nguyenLieu = nguyenLieuBUS.timNguyenLieu(loNguyenLieu.getMaNL());
            modelNguyenLieu.addRow(new Object[] {
                    loNguyenLieu.getMaLoNL(),
                    nguyenLieu != null ? nguyenLieu.getTenNL() : "",
                    loNguyenLieu.getGiaNhap(),
                    loNguyenLieu.getSoLuong(),
                    loNguyenLieu.getNgaySanXuat(),
                    loNguyenLieu.getHanSuDung()
            });
        }

        txtMaNCC.setText(phieuNhapNguyenLieu.getMaNCC());
        txtMaNV.setText(phieuNhapNguyenLieu.getMaNV());
        txtMaPN.setText(phieuNhapNguyenLieu.getMaPN());
        txtNgayNhap.setText(phieuNhapNguyenLieu.getNgayNhap());
        txtTongTien.setText(String.valueOf(phieuNhapNguyenLieu.getTongTien()));
        cbTrangThai.setSelectedItem(phieuNhapNguyenLieu.getTrangThaiXuLy());
        txaGhiChu.setText(phieuNhapNguyenLieu.getGhiChu());

        String maNVXacNhan = phieuNhapNguyenLieu.getMaNVXacNhan();
        txtMaNVXacNhan.setText(maNVXacNhan != null ? maNVXacNhan : "");
    }

    private JPanel taoDong(JComponent comp) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        panel.add(comp, BorderLayout.CENTER);

        JPanel marginPanel = new JPanel(new BorderLayout());
        marginPanel.add(panel, BorderLayout.CENTER);
        marginPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        return marginPanel;
    }

    private JPanel taoDongArea(JComponent comp) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(comp, BorderLayout.CENTER);

        JPanel marginPanel = new JPanel(new BorderLayout());
        marginPanel.add(panel, BorderLayout.CENTER);
        marginPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        return marginPanel;
    }

    private void ganSuKien() {
        btnSua.addActionListener(e -> {
            btnSua.setEnabled(false);
            btnLuu.setEnabled(true);
            txaGhiChu.setEditable(true);
            cbTrangThai.setEnabled(true);
        });

        btnLuu.addActionListener(e -> {
            if (phieuNhapNguyenLieu.getTrangThaiXuLy().equals("Đang xử lý")
                    && cbTrangThai.getSelectedItem().toString().equals("Đã xác nhận")) {
                int luaChon = JOptionPane.showConfirmDialog(null, "Xác nhận phiếu nhập kho nguyên liệu?", "Xác nhận",
                        JOptionPane.YES_NO_CANCEL_OPTION);
                if (!(luaChon == JOptionPane.YES_OPTION)) {
                    return;
                }

                if (ui.login.PhienDangNhap.getUser() != null) {
                    txtMaNVXacNhan.setText(ui.login.PhienDangNhap.getUser().getMaNV());
                }
            }

            PhieuNhapNguyenLieu phieuGoi = dongGoiPhieuNhapNguyenLieu();
            PhieuNhapNguyenLieuBUS bus = PhieuNhapNguyenLieuBUS.getPhieuNhapNguyenLieuBUS();

            if (bus.capNhapPhieuNhapNguyenLieu(phieuGoi)) {
                TaoTinNhan.showAutoCloseMessage("Cập nhật phiếu nhập nguyên liệu thành công", "Thông báo", 1);
                LoginUI.getLoginUI().getMainFrame().loadAllData();
            } else {
                TaoTinNhan.showAutoCloseMessage("Cập nhật phiếu nhập nguyên liệu thất bại", "Thông báo", 1);
            }
            dispose();
        });
    }

    private PhieuNhapNguyenLieu dongGoiPhieuNhapNguyenLieu() {
        phieuNhapNguyenLieu.setGhiChu(txaGhiChu.getText());
        phieuNhapNguyenLieu.setTrangThaiXuLy(cbTrangThai.getSelectedItem().toString());

        phieuNhapNguyenLieu.setMaNVXacNhan(txtMaNVXacNhan.getText());
        return phieuNhapNguyenLieu;
    }

    public JPanel getPnMaNVXacNhan() {
        return pnMaNVXacNhan;
    }
}