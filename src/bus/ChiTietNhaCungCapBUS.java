package bus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.ChiTietNhaCungCapDAO;
import dto.ChiTietNhaCungCap;

public class ChiTietNhaCungCapBUS {

    // 1. Áp dụng Singleton Pattern
    private static ChiTietNhaCungCapBUS chiTietNhaCungCapBUS = null;

    public static ChiTietNhaCungCapBUS getChiTietNhaCungCapBUS() {
        if (chiTietNhaCungCapBUS == null) {
            chiTietNhaCungCapBUS = new ChiTietNhaCungCapBUS();
        }
        return chiTietNhaCungCapBUS;
    }

    private ChiTietNhaCungCapDAO chiTietNhaCungCapDAO = new ChiTietNhaCungCapDAO();

    // 2. Khai báo Mảng lưu trữ (Cache)
    private ArrayList<ChiTietNhaCungCap> listChiTietNhaCungCap;
    private boolean canUpdate = false;

    // Constructor
    public ChiTietNhaCungCapBUS() {
        khoitao();
    }

    // Hàm load dữ liệu từ Database (thông qua DAO) vào mảng
    public void khoitao() {
        listChiTietNhaCungCap = chiTietNhaCungCapDAO.layListChiTietNhaCungCap();
    }

    // ========================================================
    // HÀM LẤY LIST MÀ BẠN YÊU CẦU
    // ========================================================

    public ArrayList<ChiTietNhaCungCap> layListChiTietNhaCungCap() {
        if (canUpdate || listChiTietNhaCungCap == null) {
            canUpdate = false;
            khoitao();
        }
        return listChiTietNhaCungCap;
    }

    public void setCanUpdate(boolean canUpdate) {
        this.canUpdate = canUpdate;
    }

    public boolean themChiTietNhaCungCap(ChiTietNhaCungCap chiTietNhaCungCap, Connection conn) {
        try {

            if (!chiTietNhaCungCapDAO.themChiTietNhaCungCap(chiTietNhaCungCap, conn)) {
                throw new SQLException();
            }

            conn.commit();

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    canUpdate = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return true;
    }

    public boolean xoaChiTietNhaCungCap(String ma, Connection conn) {
         try {

            if (!chiTietNhaCungCapDAO.xoaChiTietNhaCungCap(ma, conn)) {
                throw new SQLException();
            }

            conn.commit();

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    canUpdate = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return true;
    }

    public boolean capNhapChiTietNhaCungCap(ChiTietNhaCungCap chiTietNhaCungCap, Connection conn) {
         try {

            if (!chiTietNhaCungCapDAO.capNhapChiTietNhaCungCap(chiTietNhaCungCap, conn)) {
                throw new SQLException();
            }

            conn.commit();

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    canUpdate = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return true;
    }
}