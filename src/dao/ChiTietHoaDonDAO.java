package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.conection.DBConnection;
import dto.ChiTietHoaDon;
import dto.DanhMuc;
import dto.SanPham;
import dto.Size;

public class ChiTietHoaDonDAO {
    public ArrayList<ChiTietHoaDon> layListChiTietHoaDon() {
        ArrayList<ChiTietHoaDon> listChiTietHoaDon = new ArrayList<>();
        String sql = "SELECT cthd.*, " +
                     "sp.TenSP, sp.GiaBan, " +
                     "size.TenSize, " +
                     "dm.MaDM, dm.TenDM " +
                     "FROM ChiTietHoaDon cthd " +
                     "INNER JOIN SanPham sp ON cthd.MaSP = sp.MaSP " +
                     "INNER JOIN Size size ON cthd.MaSize = size.MaSize " +
                     "INNER JOIN DanhMuc dm ON sp.MaDM = dm.MaDM";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ChiTietHoaDon cthd = new ChiTietHoaDon();
                cthd.setMaCTHD(rs.getString("MaCTHD"));
                cthd.setMaHD(rs.getString("MaHD"));
                cthd.setSoLuong(rs.getInt("SoLuong"));
                cthd.setGia(rs.getDouble("Gia"));

                DanhMuc danhMuc = new DanhMuc();
                danhMuc.setMaDM(rs.getString("MaDM"));
                danhMuc.setTenDM(rs.getString("TenDM"));


                SanPham sanPham = new SanPham();
                sanPham.setMaSP(rs.getString("MaSP"));
                sanPham.setTenSP(rs.getString("TenSP"));
                sanPham.setGiaBan(rs.getLong("GiaBan"));
                sanPham.setDanhMuc(danhMuc);

                cthd.setSanPham(sanPham);

                Size size = new Size();
                size.setMaSize(rs.getString("MaSize"));
                size.setTenSize(rs.getString("TenSize"));

                cthd.setSize(size);
                listChiTietHoaDon.add(cthd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listChiTietHoaDon;
    }

    public boolean themChiTietHoaDon(ChiTietHoaDon ct) {
        String sql = "INSERT INTO ChiTietHoaDon (MaCTHD, MaHD, MaSP, MaSize, SoLuong, Gia) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, ct.getMaCTHD());
            pst.setString(2, ct.getMaHD());
            pst.setString(3, ct.getSanPham().getMaSP());

            if (ct.getSize() != null && ct.getSize().getMaSize() != null && !ct.getSize().getMaSize().isEmpty()) {
                pst.setString(4, ct.getSize().getMaSize());
            } else {
                pst.setNull(4, java.sql.Types.VARCHAR);
            }



            pst.setInt(5, ct.getSoLuong());
            pst.setDouble(6, ct.getGia());

            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
