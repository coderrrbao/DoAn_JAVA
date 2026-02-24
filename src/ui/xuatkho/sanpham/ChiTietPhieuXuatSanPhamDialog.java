package ui.xuatkho.sanpham;

import bus.PhieuHuySanPhamBUS;
import bus.SanPhamBUS;
import dto.LoSanPham;
import dto.PhieuHuySanPham;
import dto.SanPham;
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

  public ChiTietPhieuXuatSanPhamDialog(
      Frame owner, PhieuHuySanPham ph, XuatKhoSanPhamPanel parent) {
    super(owner, "Chi Tiết Phiếu Hủy Sản Phẩm", true);
    this.phieuHuy = ph;
    this.parent = parent;
    setSize(600, 650);
    setLocationRelativeTo(owner);
    setLayout(new BorderLayout(10, 10));

    // CHỈNH SỬA 1: Ngăn chỉnh sửa các ô trong Table
    modelChiTiet =
        new DefaultTableModel(new String[] {"Mã Lô", "Tên SP", "Số lượng", "Giá"}, 0) {
          @Override
          public boolean isCellEditable(int row, int column) {
            return false;
          }
        };

    tblChiTiet = new JTable(modelChiTiet);
    tblChiTiet.getTableHeader().setReorderingAllowed(false); // Ngăn kéo cột
    add(new JScrollPane(tblChiTiet), BorderLayout.NORTH);
    tblChiTiet.setPreferredScrollableViewportSize(new Dimension(500, 150));

    JPanel pnForm = new JPanel();
    pnForm.setLayout(new BoxLayout(pnForm, BoxLayout.Y_AXIS));
    pnForm.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

    txtMaPH = new JTextField(ph.getMaPH());
    txtNgay = new JTextField(ph.getNgayHuy().toString());
    txtNV = new JTextField(ph.getMaNV());
    txtLyDo = new JTextField(ph.getLyDo());
    txtTong = new JTextField(String.format("%,.0f VNĐ", ph.getTongGiaTri()));
    cbTrangThai = new JComboBox<>(new String[] {"Đang xử lý", "Đã xác nhận"});
    cbTrangThai.setSelectedItem(ph.getTrangThaiXuLy());

    // CHỈNH SỬA 2: Chặn hoàn toàn việc click và hiện con trỏ chuột
    JTextField[] fields = {txtMaPH, txtNgay, txtNV, txtLyDo, txtTong};
    for (JTextField f : fields) {
      f.setEditable(false);
      f.setBackground(Color.WHITE);
      f.setFocusable(false); // Ngăn hiện con trỏ nhấp nháy
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
    if (ph.getTrangThaiXuLy().equals("Đã xác nhận")) btnSua.setEnabled(false);
    pnBtn.add(btnSua);
    pnBtn.add(btnLuu);
    add(pnBtn, BorderLayout.SOUTH);

    loadData();
    ganSuKien();
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
    btnSua.addActionListener(
        e -> {
          // CHỈNH SỬA 3: Mở lại focus cho ô Lý do khi cần sửa
          txtLyDo.setEditable(true);
          txtLyDo.setFocusable(true);
          txtLyDo.requestFocus(); // Nhảy con trỏ vào ô lý do luôn
          cbTrangThai.setEnabled(true);
          btnLuu.setEnabled(true);
          btnSua.setEnabled(false);
        });

    btnLuu.addActionListener(
        e -> {
          phieuHuy.setLyDo(txtLyDo.getText());
          phieuHuy.setTrangThaiXuLy(cbTrangThai.getSelectedItem().toString());
          if (PhieuHuySanPhamBUS.getPhieuHuySanPhamBUS().capNhatPhieuHuy(phieuHuy)) {
            parent.loadDuLieu();
            dispose();
          }
        });
  }
}
