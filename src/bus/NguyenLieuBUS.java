package bus;

import dao.NguyenLieuDAO;
import dao.conection.DBConnection;
import dto.NguyenLieu;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class NguyenLieuBUS {
  private static NguyenLieuBUS nguyenLieuBUS = null;

  public static NguyenLieuBUS getNguyenLieuBUS() {
    if (nguyenLieuBUS == null) {
      nguyenLieuBUS = new NguyenLieuBUS();
    }
    return nguyenLieuBUS;
  }

  private NguyenLieuDAO nguyenLieuDAO = new NguyenLieuDAO();
  private ArrayList<NguyenLieu> listNguyenLieu;
  private boolean canUpdate = false;

  public NguyenLieuBUS() {
    khoitao();
  }

  public void khoitao() {

    try(Connection conn = DBConnection.getConnection()) {
      listNguyenLieu = nguyenLieuDAO.layListNguyenLieu(conn);
    }catch (SQLException e){
      e.printStackTrace();
    }
  }

  public boolean suaCanhBao(NguyenLieu nguyenLieu) {
    return nguyenLieuDAO.capNhatMucCanhBao(nguyenLieu);
  }

  public ArrayList<NguyenLieu> layListNguyenLieu() {
    if (canUpdate || listNguyenLieu == null) {
      khoitao();
      canUpdate = false;
    }
    return listNguyenLieu;
  }

  public NguyenLieu timNguyenLieu(String ma) {
    if (canUpdate || listNguyenLieu == null) {
      khoitao();
      canUpdate = false;
    }
    for (NguyenLieu nl : listNguyenLieu) {
      if (nl.getMaNL().equals(ma)) {
        return nl;
      }
    }
    return null;
  }

  public boolean themNguyenLieu(NguyenLieu nl) {
    Connection conn = DBConnection.getConnection();
    try {
      conn.setAutoCommit(false);
      if (!nguyenLieuDAO.themNguyenLieu(nl, conn)) {
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

  public boolean xoaNguyenLieu(String maNL) {
    Connection conn = DBConnection.getConnection();
    try {
      conn.setAutoCommit(false);
      if (!nguyenLieuDAO.xoaNguyenLieu(maNL, conn)) {
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

  public boolean capNhatNguyenLieu(NguyenLieu nl) {
    Connection conn = DBConnection.getConnection();
    try {
      conn.setAutoCommit(false);
      if (!nguyenLieuDAO.capNhatNguyenLieu(nl, conn)) {
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

  public ArrayList<NguyenLieu> timKiemNguyenLieu(String keyword) {
    ArrayList<NguyenLieu> ketQua = new ArrayList<>();
    ArrayList<NguyenLieu> dsGốc = layListNguyenLieu();

    String lowerKeyword = keyword.toLowerCase().trim();
    if (keyword == null || keyword.trim().isEmpty()) {
      return dsGốc;
    }
    for (NguyenLieu nl : dsGốc) {
      if (nl.getMaNL().toLowerCase().contains(lowerKeyword)
          || nl.getTenNL().toLowerCase().contains(lowerKeyword)) {
        ketQua.add(nl);
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
    ArrayList<NguyenLieu> dsNguyenLieu = layListNguyenLieu();
    try (Workbook workbook = new XSSFWorkbook()) {
      Sheet sheet = workbook.createSheet("Nguyen Lieu");
      String[] columns = {"Mã NL", "Tên Nguyên Liệu", "Giá Nhập", "Đơn Vị", "Mức Cảnh Báo"};
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
      for (NguyenLieu nl : dsNguyenLieu) {
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(nl.getMaNL()); //
        row.createCell(1).setCellValue(nl.getTenNL()); //
        row.createCell(2).setCellValue(nl.getGia()); //
        row.createCell(3).setCellValue(nl.getDonVi()); //
        row.createCell(4).setCellValue(nl.getMucCanhBao()); //
      }

      for (int i = 0; i < columns.length; i++) {
        sheet.autoSizeColumn(i);
      }

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
      ArrayList<NguyenLieu> dsMoi = new ArrayList<>();
      for (int i = 1; i <= sheet.getLastRowNum(); i++) {
        Row row = sheet.getRow(i);
        if (row == null) continue;

        NguyenLieu nl = new NguyenLieu();

        // BỎ QUA MÃ: Gán null để NguyenLieuDAO tự động sinh mã mới
        nl.setMaNL(null);

        nl.setTenNL(getCellValueAsString(row.getCell(1)));
        nl.setGia(row.getCell(2).getNumericCellValue());
        nl.setDonVi(getCellValueAsString(row.getCell(3)));
        nl.setMucCanhBao((int) row.getCell(4).getNumericCellValue());

        dsMoi.add(nl);
      }

      for (NguyenLieu nl : dsMoi) {
        this.themNguyenLieu(nl);
      }

      canUpdate = true;
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  private String getCellValueAsString(Cell cell) {
    if (cell == null) {
      return "";
    }
    switch (cell.getCellType()) {
      case STRING:
        return cell.getStringCellValue().trim();
      case NUMERIC:
        return String.valueOf((int) cell.getNumericCellValue());
      case BOOLEAN:
        return String.valueOf(cell.getBooleanCellValue());
      case FORMULA:
        return cell.getCellFormula();
      default:
        return "";
    }
  }
}
