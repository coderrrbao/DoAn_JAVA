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
            pst.setInt(6, s.getTrangThai() ? 1 : 0);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi thêm Size: " + e.getMessage());
            return false;
        }
    }

}
