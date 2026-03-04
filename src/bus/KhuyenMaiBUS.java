package bus;

import dao.KhuyenMaiDAO;
import dao.conection.DBConnection;
import dto.KhuyenMai;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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

            // Parse chuỗi ngày thành số milliseconds để so sánh
            long tuNgay = sdf.parse(km.getTuNgay()).getTime();
            long denNgay = sdf.parse(km.getDenNgay()).getTime() + 86400000L - 1; // +1 ngày trừ 1ms

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
        // 1. Kiểm tra đối tượng null
        if (km == null) {
            return "Mã khuyến mãi không tồn tại!";
        }

        // 2. Kiểm tra tính hợp lệ của chuỗi ngày tháng
        if (km.getTuNgay() == null || km.getDenNgay() == null ||
                km.getTuNgay().isEmpty() || km.getDenNgay().isEmpty()) {
            return "Dữ liệu thời gian của mã không hợp lệ!";
        }

        try {
            // Định dạng chuẩn yyyy-MM-dd để khớp với dữ liệu từ DAO
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false); // Kiểm tra nghiêm ngặt tính đúng đắn của ngày

            long currentTime = System.currentTimeMillis();

            // Chuyển String sang milliseconds
            long timeStart = sdf.parse(km.getTuNgay()).getTime();

            // timeEnd + 86399999ms để tính đến hết 23:59:59 của ngày kết thúc
            long timeEnd = sdf.parse(km.getDenNgay()).getTime() + 86399999L;

            // 3. So sánh thời gian hiện tại với khoảng hiệu lực
            if (currentTime < timeStart) {
                return "Mã khuyến mãi này chưa đến thời gian bắt đầu áp dụng!";
            }

            if (currentTime > timeEnd) {
                return "Mã khuyến mãi này đã hết hạn sử dụng!";
            }

        } catch (Exception e) {
            return "Lỗi định dạng ngày tháng (Yêu cầu: yyyy-MM-dd)!";
        }

        // Trả về chuỗi rỗng nếu tất cả đều hợp lệ
        return "";
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