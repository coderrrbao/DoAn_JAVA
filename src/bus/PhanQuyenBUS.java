package bus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import dao.PhanQuyenDAO;
import dao.conection.DBConnection;
import dto.PhanQuyen;

public class PhanQuyenBUS {
    private static PhanQuyenBUS phanQuyenBUS; // Biến static để lưu instance duy nhất
    private PhanQuyenDAO pqDAO = new PhanQuyenDAO();
    private ArrayList<PhanQuyen> listPhanQuyen;
    private boolean canUpdate = true; // Biến kiểm soát việc làm mới dữ liệu

    // Hàm Singleton để lấy instance duy nhất
    public static PhanQuyenBUS getPhanQuyenBUS() {
        if (phanQuyenBUS == null) {
            phanQuyenBUS = new PhanQuyenBUS();
        }
        return phanQuyenBUS;
    }

    public PhanQuyenBUS() {
        khoiTao();
    }

    // Hàm nạp dữ liệu từ DAO vào List
    public void khoiTao() {
        listPhanQuyen = pqDAO.layListPhanQuyen();
    }

    public ArrayList<PhanQuyen> layDanhSachPhanQuyen() {
        if (canUpdate || listPhanQuyen == null) {
            khoiTao();
            canUpdate = false;
        }
        return listPhanQuyen;
    }



    public boolean themPhanQuyen(PhanQuyen phanQuyen, Connection conn) {
        try {
            conn.setAutoCommit(false);

            if (!pqDAO.themPhanQuyen(phanQuyen, conn)) {
                throw new SQLException();
            }

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
        this.canUpdate = true;
        return true;
    }

    public boolean xoaPhanQuyen(PhanQuyen phanQuyen, Connection conn) {
        try {
            conn.setAutoCommit(false);

            if (!pqDAO.xoaPhanQuyen(phanQuyen, conn)) {
                return false;
            }

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
        this.canUpdate = true;
        return true;
    }

}