package bus;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;

import dao.ChiTietHoaDonDAO;
import dao.HoaDonDAO;
import dao.LoNguyenLieuDAO;
import dao.LoSanPhamDAO;
import dao.conection.DBConnection;
import dto.ChiTietCongThuc;
import dto.ChiTietHoaDon;
import dto.CongThuc;
import dto.HoaDon;
import dto.NguyenLieu;
import dto.Size;

public class HoaDonBUS {
    private HoaDonDAO hoaDonDAO = new HoaDonDAO();
    private ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO();
    private LoSanPhamDAO loSanPhamDAO = new LoSanPhamDAO();
    private LoNguyenLieuDAO loNguyenLieuDAO = new LoNguyenLieuDAO();

    public ArrayList<String> kiemTraTonKho(HoaDon hd) {
        HashMap<NguyenLieu, Double> listNLCan = new HashMap<>();
        ArrayList<String> loi = new ArrayList<>();
        for (ChiTietHoaDon ct : hd.getListChiTietHoaDon()) {
            String loaiNuoc = ct.getSanPham().getLoaiNuoc();
            String maSP = ct.getSanPham().getMaSP();
            int soLuongMua = ct.getSoLuong();
            LoSanPhamBUS loSanPhamBUS = LoSanPhamBUS.getLoSanPhamBUS();
            if (loaiNuoc.equalsIgnoreCase("Có sẵn")) {
                if (loSanPhamBUS.laySoLuongSanPhamTrongKho(maSP) < soLuongMua) {
                    loi.add("Sản phẩm " + ct.getSanPham().getTenSP() + " không đủ hàng!");
                }
            } else if (loaiNuoc.equalsIgnoreCase("Pha chế")) {
                CongThucBUS congThucBUS = CongThucBUS.getCongThucBUS();
                CongThuc congThuc = congThucBUS.timCongThucChoSP(maSP);

                if (congThuc.getListChiTietCongThuc().isEmpty()) {
                    System.out.println("Cảnh báo: Món " + ct.getSanPham().getTenSP() + " chưa có công thức!");
                    continue;
                }
                LoNguyenLieuBUS loNguyenLieuBUS = LoNguyenLieuBUS.getLoNguyenLieuBUS();
                for (ChiTietCongThuc ctct : congThuc.getListChiTietCongThuc()) {
                    double canDung = ctct.getSoLuong() * soLuongMua;
                    if (listNLCan.containsKey(ctct.getNguyenLieu())) {
                        double soLuong = listNLCan.get(ctct.getNguyenLieu());
                        soLuong += canDung;
                        listNLCan.put(ctct.getNguyenLieu(), soLuong);
                    } else {
                        listNLCan.put(ctct.getNguyenLieu(), canDung);
                    }
                }
                for (NguyenLieu nguyenLieu : listNLCan.keySet()) {
                    if (loNguyenLieuBUS.laySoLuongNguyenLieuTrongKho(nguyenLieu.getMaNL()) < listNLCan
                            .get(nguyenLieu)) {
                        loi.add("Không đủ " + nguyenLieu.getTenNL());
                    }
                }

            }
        }
        return loi;
    }

    public double layTongDanhThu() {
        double tong = 0;
        ArrayList<HoaDon> listHoaDon = hoaDonDAO.layDanhSachHoaDon();
        for (HoaDon hoaDon : listHoaDon) {
            tong += hoaDon.getTongTien();
        }
        return tong;
    }

    public boolean ThanhToan(HoaDon hd) {
        Connection conn = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            Date now = new Date();
            hd.setNgayBan(now);

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
                } else if (loaiNuoc.equalsIgnoreCase("Pha chế")) {
                    CongThuc congThuc = CongThucBUS.getCongThucBUS().timCongThucChoSP(maSP);
                    if (congThuc.getListChiTietCongThuc().isEmpty()) {
                        System.out.println("LỖI NGHIÊM TRỌNG: Món " + maSP + " chưa được cấu hình công thức!");
                        conn.rollback();
                        return false;
                    }
                    for (ChiTietCongThuc ctct : congThuc.getListChiTietCongThuc()) {
                        double canTru = ctct.getSoLuong() * soLuongMua;
                        if (size != null) {
                            canTru = canTru + canTru * ((double) size.getPhanTramNL() / 100);
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
                if (conn != null)
                    conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String taoMaHoaDonMoi() {
        String maCuoi = hoaDonDAO.layMaHoaDonCuoiCung();

        if (maCuoi == null) {
            return "HD_TK_01";
        }

        try {
            String phanSo = maCuoi.replaceAll("[^0-9]", "");
            int soThuTu = Integer.parseInt(phanSo);

            soThuTu++;

            return String.format("HD_TK_%02d", soThuTu);
        } catch (NumberFormatException e) {
            System.out.println("Lỗi parse mã cũ: " + maCuoi);
            return "HD_TK_" + System.currentTimeMillis();
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

    public boolean xuatExcel(ArrayList<HoaDon> dsHoaDonCanXuat) {
        if (dsHoaDonCanXuat == null || dsHoaDonCanXuat.isEmpty()) {
            return false;
        }
        return util.XuLyExcel.xuatFileHoaDon(dsHoaDonCanXuat);
    }
}
