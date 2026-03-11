package ui.kiemke;

import bus.PhieuKiemKeBUS;
import dto.PhieuKiemKe;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import ui.component.LocNgay_Item;
import ui.login.PhienDangNhap;
import util.TaoTinNhan;
import util.TaoUI;

public class KiemKeUI extends JPanel {
  private JTable table;
  private DefaultTableModel model;
  private LocNgay_Item locNgay;
  private JButton btnThem;
  private JButton btnSua, btnXoa, btnXuatExcel, btnNhapExcel, btnXemCt;
  private PhieuKiemKeBUS phieuKiemKeBUS = PhieuKiemKeBUS.getPhieuKiemKeBUS();
  private ArrayList<PhieuKiemKe> listPhieuKiemKe = new ArrayList<>();

  public KiemKeUI() {
    setLayout(new BorderLayout());

    JPanel centerContainer = new JPanel(new BorderLayout());
    centerContainer.add(taoTopPanel(), BorderLayout.NORTH);
    centerContainer.add(taoPanelTable(), BorderLayout.CENTER);

    add(centerContainer, BorderLayout.CENTER);
    loaiDuLieu();
    ganSuKien();
  }

  public void suaLaiGiaoDienTheoQuyen() {
    var listQuyen = PhienDangNhap.getListQuyen();

    if (!listQuyen.contains("KK_TAO")) {
      btnThem.setVisible(false);
    }

    if (!listQuyen.contains("KK_SUA")) {
      btnSua.setVisible(false);
    }

    if (!listQuyen.contains("KK_XOA")) {
      btnXoa.setVisible(false);
    }
    this.revalidate();
    this.repaint();
  }

  private void ganSuKien() {
    btnThem.addActionListener(
        e -> {
          ThemPhieuKiemDialog them = new ThemPhieuKiemDialog(this, null);
          them.setVisible(true);
        });

    btnSua.addActionListener(
        e -> {
          int row = table.getSelectedRow();
          if (row >= 0) {
            if (model.getValueAt(row, 8).equals("Đã xác nhận")) {
              JOptionPane.showMessageDialog(
                  null,
                  "Phiểu kiểm kê đã xác nhận không thể sửa",
                  "Thông báo",
                  JOptionPane.ERROR_MESSAGE);
            } else {
              PhieuKiemKe phieuKiemKe = phieuKiemKeBUS.timPhieuKiemKe(model.getValueAt(row, 0).toString());
              ThemPhieuKiemDialog themPhieuKiemDialog = new ThemPhieuKiemDialog(this, phieuKiemKe);
              themPhieuKiemDialog.setVisible(true);
            }
          } else {
            JOptionPane.showMessageDialog(
                null, "Vui lòng chọn dòng để sửa", "Thông báo", JOptionPane.ERROR_MESSAGE);
          }
        });
    locNgay.setEvent(
        () -> {
          loaiDuLieu();
        });

    btnXemCt.addActionListener(
        e -> {
          int dongChon = table.getSelectedRow();
          if (dongChon >= 0) {
            PhieuKiemKe phieuKiemKe = phieuKiemKeBUS.timPhieuKiemKe(model.getValueAt(dongChon, 0).toString());

            ChiTietKiemKeDialog chiTietKiemKeDialog = new ChiTietKiemKeDialog(null, phieuKiemKe);
            chiTietKiemKeDialog.setVisible(true);
          } else {
            TaoTinNhan.showAutoCloseMessage("Vui lòng chọn dòng để xem chi tiết", "Thông báo", 1);
          }
        });

    btnXoa.addActionListener(
        e -> {
          int dongChon = table.getSelectedRow();
          if (dongChon >= 0) {
            if (!model.getValueAt(dongChon, 5).toString().equals("Đang xử lý")) {
              TaoTinNhan.showAutoCloseMessage(
                  "Phiếu kiểm kê đã xác nhận, không thể xóa", "Thông báo", 1);
              return;
            }
            if (phieuKiemKeBUS.xoaPhieuKiemKe(
                phieuKiemKeBUS.timPhieuKiemKe(model.getValueAt(dongChon, 0).toString()))) {
              TaoTinNhan.showAutoCloseMessage("Đã xóa phiếu kiểm kê thành thông", "Thông báo", 1);
              loaiDuLieu();
            } else {
              TaoTinNhan.showAutoCloseMessage("Đã xóa phiếu kiểm kê thất bại", "Thông báo", 1);
            }
          } else {
            TaoTinNhan.showAutoCloseMessage("Vui lòng chọn dòng để xóa", "Thông báo", 1);
          }
        });

    btnXuatExcel.addActionListener(
        e -> {
          JFileChooser fileChooser = new JFileChooser();
          fileChooser.setDialogTitle("Chọn nơi lưu Phiếu Kiểm Kê");
          fileChooser.setSelectedFile(new File("danhsachphieukiemke.xlsx"));

          if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filePath = file.getAbsolutePath();

            if (!filePath.toLowerCase().endsWith(".xlsx")) {
              filePath += ".xlsx";
            }
            if (phieuKiemKeBUS.xuatExcel(filePath)) {
              JOptionPane.showMessageDialog(
                  this, "Xuất thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
              loaiDuLieu();
            } else {
              JOptionPane.showMessageDialog(
                  this, "Lỗi khi ghi file Excel!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
          }
        });

    btnNhapExcel.addActionListener(
        e -> {

          JFileChooser fileChooser = new JFileChooser();
          fileChooser.setDialogTitle("Chọn file Excel danh sách kiểm kê");
          fileChooser.setFileFilter(
              new javax.swing.filechooser.FileNameExtensionFilter("Excel Files (.xlsx)", "xlsx"));

          if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            PhieuKiemKeBUS phieuKiemKeBUS = PhieuKiemKeBUS.getPhieuKiemKeBUS();

            boolean success = phieuKiemKeBUS.nhapExcel(file);

            if (success) {

              loaiDuLieu();

              JOptionPane.showMessageDialog(
                  this,
                  "Nhập dữ liệu từ file Excel thành công!",
                  "Thành công",
                  JOptionPane.INFORMATION_MESSAGE);
            } else {
              JOptionPane.showMessageDialog(
                  this,
                  "File trống, sai định dạng hoặc lỗi đọc file!",
                  "Lỗi",
                  JOptionPane.ERROR_MESSAGE);
            }
          }
        });
  }

  public void loaiDuLieu() {
    model.setRowCount(0);

    listPhieuKiemKe = phieuKiemKeBUS.layListKiemKe();
    for (PhieuKiemKe phieuKiemKe : listPhieuKiemKe) {
      if (locNgay.ngayTrongKhoan(phieuKiemKe.getNgayKiem())) {
        model.addRow(
            new Object[] {
                phieuKiemKe.getMaKK(),
                phieuKiemKe.getMaNV(),
                phieuKiemKe.getNgayKiem(),
                phieuKiemKe.getMaLo(),
                phieuKiemKe.getLoaiLo(),
                phieuKiemKe.getSoLuongSoSach(),
                phieuKiemKe.getSoLuongThuc(),
                phieuKiemKe.getSoLuongThuc() - phieuKiemKe.getSoLuongSoSach(),
                phieuKiemKe.getTrangThaiXuLy()
            });
      }
    }
  }

  private JPanel taoTopPanel() {
    JPanel top = new JPanel();
    top.setPreferredSize(new Dimension(100, 45));
    top.setLayout(new FlowLayout(FlowLayout.LEFT));
    top.setBackground(Color.WHITE);

    locNgay = new LocNgay_Item(400, 32);
    top.add(locNgay);

    btnThem = new JButton("Thêm");
    btnThem.setPreferredSize(new Dimension(80, 32));
    top.add(btnThem);

    btnSua = new JButton("Sửa");
    btnSua.setPreferredSize(new Dimension(btnSua.getPreferredSize().width, 32));
    top.add(btnSua);

    btnXoa = new JButton("Xóa");
    btnXoa.setPreferredSize(new Dimension(80, 32));
    top.add(btnXoa);

    btnNhapExcel = new JButton("Nhập Excel");
    btnNhapExcel.setPreferredSize(new Dimension(100, 32));
    top.add(btnNhapExcel);

    btnXuatExcel = new JButton("Xuất Excel");
    btnXuatExcel.setPreferredSize(new Dimension(100, 32));
    top.add(btnXuatExcel);
    btnXemCt = new JButton("Xem chi tiết");
    btnXemCt.setPreferredSize(new Dimension(100, 32));
    top.add(btnXemCt);

    return top;
  }

  private JPanel taoPanelTable() {
    JPanel panel = new JPanel(new BorderLayout());
    String[] columns = {
        "Mã Phiếu Kiểm",
        "Mã NV",
        "Ngày kiểm",
        "Mã lô",
        "Loại lô",
        "SL sổ sách",
        "SL thực tế",
        "Chênh lệch",
        "Trạng thái"
    };
    model = new DefaultTableModel(columns, 0);
    JScrollPane scrollPane = TaoUI.taoTableScroll(model);
    table = (JTable) scrollPane.getViewport().getView();
    panel.add(scrollPane, BorderLayout.CENTER);

    return panel;
  }
}
