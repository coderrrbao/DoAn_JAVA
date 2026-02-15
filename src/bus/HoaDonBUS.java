package bus;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Connection;

import dao.ChiTietHoaDonDAO;
import dao.CongThucDAO;
import dao.HoaDonDAO;
import dao.SanPhamDAO;
import dao.LoNguyenLieuDAO;
import dao.LoSanPhamDAO;
import dao.conection.DBConnection;
import dto.ChiTietCongThuc;
import dto.ChiTietHoaDon;
import dto.HoaDon;


import javax.swing.*;

public class HoaDonBUS {
    private HoaDonDAO hoaDonDAO = new HoaDonDAO();
    private ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO();
    private SanPhamDAO sanPhamDAO = new SanPhamDAO();
    private LoSanPhamDAO loSanPhamDAO = new LoSanPhamDAO();
    private CongThucDAO congThucDAO = new CongThucDAO();
    private LoNguyenLieuDAO loNguyenLieuDAO = new LoNguyenLieuDAO();

    public String kiemTraTonKho(HoaDon hd) {
        for (ChiTietHoaDon ct : hd.getListChiTietHoaDon()) {
            String loaiNuoc = ct.getSanPham().getLoaiNuoc();
            String maSP = ct.getSanPham().getMaSP();
            String maSize = (ct.getSize() != null) ? ct.getSize().getMaSize() : null;
            int soLuongMua = ct.getSoLuong();

            if (loaiNuoc.equalsIgnoreCase("Có sẵn")) {
                if (!loSanPhamDAO.kiemTraDuHang(maSP, soLuongMua)) {
                    return "Sản phẩm " + ct.getSanPham().getTenSP() + " không đủ hàng!";
                }
            }
            else if (loaiNuoc.equalsIgnoreCase("Pha chế")) {
                // Logic mới: Kiểm tra nguyên liệu
                ArrayList<ChiTietCongThuc> lstNguyenLieu = congThucDAO.layCongThucPhaChe(maSP, maSize);

                if (lstNguyenLieu.isEmpty()) {
                    System.out.println("Cảnh báo: Món " + ct.getSanPham().getTenSP() + " chưa có công thức!");
                    continue;
                }

                for (ChiTietCongThuc ctct : lstNguyenLieu) {
                    double canDung = ctct.getSoLuong() * soLuongMua;
                    if (!loNguyenLieuDAO.kiemTraDuNguyenLieu(ctct.getNguyenLieu().getMaNL(), canDung)) {
                        return "Nguyên liệu " + ctct.getNguyenLieu().getTenNL() + " không đủ để pha chế!";
                    }
                }
            }
        }
        return null;
    }

    public boolean ThanhToan(HoaDon hd) {
        Connection conn = null; // Khởi tạo kết nối để quản lý Transaction

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // 1. BẮT ĐẦU TRANSACTION (Quan trọng!)

            // --- BƯỚC 1: LƯU HÓA ĐƠN ---
            // Lưu ý: Tốt nhất bạn nên sửa hoaDonDAO.themHoaDon để nhận tham số 'conn' luôn.
            // Nếu chưa sửa, nó sẽ chạy connection riêng (tạm chấp nhận được).
            if (!hoaDonDAO.themHoaDon(hd)) {
                return false;
            }

            // --- BƯỚC 2: DUYỆT CHI TIẾT & TRỪ KHO ---
            for (ChiTietHoaDon ct : hd.getListChiTietHoaDon()) {
                // Lưu chi tiết hóa đơn
                if (!chiTietHoaDonDAO.themChiTietHoaDon(ct)) {
                    return false;
                }

                String loaiNuoc = ct.getSanPham().getLoaiNuoc();
                String maSP = ct.getSanPham().getMaSP();
                // Lấy mã size (nếu null thì coi như không có size)
                String maSize = (ct.getSize() != null) ? ct.getSize().getMaSize() : null;
                int soLuongMua = ct.getSoLuong();

                // --- XỬ LÝ TRỪ KHO ---
                if (loaiNuoc.equalsIgnoreCase("Có sẵn")) {
                    // Xử lý cho sản phẩm đóng chai (nếu có logic trừ bảng LoSanPham)
                    if (!loSanPhamDAO.truSoLuong(conn, maSP, soLuongMua)) { // Nhớ truyền conn
                        conn.rollback();
                        return false;
                    }
                }
                else if (loaiNuoc.equalsIgnoreCase("Pha chế")) {
                    // Lấy danh sách nguyên liệu cần trừ từ công thức
                    ArrayList<ChiTietCongThuc> lstNguyenLieuCan = congThucDAO.layCongThucPhaChe(maSP, maSize);

                    for (ChiTietCongThuc ctct : lstNguyenLieuCan) {
                        double canTru = ctct.getSoLuong() * soLuongMua;
                        String maNL = ctct.getNguyenLieu().getMaNL();

                        // GỌI HÀM TRỪ KHO (Truyền 'conn' vào để chạy chung Transaction)
                        // Đây là chỗ fix lỗi báo đỏ của bạn
                        boolean ketQuaTru = loNguyenLieuDAO.truNguyenLieu(conn, maNL, canTru);

                        if (!ketQuaTru) {
                            conn.rollback(); // Lỗi thiếu hàng -> Rollback toàn bộ hóa đơn
                            System.out.println("Thanh toán thất bại do thiếu nguyên liệu: " + maNL);
                            return false;
                        }
                    }
                }
            }

            conn.commit(); // 3. TẤT CẢ THÀNH CÔNG -> LƯU VÀO DATABASE
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback(); // Gặp lỗi bất kỳ (SQL, Code...) -> Quay lui hết
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (conn != null) conn.close(); // Đóng kết nối cuối cùng
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String taoMaHoaDonMoi() {
        String maCuoi = hoaDonDAO.layMaHoaDonCuoiCung();

        if (maCuoi == null) {
            return "HD001";
        }

        try {
            String phanSo = maCuoi.replaceAll("[^0-9]", "");
            int soThuTu = Integer.parseInt(phanSo);

            soThuTu++;

            return String.format("HD%03d", soThuTu);
        } catch (NumberFormatException e) {
            System.out.println("Lỗi parse mã cũ: " + maCuoi);
            return "HD" + System.currentTimeMillis();
        }
    }
}
