package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.conection.DBConnection;
import dto.PhieuKiemKe;

public class PhieuKiemKeDAO {
    public ArrayList<PhieuKiemKe> layListPhieuKiemKe() {
        ArrayList<PhieuKiemKe> list = new ArrayList<>();
        String sql = "SELECT * FROM PhieuKiemKe WHERE TrangThai = 1";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)) {

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                PhieuKiemKe pkk = new PhieuKiemKe();

                pkk.setMaKK(rs.getString("MaKK"));
                pkk.setNgayKiem(rs.getString("NgayKiem"));
                pkk.setMaLo(rs.getString("MaLo"));
                pkk.setLoaiLo(rs.getString("LoaiLo"));
                pkk.setSoLuongSoSach(rs.getDouble("SoLuongSoSach"));
                pkk.setSoLuongThuc(rs.getDouble("SoLuongThuc"));
                pkk.setGhiChu(rs.getString("GhiChu"));
                pkk.setMaNV(rs.getString("MaNV"));
                pkk.setTrangThaiXuLy(rs.getString("TrangThaiXuLy"));
                list.add(pkk);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String maPKKKhaDung() {
        String sql = "SELECT COUNT(MaKK) AS tong FROM PhieuKiemKe";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)) {

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("tong") + 1;
                return String.format("PKK%03d", count);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "PKK001";
    }

    public Boolean themPhieuKiemKe(PhieuKiemKe pkk, Connection conn) {
        String sql = "INSERT INTO PhieuKiemKe ( MaKK,NgayKiem, MaLo, LoaiLo, SoLuongSoSach, SoLuongThuc, GhiChu, MaNV, TrangThaiXuLy,TrangThai) "
                + "VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, maPKKKhaDung());
            pst.setDate(2, Date.valueOf(pkk.getNgayKiem()));
            pst.setString(3, pkk.getMaLo());
            pst.setString(4, pkk.getLoaiLo());
            pst.setDouble(5, pkk.getSoLuongSoSach());
            pst.setDouble(6, pkk.getSoLuongThuc());
            pst.setString(7, pkk.getGhiChu());
            pst.setString(8, pkk.getMaNV());
            pst.setString(9, pkk.getTrangThaiXuLy());
            pst.setInt(10, 1);
            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhapPhieuKiemKe(PhieuKiemKe phieuKiemKe, Connection conn) {
        String sql = "UPDATE PhieuKiemKe  SET MaLo=?,LoaiLo=?,SoLuongSoSach=?,SoLuongThuc=?,MaNV=?,GhiChu=?,TrangThaiXuLy=? WHERE MaKK=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, phieuKiemKe.getMaLo());
            pst.setString(2, phieuKiemKe.getLoaiLo());
            pst.setDouble(3, phieuKiemKe.getSoLuongSoSach());
            pst.setDouble(4, phieuKiemKe.getSoLuongThuc());
            pst.setString(5, phieuKiemKe.getMaNV());
            pst.setString(6, phieuKiemKe.getGhiChu());
            pst.setString(7, phieuKiemKe.getTrangThaiXuLy());
            pst.setString(8, phieuKiemKe.getMaKK());
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaPhieuKiemKe(PhieuKiemKe phieuKiemKe, Connection conn) {
        String sql = "UPDATE PhieuKiemKe  SET TrangThai=? WHERE MaKK=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, 0);
            pst.setString(2, phieuKiemKe.getMaKK());
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean insert(PhieuKiemKe pkk, Connection conn) {
        String sql = "INSERT INTO PhieuKiemKe ( MaKK,NgayKiem, MaLo, LoaiLo, SoLuongSoSach, SoLuongThuc, GhiChu, MaNV, TrangThaiXuLy,TrangThai) "
                + "VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, pkk.getMaKK());
            pst.setString(2, pkk.getNgayKiem());
            pst.setString(3, pkk.getMaLo());
            pst.setString(4, pkk.getLoaiLo());
            pst.setDouble(5, pkk.getSoLuongSoSach());
            pst.setDouble(6, pkk.getSoLuongThuc());
            pst.setString(7, pkk.getGhiChu());
            pst.setString(8, pkk.getMaNV());
            pst.setString(9, pkk.getTrangThaiXuLy());
            pst.setInt(10, 1);
            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
