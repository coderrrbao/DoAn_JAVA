package ui.phanquyen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dto.NhomQuyen;
import util.TaoUI;

public class PhanQuyenDialog extends JDialog {
    private JTextField tfTenNhomQuyen;
    private JPanel pnlChucNangContent;
    private JButton btnThem, btnHuy;
    private Map<String, JCheckBox[]> mapCheckBoxes = new HashMap<>();

    private String[] danhSachChucNang = {
            "Quản lý sản phẩm", "Nhà cung cấp", "Nhập kho", "Tồn kho",
            "Xuất kho", "Kiểm kê", "Bán hàng", "Hóa đơn",
            "Khách hàng", "Nhân viên", "Tài khoản", "Phân quyền",
            "Thống kê", "Khuyến mãi"
    };

    public PhanQuyenDialog(NhomQuyen nhomQuyen) {
        setTitle("Thêm nhóm quyền");
        setSize(800, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        initGUI(nhomQuyen);
        setVisible(true);
    }

    private void initGUI(NhomQuyen nhomQuyen) {
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
            cheDoXem(nhomQuyen);
        } else {
            add(taoSouthPanel(), BorderLayout.SOUTH);
        }

    }

    private void cheDoXem(NhomQuyen nhomQuyen) {
        tfTenNhomQuyen.setText(nhomQuyen.getTenNhomQuyen());
        tfTenNhomQuyen.setEditable(false);
        tfTenNhomQuyen.setFocusable(false);
        for (JCheckBox[] boxes : mapCheckBoxes.values()) {
            for (JCheckBox cb : boxes) {
                cb.setEnabled(false);
            }
        }
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

        for (String chucNang : danhSachChucNang) {
            pnlChucNangContent.add(taoHangChucNang(chucNang));
        }

        JScrollPane scrollPane = TaoUI.taoScrollPane(pnlChucNangContent);
        scrollPane.setBorder(null);
        mainContent.add(scrollPane, BorderLayout.CENTER);

        return mainContent;
    }

    private JPanel taoHangChucNang(String tenChucNang) {
        JPanel row = new JPanel(new GridLayout(1, 5));
        row.setBackground(Color.WHITE);
        row.setPreferredSize(new Dimension(700, 35));

        row.add(new JLabel(tenChucNang));

        JCheckBox[] boxes = new JCheckBox[4];
        for (int i = 0; i < 4; i++) {
            boxes[i] = new JCheckBox();
            row.add(createCheckBoxPanel(boxes[i]));
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

    public void xuLyThemNhomQuyen() {
        String tenNhom = tfTenNhomQuyen.getText();
        for (String chucNang : danhSachChucNang) {
            JCheckBox[] boxes = mapCheckBoxes.get(chucNang);
            boolean xem = boxes[0].isSelected();
            boolean tao = boxes[1].isSelected();
            boolean sua = boxes[2].isSelected();
            boolean xoa = boxes[3].isSelected();

        }
    }

    private JPanel taoSouthPanel() {
        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        south.setBackground(Color.WHITE);

        btnThem = new JButton("Thêm nhóm quyền");
        btnThem.setBackground(new Color(52, 152, 219));
        btnThem.setForeground(Color.WHITE);
        btnThem.setPreferredSize(new Dimension(150, 40));
        btnThem.addActionListener(e -> xuLyThemNhomQuyen());

        btnHuy = new JButton("Huỷ bỏ");
        btnHuy.setBackground(new Color(231, 76, 60));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setPreferredSize(new Dimension(150, 40));
        btnHuy.addActionListener(e -> dispose());

        south.add(btnThem);
        south.add(btnHuy);
        return south;
    }

    public static void main(String[] args) {
        new PhanQuyenDialog(new NhomQuyen("123", "Quan li"));
    }
}