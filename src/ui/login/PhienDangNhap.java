package ui.login;

import java.util.HashSet;

import dto.NhanVien;
import dto.TaiKhoan;

public class PhienDangNhap {
    private static NhanVien user;
    private static TaiKhoan taiKhoan;
    private static HashSet<String> listQuyen = new HashSet<>();

    public static void setUser(NhanVien nv) {
        user = nv;
    }

    public static NhanVien getUser() {
        return user;
    }

    public static TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public static void setTaiKhoan(TaiKhoan taiKhoan) {
        PhienDangNhap.taiKhoan = taiKhoan;
    }

    public static HashSet<String> getListQuyen() {
        return listQuyen;
    }

    public static void themQuyen(String tenQuyen) {
        listQuyen.add(tenQuyen);
    }

    public static void dangXuat() {
        taiKhoan = null;
        user = null;
    }
}
