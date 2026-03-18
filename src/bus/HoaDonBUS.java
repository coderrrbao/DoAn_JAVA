package bus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import dao.ChiTietHoaDonDAO;
import dao.HoaDonDAO;
import dao.conection.DBConnection;
import dto.ChiTietCongThuc;
import dto.ChiTietHoaDon;
import dto.CongThuc;
import dto.HoaDon;
import dto.LoNguyenLieu;
import dto.LoSanPham;
import dto.NguyenLieu;
import dto.SanPham;
import dto.Size;
import java.text.DecimalFormat;

public class HoaDonBUS {
    private HoaDonDAO hoaDonDAO = new HoaDonDAO();
    private ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO();

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

                if (congThuc == null || congThuc.getListChiTietCongThuc().isEmpty()) {
                    System.out.println("Cảnh báo: Món " + ct.getSanPham().getTenSP() + " chưa có công thức!");
                    continue;
                }

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
            }
        }
        LoNguyenLieuBUS loNguyenLieuBUS = LoNguyenLieuBUS.getLoNguyenLieuBUS();
        ArrayList<NguyenLieu> listNLRemove = new ArrayList<>();
        for (Map.Entry<NguyenLieu, Double> entry : listNLCan.entrySet()) {
            NguyenLieu nguyenLieu = entry.getKey();
            double soLuongTrongKho = loNguyenLieuBUS.laySoLuongNguyenLieuTrongKho(nguyenLieu.getMaNL());
            double soLuongCan = listNLCan.get(nguyenLieu);
            if (soLuongTrongKho < soLuongCan) {
                entry.setValue(soLuongCan - soLuongTrongKho);
            } else {
                listNLRemove.add(nguyenLieu);
            }

        }

        for (NguyenLieu nguyenLieu : listNLRemove) {
            listNLCan.remove(nguyenLieu);
        }

        for (Map.Entry<NguyenLieu, Double> entry : listNLCan.entrySet()) {
            loi.add(entry.getKey().getTenNL() + " Còn thiếu " + entry.getValue());
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

    public ArrayList<String> ThanhToan(HoaDon hd) {
        Connection conn = null;
        ArrayList<String> listThongBao = new ArrayList<>();
        HashMap<LoSanPham, Double> mapHangHoaSp = new HashMap<>();
        HashMap<LoNguyenLieu, Double> mapHangHoaNL = new HashMap<>();
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            Date now = new Date();
            hd.setNgayBan(now);

            if (!hoaDonDAO.themHoaDon(hd)) {
                throw new SQLException();
            }

            for (ChiTietHoaDon ct : hd.getListChiTietHoaDon()) {
                if (!chiTietHoaDonDAO.themChiTietHoaDon(ct)) {
                    throw new SQLException();
                }

                String loaiNuoc = ct.getSanPham().getLoaiNuoc();
                String maSP = ct.getSanPham().getMaSP();
                Size size = ct.getSize();
                int soLuongMua = ct.getSoLuong();

                if (loaiNuoc.equalsIgnoreCase("Có sẵn")) {

                    HashMap<LoSanPham, Double> ketQua = LoSanPhamBUS.getLoSanPhamBUS()
                            .capNhapTonKhoSauKhiBan(conn, ct.getSanPham(), soLuongMua);

                    for (Map.Entry<LoSanPham, Double> entry : ketQua.entrySet()) {
                        LoSanPham lo = entry.getKey();
                        Double slLayTuLo = entry.getValue();

                        mapHangHoaSp.put(lo, mapHangHoaSp.getOrDefault(lo, 0.0) + slLayTuLo);
                    }

                } else if (loaiNuoc.equalsIgnoreCase("Pha chế")) {
                    CongThuc congThuc = ct.getSanPham().getCongThuc();
                    if (ct.getSanPham().getCongThuc() == null) {
                        System.out.println("LỖI NGHIÊM TRỌNG: Món " + maSP + " chưa được cấu hình công thức!");
                        conn.rollback();
                        throw new SQLException();
                    }
                    for (ChiTietCongThuc ctct : congThuc.getListChiTietCongThuc()) {
                        System.out.println(ctct.getNguyenLieu().getTenNL());
                        double canTru = ctct.getSoLuong() * soLuongMua;
                        if (size != null) {
                            canTru = canTru + canTru * ((double) size.getPhanTramNL() / 100);
                        }

                        HashMap<LoNguyenLieu, Double> ketQua = LoNguyenLieuBUS.getLoNguyenLieuBUS()
                                .capNhapTonKhoSauKhiBan(conn, ctct.getNguyenLieu(), canTru);

                        for (Map.Entry<LoNguyenLieu, Double> entry : ketQua.entrySet()) {
                            LoNguyenLieu lo = entry.getKey();
                            Double slLayTuLo = entry.getValue();

                            mapHangHoaNL.put(lo, mapHangHoaNL.getOrDefault(lo, 0.0) + slLayTuLo);
                        }

                    }
                }
            }

            DecimalFormat df = new DecimalFormat("#.###");

            for (Map.Entry<LoNguyenLieu, Double> entry : mapHangHoaNL.entrySet()) {
                LoNguyenLieu loNL = entry.getKey();
                Double tongSL = entry.getValue();

                NguyenLieu nguyenLieu = NguyenLieuBUS.getNguyenLieuBUS().timNguyenLieu(loNL.getMaNL());
                String tenNL = (nguyenLieu != null) ? nguyenLieu.getTenNL() : "Chưa xác định";

                String slDinhDang = df.format(tongSL);

                listThongBao.add("Vui lòng lấy " + slDinhDang + " nguyên liệu " + tenNL +
                        " ở lô có mã: " + loNL.getMaLoNL() + " để sử dụng.");
            }

            for (Map.Entry<LoSanPham, Double> entry : mapHangHoaSp.entrySet()) {
                LoSanPham loSP = entry.getKey();
                Double tongSL = entry.getValue();

                SanPham sanPham = SanPhamBUS.getSanPhamBUS().timSanPham(loSP.getMaSP());
                String tenSP = (sanPham != null) ? sanPham.getTenSP() : "Chưa xác định";

                String slDinhDang = df.format(tongSL);

                listThongBao.add("Vui lòng lấy " + slDinhDang + " sản phẩm " + tenSP +
                        " ở lô có mã: " + loSP.getMaLoSP() + " để sử dụng.");
            }

            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null)
                    conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            LoSanPhamBUS.getLoSanPhamBUS().setCanUpdate(true);
            LoNguyenLieuBUS.getLoNguyenLieuBUS().setCanUpdate(true);
        }
        return listThongBao;
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
