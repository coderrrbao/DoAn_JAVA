package bus;

import dao.PhieuHuySanPhamDAO;
import dao.conection.DBConnection;
import dto.LoSanPham;
import dto.PhieuHuySanPham;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PhieuHuySanPhamBUS {
  private static PhieuHuySanPhamBUS instance;
  private PhieuHuySanPhamDAO dao = new PhieuHuySanPhamDAO();
  private ArrayList<PhieuHuySanPham> listPhieuHuy = null;
  private boolean canUpdate = true;

  public static PhieuHuySanPhamBUS getPhieuHuySanPhamBUS() {
    if (instance == null) instance = new PhieuHuySanPhamBUS();
    return instance;
  }

  public void khoiTao() {
    listPhieuHuy = dao.layListPhieuHuy();
    for (PhieuHuySanPham ph : listPhieuHuy) {
      ph.setListLoSanPhamHuy(dao.layChiTietHuyTheoMaPH(ph.getMaPH()));
    }
    canUpdate = false;
  }

  public ArrayList<PhieuHuySanPham> layListPhieuHuy() {
    if (canUpdate || listPhieuHuy == null) khoiTao();
    return listPhieuHuy;
  }

  public boolean thucHienHuy(PhieuHuySanPham phieuHuy, Object[][] data) {
    Connection conn = DBConnection.getConnection();
    try {
      conn.setAutoCommit(false);
      String maPH = dao.layMaPhieuHuySPKhaDung(conn);
      phieuHuy.setMaPH(maPH);

      // 1. Lưu thông tin phiếu chính
      if (!dao.themPhieuHuy(phieuHuy, conn)) throw new SQLException();

      // 2. Duyệt danh sách từ giao diện để lưu vào bảng chi tiết
      for (Object[] row : data) {
        String maLo = row[3].toString();
        double soLuong = Double.parseDouble(row[2].toString());
        double gia = Double.parseDouble(row[4].toString());

        if (!dao.themChiTietHuy(maPH, maLo, soLuong, gia, conn)) throw new SQLException();
      }
      conn.commit();
      this.canUpdate = true;
      return true;
    } catch (SQLException e) {
      try {
        if (conn != null) conn.rollback();
      } catch (Exception ex) {
      }
      return false;
    } finally {
      try {
        conn.setAutoCommit(true);
        conn.close();
      } catch (Exception e) {
      }
    }
  }

  public boolean capNhatPhieuHuy(PhieuHuySanPham ph) {
    Connection conn = DBConnection.getConnection();
    try {
      conn.setAutoCommit(false);
      if (!dao.capNhatPhieuHuy(ph, conn)) throw new SQLException("Câp nhật thất bại");
      if ("Đã xác nhận".equalsIgnoreCase(ph.getTrangThaiXuLy().trim())) {
        ArrayList<LoSanPham> chiTiet = dao.layChiTietHuyTheoMaPH(ph.getMaPH());
        if (chiTiet == null) throw new SQLException("Không tìm thấy chi tiết phiếu hủy");
        for (LoSanPham Lo : chiTiet) {
          if (!dao.truKhoLoSanPham(Lo.getMaLoSP(), Lo.getSoLuong(), conn))
            throw new SQLException("Trừ kho thất bại");
        }
      }
      conn.commit();
      this.canUpdate = true;
      khoiTao();
      bus.LoSanPhamBUS.getLoSanPhamBUS().khoitao();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      try {
        if (conn != null) conn.rollback();
      } catch (Exception ex) {
      }
      return false;
    } finally {
      try {
        conn.setAutoCommit(true);
        conn.close();
      } catch (Exception e) {
      }
    }
  }

  public boolean xuatExcel(String filePath) {
    ArrayList<PhieuHuySanPham> dsPhieu = layListPhieuHuy();

    try (Workbook workbook = new XSSFWorkbook()) {
      Sheet sheetPhieu = workbook.createSheet("Danh Sách Phiếu Hủy");
      String[] headerPhieu = {"Mã Phiếu", "Mã NV", "Ngày Hủy", "Lý Do", "Tổng Tiền"};
      createHeader(workbook, sheetPhieu, headerPhieu);

      Sheet sheetChiTiet = workbook.createSheet("Chi Tiết Lô Sản Phẩm");
      String[] headerCT = {"Mã Phiếu", "Mã Lô SP", "Số Lượng", "Đơn Giá"};
      createHeader(workbook, sheetChiTiet, headerCT);

      int rowPhieuIdx = 1;
      int rowCTIdx = 1;

      for (PhieuHuySanPham phieu : dsPhieu) {
        Row rowP = sheetPhieu.createRow(rowPhieuIdx++);
        rowP.createCell(0).setCellValue(phieu.getMaPH());
        rowP.createCell(1).setCellValue(phieu.getMaNV());
        rowP.createCell(2).setCellValue(phieu.getNgayHuy().toString());
        rowP.createCell(3).setCellValue(phieu.getLyDo());
        rowP.createCell(4).setCellValue(phieu.getTongGiaTri());

        ArrayList<LoSanPham> dsChiTiet = phieu.getListLoSanPhamHuy();

        if (dsChiTiet != null) {
          for (LoSanPham ct : dsChiTiet) {

            Row rowCT = sheetChiTiet.createRow(rowCTIdx++);
            rowCT.createCell(0).setCellValue(phieu.getMaPH());
            rowCT.createCell(1).setCellValue(ct.getMaLoSP());
            rowCT.createCell(2).setCellValue(ct.getSoLuong());
            rowCT.createCell(3).setCellValue(ct.getGiaNhap());
          }
        }
      }

      for (int i = 0; i < headerPhieu.length; i++) sheetPhieu.autoSizeColumn(i);
      for (int i = 0; i < headerCT.length; i++) sheetChiTiet.autoSizeColumn(i);

      try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
        workbook.write(fileOut);
      }
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  private void createHeader(Workbook workbook, Sheet sheet, String[] headers) {
    Row headerRow = sheet.createRow(0);
    CellStyle style = workbook.createCellStyle();
    Font font = workbook.createFont();
    font.setBold(true);
    style.setFont(font);

    for (int i = 0; i < headers.length; i++) {
      Cell cell = headerRow.createCell(i);
      cell.setCellValue(headers[i]);
      cell.setCellStyle(style);
    }
  }

  public boolean nhapExcel(String filePath) {
    Connection conn = DBConnection.getConnection();
    try (FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis)) {

      Sheet sheetPhieu = workbook.getSheetAt(0);
      Sheet sheetChiTiet = workbook.getSheetAt(1);

      Map<String, PhieuHuySanPham> mapPhieu = new HashMap<>();

      for (int i = 1; i <= sheetPhieu.getLastRowNum(); i++) {
        Row row = sheetPhieu.getRow(i);
        if (row == null) continue;

        String maCu = getCellValueAsString(row.getCell(0));
        PhieuHuySanPham phieuMoi = new PhieuHuySanPham();
        phieuMoi.setMaNV(getCellValueAsString(row.getCell(1)));
        phieuMoi.setLyDo(getCellValueAsString(row.getCell(3)));
        phieuMoi.setTrangThaiXuLy("Chờ xử lý");
        phieuMoi.setListLoSanPhamHuy(new ArrayList<>());

        mapPhieu.put(maCu, phieuMoi);
      }

      for (int i = 1; i <= sheetChiTiet.getLastRowNum(); i++) {
        Row row = sheetChiTiet.getRow(i);
        if (row == null) continue;

        String maPhieuLienKet = getCellValueAsString(row.getCell(0));
        if (mapPhieu.containsKey(maPhieuLienKet)) {
          LoSanPham lo = new LoSanPham();
          lo.setMaLoSP(getCellValueAsString(row.getCell(1)));
          lo.setSoLuong(row.getCell(2).getNumericCellValue());
          lo.setGiaNhap(row.getCell(3).getNumericCellValue());
          mapPhieu.get(maPhieuLienKet).getListLoSanPhamHuy().add(lo);
        }
      }

      conn.setAutoCommit(false);
      for (PhieuHuySanPham phieu : mapPhieu.values()) {

        String maMoi = dao.layMaPhieuHuySPKhaDung(conn);
        phieu.setMaPH(maMoi);

        double tongTien = 0;
        for (LoSanPham lo : phieu.getListLoSanPhamHuy()) {
          tongTien += lo.getSoLuong() * lo.getGiaNhap();
        }
        phieu.setTongGiaTri(tongTien);

        // Lưu phiếu chính
        if (!dao.themPhieuHuy(phieu, conn)) throw new SQLException();

        // Lưu chi tiết lô
        for (LoSanPham lo : phieu.getListLoSanPhamHuy()) {
          if (!dao.themChiTietHuy(
              phieu.getMaPH(), lo.getMaLoSP(), lo.getSoLuong(), lo.getGiaNhap(), conn))
            throw new SQLException();
        }
      }
      conn.commit();
      canUpdate = true;
      khoiTao();
      return true;
    } catch (Exception e) {
      try {
        if (conn != null) conn.rollback();
      } catch (SQLException ex) {
      }
      e.printStackTrace();
      return false;
    } finally {
      try {
        if (conn != null) conn.close();
      } catch (SQLException e) {
      }
    }
  }

  private String getCellValueAsString(Cell cell) {
    if (cell == null) return "";
    if (cell.getCellType() == CellType.STRING) return cell.getStringCellValue().trim();
    if (cell.getCellType() == CellType.NUMERIC)
      return String.valueOf((int) cell.getNumericCellValue());
    return "";
  }

  // Soft delete - set TrangThai = 0
  public boolean xoaMemPhieuHuy(String maPH) {
    Connection conn = DBConnection.getConnection();
    try {
      if (!dao.xoaMemPhieuHuy(maPH, conn)) throw new SQLException("Xóa thất bại");
      this.canUpdate = true;
      khoiTao();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    } finally {
      try {
        if (conn != null) conn.close();
      } catch (Exception e) {
      }
    }
  }
}
