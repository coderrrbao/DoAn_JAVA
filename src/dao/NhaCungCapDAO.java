package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.conection.DBConnection;
import dto.NhaCungCap;

public class NhaCungCapDAO {
    public NhaCungCap timNhaCungCap(String maNCC) {
        NhaCungCap nhaCungCap = null;

        String sql = "SELECT * FROM NhaCungCap";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            nhaCungCap = new NhaCungCap();
            while (rs.next()) {
                if (rs.getString("MaNCC").equals(maNCC)) {
                    nhaCungCap.setMaNCC(rs.getString("MaNCC"));
                    nhaCungCap.setTenNCC(rs.getString("TenNCC"));
                    nhaCungCap.setSoDienThoai(rs.getString("SoDienThoai"));
                    nhaCungCap.setDiaChi(rs.getString("DiaChi"));
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn sản phẩm: " + e.getMessage());
        }
        return nhaCungCap;
    }

    public NhaCungCap timNhaCungCapTheoTen(String ten) {
        NhaCungCap nhaCungCap = null;

        String sql = "SELECT * FROM NhaCungCap WHERE TrangThai=1 AND TenNCC = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, ten);
            ResultSet rs = pst.executeQuery();
            nhaCungCap = new NhaCungCap();
            if (rs.next()) {
                nhaCungCap.setMaNCC(rs.getString("MaNCC"));
                nhaCungCap.setTenNCC(rs.getString("TenNCC"));
                nhaCungCap.setSoDienThoai(rs.getString("SoDienThoai"));
                nhaCungCap.setDiaChi(rs.getString("DiaChi"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn sản phẩm: " + e.getMessage());
        }
        return nhaCungCap;
    }

    public ArrayList<NhaCungCap> layListNhaCungCap() {
        ArrayList<NhaCungCap> listNhaCungCap = new ArrayList<>();

        String sql = "SELECT * FROM NhaCungCap";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                NhaCungCap nhaCungCap = new NhaCungCap();
                nhaCungCap.setMaNCC(rs.getString("MaNCC"));
                nhaCungCap.setTenNCC(rs.getString("TenNCC"));
                nhaCungCap.setSoDienThoai(rs.getString("SoDienThoai"));
                nhaCungCap.setDiaChi(rs.getString("DiaChi"));
                listNhaCungCap.add(nhaCungCap);

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn sản phẩm: " + e.getMessage());
        }
        return listNhaCungCap;
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

    public String layMaNhaCungCapKhaDung() {
        String sql = "SELECT COUNT(*) FROM NhaCungCap";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int so = rs.getInt(1) + 1;
                String ma = String.format("%02d", so);
                return "NCC" + ma;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn NCC: " + e.getMessage());
        }
        return "";
    }

    public boolean themNhaCungCap(NhaCungCap ncc) {
        String sql = "INSERT INTO NhaCungCap (MaNCC, TenNCC, SoDienThoai, DiaChi, TrangThai) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            if (ncc.getMaNCC() == null || ncc.getMaNCC().trim().isEmpty()) {
                ncc.setMaNCC(layMaNhaCungCapKhaDung());
            }
            pst.setString(1, ncc.getMaNCC());
            pst.setString(2, ncc.getTenNCC());
            pst.setString(3, ncc.getSoDienThoai());
            pst.setString(4, ncc.getDiaChi());
            pst.setInt(5, 1);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi thêm NhaCungCap: " + e.getMessage());
            return false;
        }
    }
}
