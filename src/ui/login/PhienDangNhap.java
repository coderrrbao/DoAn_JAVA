package ui.login;

import dto.NhanVien;

public class PhienDangNhap {
    private static NhanVien user;

    public static void setUser(NhanVien nv){
        user = nv;
    }

    public static NhanVien getUser(){
        return user;
    }

    public static void dangXuat(){
        user = null;
    }
}
