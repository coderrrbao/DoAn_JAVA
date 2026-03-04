package ui.phanquyen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import bus.NhomQuyenBUS;
import dto.NhomQuyen;
import dto.Quyen;
import util.TaoTinNhan;
import util.TaoUI;

public class NhomQuyenDialog extends JDialog {
    private JTextField tfTenNhomQuyen;
    private JPanel pnlChucNangContent;
    private JButton btnThem, btnHuy, btnLuu, btnSua;
    private Map<String, JCheckBox[]> mapCheckBoxes = new HashMap<>();

    private HashSet<String> set = new HashSet<>();

    private String[] danhSachChucNang = {
            "Quản lý sản phẩm",
            "Nguyên liệu",
            "Nhà cung cấp",
            "Nhập kho",
            "Tồn kho",
            "Xuất kho",
            "Kiểm kê",
            "Bán hàng",
            "Hóa đơn",
            "Khách hàng",
            "Hạng thành viên",
            "Nhân viên",
            "Tài khoản",
            "Phân quyền",
            "Thống kê",
            "Khuyến mãi"
    };
    private String[] danhSachMaChucNang = {
            "QLSP", // 1. Quản lý sản phẩm
            "NL", // 2. Nguyên liệu
            "NCC", // 3. Nhà cung cấp
            "NK", // 4. Nhập kho
            "TKHO", // 5. Tồn kho (Sửa từ TKho thành TKHO cho khớp SQL)
            "XK", // 6. Xuất kho
            "KK", // 7. Kiểm kê
            "BH", // 8. Bán hàng
            "HD", // 9. Hóa đơn
            "KH", // 10. Khách hàng
            "HTV", // 11. Hạng thành viên
            "NV", // 12. Nhân viên
            "TK", // 13. Tài khoản (Sửa từ TKHOAN thành TK cho khớp SQL)
            "PQ", // 14. Phân quyền
            "TKE", // 15. Thống kê
            "KM" // 16. Khuyến mãi
    };
    private NhomQuyen nhomQuyen;
    private NhomQuyenUI nhomQuyenUI;

    public NhomQuyenDialog(NhomQuyen nhomQuyen, NhomQuyenUI nhomQuyenUI) {
        setTitle("Thêm nhóm quyền");
        setSize(800, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        this.nhomQuyen = nhomQuyen;
        this.nhomQuyenUI = nhomQuyenUI;
        initGUI();
        ganSuKien();
        setVisible(true);
        suaLaiGiaoDienTheoQuyen();
    }

    public void suaLaiGiaoDienTheoQuyen() {
        var listQuyen = ui.login.PhienDangNhap.getListQuyen();

        // 1. Trường hợp Thêm mới
        if (nhomQuyen == null) {
            if (!listQuyen.contains("PQ_THEM")) {
                if (btnThem != null)
                    btnThem.setEnabled(false);
                tfTenNhomQuyen.setEditable(false);
                setEnableAllCheckBoxes(false);
                setTitle("Bạn không có quyền thêm nhóm quyền");
            }
        }
        // 2. Trường hợp Xem/Sửa
        else {
            if (!listQuyen.contains("PQ_SUA")) {
                if (btnSua != null)
                    btnSua.setVisible(false); // Ẩn luôn nút Sửa
                if (btnLuu != null)
                    btnLuu.setVisible(false);
                setTitle("Chi tiết nhóm quyền (Chỉ xem)");
                // Đảm bảo mọi thứ bị khóa
                setEnableAllCheckBoxes(false);
            }
        }
    }

    /**
     * Hàm hỗ trợ bật/tắt nhanh tất cả checkbox
     */
    private void setEnableAllCheckBoxes(boolean status) {
        for (JCheckBox[] boxes : mapCheckBoxes.values()) {
            for (JCheckBox cb : boxes) {
                if (cb != null)
                    cb.setEnabled(status);
            }
        }
    }

    private void initGUI() {
        JPanel pnlNorth = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        pnlNorth.setBackground(Color.WHITE);

        JLabel lblTen = new JLabel("Tên nhóm quyền");
        tfTenNhomQuyen = new JTextField();
        tfTenNhomQuyen.setPreferredSize(new Dimension(600, 35));

        pnlNorth.add(lblTen);
        pnlNorth.add(tfTenNhomQuyen);
        add(pnlNorth, BorderLayout.NORTH);
        add(contentPanel(), BorderLayout.CENTER);
        if (nhomQuyen != null) {
            loadDuLieu(nhomQuyen);
            add(taoSouJPanelSua(), BorderLayout.SOUTH);
            tacThaoTacSua();
        } else {
            add(taoSouthPanelThem(), BorderLayout.SOUTH);
        }

    }

    private void loadDuLieu(NhomQuyen nhomQuyen) {
        if (nhomQuyen == null) {
            return;
        }
        for (Quyen quyen : nhomQuyen.getListQuyen()) {
            set.add(quyen.getTenQuyen());
        }
        tfTenNhomQuyen.setText(nhomQuyen.getTenNhomQuyen());
        tfTenNhomQuyen.setEditable(false);
        tfTenNhomQuyen.setFocusable(false);
        for (JCheckBox[] boxes : mapCheckBoxes.values()) {
            for (JCheckBox cb : boxes) {
                if (set.contains(cb.getActionCommand())) {
                    cb.setSelected(true);
                }
            }
        }
    }

    private void ganSuKien() {
        if (btnSua != null) {
            btnSua.addActionListener(e -> {
                batThaoTacSua();
            });
        }

        if (btnLuu != null) {
            btnLuu.addActionListener(e -> {
                NhomQuyen duLieuNQ = dongGoiNhomQuyen();
                nhomQuyen.setListQuyen(duLieuNQ.getListQuyen());
                NhomQuyenBUS nhomQuyenBUS = NhomQuyenBUS.getNhomQuyenBUS();
                if (nhomQuyenBUS.capNhatNhomQuyen(nhomQuyen)) {
                    TaoTinNhan.showAutoCloseMessage("Cập nhật nhóm quyền thành công", "Thông báo", 1);
                    nhomQuyen = nhomQuyenBUS.timNhomQuyen(nhomQuyen.getMaNQ());
                    loadDuLieu(nhomQuyen);
                    tacThaoTacSua();
                    nhomQuyenUI.loadDuLieu();
                } else {
                    TaoTinNhan.showAutoCloseMessage("Cập nhật nhóm quyền thất bại", "Thông báo", 1);
                }
            });
        }

        if (btnThem != null) {
            btnThem.addActionListener(e -> {
                NhomQuyen nhomQuyen = dongGoiNhomQuyen();
                NhomQuyenBUS nhomQuyenBUS = NhomQuyenBUS.getNhomQuyenBUS();
                if (nhomQuyenBUS.themNhomQuyen(nhomQuyen)) {
                    TaoTinNhan.showAutoCloseMessage("Thêm nhóm quyền thành công", "Thông báo", 1);
                    nhomQuyenUI.loadDuLieu();
                } else {
                    TaoTinNhan.showAutoCloseMessage("Thêm nhóm quyền thất bại", "Thông báo", 1);
                }
                dispose();
            });
        }
    }

    private void tacThaoTacSua() {
        if (btnLuu != null) {
            btnLuu.setEnabled(false);
        }
        if (btnSua != null) {
            btnSua.setEnabled(true);
        }

        tfTenNhomQuyen.setEditable(false);
        for (JCheckBox[] boxes : mapCheckBoxes.values()) {
            for (JCheckBox cb : boxes) {
                cb.setEnabled(false);
            }
        }
    }

    private void batThaoTacSua() {
        if (btnLuu != null) {
            btnLuu.setEnabled(true);
        }
        if (btnSua != null) {
            btnSua.setEnabled(false);
        }

        tfTenNhomQuyen.setEditable(true);
        tfTenNhomQuyen.requestFocus();
        for (JCheckBox[] boxes : mapCheckBoxes.values()) {
            if (boxes != null) {
                for (JCheckBox cb : boxes) {
                    if (cb != null) {
                        cb.setEnabled(true);
                    }
                }
            }
        }
    }

    private NhomQuyen dongGoiNhomQuyen() {
        NhomQuyen nhomQuyen = new NhomQuyen();
        nhomQuyen.setTenNhomQuyen(tfTenNhomQuyen.getText());
        ArrayList<Quyen> listQuyen = new ArrayList<>();
        for (JCheckBox[] boxes : mapCheckBoxes.values()) {
            for (JCheckBox cb : boxes) {
                if (cb.isSelected()) {
                    listQuyen.add(new Quyen("", cb.getActionCommand()));
                }
            }
        }
        nhomQuyen.setListQuyen(listQuyen);
        return nhomQuyen;
    }

    private JPanel contentPanel() {
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(Color.WHITE);
        mainContent.setBorder(new EmptyBorder(10, 20, 10, 20));

        JPanel header = new JPanel(new GridLayout(1, 5));
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(700, 40));

        JLabel lblTitle = new JLabel("Danh mục chức năng");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 14));

        header.add(lblTitle);
        header.add(createHeaderLabel("Xem"));
        header.add(createHeaderLabel("Tạo mới"));
        header.add(createHeaderLabel("Cập nhật"));
        header.add(createHeaderLabel("Xóa"));

        mainContent.add(header, BorderLayout.NORTH);

        pnlChucNangContent = new JPanel(new GridLayout(danhSachChucNang.length, 1, 0, 5));
        pnlChucNangContent.setBackground(Color.WHITE);

        for (int i = 0; i < danhSachChucNang.length; i++) {
            pnlChucNangContent.add(taoHangChucNang(danhSachChucNang[i], danhSachMaChucNang[i]));
        }

        JScrollPane scrollPane = TaoUI.taoScrollPane(pnlChucNangContent);
        scrollPane.setBorder(null);
        mainContent.add(scrollPane, BorderLayout.CENTER);

        return mainContent;
    }

    private JPanel taoHangChucNang(String tenChucNang, String maChucNang) {
        JPanel row = new JPanel(new GridLayout(1, 5));
        row.setBackground(Color.WHITE);
        row.setPreferredSize(new Dimension(700, 35));

        row.add(new JLabel(tenChucNang));

        JCheckBox[] boxes = new JCheckBox[4];
        ArrayList<String> chucNangChinh = new ArrayList<>();
        chucNangChinh.add("_XEM");
        chucNangChinh.add("_TAO");
        chucNangChinh.add("_SUA");
        chucNangChinh.add("_XOA");

        for (int i = 0; i < 4; i++) {
            boxes[i] = new JCheckBox();
            row.add(createCheckBoxPanel(boxes[i]));
            boxes[i].setActionCommand(maChucNang + chucNangChinh.get(i));

        }
        mapCheckBoxes.put(tenChucNang, boxes);

        return row;
    }

    private JPanel createCheckBoxPanel(JCheckBox cb) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.setBackground(Color.WHITE);
        p.add(cb);
        return p;
    }

    private JLabel createHeaderLabel(String text) {
        JLabel lbl = new JLabel(text, JLabel.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 13));
        return lbl;
    }

    private JPanel taoSouthPanelThem() {
        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        south.setBackground(Color.WHITE);

        btnThem = new JButton("Thêm nhóm quyền");
        btnThem.setPreferredSize(new Dimension(150, 25));

        btnHuy = new JButton("Huỷ bỏ");
        btnHuy.setPreferredSize(new Dimension(150, 25));
        btnHuy.addActionListener(e -> dispose());

        south.add(btnThem);
        south.add(btnHuy);
        return south;
    }

    private JPanel taoSouJPanelSua() {
        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        south.setBackground(Color.WHITE);

        btnSua = new JButton("Sửa");
        btnSua.setPreferredSize(new Dimension(120, 25));

        btnLuu = new JButton("Lưu thay đổi");
        btnLuu.setPreferredSize(new Dimension(150, 25));
        btnLuu.setEnabled(false);

        south.add(btnSua);
        south.add(btnLuu);

        return south;
    }
}