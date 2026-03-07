package ui.xuatkho.sanpham;

import bus.PhieuHuySanPhamBUS;
import bus.SanPhamBUS;
import dto.LoSanPham;
import dto.PhieuHuySanPham;
import dto.SanPham;
import util.TaoUI;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ChiTietPhieuXuatSanPhamDialog extends JDialog {
  private JTable tblChiTiet;
  private DefaultTableModel modelChiTiet;
  private JTextField txtMaPH, txtNgay, txtNV, txtLyDo, txtTong;
  private JComboBox<String> cbTrangThai;
  private JButton btnLuu, btnSua;
  private PhieuHuySanPham phieuHuy;
  private XuatKhoSanPhamPanel parent;

  public ChiTietPhieuXuatSanPhamDialog(Frame owner, PhieuHuySanPham ph, XuatKhoSanPhamPanel parent) {
    super(owner, "Chi Tiết Phiếu Hủy Sản Phẩm", true);
    this.phieuHuy = ph;
    this.parent = parent;
    setSize(480, 650); // Thu gọn width một chút để form ôm sát đẹp hơn giống bên NCC
    setLocationRelativeTo(owner);
    setLayout(new BorderLayout(10, 10));

    // ==================== PHẦN TOP (TABLE) ====================
    JPanel pnTop = new JPanel(new BorderLayout(5, 10));
    pnTop.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));

    // CHỈNH SỬA 1: Ngăn chỉnh sửa các ô trong Table
    modelChiTiet = new DefaultTableModel(new String[] { "Mã Lô", "Tên SP", "Số lượng", "Giá" }, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    // Sử dụng TaoUI để đồng bộ giao diện Table
    JScrollPane scrollChiTiet = TaoUI.taoTableScroll(modelChiTiet);
    tblChiTiet = (JTable) scrollChiTiet.getViewport().getView();
    tblChiTiet.getTableHeader().setReorderingAllowed(false);
    scrollChiTiet.setPreferredSize(new Dimension(400, 150));

    pnTop.add(scrollChiTiet, BorderLayout.CENTER);
    add(pnTop, BorderLayout.NORTH);

    // ==================== PHẦN FORM (CENTER) ====================
    JPanel pnForm = new JPanel();
    pnForm.setLayout(new BoxLayout(pnForm, BoxLayout.Y_AXIS));
    pnForm.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

    txtMaPH = new JTextField(ph.getMaPH());
    txtNgay = new JTextField(ph.getNgayHuy().toString());
    txtNV = new JTextField(ph.getMaNV());
    txtLyDo = new JTextField(ph.getLyDo());
    txtTong = new JTextField(String.format("%,.0f VNĐ", ph.getTongGiaTri()));
    cbTrangThai = new JComboBox<>(new String[] { "Đang xử lý", "Đã xác nhận" });
    cbTrangThai.setSelectedItem(ph.getTrangThaiXuLy());

    // CHỈNH SỬA 2: Chặn hoàn toàn việc click và hiện con trỏ chuột
    JTextField[] fields = { txtMaPH, txtNgay, txtNV, txtLyDo, txtTong };
    for (JTextField f : fields) {
      f.setEditable(false);
      f.setBackground(Color.WHITE);
      f.setFocusable(false); // Ngăn hiện con trỏ nhấp nháy
    }
    cbTrangThai.setEnabled(false);

    // Thêm các thành phần theo cấu trúc: 1 dòng Label - 1 dòng Input
    pnForm.add(taoDong(new JLabel("Mã Phiếu:")));
    pnForm.add(taoDong(txtMaPH));

    pnForm.add(taoDong(new JLabel("Ngày Hủy:")));
    pnForm.add(taoDong(txtNgay));

    pnForm.add(taoDong(new JLabel("Nhân Viên:")));
    pnForm.add(taoDong(txtNV));

    pnForm.add(taoDong(new JLabel("Lý Do:")));
    pnForm.add(taoDong(txtLyDo));

    pnForm.add(taoDong(new JLabel("Tổng Tiền:")));
    pnForm.add(taoDong(txtTong));

    pnForm.add(taoDong(new JLabel("Trạng Thái:")));
    pnForm.add(taoDong(cbTrangThai));

    add(pnForm, BorderLayout.CENTER);

    // ==================== PHẦN BOTTOM (NÚT BẤM) ====================
    JPanel pnBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
    btnSua = new JButton("Sửa");
    btnLuu = new JButton("Lưu");
    btnLuu.setEnabled(false);

    pnBottom.add(btnSua);
    pnBottom.add(btnLuu);

    add(pnBottom, BorderLayout.SOUTH);

    if (ph.getTrangThaiXuLy().equals("Đã xác nhận")) {
      pnBottom.setVisible(false);
    }

    loadData();
    ganSuKien();
    suaLaiGiaoDienTheoQuyen();
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

  public void suaLaiGiaoDienTheoQuyen() {
    var listQuyen = ui.login.PhienDangNhap.getListQuyen();

    // Kiểm tra quyền SỬA phiếu xuất/hủy kho sản phẩm
    if (!listQuyen.contains("XK_SUA")) {
      // Ẩn hoàn toàn các nút có khả năng thay đổi dữ liệu
      btnSua.setVisible(false);
      btnLuu.setVisible(false);
      this.setTitle("Chi Tiết Phiếu Hủy Sản Phẩm (Chế độ chỉ đọc)");
    }
    this.revalidate();
    this.repaint();
  }

  private void loadData() {
    modelChiTiet.setRowCount(0);
    ArrayList<LoSanPham> list = phieuHuy.getListLoSanPhamHuy();
    if (list != null) {
      for (LoSanPham lo : list) {
        SanPham sp = SanPhamBUS.getSanPhamBUS().timSanPham(lo.getMaSP());
        modelChiTiet.addRow(
            new Object[] {
                lo.getMaLoSP(), (sp != null ? sp.getTenSP() : "N/A"), lo.getSoLuong(), lo.getGiaNhap()
            });
      }
    }
  }

  private void ganSuKien() {

    btnSua.addActionListener(e -> {
      txtLyDo.setEditable(true);
      txtLyDo.setFocusable(true);
      txtLyDo.requestFocus();
      cbTrangThai.setEnabled(true);
      btnLuu.setEnabled(true);
      btnSua.setEnabled(false);
    });

    btnLuu.addActionListener(e -> {
      phieuHuy.setLyDo(txtLyDo.getText());
      phieuHuy.setTrangThaiXuLy(cbTrangThai.getSelectedItem().toString());
      if (PhieuHuySanPhamBUS.getPhieuHuySanPhamBUS().capNhatPhieuHuy(phieuHuy)) {
        parent.loadDuLieu();
        dispose();
      }
    });
  }
}