package dto;

public class ChiTietHoaDon {

    private String maHD;
    private String maSP;
    private String maSize;
    private int soLuong;
    private double gia;
    private boolean trangThai;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(String maHD, String maSP, String maSize, int soLuong, double gia) {
        this.maHD = maHD;
        this.maSP = maSP;
        this.maSize = maSize;
        this.soLuong = soLuong;
        this.gia = gia;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public boolean getTrangThai() {
        return trangThai;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getMaSize() {
        return maSize;
    }

    public void setMaSize(String maSize) {
        this.maSize = maSize;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }
}
