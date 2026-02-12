package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.conection.DBConnection;
import dto.PhieuNhapNguyenLieu;

public class PhieuNhapNguyenLieuDAO {
    
    public ArrayList<PhieuNhapNguyenLieu> layListPhieuNhapNguyenLieu() {
        ArrayList<PhieuNhapNguyenLieu> listPhieuNhapNguyenLieu = new ArrayList<>();
        String sql = "SELECT * FROM PhieuNhapNguyenLieu WHERE TrangThai=1";
        
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                PhieuNhapNguyenLieu phieuNhap = new PhieuNhapNguyenLieu();
                phieuNhap.setMaPN(rs.getString("MaPN")); 
                phieuNhap.setNgayNhap(rs.getString("NgayNhap"));
                phieuNhap.setMaNV(rs.getString("MaNV"));
                phieuNhap.setTongTien(rs.getDouble("TongTien"));
                phieuNhap.setMaNCC(rs.getString("MaNCC"));
                phieuNhap.setTrangThaiXuLy(rs.getString("TrangThaiXuLy"));
                phieuNhap.setGhiChu(rs.getString("GhiChu"));
                
                listPhieuNhapNguyenLieu.add(phieuNhap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listPhieuNhapNguyenLieu;
    }
}