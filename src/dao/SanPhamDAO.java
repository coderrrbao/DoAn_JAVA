package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import dao.conection.DBConnection;
import dto.DanhMuc;
import dto.NhaCungCap;
import dto.SanPham;

public class SanPhamDAO {
    public ArrayList<SanPham> layListSanPham() {
        ArrayList<SanPham> listSanPham = new ArrayList<>();
        String sql = "SELECT *, dm.TenDM, ncc.TenNCC FROM SanPham sp INNER JOIN DanhMuc dm ON sp.MaDM = dm.MaDM INNER JOIN NhaCungCap ncc ON sp.MaNCC = ncc.MaNCC WHERE sp.TrangThai = 1";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSP(rs.getString("MaSP"));
                sp.setTenSP(rs.getNString("TenSP"));
                sp.setAnh(rs.getString("Anh"));
                sp.setGiaNhap(rs.getLong("GiaNhap"));
                sp.setGiaBan(rs.getLong("GiaBan"));
                sp.setSoLuongTon(rs.getInt("SoLuongTon"));
                sp.setTrangThai(rs.getBoolean("TrangThai"));
                sp.setLoaiNuoc(rs.getString("LoaiNuoc"));
                sp.setTheTich(rs.getInt("TheTich"));
                sp.setMucCanhBao(rs.getInt("MucCanhBao"));
                sp.setTrangThaiXuLy(rs.getString("TrangThaiXuLy"));
                NhaCungCap ncc = new NhaCungCap(rs.getString("MaNCC"), rs.getString("TenNCC"),
                        rs.getString("SoDienThoai"), rs.getString("DiaChi"));
                DanhMuc danhMuc = new DanhMuc(rs.getString("MaDM"), rs.getString("TenDM"));
                sp.setDanhMuc(danhMuc);
                sp.setNhaCungCap(ncc);
                listSanPham.add(sp);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn sản phẩm: " + e.getMessage());
        }

        return listSanPham;
    }

    public ArrayList<String> layLuaChonNCC() {
        ArrayList<String> list = new ArrayList<>();
        String sql = "SELECT TenNCC FROM NhaCungCap";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("TenNCC"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn sản phẩm: " + e.getMessage());
        }
        return list;
    }

    public boolean themSanPham(SanPham sanPham) {
        String sql = "INSERT INTO SanPham (MaSP, TenSP, MaDM, GiaNhap, GiaBan, MaNCC, SoLuongTon, LoaiNuoc, Anh, TheTich, MucCanhBao, TrangThaiXuLy,TrangThai) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, sanPham.getMaSP());
            pst.setString(2, sanPham.getTenSP());
            pst.setString(3, sanPham.getDanhMuc().getMaDM());
            pst.setDouble(4, sanPham.getGiaNhap());
            pst.setDouble(5, sanPham.getGiaBan());
            pst.setString(6, sanPham.getNhaCungCap().getMaNCC());
            pst.setInt(7, sanPham.getSoLuongTon());
            pst.setString(8, sanPham.getLoaiNuoc());
            pst.setString(9, sanPham.getAnh());
            pst.setDouble(10, sanPham.getTheTich());
            pst.setInt(11, sanPham.getMucCanhBao());
            pst.setString(12, sanPham.getTrangThaiXuLy());
            pst.setInt(13, sanPham.getTrangThai() ? 1 : 0);

            int rowAffected = pst.executeUpdate();
            return rowAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi khi thêm sản phẩm: " + e.getMessage());
            return false;
        }
    }

    public SanPham timSanPham(String ma) {
        String sql = "SELECT sp.*, dm.TenDM, ncc.TenNCC, ncc.SoDienThoai, ncc.DiaChi "
                + "FROM SanPham sp "
                + "INNER JOIN DanhMuc dm ON sp.MaDM = dm.MaDM "
                + "INNER JOIN NhaCungCap ncc ON sp.MaNCC = ncc.MaNCC "
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
                sp.setGiaNhap(rs.getLong("GiaNhap"));
                sp.setGiaBan(rs.getLong("GiaBan"));
                sp.setSoLuongTon(rs.getInt("SoLuongTon"));
                sp.setTrangThai(rs.getBoolean("TrangThai"));
                sp.setLoaiNuoc(rs.getString("LoaiNuoc"));
                sp.setTheTich(rs.getInt("TheTich"));
                sp.setMucCanhBao(rs.getInt("MucCanhBao"));
                sp.setTrangThaiXuLy(rs.getString("TrangThaiXuLy"));
                
                NhaCungCap ncc = new NhaCungCap(rs.getString("MaNCC"), rs.getString("TenNCC"),
                        rs.getString("SoDienThoai"), rs.getString("DiaChi"));
                DanhMuc danhMuc = new DanhMuc(rs.getString("MaDM"), rs.getString("TenDM"));
                sp.setDanhMuc(danhMuc);
                sp.setNhaCungCap(ncc);
                return sp;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn sản phẩm: " + e.getMessage());
        }
        return null;
    }
}