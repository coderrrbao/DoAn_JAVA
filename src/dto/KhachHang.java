package dto;

public class KhachHang {
    private String maKH;
    private String tenKH;
    private String gioiTinh;
    private String sdt;
    private double tenDaMua;
    private String maHang;
    private boolean trangThai;

    public KhachHang() {
    }

    public KhachHang(String maKH, String tenKH, String gioiTinh, String sdt, double tenDaMua, String maHang,
            boolean trangThai) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.gioiTinh = gioiTinh;
        this.sdt = sdt;
        this.tenDaMua = tenDaMua;
        this.maHang = maHang;
        this.trangThai = trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public boolean getTrangThai() {
        return trangThai;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public double getTenDaMua() {
        return tenDaMua;
    }

    public void setTenDaMua(double tenDaMua) {
        this.tenDaMua = tenDaMua;
    }

    public String getMaHang() {
        return maHang;
    }

    public void setMaHang(String maHang) {
        this.maHang = maHang;
    }
}
