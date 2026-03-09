package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import dao.conection.DBConnection;
import dto.HoaDon;
import dto.KhuyenMai;
import dto.NhanVien;
import ui.thongke.ThongKeValue;
import java.sql.Timestamp;

public class HoaDonDAO {

    public boolean themHoaDon(HoaDon hd) {
        String sql = "INSERT INTO HoaDon (MaHD, MaNV, MaKH, MaKM, NgayBan, TongTien, TienKhuyenMai, TrangThai) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, hd.getMaHD());
            if (hd.getNhanVien() != null) {
                pst.setString(2, hd.getNhanVien().getMaNV());
            } else {
                pst.setNull(2, java.sql.Types.VARCHAR);
            }

            pst.setString(3, hd.getMaKH());

            if (hd.getMaGiamGia() != null) {
                pst.setString(4, hd.getMaGiamGia().getMaKM());
            } else {
                pst.setNull(4, java.sql.Types.VARCHAR);
            }

            pst.setTimestamp(5, new Timestamp(hd.getNgayBan().getTime()));
            pst.setDouble(6, hd.getTongTien());
            pst.setDouble(7, hd.getTienKhuyenMai());
            pst.setBoolean(8, hd.getTrangThai());

            int rowAffected = pst.executeUpdate();
            return rowAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi thêm hóa đơn: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<ThongKeValue> layKeQuaThongKeTheoNgay(String ngay) {
        ArrayList<ThongKeValue> list = new ArrayList<>();
        String sql = "SELECT DATEPART(HOUR, NgayBan) AS GIO,SUM(TongTien) AS TongTien FROM HoaDon WHERE CAST(NgayBan AS DATE)=? GROUP BY DATEPART(HOUR, NgayBan) ORDER BY GIO";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, ngay);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ThongKeValue thongKeValue = new ThongKeValue();
                thongKeValue.setTongTien(rs.getDouble("TongTien"));
                thongKeValue.setThoiGian(rs.getString("GIO") + ":00");
                list.add(thongKeValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<ThongKeValue> layKetQuaThongKeTheoThang(int thang, int nam) {
        ArrayList<ThongKeValue> list = new ArrayList<>();
        String sql = "SELECT DAY(NgayBan) AS NGAY, SUM(TongTien) AS TongTien " +
                "FROM HoaDon " +
                "WHERE MONTH(NgayBan) = ? AND YEAR(NgayBan) = ? " +
                "GROUP BY DAY(NgayBan) " +
                "ORDER BY NGAY";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, thang);
            pst.setInt(2, nam);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                ThongKeValue thongKeValue = new ThongKeValue();
                thongKeValue.setTongTien(rs.getDouble("TongTien"));
                thongKeValue.setThoiGian("Ngày " + rs.getInt("NGAY"));
                list.add(thongKeValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<ThongKeValue> layKetQuaThongKeTheoNam(int nam) {
        ArrayList<ThongKeValue> list = new ArrayList<>();
        String sql = "SELECT MONTH(NgayBan) AS THANG, SUM(TongTien) AS TongTien " +
                "FROM HoaDon " +
                "WHERE YEAR(NgayBan) = ? " +
                "GROUP BY MONTH(NgayBan) " +
                "ORDER BY THANG";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, nam);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                ThongKeValue thongKeValue = new ThongKeValue();
                thongKeValue.setTongTien(rs.getDouble("TongTien"));
                thongKeValue.setThoiGian("Tháng " + rs.getInt("THANG"));
                list.add(thongKeValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<HoaDon> layDanhSachHoaDon() {
        ArrayList<HoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE TrangThai = 1 ORDER BY MaHD DESC";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getString("MaHD"));

                String maNV = rs.getString("MaNV");
                if (maNV != null) {
                    NhanVien nv = new NhanVien();
                    nv.setMaNV(maNV);
                    hd.setNhanVien(nv);
                }
                hd.setMaKH(rs.getString("MaKH"));
                String maKM = rs.getString("MaKM");
                if (maKM != null) {
                    KhuyenMai km = new KhuyenMai();
                    km.setMaKM(maKM);
                    hd.setMaGiamGia(km);
                }

                hd.setNgayBan(rs.getDate("NgayBan"));
                hd.setTongTien(rs.getDouble("TongTien"));
                hd.setTienKhuyenMai(rs.getDouble("TienKhuyenMai"));
                hd.setTrangThai(rs.getBoolean("TrangThai"));
                list.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi lấy danh sách hóa đơn: " + e.getMessage());
        }
        return list;
    }

    public String layMaHoaDonCuoiCung() {
        String sql = "SELECT TOP 1 MaHD FROM HoaDon ORDER BY MaHD DESC";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                return rs.getString("MaHD");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public HoaDon timHoaDonTheoMa(String maHD) {
        String sql = "SELECT * FROM HoaDon WHERE MaHD = ?";
        try (java.sql.Connection conn = dao.conection.DBConnection.getConnection();
                java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, maHD);

            try (java.sql.ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    HoaDon hd = new HoaDon();
                    hd.setMaHD(rs.getString("MaHD"));

                    String maNV = rs.getString("MaNV");
                    if (maNV != null) {
                        dto.NhanVien nv = new dto.NhanVien();
                        nv.setMaNV(maNV);
                        hd.setNhanVien(nv);
                    }

                    hd.setMaKH(rs.getString("MaKH"));

                    String maKM = rs.getString("MaKM");
                    if (maKM != null) {
                        dto.KhuyenMai mg = new dto.KhuyenMai();
                        mg.setMaKM(maKM);
                        hd.setMaGiamGia(mg);
                    }

                    hd.setNgayBan(rs.getDate("NgayBan"));
                    hd.setTongTien(rs.getDouble("TongTien"));
                    hd.setTienKhuyenMai(rs.getDouble("TienKhuyenMai"));
                    hd.setTrangThai(rs.getBoolean("TrangThai"));

                    return hd;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean xoaHoaDon(String maHD) {
        String sql = "UPDATE HoaDon SET TrangThai = 0 WHERE MaHD = ?";
        try (java.sql.Connection conn = dao.conection.DBConnection.getConnection();
                java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, maHD);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}