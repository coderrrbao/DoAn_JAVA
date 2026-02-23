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
                ct.setMaCTNCC(rs.getString("MaCTNCC"));
                ct.setMaNCC(rs.getString("MaNCC"));
                ct.setLoaiDoiTuong(rs.getString("LoaiDoiTuong"));
                ct.setMaDoiTuong(rs.getString("MaDoiTuong"));
                ct.setGiaNhap(rs.getDouble("GiaNhap"));

                listCTNCC.add(ct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listCTNCC;
    }

    public boolean themChiTietNhaCungCap(ChiTietNhaCungCap ct, Connection conn) {
        String sql = "INSERT INTO ChiTietNhaCungCap (MaCTNCC, MaNCC, LoaiDoiTuong, MaDoiTuong, GiaNhap, TrangThai) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, layMaCTNCCKhaDung(conn));
            pst.setString(2, ct.getMaNCC());
            pst.setNString(3, ct.getLoaiDoiTuong());
            pst.setString(4, ct.getMaDoiTuong());
            pst.setDouble(5, ct.getGiaNhap());
            pst.setInt(6, 1);

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String layMaCTNCCKhaDung(Connection conn) {
        String sql = "SELECT COUNT(*) FROM ChiTietNhaCungCap";
        try (PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                int count = rs.getInt(1);
                return String.format("CTNCC%03d", count + 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "CTNCC001";
    }

    public boolean capNhapChiTietNhaCungCap(ChiTietNhaCungCap ct, Connection conn) {
        String sql = "UPDATE ChiTietNhaCungCap SET MaNCC = ?, LoaiDoiTuong = ?, MaDoiTuong = ?, GiaNhap = ? "
                + "WHERE MaCTNCC = ?";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, ct.getMaNCC());
            pst.setNString(2, ct.getLoaiDoiTuong());
            pst.setString(3, ct.getMaDoiTuong());
            pst.setDouble(4, ct.getGiaNhap());
            pst.setString(5, ct.getMaCTNCC()); // Kiểu String

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaChiTietNhaCungCap(String maCTNCC, Connection conn) {
        // Tham số truyền vào đổi từ int sang String
        String sql = "UPDATE ChiTietNhaCungCap SET TrangThai = 0 WHERE MaCTNCC = ?";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, maCTNCC);

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}