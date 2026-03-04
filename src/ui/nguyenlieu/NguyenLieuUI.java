package ui.nguyenlieu;

import bus.NguyenLieuBUS;
import dto.NguyenLieu;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import ui.component.Search_Item;
import ui.login.PhienDangNhap;
import util.TaoUI;

public class NguyenLieuUI extends JPanel {
  private JButton btnTao, btnXoa, btnSua;
  private Search_Item search_Item;
  private JTable tableUI;
  private DefaultTableModel model;
  private NguyenLieuBUS nlBUS = NguyenLieuBUS.getNguyenLieuBUS();

  public NguyenLieuUI() {
    setLayout(new BorderLayout());

    // --- GIAO DIỆN PHẦN TRÊN ---
    JPanel top = TaoUI.taoPanelBoxLayoutNgang(3000, 35);
    top.setBackground(Color.WHITE);
    top = TaoUI.suaBorderChoPanel(top, 0, 10, 0, 10);

    search_Item = new Search_Item(300, 30);
    btnTao = new JButton("Thêm");
    btnSua = new JButton("Sửa");
    btnXoa = new JButton("Xóa");

    TaoUI.setFixSize(btnTao, 80, 30);
    TaoUI.setFixSize(btnXoa, 80, 30);
    TaoUI.setFixSize(btnSua, 80, 30);

    top.add(search_Item);
    top.add(Box.createRigidArea(new Dimension(10, 0)));
    top.add(btnTao);
    top.add(Box.createRigidArea(new Dimension(10, 0)));
    top.add(btnSua);
    top.add(Box.createRigidArea(new Dimension(10, 0)));
    top.add(btnXoa);
    top.add(Box.createRigidArea(new Dimension(10, 0)));
    top.add(Box.createHorizontalGlue());

    add(top, BorderLayout.NORTH);

    // --- GIAO DIỆN BẢNG ---
    model = new DefaultTableModel() {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    model.addColumn("Mã NL");
    model.addColumn("Tên nguyên liệu");
    model.addColumn("Giá nhập");
    model.addColumn("Đơn vị tính");
    model.addColumn("Mức cảnh báo");

    JScrollPane scrollPane = TaoUI.taoTableScroll(model);
    tableUI = (JTable) scrollPane.getViewport().getView();

    JPanel tableContainer = new JPanel(new BorderLayout());
    tableContainer.setBackground(new Color(238, 238, 238));
    tableContainer.add(scrollPane, BorderLayout.CENTER);

    add(tableContainer, BorderLayout.CENTER);

    loadDataToTable();
    addEvents();
  }

  public void suaLaiGiaoDienTheoQuyen() {
    HashSet<String> listQuyen = PhienDangNhap.getListQuyen();

    if (!listQuyen.contains("NL_TAO")) {
      btnTao.setVisible(false);
    }

    if (!listQuyen.contains("NL_SUA")) {
      btnSua.setVisible(false);
    }

    if (!listQuyen.contains("NL_XOA")) {
      btnXoa.setVisible(false);
    }
  }

  public void loadDataToTable() {
    thucHienTimKiem("");
  }

  private void addEvents() {
    btnTao.addActionListener(
        e -> {
          FormNguyenLieu form = new FormNguyenLieu((Frame) SwingUtilities.getWindowAncestor(this), null);
          form.setVisible(true);
          if (form.getKetQua() != null) {
            if (nlBUS.themNguyenLieu(form.getKetQua())) {
              JOptionPane.showMessageDialog(this, "Thêm thành công!");
              loadDataToTable();
            } else {
              JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            }
          }
        });

    btnXoa.addActionListener(
        e -> {
          int row = tableUI.getSelectedRow();
          if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!");
            return;
          }
          String maNL = model.getValueAt(row, 0).toString();
          int confirm = JOptionPane.showConfirmDialog(
              this, "Xác nhận xóa " + maNL + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
          if (confirm == JOptionPane.YES_OPTION) {
            if (nlBUS.xoaNguyenLieu(maNL)) {
              JOptionPane.showMessageDialog(this, "Đã xóa!");
              loadDataToTable();
            }
          }
        });

    btnSua.addActionListener(
        e -> {
          int row = tableUI.getSelectedRow();
          if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần sửa!");
            return;
          }
          String maNL = model.getValueAt(row, 0).toString();
          NguyenLieu nlCanSua = nlBUS.timNguyenLieu(maNL);
          FormNguyenLieu form = new FormNguyenLieu((Frame) SwingUtilities.getWindowAncestor(this), nlCanSua);
          form.setVisible(true);
          if (form.getKetQua() != null) {
            if (nlBUS.capNhatNguyenLieu(form.getKetQua())) {
              JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
              loadDataToTable();
            }
          }
        });

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

  private void thucHienTimKiem(String keyword) {
    ArrayList<NguyenLieu> list = nlBUS.timKiemNguyenLieu(keyword);
    model.setRowCount(0);
    for (NguyenLieu nl : list) {
      model.addRow(
          new Object[] {
              nl.getMaNL(),
              nl.getTenNL(),
              nl.getGia(),
              nl.getDonVi(),
              nl.getMucCanhBao()
          });
    }
  }

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
