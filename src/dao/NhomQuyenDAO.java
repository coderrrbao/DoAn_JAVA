package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.conection.DBConnection;
import dto.NhomQuyen;

public class NhomQuyenDAO {
    // lay list nhom quyen
    public ArrayList<NhomQuyen> layDanhSachNhomQuyen_Dao() {
        ArrayList<NhomQuyen> ds = new ArrayList<>();
        String sql = "SELECT MaNQ, TenNhomQuyen FROM NhomQuyen WHERE TrangThai=1";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                NhomQuyen nq = new NhomQuyen();
                nq.setMaNQ(rs.getString("MaNQ"));
                nq.setTenNhomQuyen(rs.getString("TenNhomQuyen"));
                ds.add(nq);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public String taoMaNhomQuyenMoi(Connection conn) {
        int count = 0;
        String sql = "SELECT COUNT(MaNQ) FROM NhomQuyen";

        try (PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("NQ%02d", count + 1);
    }

    public boolean themNhomQuyen(NhomQuyen nq, Connection conn) {
        boolean ketQua = false;
        String sql = "INSERT INTO NhomQuyen (MaNQ, TenNhomQuyen, TrangThai) VALUES (?, ?, 1)";

        try (
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nq.getMaNQ());
            ps.setString(2, nq.getTenNhomQuyen());

            int rowsAffected = ps.executeUpdate();
            ketQua = rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public boolean xoaNhomQuyen(NhomQuyen nhomQuyen, Connection conn) {
        boolean ketQua = false;
        String sql = "UPDATE NhomQuyen SET TrangThai = 0 WHERE MaNQ = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nhomQuyen.getMaNQ());

            int rowsAffected = ps.executeUpdate();
            ketQua = rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }
}
