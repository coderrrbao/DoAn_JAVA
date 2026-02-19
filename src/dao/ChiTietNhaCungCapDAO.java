package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.conection.DBConnection;
import dto.ChiTietNhaCungCap;

public class ChiTietNhaCungCapDAO {

    public ArrayList<ChiTietNhaCungCap> layListChiTietNhaCungCap() {
        ArrayList<ChiTietNhaCungCap> listCTNCC = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietNhaCungCap WHERE TrangThai = 1";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                ChiTietNhaCungCap ct = new ChiTietNhaCungCap();
                ct.setMaNCCDT(rs.getInt("MaNCCDT"));
                ct.setMaNCC(rs.getString("MaNCC"));
                ct.setLoaiDoiTuong(rs.getString("LoaiDoiTuong"));
                ct.setMaDoiTuong(rs.getString("MaDoiTuong"));
                ct.setGiaNhap(rs.getDouble("GiaNhap"));

                listCTNCC.add(ct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn danh sách CTNCC: " + e.getMessage());
        }
        return listCTNCC;
    }

    public boolean themChiTietNhaCungCap(ChiTietNhaCungCap ct, Connection conn) {
        String sql = "INSERT INTO ChiTietNhaCungCap (MaNCC, LoaiDoiTuong, MaDoiTuong, GiaNhap, TrangThai) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, ct.getMaNCC());
            pst.setString(2, ct.getLoaiDoiTuong());
            pst.setString(3, ct.getMaDoiTuong());
            pst.setDouble(4, ct.getGiaNhap());
            pst.setInt(5, 1);

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi thêm CTNCC: " + e.getMessage());
            return false;
        }
    }

    public boolean capNhatChiTietNhaCungCap(ChiTietNhaCungCap ct, Connection conn) {
        String sql = "UPDATE ChiTietNhaCungCap SET MaNCC = ?, LoaiDoiTuong = ?, MaDoiTuong = ?, GiaNhap = ? "
                + "WHERE MaNCCDT = ?";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, ct.getMaNCC());
            pst.setString(2, ct.getLoaiDoiTuong());
            pst.setString(3, ct.getMaDoiTuong());
            pst.setDouble(4, ct.getGiaNhap());
            pst.setInt(5, ct.getMaNCCDT());

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi cập nhật CTNCC: " + e.getMessage());
            return false;
        }
    }

    public boolean xoaChiTietNhaCungCap(int maNCCDT, Connection conn) {
        String sql = "UPDATE ChiTietNhaCungCap SET TrangThai = 0 WHERE MaNCCDT = ?";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, maNCCDT);

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi xóa mềm CTNCC: " + e.getMessage());
            return false;
        }
    }
}