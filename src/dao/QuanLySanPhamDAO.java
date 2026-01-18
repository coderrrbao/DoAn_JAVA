package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.NhaCungCap;
import dto.SanPham;

public class QuanLySanPhamDAO {
    public ArrayList<SanPham> layListSanPham() {
        ArrayList<SanPham> listSanPham = new ArrayList<>();
        String sql = "select * from SanPham";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();

                // Đọc các chuỗi (String)
                sp.setMa(rs.getString("ma"));
                sp.setTen(rs.getNString("ten")); // Dùng getNString cho dữ liệu có tiếng Việt
                sp.setLoaiNuoc(rs.getNString("loaiNuoc"));
                sp.setAnh(rs.getString("anh"));
                sp.setGioiThieu(rs.getNString("gioiThieu"));

                // Đọc các số nguyên và số lớn (int, long)
                sp.setGiaNhap(rs.getLong("giaNhap"));
                sp.setGiaBan(rs.getLong("giaBan"));
                sp.setDungTichMl(rs.getInt("dungTichMl"));
                sp.setSoLuongTon(rs.getInt("soLuongTon"));

                // Đọc kiểu logic (boolean)
                sp.setTrangThai(rs.getBoolean("trangThai")); // SQL BIT -> Java boolean

                // Đọc kiểu ngày tháng (Date)
                // Chuyển từ java.sql.Date sang java.util.Date
                if (rs.getDate("hanSanXuat") != null) {
                    sp.setHanSanXuat(null);
                }
                if (rs.getDate("hanSuDung") != null) {
                    sp.setHanSuDung(null);
                }

                // Xử lý đối tượng liên kết (NhaCungCap)
                // Thông thường ta tạo một đối tượng NCC mới và chỉ set Mã cho nó
                NhaCungCap ncc = new NhaCungCap();
                ncc.setMa(rs.getString("maNhaCungCap"));
                sp.setNhaCungCap(ncc);


                System.out.println(sp.getMa());
            }

        } catch (Exception e) {
            System.out.println("Khong the ket noi");
        }

        return listSanPham;
    }
    public static void main(String[] args) {
        QuanLySanPhamDAO quanLySanPhamDAO = new QuanLySanPhamDAO();
        quanLySanPhamDAO.layListSanPham();
    }
}