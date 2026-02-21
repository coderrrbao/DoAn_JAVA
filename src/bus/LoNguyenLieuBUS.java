package bus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import dao.LoNguyenLieuDAO;
import dao.conection.DBConnection;
import dto.LoNguyenLieu;

public class LoNguyenLieuBUS {

    private static LoNguyenLieuBUS loNguyenLieuBUS = null;

    public static LoNguyenLieuBUS getLoNguyenLieuBUS() {
        if (loNguyenLieuBUS == null) {
            loNguyenLieuBUS = new LoNguyenLieuBUS();
        }
        return loNguyenLieuBUS;
    }

    private LoNguyenLieuDAO loNguyenLieuDAO = new LoNguyenLieuDAO();
    private ArrayList<LoNguyenLieu> listLoNguyenLieu;
    private boolean canUpdate = false;

    public LoNguyenLieuBUS() {
        khoitao();
    }

    public void khoitao() {
        listLoNguyenLieu = loNguyenLieuDAO.layListLoNguyenLieu();
    }

    public ArrayList<LoNguyenLieu> layListLoNguyenLieu() {
        if (canUpdate || listLoNguyenLieu == null) {
            canUpdate = false;
            khoitao();
        }
        return listLoNguyenLieu;
    }

    public LoNguyenLieu timLoNguyenLieu(String maLo) {
        if (canUpdate || listLoNguyenLieu == null) {
            khoitao();
            canUpdate = false;
        }
        for (LoNguyenLieu lo : listLoNguyenLieu) {
            if (lo.getMaLoNL().equals(maLo)) {
                return lo;
            }
        }
        return null;
    }

    public int laySoLuongNguyenLieuTrongKho(String maNL) {
        int tong = 0;
        for (LoNguyenLieu loNguyenLieu : listLoNguyenLieu) {
            if (loNguyenLieu.getMaNL().equals(maNL)) {
                tong += loNguyenLieu.getSoLuong();
            }
        }
        return tong;
    }

    public boolean capNhapLoNguyenLieu(LoNguyenLieu loNguyenLieu) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);

            if (!loNguyenLieuDAO.capNhapLoNguyenLieu(loNguyenLieu, conn)) {
                throw new SQLException();
            }

            conn.commit();
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                    canUpdate = true; // Đánh dấu để lần lấy list sau sẽ load lại từ DB
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public boolean themLoNguyenLieu(LoNguyenLieu loNguyenLieu, Connection conn) {
        try {
            conn.setAutoCommit(false);

            if (!loNguyenLieuDAO.themLoNguyenLieu(loNguyenLieu, conn)) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                canUpdate = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public ArrayList<LoNguyenLieu> layLoNguyenLieuChoPhieuNhap(String maPN) {
        if (canUpdate || listLoNguyenLieu == null) {
            khoitao();
            canUpdate = false;
        }
        ArrayList<LoNguyenLieu> list = new ArrayList<>();
        for (LoNguyenLieu loNguyenLieu : listLoNguyenLieu) {
            if (loNguyenLieu.getMaPN().equals(maPN)) {
                list.add(loNguyenLieu);
            }
        }
        return list;
    }
}