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

    public List<KhachHang> layDanhSachKhachHang() {
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
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            int count = 0;

            for (SanPham sp : dsNhap) {

                if (sp.getDanhMuc() != null && sp.getDanhMuc().getTenDM() != null) {
                    DanhMuc dmFull = danhMucDao.timDanhMucTheoTen(sp.getDanhMuc().getTenDM());
                    if (dmFull != null) {
                        sp.setDanhMuc(dmFull);
                    } else {

                        throw new SQLException("Lỗi: Danh mục '" + sp.getDanhMuc().getTenDM()
                                + "' không tồn tại trong hệ thống. Vui lòng thêm danh mục này trước khi import!");
                    }
                } else {
                    throw new SQLException("Lỗi: Sản phẩm '" + sp.getMaSP() + "' thiếu thông tin Danh Mục!");
                }

                if (!sanPhamDAO.themSanPham(sp, conn)) {
                    throw new SQLException("Lỗi thêm Sản phẩm: " + sp.getMaSP());
                }

                if (sp.getListSize() != null) {
                    for (Size s : sp.getListSize()) {
                        if (!sizeDao.themSize(s, conn)) {
                            throw new SQLException("Lỗi thêm Size: " + s.getMaSize());
                        }
                    }
                }

                if (sp.getCongThuc() != null) {
                    CongThuc ct = sp.getCongThuc();
                    if (!congThucDao.themCongThuc(ct, conn)) {
                        throw new SQLException("Lỗi thêm Công thức cho SP: " + sp.getMaSP());
                    }

                    if (ct.getListChiTietCongThuc() != null) {
                        for (ChiTietCongThuc ctct : ct.getListChiTietCongThuc()) {
                            if (!ctctDao.themCTCT(ctct, conn)) {
                                throw new SQLException("Lỗi thêm CTCT cho mã CT: " + ct.getMaCT());
                            }
                        }
                    }
                }
                count++;
            }

            conn.commit();
            this.canUpdate = true;
            return count > 0;

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null)
                    conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean xuatFile(File file) {

    }

}
