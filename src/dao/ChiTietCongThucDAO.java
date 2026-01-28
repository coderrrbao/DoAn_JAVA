package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.conection.DBConnection;
import dto.ChiTietCongThuc;
import dto.NguyenLieu;

public class ChiTietCongThucDAO {
    public ArrayList<ChiTietCongThuc> laylistChiTietCongThuc(String maCT) {
        ArrayList<ChiTietCongThuc> listChiTietCongThuc = new ArrayList<>();

        String sql = "SELECT * FROM ChiTietCongThuc WHERE TrangThai=1 and MaCT=?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, maCT);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                ChiTietCongThuc chiTietCongThuc = new ChiTietCongThuc(rs.getString("MaCTCT"), rs.getString("MaCT"),
                        new NguyenLieu(rs.getString("MaNL"), "", null, 0, "", 0, false),
                        Double.parseDouble(rs.getString("SoLuong")));
                listChiTietCongThuc.add(chiTietCongThuc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn sản phẩm: " + e.getMessage());
        }

        return listChiTietCongThuc;
    }

    public String layMaChiTietCongThucKhaDung() {
        String sql = "SELECT COUNT(*) FROM ChiTietCongThuc";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int so = rs.getInt(1) + 1;
                String ma = String.format("%02d", so);
                return "CTCT" + ma;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn ChiTietCongThuc: " + e.getMessage());
        }
        return "";
    }

    public boolean themCTCT(ChiTietCongThuc ctct) {
        String sql = "INSERT INTO ChiTietCongThuc (MaCTCT, MaCT, MaNL, SoLuong,TrangThai) VALUES (?, ?, ?, ?,?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (ctct.getMaCTCT() == null || ctct.getMaCTCT().trim().isEmpty()) {
                ctct.setMaCTCT(layMaChiTietCongThucKhaDung());
            }
            pstmt.setString(1, ctct.getMaCTCT());
            pstmt.setString(2, ctct.getMaCT());
            pstmt.setString(3, ctct.getNguyenLieu().getMaNL());
            pstmt.setDouble(4, ctct.getSoLuong());
            pstmt.setInt(5, 1);
            int rowAffected = pstmt.executeUpdate();
            return rowAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaCTCT(ChiTietCongThuc chiTietCongThuc) {
        String sql = "UPDATE ChiTietCongThuc SET TrangThai=0 WHERE MaCTCT=?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, chiTietCongThuc.getMaCTCT());
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
