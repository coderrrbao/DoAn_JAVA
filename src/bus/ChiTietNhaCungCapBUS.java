package bus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.ChiTietNhaCungCapDAO;
import dto.ChiTietNhaCungCap;

public class ChiTietNhaCungCapBUS {

    private static ChiTietNhaCungCapBUS chiTietNhaCungCapBUS = null;

    public static ChiTietNhaCungCapBUS getChiTietNhaCungCapBUS() {
        if (chiTietNhaCungCapBUS == null) {
            chiTietNhaCungCapBUS = new ChiTietNhaCungCapBUS();
        }
        return chiTietNhaCungCapBUS;
    }

    private ChiTietNhaCungCapDAO chiTietNhaCungCapDAO = new ChiTietNhaCungCapDAO();

    private ArrayList<ChiTietNhaCungCap> listChiTietNhaCungCap;
    private boolean canUpdate = false;

    public ChiTietNhaCungCapBUS() {
        khoitao();
    }

    public void khoitao() {
        listChiTietNhaCungCap = chiTietNhaCungCapDAO.layListChiTietNhaCungCap();
    }

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