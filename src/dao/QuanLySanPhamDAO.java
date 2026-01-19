package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bus.NhaCungCapBus;
import dto.NhaCungCap;
import dto.SanPham;

public class QuanLySanPhamDAO {
    public ArrayList<SanPham> layListSanPham() {
        ArrayList<SanPham> listSanPham = new ArrayList<>();

        String sql = "SELECT MaSP, TenSP, Anh, GiaNhap, GiaBan, SoLuongTon, " +
                "TrangThai, MaNCC, LoaiNuoc, MaDM, TheTich, MucCanhBao FROM SanPham";

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
                NhaCungCapBus nhaCungCapBus = new NhaCungCapBus();
                NhaCungCap ncc = nhaCungCapBus.timNhaCungCap(rs.getString("MaNCC"));
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

}