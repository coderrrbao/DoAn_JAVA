package dto;

import java.sql.Date;

public class MaGiamGia {
    private String maKM;
    private int phanTramGiam;
    private Date tuNgay;
    private Date denNgay;
    private boolean trangThai;

    public MaGiamGia() {
    }

    public MaGiamGia(String maKM, int phanTramGiam, Date tuNgay, Date denNgay, boolean trangThai) {
        this.maKM = maKM;
        this.phanTramGiam = phanTramGiam;
        this.tuNgay = tuNgay;
        this.denNgay = denNgay;
        this.trangThai = trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public boolean getTrangThai() {
        return trangThai;
    }

    public String getMaKM() {
        return maKM;
    }

    public void setMaKM(String maKM) {
        this.maKM = maKM;
    }

    public int getPhanTramGiam() {
        return phanTramGiam;
    }

    public void setPhanTramGiam(int phanTramGiam) {
        this.phanTramGiam = phanTramGiam;
    }

    public Date getTuNgay() {
        return tuNgay;
    }

    public void setTuNgay(Date tuNgay) {
        this.tuNgay = tuNgay;
    }

    public Date getDenNgay() {
        return denNgay;
    }

    public void setDenNgay(Date denNgay) {
        this.denNgay = denNgay;
    }
}
