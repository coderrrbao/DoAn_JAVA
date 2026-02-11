package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.conection.DBConnection;
import dto.LoSanPham;

public class LoSanPhamDAO {
    public ArrayList<LoSanPham> layListLoSanPham() {
        ArrayList<LoSanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM LoSanPham WHERE TrangThai=1";
        try (Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                LoSanPham lo = new LoSanPham();

                lo.setMaLoSP(rs.getString("MaLoSP"));
                lo.setMaPN(rs.getString("MaPN"));
                lo.setMaSP(rs.getString("MaSP"));
                lo.setSoLuong(rs.getInt("SoLuong"));
                lo.setNgayNhap(rs.getString("NgayNhap"));
                lo.setNgaySanXuat(rs.getString("NgaySanXuat"));
                lo.setHanSuDung(rs.getString("HanSuDung"));
                lo.setTongTien(rs.getDouble("TongTien"));

                list.add(lo);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
        return list;

    }
}
