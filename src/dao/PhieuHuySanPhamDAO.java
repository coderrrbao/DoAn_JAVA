package dao;

import dao.conection.DBConnection;
import dto.PhieuHuySanPham;
import java.sql.*;
import java.util.ArrayList;

public class PhieuHuySanPhamDAO {
  public boolean luuPhieuHuy(String maNV, String lyDo, Object[][] dataTable) {
    Connection conn = DBConnection.getConnection();
    try {
      conn.setAutoCommit(false);
      // Bảng PhieuHuySanPham dùng cột MaLo, Bảng LoSanPham dùng cột MaLoSP
      String sqlInsert =
          "INSERT INTO PhieuHuySanPham (MaPH, MaLo, NgayHuy, MaNV, LyDo, TongGiaTri, TrangThaiXuLy,"
              + " TrangThai) VALUES (?, ?, GETDATE(), ?, ?, ?, N'Đã xử lý', 1)";
      String sqlUpdateStock = "UPDATE LoSanPham SET SoLuong = SoLuong - ? WHERE MaLoSP = ?";

      PreparedStatement pstInsert = conn.prepareStatement(sqlInsert);
      PreparedStatement pstUpdate = conn.prepareStatement(sqlUpdateStock);

      for (Object[] row : dataTable) {
        String maLo = row[3].toString();
        double slHuy = Double.parseDouble(row[2].toString());
        double giaNhap = Double.parseDouble(row[4].toString()); // Cột giá mới thêm vào
        double tongGiaTri = slHuy * giaNhap;

        String maPH = "PHSP" + (System.currentTimeMillis() % 100000) + (int) (Math.random() * 100);

        pstInsert.setString(1, maPH);
        pstInsert.setString(2, maLo);
        pstInsert.setString(3, maNV);
        pstInsert.setString(4, lyDo);
        pstInsert.setDouble(5, tongGiaTri);
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

  public ArrayList<PhieuHuySanPham> layListPhieuHuy() {
    ArrayList<PhieuHuySanPham> list = new ArrayList<>();
    String sql = "SELECT * FROM PhieuHuySanPham WHERE TrangThai=1 ORDER BY NgayHuy DESC";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery()) {
      while (rs.next()) {
        PhieuHuySanPham ph = new PhieuHuySanPham();
        ph.setMaPH(rs.getString("MaPH"));
        ph.setMaLo(rs.getString("MaLo"));
        ph.setNgayHuy(rs.getDate("NgayHuy"));
        ph.setMaNV(rs.getString("MaNV"));
        ph.setLyDo(rs.getString("LyDo"));
        ph.setTongGiaTri(rs.getDouble("TongGiaTri"));
        list.add(ph);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }
}
