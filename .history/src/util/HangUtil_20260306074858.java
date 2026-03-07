package util;

import bus.HangThanhVienBUS;
import dto.HangThanhVien;

public class HangUtil {
    public static String MaSangHang(String maHang){
        HangThanhVienBUS bus = new HangThanhVienBUS();
        HangThanhVien htv = bus.timHangThanhVien(maHang)
    }
}
