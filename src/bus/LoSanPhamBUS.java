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

    public int laySoLuongSanPhamTrongKho(String maSP) {
        int tong = 0;
        for (LoSanPham loSanPham : listLoSanPham) {
            if (loSanPham.getMaSP().equals(maSP)) {
                tong += loSanPham.getSoLuong();
            }
        }
        return tong;
    }

    public boolean capNhapLoSanPham(LoSanPham loSanPham) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);

            if (!loSanPhamDAO.capNhapLoSanPham(loSanPham, conn)) {
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

    public boolean xacNhanLoSanPham(LoSanPham loSanPham, Connection conn) {
        try {
            conn.setAutoCommit(false);

            if (!loSanPhamDAO.xacNhanLoSanPham(loSanPham, conn)) {
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
                    canUpdate = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public boolean themLoSanPham(LoSanPham loSanPham, Connection conn) {
        try {
            conn.setAutoCommit(false);
            if (!loSanPhamDAO.themLoSanPham(loSanPham, conn)) {
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

    public ArrayList<LoSanPham> layLoSanPhamChoPhieuNhap(String maPN) {
        if (canUpdate || listLoSanPham == null) {
            khoitao();
            canUpdate = false;
        }
        ArrayList<LoSanPham> list = new ArrayList<>();
        for (LoSanPham loSanPham : listLoSanPham) {
            if (loSanPham.getMaPN().equals(maPN)) {
                list.add(loSanPham);
            }
        }
        return list;
    }
}