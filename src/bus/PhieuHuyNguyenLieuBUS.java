package bus;

import dao.PhieuHuyNguyenLieuDAO;
import dao.conection.DBConnection;
import dto.LoNguyenLieu;
import dto.PhieuHuyNguyenLieu;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

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
}
