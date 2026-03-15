package bus;

import dao.ChiTietPhieuHuyNguyenLieuDAO;
import dao.LoNguyenLieuDAO;
import dao.PhieuHuyNguyenLieuDAO;
import dao.conection.DBConnection;
import dto.ChiTietPhieuHuyNguyenLieu;
import dto.PhieuHuyNguyenLieu;
import util.XuLyExcel;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PhieuHuyNguyenLieuBUS {
    private static PhieuHuyNguyenLieuBUS instance;
    private PhieuHuyNguyenLieuDAO dao = new PhieuHuyNguyenLieuDAO();
    private ArrayList<PhieuHuyNguyenLieu> listPhieuHuy = null;
    private boolean canUpdate = true;
    private ChiTietPhieuHuyNguyenLieuDAO chiTietPhieuHuyNguyenLieuDAO = new ChiTietPhieuHuyNguyenLieuDAO();

    public static PhieuHuyNguyenLieuBUS getPhieuHuyNguyenLieuBUS() {
        if (instance == null)
            instance = new PhieuHuyNguyenLieuBUS();
        return instance;
    }

    public void khoiTao() {
    // 1. Lấy danh sách phiếu hủy từ DAO
    listPhieuHuy = dao.layListPhieuHuy();
    LoNguyenLieuBUS loNguyenLieuBUS = LoNguyenLieuBUS.getLoNguyenLieuBUS();

    for (PhieuHuyNguyenLieu ph : listPhieuHuy) {
        ph.setListChiTiet(chiTietPhieuHuyNguyenLieuDAO.layChiTietHuyTheoMaPH(ph.getMaPH()));
        
        if (ph.getListChiTiet() != null) {
            for (ChiTietPhieuHuyNguyenLieu ct : ph.getListChiTiet()) {
                String maLo = ct.getLoNguyenLieu().getMaLoNL();
                ct.setLoNguyenLieu(loNguyenLieuBUS.timLoNguyenLieu(maLo));
            }
        }
    }
    canUpdate = false;
}

    public ArrayList<PhieuHuyNguyenLieu> layListPhieuHuy() {
        if (canUpdate || listPhieuHuy == null)
            khoiTao();
        return listPhieuHuy;
    }

    public boolean thucHienHuy(PhieuHuyNguyenLieu phieuHuy, Object[][] data) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            String maPH = dao.layMaPhieuHuyNLKhaDung(conn);
            phieuHuy.setMaPH(maPH);

            if (!dao.themPhieuHuy(phieuHuy, conn))
                throw new SQLException("Lỗi thêm phiếu hủy");

            int index = 1;
            for (Object[] row : data) {
                String maLo = row[3].toString();
                double soLuong = Double.parseDouble(row[2].toString());
                double gia = Double.parseDouble(row[4].toString());
                String maCTPHNL = maPH + "_CT" + index;

                if (!dao.themChiTietHuy(maCTPHNL, maPH, maLo, soLuong, gia, conn))
                    throw new SQLException("Lỗi thêm chi tiết");
                index++;
            }
            conn.commit();
            this.canUpdate = true;
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {}
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception e) {}
        }
    }

    public boolean capNhatPhieuHuy(PhieuHuyNguyenLieu ph) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            
            // Tự động lấy NV đang đăng nhập làm NV Xác Nhận
            if ("Đã xác nhận".equalsIgnoreCase(ph.getTrangThaiXuLy().trim()) && 
                (ph.getMaNVXacNhan() == null || ph.getMaNVXacNhan().isEmpty())) {
                if(ui.login.PhienDangNhap.getUser() != null) {
                    ph.setMaNVXacNhan(ui.login.PhienDangNhap.getUser().getMaNV());
                }
            }

            if (!dao.capNhatPhieuHuy(ph, conn))
                throw new SQLException("Cập nhật thất bại");

            if ("Đã xác nhận".equalsIgnoreCase(ph.getTrangThaiXuLy().trim())) {
                ArrayList<ChiTietPhieuHuyNguyenLieu> chiTiet = ph.getListChiTiet();
                if (chiTiet == null || chiTiet.isEmpty())
                    throw new SQLException("Không tìm thấy chi tiết phiếu hủy");
                
                for (ChiTietPhieuHuyNguyenLieu ct : chiTiet) {
                    String maLo = ct.getLoNguyenLieu().getMaLoNL();
                    double soLuongHuy = ct.getSoLuong();
                    double soLuongTonHienTai = ct.getLoNguyenLieu().getSoLuong(); // Lấy số lượng trước khi hủy

                    // 1. Trừ kho dựa trên mã lô
                    if (!dao.truKhoLoNguyenLieu(maLo, soLuongHuy, conn))
                        throw new SQLException("Trừ kho thất bại");
                        
                    // 2. Kiểm tra nếu số lượng trừ đi <= 0 thì xóa lô luôn
                    if (soLuongTonHienTai - soLuongHuy <= 0) {
                        // Đảm bảo bạn đã viết hàm xoaLoNguyenLieu trong DAO
                        LoNguyenLieuDAO loNguyenLieuDAO = new LoNguyenLieuDAO();
                        if (!loNguyenLieuDAO.xoaLoNguyenLieu(conn, maLo)) {
                            throw new SQLException("Lỗi khi xóa lô nguyên liệu có số lượng = 0");
                        }
                    }
                }
            }
            conn.commit();
            this.canUpdate = true;
            khoiTao();
            bus.LoNguyenLieuBUS.getLoNguyenLieuBUS().setCanUpdate(true);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {}
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception e) {}
        }
    }

    public boolean themPhieuHuy(PhieuHuyNguyenLieu p, Connection conn) throws SQLException {
        p.setMaPH(dao.layMaPhieuHuyNLKhaDung(conn));

        double tong = 0;
        if(p.getListChiTiet() != null) {
            for (ChiTietPhieuHuyNguyenLieu ct : p.getListChiTiet()) {
                tong += ct.getThanhTien();
            }
        }
        p.setTongTien(tong);

        if (!dao.themPhieuHuy(p, conn)) return false;

        int index = 1;
        if (p.getListChiTiet() != null) {
            for (ChiTietPhieuHuyNguyenLieu ct : p.getListChiTiet()) {
                String maCT = p.getMaPH() + "_CT" + index;
                if (!dao.themChiTietHuy(maCT, p.getMaPH(), ct.getLoNguyenLieu().getMaLoNL(), ct.getSoLuong(), ct.getDonGia(), conn)) {
                    return false;
                }
                index++;
            }
        }
        return true;
    }

    public boolean nhapExcel(File file) {
        ArrayList<PhieuHuyNguyenLieu> dsNhap = XuLyExcel.nhapFilePhieuHuyNguyenLieu(file);
        if (dsNhap == null || dsNhap.isEmpty()) return false;

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            for (PhieuHuyNguyenLieu p : dsNhap) {
                if (!themPhieuHuy(p, conn)) {
                    throw new SQLException("Lỗi thêm phiếu hủy từ Excel");
                }
            }

            conn.commit();
            this.canUpdate = true;
            khoiTao();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) {}
            }
            return false;
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (Exception e) {}
            }
        }
    }

    public boolean xuatExcel(File file) {
        ArrayList<PhieuHuyNguyenLieu> dsPhieu = layListPhieuHuy();
        if (dsPhieu == null) return false;
        return XuLyExcel.xuatFilePhieuHuyNguyenLieu(file, dsPhieu);
    }

    public boolean xoaMemPhieuHuy(String maPH) {
        Connection conn = DBConnection.getConnection();
        try {
            if (!dao.xoaMemPhieuHuy(maPH, conn))
                throw new SQLException("Xóa thất bại");
            this.canUpdate = true;
            khoiTao();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {}
        }
    }
}