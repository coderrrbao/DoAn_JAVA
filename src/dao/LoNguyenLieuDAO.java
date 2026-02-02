package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.conection.DBConnection;
import dto.LoNguyenLieu;

public class LoNguyenLieuDAO {
    public ArrayList<LoNguyenLieu> layListLoNguyenLieu() {
    ArrayList<LoNguyenLieu> list = new ArrayList<>();
    String sql = "SELECT * FROM LoNguyenLieu WHERE TrangThai = 1";
    
    try (Connection con = DBConnection.getConnection(); 
         PreparedStatement pst = con.prepareStatement(sql)) {
        
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            LoNguyenLieu lo = new LoNguyenLieu();

            lo.setMaLoNL(rs.getString("MaLoNL"));
            lo.setMaPN(rs.getString("MaPN"));
            lo.setMaNL(rs.getString("MaNL"));
            lo.setSoLuong(rs.getInt("SoLuong"));
            lo.setNgayNhap(rs.getString("NgayNhap"));
            lo.setNgaySanXuat(rs.getString("NgaySanXuat"));
            lo.setHanSuDung(rs.getString("HanSuDung"));
            lo.setTrangThai(rs.getBoolean("TrangThai"));

            list.add(lo);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}
}
