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
        if (nv.getTenNV().isEmpty() || nv.getDiaChi().isEmpty() || nv.getSdt().isEmpty() || nv.getSdt().isEmpty()) {
            return "Vui lòng điền đầy đủ thông tin";
        }
        Boolean result = dao.themNhanVien(nv);
        if (!result) {
            return "Lỗi thêm nhân viên";
        }
        return null;
    }

    public List<NhanVien> layDanhSachNhanVien(){
        return dao.layDanhSachNhanVien();
    }

    public NhanVien timNhanVienTheoMa(String maNV) {
        if (maNV == null || maNV.isEmpty()) {
            return null;
        }
        return dao.timNhanVienTheoMa(maNV);
    }

    public String capNhatNhanVien(NhanVien nv) {
        if (nv == null || nv.getMaNV() == null || nv.getMaNV().isEmpty()) {
            return "Không tìm thấy mã nhân viên";
        }
        if (nv.getTenNV().isEmpty() || nv.getDiaChi().isEmpty() || nv.getSdt().isEmpty()) {
            return "Vui lòng điền đầy đủ thông tin";
        }
        boolean ok = dao.capNhatNhanVien(nv);
        if (!ok) {
            return "Lỗi cập nhật nhân viên";
        }
        return null;
    }

    public boolean xoaNhanVien(String maNV) {
        if (maNV == null || maNV.isEmpty()) {
            return false;
        }
        return dao.xoaNhanVien(maNV);
    }
}
