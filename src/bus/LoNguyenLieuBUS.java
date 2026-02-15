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
                conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                    canUpdate = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;

    }
}