package ui.hangthanhvien;

import bus.HangThanhVienBUS;
import dao.HangThanhVienDAO;
import dao.conection.DBConnection;
import dto.HangThanhVien;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import ui.component.Search_Item;
import ui.login.PhienDangNhap;
import util.ExcelUtil;
import util.TaoUI;

public class HangThanhVienUI extends JPanel {

  private JButton btnTao, btnXoa, btnSua, btnXuatExcel, btnNhapExcel;
  private Search_Item search_Item;
  private JTable tableUI;
  private DefaultTableModel model;
  private HangThanhVienBUS htvBUS = HangThanhVienBUS.getHangThanhVienBUS();

  public HangThanhVienUI() {
    setLayout(new BorderLayout());

    // --- GIAO DIỆN PHẦN TRÊN ---
    // Giữ chiều cao 45 từ HEAD để giao diện thoáng hơn
    JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 45);
    top.setBackground(Color.WHITE);
    top = TaoUI.suaBorderChoPanel(top, 0, 10, 0, 10);

    search_Item = new Search_Item(300, 32);
    btnTao = new JButton("Thêm Hạng");
    btnSua = new JButton("Sửa");
    btnXoa = new JButton("Xóa");
    btnXuatExcel = new JButton("Xuất Excel");
    btnNhapExcel = new JButton("Nhập Excel");

    // Đồng bộ kích thước nút theo HEAD (32px)
    TaoUI.setFixSize(btnTao, 120, 32);
    TaoUI.setFixSize(btnXoa, 80, 32);
    TaoUI.setFixSize(btnSua, 80, 32);
    TaoUI.setFixSize(btnXuatExcel, 100, 32);
    TaoUI.setFixSize(btnNhapExcel, 100, 32);

    top.add(search_Item);
    top.add(Box.createRigidArea(new Dimension(10, 0)));
    top.add(btnTao);
    top.add(Box.createRigidArea(new Dimension(10, 0)));
    top.add(btnSua);
    top.add(Box.createRigidArea(new Dimension(10, 0)));
    top.add(btnXoa);
    top.add(Box.createRigidArea(new Dimension(10, 0)));
    top.add(btnXuatExcel);
    top.add(Box.createRigidArea(new Dimension(10, 0)));
    top.add(btnNhapExcel);
    top.add(Box.createHorizontalGlue());

    add(top, BorderLayout.NORTH);

    // --- GIAO DIỆN BẢNG ---
    model = new DefaultTableModel() {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    model.addColumn("Mã Hạng");
    model.addColumn("Tên Hạng");
    model.addColumn("Phần Trăm Giảm (%)");
    model.addColumn("Điều Kiện (VNĐ)");

    JScrollPane scrollPane = TaoUI.taoTableScroll(model);
    tableUI = (JTable) scrollPane.getViewport().getView();

    JPanel tableContainer = new JPanel(new BorderLayout());
    tableContainer.setBackground(new Color(238, 238, 238));
    tableContainer.add(scrollPane, BorderLayout.CENTER);

    add(tableContainer, BorderLayout.CENTER);

    loadDataToTable();
    addEvents();
    suaLaiGiaoDienTheoQuyen(); // Giữ logic phân quyền từ HEAD
  }

  public void suaLaiGiaoDienTheoQuyen() {
    var listQuyen = PhienDangNhap.getListQuyen();

    if (!listQuyen.contains("HTV_TAO")) {
      btnTao.setVisible(false);
      btnNhapExcel.setVisible(false); // Thường quyền tạo đi kèm quyền nhập excel
    }
    if (!listQuyen.contains("HTV_SUA")) {
      btnSua.setVisible(false);
    }
    if (!listQuyen.contains("HTV_XOA")) {
      btnXoa.setVisible(false);
    }
  }

  public void loadDataToTable() {
    thucHienTimKiem("");
  }

        // btnXoa.addActionListener(
        //         e -> {
        //             int row = tableUI.getSelectedRow();
        //             if (row == -1) {
        //                 JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!");
        //                 return;
        //             }
        //             String maHang = model.getValueAt(row, 0).toString();
        //             int confirm = JOptionPane.showConfirmDialog(
        //                     this, "Xác nhận xóa " + maHang + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        //             if (confirm == JOptionPane.YES_OPTION) {
        //                 if (htvBUS.xoaHangThanhVien(maHang)) {
        //                     JOptionPane.showMessageDialog(this, "Đã xóa!");
        //                     loadDataToTable();
        //                 }
        //             }
        //         });

        // btnSua.addActionListener(
        //         e -> {
        //             int row = tableUI.getSelectedRow();
        //             if (row == -1) {
        //                 JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần sửa!");
        //                 return;
        //             }
        //             String maHang = model.getValueAt(row, 0).toString();
        //             HangThanhVien htvCanSua = htvBUS.timHangThanhVien(maHang);
        //             FormHangThanhVien form = new FormHangThanhVien((Frame) SwingUtilities.getWindowAncestor(this),
        //                     htvCanSua);
        //             form.setVisible(true);
        //             if (form.getKetQua() != null) {
        //                 if (htvBUS.capNhatHangThanhVien(form.getKetQua())) {
        //                     JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
        //                     loadDataToTable();
        //                 }
        //             }
        //         });

        search_Item.getSearchText().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                thucHienTimKiem(search_Item.getTextSearch());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                thucHienTimKiem(search_Item.getTextSearch());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                thucHienTimKiem(search_Item.getTextSearch());
            }
        }
  private void addEvents() {
    // Sự kiện Thêm
    btnTao.addActionListener(
        e -> {
          FormHangThanhVien form =
              new FormHangThanhVien((Frame) SwingUtilities.getWindowAncestor(this), null);
          form.setVisible(true);
          if (form.getKetQua() != null) {
            if (htvBUS.themHangThanhVien(form.getKetQua())) {
              JOptionPane.showMessageDialog(this, "Thêm thành công!");
              loadDataToTable();
            } else {
              JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            }
          }
        });

    // Sự kiện Xóa
    btnXoa.addActionListener(
        e -> {
          int row = tableUI.getSelectedRow();
          if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!");
            return;
          }
          String maHang = model.getValueAt(row, 0).toString();
          int confirm =
              JOptionPane.showConfirmDialog(
                  this, "Xác nhận xóa " + maHang + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
          if (confirm == JOptionPane.YES_OPTION) {
            if (htvBUS.xoaHangThanhVien(maHang)) {
              JOptionPane.showMessageDialog(this, "Đã xóa!");
              loadDataToTable();
            }
          }
        });

    // Sự kiện Sửa
    btnSua.addActionListener(
        e -> {
          int row = tableUI.getSelectedRow();
          if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần sửa!");
            return;
          }
          String maHang = model.getValueAt(row, 0).toString();
          HangThanhVien htvCanSua = htvBUS.timHangThanhVien(maHang);
          FormHangThanhVien form =
              new FormHangThanhVien((Frame) SwingUtilities.getWindowAncestor(this), htvCanSua);
          form.setVisible(true);
          if (form.getKetQua() != null) {
            if (htvBUS.capNhatHangThanhVien(form.getKetQua())) {
              JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
              loadDataToTable();
            }
          }
        });

          btnXuatExcel.addActionListener(e -> ExcelUtil.export(htvBUS.layListHangThanhVien(), "DanhSachHangThanhVien"));

        btnNhapExcel.addActionListener(e -> importFile());

    // Sự kiện Xuất Excel
    // btnXuatExcel.addActionListener(
    //     e -> {
    //       JFileChooser fileChooser = new JFileChooser();
    //       fileChooser.setDialogTitle("Chọn nơi lưu file");
    //       fileChooser.setSelectedFile(new java.io.File("HangThanhVien.xlsx"));
    //       if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
    //         String path = fileChooser.getSelectedFile().getAbsolutePath();
    //         if (!path.toLowerCase().endsWith(".xlsx")) {
    //           path += ".xlsx";
    //         }
    //         if (htvBUS.xuatExcel(path)) {
    //           JOptionPane.showMessageDialog(this, "Xuất Excel thành công!");
    //         }
    //       }
    //     });

    // // Sự kiện Nhập Excel
    // btnNhapExcel.addActionListener(
    //     e -> {
    //       JFileChooser fileChooser = new JFileChooser();
    //       fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files", "xlsx"));
    //       if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
    //         if (htvBUS.nhapExcel(fileChooser.getSelectedFile().getAbsolutePath())) {
    //           JOptionPane.showMessageDialog(this, "Nhập Excel thành công!");
    //           loadDataToTable();
    //         } else {
    //           JOptionPane.showMessageDialog(
    //               this, "Có lỗi xảy ra khi nhập file!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    //         }
    //       }
        });

    // Sự kiện Tìm kiếm
    search_Item
        .getSearchText()
        .getDocument()
        .addDocumentListener(
            new DocumentListener() {
              @Override
              public void insertUpdate(DocumentEvent e) {
                thucHienTimKiem(search_Item.getTextSearch());
              }

              @Override
              public void removeUpdate(DocumentEvent e) {
                thucHienTimKiem(search_Item.getTextSearch());
              }

              @Override
              public void changedUpdate(DocumentEvent e) {
                thucHienTimKiem(search_Item.getTextSearch());
              }
            });
        }
        model.fireTableDataChanged();
        tableUI.revalidate();
        tableUI.repaint();
    }

  private void importFile() {
    JFileChooser fileChooser = new JFileChooser();
    int result = fileChooser.showOpenDialog(this);

    if (result != JFileChooser.APPROVE_OPTION) {
      return;
    }

    File selectedFile = fileChooser.getSelectedFile();

    if (!selectedFile.getName().toLowerCase().endsWith(".xlsx")) {
      JOptionPane.showMessageDialog(
          this,
          "Định dạng file không hợp lệ (.xlsx)",
          "Lỗi",
          JOptionPane.ERROR_MESSAGE);
      return;
    }

    List<HangThanhVien> list;

    try {
      list = ExcelUtil.importFile(selectedFile, row -> {

        String maHang = ExcelUtil.getNullableString(row, 0);
        String tenHang = ExcelUtil.getNullableString(row, 1);
        Integer phanTram = ExcelUtil.getIntCell(row, 2);
        Double dieuKienVal = ExcelUtil.getDoubleCell(row, 3);

        int phanTramGiam = phanTram != null ? phanTram : 0;
        double dieuKien = dieuKienVal != null ? dieuKienVal : 0;

        return new HangThanhVien(
            maHang, tenHang, phanTramGiam, dieuKien);
      });

    } catch (Exception e) {
      JOptionPane.showMessageDialog(
          this,
          "Lỗi đọc file Excel!",
          "Lỗi",
          JOptionPane.ERROR_MESSAGE);
      return;
    }

    Connection conn = null;

    try {
      conn = DBConnection.getConnection();
      conn.setAutoCommit(false);

      HangThanhVienDAO dao = new HangThanhVienDAO();

      for (HangThanhVien nv : list) {
        if (!dao.exists(conn, nv.getMaHang())) {
          dao.insert(conn, nv);
        }
      }

      conn.commit();

      JOptionPane.showMessageDialog(
          this,
          "Import Thành công!",
          "Thông báo",
          JOptionPane.INFORMATION_MESSAGE);

      loadDataToTable();
    } catch (Exception e) {
      try {
        if (conn != null)
          conn.rollback();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }

      JOptionPane.showMessageDialog(this,
          "Import thất bại!\nCó dữ liệu trùng hoặc sai.\nĐã rollback toàn bộ.",
          "Lỗi",
          JOptionPane.ERROR_MESSAGE);
    } finally {
      try {
        if (conn != null) {
          conn.setAutoCommit(true);
          conn.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  private void thucHienTimKiem(String keyword) {
    ArrayList<HangThanhVien> list = htvBUS.timKiemHangThanhVien(keyword);
    model.setRowCount(0);
    for (HangThanhVien htv : list) {
      model.addRow(
          new Object[] {
              htv.getMaHang(),
              htv.getTenHang(),
              htv.getPhanTramGiam(),
              String.format("%,.0f", htv.getDieuKien()) // Định dạng tiền tệ
          });
    }
  }

  // --- Getters ---
  public JButton getBtnTao() {
    return btnTao;
  }

  public JButton getBtnXoa() {
    return btnXoa;
  }

  public JButton getBtnSua() {
    return btnSua;
  }

  public Search_Item getSearch_Item() {
    return search_Item;
  }

  public JTable getTableUI() {
    return tableUI;
  }

  public DefaultTableModel getModel() {
    return model;
  }
}
