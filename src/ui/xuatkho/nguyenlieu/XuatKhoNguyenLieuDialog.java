package ui.xuatkho.nguyenlieu;

import bus.LoNguyenLieuBUS;
import bus.NguyenLieuBUS;
import bus.PhieuHuyNguyenLieuBUS;
import dto.LoNguyenLieu;
import dto.NguyenLieu;
import dto.PhieuHuyNguyenLieu;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import ui.login.PhienDangNhap;
import util.TaoUI;

public class XuatKhoNguyenLieuDialog extends JDialog {
  private JTable tblKho, tblChoXuat;
  private DefaultTableModel modelKho, modelChoXuat;
  private JTextField txtMaNL, txtTenNL, txtSoLuong, txtMaLo, txtMaNV, txtLyDo;
  private JButton btnThem, btnXacNhan;
  private XuatKhoNguyenLieuPanel parentPanel;

  public XuatKhoNguyenLieuDialog(XuatKhoNguyenLieuPanel parent) {
    super((Frame) null, "Tạo Phiếu Hủy Nguyên Liệu", true);
    this.parentPanel = parent;
    setSize(1000, 680);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());

    JPanel main = new JPanel(new GridLayout(1, 2, 10, 0));
    JPanel left = TaoUI.taoPanelBorderLayout(450, 600);
    modelKho =
        new DefaultTableModel(new String[] {"Mã NL", "Mã Lô", "Hạn SD", "Tồn", "Giá Nhập"}, 0);
    tblKho = new JTable(modelKho);
    left.add(new JScrollPane(tblKho), BorderLayout.CENTER);

    JPanel right = new JPanel(new BorderLayout());
    JPanel form = TaoUI.taoPanelBoxLayoutDoc(400, 350);

    txtMaNV =
        new JTextField(PhienDangNhap.getUser() != null ? PhienDangNhap.getUser().getMaNV() : "");
    txtMaNV.setEditable(false);
    txtMaNL = new JTextField();
    txtMaNL.setEditable(false);
    txtTenNL = new JTextField();
    txtTenNL.setEditable(false);
    txtMaLo = new JTextField();
    txtMaLo.setEditable(false);
    txtSoLuong = new JTextField();
    txtLyDo = new JTextField(); // Yêu cầu: Thêm phần nhập lý do

    form.add(new JLabel("Mã Nhân Viên:"));
    form.add(txtMaNV);
    form.add(new JLabel("Mã Nguyên liệu:"));
    form.add(txtMaNL);
    form.add(new JLabel("Tên Nguyên liệu:"));
    form.add(txtTenNL);
    form.add(new JLabel("Mã Lô:"));
    form.add(txtMaLo);
    form.add(new JLabel("Số lượng hủy:"));
    form.add(txtSoLuong);
    form.add(new JLabel("Lý do hủy:"));
    form.add(txtLyDo);

    btnThem = new JButton("Thêm vào danh sách hủy");
    form.add(Box.createVerticalStrut(10));
    form.add(btnThem);

    modelChoXuat =
        new DefaultTableModel(new String[] {"Mã NL", "Tên NL", "SL Hủy", "Mã Lô", "Giá Nhập"}, 0);
    tblChoXuat = new JTable(modelChoXuat);

    btnXacNhan = new JButton("XÁC NHẬN");
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
    java.util.ArrayList<LoNguyenLieu> listLo =
        LoNguyenLieuBUS.getLoNguyenLieuBUS().layListLoNguyenLieu();
    for (LoNguyenLieu lo : listLo) {
      if (lo.getSoLuong() > 0)
        modelKho.addRow(
            new Object[] {
              lo.getMaNL(), lo.getMaLoNL(), lo.getHanSuDung(), lo.getSoLuong(), lo.getGiaNhap()
            });
    }
  }

  private void ganSuKien() {
    tblKho
        .getSelectionModel()
        .addListSelectionListener(
            e -> {
              int row = tblKho.getSelectedRow();
              if (row != -1) {
                txtMaNL.setText(modelKho.getValueAt(row, 0).toString());
                txtMaLo.setText(modelKho.getValueAt(row, 1).toString());
                NguyenLieu nl = NguyenLieuBUS.getNguyenLieuBUS().timNguyenLieu(txtMaNL.getText());
                txtTenNL.setText(nl != null ? nl.getTenNL() : "N/A");
              }
            });

    btnThem.addActionListener(
        e -> {
          try {
            int r = tblKho.getSelectedRow();
            double sl = Double.parseDouble(txtSoLuong.getText());
            if (sl <= 0 || sl > Double.parseDouble(modelKho.getValueAt(r, 3).toString())) {
              JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ!");
              return;
            }
            modelChoXuat.addRow(
                new Object[] {
                  txtMaNL.getText(),
                  txtTenNL.getText(),
                  sl,
                  txtMaLo.getText(),
                  modelKho.getValueAt(r, 4)
                });
          } catch (Exception ex) {
          }
        });

    btnXacNhan.addActionListener(
        e -> {
          if (modelChoXuat.getRowCount() == 0 || txtLyDo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập lý do và danh sách hủy!");
            return;
          }
          double tongTien = 0;
          Object[][] data = new Object[modelChoXuat.getRowCount()][5];
          for (int i = 0; i < modelChoXuat.getRowCount(); i++) {
            for (int j = 0; j < 5; j++) data[i][j] = modelChoXuat.getValueAt(i, j);
            tongTien +=
                Double.parseDouble(data[i][2].toString())
                    * Double.parseDouble(data[i][4].toString());
          }

          PhieuHuyNguyenLieu ph = new PhieuHuyNguyenLieu();
          ph.setMaNV(txtMaNV.getText());
          ph.setLyDo(txtLyDo.getText());
          ph.setTongTien(tongTien);

          if (PhieuHuyNguyenLieuBUS.getPhieuHuyNguyenLieuBUS().thucHienHuy(ph, data)) {
            parentPanel.loadDuLieu();
            dispose();
          }
        });
  }
}
