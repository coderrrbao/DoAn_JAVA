package bus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.PhieuNhapSanPhamDAO;
import dao.conection.DBConnection;
import dto.LoSanPham;
import dto.PhieuNhapSanPham;

public class PhieuNhapSanPhamBUS {
    private static PhieuNhapSanPhamBUS phieuNhapSanPhamBUS = null;
    private PhieuNhapSanPhamDAO phieuNhapSanPhamDAO = new PhieuNhapSanPhamDAO();

    public static PhieuNhapSanPhamBUS getPhieuNhapSanPhamBUS() {
        if (phieuNhapSanPhamBUS == null) {
            phieuNhapSanPhamBUS = new PhieuNhapSanPhamBUS();
        }
        return phieuNhapSanPhamBUS;
    }

    private ArrayList<PhieuNhapSanPham> listPhieuNhapSanPham = null;
    private boolean canUpdate;

    public PhieuNhapSanPhamBUS() {
        khoiTao();
    }

    public void khoiTao() {
        listPhieuNhapSanPham = phieuNhapSanPhamDAO.layListPhieuNhapSanPham();
        LoSanPhamBUS loSanPhamBUS = LoSanPhamBUS.getLoSanPhamBUS();
        for (PhieuNhapSanPham phieuNhapSanPham : listPhieuNhapSanPham) {
            phieuNhapSanPham.setListLoSanPham(loSanPhamBUS.layLoSanPhamChoPhieuNhap(phieuNhapSanPham.getMaPN()));
        }
        canUpdate = false;
    }

    public ArrayList<PhieuNhapSanPham> layListPhieuNhapSanPham() {
        if (canUpdate || listPhieuNhapSanPham == null) {
            khoiTao();
        }
        return listPhieuNhapSanPham;
    }

    public boolean themPhieuNhapSanPham(PhieuNhapSanPham phieuNhapSanPham) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            LoSanPhamBUS loSanPhamBUS = LoSanPhamBUS.getLoSanPhamBUS();
            String maPN = phieuNhapSanPhamDAO.layMaPhieuNhapSPKhaDung(conn);
            phieuNhapSanPham.setMaPN(maPN);
            if (!phieuNhapSanPhamDAO.themPhieuNhapSanPham(phieuNhapSanPham, conn)) {
                throw new SQLException();
            }

            for (LoSanPham loSanPham : phieuNhapSanPham.getListLoSanPham()) {
                loSanPham.setMaPN(maPN);
                if (!loSanPhamBUS.themLoSanPham(loSanPham, conn)) {
                    throw new SQLException();
                }

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
        this.canUpdate = true;
        return true;
    }

}
