package bus;

import dao.PhieuHuyNguyenLieuDAO;
import dao.conection.DBConnection;
import dto.LoNguyenLieu;
import dto.PhieuHuyNguyenLieu;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PhieuHuyNguyenLieuBUS {
  private static PhieuHuyNguyenLieuBUS instance;
  private PhieuHuyNguyenLieuDAO dao = new PhieuHuyNguyenLieuDAO();
  private ArrayList<PhieuHuyNguyenLieu> listPhieuHuy = null;
  private boolean canUpdate = true;

  public static PhieuHuyNguyenLieuBUS getPhieuHuyNguyenLieuBUS() {
    if (instance == null) instance = new PhieuHuyNguyenLieuBUS();
    return instance;
  }

  public void khoiTao() {
    listPhieuHuy = dao.layListPhieuHuy();
    for (PhieuHuyNguyenLieu ph : listPhieuHuy) {
      ph.setListLoNguyenLieuHuy(dao.layChiTietHuyTheoMaPH(ph.getMaPH()));
    }
    canUpdate = false;
  }

  public ArrayList<PhieuHuyNguyenLieu> layListPhieuHuy() {
    if (canUpdate || listPhieuHuy == null) khoiTao();
    return listPhieuHuy;
  }

  public boolean thucHienHuy(PhieuHuyNguyenLieu phieuHuy, Object[][] data) {
    Connection conn = DBConnection.getConnection();
    try {
      conn.setAutoCommit(false);
      String maPH = dao.layMaPhieuHuyNLKhaDung(conn);
      phieuHuy.setMaPH(maPH);

      if (!dao.themPhieuHuy(phieuHuy, conn)) throw new SQLException();

      for (Object[] row : data) {
        String maLo = row[3].toString();
        double soLuong = Double.parseDouble(row[2].toString());
        double gia = Double.parseDouble(row[4].toString());

        // Sử dụng đúng hàm themChiTietHuy của bạn
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

  public boolean capNhatPhieuHuy(PhieuHuyNguyenLieu ph) {
    Connection conn = DBConnection.getConnection();
    try {
      conn.setAutoCommit(false);
      if (!dao.capNhatPhieuHuy(ph, conn)) throw new SQLException("Câp nhật thất bại");
      if ("Đã xác nhận".equalsIgnoreCase(ph.getTrangThaiXuLy().trim())) {
        ArrayList<LoNguyenLieu> chiTiet = dao.layChiTietHuyTheoMaPH(ph.getMaPH());
        if (chiTiet == null) throw new SQLException("Không tìm thấy chi tiết phiếu hủy");
        for (LoNguyenLieu Lo : chiTiet) {
          if (!dao.truKhoLoNguyenLieu(Lo.getMaLoNL(), Lo.getSoLuong(), conn))
            throw new SQLException("Trừ kho thất bại");
        }
      }
      conn.commit();
      this.canUpdate = true;
      khoiTao();
      bus.LoNguyenLieuBUS.getLoNguyenLieuBUS().khoitao();
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
    ArrayList<PhieuHuyNguyenLieu> dsPhieu = layListPhieuHuy();
    try (Workbook workbook = new XSSFWorkbook()) {
      Sheet sheetP = workbook.createSheet("Phiếu Hủy Nguyên Liệu");
      String[] headerP = {"Mã Phiếu", "Mã NV", "Ngày Hủy", "Lý Do", "Tổng Tiền"};
      createHeader(workbook, sheetP, headerP);

      Sheet sheetCT = workbook.createSheet("Chi Tiết Lô Nguyên Liệu");
      String[] headerCT = {"Mã Phiếu", "Mã Lô NL", "Số Lượng", "Đơn Giá"};
      createHeader(workbook, sheetCT, headerCT);

      int rowPIdx = 1, rowCTIdx = 1;
      for (PhieuHuyNguyenLieu phieu : dsPhieu) {
        Row row = sheetP.createRow(rowPIdx++);
        row.createCell(0).setCellValue(phieu.getMaPH());
        row.createCell(1).setCellValue(phieu.getMaNV());
        row.createCell(2).setCellValue(phieu.getNgayHuy().toString());
        row.createCell(3).setCellValue(phieu.getLyDo());
        row.createCell(4).setCellValue(phieu.getTongTien());

        ArrayList<LoNguyenLieu> dsLo = phieu.getListLoNguyenLieuHuy();
        if (dsLo != null) {
          for (LoNguyenLieu lo : dsLo) {
            Row rCT = sheetCT.createRow(rowCTIdx++);
            rCT.createCell(0).setCellValue(phieu.getMaPH());
            rCT.createCell(1).setCellValue(lo.getMaLoNL());
            rCT.createCell(2).setCellValue(lo.getSoLuong());
            rCT.createCell(3).setCellValue(lo.getGiaNhap());
          }
        }
      }
      for (int i = 0; i < 5; i++) {
        sheetP.autoSizeColumn(i);
        sheetCT.autoSizeColumn(i);
      }
      try (FileOutputStream out = new FileOutputStream(filePath)) {
        workbook.write(out);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean nhapExcel(String filePath) {
    Connection conn = DBConnection.getConnection();
    try (FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis)) {
      Sheet sheetP = workbook.getSheetAt(0);
      Sheet sheetCT = workbook.getSheetAt(1);
      Map<String, PhieuHuyNguyenLieu> mapPhieu = new HashMap<>();

      for (int i = 1; i <= sheetP.getLastRowNum(); i++) {
        Row row = sheetP.getRow(i);
        if (row == null) continue;
        String maCu = getCellValueAsString(row.getCell(0));
        PhieuHuyNguyenLieu p = new PhieuHuyNguyenLieu();
        p.setMaNV(getCellValueAsString(row.getCell(1)));
        p.setLyDo(getCellValueAsString(row.getCell(3)));
        p.setTrangThaiXuLy("Chờ xử lý");
        p.setListLoNguyenLieuHuy(new ArrayList<>());
        mapPhieu.put(maCu, p);
      }

      for (int i = 1; i <= sheetCT.getLastRowNum(); i++) {
        Row row = sheetCT.getRow(i);
        if (row == null) continue;
        String maLK = getCellValueAsString(row.getCell(0));
        if (mapPhieu.containsKey(maLK)) {
          LoNguyenLieu lo = new LoNguyenLieu();
          lo.setMaLoNL(getCellValueAsString(row.getCell(1)));
          lo.setSoLuong(row.getCell(2).getNumericCellValue());
          lo.setGiaNhap(row.getCell(3).getNumericCellValue());
          mapPhieu.get(maLK).getListLoNguyenLieuHuy().add(lo);
        }
      }

      conn.setAutoCommit(false);
      for (PhieuHuyNguyenLieu p : mapPhieu.values()) {
        // Lấy mã mới ngay trong vòng lặp để tránh trùng Primary Key
        p.setMaPH(dao.layMaPhieuHuyNLKhaDung(conn));
        double tong = 0;
        for (LoNguyenLieu lo : p.getListLoNguyenLieuHuy())
          tong += lo.getSoLuong() * lo.getGiaNhap();
        p.setTongTien(tong);

        if (!dao.themPhieuHuy(p, conn)) throw new SQLException();
        for (LoNguyenLieu lo : p.getListLoNguyenLieuHuy()) {
          if (!dao.themChiTietHuy(
              p.getMaPH(), lo.getMaLoNL(), lo.getSoLuong(), lo.getGiaNhap(), conn))
            throw new SQLException();
        }
      }
      conn.commit();
      this.canUpdate = true;
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
      } catch (Exception e) {
      }
    }
  }

  private void createHeader(Workbook wb, Sheet s, String[] h) {
    Row r = s.createRow(0);
    CellStyle st = wb.createCellStyle();
    Font f = wb.createFont();
    f.setBold(true);
    st.setFont(f);
    for (int i = 0; i < h.length; i++) {
      Cell c = r.createCell(i);
      c.setCellValue(h[i]);
      c.setCellStyle(st);
    }
  }

  private String getCellValueAsString(Cell c) {
    if (c == null) return "";
    if (c.getCellType() == CellType.STRING) return c.getStringCellValue().trim();
    if (c.getCellType() == CellType.NUMERIC) return String.valueOf((int) c.getNumericCellValue());
    return "";
  }
}
