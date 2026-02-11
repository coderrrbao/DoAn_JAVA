package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import dao.conection.DBConnection;
import dto.HoaDon;
import dto.MaGiamGia;
import dto.NhanVien;

public class HoaDonDAO {

    public boolean themHoaDon(HoaDon hd) {
        String sql = "INSERT INTO HoaDon (MaHD, MaNV, MaKH, MaKM, NgayBan, TongTien, TienKhuyenMai, TrangThai) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, hd.getMaHD());
            if (hd.getNhanVien() != null) {
                pst.setString(2, hd.getNhanVien().getMaNV());
            } else {
                pst.setNull(2, java.sql.Types.VARCHAR);
            }

            pst.setString(3, hd.getMaKH());

            if (hd.getMaGiamGia() != null) {
                pst.setString(4, hd.getMaGiamGia().getMaKM());
            } else {
                pst.setNull(4, java.sql.Types.VARCHAR);
            }

            pst.setDate(5, hd.getNgayBan());
            pst.setDouble(6, hd.getTongTien());
            pst.setDouble(7, hd.getTienKhuyenMai());
            pst.setBoolean(8, hd.getTrangThai());

            int rowAffected = pst.executeUpdate();
            return rowAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi thêm hóa đơn: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<HoaDon> layDanhSachHoaDon() {
        return new ArrayList<>();
    }

    public String layMaHoaDonCuoiCung() {
        String sql = "SELECT TOP 1 MaHD FROM HoaDon ORDER BY MaHD DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                return rs.getString("MaHD");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}