package bus;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import dao.LoNguyenLieuDAO;
import dao.conection.DBConnection;
import dto.LoNguyenLieu;
import dto.NguyenLieu;

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

    public int layTongLoChoNguyenLieu(String maNL) {
        if (canUpdate || listLoNguyenLieu == null) {
            khoitao();
            canUpdate = false;
        }
        int tong = 0;
        for (LoNguyenLieu loNguyenLieu : listLoNguyenLieu) {
            if (loNguyenLieu.getMaNL().equals(maNL)) {
                tong++;
            }
        }
        return tong;
    }

    public int layTongLoHetHangChoNguyenLieu(String maNL) {
        if (canUpdate || listLoNguyenLieu == null) {
            khoitao();
            canUpdate = false;
        }
        int tong = 0;
        try {
            for (LoNguyenLieu loNguyenLieu : listLoNguyenLieu) {
                LocalDate ngayHetHang = LocalDate.parse(loNguyenLieu.getHanSuDung());
                if (loNguyenLieu.getMaNL().equals(maNL) && ngayHetHang.isBefore(LocalDate.now())) {
                    tong++;
                }
            }
            return tong;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ArrayList<LoNguyenLieu> layLoChoNguyenLieu(String maNL) {
        if (canUpdate || listLoNguyenLieu == null) {
            khoitao();
            canUpdate = false;
        }

        ArrayList<LoNguyenLieu> list = new ArrayList<>();
        for (LoNguyenLieu lo : listLoNguyenLieu) {
            if (lo.getMaNL().equals(maNL)) {
                list.add(lo);
            }
        }

        return list;
    }

    public int layTongLoHetHan() {
        if (canUpdate || listLoNguyenLieu == null) {
            khoitao();
            canUpdate = false;
        }
        int tong = 0;
        try {
            for (LoNguyenLieu loNguyenLieu : listLoNguyenLieu) {
                LocalDate ngayHetHang = LocalDate.parse(loNguyenLieu.getHanSuDung());
                if (ngayHetHang.isBefore(LocalDate.now())) {
                    tong++;
                }
            }
            return tong;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public double laySoLuongNguyenLieuTrongKho(String ma) {
        double tong = 0;
        for (LoNguyenLieu loNguyenLieu : listLoNguyenLieu) {
            if (loNguyenLieu.getMaNL().equals(ma)) {
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

    // ==========================================
    // CÁC HÀM BỔ SUNG CHO TÍNH NĂNG XÁC NHẬN VÀ XÓA
    // ==========================================

    public boolean xacNhanLoNguyenLieu(LoNguyenLieu loNguyenLieu, Connection conn) {
        try {
            // Lưu ý: conn.setAutoCommit(false) và conn.commit() đã được xử lý ở
            // PhieuNhapBUS,
            // nhưng mình vẫn giữ cấu trúc try-catch giống với LoSanPhamBUS của bạn để đồng
            // bộ.
            conn.setAutoCommit(false);

            if (!loNguyenLieuDAO.xacNhanLoNguyenLieu(loNguyenLieu, conn)) {
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
            try {
                canUpdate = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean xoaLoNguyenLieu(LoNguyenLieu loNguyenLieu, Connection conn) {
        try {
            conn.setAutoCommit(false);

            if (!loNguyenLieuDAO.xoaLoNguyenLieu(loNguyenLieu, conn)) {
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
            try {
                canUpdate = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}