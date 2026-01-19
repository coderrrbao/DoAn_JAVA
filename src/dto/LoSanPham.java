package dto;

public class LoSanPham {

    private String maLoSP;
    private String maPN;
    private String maSP;
    private int soLuong;
    private String ngayNhap;
    private String ngaySanXuat;
    private String hanSuDung;
    private double tongTien;
    private boolean trangThai;

    public LoSanPham() {
    }

    public LoSanPham(String maLoSP, String maPN, String maSP, int soLuong,
            String ngayNhap, String ngaySanXuat, String hanSuDung, double tongTien) {
        this.maLoSP = maLoSP;
        this.maPN = maPN;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.ngayNhap = ngayNhap;
        this.ngaySanXuat = ngaySanXuat;
        this.hanSuDung = hanSuDung;
        this.tongTien = tongTien;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public boolean getTrangThai() {
        return trangThai;
    }

    public String getMaLoSP() {
        return maLoSP;
    }

    public void setMaLoSP(String maLoSP) {
        this.maLoSP = maLoSP;
    }

    public String getMaPN() {
        return maPN;
    }

    public void setMaPN(String maPN) {
        this.maPN = maPN;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public String getNgaySanXuat() {
        return ngaySanXuat;
    }

    public void setNgaySanXuat(String ngaySanXuat) {
        this.ngaySanXuat = ngaySanXuat;
    }

    public String getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(String hanSuDung) {
        this.hanSuDung = hanSuDung;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
}
