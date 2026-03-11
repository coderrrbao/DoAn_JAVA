package bus;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import dao.KhachHangDAO;
import dao.conection.DBConnection;
import dto.HangThanhVien;
import dto.KhachHang;
import util.XuLyExcel;

public class KhachHangBUS {

    private KhachHangDAO khachHangDAO = new KhachHangDAO();

    public KhachHang timTheoSDT(String sdt) {
        if (sdt == null || sdt.trim().isEmpty()) {
            return null;
        }
        return khachHangDAO.layKhachHangTheoSDT(sdt);
    }

    public ArrayList<KhachHang> getDanhSachKhachHang() {
        return khachHangDAO.layDanhSachKhachHang();
    }

    public String taoMaKHMoi() {
        ArrayList<KhachHang> ds = khachHangDAO.layDanhSachKhachHang();
        if (ds == null || ds.isEmpty())
            return "KH001";

        int maxId = 0;
        for (KhachHang kh : ds) {
            String ma = kh.getMaKH();
            if (ma != null && ma.startsWith("KH")) {
                try {
                    int num = Integer.parseInt(ma.substring(2).trim());
                    if (num > maxId)
                        maxId = num;
                } catch (Exception e) {

                }
            }
        }
        return String.format("KH%03d", maxId + 1);
    }

    public boolean capNhatTienDaMua(String maKH, double tienThem) {
        return khachHangDAO.capNhatTienDaMua(maKH, tienThem);
    }

    public ArrayList<KhachHang> layDanhSachKhachHang() {
        return khachHangDAO.layDanhSachKhachHang();
    }

    public KhachHang timKhachHangTheoMa(String maKH) {
        if (maKH == null || maKH.trim().isEmpty()) {
            return null;
        }
        return khachHangDAO.layKhachHangTheoMa(maKH);
    }

    public boolean themKhachHang(KhachHang kh) throws Exception {
        if (kh.getTenKH() == null || kh.getTenKH().trim().isEmpty()) {
            throw new Exception("Tên khách hàng không được để trống!");
        }
        if (kh.getSdt() == null || kh.getSdt().trim().isEmpty()) {
            throw new Exception("Số điện thoại không được để trống!");
        }
        if (!kh.getSdt().matches("\\d{10,11}")) {
            throw new Exception("Số điện thoại phải có từ 10-11 chữ số!");
        }
        if (timTheoSDT(kh.getSdt()) != null) {
            throw new Exception("Số điện thoại này đã tồn tại trong hệ thống!");
        }
        if (kh.getTenDaMua() < 0)
            kh.setTenDaMua(0);
        if (kh.getMaHang() == null || kh.getMaHang().trim().isEmpty()) {
            kh.setMaHang("HTV01");
        }

        boolean ok = khachHangDAO.themKhachHang(kh);
        if (!ok) {
            throw new Exception("Lỗi: Không thể lưu vào cơ sở dữ liệu!");
        }

        return true;
    }

    public boolean themKhachHang(KhachHang kh, Connection conn) throws Exception {
        if (kh.getTenKH() == null || kh.getTenKH().trim().isEmpty()) {
            throw new Exception("Tên khách hàng không được để trống!");
        }
        if (kh.getSdt() == null || kh.getSdt().trim().isEmpty()) {
            throw new Exception("Số điện thoại không được để trống!");
        }
        if (!kh.getSdt().matches("\\d{10,11}")) {
            throw new Exception("Số điện thoại phải có từ 10-11 chữ số!");
        }

        if (kh.getTenDaMua() < 0)
            kh.setTenDaMua(0);
        if (kh.getMaHang() == null || kh.getMaHang().trim().isEmpty()) {
            kh.setMaHang("HTV01");
        }

        boolean ok = khachHangDAO.themKhachHang(kh, conn);
        if (!ok) {
            throw new Exception("Lỗi: Không thể lưu vào cơ sở dữ liệu!");
        }

        return true;
    }

    public String capNhatKhachHang(KhachHang kh) {
        if (kh == null || kh.getMaKH() == null || kh.getMaKH().trim().isEmpty()) {
            return "Không tìm thấy mã khách hàng";
        }
        if (kh.getTenKH() == null || kh.getTenKH().trim().isEmpty()) {
            return "Tên khách hàng không được để trống!";
        }
        if (kh.getSdt() == null || kh.getSdt().trim().isEmpty()) {
            return "Số điện thoại không được để trống!";
        }
        if (!kh.getSdt().matches("\\d{10,11}")) {
            return "Số điện thoại phải là 10-11 chữ số!";
        }

        KhachHang khExist = timTheoSDT(kh.getSdt());
        if (khExist != null && !khExist.getMaKH().equals(kh.getMaKH())) {
            return "Số điện thoại này đã thuộc về khách hàng khác!";
        }

        boolean ok = khachHangDAO.capNhatKhachHang(kh);
        if (!ok) {
            return "Lỗi cập nhật khách hàng vào CSDL";
        }
        return null;
    }

    public String layTenHangTuMa(String maHang) {
        if (maHang == null || maHang.trim().isEmpty()) {
            return "Thành Viên Mới";
        }
        HangThanhVien htv = bus.HangThanhVienBUS.getHangThanhVienBUS().timHangThanhVien(maHang);
        return htv != null ? htv.getTenHang() : "Thành Viên Mới";
    }

    public boolean xoaKhachHang(String maKH) {
        if (maKH == null || maKH.trim().isEmpty()) {
            return false;
        }
        return khachHangDAO.xoaKhachHang(maKH);
    }

    public boolean xuatExcel(File file) {
        ArrayList<KhachHang> list = layDanhSachKhachHang();
        return XuLyExcel.xuatFileKhachHang(file, list);
    }

    public boolean nhapExcel(File file) {
        ArrayList<KhachHang> dsNhap = XuLyExcel.nhapFileKhachHang(file);
        if (dsNhap == null || dsNhap.isEmpty()) {
            return false;
        }

        HashSet<String> setSdt = new HashSet<>();
        ArrayList<KhachHang> hienTai = khachHangDAO.layDanhSachKhachHang();
        for (KhachHang kh : hienTai) {
            setSdt.add(kh.getSdt());
        }

        boolean hasAdded = false;
        Connection conn = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            for (KhachHang kh : dsNhap) {
                if (setSdt.contains(kh.getSdt())) {
                    System.out.println("Bỏ qua khách hàng (Trùng SDT): " + kh.getSdt());
                    continue;
                }

                if (themKhachHang(kh, conn)) {
                    setSdt.add(kh.getSdt());
                    hasAdded = true;
                }
            }

            if (hasAdded) {
                conn.commit();
            } else {
                conn.rollback();
            }
            return hasAdded;

        } catch (Exception e) {
            System.err.println("Lỗi khi nhập khách hàng: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}