package bus;

import java.util.*;

import dao.NhanVienDAO;
import dto.NhanVien;

public class NhanVienBUS {
    private NhanVienDAO dao = new NhanVienDAO();

    public List<String> layDanhSachChucVu() {
        return dao.layDanhSachChucVu();
    }

    public String themNhanVien(NhanVien nv) {
        if (nv.getTenNV().isEmpty()) {
            return "Vui lòng nhập tên nhân viên";
        }
        if (nv.getDiaChi().isEmpty()) {
            return "Vui lòng nhâp địa chỉ";
        }
        if (nv.getTenNV().isEmpty()) {
            return "Vui lòng nhâp tên nhân viên";
        }
        if (nv.getTenNV().isEmpty()) {
            return "Vui lòng nhâp tên nhân viên";
        }
        if (nv.getTenNV().isEmpty()) {
            return "Vui lòng nhâp tên nhân viên";
        }
        if (nv.getTenNV().isEmpty()) {
            return "Vui lòng nhâp tên nhân viên";
        }
        return null;
    }
}
