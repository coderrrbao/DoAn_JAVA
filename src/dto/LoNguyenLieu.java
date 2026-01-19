package dto;

public class LoNguyenLieu {

    private String maLoNL;
    private String maPN;
    private String maNL;
    private int soLuong;
    private String ngayNhap;
    private String ngaySanXuat;
    private String hanSuDung;
    private boolean trangThai;

    public LoNguyenLieu() {
    }

    public LoNguyenLieu(String maLoNL, String maPN, String maNL, int soLuong,
            String ngayNhap, String ngaySanXuat, String hanSuDung) {
        this.maLoNL = maLoNL;
        this.maPN = maPN;
        this.maNL = maNL;
        this.soLuong = soLuong;
        this.ngayNhap = ngayNhap;
        this.ngaySanXuat = ngaySanXuat;
        this.hanSuDung = hanSuDung;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public boolean getTrangThai() {
        return trangThai;
    }

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
}
