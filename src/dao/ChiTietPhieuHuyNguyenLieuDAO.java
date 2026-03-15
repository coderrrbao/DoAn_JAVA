package dao;

import dao.conection.DBConnection;
import dto.ChiTietPhieuHuyNguyenLieu;
import dto.LoNguyenLieu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ChiTietPhieuHuyNguyenLieuDAO {

    public ArrayList<ChiTietPhieuHuyNguyenLieu> layChiTietHuyTheoMaPH(String maPH) {
        ArrayList<ChiTietPhieuHuyNguyenLieu> list = new ArrayList<>();

        String sql = "SELECT * FROM ChiTietPhieuHuyNguyenLieu WHERE MaPH = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, maPH);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    ChiTietPhieuHuyNguyenLieu ct = new ChiTietPhieuHuyNguyenLieu();
                    ct.setMaPH(rs.getString("MaPH"));
                    ct.setSoLuong(rs.getDouble("SoLuong"));
                    ct.setDonGia(rs.getDouble("DonGia"));

                    LoNguyenLieu lo = new LoNguyenLieu();
                    lo.setMaLoNL(rs.getString("MaLo"));

                    ct.setLoNguyenLieu(lo);

                    list.add(ct);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}