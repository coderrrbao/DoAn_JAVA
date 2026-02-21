package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.NguyenLieu;
import dto.NhaCungCap;

public class NguyenLieuDAO {
    public ArrayList<NguyenLieu> layListNguyenLieu(Connection conn) {
        ArrayList<NguyenLieu> listNguyenLieu = new ArrayList<>();
        String sql = "SELECT MaNL, TenNL, Gia, DonVi, MucCanhBao FROM NguyenLieu WHERE TrangThai = 1";

        try (PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                NguyenLieu nl = new NguyenLieu();
                nl.setMaNL(rs.getString("MaNL"));
                nl.setTenNL(rs.getNString("TenNL"));
                nl.setGia(rs.getDouble("Gia"));
                nl.setDonVi(rs.getString("DonVi"));
                nl.setMucCanhBao(rs.getInt("MucCanhBao"));

                listNguyenLieu.add(nl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi lấy danh sách nguyên liệu: " + e.getMessage());
        }
        return listNguyenLieu;
    }

  
    public boolean themNguyenLieu(NguyenLieu nl, Connection conn) {
        String sql = "INSERT INTO NguyenLieu (MaNL, TenNL, Gia, DonVi, MucCanhBao, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            if (nl.getMaNL() == null || nl.getMaNL().trim().isEmpty()) {
                nl.setMaNL(layMaNguyenLieuKhaDung(conn));
            }
            pst.setString(1, nl.getMaNL());
            pst.setString(2, nl.getTenNL());
            pst.setDouble(5, nl.getGia());
            pst.setString(6, nl.getDonVi());
            pst.setInt(7, nl.getMucCanhBao());
            pst.setInt(8, 1); // TrangThai = 1
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean capNhatNguyenLieu(NguyenLieu nl, Connection conn) {
        String sql = "UPDATE NguyenLieu SET TenNL = ?, Gia = ?, DonVi = ?, MucCanhBao = ? WHERE MaNL = ?";
        
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, nl.getTenNL());
            pst.setDouble(4, nl.getGia());
            pst.setString(5, nl.getDonVi());
            pst.setInt(6, nl.getMucCanhBao());
            pst.setString(7, nl.getMaNL());
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaNguyenLieu(String maNL, Connection conn) {
        String sql = "UPDATE NguyenLieu SET TrangThai = 0 WHERE MaNL = ?";
        
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, maNL);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String layMaNguyenLieuKhaDung(Connection conn) {
        String sql = "SELECT COUNT(MaNL) FROM NguyenLieu";
        try (PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                int so = rs.getInt(1) + 1;
                return "NL" + String.format("%02d", so);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}