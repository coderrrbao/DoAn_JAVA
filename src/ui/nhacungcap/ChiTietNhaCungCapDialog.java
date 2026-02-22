package ui.nhacungcap;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import util.TaoUI;

import java.awt.*;

public class ChiTietNhaCungCapDialog extends JDialog {

    private JComboBox<String> cbLoaiHang;
    private JTextField txtMaNCC, txtTenNCC, txtSoDienThoai, txtDiaChi;
    private JButton btnLuu, btnDong;

    // Khai báo thêm các component cho phần Top
    private JButton btnThemHang, btnXoaHang;
    private JTable tblHangHoa;
    private DefaultTableModel modelHangHoa;

    public ChiTietNhaCungCapDialog(Frame parent) {
        super(parent, "Quản lý Nhà Cung Cấp", true);
        setSize(480, 600); // Tăng kích thước để có không gian chứa JTable
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        // ==================== PHẦN TOP ====================
        JPanel pnTop = new JPanel(new BorderLayout(5, 10));
        pnTop.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));

        // --- Dòng 1: Thêm, Xóa, ComboBox ---
        JPanel pnRow1 = TaoUI.taoPanelBoxLayoutNgang(480, 30);
        btnThemHang = new JButton("Thêm");
        btnXoaHang = new JButton("Xóa");
        cbLoaiHang = new JComboBox<>(new String[] { "Sản phẩm", "Nguyên liệu" });

        cbLoaiHang.addActionListener(e -> {
            String loaiHangDuocChon = (String) cbLoaiHang.getSelectedItem();
            System.out.println("Đang reload lại model cho: " + loaiHangDuocChon);
            // TODO: Gọi hàm truy vấn SQL và đổ dữ liệu lại vào modelHangHoa
        });
        TaoUI.setFixSize(cbLoaiHang, 100, 30);

        pnRow1.add(btnThemHang);
        pnRow1.add(Box.createRigidArea(new Dimension(10, 0)));
        pnRow1.add(btnXoaHang);
        pnRow1.add(Box.createHorizontalGlue());
        pnRow1.add(cbLoaiHang);

        // --- Dòng 2: JTable ---
        String[] columnNames = { "Mã", "Tên", "Loại", "Giá nhập" };
        modelHangHoa = new DefaultTableModel(columnNames, 0);

        JScrollPane scrollHangHoa = TaoUI.taoTableScroll(modelHangHoa);
        tblHangHoa = (JTable) scrollHangHoa.getViewport().getView();
        // Thiết lập chiều cao cố định cho bảng ở phần top khoảng 150px
        scrollHangHoa.setPreferredSize(new Dimension(400, 150));

        pnTop.add(pnRow1, BorderLayout.NORTH);
        pnTop.add(scrollHangHoa, BorderLayout.CENTER);

        add(pnTop, BorderLayout.NORTH);

        // ==================== PHẦN FORM (CENTER) ====================
        JPanel pnForm = new JPanel();
        pnForm.setLayout(new BoxLayout(pnForm, BoxLayout.Y_AXIS));
        pnForm.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        // Khởi tạo TextFields
        txtMaNCC = new JTextField();
        txtTenNCC = new JTextField();
        txtSoDienThoai = new JTextField();
        txtDiaChi = new JTextField();

        // Thêm các thành phần theo cấu trúc: 1 dòng Label - 1 dòng TextField
        pnForm.add(taoDong(new JLabel("Mã Nhà Cung Cấp:")));
        pnForm.add(taoDong(txtMaNCC));

        pnForm.add(taoDong(new JLabel("Tên Nhà Cung Cấp:")));
        pnForm.add(taoDong(txtTenNCC));

        pnForm.add(taoDong(new JLabel("Số Điện Thoại:")));
        pnForm.add(taoDong(txtSoDienThoai));

        pnForm.add(taoDong(new JLabel("Địa Chỉ:")));
        pnForm.add(taoDong(txtDiaChi));

        add(pnForm, BorderLayout.CENTER);

        // ==================== PHẦN BOTTOM (NÚT BẤM) ====================
        JPanel pnBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnLuu = new JButton("Lưu");
        btnDong = new JButton("Đóng");

        btnDong.addActionListener(e -> dispose());

        pnBottom.add(btnLuu);
        pnBottom.add(btnDong);
        add(pnBottom, BorderLayout.SOUTH);
    }

    // Hàm hỗ trợ tạo 1 dòng (Panel) với chiều cao cố định
    private JPanel taoDong(JComponent comp) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        panel.add(comp, BorderLayout.CENTER);

        JPanel marginPanel = new JPanel(new BorderLayout());
        marginPanel.add(panel, BorderLayout.CENTER);
        marginPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        return marginPanel;
    }

    public static void main(String[] args) {

        ChiTietNhaCungCapDialog nhaCungCapDialog = new ChiTietNhaCungCapDialog(null);
        nhaCungCapDialog.setVisible(true);
    }
}