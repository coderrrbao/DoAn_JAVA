package dao;

import dao.conection.DBConnection;
import dto.PhieuHuySanPham;
import java.sql.*;
import java.util.ArrayList;

public class PhieuHuySanPhamDAO {
  // Phương thức này nhận toàn bộ bảng dữ liệu để xử lý trong 1 lần kết nối
  public boolean luuPhieuHuy(String maNV, String lyDo, Object[][] dataTable) {
    Connection conn = DBConnection.getConnection();
    try {
      conn.setAutoCommit(false); // Bắt đầu Transaction

      // Chuẩn bị các câu lệnh SQL
      String sqlInsert =
          "INSERT INTO PhieuHuySanPham (MaPH, MaLo, NgayHuy, MaNV, LyDo, TongGiaTri, TrangThaiXuLy,"
              + " TrangThai) VALUES (?, ?, GETDATE(), ?, ?, ?, N'Đã xử lý', 1)";
      String sqlUpdateStock = "UPDATE LoSanPham SET SoLuong = SoLuong - ? WHERE MaLo = ?";

      PreparedStatement pstInsert = conn.prepareStatement(sqlInsert);
      PreparedStatement pstUpdate = conn.prepareStatement(sqlUpdateStock);

      for (Object[] row : dataTable) {
        // Giả sử row[0]: MaSP, row[2]: SoLuongHuy, row[3]: MaLo (cần bổ sung MaLo vào bảng chọn)
        String maLo = row[3].toString();
        int slHuy = Integer.parseInt(row[2].toString());
        String maPH = "PHSP" + System.currentTimeMillis() % 100000 + (int) (Math.random() * 100);

        // 1. Thực hiện Insert phiếu hủy cho từng lô
        pstInsert.setString(1, maPH);
        pstInsert.setString(2, maLo);
        pstInsert.setString(3, maNV);
        pstInsert.setString(4, lyDo);
        pstInsert.setDouble(5, 0.0); // Tổng giá trị có thể tính sau
        pstInsert.addBatch();

        // 2. Thực hiện trừ tồn kho trong bảng LoSanPham
        pstUpdate.setInt(1, slHuy);
        pstUpdate.setString(2, maLo);
        pstUpdate.addBatch();
      }

      pstInsert.executeBatch();
      pstUpdate.executeBatch();

      conn.commit(); // Hoàn tất Transaction
      return true;
    } catch (Exception e) {
      try {
        conn.rollback();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
      e.printStackTrace();
      return false;
    } finally {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public ArrayList<PhieuHuySanPham> layListPhieuHuy() {
    ArrayList<PhieuHuySanPham> list = new ArrayList<>();
    String sql = "SELECT * FROM PhieuHuySanPham WHERE TrangThai=1";
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
