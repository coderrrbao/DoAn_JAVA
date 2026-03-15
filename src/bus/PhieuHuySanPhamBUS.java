package bus;

import dao.ChiTietPhieuHuySanPhamDAO;
import dao.LoSanPhamDAO;
import dao.PhieuHuySanPhamDAO;
import dao.conection.DBConnection;
import dto.ChiTietPhieuHuySanPham;
import dto.PhieuHuySanPham;
import util.XuLyExcel;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PhieuHuySanPhamBUS {
    private static PhieuHuySanPhamBUS instance;
    private PhieuHuySanPhamDAO dao = new PhieuHuySanPhamDAO();
    private ArrayList<PhieuHuySanPham> listPhieuHuy = null;
    private boolean canUpdate = true;
    private ChiTietPhieuHuySanPhamDAO chiTietPhieuHuySanPhamDAO = new ChiTietPhieuHuySanPhamDAO();
    public static PhieuHuySanPhamBUS getPhieuHuySanPhamBUS() {
        if (instance == null) instance = new PhieuHuySanPhamBUS();
        return instance;
    }

    public void khoiTao() {
        listPhieuHuy = dao.layListPhieuHuy();
        LoSanPhamBUS loSanPhamBUS = LoSanPhamBUS.getLoSanPhamBUS();
        for (PhieuHuySanPham ph : listPhieuHuy) {
            ph.setListChiTiet(chiTietPhieuHuySanPhamDAO.layChiTietHuyTheoMaPH(ph.getMaPH()));
            if (ph.getListChiTiet()!=null){
                 for (ChiTietPhieuHuySanPham chiTietPhieuHuySanPham : ph.getListChiTiet()){
                chiTietPhieuHuySanPham.setLoSanPham(loSanPhamBUS.timLoSanPham(chiTietPhieuHuySanPham.getLoSanPham().getMaLoSP()));
            }
            }
           
        }
        
        
        canUpdate = false;
    }

    public ArrayList<PhieuHuySanPham> layListPhieuHuy() {
        if (canUpdate || listPhieuHuy == null) khoiTao();
        return listPhieuHuy;
    }

    public boolean thucHienHuy(PhieuHuySanPham phieuHuy, Object[][] data) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            String maPH = dao.layMaPhieuHuySPKhaDung(conn);
            phieuHuy.setMaPH(maPH);

            if (!dao.themPhieuHuy(phieuHuy, conn)) throw new SQLException();

            int index = 1;
            for (Object[] row : data) {
                String maLo = row[3].toString();
                double soLuong = Double.parseDouble(row[2].toString());
                double gia = Double.parseDouble(row[4].toString());
                String maCTPHSP = maPH + "_CT" + index;

                if (!dao.themChiTietHuy(maCTPHSP, maPH, maLo, soLuong, gia, conn)) 
                    throw new SQLException();
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

    public boolean capNhatPhieuHuy(PhieuHuySanPham ph) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            
            // Tự động gán NV Xác Nhận
            if ("Đã xác nhận".equalsIgnoreCase(ph.getTrangThaiXuLy().trim()) && 
                (ph.getMaNVXacNhan() == null || ph.getMaNVXacNhan().isEmpty())) {
                if(ui.login.PhienDangNhap.getUser() != null) {
                    ph.setMaNVXacNhan(ui.login.PhienDangNhap.getUser().getMaNV());
                }
            }
            
            if (!dao.capNhatPhieuHuy(ph, conn)) throw new SQLException("Cập nhật thất bại");
            
            if ("Đã xác nhận".equalsIgnoreCase(ph.getTrangThaiXuLy().trim())) {
                ArrayList<ChiTietPhieuHuySanPham> chiTiet = ph.getListChiTiet();
                if (chiTiet == null || chiTiet.isEmpty()) throw new SQLException("Không tìm thấy chi tiết");
                
                for (ChiTietPhieuHuySanPham ct : chiTiet) {
                    String maLo = ct.getLoSanPham().getMaLoSP();
                    double soLuongHuy = ct.getSoLuong();
                    double soLuongTonHienTai = ct.getLoSanPham().getSoLuong(); // Lấy số lượng trước khi hủy

                    // 1. Trừ kho sản phẩm
                    if (!dao.truKhoLoSanPham(maLo, soLuongHuy, conn))
                        throw new SQLException("Trừ kho thất bại");
                        
                    // 2. Kiểm tra nếu số lượng trừ đi <= 0 thì xóa lô
                    if (soLuongTonHienTai - soLuongHuy <= 0) {
                        LoSanPhamDAO loSanPhamDAO = new LoSanPhamDAO();
                        if (!loSanPhamDAO.xoaLoSanPham(conn, maLo)) {
                            throw new SQLException("Lỗi khi xóa lô sản phẩm có số lượng = 0");
                        }
                    }
                }
            }
            conn.commit();
            this.canUpdate = true;
            khoiTao();
            bus.LoSanPhamBUS.getLoSanPhamBUS().setCanUpdate(true);
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

    public boolean themPhieuHuy(PhieuHuySanPham p, Connection conn) throws SQLException {
        p.setMaPH(dao.layMaPhieuHuySPKhaDung(conn));

        double tongTien = 0;
        if (p.getListChiTiet() != null) {
            for (ChiTietPhieuHuySanPham ct : p.getListChiTiet()) {
                tongTien += ct.getThanhTien();
            }
        }
        p.setTongGiaTri(tongTien);

        if (!dao.themPhieuHuy(p, conn)) return false;

        int index = 1;
        if (p.getListChiTiet() != null) {
            for (ChiTietPhieuHuySanPham ct : p.getListChiTiet()) {
                String maCT = p.getMaPH() + "_CT" + index;
                if (!dao.themChiTietHuy(maCT, p.getMaPH(), ct.getLoSanPham().getMaLoSP(), ct.getSoLuong(), ct.getDonGia(), conn)) {
                    return false;
                }
                index++;
            }
        }
        return true;
    }

    public boolean nhapExcel(File file) {
        ArrayList<PhieuHuySanPham> dsNhap = XuLyExcel.nhapFilePhieuHuySanPham(file);
        if (dsNhap == null || dsNhap.isEmpty()) return false;

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            for (PhieuHuySanPham phieu : dsNhap) {
                if (!themPhieuHuy(phieu, conn)) {
                    throw new SQLException("Lỗi thêm phiếu hủy từ Excel");
                }
            }

            conn.commit();
            canUpdate = true;
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
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) {}
            }
        }
    }

    public boolean xuatExcel(File file) {
        ArrayList<PhieuHuySanPham> dsPhieu = layListPhieuHuy();
        if (dsPhieu == null) return false;
        return XuLyExcel.xuatFilePhieuHuySanPham(file, dsPhieu);
    }

    public boolean xoaMemPhieuHuy(String maPH) {
        Connection conn = DBConnection.getConnection();
        try {
            if (!dao.xoaMemPhieuHuy(maPH, conn)) throw new SQLException("Xóa thất bại");
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