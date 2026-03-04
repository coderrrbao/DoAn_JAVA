package dao;

import dto.KhuyenMai;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date; // Chú ý import java.sql.Date
import java.util.ArrayList;

public class KhuyenMaiDAO {
    public ArrayList<KhuyenMai> layListKhuyenMai(Connection conn) {
        ArrayList<KhuyenMai> listKM = new ArrayList<>();
        String sql = "SELECT MaKM, PhanTramGiam, TuNgay, DenNgay FROM KhuyenMai WHERE TrangThai = 1";

        try (PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                KhuyenMai km = new KhuyenMai();
                km.setMaKM(rs.getString("MaKM"));
                km.setPhanTramGiam(rs.getInt("PhanTramGiam"));
                
                // Đọc Date từ DB và chuyển ngay sang String (định dạng yyyy-MM-dd)
                Date tuNgayDB = rs.getDate("TuNgay");
                Date denNgayDB = rs.getDate("DenNgay");
                
                km.setTuNgay(tuNgayDB != null ? tuNgayDB.toString() : "");
                km.setDenNgay(denNgayDB != null ? denNgayDB.toString() : "");
                
                listKM.add(km);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listKM;
    }

    public boolean themKhuyenMai(KhuyenMai km, Connection conn) {
        String sql = "INSERT INTO KhuyenMai (MaKM, PhanTramGiam, TuNgay, DenNgay, TrangThai) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            if (km.getMaKM() == null || km.getMaKM().trim().isEmpty()) {
                km.setMaKM(layMaKhuyenMaiKhaDung(conn));
            }
            pst.setString(1, km.getMaKM());
            pst.setInt(2, km.getPhanTramGiam());
            
            // Chuyển String từ Java sang Date để lưu xuống DB
            pst.setDate(3, Date.valueOf(km.getTuNgay()));
            pst.setDate(4, Date.valueOf(km.getDenNgay()));
            pst.setInt(5, 1); 

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean capNhatKhuyenMai(KhuyenMai km, Connection conn) {
        String sql = "UPDATE KhuyenMai SET PhanTramGiam = ?, TuNgay = ?, DenNgay = ? WHERE MaKM = ?";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, km.getPhanTramGiam());
            
            // Chuyển String từ Java sang Date để cập nhật DB
            pst.setDate(2, Date.valueOf(km.getTuNgay()));
            pst.setDate(3, Date.valueOf(km.getDenNgay()));
            pst.setString(4, km.getMaKM());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaKhuyenMai(String maKM, Connection conn) {
        String sql = "UPDATE KhuyenMai SET TrangThai = 0 WHERE MaKM = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, maKM);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String layMaKhuyenMaiKhaDung(Connection conn) {
        String sql = "SELECT COUNT(MaKM) FROM KhuyenMai";
        try (PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                int so = rs.getInt(1) + 1;
                return "KM" + String.format("%03d", so);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "KM001";
    }
}