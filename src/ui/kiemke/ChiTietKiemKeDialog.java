package ui.kiemke;

import bus.LoNguyenLieuBUS;
import bus.LoSanPhamBUS;
import bus.NguyenLieuBUS;
import bus.SanPhamBUS;
import dto.LoNguyenLieu;
import dto.LoSanPham;
import dto.NguyenLieu;
import dto.PhieuKiemKe;
import dto.SanPham;

import javax.swing.*;
import java.awt.*;

public class ChiTietKiemKeDialog extends JDialog {

    private JTextField txtMaPhieu, txtMaNV, txtMaLo, txtLoaiLo, txtTenDoiTuong, txtSLSoSach, txtSLThucTe, txtChenhLech,
            txtMaNVXacNhan;
    private JTextArea txtGhiChu;

    public ChiTietKiemKeDialog(Frame parent, PhieuKiemKe phieuKiemKe) {
        super(parent, "Chi Tiết Phiếu Kiểm Kê", true);
        setSize(480, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel pnForm = new JPanel();
        pnForm.setLayout(new BoxLayout(pnForm, BoxLayout.Y_AXIS));
        pnForm.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        txtMaPhieu = new JTextField(phieuKiemKe.getMaKK());
        txtMaNV = new JTextField(phieuKiemKe.getMaNV());
        txtMaNVXacNhan = new JTextField(phieuKiemKe.getMaNVXacNhan() != null ? phieuKiemKe.getMaNVXacNhan() : "");
        txtMaLo = new JTextField(phieuKiemKe.getMaLo());
        txtLoaiLo = new JTextField(phieuKiemKe.getLoaiLo());

        String tenDoiTuong = "Không tìm thấy thông tin";
        if ("Sản phẩm".equalsIgnoreCase(phieuKiemKe.getLoaiLo())) {
            LoSanPham loSP = LoSanPhamBUS.getLoSanPhamBUS().timLoSanPham(phieuKiemKe.getMaLo());
            if (loSP != null) {
                SanPham sp = SanPhamBUS.getSanPhamBUS().timSanPham(loSP.getMaSP());
                if (sp != null) {
                    tenDoiTuong = sp.getTenSP();
                }
            }
        } else if ("Nguyên liệu".equalsIgnoreCase(phieuKiemKe.getLoaiLo())) {
            LoNguyenLieu loNL = LoNguyenLieuBUS.getLoNguyenLieuBUS().timLoNguyenLieu(phieuKiemKe.getMaLo());
            if (loNL != null) {
                NguyenLieu nl = NguyenLieuBUS.getNguyenLieuBUS().timNguyenLieu(loNL.getMaNL());
                if (nl != null) {
                    tenDoiTuong = nl.getTenNL();
                }
            }
        }
        txtTenDoiTuong = new JTextField(tenDoiTuong);

        txtSLSoSach = new JTextField(String.valueOf(phieuKiemKe.getSoLuongSoSach()));
        txtSLThucTe = new JTextField(String.valueOf(phieuKiemKe.getSoLuongThuc()));

        double chenhLech = phieuKiemKe.getSoLuongThuc() - phieuKiemKe.getSoLuongSoSach();
        txtChenhLech = new JTextField(String.valueOf(chenhLech));

        if (chenhLech < 0) {
            txtChenhLech.setForeground(Color.RED);
            txtChenhLech.setFont(new Font("Arial", Font.BOLD, 12));
        } else if (chenhLech > 0) {
            txtChenhLech.setForeground(new Color(0, 153, 76));
            txtChenhLech.setFont(new Font("Arial", Font.BOLD, 12));
        }

        JTextField[] fields = { txtMaPhieu, txtMaNV, txtMaNVXacNhan, txtMaLo, txtLoaiLo, txtTenDoiTuong, txtSLSoSach,
                txtSLThucTe,
                txtChenhLech };
        for (JTextField f : fields) {
            f.setEditable(false);
            f.setBackground(Color.WHITE);
            f.setFocusable(false);
        }

        pnForm.add(taoDongDoi("Mã Phiếu Kiểm Kê:", txtMaPhieu, "Mã Nhân Viên:", txtMaNV));
        pnForm.add(taoDong("Mã NV Xác Nhận:", txtMaNVXacNhan));
        pnForm.add(taoDong("Mã Lô:", txtMaLo));
        pnForm.add(taoDong("Loại Lô:", txtLoaiLo));

        String labelTen = "Sản phẩm".equalsIgnoreCase(phieuKiemKe.getLoaiLo()) ? "Tên Sản Phẩm:" : "Tên Nguyên Liệu:";
        pnForm.add(taoDong(labelTen, txtTenDoiTuong));

        pnForm.add(taoDongDoi("SL Sổ Sách:", txtSLSoSach, "SL Thực Tế:", txtSLThucTe));
        pnForm.add(taoDong("Chênh Lệch:", txtChenhLech));

        txtGhiChu = new JTextArea(phieuKiemKe.getGhiChu() != null ? phieuKiemKe.getGhiChu() : "");
        txtGhiChu.setLineWrap(true);
        txtGhiChu.setWrapStyleWord(true);
        txtGhiChu.setEditable(false);
        txtGhiChu.setBackground(Color.WHITE);
        txtGhiChu.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        JScrollPane scrollGhiChu = new JScrollPane(txtGhiChu);
        JPanel pnGhiChu = new JPanel(new BorderLayout(0, 5));
        pnGhiChu.add(new JLabel("Ghi Chú:"), BorderLayout.NORTH);
        pnGhiChu.add(scrollGhiChu, BorderLayout.CENTER);

        pnGhiChu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        pnGhiChu.setPreferredSize(new Dimension(Integer.MAX_VALUE, 120));

        pnForm.add(pnGhiChu);

        add(pnForm, BorderLayout.CENTER);
    }

    /**
     * Hàm set kích thước TextField cao 30 và fix lỗi dãn của BoxLayout (1 component
     * / dòng)
     */
    private JPanel taoDong(String tenLabel, JComponent comp) {
        comp.setPreferredSize(new Dimension(Integer.MAX_VALUE, 30));

        JPanel pn = new JPanel(new BorderLayout(0, 5));
        pn.add(new JLabel(tenLabel), BorderLayout.NORTH);
        pn.add(comp, BorderLayout.CENTER);
        pn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        pn.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        return pn;
    }

    /**
     * Hàm set kích thước và gom 2 TextField lên cùng 1 dòng
     */
    private JPanel taoDongDoi(String label1, JComponent comp1, String label2, JComponent comp2) {
        comp1.setPreferredSize(new Dimension(Integer.MAX_VALUE, 30));
        JPanel pnTrai = new JPanel(new BorderLayout(0, 5));
        pnTrai.add(new JLabel(label1), BorderLayout.NORTH);
        pnTrai.add(comp1, BorderLayout.CENTER);

        comp2.setPreferredSize(new Dimension(Integer.MAX_VALUE, 30));
        JPanel pnPhai = new JPanel(new BorderLayout(0, 5));
        pnPhai.add(new JLabel(label2), BorderLayout.NORTH);
        pnPhai.add(comp2, BorderLayout.CENTER);

        JPanel pn = new JPanel(new GridLayout(1, 2, 15, 0));
        pn.add(pnTrai);
        pn.add(pnPhai);
        pn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        pn.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        return pn;
    }
}