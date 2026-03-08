package bus;

import java.awt.List;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.PhieuNhapSanPhamDAO;
import dao.conection.DBConnection;
import dto.LoSanPham;
import dto.PhieuNhapSanPham;
import util.XuLyExcel;

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
                loSanPham.setTrangThaiXuLy("Đang xử lý");
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

    public boolean capNhapPhieuNhapSanPham(PhieuNhapSanPham phieuNhapSanPham) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            if (!phieuNhapSanPhamDAO.capNhapPhieuNhapSanPham(phieuNhapSanPham, conn)) {
                throw new SQLException();
            }
            LoSanPhamBUS loSanPhamBUS = LoSanPhamBUS.getLoSanPhamBUS();
            for (LoSanPham loSanPham : phieuNhapSanPham.getListLoSanPham()) {
                if (!loSanPhamBUS.xacNhanLoSanPham(loSanPham, conn)) {
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

    public boolean xoaPhieuNhapSanPham(PhieuNhapSanPham phieuNhapSanPham) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            if (!phieuNhapSanPhamDAO.xoaPhieuNhapSanPham(phieuNhapSanPham, conn)) {
                throw new SQLException();
            }
            LoSanPhamBUS loSanPhamBUS = LoSanPhamBUS.getLoSanPhamBUS();
            for (LoSanPham loSanPham : phieuNhapSanPham.getListLoSanPham()) {
                if (!loSanPhamBUS.xacNhanLoSanPham(loSanPham, conn)) {
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

    public PhieuNhapSanPham timPhieuNhapSanPham(String ma) {
        if (canUpdate || listPhieuNhapSanPham == null) {
            khoiTao();
        }
        for (PhieuNhapSanPham phieuNhapSanPham : listPhieuNhapSanPham) {
            if (phieuNhapSanPham.getMaPN().equals(ma)) {
                return phieuNhapSanPham;
            }
        }
        return null;
    }

    public boolean nhapExcel(File file) {
        ArrayList<PhieuNhapSanPham> dsNhap = XuLyExcel.nhapFilePhieuNhapSanPham(file);
        if (dsNhap == null || dsNhap.isEmpty())
            return false;

        int thanhCong = 0;
        for (PhieuNhapSanPham pn : dsNhap) {
            pn.setTrangThaiXuLy("Đang xử lý");
            if (themPhieuNhapSanPham(pn)) {
                thanhCong++;
            }
        }
        return thanhCong > 0;
    }

    public boolean xuatExcel(File file) {
        ArrayList<PhieuNhapSanPham> list = layListPhieuNhapSanPham();
        return XuLyExcel.xuatFilePhieuNhapSanPham(file, list);
    }
}
