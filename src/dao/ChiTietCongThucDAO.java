package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.conection.DBConnection;
import dto.ChiTietCongThuc;

public class ChiTietCongThucDAO {
    public ArrayList<ChiTietCongThuc> laylistChiTietCongThuc(String maCT) {
        ArrayList<ChiTietCongThuc> listChiTietCongThuc = new ArrayList<>();

        String sql = "SELECT * FROM ChiTietCongThuc";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                if (rs.getString("MaCT").equals(maCT)) {
                    ChiTietCongThuc chiTietCongThuc = new ChiTietCongThuc(rs.getString("MaCTCT"),Integer.parseInt(rs.getString("SoLuong")));
                    listChiTietCongThuc.add(chiTietCongThuc);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn sản phẩm: " + e.getMessage());
        }

        return listChiTietCongThuc;
    }
}
