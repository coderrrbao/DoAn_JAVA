package dto;

import util.ExcelExport;

public class HangThanhVien implements ExcelExport{
    private String maHang;
    private String tenHang;
    private int phanTramGiam;
    private double dieuKien;

    @Override
    public String[] getExcelHeaders() {
        return new String[] {
            "Mã Hạng",
            "Tên Hạng",
            "Phần Trăm Giảm (%)",
            
        };
    }

    @Override
    public Object[] toExcelRow() {
        return new Object[] {
        };
    }

    public HangThanhVien() {
    }

    public HangThanhVien(String maHang, String tenHang, int phanTramGiam, double dieuKien) {
        this.maHang = maHang;
        this.tenHang = tenHang;
        this.phanTramGiam = phanTramGiam;
        this.dieuKien = dieuKien;
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
}