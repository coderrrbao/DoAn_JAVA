package dto;

public class Kho {

    private String maSP;
    private int soLuongTon;
    private int mucCanhBao;
    private boolean trangThai;

    public Kho() {
    }

    public Kho(String maSP, int soLuongTon, int mucCanhBao) {
        this.maSP = maSP;
        this.soLuongTon = soLuongTon;
        this.mucCanhBao = mucCanhBao;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public boolean getTrangThai() {
        return trangThai;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public int getMucCanhBao() {
        return mucCanhBao;
    }

    public void setMucCanhBao(int mucCanhBao) {
        this.mucCanhBao = mucCanhBao;
    }
}
