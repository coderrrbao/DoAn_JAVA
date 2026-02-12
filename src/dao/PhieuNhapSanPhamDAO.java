package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.conection.DBConnection;
import dto.PhieuNhapSanPham;

public class PhieuNhapSanPhamDAO {
    public ArrayList<PhieuNhapSanPham> layListPhieuNhapSanPham() {
        ArrayList<PhieuNhapSanPham> listPhieuNhapSanPham = new ArrayList<>();
        String sql = "SELECT * FROM PhieuNhapSanPham WHERE TrangThai=1";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                PhieuNhapSanPham phieuNhapSanPham = new PhieuNhapSanPham();
                phieuNhapSanPham.setMaPN(rs.getString("MaPN"));
                phieuNhapSanPham.setNgayNhap(rs.getString("NgayNhap"));
                phieuNhapSanPham.setMaNV(rs.getString("MaNV"));
                phieuNhapSanPham.setTongTien(rs.getDouble("TongTien"));
                phieuNhapSanPham.setMaNCC(rs.getString("MaNCC"));
                phieuNhapSanPham.setTrangThaiXuLy(rs.getString("TrangThaiXuLy"));
                phieuNhapSanPham.setGhiChu(rs.getString("GhiChu"));
                listPhieuNhapSanPham.add(phieuNhapSanPham);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listPhieuNhapSanPham;
    }
}
