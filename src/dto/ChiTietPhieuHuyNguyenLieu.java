package dto;

public class ChiTietPhieuHuyNguyenLieu {
    private String maCTPHNL;
    private String maPH;
    private LoNguyenLieu loNguyenLieu;
    private double soLuong;
    private double donGia;

    public ChiTietPhieuHuyNguyenLieu() {
    }

    public ChiTietPhieuHuyNguyenLieu(String maCTPHNL, String maPH, LoNguyenLieu loNL, double soLuong, double donGia) {
        this.maCTPHNL = maCTPHNL;
        this.maPH = maPH;
        this.loNguyenLieu = loNL;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public String getMaCTPHNL() {
        return maCTPHNL;
    }

    public void setMaCTPHNL(String maCTPHNL) {
        this.maCTPHNL = maCTPHNL;
    }

    public String getMaPH() {
        return maPH;
    }

    public void setMaPH(String maPH) {
        this.maPH = maPH;
    }

    public LoNguyenLieu getLoNguyenLieu() {
        return loNguyenLieu;
    }

    public void setLoNguyenLieu(LoNguyenLieu loNL) {
        this.loNguyenLieu = loNL;
    }

    public double getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(double soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public double getThanhTien() {
        return this.soLuong * this.donGia;
    }
}