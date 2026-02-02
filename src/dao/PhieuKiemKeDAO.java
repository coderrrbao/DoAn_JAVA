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
                pkk.setSoLuongSoSach(rs.getInt("SoLuongSoSach"));
                pkk.setSoLuongThuc(rs.getInt("SoLuongThuc"));
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

    public Boolean themPhieuKiemKe(PhieuKiemKe pkk) {
        String sql = "INSERT INTO PhieuKiemKe ( MaKK,NgayKiem, MaLo, LoaiLo, SoLuongSoSach, SoLuongThuc, GhiChu, MaNV, TrangThaiXuLy,TrangThai) "
                + "VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, maPKKKhaDung());
            pst.setDate(2, Date.valueOf(pkk.getNgayKiem()));
            pst.setString(3, pkk.getMaLo());
            pst.setString(4, pkk.getLoaiLo());
            pst.setInt(5, pkk.getSoLuongSoSach());
            pst.setInt(6, pkk.getSoLuongThuc());
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
