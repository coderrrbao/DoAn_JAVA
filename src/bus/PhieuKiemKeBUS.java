package bus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.PhieuKiemKeDAO;
import dao.conection.DBConnection;
import dto.LoNguyenLieu;
import dto.LoSanPham;
import dto.PhieuKiemKe;

public class PhieuKiemKeBUS {

    private static PhieuKiemKeBUS phieuKiemKeBUS = null;

    public static PhieuKiemKeBUS getPhieuKiemKeBUS() {
        if (phieuKiemKeBUS == null) {
            phieuKiemKeBUS = new PhieuKiemKeBUS();
        }
        return phieuKiemKeBUS;
    }

    private PhieuKiemKeDAO phieuKiemKeDAO = new PhieuKiemKeDAO();
    private ArrayList<PhieuKiemKe> listPhieuKiemKe;
    private boolean canUpdate = false;

    public PhieuKiemKeBUS() {
        khoitao();
    }

    public void khoitao() {
        listPhieuKiemKe = phieuKiemKeDAO.layListPhieuKiemKe();
    }

    public ArrayList<PhieuKiemKe> layListKiemKe() {
        if (canUpdate || listPhieuKiemKe == null) {
            canUpdate = false;
            khoitao();
        }
        return listPhieuKiemKe;
    }

    public PhieuKiemKe timPhieuKiemKe(String ma) {
        if (canUpdate || listPhieuKiemKe == null) {
            khoitao();
            canUpdate = false;
        }
        for (PhieuKiemKe phieu : listPhieuKiemKe) {
            if (phieu.getMaKK().equals(ma)) {
                return phieu;
            }
        }
        return null;
    }

    public boolean themPhieuKiemKe(PhieuKiemKe phieuKiemKe) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);

            if (!phieuKiemKeDAO.themPhieuKiemKe(phieuKiemKe, conn)) {
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

    public boolean xoaPhieuKiemKe(PhieuKiemKe phieuKiemKe) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);

            if (!phieuKiemKeDAO.xoaPhieuKiemKe(phieuKiemKe, conn)) {
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

    public boolean capNhapPhieuKiemKe(PhieuKiemKe phieuKiemKe) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);

            if (!phieuKiemKeDAO.capNhapPhieuKiemKe(phieuKiemKe, conn)) {
                throw new SQLException();
            }
            if (phieuKiemKe.getTrangThaiXuLy().equals("Đã xác nhận")) {
                if (phieuKiemKe.getLoaiLo().equals("Nguyên liệu")) {
                    LoNguyenLieuBUS loNguyenLieuBUS = LoNguyenLieuBUS.getLoNguyenLieuBUS();
                    LoNguyenLieu loNguyenLieu = loNguyenLieuBUS.timLoNguyenLieu(phieuKiemKe.getMaLo());
                    loNguyenLieu.setSoLuong(phieuKiemKe.getSoLuongThuc());
                    loNguyenLieuBUS.capNhapLoNguyenLieu(loNguyenLieu);
                } else if (phieuKiemKe.getLoaiLo().equals("Sản phẩm")) {
                    LoSanPhamBUS loSanPhamBUS = LoSanPhamBUS.getLoSanPhamBUS();
                    LoSanPham loSanPham = loSanPhamBUS.timLoSanPham(phieuKiemKe.getMaLo());
                    loSanPham.setSoLuong(phieuKiemKe.getSoLuongThuc());
                    loSanPhamBUS.capNhapLoSanPham(loSanPham);
                } else {
                    System.out.println("Lỗi loại lô trong phiếu kiểm kê,không thể cập nhập số lượng");
                }
            }

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
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
                    canUpdate = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}