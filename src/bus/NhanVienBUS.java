package bus;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import dao.NhanVienDAO;
import dao.conection.DBConnection;
import dto.NhanVien;
import util.XuLyExcel;

public class NhanVienBUS {

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

    public boolean themNhanVien(NhanVien nv, Connection conn) throws SQLException {

        String validation = kiemTraDuLieu(nv);
        if (validation != null) {
            throw new SQLException("Lỗi dữ liệu nhân viên " + nv.getTenNV() + ": " + validation);
        }

        if (!dao.themNhanVien(nv, conn)) {
            return false;
        }
        return true;
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
        if (dsNhap == null || dsNhap.isEmpty()) {
            return false;
        }

        HashSet<String> setSdt = new HashSet<>();
        for (NhanVien nvHienTai : layDanhSachNhanVien()) {
            setSdt.add(nvHienTai.getSdt());
        }

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            for (NhanVien nv : dsNhap) {

                if (setSdt.contains(nv.getSdt())) {
                    throw new SQLException("Trùng số điện thoại: " + nv.getSdt());
                }

                if (nv.getMaNV() == null || nv.getMaNV().isEmpty()) {

                    nv.setMaNV(dao.layMaNhanVien(conn));
                }

                if (!themNhanVien(nv, conn)) {
                    throw new SQLException("Lỗi thao tác thêm nhân viên vào CSDL.");
                }

                setSdt.add(nv.getSdt());
            }

            conn.commit();
            this.canUpdate = true;
            this.khoitao();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Import Excel Nhân Viên thất bại: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean xuatExcel(File file) {
        ArrayList<NhanVien> list = layDanhSachNhanVien();
        return XuLyExcel.xuatFileNhanVien(file, list);
    }
}