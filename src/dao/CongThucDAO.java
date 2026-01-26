package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bus.CongThucBUS;
import dao.conection.DBConnection;
import dto.ChiTietCongThuc;
import dto.CongThuc;

public class CongThucDAO {
    public CongThuc timCongThuc(String maSP) {
        CongThuc congThuc = null;

        String sql = "SELECT * FROM CongThuc";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                if (rs.getString("MaSP").equals(maSP)) {
                    congThuc = new CongThuc(rs.getString("MaCT"));
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn sản phẩm: " + e.getMessage());
        }
        return congThuc;
    }


    public boolean themCongThuc(CongThuc congThuc) {
        String sql = "INSERT INTO CongThuc (MaCT, MaSP, TrangThai) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            if (congThuc.getMaCT() == null || congThuc.getMaCT().trim().isEmpty()) {
                congThuc.setMaCT(layMaCongThucKhaDung());
            }
            pst.setString(1, layMaCongThucKhaDung());
            pst.setString(2, congThuc.getMaSp());
            pst.setInt(3, 1);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi thêm CongThuc: " + e.getMessage());
            return false;
        }
    }

    public String layMaCongThucKhaDung() {
        String sql = "SELECT COUNT(*) FROM CongThuc";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int so = rs.getInt(1) + 1;
                String ma = String.format("%02d", so);
                return "CT" + ma;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn Công thức: " + e.getMessage());
        }
        return "";
    }
}
