package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.conection.DBConnection;
import dto.DanhMuc;

public class DanhMucDao {
    public ArrayList<String> layLuaChonDanhMuc() {
        ArrayList<String> list = new ArrayList<>();
        String sql = "SELECT TenDM FROM DanhMuc";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("TenDM"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn sản phẩm: " + e.getMessage());
        }
        return list;
    }

    public DanhMuc timDanhMuc(String maDM) {
        String sql = "SELECT * FROM DanhMuc WHERE TrangThai=1";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                if (rs.getString("MaDM").equals(maDM)) {
                    return new DanhMuc(maDM, rs.getString("TenDM"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn sản phẩm: " + e.getMessage());
        }
        return null;
    }
    public DanhMuc timDanhMucTheoTen(String ten){
         String sql = "SELECT * FROM DanhMuc WHERE TrangThai=1 AND TenDM=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return new DanhMuc(rs.getString("MaDM"), rs.getString("TenDM"));
                }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn sản phẩm: " + e.getMessage());
        }
        return null;
    }
    public ArrayList<DanhMuc> layDanhMucDangHoatDong() {
        ArrayList<DanhMuc> list = new ArrayList<>();

        String sql = "SELECT MaDM, TenDM FROM DanhMuc WHERE TrangThai = 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                list.add(new DanhMuc(
                        rs.getString("MaDM"),
                        rs.getString("TenDM")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
