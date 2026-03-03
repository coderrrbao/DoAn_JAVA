package ui.login;

import dto.NhanVien;
import dto.TaiKhoan;

public class PhienDangNhap {
    private static NhanVien user;
    private static TaiKhoan taiKhoan;

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

    public static void dangXuat() {
        taiKhoan = null;
        user = null;
    }
}
