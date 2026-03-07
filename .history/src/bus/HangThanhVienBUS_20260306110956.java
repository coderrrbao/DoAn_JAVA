package bus;

import dao.HangThanhVienDAO;
import dao.conection.DBConnection;
import dto.HangThanhVien;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import javax.swing.*;

import javax.swing.JOptionPane;

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
                if (conn != null)
                    conn.close();
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

    public int importExcel(List<HangThanhVien> list) throws Exception {

        HangThanhVienDAO dao = new HangThanhVienDAO();
        Connection conn = DBConnection.getConnection();

        int inserted = 0;

        try {

            conn.setAutoCommit(false);

            for (HangThanhVien h : list) {
                dao.insert(conn, h);
            }

            conn.commit();

            JOptionPane.showMessageDialog(
                    this,
                    "Import Thành công:",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);

            dao.layListHangThanhVien();

        } catch (Exception e) {

            try {
                if (conn != null)
                    conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            JOptionPane.showMessageDialog(this,
                    "Import thất bại!\nCó dữ liệu trùng hoặc sai.\nĐã rollback toàn bộ.",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);

        } finally {

            conn.setAutoCommit(true);
            conn.close();

        }
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
}