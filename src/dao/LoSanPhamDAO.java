package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.conection.DBConnection;
import dto.LoSanPham;

public class LoSanPhamDAO {

    // Hàm lấy danh sách (Giữ nguyên logic của bạn)
    public ArrayList<LoSanPham> layListLoSanPham() {
        ArrayList<LoSanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM LoSanPham WHERE TrangThai=1";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                LoSanPham lo = new LoSanPham();
                lo.setMaLoSP(rs.getString("MaLoSP"));
                lo.setMaPN(rs.getString("MaPN"));
                lo.setMaSP(rs.getString("MaSP"));
                lo.setSoLuong(rs.getInt("SoLuong"));
                lo.setNgayNhap(rs.getString("NgayNhap"));
                lo.setNgaySanXuat(rs.getString("NgaySanXuat"));
                lo.setHanSuDung(rs.getString("HanSuDung"));
                lo.setTongTien(rs.getDouble("TongTien"));

                list.add(lo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * HÀM TRỪ KHO CHUẨN TRANSACTION (FIFO)
     * - Nhận Connection từ BUS truyền vào.
     * - KHÔNG tự tạo connection mới.
     * - KHÔNG tự commit/rollback (để BUS lo).
     * - Ném SQLException để BUS biết nếu có lỗi.
     */
    public boolean truSoLuong(Connection conn, String maSP, int soLuongCanTru) throws SQLException {
        // Lấy danh sách lô hàng còn hạn, sắp xếp lô cũ (HSD bé) lên trước
        String sqlGet = "SELECT MaLoSP, SoLuong FROM LoSanPham WHERE MaSP = ? AND SoLuong > 0 ORDER BY HanSuDung ASC";
        String sqlUpdate = "UPDATE LoSanPham SET SoLuong = SoLuong - ? WHERE MaLoSP = ?";

        PreparedStatement pstGet = null;
        PreparedStatement pstUpdate = null;
        ResultSet rs = null;

        try {
            // 1. Lấy danh sách lô (Dùng biến 'conn' được truyền vào)
            pstGet = conn.prepareStatement(sqlGet);
            pstGet.setString(1, maSP);
            rs = pstGet.executeQuery();

            // 2. Chuẩn bị câu lệnh Update
            pstUpdate = conn.prepareStatement(sqlUpdate);

            int conLai = soLuongCanTru;

            while (rs.next() && conLai > 0) {
                String maLo = rs.getString("MaLoSP");
                int slTrongLo = rs.getInt("SoLuong");

                // Tính số lượng cần trừ ở lô này
                int truO_LoNay = (slTrongLo >= conLai) ? conLai : slTrongLo;

                // Thêm vào Batch (Gom lệnh update)
                pstUpdate.setInt(1, truO_LoNay);
                pstUpdate.setString(2, maLo);
                pstUpdate.addBatch();

                conLai -= truO_LoNay;
            }

            // 3. Kiểm tra kết quả
            if (conLai == 0) {
                // Nếu đã đủ hàng để trừ -> Thực thi Update
                pstUpdate.executeBatch();
                return true;
            } else {
                // Nếu thiếu hàng -> Báo lỗi, BUS sẽ nhận được false và Rollback
                System.out.println("Lỗi: Kho không đủ hàng (Thiếu " + conLai + ") cho mã " + maSP);
                return false;
            }

        } finally {
            // CHỈ ĐÓNG ResultSet và Statement
            // TUYỆT ĐỐI KHÔNG ĐÓNG 'conn' Ở ĐÂY
            if (rs != null) rs.close();
            if (pstGet != null) pstGet.close();
            if (pstUpdate != null) pstUpdate.close();
        }
    }

    // Hàm kiểm tra tồn kho (Dùng để check trước khi bán - Giữ nguyên logic)
    public boolean kiemTraDuHang(String maSP, int soLuongCan) {
        String sql = "SELECT SUM(SoLuong) FROM LoSanPham WHERE MaSP = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, maSP);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int tongTonKho = rs.getInt(1);
                return tongTonKho >= soLuongCan;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int layTongTonKhoSanPham(String maSP) {
        String sql = "SELECT ISNULL(SUM(SoLuong), 0) FROM LoSanPham WHERE MaSP = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, maSP);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}