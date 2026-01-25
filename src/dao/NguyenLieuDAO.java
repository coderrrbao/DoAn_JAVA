package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.conection.DBConnection;
import dto.NguyenLieu;
import dto.NhaCungCap;

public class NguyenLieuDAO {
    public NguyenLieu timNguyenLieu(String maNL) {
        NguyenLieu nguyenLieu = null;

        String sql = "SELECT MaNL, TenNL, MaNCC, Gia, DonVi, MucCanhBao, TrangThai FROM NguyenLieu WHERE MaNL = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, maNL);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                NhaCungCap nhaCungCap = new NhaCungCap();
                nhaCungCap.setMaNCC(rs.getString("MaNCC"));

                nguyenLieu = new NguyenLieu();
                nguyenLieu.setMaNL(rs.getString("MaNL"));
                nguyenLieu.setTenNL(rs.getNString("TenNL"));
                nguyenLieu.setNhaCungCap(nhaCungCap);
                nguyenLieu.setGia(rs.getDouble("Gia"));
                nguyenLieu.setDonVi(rs.getString("DonVi"));
                nguyenLieu.setMucCanhBao(rs.getInt("MucCanhBao"));
                nguyenLieu.setTrangThai(rs.getBoolean("TrangThai"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi tìm nguyên liệu theo mã: " + e.getMessage());
        }

        return nguyenLieu;
    }
     public ArrayList<NguyenLieu> layListNguyenLieu() {
       ArrayList<NguyenLieu> listNguyenLieu  = new ArrayList<>();
        String sql = "SELECT MaNL, TenNL, MaNCC, Gia, DonVi, MucCanhBao, TrangThai FROM NguyenLieu";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();

            while  (rs.next()) {
                NguyenLieu nguyenLieu = new NguyenLieu();
                NhaCungCap nhaCungCap = new NhaCungCap();
                nhaCungCap.setMaNCC(rs.getString("MaNCC"));
                nguyenLieu = new NguyenLieu();
                nguyenLieu.setMaNL(rs.getString("MaNL"));
                nguyenLieu.setTenNL(rs.getNString("TenNL"));
                nguyenLieu.setNhaCungCap(nhaCungCap);
                nguyenLieu.setGia(rs.getDouble("Gia"));
                nguyenLieu.setDonVi(rs.getString("DonVi"));
                nguyenLieu.setMucCanhBao(rs.getInt("MucCanhBao"));
                nguyenLieu.setTrangThai(rs.getBoolean("TrangThai"));
                listNguyenLieu.add(nguyenLieu);
                
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi tìm nguyên liệu theo mã: " + e.getMessage());
        }

        return listNguyenLieu;
    }



}
