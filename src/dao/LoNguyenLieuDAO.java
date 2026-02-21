package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                lo.setTrangThaiXuLy(rs.getBoolean("TrangThaiXuLy"));

                list.add(lo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean kiemTraDuNguyenLieu(String maNL, double soLuongCan) {

        String sql = "SELECT SUM(SoLuong) FROM LoNguyenLieu WHERE MaNL = ? AND TrangThai=1";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, maNL);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                double tongTon = rs.getDouble(1);
                return tongTon >= soLuongCan;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean truNguyenLieu(Connection conn, String maNL, double soLuongCanTru) throws SQLException {
        String sqlGet = "SELECT MaLoNL, SoLuong FROM LoNguyenLieu WHERE MaNL = ? AND SoLuong > 0 ORDER BY HanSuDung ASC ";
        String sqlUpdate = "UPDATE LoNguyenLieu SET SoLuong = SoLuong - ? WHERE MaLoNL = ? AND TrangThai=1";

        PreparedStatement pstGet = null;
        PreparedStatement pstUpdate = null;
        ResultSet rs = null;

        try {
            pstGet = conn.prepareStatement(sqlGet);
            pstGet.setString(1, maNL);
            rs = pstGet.executeQuery();

            pstUpdate = conn.prepareStatement(sqlUpdate);

            double conLai = soLuongCanTru;

            while (rs.next() && conLai > 0.0001) {
                String maLo = rs.getString("MaLoNL");
                double slTrongLo = rs.getDouble("SoLuong");

                double truO_LoNay = (slTrongLo >= conLai) ? conLai : slTrongLo;

                pstUpdate.setDouble(1, truO_LoNay);
                pstUpdate.setString(2, maLo);
                pstUpdate.addBatch();

                conLai -= truO_LoNay;
            }

            if (conLai <= 0.0001) {
                pstUpdate.executeBatch();
                return true;
            } else {
                System.out.println("Kho thiếu nguyên liệu: " + maNL + " (Còn thiếu: " + conLai + ")");
                return false;
            }

        } finally {
            if (rs != null)
                rs.close();
            if (pstGet != null)
                pstGet.close();
            if (pstUpdate != null)
                pstUpdate.close();
        }
    }

    public boolean capNhapLoNguyenLieu(LoNguyenLieu lo, Connection conn) throws SQLException {
        String sql = "UPDATE LoNguyenLieu SET SoLuong = ? WHERE MaLoNL = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setDouble(1, lo.getSoLuong());
            pst.setString(2, lo.getMaLoNL());
            return pst.executeUpdate() > 0;
        }
    }
}
