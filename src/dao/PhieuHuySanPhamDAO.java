package dao;

import dao.conection.DBConnection;
import dto.PhieuHuySanPham;
import java.sql.*;
import java.util.ArrayList;

public class PhieuHuySanPhamDAO {

  public String layMaPhieuHuySPKhaDung(Connection conn) {
    String sql = "SELECT COUNT(MaPH) FROM PhieuHuySanPham";
    try (PreparedStatement pst = conn.prepareStatement(sql)) {
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        return "PHSP" + String.format("%02d", rs.getInt(1) + 1);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "PHSP01";
  }

  public boolean themPhieuHuy(PhieuHuySanPham ph, Connection conn) {
    String sql = "INSERT INTO PhieuHuySanPham (MaPH, NgayHuy, MaNV, MaNVXacNhan, LyDo, TongGiaTri, TrangThaiXuLy, TrangThai) "
        + "VALUES (?, GETDATE(), ?, ?, ?, ?, N'Đang xử lý', 1)";
    try (PreparedStatement pst = conn.prepareStatement(sql)) {
      pst.setString(1, ph.getMaPH());
      pst.setString(2, ph.getMaNV());
      pst.setString(3, ph.getMaNVXacNhan());
      pst.setString(4, ph.getLyDo());
      pst.setDouble(5, ph.getTongGiaTri());
      return pst.executeUpdate() > 0;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean themChiTietHuy(String maCTPHSP, String maPH, String maLo, double soLuong, double gia, Connection conn) {
    String sql = "INSERT INTO ChiTietPhieuHuySanPham (MaCTPHSP, MaPH, MaLo, SoLuong, DonGia) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement pst = conn.prepareStatement(sql)) {
      pst.setString(1, maCTPHSP);
      pst.setString(2, maPH);
      pst.setString(3, maLo);
      pst.setDouble(4, soLuong);
      pst.setDouble(5, gia);
      return pst.executeUpdate() > 0;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean capNhatPhieuHuy(PhieuHuySanPham ph, Connection conn) {
    String sql = "UPDATE PhieuHuySanPham SET LyDo = ?, TrangThaiXuLy = ?, MaNVXacNhan = ? WHERE MaPH = ?";
    try (PreparedStatement pst = conn.prepareStatement(sql)) {
      pst.setString(1, ph.getLyDo());
      pst.setString(2, ph.getTrangThaiXuLy());
      pst.setString(3, ph.getMaNVXacNhan());
      pst.setString(4, ph.getMaPH());
      return pst.executeUpdate() > 0;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean truKhoLoSanPham(String maLo, double soLuong, Connection conn) {
    String sql = "UPDATE LoSanPham SET SoLuong = SoLuong - ? WHERE MaLoSP = ?";
    try (PreparedStatement pst = conn.prepareStatement(sql)) {
      pst.setDouble(1, soLuong);
      pst.setString(2, maLo);
      return pst.executeUpdate() > 0;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
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
        ph.setNgayHuy(rs.getDate("NgayHuy"));
        ph.setMaNV(rs.getString("MaNV"));
        ph.setMaNVXacNhan(rs.getString("MaNVXacNhan"));
        ph.setLyDo(rs.getString("LyDo"));
        ph.setTongGiaTri(rs.getDouble("TongGiaTri"));
        ph.setTrangThaiXuLy(rs.getString("TrangThaiXuLy"));
        list.add(ph);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }


  public boolean xoaMemPhieuHuy(String maPH, Connection conn) {
    String sql = "UPDATE PhieuHuySanPham SET TrangThai = 0 WHERE MaPH = ?";
    try (PreparedStatement pst = conn.prepareStatement(sql)) {
      pst.setString(1, maPH);
      return pst.executeUpdate() > 0;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
  
}