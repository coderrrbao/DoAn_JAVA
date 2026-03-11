package bus;

import dao.KhuyenMaiDAO;
import dao.conection.DBConnection;
import dto.KhuyenMai;
import util.XuLyExcel;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;

public class KhuyenMaiBUS {
    private static KhuyenMaiBUS khuyenMaiBUS = null;

    public static KhuyenMaiBUS getKhuyenMaiBUS() {
        if (khuyenMaiBUS == null) {
            khuyenMaiBUS = new KhuyenMaiBUS();
        }
        return khuyenMaiBUS;
    }

    private KhuyenMaiDAO khuyenMaiDAO = new KhuyenMaiDAO();
    private ArrayList<KhuyenMai> listKhuyenMai;
    private boolean canUpdate = false;

    public KhuyenMaiBUS() {
        khoitao();
    }

    public void khoitao() {
        Connection conn = DBConnection.getConnection();
        try {
            listKhuyenMai = khuyenMaiDAO.layListKhuyenMai(conn);
        } finally {
            dongKetNoi(conn);
        }
    }

    public ArrayList<KhuyenMai> layListKhuyenMai() {
        if (canUpdate || listKhuyenMai == null) {
            khoitao();
            canUpdate = false;
        }
        return listKhuyenMai;
    }

    public KhuyenMai timKhuyenMai(String ma) {
        for (KhuyenMai km : layListKhuyenMai()) {
            if (km.getMaKM().equals(ma)) {
                return km;
            }
        }
        return null;
    }

    public boolean themKhuyenMai(KhuyenMai km) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            if (!khuyenMaiDAO.themKhuyenMai(km, conn)) {
                throw new SQLException();
            }
            conn.commit();
            canUpdate = true;
            return true;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
            }
            return false;
        } finally {
            dongKetNoi(conn);
        }
    }

    public boolean themKhuyenMai(KhuyenMai km, Connection conn) throws SQLException {
        return khuyenMaiDAO.themKhuyenMai(km, conn);
    }

    public boolean capNhatKhuyenMai(KhuyenMai km) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            if (!khuyenMaiDAO.capNhatKhuyenMai(km, conn)) {
                throw new SQLException();
            }
            conn.commit();
            canUpdate = true;
            return true;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
            }
            return false;
        } finally {
            dongKetNoi(conn);
        }
    }

    public boolean xoaKhuyenMai(String maKM) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            if (!khuyenMaiDAO.xoaKhuyenMai(maKM, conn)) {
                throw new SQLException();
            }
            conn.commit();
            canUpdate = true;
            return true;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
            }
            return false;
        } finally {
            dongKetNoi(conn);
        }
    }

    public ArrayList<KhuyenMai> timKiemKhuyenMai(String keyword) {
        ArrayList<KhuyenMai> ketQua = new ArrayList<>();
        ArrayList<KhuyenMai> dsGoc = layListKhuyenMai();

        String lowerKeyword = keyword.toLowerCase().trim();
        if (keyword.isEmpty()) {
            return dsGoc;
        }
        for (KhuyenMai km : dsGoc) {
            if (km.getMaKM().toLowerCase().contains(lowerKeyword)) {
                ketQua.add(km);
            }
        }
        return ketQua;
    }

    public String xacDinhTrangThai(KhuyenMai km) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            long currentMillis = System.currentTimeMillis();

            long tuNgay = sdf.parse(km.getTuNgay()).getTime();
            long denNgay = sdf.parse(km.getDenNgay()).getTime() + 86400000L - 1;

            if (currentMillis < tuNgay)
                return "Chờ kích hoạt";
            if (currentMillis > denNgay)
                return "Đã kết thúc";
            return "Đang áp dụng";
        } catch (Exception e) {
            return "Lỗi định dạng";
        }
    }

    public String kiemTraTrangThaiHopLe(KhuyenMai km) {
        if (km == null) {
            return "Mã khuyến mãi không tồn tại!";
        }
        if (km.getTuNgay() == null || km.getDenNgay() == null ||
                km.getTuNgay().isEmpty() || km.getDenNgay().isEmpty()) {
            return "Dữ liệu thời gian của mã không hợp lệ!";
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);

            long currentTime = System.currentTimeMillis();
            long timeStart = sdf.parse(km.getTuNgay()).getTime();
            long timeEnd = sdf.parse(km.getDenNgay()).getTime() + 86399999L;

            if (currentTime < timeStart) {
                return "Mã khuyến mãi này chưa đến thời gian bắt đầu áp dụng!";
            }
            if (currentTime > timeEnd) {
                return "Mã khuyến mãi này đã hết hạn sử dụng!";
            }
        } catch (Exception e) {
            return "Lỗi định dạng ngày tháng (Yêu cầu: yyyy-MM-dd)!";
        }
        return "";
    }

    public boolean xuatExcel(File file) {
        ArrayList<KhuyenMai> dsXuat = layListKhuyenMai();
        if (dsXuat == null || dsXuat.isEmpty())
            return false;
        return XuLyExcel.xuatFileKhuyenMai(file, dsXuat);
    }

    public boolean nhapExcel(File file) {
        ArrayList<KhuyenMai> dsNhap = XuLyExcel.nhapFileKhuyenMai(file);
        if (dsNhap == null || dsNhap.isEmpty()) {
            return false;
        }

        HashSet<String> setMaKM = new HashSet<>();
        for (KhuyenMai kmHienTai : layListKhuyenMai()) {
            setMaKM.add(kmHienTai.getMaKM().trim().toLowerCase());
        }

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            for (KhuyenMai km : dsNhap) {
                String maKmMoi = km.getMaKM().trim().toLowerCase();

                if (setMaKM.contains(maKmMoi)) {
                    throw new SQLException("Trùng mã khuyến mãi đã tồn tại: " + km.getMaKM());
                }

                if (!themKhuyenMai(km, conn)) {
                    throw new SQLException("Lỗi hệ thống khi thêm mã: " + km.getMaKM());
                }

                setMaKM.add(maKmMoi);
            }

            conn.commit();
            this.canUpdate = true;
            this.khoitao();
            return true;

        } catch (Exception e) {
            System.err.println("Lỗi Import Excel Khuyến Mãi: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            dongKetNoi(conn);
        }
    }

    private void dongKetNoi(Connection conn) {
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