package bus;

import dao.HangThanhVienDAO;
import dao.conection.DBConnection;
import dto.HangThanhVien;
import util.XuLyExcel;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

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

    public boolean themHangThanhVien(HangThanhVien htv, Connection conn) throws SQLException {
        return hangThanhVienDAO.themHangThanhVien(htv, conn);
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

    public boolean xuatExcel(File file) {
        ArrayList<HangThanhVien> list = layListHangThanhVien();
        return XuLyExcel.xuatFileHangThanhVien(file, list);
    }

    public boolean nhapExcel(File file) {
        ArrayList<HangThanhVien> dsNhap = XuLyExcel.nhapFileHangThanhVien(file);

        if (dsNhap == null || dsNhap.isEmpty()) {
            return false;
        }

        HashSet<String> setTenHang = new HashSet<>();
        for (HangThanhVien htv : layListHangThanhVien()) {
            setTenHang.add(htv.getTenHang().trim().toLowerCase());
        }

        boolean hasAdded = false;
        Connection conn = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            for (HangThanhVien htv : dsNhap) {

                if (setTenHang.contains(htv.getTenHang().trim().toLowerCase())) {
                    System.out.println("Bỏ qua hạng thành viên đã tồn tại: " + htv.getTenHang());
                    continue;
                }

                if (themHangThanhVien(htv, conn)) {
                    setTenHang.add(htv.getTenHang().trim().toLowerCase());
                    hasAdded = true;
                } else {
                    throw new SQLException("Lỗi thao tác DB ở Hạng: " + htv.getTenHang());
                }
            }

            if (hasAdded) {
                conn.commit();
                this.canUpdate = true;
                this.khoitao();
            } else {
                conn.rollback();
            }

            return hasAdded;

        } catch (Exception e) {
            System.err.println("Lỗi khi nhập hạng thành viên: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            dongKetNoi(conn);
        }
    }
}