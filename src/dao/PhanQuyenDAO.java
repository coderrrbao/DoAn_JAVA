
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import dao.conection.DBConnection;
import dto.PhanQuyen;

public class PhanQuyenDAO {
    public ArrayList<PhanQuyen> layListPhanQuyen() {
        ArrayList<PhanQuyen> list = new ArrayList<>();
        String sql = "SELECT * FROM PhanQuyen WHERE TrangThai=1";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new PhanQuyen(rs.getString("MaNQ"), rs.getString("MaQuyen")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean themPhanQuyen(PhanQuyen pq, Connection conn) {
        boolean result = false;
        String sql = "INSERT INTO PhanQuyen (MaNQ, MaQuyen, TrangThai) VALUES (?, ?, 1)";

        try (
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, pq.getMaNQ());
            pst.setString(2, pq.getMaQuyen());

            int rowAffected = pst.executeUpdate();
            if (rowAffected > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean xoaPhanQuyen(PhanQuyen phanQuyen, Connection conn) {
        boolean result = false;

        String sql = "UPDATE PhanQuyen SET TrangThai = 0 WHERE MaNQ = ? AND MaQuyen = ?";

        try (
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, phanQuyen.getMaNQ());
            pst.setString(2, phanQuyen.getMaQuyen());

            int rowAffected = pst.executeUpdate();
            if (rowAffected > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}