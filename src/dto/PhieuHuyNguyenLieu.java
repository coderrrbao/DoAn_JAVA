package dto;

import java.sql.Date;

public class PhieuHuyNguyenLieu {
    private String maPH;
    private String maLo;
    private Date ngayHuy;
    private String maNV;
    private String lyDo;
    private double tongTien;
    private boolean trangThai;

    public PhieuHuyNguyenLieu() {
    }

    public PhieuHuyNguyenLieu(String maPH, String maLo, Date ngayHuy, String maNV, String lyDo, double tongTien,
            boolean trangThai) {
        this.maPH = maPH;
        this.maLo = maLo;
        this.ngayHuy = ngayHuy;
        this.maNV = maNV;
        this.lyDo = lyDo;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    public String getMaPH() {
        return maPH;
    }

    public void setMaPH(String maPH) {
        this.maPH = maPH;
    }

    public String getMaLo() {
        return maLo;
    }

    public void setMaLo(String maLo) {
        this.maLo = maLo;
    }

    public Date getNgayHuy() {
        return ngayHuy;
    }

    public void setNgayHuy(Date ngayHuy) {
        this.ngayHuy = ngayHuy;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getLyDo() {
        return lyDo;
    }

    public void setLyDo(String lyDo) {
        this.lyDo = lyDo;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
}
