package dao;

import dao.conection.DBConnection;
import dto.ChiTietPhieuHuyNguyenLieu;
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
    String sql = "INSERT INTO PhieuHuyNguyenLieu (MaPH, NgayHuy, MaNV, MaNVXacNhan, LyDo, TongTien, TrangThaiXuLy, TrangThai) "
        + "VALUES (?, GETDATE(), ?, ?, ?, ?, N'Đang xử lý', 1)";
    try (PreparedStatement pst = conn.prepareStatement(sql)) {
      pst.setString(1, ph.getMaPH());
      pst.setString(2, ph.getMaNV());
      pst.setString(3, ph.getMaNVXacNhan());
      pst.setString(4, ph.getLyDo());
      pst.setDouble(5, ph.getTongTien());
      return pst.executeUpdate() > 0;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean themChiTietHuy(String maCTPHNL, String maPH, String maLo, double soLuong, double gia, Connection conn) {
    String sql = "INSERT INTO ChiTietPhieuHuyNguyenLieu (MaCTPHNL, MaPH, MaLo, SoLuong, DonGia) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement pst = conn.prepareStatement(sql)) {
      pst.setString(1, maCTPHNL);
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

  public boolean capNhatPhieuHuy(PhieuHuyNguyenLieu ph, Connection conn) {
    String sql = "UPDATE PhieuHuyNguyenLieu SET LyDo = ?, TrangThaiXuLy = ?, MaNVXacNhan = ? WHERE MaPH = ?";
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
        ph.setMaNVXacNhan(rs.getString("MaNVXacNhan"));
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

  public boolean xoaMemPhieuHuy(String maPH, Connection conn) {
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