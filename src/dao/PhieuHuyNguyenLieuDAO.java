package dao;

import dao.conection.DBConnection;
import dto.PhieuHuyNguyenLieu;
import java.sql.*;
import java.util.ArrayList;

public class PhieuHuyNguyenLieuDAO {

    /**
     * Lưu phiếu hủy và trừ tồn kho trong cùng một Transaction
     */
    public boolean luuPhieuHuy(String maNV, String lyDo, Object[][] dataTable) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false); // Bắt đầu giao dịch

            String sqlInsert = "INSERT INTO PhieuHuyNguyenLieu (MaPH, MaLo, NgayHuy, MaNV, LyDo, TongTien, TrangThaiXuLy, TrangThai) VALUES (?, ?, GETDATE(), ?, ?, ?, N'Đã xử lý', 1)";
            String sqlUpdateStock = "UPDATE LoNguyenLieu SET SoLuong = SoLuong - ? WHERE MaLo = ?";

            PreparedStatement pstInsert = conn.prepareStatement(sqlInsert);
            PreparedStatement pstUpdate = conn.prepareStatement(sqlUpdateStock);

            for (Object[] row : dataTable) {
                // Thứ tự row từ Dialog truyền xuống: [0]:MaNL, [1]:TenNL, [2]:SoLuong, [3]:MaLo
                String maNL = row[0].toString();
                double slHuy = Double.parseDouble(row[2].toString());
                String maLo = row[3].toString();
                String maPH = "PHNL" + System.currentTimeMillis() % 100000 + (int)(Math.random() * 100);

                // 1. Thêm vào Batch câu lệnh Insert phiếu hủy
                pstInsert.setString(1, maPH);
                pstInsert.setString(2, maLo);
                pstInsert.setString(3, maNV);
                pstInsert.setString(4, lyDo);
                pstInsert.setDouble(5, 0.0); // Bạn có thể tính tổng tiền dựa trên đơn giá lô nếu cần
                pstInsert.addBatch();

                // 2. Thêm vào Batch câu lệnh Update trừ tồn kho
                pstUpdate.setDouble(1, slHuy);
                pstUpdate.setString(2, maLo);
                pstUpdate.addBatch();
            }

            pstInsert.executeBatch();
            pstUpdate.executeBatch();

            conn.commit(); // Xác nhận thành công toàn bộ
            return true;
        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback(); // Hoàn nguyên nếu có lỗi
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Lấy danh sách phiếu hủy để hiển thị lên Table (Giải quyết lỗi undefined ở BUS)
     */
    public ArrayList<PhieuHuyNguyenLieu> layListPhieuHuy() {
        ArrayList<PhieuHuyNguyenLieu> list = new ArrayList<>();
        String sql = "SELECT * FROM PhieuHuyNguyenLieu WHERE TrangThai=1";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            
            while (rs.next()) {
                PhieuHuyNguyenLieu ph = new PhieuHuyNguyenLieu();
                ph.setMaPH(rs.getString("MaPH"));
                ph.setMaLo(rs.getString("MaLo"));
                ph.setNgayHuy(rs.getDate("NgayHuy"));
                ph.setMaNV(rs.getString("MaNV"));
                ph.setLyDo(rs.getString("LyDo"));
                ph.setTongTien(rs.getDouble("TongTien"));
                // Giả sử DTO của bạn có setTrangThaiXuLy
                // ph.setTrangThaiXuLy(rs.getString("TrangThaiXuLy")); 
                list.add(ph);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}