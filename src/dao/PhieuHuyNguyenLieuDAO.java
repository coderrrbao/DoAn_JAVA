package dao;

import dao.conection.DBConnection;
import dto.PhieuHuyNguyenLieu;
import java.sql.*;
import java.util.ArrayList;

public class PhieuHuyNguyenLieuDAO {
  public boolean luuPhieuHuy(String maNV, String lyDo, Object[][] dataTable) {
    Connection conn = DBConnection.getConnection();
    try {
      conn.setAutoCommit(false);

      // INSERT vào PhieuHuyNguyenLieu dùng cột MaLo
      String sqlInsert =
          "INSERT INTO PhieuHuyNguyenLieu (MaPH, MaLo, NgayHuy, MaNV, LyDo, TongTien,"
              + " TrangThaiXuLy, TrangThai) VALUES (?, ?, GETDATE(), ?, ?, ?, N'Đã xử lý', 1)";

      // UPDATE vào LoNguyenLieu dùng cột MaLoNL theo nghi vấn của bạn
      String sqlUpdateStock = "UPDATE LoNguyenLieu SET SoLuong = SoLuong - ? WHERE MaLoNL = ?";

      PreparedStatement pstInsert = conn.prepareStatement(sqlInsert);
      PreparedStatement pstUpdate = conn.prepareStatement(sqlUpdateStock);

      for (Object[] row : dataTable) {
        // Thứ tự: [0]:MaNL, [1]:TenNL, [2]:SL, [3]:MaLo, [4]:GiaNhap
        String maLo = row[3].toString();
        double slHuy = Double.parseDouble(row[2].toString());
        double giaNhap = Double.parseDouble(row[4].toString());
        double tongTienRow = slHuy * giaNhap; // Tính tổng tiền thực tế

        String maPH = "PHNL" + (System.currentTimeMillis() % 100000) + (int) (Math.random() * 100);

        pstInsert.setString(1, maPH);
        pstInsert.setString(2, maLo);
        pstInsert.setString(3, maNV);
        pstInsert.setString(4, lyDo);
        pstInsert.setDouble(5, tongTienRow);
        pstInsert.addBatch();

        pstUpdate.setDouble(1, slHuy);
        pstUpdate.setString(2, maLo);
        pstUpdate.addBatch();
      }

      pstInsert.executeBatch();
      pstUpdate.executeBatch();
      conn.commit();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      try {
        if (conn != null) conn.rollback();
      } catch (SQLException ex) {
      }
      return false;
    } finally {
      try {
        if (conn != null) conn.close();
      } catch (SQLException e) {
      }
    }
  }

  public ArrayList<PhieuHuyNguyenLieu> layListPhieuHuy() {
    ArrayList<PhieuHuyNguyenLieu> list = new ArrayList<>();
    String sql = "SELECT * FROM PhieuHuyNguyenLieu WHERE TrangThai=1 ORDER BY NgayHuy DESC";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery()) {
      while (rs.next()) {
        PhieuHuyNguyenLieu ph = new PhieuHuyNguyenLieu();
        ph.setMaPH(rs.getString("MaPH"));
        ph.setNgayHuy(rs.getDate("NgayHuy"));
        ph.setMaNV(rs.getString("MaNV"));
        ph.setLyDo(rs.getString("LyDo"));
        ph.setTongTien(rs.getDouble("TongTien"));
        list.add(ph);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }
}
