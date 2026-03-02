package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.conection.DBConnection;
import dto.Quyen;

public class QuyenDAO {

    // Lấy toàn bộ 64 quyền có trong hệ thống
    public ArrayList<Quyen> layListQuyen() {
        ArrayList<Quyen> list = new ArrayList<>();
        String sql = "SELECT * FROM Quyen";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                // Đã bỏ maNQ đi theo cấu trúc mới
                Quyen quyen = new Quyen(rs.getString("MaQuyen"), rs.getString("TenQuyen"));
                list.add(quyen);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Tìm kiếm theo tên (Giữ nguyên logic của bạn)
    public boolean timQuyen(String tenQuyen) {
        String sql = "SELECT 1 FROM Quyen WHERE TenQuyen LIKE ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + tenQuyen + "%");
            return ps.executeQuery().next(); // có dòng => true
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}