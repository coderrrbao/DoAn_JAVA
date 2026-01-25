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
                sp.setGiaBan(rs.getLong("GiaBan"));
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

    public boolean themSanPham(SanPham sanPham) {

        String sql = "INSERT INTO SanPham (MaSP, TenSP, MaDM, GiaBan, MaNCC, LoaiNuoc, Anh, TheTich, MucCanhBao, TrangThaiXuLy, TrangThai) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            if (sanPham.getMaSP() == null || sanPham.getMaSP().trim().isEmpty()) {
                sanPham.setMaSP(layMaSanPhamKhaDung());
            }
            pst.setString(1, sanPham.getMaSP());
            pst.setString(2, sanPham.getTenSP());
            pst.setString(3, sanPham.getDanhMuc() != null ? sanPham.getDanhMuc().getMaDM() : null);
            pst.setDouble(4, sanPham.getGiaBan());
            pst.setString(5, sanPham.getNhaCungCap() != null ? sanPham.getNhaCungCap().getMaNCC() : null);
            pst.setString(6, sanPham.getLoaiNuoc());
            pst.setString(7, sanPham.getAnh());
            pst.setInt(8, sanPham.getTheTich());
            pst.setInt(9, sanPham.getMucCanhBao());
            pst.setString(10, sanPham.getTrangThaiXuLy());
            pst.setInt(11, sanPham.getTrangThai() ? 1 : 0);

            int rowAffected = pst.executeUpdate();
            return rowAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi khi thêm sản phẩm: " + e.getMessage());
            return false;
        }
    }

    public String layMaSanPhamKhaDung() {
        String sql = "SELECT COUNT(*) FROM SanPham";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {

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
                sp.setGiaBan(rs.getLong("GiaBan"));
                sp.setTheTich(rs.getInt("TheTich"));
                sp.setMucCanhBao(rs.getInt("MucCanhBao"));
                sp.setTrangThaiXuLy(rs.getString("TrangThaiXuLy"));
                sp.setLoaiNuoc(rs.getString("LoaiNuoc"));
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