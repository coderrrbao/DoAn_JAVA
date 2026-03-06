package ui.xuatkho.nguyenlieu;

import bus.PhieuHuyNguyenLieuBUS;
import dto.PhieuHuyNguyenLieu;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import ui.component.LocNgay_Item;
import ui.login.PhienDangNhap;
import util.TaoTinNhan;
import util.TaoUI;

public class XuatKhoNguyenLieuPanel extends JPanel {
  private JTable table;
  private DefaultTableModel model;
  private LocNgay_Item locNgay_Item;
  private JButton btnXuat, btnXemChiTiet;

  public XuatKhoNguyenLieuPanel() {
    setLayout(new BorderLayout());
    JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 45);
    top.setBackground(Color.WHITE);

    btnXuat = new JButton("Xuất nguyên liệu");
    btnXemChiTiet = new JButton("Xem Chi tiết");
    locNgay_Item = new LocNgay_Item(400, 32);

    TaoUI.setFixSize(btnXuat, 150, 32);
    TaoUI.setFixSize(btnXemChiTiet, 150, 32);

    btnXuat.addActionListener(
        e -> {
          XuatKhoNguyenLieuDialog dialog = new XuatKhoNguyenLieuDialog(this);
          dialog.setVisible(true);
        });

    btnXemChiTiet.addActionListener(
        e -> {
          int row = table.getSelectedRow();
          if (row != -1) {
            String maPH = model.getValueAt(row, 0).toString();
            PhieuHuyNguyenLieu selected = null;
            for (PhieuHuyNguyenLieu p : PhieuHuyNguyenLieuBUS.getPhieuHuyNguyenLieuBUS().layListPhieuHuy()) {
              if (p.getMaPH().equals(maPH)) {
                selected = p;
                break;
              }
            }
            if (selected != null) {
              // Đã sửa: Truyền đủ 3 tham số (Frame, DTO, Panel)
              ChiTietPhieuXuatNguyenLieuDialog detail = new ChiTietPhieuXuatNguyenLieuDialog((Frame) null, selected,
                  this);
              detail.setVisible(true);
            }
          } else {
            TaoTinNhan.showAutoCloseMessage("Vui lòng chọn phiếu để xem", "Thông báo", 1);
          }
        });

    locNgay_Item.setEvent(() -> loadDuLieu());
    top.add(locNgay_Item);
    top.add(Box.createRigidArea(new Dimension(10, 0)));
    top.add(btnXuat);
    top.add(Box.createRigidArea(new Dimension(10, 0)));
    top.add(btnXemChiTiet);
    top.add(Box.createHorizontalGlue());
    add(top, BorderLayout.NORTH);

    model = new DefaultTableModel(
        new String[] { "Mã phiếu", "Ngày xuất", "Nhân viên", "Lý do", "Tổng tiền", "Trạng thái" },
        0);
    JScrollPane scrollPane = TaoUI.taoTableScroll(model);
    table = (JTable) scrollPane.getViewport().getView();
    add(scrollPane, BorderLayout.CENTER);
    loadDuLieu();
  }

  public void suaLaiGiaoDienTheoQuyen() {
    var listQuyen = PhienDangNhap.getListQuyen();

    // Kiểm tra quyền tạo phiếu xuất/hủy nguyên liệu (XK_TAO)
    if (!listQuyen.contains("XK_TAO")) {
      btnXuat.setVisible(false);
      // Nút btnXemChiTiet vẫn để mặc định là true (hiển thị)
    }
  }

  public void loadDuLieu() {
    model.setRowCount(0);
    ArrayList<PhieuHuyNguyenLieu> list = PhieuHuyNguyenLieuBUS.getPhieuHuyNguyenLieuBUS().layListPhieuHuy();
    for (PhieuHuyNguyenLieu ph : list) {
      if (locNgay_Item.ngayTrongKhoan(ph.getNgayHuy().toString())) {
        model.addRow(
            new Object[] {
                ph.getMaPH(),
                ph.getNgayHuy(),
                ph.getMaNV(),
                ph.getLyDo(),
                String.format("%,.0f VNĐ", ph.getTongTien()),
                ph.getTrangThaiXuLy()
            });
      }
    }
  }
}
