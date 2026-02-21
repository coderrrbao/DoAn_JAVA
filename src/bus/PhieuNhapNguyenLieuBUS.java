package bus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.PhieuNhapNguyenLieuDAO;
import dao.conection.DBConnection;
import dto.LoNguyenLieu;
import dto.PhieuNhapNguyenLieu;

public class PhieuNhapNguyenLieuBUS {

    private static PhieuNhapNguyenLieuBUS phieuNhapNguyenLieuBUS = null;
    private PhieuNhapNguyenLieuDAO phieuNhapNguyenLieuDAO = new PhieuNhapNguyenLieuDAO();

    public static PhieuNhapNguyenLieuBUS getPhieuNhapNguyenLieuBUS() {
        if (phieuNhapNguyenLieuBUS == null) {
            phieuNhapNguyenLieuBUS = new PhieuNhapNguyenLieuBUS();
        }
        return phieuNhapNguyenLieuBUS;
    }

    private ArrayList<PhieuNhapNguyenLieu> listPhieuNhapNguyenLieu = null;
    private boolean canUpdate;

    public PhieuNhapNguyenLieuBUS() {
        khoiTao();
    }

    public void khoiTao() {
        listPhieuNhapNguyenLieu = phieuNhapNguyenLieuDAO.layListPhieuNhapNguyenLieu();
        LoNguyenLieuBUS loNguyenLieuBUS = LoNguyenLieuBUS.getLoNguyenLieuBUS();
        for (PhieuNhapNguyenLieu phieuNhapNguyenLieu : listPhieuNhapNguyenLieu) {
            phieuNhapNguyenLieu.setListLoNguyenLieu(loNguyenLieuBUS.layLoNguyenLieuChoPhieuNhap(phieuNhapNguyenLieu.getMaPN()));
        }
        canUpdate = false;
    }

    public ArrayList<PhieuNhapNguyenLieu> layListPhieuNhapNguyenLieu() {
        if (canUpdate || listPhieuNhapNguyenLieu == null) {
            khoiTao();
        }
        return listPhieuNhapNguyenLieu;
    }

    public boolean themPhieuNhapNguyenLieu(PhieuNhapNguyenLieu phieuNhapNguyenLieu) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            LoNguyenLieuBUS loNguyenLieuBUS = LoNguyenLieuBUS.getLoNguyenLieuBUS();
            
            // Lấy mã phiếu nhập khả dụng (tự tăng) từ DAO
            String maPN = phieuNhapNguyenLieuDAO.layMaPhieuNhapNLKhaDung(conn);
            phieuNhapNguyenLieu.setMaPN(maPN);
            
            if (!phieuNhapNguyenLieuDAO.themPhieuNhapNguyenLieu(phieuNhapNguyenLieu, conn)) {
                throw new SQLException("Lỗi khi thêm Phiếu Nhập Nguyên Liệu");
            }

            // Thêm danh sách Lô Nguyên Liệu đi kèm
            for (LoNguyenLieu loNguyenLieu : phieuNhapNguyenLieu.getListLoNguyenLieu()) {
                loNguyenLieu.setMaPN(maPN);
                if (!loNguyenLieuBUS.themLoNguyenLieu(loNguyenLieu, conn)) {
                    throw new SQLException("Lỗi khi thêm Lô Nguyên Liệu");
                }
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        this.canUpdate = true;
        return true;
    }
}