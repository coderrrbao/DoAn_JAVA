package dto;

public class HangThanhVien {
    private String maHang;
    private String tenHang;
    private int phanTramGiam;
    private double dieuKien;
    private boolean trangThai;

    public HangThanhVien() {
    }

    public HangThanhVien(String maHang, String tenHang, int phanTramGiam, double dieuKien, boolean trangThai) {
        this.maHang = maHang;
        this.tenHang = tenHang;
        this.phanTramGiam = phanTramGiam;
        this.dieuKien = dieuKien;
        this.trangThai = trangThai;
    }

    public String getMaHang() {
        return maHang;
    }

    public void setMaHang(String maHang) {
        this.maHang = maHang;
    }

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    public int getPhanTramGiam() {
        return phanTramGiam;
    }

    public void setPhanTramGiam(int phanTramGiam) {
        this.phanTramGiam = phanTramGiam;
    }

    public double getDieuKien() {
        return dieuKien;
    }

    public void setDieuKien(double dieuKien) {
        this.dieuKien = dieuKien;
    }

    public boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
}
