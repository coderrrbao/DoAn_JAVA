package bus;

import java.util.ArrayList;
import java.util.List;

import dao.KhachHangDAO;
import dto.KhachHang;

public class KhachHangBUS {

    private KhachHangDAO dao = new KhachHangDAO();

    public List<KhachHang> layDanhSachKhachHang() {
        return dao.layDanhSachKhachHang();
    }

    public KhachHang timKhachHangTheoMa(String maKH) {
        if (maKH == null || maKH.trim().isEmpty()) {
            return null;
        }
        return dao.layKhachHangTheoMa(maKH);
    }

    public String themKhachHang(KhachHang kh) {
        if (kh == null) {
            return "Dữ liệu khách hàng không hợp lệ";
        }
        if (kh.getTenKH() == null || kh.getTenKH().trim().isEmpty()
                || kh.getSdt() == null || kh.getSdt().trim().isEmpty()) {
            return "Vui lòng nhập đầy đủ tên và số điện thoại";
        }

        if (kh.getTenDaMua() <= 0) {
            kh.setTenDaMua(0);
        }
        if (kh.getMaHang() == null || kh.getMaHang().trim().isEmpty()) {
            kh.setMaHang("HTV01");
        }

        boolean ok = dao.themKhachHang(kh);
        if (!ok) {
            return "Lỗi thêm khách hàng";
        }
        return null;
    }

    public String capNhatKhachHang(KhachHang kh) {
        if (kh == null || kh.getMaKH() == null || kh.getMaKH().trim().isEmpty()) {
            return "Không tìm thấy mã khách hàng";
        }
        if (kh.getTenKH() == null || kh.getTenKH().trim().isEmpty()
                || kh.getSdt() == null || kh.getSdt().trim().isEmpty()) {
            return "Vui lòng nhập đầy đủ tên và số điện thoại";
        }
        boolean ok = dao.capNhatKhachHang(kh);
        if (!ok) {
            return "Lỗi cập nhật khách hàng";
        }
        return null;
    }

    public boolean xoaKhachHang(String maKH) {
        if (maKH == null || maKH.trim().isEmpty()) {
            return false;
        }
        return dao.xoaKhachHang(maKH);
    }
}

