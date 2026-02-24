package dao;

import dao.conection.DBConnection;
import dto.LoSanPham;
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
    // CHỈ INSERT các cột thông tin chung, bỏ MaLo
    String sql =
        "INSERT INTO PhieuHuySanPham (MaPH, NgayHuy, MaNV, LyDo, TongGiaTri, TrangThaiXuLy,"
            + " TrangThai) VALUES (?, GETDATE(), ?, ?, ?, N'Đang xử lý', 1)";
    try (PreparedStatement pst = conn.prepareStatement(sql)) {
      pst.setString(1, ph.getMaPH());
      pst.setString(2, ph.getMaNV());
      pst.setString(3, ph.getLyDo());
      pst.setDouble(4, ph.getTongGiaTri());
      return pst.executeUpdate() > 0;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean themChiTietHuy(
      String maPH, String maLo, double soLuong, double gia, Connection conn) {
    String sql =
        "INSERT INTO ChiTietPhieuHuySanPham (MaPH, MaLo, SoLuong, DonGia) VALUES (?, ?, ?, ?)";
    try (PreparedStatement pst = conn.prepareStatement(sql)) {
      pst.setString(1, maPH);
      pst.setString(2, maLo);
      pst.setDouble(3, soLuong);
      pst.setDouble(4, gia);
      return pst.executeUpdate() > 0;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean capNhatPhieuHuy(PhieuHuySanPham ph, Connection conn) {
    String sql = "UPDATE PhieuHuySanPham SET LyDo = ?, TrangThaiXuLy = ? WHERE MaPH = ?";
    try (PreparedStatement pst = conn.prepareStatement(sql)) {
      pst.setString(1, ph.getLyDo());
      pst.setString(2, ph.getTrangThaiXuLy());
      pst.setString(3, ph.getMaPH());
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

  public ArrayList<LoSanPham> layChiTietHuyTheoMaPH(String maPH) {
    ArrayList<LoSanPham> list = new ArrayList<>();
    // Sử dụng JOIN để lấy MaSP từ bảng LoSanPham thông qua MaLo
    String sql =
        "SELECT ct.*, lo.MaSP "
            + "FROM ChiTietPhieuHuySanPham ct "
            + "JOIN LoSanPham lo ON ct.MaLo = lo.MaLoSP "
            + "WHERE ct.MaPH = ?";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql)) {
      pst.setString(1, maPH);
      ResultSet rs = pst.executeQuery();
      while (rs.next()) {
        LoSanPham lo = new LoSanPham();
        lo.setMaLoSP(rs.getString("MaLo"));
        lo.setMaSP(rs.getString("MaSP")); // Bây giờ đã có MaSP từ bảng LoSanPham
        lo.setSoLuong(rs.getDouble("SoLuong"));
        lo.setGiaNhap(rs.getDouble("DonGia"));
        list.add(lo);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }
}
