package dto;

public class LoNguyenLieu {

    private String maLoNL;
    private String maPN;
    private String maNL;
    private Double soLuong;
    private String ngayNhap;
    private String ngaySanXuat;
    private String hanSuDung;
    private double giaNhap;
    private String trangThaiXuLy;

    public LoNguyenLieu() {
    }

    // Constructor đầy đủ tham số để đồng bộ với SQL
    public LoNguyenLieu(String maLoNL, String maPN, String maNL, Double soLuong,
            String ngayNhap, String ngaySanXuat, String hanSuDung,
            double giaNhap, String trangThaiXuLy) {
        this.maLoNL = maLoNL;
        this.maPN = maPN;
        this.maNL = maNL;
        this.soLuong = soLuong;
        this.ngayNhap = ngayNhap;
        this.ngaySanXuat = ngaySanXuat;
        this.hanSuDung = hanSuDung;
        this.giaNhap = giaNhap;
        this.trangThaiXuLy = trangThaiXuLy;
    }

    // Getters and Setters
    public String getMaLoNL() {
        return maLoNL;
    }

    public void setMaLoNL(String maLoNL) {
        this.maLoNL = maLoNL;
    }

    public String getMaPN() {
        return maPN;
    }

    public void setMaPN(String maPN) {
        this.maPN = maPN;
    }

    public String getMaNL() {
        return maNL;
    }

    public void setMaNL(String maNL) {
        this.maNL = maNL;
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

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public String getTrangThaiXuLy() {
        return trangThaiXuLy;
    }

    public void setTrangThaiXuLy(String trangThaiXuLy) {
        this.trangThaiXuLy = trangThaiXuLy;
    }
}