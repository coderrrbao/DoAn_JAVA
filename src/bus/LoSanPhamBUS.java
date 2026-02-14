package bus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import dao.LoSanPhamDAO;
import dao.conection.DBConnection;
import dto.LoSanPham;

public class LoSanPhamBUS {

    private static LoSanPhamBUS loSanPhamBUS = null;

    public static LoSanPhamBUS getLoSanPhamBUS() {
        if (loSanPhamBUS == null) {
            loSanPhamBUS = new LoSanPhamBUS();
        }
        return loSanPhamBUS;
    }

    private LoSanPhamDAO loSanPhamDAO = new LoSanPhamDAO();
    private ArrayList<LoSanPham> listLoSanPham;
    private boolean canUpdate = false;

    public LoSanPhamBUS() {
        khoitao();
    }

    public void khoitao() {
        listLoSanPham = loSanPhamDAO.layListLoSanPham();
    }

    public ArrayList<LoSanPham> layListLoSanPham() {
        if (canUpdate || listLoSanPham == null) {
            canUpdate = false;
            khoitao();
        }
        return listLoSanPham;
    }

    public LoSanPham timLoSanPham(String maLo) {
        if (canUpdate || listLoSanPham == null) {
            khoitao();
            canUpdate = false;
        }
        for (LoSanPham lo : listLoSanPham) {
            if (lo.getMaLoSP().equals(maLo)) {
                return lo;
            }
        }
        return null;
    }

    public boolean capNhapLoSanPham(LoSanPham loSanPham) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);

            if (!loSanPhamDAO.capNhapLoSanPham(loSanPham, conn)) {
                throw new SQLException("Update LoSanPham failed");
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
}