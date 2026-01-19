package dto;

import java.sql.Date;

public class HoaDon {

    private String maHD;
    private String maNV;
    private String maKH;
    private String maKM;
    private Date ngayBan;
    private double tongTien;
    private double tienKhuyenMai;
    private boolean trangThai;

    public HoaDon() {
    }

    public HoaDon(String maHD, String maNV, String maKH, String maKM,
            Date ngayBan, double tongTien, double tienKhuyenMai, boolean trangThai) {
        this.maHD = maHD;
        this.maNV = maNV;
        this.maKH = maKH;
        this.maKM = maKM;
        this.ngayBan = ngayBan;
        this.tongTien = tongTien;
        this.tienKhuyenMai = tienKhuyenMai;
        this.trangThai = trangThai;
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

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getMaKM() {
        return maKM;
    }

    public void setMaKM(String maKM) {
        this.maKM = maKM;
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
}
