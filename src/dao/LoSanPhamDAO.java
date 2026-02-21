package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.conection.DBConnection;
import dto.LoSanPham;

public class LoSanPhamDAO {

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
                lo.setGiaNhap(rs.getDouble("GiaNhap"));

                list.add(lo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean truSoLuong(Connection conn, String maSP, int soLuongCanTru) throws SQLException {
        String sqlGet = "SELECT MaLoSP, SoLuong FROM LoSanPham WHERE MaSP = ? AND SoLuong > 0 ORDER BY HanSuDung ASC";
        String sqlUpdate = "UPDATE LoSanPham SET SoLuong = SoLuong - ? WHERE MaLoSP = ?";

        PreparedStatement pstGet = null;
        PreparedStatement pstUpdate = null;
        ResultSet rs = null;

        try {
            pstGet = conn.prepareStatement(sqlGet);
            pstGet.setString(1, maSP);
            rs = pstGet.executeQuery();

            pstUpdate = conn.prepareStatement(sqlUpdate);

            int conLai = soLuongCanTru;

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
                return true;
            } else {
                System.out.println("Lỗi: Kho không đủ hàng (Thiếu " + conLai + ") cho mã " + maSP);
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

    public boolean capNhapLoSanPham(LoSanPham lo, Connection conn) throws SQLException {
        String sql = "UPDATE LoSanPham SET SoLuong = ? WHERE MaLoSP = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, lo.getSoLuong());
            pst.setString(2, lo.getMaLoSP());
            return pst.executeUpdate() > 0;
        }
    }

    public String layMaLoSanPhamKhaDung(Connection conn) {
        if (conn == null) {
            conn = DBConnection.getConnection();
        }
        String sql = "SELECT COUNT(MaLoSP) FROM LoSanPham";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int soLoSp = rs.getInt(1) + 1;
                String ma = String.format("%02d", soLoSp);
                return "LOSP" + ma;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn sản phẩm: " + e.getMessage());
        }
        return "";
    }

    public boolean themLoSanPham(LoSanPham loSP, Connection conn) {

        String sql = "INSERT INTO LoSanPham (MaLoSP, MaPN, MaSP, SoLuong, GiaNhap, NgaySanXuat, HanSuDung, NgayNhap, TrangThaiXuLy,TrangThai) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?,?)";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, layMaLoSanPhamKhaDung(conn));
            pst.setString(2, loSP.getMaPN());
            pst.setString(3, loSP.getMaSP());
            pst.setInt(4, loSP.getSoLuong());
            pst.setDouble(5, loSP.getGiaNhap());

            pst.setString(6, loSP.getNgaySanXuat());
            pst.setString(7, loSP.getHanSuDung());
            pst.setString(8, loSP.getNgayNhap());
            pst.setString(9, loSP.getTrangThaiXuLy());
            pst.setInt(10, 1);

            int result = pst.executeUpdate();

            return result > 0;

        } catch (Exception e) {
            System.out.println("Lỗi khi thêm Lô Sản Phẩm: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}