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
                lo.setTrangThai(rs.getBoolean("TrangThai"));

                list.add(lo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean kiemTraDuNguyenLieu(String maNL, double soLuongCan) {

        String sql = "SELECT SUM(SoLuong) FROM LoNguyenLieu WHERE MaNL = ?";
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
        String sqlGet = "SELECT MaLoNL, SoLuong FROM LoNguyenLieu WHERE MaNL = ? AND SoLuong > 0 ORDER BY HanSuDung ASC";
        String sqlUpdate = "UPDATE LoNguyenLieu SET SoLuong = SoLuong - ? WHERE MaLoNL = ?";

        PreparedStatement pstGet = null;
        PreparedStatement pstUpdate = null;
        ResultSet rs = null;

        try {
            // 1. Lấy danh sách lô hàng (Dùng conn được truyền vào)
            pstGet = conn.prepareStatement(sqlGet);
            pstGet.setString(1, maNL);
            rs = pstGet.executeQuery();

            // Chuẩn bị câu lệnh Update
            pstUpdate = conn.prepareStatement(sqlUpdate);

            double conLai = soLuongCanTru;

            while (rs.next() && conLai > 0.0001) {
                String maLo = rs.getString("MaLoNL");
                double slTrongLo = rs.getDouble("SoLuong");

                // Tính toán lượng trừ
                double truO_LoNay = (slTrongLo >= conLai) ? conLai : slTrongLo;

                // Thêm vào Batch (Gom lệnh lại chạy 1 lần cho nhanh)
                pstUpdate.setDouble(1, truO_LoNay);
                pstUpdate.setString(2, maLo);
                pstUpdate.addBatch();

                conLai -= truO_LoNay;
            }

            // 2. Kiểm tra kết quả
            if (conLai <= 0.0001) {
                // Nếu kho đủ hàng -> Chạy lệnh Update
                pstUpdate.executeBatch();
                return true;
            } else {
                // Nếu kho thiếu hàng -> Không làm gì cả, trả về false
                // BUS sẽ nhận được false và tự Rollback toàn bộ
                System.out.println("Kho thiếu nguyên liệu: " + maNL + " (Còn thiếu: " + conLai + ")");
                return false;
            }

        } finally {
            // QUAN TRỌNG: Chỉ đóng ResultSet và PreparedStatement
            // TUYỆT ĐỐI KHÔNG ĐÓNG 'conn' Ở ĐÂY
            if (rs != null) rs.close();
            if (pstGet != null) pstGet.close();
            if (pstUpdate != null) pstUpdate.close();
        }
    }
}
