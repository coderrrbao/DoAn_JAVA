package dao;

import dao.conection.DBConnection;
import dto.LoNguyenLieu;
import dto.PhieuHuyNguyenLieu;
import java.sql.*;
import java.util.ArrayList;

public class PhieuHuyNguyenLieuDAO {

  public String layMaPhieuHuyNLKhaDung(Connection conn) {
    String sql = "SELECT COUNT(MaPH) FROM PhieuHuyNguyenLieu";
    try (PreparedStatement pst = conn.prepareStatement(sql)) {
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        int soPH = rs.getInt(1) + 1;
        return "PHNL" + String.format("%02d", soPH);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "PHNL01";
  }

  public boolean themPhieuHuy(PhieuHuyNguyenLieu ph, Connection conn) {
    // CHỈ INSERT các cột thông tin chung, bỏ MaLo
    String sql =
        "INSERT INTO PhieuHuyNguyenLieu (MaPH, NgayHuy, MaNV, LyDo, TongTien, TrangThaiXuLy,"
            + " TrangThai) VALUES (?, GETDATE(), ?, ?, ?, N'Đang xử lý', 1)";
    try (PreparedStatement pst = conn.prepareStatement(sql)) {
      pst.setString(1, ph.getMaPH());
      pst.setString(2, ph.getMaNV());
      pst.setString(3, ph.getLyDo());
      pst.setDouble(4, ph.getTongTien());
      return pst.executeUpdate() > 0;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean themChiTietHuy(
      String maPH, String maLo, double soLuong, double gia, Connection conn) {
    String sql =
        "INSERT INTO ChiTietPhieuHuyNguyenLieu (MaPH, MaLo, SoLuong, DonGia) VALUES (?, ?, ?, ?)";
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

  public boolean capNhatPhieuHuy(PhieuHuyNguyenLieu ph, Connection conn) {
    String sql = "UPDATE PhieuHuyNguyenLieu SET LyDo = ?, TrangThaiXuLy = ? WHERE MaPH = ?";
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

  public boolean truKhoLoNguyenLieu(String maLo, double soLuong, Connection conn) {
    String sql = "UPDATE LoNguyenLieu SET SoLuong = SoLuong - ? WHERE MaLoNL = ?";
    try (PreparedStatement pst = conn.prepareStatement(sql)) {
      pst.setDouble(1, soLuong);
      pst.setString(2, maLo);
      return pst.executeUpdate() > 0;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
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
        ph.setTrangThaiXuLy(rs.getString("TrangThaiXuLy"));
        list.add(ph);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public ArrayList<LoNguyenLieu> layChiTietHuyTheoMaPH(String maPH) {
    ArrayList<LoNguyenLieu> list = new ArrayList<>();
    String sql =
        "SELECT ct.*, lo.MaNL "
            + "FROM ChiTietPhieuHuyNguyenLieu ct "
            + "JOIN LoNguyenLieu lo ON ct.MaLo = lo.MaLoNL "
            + "WHERE ct.MaPH = ?";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql)) {
      pst.setString(1, maPH);
      ResultSet rs = pst.executeQuery();
      while (rs.next()) {
        LoNguyenLieu lo = new LoNguyenLieu();
        lo.setMaLoNL(rs.getString("MaLo"));
        lo.setMaNL(rs.getString("MaNL")); // Lấy MaNL từ bảng lô
        lo.setSoLuong(rs.getDouble("SoLuong"));
        lo.setGiaNhap(rs.getDouble("DonGia"));
        list.add(lo);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }
  public boolean xoaPhieuHuy(String maPH, Connection conn) {
    String sql = "UPDATE PhieuHuyNguyenLieu SET TrangThai = 0 WHERE MaPH = ?";
    try (PreparedStatement pst = conn.prepareStatement(sql)) {
      pst.setString(1, maPH);
      return pst.executeUpdate() > 0;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}
