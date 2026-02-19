package dto;

public class ChiTietNhaCungCap {
    private int maNCCDT;
    private String maNCC;
    private String loaiDoiTuong;
    private String maDoiTuong;
    private double giaNhap;

    public ChiTietNhaCungCap() {
    }

    public ChiTietNhaCungCap(int maNCCDT, String maNCC, String loaiDoiTuong, String maDoiTuong, double giaNhap) {
        this.maNCCDT = maNCCDT;
        this.maNCC = maNCC;
        this.loaiDoiTuong = loaiDoiTuong;
        this.maDoiTuong = maDoiTuong;
        this.giaNhap = giaNhap;
    }

    public ChiTietNhaCungCap(String maNCC, String loaiDoiTuong, String maDoiTuong, double giaNhap) {
        this.maNCC = maNCC;
        this.loaiDoiTuong = loaiDoiTuong;
        this.maDoiTuong = maDoiTuong;
        this.giaNhap = giaNhap;
    }

    public int getMaNCCDT() {
        return maNCCDT;
    }

    public void setMaNCCDT(int maNCCDT) {
        this.maNCCDT = maNCCDT;
    }

    public String getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    public String getLoaiDoiTuong() {
        return loaiDoiTuong;
    }

    public void setLoaiDoiTuong(String loaiDoiTuong) {
        this.loaiDoiTuong = loaiDoiTuong;
    }

    public String getMaDoiTuong() {
        return maDoiTuong;
    }

    public void setMaDoiTuong(String maDoiTuong) {
        this.maDoiTuong = maDoiTuong;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }
}