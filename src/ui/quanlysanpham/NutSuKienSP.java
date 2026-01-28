package ui.quanlysanpham;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import bus.SanPhamBUS;
import dto.ChiTietCongThuc;
import dto.CongThuc;
import dto.SanPham;
import dto.Size;
import util.TaoUI;

import java.awt.Component;
import java.net.URL;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class NutSuKienSP extends DefaultCellEditor {
    protected JButton button;
    private int currentRow;
    private JTable currentTable;

    public NutSuKienSP(JCheckBox checkBox, QuanLySanPhamUI quanLySanPhamUI) {
        super(checkBox);
        button = new JButton();

        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);

        button.addActionListener(e -> {
            fireEditingStopped();

            SanPhamBUS quanLySanPhamBUS = new SanPhamBUS();
            SanPham sanPham = quanLySanPhamBUS
                    .timSanPham(String.valueOf(currentTable.getModel().getValueAt(currentRow, 2)));
                    
                    inThongTinSanPham(sanPham);
            if (sanPham != null) {
                quanLySanPhamUI.layXemChiTietSanPhamDialog().settupGiaoDien(sanPham);

                quanLySanPhamUI.layXemChiTietSanPhamDialog().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Bruhh");
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.currentRow = row;
        this.currentTable = table;
        URL url = TaoUI.class.getResource("../assets/icon/sua.svg");
        FlatSVGIcon icon = new FlatSVGIcon(url).derive(30, 30);
        button.setIcon(icon);
        button.setBackground(table.getSelectionBackground());
        button.setOpaque(true);
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }

    public void inThongTinSanPham(SanPham sp) {
        if (sp == null) {
            System.out.println(">> LỖI: Đối tượng Sản phẩm là NULL.");
            return;
        }

        System.out.println("\n==================== THÔNG TIN SẢN PHẨM ====================");
        // 1. In thông tin cơ bản
        System.out.println("Mã SP          : " + sp.getMaSP());
        System.out.println("Tên SP         : " + sp.getTenSP());

        // Xử lý các object tham chiếu (DanhMuc, NhaCungCap) để tránh null pointer
        String tenDM = (sp.getDanhMuc() != null) ? sp.getDanhMuc().getTenDM() : "---"; // Thay .getTenDM() bằng hàm của
                                                                                       // bạn
        System.out.println("Danh mục       : " + tenDM);

        String tenNCC = (sp.getNhaCungCap() != null) ? sp.getNhaCungCap().getTenNCC() : "---"; // Thay .getTenNCC() bằng
                                                                                               // hàm của bạn
        System.out.println("Nhà cung cấp   : " + tenNCC);

        System.out.printf("Giá bán        : %,d VNĐ\n", sp.getGiaBan());
        System.out.println("Loại nước      : " + sp.getLoaiNuoc());
        System.out.println("Dung tích      : " + sp.getTheTich() + " ml");
        System.out.println("Cảnh báo kho   : " + sp.getMucCanhBao());
        System.out.println("Trạng thái bán : " + (sp.getTrangThai() ? "Đang kinh doanh" : "Ngừng kinh doanh"));
        System.out.println("Trạng thái XL  : " + sp.getTrangThaiXuLy());
        System.out.println("Ảnh            : " + sp.getAnh());

        // 2. In danh sách Size
        System.out.println("\n-------------------- DANH SÁCH SIZE --------------------");
        if (sp.getListSize() != null && !sp.getListSize().isEmpty()) {
            System.out.printf("%-10s | %-15s | %-15s | %-15s\n", "Mã Size", "Tên Size", "% Giá thêm", "% NL thêm");
            for (Size s : sp.getListSize()) {
                System.out.printf("%-10s | %-15s | %-15d | %-15d\n",
                        s.getMaSize(), s.getTenSize(), s.getPhanTramGia(), s.getPhanTramNL());
            }
        } else {
            System.out.println(">> Sản phẩm chưa có Size nào.");
        }

        // 3. In thông tin Công thức
        System.out.println("\n-------------------- CÔNG THỨC PHA CHẾ --------------------");
        CongThuc ct = sp.getCongThuc();
        if (ct != null) {
            System.out.println("Mã công thức: " + ct.getMaCT());

            if (ct.getListChiTietCongThuc() != null && !ct.getListChiTietCongThuc().isEmpty()) {
                System.out.printf("%-20s | %-10s\n", "Tên Nguyên Liệu", "Số Lượng");
                for (ChiTietCongThuc ctct : ct.getListChiTietCongThuc()) {
                    // Giả định class NguyenLieu có hàm getTenNL()
                    String tenNL = (ctct.getNguyenLieu() != null) ? ctct.getNguyenLieu().getTenNL() : "Null NL";
                    System.out.printf("%-20s | %-10.2f\n", tenNL, ctct.getSoLuong());
                }
            } else {
                System.out.println(">> Công thức này chưa có nguyên liệu chi tiết.");
            }
        } else {
            System.out.println(">> Sản phẩm chưa có công thức.");
        }
        System.out.println("===========================================================\n");
    }
}