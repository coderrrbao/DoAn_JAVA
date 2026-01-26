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
        ArrayList<NguyenLieu> listNguyenLieu = new ArrayList<>();
        String sql = "SELECT MaNL, TenNL, MaNCC, Gia, DonVi, MucCanhBao, TrangThai FROM NguyenLieu";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
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

    public String layMaNguyenLieuKhaDung() {
        String sql = "SELECT COUNT(*) FROM NguyenLieu";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int so = rs.getInt(1) + 1;
                String ma = String.format("%02d", so);
                return "NL" + ma;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn nguyên liệu: " + e.getMessage());
        }
        return "";
    }

    public boolean themNguyenLieu(NguyenLieu nl) {
        String sql = "INSERT INTO NguyenLieu (MaNL, TenNL, MaNCC, TenNhaCC, Gia, DonVi, MucCanhBao, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            if (nl.getMaNL() == null || nl.getMaNL().trim().isEmpty()) {
                nl.setMaNL(layMaNguyenLieuKhaDung());
            }
            pst.setString(1, nl.getMaNL());
            pst.setString(2, nl.getTenNL());
            pst.setString(3, nl.getNhaCungCap() != null ? nl.getNhaCungCap().getMaNCC() : null);
            pst.setString(4, nl.getNhaCungCap() != null ? nl.getNhaCungCap().getTenNCC() : null);
            pst.setDouble(5, nl.getGia());
            pst.setString(6, nl.getDonVi());
            pst.setInt(7, nl.getMucCanhBao());
            pst.setInt(8, 1);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi thêm NguyenLieu: " + e.getMessage());
            return false;
        }
    }

}
