package ui.xuatkho.sanpham;

import bus.PhieuHuySanPhamBUS;
import dto.PhieuHuySanPham;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import ui.component.LocNgay_Item; // Thêm bộ lọc ngày cho đồng bộ
import ui.login.PhienDangNhap;
import util.TaoTinNhan;
import util.TaoUI;

public class XuatKhoSanPhamPanel extends JPanel {
  private JTable table;
  private DefaultTableModel model;
  private LocNgay_Item locNgay_Item;
  private JButton xuatHangBtn, xemChiTietBtn, xuatExcelBtn, nhapExcelBtn;

  public XuatKhoSanPhamPanel() {
    setLayout(new BorderLayout());
    JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 45);
    top.setBackground(Color.WHITE);

    xuatHangBtn = new JButton("Tạo Phiếu Hủy");
    xemChiTietBtn = new JButton("Xem Chi Tiết");
    xuatExcelBtn = new JButton("Xuất Excel");
    nhapExcelBtn = new JButton("Nhập Excel");
    locNgay_Item = new LocNgay_Item(400, 32);

    TaoUI.setFixSize(xuatHangBtn, 150, 32);
    TaoUI.setFixSize(xemChiTietBtn, 150, 32);
    TaoUI.setFixSize(xuatExcelBtn, 150, 32);
    TaoUI.setFixSize(nhapExcelBtn, 150, 32);
    xuatHangBtn.addActionListener(
        e -> {
          XuatKhoSanPhamDialog dialog = new XuatKhoSanPhamDialog(this);
          dialog.setVisible(true);
        });

    xemChiTietBtn.addActionListener(
        e -> {
          int row = table.getSelectedRow();
          if (row != -1) {
            String maPH = model.getValueAt(row, 0).toString();
            PhieuHuySanPham selected = null;
            for (PhieuHuySanPham p : PhieuHuySanPhamBUS.getPhieuHuySanPhamBUS().layListPhieuHuy()) {
              if (p.getMaPH().equals(maPH)) {
                selected = p;
                break;
              }
            }
            if (selected != null) {
              ChiTietPhieuXuatSanPhamDialog detail =
                  new ChiTietPhieuXuatSanPhamDialog((Frame) null, selected, this);
              detail.setVisible(true);
            }
          } else {
            TaoTinNhan.showAutoCloseMessage("Vui lòng chọn phiếu để xem chi tiết", "Thông báo", 1);
          }
        });
    xuatExcelBtn.addActionListener(
        e -> {
          JFileChooser fileChooser = new JFileChooser();
          fileChooser.setDialogTitle("Lưu file Excel Phiếu Hủy Sản Phẩm");
          fileChooser.setSelectedFile(new File("PhieuHuySanPham.xlsx"));

          int userSelection = fileChooser.showSaveDialog(this);
          if (userSelection == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            if (!path.toLowerCase().endsWith(".xlsx")) {
              path += ".xlsx";
            }
            if (PhieuHuySanPhamBUS.getPhieuHuySanPhamBUS().xuatExcel(path)) {
              JOptionPane.showMessageDialog(this, "Xuất dữ liệu Excel thành công!");
            } else {
              JOptionPane.showMessageDialog(
                  this, "Lỗi khi xuất file Excel!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
          }
        });
    nhapExcelBtn.addActionListener(
        e -> {
          JFileChooser fileChooser = new JFileChooser();
          fileChooser.setFileFilter(
              new javax.swing.filechooser.FileNameExtensionFilter("Excel Files", "xlsx"));

          if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            if (PhieuHuySanPhamBUS.getPhieuHuySanPhamBUS().nhapExcel(path)) {
              JOptionPane.showMessageDialog(this, "Nhập dữ liệu Excel thành công!");
              loadDuLieu(); // Cập nhật lại bảng hiển thị
            } else {
              JOptionPane.showMessageDialog(
                  this, "Lỗi khi nhập dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
          }
        });
    locNgay_Item.setEvent(() -> loadDuLieu());
    top.add(locNgay_Item);
    top.add(Box.createRigidArea(new Dimension(10, 0)));
    top.add(xuatHangBtn);
    top.add(Box.createRigidArea(new Dimension(10, 0)));
    top.add(xemChiTietBtn);
    top.add(Box.createRigidArea(new Dimension(10, 0)));
    top.add(xuatExcelBtn);
    top.add(Box.createRigidArea(new Dimension(10, 0)));
    top.add(nhapExcelBtn);
    top.add(Box.createHorizontalGlue());
    add(top, BorderLayout.NORTH);

    model =
        new DefaultTableModel(
            new String[] {
              "Mã Phiếu", "Ngày Hủy", "Nhân Viên", "Lý Do", "Tổng Giá Trị", "Trạng Thái"
            },
            0);
    JScrollPane scrollPane = TaoUI.taoTableScroll(model);
    table = (JTable) scrollPane.getViewport().getView();

    add(scrollPane, BorderLayout.CENTER);
    loadDuLieu();
  }

  public void suaLaiGiaoDienTheoQuyen() {
    var listQuyen = PhienDangNhap.getListQuyen();

    if (!listQuyen.contains("NK_TAO")) {
      xuatHangBtn.setVisible(false);
    }
    this.revalidate();
    this.repaint();
  }

  public void loadDuLieu() {
    model.setRowCount(0);
    ArrayList<PhieuHuySanPham> list = PhieuHuySanPhamBUS.getPhieuHuySanPhamBUS().layListPhieuHuy();
    for (PhieuHuySanPham ph : list) {
      if (locNgay_Item.ngayTrongKhoan(ph.getNgayHuy().toString())) {
        model.addRow(
            new Object[] {
              ph.getMaPH(),
              ph.getNgayHuy(),
              ph.getMaNV(),
              ph.getLyDo(),
              String.format("%,.0f VNĐ", ph.getTongGiaTri()),
              ph.getTrangThaiXuLy()
            });
      }
    }
  }
}
