package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.conection.DBConnection;
import dto.LoSanPham;

public class LoSanPhamDAO {
    public ArrayList<LoSanPham> layListLoSanPham() {
        ArrayList<LoSanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM LoSanPham WHERE TrangThai=1";
        try (Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
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
            return list;
        }
        return list;

    }

    public boolean truSoLuong(String maSP, int soLuongCanTru) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            try (PreparedStatement pstGet = conn.prepareStatement(
                    "SELECT MaLoSP, SoLuong FROM LoSanPham WHERE MaSP = ? AND SoLuong > 0 ORDER BY HanSuDung ASC")) {
                pstGet.setString(1, maSP);

                try (ResultSet rs = pstGet.executeQuery()) {
                    int conLai = soLuongCanTru;
                    try (PreparedStatement pstUpdate = conn.prepareStatement(
                            "UPDATE LoSanPham SET SoLuong = SoLuong - ? WHERE MaLoSP = ?")) {

                        while (rs.next() && conLai > 0) {
                            String maLo = rs.getString("MaLoSP");
                            int slTrongLo = rs.getInt("SoLuong");
                            int truO_LoNay = (slTrongLo >= conLai) ? conLai : slTrongLo;

                            pstUpdate.setInt(1, truO_LoNay);
                            pstUpdate.setString(2, maLo);
                            pstUpdate.addBatch();

                            conLai -= truO_LoNay;
                        }

                        if (conLai == 0) {
                            pstUpdate.executeBatch();
                            conn.commit();
                            return true;
                        } else {
                            conn.rollback();
                            System.out.println("Lỗi: Kho không đủ hàng (Thiếu " + conLai + ") cho mã " + maSP);
                            return false;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null)
                    conn.rollback();
            } catch (Exception ex) {
            } // Hủy nếu lỗi sập mạng/code
            return false;
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
            } // Chỉ cần đóng mỗi Conn
        }
    }

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

    public boolean capNhapLoSanPham(LoSanPham loSanPham, Connection conn) {
        String sql = "UPDATE LoSanPham SET MaPN = ?, MaSP = ?, SoLuong = ?, NgayNhap = ?, NgaySanXuat = ?, HanSuDung = ?, TongTien = ? WHERE MaLoSP = ?";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, loSanPham.getMaPN());
            pst.setString(2, loSanPham.getMaSP());
            pst.setInt(3, loSanPham.getSoLuong());
            pst.setString(4, loSanPham.getNgayNhap());
            pst.setString(5, loSanPham.getNgaySanXuat());
            pst.setString(6, loSanPham.getHanSuDung());
            pst.setDouble(7, loSanPham.getTongTien());
            pst.setString(8, loSanPham.getMaLoSP());

            int rowCount = pst.executeUpdate();
            return rowCount > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
