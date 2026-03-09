package bus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import dao.ChiTietCongThucDAO;
import dao.CongThucDAO;
import dao.DanhMucDao;
import dao.KhachHangDAO;
import dao.SizeDAO;
import dao.conection.DBConnection;
import dto.ChiTietCongThuc;
import dto.CongThuc;
import dto.DanhMuc;
import dto.HangThanhVien;
import dto.KhachHang;
import dto.NhanVien;
import dto.SanPham;
import util.XuLyExcel;

public class KhachHangBUS {

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
        if (ds == null || ds.isEmpty())
            return "KH001";

        int maxId = 0;
        for (KhachHang kh : ds) {
            String ma = kh.getMaKH();
            if (ma != null && ma.startsWith("KH")) {
                try {
                    int num = Integer.parseInt(ma.substring(2).trim());
                    if (num > maxId)
                        maxId = num;
                } catch (Exception e) {
                }
            }
        }
        return String.format("KH%03d", maxId + 1);
    }

    public boolean capNhatTienDaMua(String maKH, double tienThem) {
        return khachHangDAO.capNhatTienDaMua(maKH, tienThem);
    }

    public ArrayList<KhachHang> layDanhSachKhachHang() {
        return khachHangDAO.layDanhSachKhachHang();
    }

    public KhachHang timKhachHangTheoMa(String maKH) {
        if (maKH == null || maKH.trim().isEmpty()) {
            return null;
        }
        return khachHangDAO.layKhachHangTheoMa(maKH);
    }

    public boolean themKhachHang(KhachHang kh) throws Exception {
        if (kh.getTenKH() == null || kh.getTenKH().trim().isEmpty()) {
            throw new Exception("Tên khách hàng không được để trống!");
        }
        if (kh.getSdt() == null || kh.getSdt().trim().isEmpty()) {
            throw new Exception("Số điện thoại không được để trống!");
        }
        if (!kh.getSdt().matches("\\d{10,11}")) {
            throw new Exception("Số điện thoại phải có từ 10-11 chữ số!");
        }

        if (kh.getTenDaMua() < 0)
            kh.setTenDaMua(0);
        if (kh.getMaHang() == null || kh.getMaHang().trim().isEmpty()) {
            kh.setMaHang("HTV01");
        }

        boolean ok = khachHangDAO.themKhachHang(kh);
        if (!ok) {
            throw new Exception("Lỗi: Không thể lưu vào cơ sở dữ liệu!");
        }

        return true;
    }

    public String capNhatKhachHang(KhachHang kh) {
        if (kh == null || kh.getMaKH() == null || kh.getMaKH().trim().isEmpty()) {
            return "Không tìm thấy mã khách hàng";
        }
        if (kh.getTenKH() == null || kh.getTenKH().trim().isEmpty()
                || kh.getSdt() == null || kh.getSdt().trim().isEmpty()) {
            return "Vui lòng nhập đầy đủ tên và số điện thoại";
        }
        boolean ok = khachHangDAO.capNhatKhachHang(kh);
        if (!ok) {
            return "Lỗi cập nhật khách hàng";
        }
        return null;
    }

    public boolean xoaKhachHang(String maKH) {
        if (maKH == null || maKH.trim().isEmpty()) {
            return false;
        }
        return khachHangDAO.xoaKhachHang(maKH);
    }


    public boolean nhapFile(File file) {
        ArrayList<KhachHang> dsNhap = XuLyExcel.nhapFileKhachHang(file);
        if (dsNhap == null || dsNhap.isEmpty())
            return false;

        int soDongCapNhat = 0;
        int soDongThemMoi = 0;

        for (KhachHang khFile : dsNhap) {
            String maKH = khFile.getMaKH();
            if (maKH == null || maKH.trim().isEmpty())
                continue;

            double tongChiTieu = khFile.getTenDaMua();
            if (tongChiTieu < 0)
                tongChiTieu = 0;

            // Tự xác định hạng thành viên theo tổng chi tiêu
            String maHangMoi = tinhMaHangTheoTongChiTieu(tongChiTieu);

            KhachHang khDb = timKhachHangTheoMa(maKH);
            if (khDb != null) {
                // Cập nhật khách hàng đã tồn tại
                khDb.setTenDaMua(tongChiTieu);
                khDb.setMaHang(maHangMoi);
                if (khachHangDAO.capNhatKhachHang(khDb)) {
                    soDongCapNhat++;
                }
            } else {
                // Thêm mới khách hàng (nếu file có đủ thông tin)
                khFile.setTenDaMua(tongChiTieu);
                khFile.setMaHang(maHangMoi);
                try {
                    if (themKhachHang(khFile)) {
                        soDongThemMoi++;
                    }
                } catch (Exception e) {
                    // Bỏ qua dòng lỗi, tiếp tục các dòng khác
                }
            }
        }

        return (soDongCapNhat + soDongThemMoi) > 0;
    }

    // Giữ tên hàm xuatFile cho tương thích cũ nếu cần
    public boolean xuatFile(File file) {
        ArrayList<KhachHang> list = layDanhSachKhachHang();
        return XuLyExcel.xuatFileKhachHang(file, list);
    }

    // Hàm xuatExcel giống cách đặt tên của HangThanhVienBUS
    public boolean xuatExcel(File file) {
        return xuatFile(file);
    }

    // Tính mã hạng thành viên dựa trên tổng chi tiêu
    private String tinhMaHangTheoTongChiTieu(double tongChiTieu) {
        String maHangChon = "HTV01"; // mặc định hạng thấp nhất
        double maxDieuKien = -1;

        ArrayList<HangThanhVien> dsHang = bus.HangThanhVienBUS.getHangThanhVienBUS().layListHangThanhVien();
        if (dsHang == null)
            return maHangChon;

        for (HangThanhVien h : dsHang) {
            if (tongChiTieu >= h.getDieuKien() && h.getDieuKien() > maxDieuKien) {
                maxDieuKien = h.getDieuKien();
                maHangChon = h.getMaHang();
            }
        }
        return maHangChon;
    }

}
