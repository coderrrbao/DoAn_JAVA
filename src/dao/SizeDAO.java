package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.conection.DBConnection;
import dto.Size;

public class SizeDAO {
    public ArrayList<Size> layListSizeChoSP(String ma) {
        ArrayList<Size> listSize = new ArrayList<>();
        String sql = "SELECT * FROM Size WHERE TrangThai = 1 AND MaSP=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, ma);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Size size = new Size(rs.getString("MaSize"), rs.getString("MaSP"), rs.getString("TenSize"),
                        rs.getInt("PhanTramGia"), rs.getInt("PhanTramNL"));
                listSize.add(size);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn : " + e.getMessage());
        }
        return listSize;
    }

    public String layMaSizeKhaDung() {
        String sql = "SELECT COUNT(*) FROM Size";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int so = rs.getInt(1) + 1;
                String ma = String.format("%02d", so);
                return "SZ" + ma;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn Size: " + e.getMessage());
        }
        return "";
    }

    public boolean themSize(Size s) {
        String sql = "INSERT INTO Size (MaSize, MaSP, TenSize, PhanTramGia, PhanTramNL, TrangThai) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            if (s.getMaSize() == null || s.getMaSize().trim().isEmpty()) {
                s.setMaSize(layMaSizeKhaDung());
            }
            pst.setString(1, s.getMaSize());
            pst.setString(2, s.getMaSP());
            pst.setString(3, s.getTenSize());
            pst.setInt(4, s.getPhanTramGia());
            pst.setInt(5, s.getPhanTramNL());
            pst.setInt(6, 1);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi thêm Size: " + e.getMessage());
            return false;
        }
    }

    public  boolean xoaSize(Size  size){
        String sql =  "UPDATE Size SET TrangThai=0 WHERE MaSize=?";
        try(Connection con = DBConnection.getConnection();PreparedStatement  pst = con.prepareStatement(sql)){
            pst.setString(1,size.getMaSize());
            pst.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public  boolean capNhapSize(Size  size){
        String sql =  "UPDATE Size SET TenSize=?,PhanTramGia=?,PhanTramNL=? WHERE MaSize=?";
        try(Connection con = DBConnection.getConnection();PreparedStatement  pst = con.prepareStatement(sql)){
            pst.setString(1,size.getTenSize());
            pst.setInt(2, size.getPhanTramGia());
            pst.setInt(3, size.getPhanTramNL());
            pst.setString(4, size.getMaSP());
            pst.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
