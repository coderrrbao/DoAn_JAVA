package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.conection.DBConnection;
import dto.MaGiamGia;

public class MaGiamGiaDAO {
    public MaGiamGia getMaGiamGiatheoMa(String maKM) {
        String sql = "SELECT * FROM KhuyenMai WHERE maKM = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, maKM);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                MaGiamGia mgg = new MaGiamGia();
                mgg.setMaKM(rs.getString("MaKM"));
                mgg.setPhanTramGiam(rs.getInt("PhanTramGiam"));
                mgg.setTuNgay(rs.getDate("TuNgay"));
                mgg.setDenNgay(rs.getDate("DenNgay"));
                mgg.setTrangThai(rs.getBoolean("TrangThai"));

                return mgg;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<MaGiamGia> getTatCaMaGiamGia() {
        ArrayList<MaGiamGia> list = new ArrayList<>();
        String sql = "SELECT * FROM KhuyenMai";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                MaGiamGia mgg = new MaGiamGia();
                mgg.setMaKM(rs.getString("MaKM"));
                mgg.setPhanTramGiam(rs.getInt("PhanTramGiam"));
                mgg.setTuNgay(rs.getDate("TuNgay"));
                mgg.setDenNgay(rs.getDate("DenNgay"));
                mgg.setTrangThai(rs.getBoolean("TrangThai"));
                list.add(mgg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
