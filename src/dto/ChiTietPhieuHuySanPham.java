package dto;

public class ChiTietPhieuHuySanPham {
    private String maCTPHSP;
    private String maPH;
    private LoSanPham loSanPham;
    private double soLuong;
    private double donGia;

    public ChiTietPhieuHuySanPham() {
    }

    public ChiTietPhieuHuySanPham(String maCTPHSP, String maPH, LoSanPham loSP, double soLuong, double donGia) {
        this.maCTPHSP = maCTPHSP;
        this.maPH = maPH;
        this.loSanPham = loSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public String getMaCTPHSP() {
        return maCTPHSP;
    }

    public void setMaCTPHSP(String maCTPHSP) {
        this.maCTPHSP = maCTPHSP;
    }

    public String getMaPH() {
        return maPH;
    }

    public void setMaPH(String maPH) {
        this.maPH = maPH;
    }

    public LoSanPham getLoSanPham() {
        return loSanPham;
    }

    public void setLoSanPham(LoSanPham loSP) {
        this.loSanPham = loSP;
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