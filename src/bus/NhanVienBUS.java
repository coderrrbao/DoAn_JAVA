package bus;

import java.util.ArrayList;
import dao.NhanVienDAO;
import dto.NhanVien;
import util.XuLyExcel; // Giả định bạn dùng class này giống bên SanPhamBUS

public class NhanVienBUS {

    // 1. Áp dụng Singleton Pattern giống SanPhamBUS
    private static NhanVienBUS instance = null;

    public static NhanVienBUS getNhanVienBUS() {
        if (instance == null) {
            instance = new NhanVienBUS();
        }
        return instance;
    }

    private NhanVienDAO dao = new NhanVienDAO();
    private ArrayList<NhanVien> listNhanVien;
    private boolean canUpdate = false;

    private NhanVienBUS() {
        khoitao();
    }

    public void khoitao() {
        TaiKhoanBUS taiKhoanBUS = TaiKhoanBUS.getTaiKhoanBUS();
        listNhanVien = dao.layDanhSachNhanVien();
        for (NhanVien nhanVien : listNhanVien){
            nhanVien.setTaiKhoan(taiKhoanBUS.timTaiKhoan(nhanVien.getTaiKhoan().getMaTK()));
        }
    }

    public ArrayList<NhanVien> layDanhSachNhanVien() {
        if (canUpdate || listNhanVien == null) {
            canUpdate = false;
            khoitao();
        }
        return listNhanVien;
    }

    public String themNhanVien(NhanVien nv) {
        String validation = kiemTraDuLieu(nv);
        if (validation != null) {
            return validation;
        }

        if (!dao.themNhanVien(nv)) {
            return "Lỗi hệ thống: Không thể thêm nhân viên vào cơ sở dữ liệu.";
        }

        canUpdate = true;
        return null;
    }

    public String capNhatNhanVien(NhanVien nv) {
        if (nv == null || nv.getMaNV() == null || nv.getMaNV().trim().isEmpty()) {
            return "Mã nhân viên không hợp lệ để cập nhật.";
        }

        String validation = kiemTraDuLieu(nv);
        if (validation != null) {
            return validation;
        }

        if (!dao.capNhatNhanVien(nv)) {
            return "Lỗi hệ thống: Cập nhật nhân viên thất bại.";
        }

        canUpdate = true;
        return null;
    }

    public boolean xoaNhanVien(String maNV) {
        if (maNV == null || maNV.trim().isEmpty()) {
            return false;
        }

        if (!dao.xoaNhanVien(maNV)) {
            return false;
        }

        canUpdate = true;
        return true;
    }


    public NhanVien timNhanVien(String maNV) {
        if (canUpdate || listNhanVien == null) {
            khoitao();
            canUpdate = false;
        }
        for (NhanVien nv : listNhanVien) {
            if (nv.getMaNV().equals(maNV)) {
                return nv;
            }
        }
        return null;
    }



    public ArrayList<NhanVien> timKiemNhanVien(String tuKhoa) {
        if (canUpdate || listNhanVien == null) {
            canUpdate = false;
            khoitao();
        }

        ArrayList<NhanVien> ketQua = new ArrayList<>();
        String tuKhoaChuanHoa =(tuKhoa != null ? tuKhoa.trim() : "");

        for (NhanVien nv : listNhanVien) {
            String tenNV = nv.getTenNV() != null ? nv.getTenNV() : "";
            String sdt = nv.getSdt() != null ? nv.getSdt() : "";
            String maNV = nv.getMaNV() != null ? nv.getMaNV() : "";

            boolean khopTen = tenNV.contains(tuKhoaChuanHoa);
            boolean khopMa = maNV.contains(tuKhoaChuanHoa);
            boolean khopSDT = sdt.contains(tuKhoaChuanHoa);

            if (khopTen || khopMa || khopSDT) {
                ketQua.add(nv);
            }
        }
        return ketQua;
    }

    private String kiemTraDuLieu(NhanVien nv) {
        if (nv.getTenNV() == null || nv.getTenNV().trim().isEmpty()) {
            return "Tên nhân viên không được để trống.";
        }
        if (nv.getSdt() == null || nv.getSdt().trim().isEmpty()) {
            return "Số điện thoại không được để trống.";
        }
        if (!nv.getSdt().matches("^0\\d{9,10}$")) {
            return "Số điện thoại không hợp lệ (phải bắt đầu bằng 0 và có 10-11 chữ số).";
        }
        if (nv.getDiaChi() == null || nv.getDiaChi().trim().isEmpty()) {
            return "Địa chỉ không được để trống.";
        }
        if (nv.getNgaySinh() == null || nv.getNgaySinh().trim().isEmpty()) {
            return "Vui lòng chọn ngày sinh.";
        }
        return null;
    }
}