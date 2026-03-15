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
import dto.NguyenLieu;
import dto.Size;

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
                    ArrayList<String> ketQuaCapNhapKho = LoSanPhamBUS.getLoSanPhamBUS().capNhapTonKhoSauKhiBan(conn,
                            ct.getSanPham(), soLuongMua);
                    listThongBao.addAll(ketQuaCapNhapKho);

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
                        ArrayList<String> ketQuaCapNhapKho = LoNguyenLieuBUS.getLoNguyenLieuBUS()
                                .capNhapTonKhoSauKhiBan(conn, ctct.getNguyenLieu(), canTru);
                        listThongBao.addAll(ketQuaCapNhapKho);
                    }
                }
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
