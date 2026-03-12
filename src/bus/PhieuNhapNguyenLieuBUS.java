package bus;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.PhieuNhapNguyenLieuDAO;
import dao.conection.DBConnection;
import dto.LoNguyenLieu;
import dto.PhieuNhapNguyenLieu;
import util.XuLyExcel;

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
            phieuNhapNguyenLieu
                    .setListLoNguyenLieu(loNguyenLieuBUS.layLoNguyenLieuChoPhieuNhap(phieuNhapNguyenLieu.getMaPN()));
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

            String maPN = phieuNhapNguyenLieuDAO.layMaPhieuNhapNLKhaDung(conn);
            phieuNhapNguyenLieu.setMaPN(maPN);

            if (!phieuNhapNguyenLieuDAO.themPhieuNhapNguyenLieu(phieuNhapNguyenLieu, conn)) {
                throw new SQLException("Lỗi khi thêm Phiếu Nhập Nguyên Liệu");
            }

            for (LoNguyenLieu loNguyenLieu : phieuNhapNguyenLieu.getListLoNguyenLieu()) {
                loNguyenLieu.setMaPN(maPN);
                loNguyenLieu.setTrangThaiXuLy("Đang xử lý");
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

    public boolean capNhapPhieuNhapNguyenLieu(PhieuNhapNguyenLieu phieuNhapNguyenLieu) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            if (!phieuNhapNguyenLieuDAO.capNhapPhieuNhapNguyenLieu(phieuNhapNguyenLieu, conn)) {
                throw new SQLException();
            }
            LoNguyenLieuBUS loNguyenLieuBUS = LoNguyenLieuBUS.getLoNguyenLieuBUS();
            if (phieuNhapNguyenLieu.getTrangThaiXuLy().equals("Đã xử lý")) {
                for (LoNguyenLieu loNguyenLieu : phieuNhapNguyenLieu.getListLoNguyenLieu()) {
                    if (!loNguyenLieuBUS.xacNhanLoNguyenLieu(loNguyenLieu, conn)) {
                        throw new SQLException();
                    }
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

    public boolean xoaPhieuNhapNguyenLieu(PhieuNhapNguyenLieu phieuNhapNguyenLieu) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            if (!phieuNhapNguyenLieuDAO.xoaPhieuNhapNguyenLieu(phieuNhapNguyenLieu, conn)) {
                throw new SQLException();
            }
            LoNguyenLieuBUS loNguyenLieuBUS = LoNguyenLieuBUS.getLoNguyenLieuBUS();
            for (LoNguyenLieu loNguyenLieu : phieuNhapNguyenLieu.getListLoNguyenLieu()) {

                if (!loNguyenLieuBUS.xoaLoNguyenLieu(loNguyenLieu, conn)) {
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

    public PhieuNhapNguyenLieu timPhieuNhapNguyenLieu(String ma) {
        if (canUpdate || listPhieuNhapNguyenLieu == null) {
            khoiTao();
        }
        for (PhieuNhapNguyenLieu phieuNhapNguyenLieu : listPhieuNhapNguyenLieu) {
            if (phieuNhapNguyenLieu.getMaPN().equals(ma)) {
                return phieuNhapNguyenLieu;
            }
        }
        return null;
    }

    public boolean themPhieuNhapNguyenLieu(PhieuNhapNguyenLieu phieuNhapNguyenLieu, Connection conn)
            throws SQLException {
        LoNguyenLieuBUS loNguyenLieuBUS = LoNguyenLieuBUS.getLoNguyenLieuBUS();

        String maPN = phieuNhapNguyenLieuDAO.layMaPhieuNhapNLKhaDung(conn);
        phieuNhapNguyenLieu.setMaPN(maPN);

        if (!phieuNhapNguyenLieuDAO.themPhieuNhapNguyenLieu(phieuNhapNguyenLieu, conn)) {
            return false;
        }

        if (phieuNhapNguyenLieu.getListLoNguyenLieu() != null) {
            for (LoNguyenLieu loNguyenLieu : phieuNhapNguyenLieu.getListLoNguyenLieu()) {
                loNguyenLieu.setMaPN(maPN);
                loNguyenLieu.setTrangThaiXuLy("Đang xử lý");

                if (!loNguyenLieuBUS.themLoNguyenLieu(loNguyenLieu, conn)) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean nhapExcel(File file) {

        ArrayList<PhieuNhapNguyenLieu> dsNhap = XuLyExcel.nhapFilePhieuNhapNguyenLieu(file);

        if (dsNhap == null || dsNhap.isEmpty()) {
            return false;
        }

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            for (PhieuNhapNguyenLieu pn : dsNhap) {
                pn.setTrangThaiXuLy("Đang xử lý");

                if (!themPhieuNhapNguyenLieu(pn, conn)) {

                    throw new SQLException("Lỗi khi thêm Phiếu Nhập Nguyên Liệu của NCC: " + pn.getMaNCC());
                }
            }

            conn.commit();
            this.canUpdate = true;
            this.khoiTao();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
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

    public boolean xuatExcel(File file) {
        return XuLyExcel.xuatFilePhieuNhapNguyenLieu(file, layListPhieuNhapNguyenLieu());
    }
}