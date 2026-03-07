package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.conection.DBConnection;

import dto.DanhMuc;
import dto.SanPham;

public class SanPhamDAO {

    public ArrayList<SanPham> layListSanPham() {
        ArrayList<SanPham> listSanPham = new ArrayList<>();
        String sql = "SELECT sp.*, dm.TenDM FROM SanPham sp INNER JOIN DanhMuc dm ON sp.MaDM = dm.MaDM WHERE sp.TrangThai = 1";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSP(rs.getString("MaSP"));
                sp.setTenSP(rs.getNString("TenSP"));
                sp.setAnh(rs.getString("Anh"));
                sp.setGiaBan(rs.getLong("GiaBan"));
                sp.setTrangThai(rs.getBoolean("TrangThai"));
                sp.setLoaiNuoc(rs.getString("LoaiNuoc"));
                sp.setTheTich(rs.getInt("TheTich"));
                sp.setMucCanhBao(rs.getInt("MucCanhBao"));
                sp.setTrangThaiXuLy(rs.getString("TrangThaiXuLy"));

                DanhMuc danhMuc = new DanhMuc(rs.getString("MaDM"), rs.getString("TenDM"));
                sp.setDanhMuc(danhMuc);

                listSanPham.add(sp);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn sản phẩm: " + e.getMessage());
        }

        return listSanPham;
    }

    public boolean capNhatMucCanhBao(SanPham sanPham) {
        String sql = "UPDATE SanPham SET MucCanhBao = ? WHERE MaSP = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, sanPham.getMucCanhBao());
            pst.setString(2, sanPham.getMaSP());

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi khi cập nhật mức cảnh báo: " + e.getMessage());
            return false;
        }
    }

    public boolean themSanPham(SanPham sanPham, Connection conn) {
        String sql = "INSERT INTO SanPham (MaSP, TenSP, MaDM, GiaBan, LoaiNuoc, Anh, TheTich, MucCanhBao, TrangThaiXuLy, TrangThai) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {

            if (sanPham.getMaSP() == null || sanPham.getMaSP().trim().isEmpty()) {
                sanPham.setMaSP(layMaSanPhamKhaDung(conn));
            }
            pst.setString(1, sanPham.getMaSP());
            pst.setString(2, sanPham.getTenSP());
            pst.setString(3, sanPham.getDanhMuc() != null ? sanPham.getDanhMuc().getMaDM() : null);
            pst.setDouble(4, sanPham.getGiaBan());
            pst.setString(5, sanPham.getLoaiNuoc());
            pst.setString(6, sanPham.getAnh());
            pst.setInt(7, sanPham.getTheTich());
            pst.setInt(8, sanPham.getMucCanhBao());
            pst.setString(9, sanPham.getTrangThaiXuLy());
            pst.setInt(10, 1);

            int rowAffected = pst.executeUpdate();
            return rowAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi khi thêm sản phẩm: " + e.getMessage());
            return false;
        }
    }

    public boolean insert(SanPham sanPham, Connection conn) {
        String sql = "INSERT INTO SanPham (MaSP, TenSP, MaDM, GiaBan, LoaiNuoc, Anh, TheTich, MucCanhBao, TrangThaiXuLy, TrangThai) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(0, sanPham.getMaSP());
            pst.setString(1, sanPham.getMaSP());
            pst.setString(2, sanPham.getTenSP());
            pst.setString(3, sanPham.getDanhMuc() != null ? sanPham.getDanhMuc().getMaDM() : null);
            pst.setDouble(4, sanPham.getGiaBan());
            pst.setString(5, sanPham.getLoaiNuoc());
            pst.setString(6, sanPham.getAnh());
            pst.setInt(7, sanPham.getTheTich());
            pst.setInt(8, sanPham.getMucCanhBao());
            pst.setString(9, sanPham.getTrangThaiXuLy());
            pst.setInt(10, 1);

            int rowAffected = pst.executeUpdate();
            return rowAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi khi thêm sản phẩm: " + e.getMessage());
            return false;
        }
    }

    public String layMaSanPhamKhaDung(Connection conn) {
        if (conn == null) {
            conn = DBConnection.getConnection();
        }
        String sql = "SELECT COUNT(MaSP) FROM SanPham";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int soSp = rs.getInt(1) + 1;
                String ma = String.format("%02d", soSp);
                return "SP" + ma;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn sản phẩm: " + e.getMessage());
        }
        return "";
    }

    public SanPham timSanPham(String ma) {
        String sql = "SELECT sp.*, dm.TenDM "
                + "FROM SanPham sp "
                + "INNER JOIN DanhMuc dm ON sp.MaDM = dm.MaDM "
                + "WHERE sp.MaSP = ? AND sp.TrangThai = 1";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, ma);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSP(rs.getString("MaSP"));
                sp.setTenSP(rs.getNString("TenSP"));
                sp.setAnh(rs.getString("Anh"));
                sp.setGiaBan(rs.getLong("GiaBan"));
                sp.setTheTich(rs.getInt("TheTich"));
                sp.setMucCanhBao(rs.getInt("MucCanhBao"));
                sp.setTrangThaiXuLy(rs.getString("TrangThaiXuLy"));
                sp.setLoaiNuoc(rs.getString("LoaiNuoc"));

                DanhMuc danhMuc = new DanhMuc(rs.getString("MaDM"), rs.getString("TenDM"));
                sp.setDanhMuc(danhMuc);

                return sp;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn sản phẩm: " + e.getMessage());
        }
        return null;
    }

    public boolean exists(Connection conn, String maSP) {
        if (conn == null || maSP == null)
            return false;
        String sql = "SELECT 1 FROM SanPham WHERE MaSP = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, maSP);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean xoaSanPham(String maSp) {
        String sql = "UPDATE SanPham SET TrangThai=0 WHERE MaSP = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, maSp);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn sản phẩm: " + e.getMessage());
            return false;
        }
        return true;
    }

    public Boolean capNhapSanPham(SanPham sanPham, Connection conn) {
        String sql = "UPDATE SanPham SET TenSP = ?, MaDM = ?, GiaBan = ?, "
                + "LoaiNuoc = ?, Anh = ?, TheTich = ?, MucCanhBao = ?, "
                + "TrangThaiXuLy = ?, TrangThai = ? "
                + "WHERE MaSP = ?";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, sanPham.getTenSP());
            pst.setString(2, sanPham.getDanhMuc().getMaDM());
            pst.setDouble(3, sanPham.getGiaBan());
            pst.setString(4, sanPham.getLoaiNuoc());
            pst.setString(5, sanPham.getAnh());
            pst.setInt(6, sanPham.getTheTich());
            pst.setInt(7, sanPham.getMucCanhBao());
            pst.setString(8, sanPham.getTrangThaiXuLy());
            pst.setInt(9, 1);
            pst.setString(10, sanPham.getMaSP());

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi khi cập nhật sản phẩm: " + e.getMessage());
            return false;
        }
    }
}