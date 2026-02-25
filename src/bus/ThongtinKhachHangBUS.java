package bus;

import dao.KhachHangDAO;
import dto.KhachHang;
import java.util.ArrayList;

public class ThongtinKhachHangBUS {

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
        java.util.ArrayList<KhachHang> ds = khachHangDAO.layDanhSachKhachHang();
        if (ds == null || ds.isEmpty()) return "KH001";

        int maxId = 0;
        for (KhachHang kh : ds) {
            String ma = kh.getMaKH();
            if (ma != null && ma.startsWith("KH")) {
                try {
                    int num = Integer.parseInt(ma.substring(2).trim());
                    if (num > maxId) maxId = num;
                } catch (Exception e) {}
            }
        }
        return String.format("KH%03d", maxId + 1);
    }

    public boolean themKhachHang(KhachHang kh) {
        return khachHangDAO.themKhachHang(kh);
    }

    public boolean capNhatTienDaMua(String maKH, double tienThem) {
        return khachHangDAO.capNhatTienDaMua(maKH, tienThem);
    }
}
