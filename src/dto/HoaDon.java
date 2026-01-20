package dto;

import java.sql.Date;
import java.util.ArrayList;

public class HoaDon {

    private String maHD;
    // private String maNV;
    private NhanVien nhanVien;
    private String maKH;
    // private String maKM;
    private MaGiamGia maGiamGia = null;
    private Date ngayBan;
    private double tongTien;
    private double tienKhuyenMai;
    private boolean trangThai;
    ArrayList<ChiTietHoaDon> listChiTietHoaDon;

    public HoaDon() {
        this.listChiTietHoaDon = new ArrayList<>();
    }

    public HoaDon(String maHD, NhanVien nhanVien, String maKH, MaGiamGia maGiamGia,
            Date ngayBan, double tongTien, double tienKhuyenMai, boolean trangThai) {
        this.maHD = maHD;
        this.nhanVien = nhanVien;
        this.maKH = maKH;
        this.maGiamGia = maGiamGia;
        this.ngayBan = ngayBan;
        this.tongTien = tongTien;
        this.tienKhuyenMai = tienKhuyenMai;
        this.trangThai = trangThai;
        this.listChiTietHoaDon = new ArrayList<>();
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public boolean getTrangThai() {
        return trangThai;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public MaGiamGia getMaGiamGia() {
        return maGiamGia;
    }

    public void setMaGiamGia(MaGiamGia maGiamGia) {
        this.maGiamGia = maGiamGia;
    }

    public Date getNgayBan() {
        return ngayBan;
    }

    public void setNgayBan(Date ngayBan) {
        this.ngayBan = ngayBan;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public double getTienKhuyenMai() {
        return tienKhuyenMai;
    }

    public void setTienKhuyenMai(double tienKhuyenMai) {
        this.tienKhuyenMai = tienKhuyenMai;
    }

    public ArrayList<ChiTietHoaDon> getListChiTietHoaDon() {
        return listChiTietHoaDon;
    }

    public void setListChiTietHoaDon(ArrayList<ChiTietHoaDon> listChiTietHoaDon) {
        this.listChiTietHoaDon = listChiTietHoaDon;
    }

    public void themChiTietHoaDon(ChiTietHoaDon chiTietHoaDon) {
        this.listChiTietHoaDon.add(chiTietHoaDon);
    }

    public void xoaChiTietHoaDon(ChiTietHoaDon chiTietHoaDon) {
        this.listChiTietHoaDon.remove(chiTietHoaDon);
    }

    public void xoaChiTietHoaDonTheoIndex(int index) {
        if (index >= 0 && index < this.listChiTietHoaDon.size()) {
            this.listChiTietHoaDon.remove(index);
        }
    }
}
