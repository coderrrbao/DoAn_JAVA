package bus;

import dao.HangThanhVienDAO;
import dao.conection.DBConnection;
import dto.HangThanhVien;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import javax.swing.*;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class HangThanhVienBUS {
  private static HangThanhVienBUS hangThanhVienBUS = null;

  public static HangThanhVienBUS getHangThanhVienBUS() {
    if (hangThanhVienBUS == null) {
      hangThanhVienBUS = new HangThanhVienBUS();
    }
    return hangThanhVienBUS;
  }

  private HangThanhVienDAO hangThanhVienDAO = new HangThanhVienDAO();
  private ArrayList<HangThanhVien> listHangThanhVien;
  private boolean canUpdate = false;

  public HangThanhVienBUS() {
    khoitao();
  }

  public void khoitao() {
    Connection conn = DBConnection.getConnection();
    try {
      listHangThanhVien = hangThanhVienDAO.layListHangThanhVien();
    } finally {
      try {
        if (conn != null) conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public ArrayList<HangThanhVien> layListHangThanhVien() {
    if (canUpdate || listHangThanhVien == null) {
      khoitao();
      canUpdate = false;
    }
    return listHangThanhVien;
  }

  public HangThanhVien timHangThanhVien(String ma) {
    if (canUpdate || listHangThanhVien == null) {
      khoitao();
      canUpdate = false;
    }
    for (HangThanhVien htv : listHangThanhVien) {
      if (htv.getMaHang().equals(ma)) {
        return htv;
      }
    }
    return null;
  }

  public boolean themHangThanhVien(HangThanhVien htv) {
    Connection conn = DBConnection.getConnection();
    try {
      conn.setAutoCommit(false);
      if (!hangThanhVienDAO.themHangThanhVien(htv, conn)) {
        throw new SQLException();
      }
      conn.commit();
      canUpdate = true;
      return true;
    } catch (SQLException e) {
      try {
        conn.rollback();
      } catch (Exception ex) {
      }
      return false;
    } finally {
      dongKetNoi(conn);
    }
  }

  public boolean xoaHangThanhVien(String maHang) {
    Connection conn = DBConnection.getConnection();
    try {
      conn.setAutoCommit(false);
      if (!hangThanhVienDAO.xoaHangThanhVien(maHang, conn)) {
        throw new SQLException();
      }
      conn.commit();
      canUpdate = true;
      return true;
    } catch (SQLException e) {
      try {
        conn.rollback();
      } catch (Exception ex) {
      }
      return false;
    } finally {
      dongKetNoi(conn);
    }
  }

  public boolean capNhatHangThanhVien(HangThanhVien htv) {
    Connection conn = DBConnection.getConnection();
    try {
      conn.setAutoCommit(false);
      if (!hangThanhVienDAO.capNhatHangThanhVien(htv, conn)) {
        throw new SQLException();
      }
      conn.commit();
      canUpdate = true;
      return true;
    } catch (SQLException e) {
      try {
        conn.rollback();
      } catch (Exception ex) {
      }
      return false;
    } finally {
      dongKetNoi(conn);
    }
  }

  public ArrayList<HangThanhVien> timKiemHangThanhVien(String keyword) {
    ArrayList<HangThanhVien> ketQua = new ArrayList<>();
    ArrayList<HangThanhVien> dsGoc = layListHangThanhVien();

    String lowerKeyword = keyword.toLowerCase().trim();
    if (keyword == null || keyword.trim().isEmpty()) {
      return dsGoc;
    }

    // public void importExcel(List<HangThanhVien> list) throws Exception {

    //     HangThanhVienDAO dao = new HangThanhVienDAO();
    //     Connection conn = DBConnection.getConnection();

    //     int inserted = 0;

    //     try {

    //         conn.setAutoCommit(false);

    //         for (HangThanhVien h : list) {
    //             dao.insert(conn, h);
    //         }

    //         conn.commit();

    //         JOptionPane.showMessageDialog(
    //                 this,
    //                 "Import Thành công:",
    //                 "Thông báo",
    //                 JOptionPane.INFORMATION_MESSAGE);

    //         dao.layListHangThanhVien();

    //     } catch (Exception e) {

    //         try {
    //             if (conn != null)
    //                 conn.rollback();
    //         } catch (SQLException ex) {
    //             ex.printStackTrace();
    //         }

    //         JOptionPane.showMessageDialog(this,
    //                 "Import thất bại!\nCó dữ liệu trùng hoặc sai.\nĐã rollback toàn bộ.",
    //                 "Lỗi",
    //                 JOptionPane.ERROR_MESSAGE);

    //     } finally {

    //         try {
    //             if (conn != null) {
    //                 conn.setAutoCommit(true);
    //                 conn.close();
    //             }
    //         } catch (SQLException e) {
    //             e.printStackTrace();
    //         }

    //     }
    // }

    for (HangThanhVien htv : dsGoc) {
      if (htv.getMaHang().toLowerCase().contains(lowerKeyword)
          || htv.getTenHang().toLowerCase().contains(lowerKeyword)) {
        ketQua.add(htv);
      }
    }
    return ketQua;
  }

  private void dongKetNoi(Connection conn) {
    if (conn != null) {
      try {
        conn.setAutoCommit(true);
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public boolean xuatExcel(String filePath) {
    ArrayList<HangThanhVien> list = layListHangThanhVien();
    try (Workbook workbook = new XSSFWorkbook()) {
      Sheet sheet = workbook.createSheet("Hang Thanh Vien");
      String[] columns = {"Mã Hạng", "Tên Hạng", "Phần Trăm Giảm (%)", "Điều Kiện (VNĐ)"};

      Row headerRow = sheet.createRow(0);
      CellStyle headerStyle = workbook.createCellStyle();
      Font font = workbook.createFont();
      font.setBold(true);
      headerStyle.setFont(font);

      for (int i = 0; i < columns.length; i++) {
        Cell cell = headerRow.createCell(i);
        cell.setCellValue(columns[i]);
        cell.setCellStyle(headerStyle);
      }

      int rowNum = 1;
      for (HangThanhVien htv : list) {
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(htv.getMaHang());
        row.createCell(1).setCellValue(htv.getTenHang());
        row.createCell(2).setCellValue(htv.getPhanTramGiam());
        row.createCell(3).setCellValue(htv.getDieuKien());
      }

      for (int i = 0; i < columns.length; i++) sheet.autoSizeColumn(i);

      try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
        workbook.write(fileOut);
      }
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean nhapExcel(String filePath) {
    try (FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis)) {

      Sheet sheet = workbook.getSheetAt(0);
      ArrayList<HangThanhVien> dsMoi = new ArrayList<>();

      for (int i = 1; i <= sheet.getLastRowNum(); i++) {
        Row row = sheet.getRow(i);
        if (row == null) continue;

        HangThanhVien htv = new HangThanhVien();
        // Bỏ qua mã (cột 0), gán null để DAO tự tạo mã
        htv.setMaHang(null);
        htv.setTenHang(getCellValueAsString(row.getCell(1)));
        htv.setPhanTramGiam(
            row.getCell(2) != null ? (int) row.getCell(2).getNumericCellValue() : 0);
        htv.setDieuKien(row.getCell(3) != null ? row.getCell(3).getNumericCellValue() : 0);

        dsMoi.add(htv);
      }

      for (HangThanhVien htv : dsMoi) {
        this.themHangThanhVien(htv);
      }
      canUpdate = true;
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  private String getCellValueAsString(Cell cell) {
    if (cell == null) return "";
    if (cell.getCellType() == CellType.STRING) return cell.getStringCellValue().trim();
    if (cell.getCellType() == CellType.NUMERIC)
      return String.valueOf((int) cell.getNumericCellValue());
    return "";
  }
}
