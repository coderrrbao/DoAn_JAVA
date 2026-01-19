package dto;

import java.sql.Date;

public class PhieuNhapSanPham {
    private String maLoSP;
    private Date ngayNhap;
    private String maNV;
    private double tongTien;
    private String maNCC;
    private boolean trangThai;

    public PhieuNhapSanPham() {
    }

    public PhieuNhapSanPham(String maLoSP, Date ngayNhap, String maNV, double tongTien, String maNCC,
            boolean trangThai) {
        this.maLoSP = maLoSP;
        this.ngayNhap = ngayNhap;
        this.maNV = maNV;
        this.tongTien = tongTien;
        this.maNCC = maNCC;
        this.trangThai = trangThai;
    }

    public String getMaLoSP() {
        return maLoSP;
    }

    public void setMaLoSP(String maLoSP) {
        this.maLoSP = maLoSP;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    public boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
}
