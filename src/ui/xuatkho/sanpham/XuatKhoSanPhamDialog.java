package ui.xuatkho.sanpham;

import bus.LoSanPhamBUS;
import bus.PhieuHuySanPhamBUS;
import bus.SanPhamBUS;
import dto.LoSanPham;
import dto.SanPham;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import util.TaoUI;

public class XuatKhoSanPhamDialog extends JDialog {
  private JTable tblTonKho, tblChoXuat;
  private DefaultTableModel modelTonKho, modelChoXuat;
  private JTextField txtMaSP, txtSoLuongXuat, txtMaLo, txtMaNV;
  private JButton btnThem, btnXacNhan;

  public XuatKhoSanPhamDialog(Frame owner) {
    super(owner, "Hủy Sản Phẩm Theo Lô", true);
    setSize(1000, 600);
    setLocationRelativeTo(owner);
    setLayout(new BorderLayout());

    JPanel main = new JPanel(new GridLayout(1, 2, 10, 0));

    // BÊN TRÁI: KHO SẢN PHẨM - ĐÃ KHÓA CHỈNH SỬA Ô
    JPanel left = TaoUI.taoPanelBorderLayout(450, 600);
    modelTonKho =
        new DefaultTableModel(new String[] {"Mã SP", "Mã Lô", "Hạn Sử Dụng", "Tồn"}, 0) {
          @Override
          public boolean isCellEditable(int row, int column) {
            return false; // Không cho phép sửa trực tiếp trên table
          }
        };
    tblTonKho = new JTable(modelTonKho);
    left.add(new JScrollPane(tblTonKho), BorderLayout.CENTER);

    // BÊN PHẢI: FORM HỦY
    JPanel right = new JPanel(new BorderLayout());
    JPanel form = TaoUI.taoPanelBoxLayoutDoc(400, 200);

    txtMaNV = new JTextField("NV001"); // Mã NV mặc định
    txtMaSP = new JTextField();
    TaoUI.setDisabled(txtMaSP);
    txtMaLo = new JTextField();
    TaoUI.setDisabled(txtMaLo);
    txtSoLuongXuat = new JTextField();

    form.add(new JLabel("Mã Nhân Viên:"));
    form.add(txtMaNV);
    form.add(new JLabel("Mã Sản Phẩm:"));
    form.add(txtMaSP);
    form.add(new JLabel("Mã Lô:"));
    form.add(txtMaLo);
    form.add(new JLabel("Số lượng hủy:"));
    form.add(txtSoLuongXuat);

    btnThem = new JButton("Thêm vào danh sách chờ");
    form.add(btnThem);

    // BẢNG CHỜ XUẤT - ĐÃ KHÓA CHỈNH SỬA Ô
    modelChoXuat =
        new DefaultTableModel(new String[] {"Mã SP", "Tên SP", "SL Hủy", "Mã Lô"}, 0) {
          @Override
          public boolean isCellEditable(int row, int column) {
            return false;
          }
        };
    tblChoXuat = new JTable(modelChoXuat);

    btnXacNhan = new JButton("XÁC NHẬN HỦY & TRỪ KHO");
    btnXacNhan.setBackground(Color.RED);
    btnXacNhan.setForeground(Color.WHITE);

    right.add(form, BorderLayout.NORTH);
    right.add(new JScrollPane(tblChoXuat), BorderLayout.CENTER);
    right.add(btnXacNhan, BorderLayout.SOUTH);

    main.add(left);
    main.add(right);
    add(main, BorderLayout.CENTER);

    loadData();
    ganSuKien();
  }

  private void loadData() {
    modelTonKho.setRowCount(0);
    ArrayList<LoSanPham> list = LoSanPhamBUS.getLoSanPhamBUS().layListLoSanPham();
    for (LoSanPham lo : list) {
      if (lo.getSoLuong() > 0)
        modelTonKho.addRow(
            new Object[] {lo.getMaSP(), lo.getMaLoSP(), lo.getHanSuDung(), lo.getSoLuong()});
    }
  }

  private void ganSuKien() {
    tblTonKho
        .getSelectionModel()
        .addListSelectionListener(
            e -> {
              int r = tblTonKho.getSelectedRow();
              if (r != -1) {
                txtMaSP.setText(modelTonKho.getValueAt(r, 0).toString());
                txtMaLo.setText(modelTonKho.getValueAt(r, 1).toString());
              }
            });

    btnThem.addActionListener(
        e -> {
          try {
            int r = tblTonKho.getSelectedRow();
            if (r == -1) return;
            int sl = Integer.parseInt(txtSoLuongXuat.getText());
            int ton = Integer.parseInt(modelTonKho.getValueAt(r, 3).toString());

            if (sl <= 0 || sl > ton) {
              JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ!");
              return;
            }

            SanPham sp = SanPhamBUS.getSanPhamBUS().timSanPham(txtMaSP.getText());
            modelChoXuat.addRow(
                new Object[] {
                  txtMaSP.getText(), (sp != null ? sp.getTenSP() : "SP"), sl, txtMaLo.getText()
                });
            txtSoLuongXuat.setText("");
          } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Nhập số lượng hợp lệ!");
          }
        });

    btnXacNhan.addActionListener(
        e -> {
          int rowCount = modelChoXuat.getRowCount();
          if (rowCount == 0) return;
          Object[][] data = new Object[rowCount][4];
          for (int i = 0; i < rowCount; i++) {
            data[i][0] = modelChoXuat.getValueAt(i, 0);
            data[i][2] = modelChoXuat.getValueAt(i, 2);
            data[i][3] = modelChoXuat.getValueAt(i, 3);
          }
          if (PhieuHuySanPhamBUS.getPhieuHuySanPhamBUS()
              .thucHienHuy(txtMaNV.getText(), "Hủy hỏng", data)) {
            JOptionPane.showMessageDialog(this, "Đã lưu phiếu và trừ tồn kho thành công!");
            dispose();
          }
        });
  }
}
