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
import dto.CongThuc;
import dto.HoaDon;
import dto.Size;

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
                CongThucBUS congThucBUS = CongThucBUS.getCongThucBUS();
                CongThuc congThuc = congThucBUS.timCongThucChoSP(maSP);

                if (congThuc.getListChiTietCongThuc().isEmpty()) {
                    System.out.println("Cảnh báo: Món " + ct.getSanPham().getTenSP() + " chưa có công thức!");
                    continue;
                }

                for (ChiTietCongThuc ctct : congThuc.getListChiTietCongThuc()) {
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
        Connection conn = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            if (!hoaDonDAO.themHoaDon(hd)) {
                return false;
            }

            for (ChiTietHoaDon ct : hd.getListChiTietHoaDon()) {
                if (!chiTietHoaDonDAO.themChiTietHoaDon(ct)) {
                    return false;
                }

                String loaiNuoc = ct.getSanPham().getLoaiNuoc();
                String maSP = ct.getSanPham().getMaSP();
                Size size = ct.getSize();
                int soLuongMua = ct.getSoLuong();

                if (loaiNuoc.equalsIgnoreCase("Có sẵn")) {
                    if (!loSanPhamDAO.truSoLuong(conn, maSP, soLuongMua)) {
                        conn.rollback();
                        return false;
                    }
                }
                else if (loaiNuoc.equalsIgnoreCase("Pha chế")) {
                    CongThuc congThuc = CongThucBUS.getCongThucBUS().timCongThucChoSP(maSP);
                    if (congThuc.getListChiTietCongThuc().isEmpty()) {
                        System.out.println("LỖI NGHIÊM TRỌNG: Món " + maSP + " chưa được cấu hình công thức!");
                        conn.rollback();
                        return false;
                    }
                    for (ChiTietCongThuc ctct : congThuc.getListChiTietCongThuc()) {
                        double canTru = ctct.getSoLuong() * soLuongMua;
                        if (size!=null){
                            canTru=canTru+canTru*((double)size.getPhanTramNL()/100);
                        }
                        String maNL = ctct.getNguyenLieu().getMaNL();
                        boolean ketQuaTru = loNguyenLieuDAO.truNguyenLieu(conn, maNL, canTru);

                        if (!ketQuaTru) {
                            conn.rollback();
                            System.out.println("Thanh toán thất bại do thiếu nguyên liệu: " + maNL);
                            return false;
                        }
                    }
                }
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (conn != null) conn.close();
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

    public ArrayList<HoaDon> layDanhSachHoaDon() {
        return hoaDonDAO.layDanhSachHoaDon();
    }

    public HoaDon timHoaDonTheoMa(String maHD) {
        return hoaDonDAO.timHoaDonTheoMa(maHD);
    }

    public boolean xoaHoaDon(String maHD) {
        return hoaDonDAO.xoaHoaDon(maHD);
    }
}
