package dto;

public class LoSanPham {

    private String maLoSP;
    private String maPN;
    private String maSP;
    private Double soLuong;
    private String ngayNhap;
    private String ngaySanXuat;
    private String hanSuDung;
    private double giaNhap;
    private String trangThaiXuLy;

    public LoSanPham() {
    }

    public LoSanPham(String maLoSP, String maPN, String maSP, Double soLuong,
            String ngayNhap, String ngaySanXuat, String hanSuDung,
            double tongTien, String trangThaiXuLy) {
        this.maLoSP = maLoSP;
        this.maPN = maPN;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.ngayNhap = ngayNhap;
        this.ngaySanXuat = ngaySanXuat;
        this.hanSuDung = hanSuDung;
        this.giaNhap = tongTien;
        this.trangThaiXuLy = trangThaiXuLy;
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

    public Double getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Double soLuong) {
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

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double tongTien) {
        this.giaNhap = tongTien;
    }

    public String getTrangThaiXuLy() {
        return trangThaiXuLy;
    }

    public void setTrangThaiXuLy(String trangThaiXuLy) {
        this.trangThaiXuLy = trangThaiXuLy;
    }
}