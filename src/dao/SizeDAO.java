package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.conection.DBConnection;
import dto.Size;

public class SizeDAO {
    public ArrayList<Size> layListSizeChoSP(String ma) {
        ArrayList<Size> listSize = new ArrayList<>();
        String sql = "SELECT * FROM Size WHERE TrangThai = 1 AND MaSP=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, ma);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Size size = new Size(rs.getString("MaSize"), rs.getString("MaSP"), rs.getString("TenSize"),
                        rs.getInt("PhanTramGia"), rs.getInt("PhanTramNL"));
                listSize.add(size);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn : " + e.getMessage());
        }
        return listSize;
    }

}
