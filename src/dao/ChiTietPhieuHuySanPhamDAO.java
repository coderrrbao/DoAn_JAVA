package dao;

import dao.conection.DBConnection;
import dto.ChiTietPhieuHuySanPham;
import dto.LoSanPham;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ChiTietPhieuHuySanPhamDAO {

    public ArrayList<ChiTietPhieuHuySanPham> layChiTietHuyTheoMaPH(String maPH) {
        ArrayList<ChiTietPhieuHuySanPham> list = new ArrayList<>();
        // SQL đơn giản, không JOIN
        String sql = "SELECT * FROM ChiTietPhieuHuySanPham WHERE MaPH = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, maPH);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    ChiTietPhieuHuySanPham ct = new ChiTietPhieuHuySanPham();
                    ct.setMaPH(rs.getString("MaPH"));
                    ct.setSoLuong(rs.getDouble("SoLuong"));
                    ct.setDonGia(rs.getDouble("DonGia"));

                    // --- KHỞI TẠO LÔ MỚI VÀ SET MÃ ---
                    LoSanPham lo = new LoSanPham();
                    lo.setMaLoSP(rs.getString("MaLo")); // Lấy mã từ cột MaLoSP của bảng chi tiết
                    
                    ct.setLoSanPham(lo);
                    // ---------------------------------

                    list.add(ct);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}