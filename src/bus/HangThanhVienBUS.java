package bus;

import dao.HangThanhVienDAO;
import dao.conection.DBConnection;
import dto.HangThanhVien;
import util.XuLyExcel;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class HangThanhVienBUS {
    private static HangThanhVienBUS hangThanhVienBUS = null;

    public static HangThanhVienBUS getHangThanhVienBUS() {
        if (hangThanhVienBUS == null) {
            hangThanhVienBUS = new HangThanhVienBUS();
        }
        return hangThanhVienBUS;
    }

    private HangThanhVienDAO hangThanhVienDAO = new HangThanhVienDAO();
    private ArrayList<HangThanhVien> listHangThanhVien;
    private boolean canUpdate = false;

    public HangThanhVienBUS() {
        khoitao();
    }

    public void khoitao() {
        Connection conn = DBConnection.getConnection();
        try {
            listHangThanhVien = hangThanhVienDAO.layListHangThanhVien();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<HangThanhVien> layListHangThanhVien() {
        if (canUpdate || listHangThanhVien == null) {
            khoitao();
            canUpdate = false;
        }
        return listHangThanhVien;
    }

    public HangThanhVien timHangThanhVien(String ma) {
        if (canUpdate || listHangThanhVien == null) {
            khoitao();
            canUpdate = false;
        }
        for (HangThanhVien htv : listHangThanhVien) {
            if (htv.getMaHang().equals(ma)) {
                return htv;
            }
        }
        return null;
    }

    public boolean themHangThanhVien(HangThanhVien htv) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            if (!hangThanhVienDAO.themHangThanhVien(htv, conn)) {
                throw new SQLException();
            }
            conn.commit();
            canUpdate = true;
            return true;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
            }
            return false;
        } finally {
            dongKetNoi(conn);
        }
    }

    public boolean xoaHangThanhVien(String maHang) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            if (!hangThanhVienDAO.xoaHangThanhVien(maHang, conn)) {
                throw new SQLException();
            }
            conn.commit();
            canUpdate = true;
            return true;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
            }
            return false;
        } finally {
            dongKetNoi(conn);
        }
    }

    public boolean capNhatHangThanhVien(HangThanhVien htv) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            if (!hangThanhVienDAO.capNhatHangThanhVien(htv, conn)) {
                throw new SQLException();
            }
            conn.commit();
            canUpdate = true;
            return true;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
            }
            return false;
        } finally {
            dongKetNoi(conn);
        }
    }

    public ArrayList<HangThanhVien> timKiemHangThanhVien(String keyword) {
        ArrayList<HangThanhVien> ketQua = new ArrayList<>();
        ArrayList<HangThanhVien> dsGoc = layListHangThanhVien();

        String lowerKeyword = keyword.toLowerCase().trim();
        if (keyword == null || keyword.trim().isEmpty()) {
            return dsGoc;
        }

        for (HangThanhVien htv : dsGoc) {
            if (htv.getMaHang().toLowerCase().contains(lowerKeyword)
                    || htv.getTenHang().toLowerCase().contains(lowerKeyword)) {
                ketQua.add(htv);
            }
        }
        return ketQua;
    }

    private void dongKetNoi(Connection conn) {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Đã sửa lại nhận String filePath nhưng truyền File qua XuLyExcel
    public boolean xuatExcel(String filePath) {
        File file = new File(filePath);
        ArrayList<HangThanhVien> list = layListHangThanhVien();
        return XuLyExcel.xuatFileHangThanhVien(file, list);
    }

    // Đã sửa lại gọi XuLyExcel và tận dụng hàm xử lý list
    public boolean nhapExcel(String filePath) {
        File file = new File(filePath);
        ArrayList<HangThanhVien> dsMoi = XuLyExcel.nhapFileHangThanhVien(file);
        
        if (dsMoi == null || dsMoi.isEmpty()) {
            return false;
        }
        
        nhapDanhSachTuExcel(dsMoi);
        return true;
    }

    public String nhapDanhSachTuExcel(ArrayList<HangThanhVien> danhSachImport) {
        if (danhSachImport == null || danhSachImport.isEmpty()) {
            return "Không có dữ liệu hợp lệ để nhập!";
        }

        int soLuongThanhCong = 0;
        int soLuongThatBai = 0;

        for (HangThanhVien htv : danhSachImport) {
            boolean ketQua = themHangThanhVien(htv);
            if (ketQua) {
                soLuongThanhCong++;
            } else {
                soLuongThatBai++;
            }
        }

        this.canUpdate = true;
        return "Nhập Excel hoàn tất!\n- Thành công: " + soLuongThanhCong + "\n- Thất bại: " + soLuongThatBai;
    }
}