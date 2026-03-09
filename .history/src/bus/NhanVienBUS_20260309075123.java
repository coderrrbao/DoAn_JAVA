package bus;

import java.io.File;
import java.util.ArrayList;

import dao.NhanVienDAO;
import dto.NhanVien;
import util.XuLyExcel;

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

    public NhanVienBUS() {
        khoitao();
    }

    public void khoitao() {
        listNhanVien = dao.layDanhSachNhanVien();
    }

    public ArrayList<NhanVien> layDanhSachNhanVien() {
        if (canUpdate || listNhanVien == null) {
            canUpdate = false;
            khoitao();
        }
        return listNhanVien;
    }

    /**
     * Gọi khi dữ liệu thay đổi bên ngoài BUS (vd: import Excel qua DAO) để lần gọi
     * sau layDanhSachNhanVien() sẽ load lại từ DB.
     */
    public void yeuCauCapNhat() {
        this.canUpdate = true;
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

    public int getTongSoTrang(int pageSize) {
        if (canUpdate || listNhanVien == null) {
            khoitao();
        }
        return (int) Math.ceil((double) listNhanVien.size() / pageSize);
    }

    public ArrayList<NhanVien> layTrang(int page, int pageSize) {
        if (canUpdate || listNhanVien == null) {
            canUpdate = false;
            khoitao();
        }
        ArrayList<NhanVien> kq = new ArrayList<>();
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, listNhanVien.size());

        if (start >= listNhanVien.size())
            return kq;

        for (int i = start; i < end; i++) {
            kq.add(listNhanVien.get(i));
        }
        return kq;
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

    public static String xoaDau(String text) {
        if (text == null)
            return "";
        java.text.Normalizer.Form form = java.text.Normalizer.Form.NFD;
        return java.text.Normalizer.normalize(text, form)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase();
    }

    public ArrayList<NhanVien> timKiemNhanVien(String tuKhoa) {
        if (canUpdate || listNhanVien == null) {
            canUpdate = false;
            khoitao();
        }

        ArrayList<NhanVien> ketQua = new ArrayList<>();
        String tuKhoaChuanHoa = xoaDau(tuKhoa != null ? tuKhoa.trim() : "");

        for (NhanVien nv : listNhanVien) {
            String tenNV = nv.getTenNV() != null ? nv.getTenNV() : "";
            String sdt = nv.getSdt() != null ? nv.getSdt() : "";
            String maNV = nv.getMaNV() != null ? nv.getMaNV() : "";

            boolean khopTen = xoaDau(tenNV).contains(tuKhoaChuanHoa);
            boolean khopMa = xoaDau(maNV).contains(tuKhoaChuanHoa);
            boolean khopSDT = sdt.contains(tuKhoaChuanHoa);

            if (khopTen || khopMa || khopSDT) {
                ketQua.add(nv);
            }
        }
        return ketQua;
    }

    public String layMaNVMoi() {
        return dao.layMaNhanVien();
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

    public boolean nhapExcel(File file) {
        ArrayList<NhanVien> dsNhap = XuLyExcel.nhapFileNhanVien(file);
        if (dsNhap == null || dsNhap.isEmpty())
            return false;

        int thanhCong = 0;
        for (NhanVien nv : dsNhap) {
            if (timNhanVien(nv.getMaNV()) == null) {
                if (themNhanVien(nv)==null)
                    thanhCong++;
            }
        }
        return thanhCong > 0;
    }

    public boolean xuatExcel(File file) {
        // Tự lấy danh sách đang quản lý trong BUS
        ArrayList<NhanVien> list = layDanhSachNhanVien();
        // Nhờ Util ghi file
        return XuLyExcel.xuatFileNhanVien(file, list);
    }
}