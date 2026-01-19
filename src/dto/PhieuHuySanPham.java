package dto;

import java.sql.Date;

public class PhieuHuySanPham {
    private String maPH;
    private String maLo;
    private Date ngayHuy;
    private String maNV;
    private String lyDo;
    private double tongGiaTri;
    private boolean trangThai;

    public PhieuHuySanPham() {
    }

    public PhieuHuySanPham(String maPH, String maLo, Date ngayHuy, String maNV, String lyDo, double tongGiaTri,
            boolean trangThai) {
        this.maPH = maPH;
        this.maLo = maLo;
        this.ngayHuy = ngayHuy;
        this.maNV = maNV;
        this.lyDo = lyDo;
        this.tongGiaTri = tongGiaTri;
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

    public double getTongGiaTri() {
        return tongGiaTri;
    }

    public void setTongGiaTri(double tongGiaTri) {
        this.tongGiaTri = tongGiaTri;
    }

    public boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
}
