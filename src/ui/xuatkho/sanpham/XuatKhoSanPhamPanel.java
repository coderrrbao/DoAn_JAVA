package ui.xuatkho.sanpham;

import bus.PhieuHuySanPhamBUS;
import dto.PhieuHuySanPham;
import java.awt.*;
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
  private JButton xuatHangBtn, xemChiTietBtn;

  public XuatKhoSanPhamPanel() {
    setLayout(new BorderLayout());
    JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 35);

    xuatHangBtn = new JButton("Tạo Phiếu Hủy");
    xemChiTietBtn = new JButton("Xem Chi Tiết");
    locNgay_Item = new LocNgay_Item(300, 30);

    TaoUI.setFixSize(xuatHangBtn, 150, 30);
    TaoUI.setFixSize(xemChiTietBtn, 150, 30);

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
              ChiTietPhieuXuatSanPhamDialog detail = new ChiTietPhieuXuatSanPhamDialog((Frame) null, selected, this);
              detail.setVisible(true);
            }
          } else {
            TaoTinNhan.showAutoCloseMessage("Vui lòng chọn phiếu để xem chi tiết", "Thông báo", 1);
          }
        });

    locNgay_Item.setEvent(() -> loadDuLieu());
    top.add(locNgay_Item);
    top.add(xuatHangBtn);
    top.add(xemChiTietBtn);
    add(top, BorderLayout.NORTH);

    model = new DefaultTableModel(
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
