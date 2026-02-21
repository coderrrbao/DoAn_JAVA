package ui.xuatkho.nguyenlieu;

import bus.LoNguyenLieuBUS;
import bus.NguyenLieuBUS;
import bus.PhieuHuyNguyenLieuBUS;
import dto.LoNguyenLieu;
import dto.NguyenLieu;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import util.TaoUI;

public class XuatKhoNguyenLieuDialog extends JDialog {
  private JTable tblKho, tblChoXuat;
  private DefaultTableModel modelKho, modelChoXuat;
  private JTextField txtMaNL, txtTenNL, txtSoLuong, txtMaLo, txtMaNV;
  private JButton btnThem, btnXacNhan;

  public XuatKhoNguyenLieuDialog(Frame owner) {
    super(owner, "Hủy Kho Nguyên Liệu Theo Lô", true);
    setSize(1000, 650);
    setLocationRelativeTo(owner);
    setLayout(new BorderLayout());

    JPanel main = new JPanel(new GridLayout(1, 2, 10, 0));

    // BÊN TRÁI: KHO NGUYÊN LIỆU - ĐÃ KHÓA CHỈNH SỬA Ô
    JPanel left = TaoUI.taoPanelBorderLayout(450, 600);
    modelKho =
        new DefaultTableModel(new String[] {"Mã NL", "Mã Lô", "Hạn Sử Dụng", "Tồn"}, 0) {
          @Override
          public boolean isCellEditable(int row, int column) {
            return false;
          }
        };
    tblKho = new JTable(modelKho);
    left.add(new JScrollPane(tblKho), BorderLayout.CENTER);

    // BÊN PHẢI: FORM HỦY
    JPanel right = new JPanel(new BorderLayout());
    JPanel form = TaoUI.taoPanelBoxLayoutDoc(400, 250);

    txtMaNV = new JTextField("NV001");
    txtMaNL = new JTextField();
    TaoUI.setDisabled(txtMaNL);
    txtTenNL = new JTextField();
    TaoUI.setDisabled(txtTenNL);
    txtMaLo = new JTextField();
    TaoUI.setDisabled(txtMaLo);
    txtSoLuong = new JTextField();

    form.add(new JLabel("Mã Nhân Viên:"));
    form.add(txtMaNV);
    form.add(new JLabel("Mã Nguyên liệu:"));
    form.add(txtMaNL);
    form.add(new JLabel("Mã Lô:"));
    form.add(txtMaLo);
    form.add(new JLabel("Tên Nguyên liệu:"));
    form.add(txtTenNL);
    form.add(new JLabel("Số lượng hủy:"));
    form.add(txtSoLuong);

    btnThem = new JButton("Thêm vào danh sách hủy");
    form.add(btnThem);

    // BẢNG CHỜ XUẤT - ĐÃ KHÓA CHỈNH SỬA Ô
    modelChoXuat =
        new DefaultTableModel(new String[] {"Mã NL", "Tên NL", "SL Hủy", "Mã Lô"}, 0) {
          @Override
          public boolean isCellEditable(int row, int column) {
            return false;
          }
        };
    tblChoXuat = new JTable(modelChoXuat);

    btnXacNhan = new JButton("XÁC NHẬN HỦY & TRỪ KHO");
    btnXacNhan.setBackground(new Color(0, 153, 76));
    btnXacNhan.setForeground(Color.WHITE);

    right.add(form, BorderLayout.NORTH);
    right.add(new JScrollPane(tblChoXuat), BorderLayout.CENTER);
    right.add(btnXacNhan, BorderLayout.SOUTH);

    main.add(left);
    main.add(right);
    add(main, BorderLayout.CENTER);

    ganSuKien();
    loadDataKhoNL();
  }

  private void loadDataKhoNL() {
    modelKho.setRowCount(0);
    ArrayList<LoNguyenLieu> listLo = LoNguyenLieuBUS.getLoNguyenLieuBUS().layListLoNguyenLieu();
    for (LoNguyenLieu lo : listLo) {
      if (lo.getSoLuong() > 0)
        modelKho.addRow(
            new Object[] {lo.getMaNL(), lo.getMaLoNL(), lo.getHanSuDung(), lo.getSoLuong()});
    }
  }

  private void ganSuKien() {
    tblKho
        .getSelectionModel()
        .addListSelectionListener(
            e -> {
              int row = tblKho.getSelectedRow();
              if (row != -1) {
                String maNL = modelKho.getValueAt(row, 0).toString();
                txtMaNL.setText(maNL);
                txtMaLo.setText(modelKho.getValueAt(row, 1).toString());
                NguyenLieu nl = NguyenLieuBUS.getNguyenLieuBUS().timNguyenLieu(maNL);
                txtTenNL.setText(nl != null ? nl.getTenNL() : "N/A");
              }
            });

    btnThem.addActionListener(
        e -> {
          try {
            int r = tblKho.getSelectedRow();
            double slHuy = Double.parseDouble(txtSoLuong.getText());
            double ton = Double.parseDouble(modelKho.getValueAt(r, 3).toString());
            if (slHuy <= 0 || slHuy > ton) {
              JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ!");
              return;
            }
            modelChoXuat.addRow(
                new Object[] {txtMaNL.getText(), txtTenNL.getText(), slHuy, txtMaLo.getText()});
            txtSoLuong.setText("");
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
          if (PhieuHuyNguyenLieuBUS.getPhieuHuyNguyenLieuBUS()
              .thucHienHuy(txtMaNV.getText(), "Hủy hỏng", data)) {
            JOptionPane.showMessageDialog(this, "Đã lưu phiếu và cập nhật kho thành công!");
            dispose();
          }
        });
  }
}
