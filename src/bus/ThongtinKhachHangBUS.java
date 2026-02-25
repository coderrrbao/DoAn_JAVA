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
}
