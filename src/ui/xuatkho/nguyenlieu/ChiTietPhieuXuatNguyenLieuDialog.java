package ui.xuatkho.nguyenlieu;

import bus.NguyenLieuBUS;
import bus.PhieuHuyNguyenLieuBUS;
import dto.LoNguyenLieu;
import dto.NguyenLieu;
import dto.PhieuHuyNguyenLieu;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ChiTietPhieuXuatNguyenLieuDialog extends JDialog {
  private JTable tblChiTiet;
  private DefaultTableModel modelChiTiet;
  private JTextField txtMaPH, txtNgay, txtNV, txtLyDo, txtTong;
  private JComboBox<String> cbTrangThai;
  private JButton btnLuu, btnSua;
  private PhieuHuyNguyenLieu phieuHuy;
  private XuatKhoNguyenLieuPanel parent;

  public ChiTietPhieuXuatNguyenLieuDialog(
      Frame owner, PhieuHuyNguyenLieu ph, XuatKhoNguyenLieuPanel parent) {
    super(owner, "Chi Tiết Phiếu Hủy Nguyên Liệu", true);
    this.phieuHuy = ph;
    this.parent = parent;
    setSize(600, 680);
    setLocationRelativeTo(owner);
    setLayout(new BorderLayout(10, 10));

    // CHỈNH SỬA: Chặn edit table cell
    modelChiTiet = new DefaultTableModel(new String[] { "Mã Lô", "Tên NL", "Số lượng", "Giá" }, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    tblChiTiet = new JTable(modelChiTiet);
    tblChiTiet.getTableHeader().setReorderingAllowed(false);
    add(new JScrollPane(tblChiTiet), BorderLayout.NORTH);
    tblChiTiet.setPreferredScrollableViewportSize(new Dimension(500, 150));

    JPanel pnForm = new JPanel();
    pnForm.setLayout(new BoxLayout(pnForm, BoxLayout.Y_AXIS));
    pnForm.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

    txtMaPH = new JTextField(ph.getMaPH());
    txtNgay = new JTextField(ph.getNgayHuy().toString());
    txtNV = new JTextField(ph.getMaNV());
    txtLyDo = new JTextField(ph.getLyDo());
    txtTong = new JTextField(String.format("%,.0f VNĐ", ph.getTongTien()));
    cbTrangThai = new JComboBox<>(new String[] { "Đang xử lý", "Đã xác nhận" });
    cbTrangThai.setSelectedItem(ph.getTrangThaiXuLy());

    // CHỈNH SỬA: Chặn focus các ô text
    JTextField[] fields = { txtMaPH, txtNgay, txtNV, txtLyDo, txtTong };
    for (JTextField f : fields) {
      f.setEditable(false);
      f.setBackground(Color.WHITE);
      f.setFocusable(false);
    }
    cbTrangThai.setEnabled(false);

    pnForm.add(new JLabel("Mã Phiếu:"));
    pnForm.add(txtMaPH);
    pnForm.add(new JLabel("Ngày Hủy:"));
    pnForm.add(txtNgay);
    pnForm.add(new JLabel("Nhân Viên:"));
    pnForm.add(txtNV);
    pnForm.add(new JLabel("Lý Do:"));
    pnForm.add(txtLyDo);
    pnForm.add(new JLabel("Tổng Tiền:"));
    pnForm.add(txtTong);
    pnForm.add(new JLabel("Trạng Thái:"));
    pnForm.add(cbTrangThai);

    add(pnForm, BorderLayout.CENTER);

    JPanel pnBtn = new JPanel(new FlowLayout());
    btnSua = new JButton("Sửa");
    btnLuu = new JButton("Lưu");
    btnLuu.setEnabled(false);
    if (!"Đã xác nhận".equals(ph.getTrangThaiXuLy())) {
      add(pnBtn, BorderLayout.SOUTH);
    }
    pnBtn.add(btnSua);
    pnBtn.add(btnLuu);

    loadData();
    ganSuKien();
    suaLaiGiaoDienTheoQuyen();
  }

  public void suaLaiGiaoDienTheoQuyen() {
    var listQuyen = ui.login.PhienDangNhap.getListQuyen();

    // Kiểm tra quyền SỬA (XK_SUA)
    if (!listQuyen.contains("XK_SUA")) {
      // Ẩn các nút thao tác chỉnh sửa
      btnSua.setVisible(false);
      btnLuu.setVisible(false);

      // Cập nhật tiêu đề để thông báo đây là chế độ chỉ đọc
      this.setTitle("Chi Tiết Phiếu Hủy Nguyên Liệu (Chế độ chỉ đọc)");
    }
    this.revalidate();
    this.repaint();
  }

  private void loadData() {
    modelChiTiet.setRowCount(0);
    ArrayList<LoNguyenLieu> list = phieuHuy.getListLoNguyenLieuHuy();
    if (list != null) {
      for (LoNguyenLieu lo : list) {
        NguyenLieu nl = NguyenLieuBUS.getNguyenLieuBUS().timNguyenLieu(lo.getMaNL());
        modelChiTiet.addRow(
            new Object[] {
                lo.getMaLoNL(), (nl != null ? nl.getTenNL() : "N/A"), lo.getSoLuong(), lo.getGiaNhap()
            });
      }
    }
  }

  private void ganSuKien() {
    btnSua.addActionListener(
        e -> {
          txtLyDo.setEditable(true);
          txtLyDo.setFocusable(true);
          txtLyDo.requestFocus();
          cbTrangThai.setEnabled(true);
          btnLuu.setEnabled(true);
          btnSua.setEnabled(false);
        });

    btnLuu.addActionListener(
        e -> {
          phieuHuy.setLyDo(txtLyDo.getText());
          phieuHuy.setTrangThaiXuLy(cbTrangThai.getSelectedItem().toString());
          if (PhieuHuyNguyenLieuBUS.getPhieuHuyNguyenLieuBUS().capNhatPhieuHuy(phieuHuy)) {
            parent.loadDuLieu();
            dispose();
          }
        });
  }
}
