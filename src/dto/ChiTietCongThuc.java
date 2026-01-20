package dto;

public class ChiTietCongThuc {

    private String maCTCT;
    private NguyenLieu nguyenLieu;
    private double soLuong;
    private boolean trangThai;

    public ChiTietCongThuc() {
    }

    public ChiTietCongThuc(String maCT, NguyenLieu nguyenLieu, double soLuong) {
        this.maCTCT = maCT;
        this.nguyenLieu = nguyenLieu;
        this.soLuong = soLuong;
    }

    public ChiTietCongThuc(String maCT, double soLuong) {
        this.maCTCT = maCT;
        this.soLuong = soLuong;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public boolean getTrangThai() {
        return trangThai;
    }

    public String getMaCTCT() {
        return maCTCT;
    }

    public void setMaCTCT(String maCT) {
        this.maCTCT = maCT;
    }

    public void setNguyenLieu(NguyenLieu nguyenLieu) {
        this.nguyenLieu = nguyenLieu;
    }

    public NguyenLieu getNguyenLieu() {
        return nguyenLieu;
    }

    public double getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(double soLuong) {
        this.soLuong = soLuong;
    }
}
