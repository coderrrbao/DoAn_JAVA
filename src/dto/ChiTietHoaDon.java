package dto;

public class ChiTietHoaDon {

    private String maCT;
    private String maHD;
    private SanPham sanPham;
    private Size size;
    private int soLuong;
    private double gia;
    private boolean trangThai;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(String maCT, String maHD, SanPham sanPham, Size size, int soLuong, double gia,
            boolean trangThai) {
        this.maCT = maCT;
        this.maHD = maHD;
        this.sanPham = sanPham;
        this.size = size;
        this.soLuong = soLuong;
        this.gia = gia;
        this.trangThai = trangThai;
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

    public String getMaCT() {
        return maCT;
    }

    public void setMaCT(String maCT) {
        this.maCT = maCT;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
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
