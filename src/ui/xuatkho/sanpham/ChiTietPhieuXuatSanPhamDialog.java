package ui.xuatkho.sanpham;

import bus.PhieuHuySanPhamBUS;
import dto.ChiTietPhieuHuySanPham;
import dto.LoSanPham;
import dto.PhieuHuySanPham;
import dto.SanPham;
import ui.login.LoginUI;
import util.TaoUI;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ChiTietPhieuXuatSanPhamDialog extends JDialog {
  private JTable tblChiTiet;
  private DefaultTableModel modelChiTiet;
  // Thêm txtMaNVXacNhan
  private JTextField txtMaPH, txtNgay, txtNV, txtLyDo, txtTong, txtMaNVXacNhan;
  private JComboBox<String> cbTrangThai;
  private JButton btnLuu, btnSua;
  private PhieuHuySanPham phieuHuy;
  private XuatKhoSanPhamPanel parent;

  public ChiTietPhieuXuatSanPhamDialog(Frame owner, PhieuHuySanPham ph, XuatKhoSanPhamPanel parent) {
    super(owner, "Chi Tiết Phiếu Hủy Sản Phẩm", true);
    this.phieuHuy = ph;
    this.parent = parent;
    // Tăng chiều cao để chứa thêm field NV Xác Nhận
    setSize(480, 700);
    setLocationRelativeTo(owner);
    setLayout(new BorderLayout(10, 10));

    JPanel pnTop = new JPanel(new BorderLayout(5, 10));
    pnTop.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));

    modelChiTiet = new DefaultTableModel(new String[] { "Mã Lô", "Tên SP", "Số lượng", "Giá" }, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    JScrollPane scrollChiTiet = TaoUI.taoTableScroll(modelChiTiet);
    tblChiTiet = (JTable) scrollChiTiet.getViewport().getView();
    tblChiTiet.getTableHeader().setReorderingAllowed(false);
    scrollChiTiet.setPreferredSize(new Dimension(400, 150));

    pnTop.add(scrollChiTiet, BorderLayout.CENTER);
    add(pnTop, BorderLayout.NORTH);

    JPanel pnForm = new JPanel();
    pnForm.setLayout(new BoxLayout(pnForm, BoxLayout.Y_AXIS));
    pnForm.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

    txtMaPH = new JTextField(ph.getMaPH());
    txtNgay = new JTextField(ph.getNgayHuy().toString());
    txtNV = new JTextField(ph.getMaNV());
    txtLyDo = new JTextField(ph.getLyDo());
    txtTong = new JTextField(String.format("%,.0f VNĐ", ph.getTongGiaTri()));
    // Khởi tạo txtMaNVXacNhan với dữ liệu cũ (nếu có)
    txtMaNVXacNhan = new JTextField(ph.getMaNVXacNhan() != null ? ph.getMaNVXacNhan() : "");

    cbTrangThai = new JComboBox<>(new String[] { "Đang xử lý", "Đã xác nhận" });
    cbTrangThai.setSelectedItem(ph.getTrangThaiXuLy());

    // Đưa txtMaNVXacNhan vào mảng để thiết lập thuộc tính hàng loạt
    JTextField[] fields = { txtMaPH, txtNgay, txtNV, txtLyDo, txtTong, txtMaNVXacNhan };
    for (JTextField f : fields) {
      f.setEditable(false);
      f.setBackground(Color.WHITE);
      f.setFocusable(false);
    }
    cbTrangThai.setEnabled(false);

    pnForm.add(taoDong(new JLabel("Mã Phiếu:")));
    pnForm.add(taoDong(txtMaPH));

    pnForm.add(taoDong(new JLabel("Ngày Hủy:")));
    pnForm.add(taoDong(txtNgay));

    pnForm.add(taoDong(new JLabel("Nhân Viên Lập:")));
    pnForm.add(taoDong(txtNV));

    pnForm.add(taoDong(new JLabel("Lý Do:")));
    pnForm.add(taoDong(txtLyDo));

    pnForm.add(taoDong(new JLabel("Tổng Tiền:")));
    pnForm.add(taoDong(txtTong));

    pnForm.add(taoDong(new JLabel("Trạng Thái:")));
    pnForm.add(taoDong(cbTrangThai));

    // Thêm dòng NV Xác nhận vào Form
    pnForm.add(taoDong(new JLabel("Mã Nhân Viên Xác Nhận:")));
    pnForm.add(taoDong(txtMaNVXacNhan));

    add(pnForm, BorderLayout.CENTER);

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

    if (!listQuyen.contains("XK_SUA")) {
      btnSua.setVisible(false);
      btnLuu.setVisible(false);
      this.setTitle("Chi Tiết Phiếu Hủy Sản Phẩm (Chế độ chỉ đọc)");
    }
    this.revalidate();
    this.repaint();
  }

  private void loadData() {
    modelChiTiet.setRowCount(0);
    ArrayList<ChiTietPhieuHuySanPham> list = phieuHuy.getListChiTiet();
    if (list != null) {
      for (ChiTietPhieuHuySanPham ct : list) {
        LoSanPham lo = ct.getLoSanPham();
        SanPham sp = bus.SanPhamBUS.getSanPhamBUS().timSanPham(lo.getMaSP());
        modelChiTiet.addRow(
            new Object[] {
                lo.getMaLoSP(),
                (sp != null ? sp.getTenSP() : "N/A"),
                ct.getSoLuong(),
                ct.getDonGia()
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
      String trangThaiMoi = cbTrangThai.getSelectedItem().toString();
      String trangThaiCu = phieuHuy.getTrangThaiXuLy();

      // Nếu đổi sang "Đã xác nhận" thì gán Mã NV Xác Nhận bằng user đang đăng nhập
      if ("Đang xử lý".equals(trangThaiCu) && "Đã xác nhận".equals(trangThaiMoi)) {
        int luaChon = JOptionPane.showConfirmDialog(this,
            "Sau khi xác nhận, số lượng hàng sẽ được trừ vào kho và không thể sửa.",
            "Xác trừ kho", JOptionPane.YES_NO_OPTION);
        if (luaChon == JOptionPane.NO_OPTION) {
          return;
        }
        if (ui.login.PhienDangNhap.getUser() != null) {
          txtMaNVXacNhan.setText(ui.login.PhienDangNhap.getUser().getMaNV());
        }
      }

      phieuHuy.setLyDo(txtLyDo.getText());
      phieuHuy.setTrangThaiXuLy(trangThaiMoi);
      phieuHuy.setMaNVXacNhan(txtMaNVXacNhan.getText()); // Cập nhật vào DTO

      if (PhieuHuySanPhamBUS.getPhieuHuySanPhamBUS().capNhatPhieuHuy(phieuHuy)) {
        JOptionPane.showMessageDialog(this, "Cập nhật phiếu hủy thành công!", "Thông báo",
            JOptionPane.INFORMATION_MESSAGE);
        LoginUI.getLoginUI().getMainFrame().loadAllData();
        dispose();
      } else {
        // Thông báo thất bại
        JOptionPane.showMessageDialog(this, "Cập nhật thất bại. Vui lòng kiểm tra lại!", "Lỗi",
            JOptionPane.ERROR_MESSAGE);
      }
    });
  }
}