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

    public String layMaPhieuNhapSPKhaDung(Connection conn) {
        if (conn == null) {
            conn = DBConnection.getConnection();
        }
        String sql = "SELECT COUNT(MaPN) FROM PhieuNhapSanPham";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int soPN = rs.getInt(1) + 1;
                String ma = String.format("%02d", soPN);
                return "PNSP" + ma;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean themPhieuNhapSanPham(PhieuNhapSanPham phieuNhapSanPham, Connection conn) {
        String sql = "INSERT INTO PhieuNhapSanPham(MaPN, NgayNhap, MaNV, TongTien, MaNCC,GhiChu ,TrangThaiXuLy, TrangThai) VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, phieuNhapSanPham.getMaPN());
            pst.setString(2, phieuNhapSanPham.getNgayNhap());
            pst.setString(3, phieuNhapSanPham.getMaNV());
            pst.setDouble(4, phieuNhapSanPham.getTongTien());
            pst.setString(5, phieuNhapSanPham.getMaNCC());
            pst.setString(6, phieuNhapSanPham.getGhiChu());
            pst.setString(7, phieuNhapSanPham.getTrangThaiXuLy());
            pst.setInt(8, 1);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
