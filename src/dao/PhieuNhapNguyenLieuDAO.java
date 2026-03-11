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

    public String layMaPhieuNhapNLKhaDung(Connection conn) {
        if (conn == null) {
            conn = DBConnection.getConnection();
        }
        String sql = "SELECT COUNT(MaPN) FROM PhieuNhapNguyenLieu";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int soPN = rs.getInt(1) + 1;
                String ma = String.format("%02d", soPN);
                return "PNNL" + ma;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean themPhieuNhapNguyenLieu(PhieuNhapNguyenLieu phieuNhapNguyenLieu, Connection conn) {
        String sql = "INSERT INTO PhieuNhapNguyenLieu(MaPN, NgayNhap, MaNV, TongTien, MaNCC, GhiChu, TrangThaiXuLy, TrangThai) VALUES (?,?,?,?,?,?,?,?)";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, phieuNhapNguyenLieu.getMaPN());
            pst.setString(2, phieuNhapNguyenLieu.getNgayNhap());
            pst.setString(3, phieuNhapNguyenLieu.getMaNV());
            pst.setDouble(4, phieuNhapNguyenLieu.getTongTien());
            pst.setString(5, phieuNhapNguyenLieu.getMaNCC());
            pst.setString(6, phieuNhapNguyenLieu.getGhiChu());
            pst.setString(7, phieuNhapNguyenLieu.getTrangThaiXuLy());
            pst.setInt(8, 1);

            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean exist(String maPN, Connection conn) {
        String sql = "SELECT 1 FROM PhieuNhapNguyenLieu WHERE MaPN = ?";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, maPN);
            ResultSet rs = pst.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean capNhapPhieuNhapNguyenLieu(PhieuNhapNguyenLieu phieuNhapNguyenLieu, Connection conn) {
        String sql = "UPDATE PhieuNhapNguyenLieu SET GhiChu=? , TrangThaiXuLy=? WHERE MaPN=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, phieuNhapNguyenLieu.getGhiChu());
            pst.setString(2, phieuNhapNguyenLieu.getTrangThaiXuLy());
            pst.setString(3, phieuNhapNguyenLieu.getMaPN());
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaPhieuNhapNguyenLieu(PhieuNhapNguyenLieu phieuNhapNguyenLieu, Connection conn) {

        String sql = "UPDATE PhieuNhapNguyenLieu SET TrangThai=? WHERE MaPN=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, 0);
            pst.setString(2, phieuNhapNguyenLieu.getMaPN());
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}