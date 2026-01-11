package dto;

import java.sql.Date;

public class SanPham {
    private String ma;
    private String ten;
    // Nước ngọt,Nước suối,Trà,Cà phê,Nước tăng lực
    private String loaiNuoc;
    private long giaNhap;
    private long giaBan;
    private int dungTichMl;
    private NhaCungCap nhaCungCap;
    private int soLuongTon;
    private Date hanSanXuat;
    private Date hanSuDung;
    private boolean trangThai;
    private String anh;
    private String gioiThieu;

    public int getDungTichMl() {
        return dungTichMl;
    }

    public String getAnh() {
        return anh;
    }

    public long getGiaBan() {
        return giaBan;
    }

    public long getGiaNhap() {
        return giaNhap;
    }

    public String getGioiThieu() {
        return gioiThieu;
    }

    public Date getHanSanXuat() {
        return hanSanXuat;
    }

    public Date getHanSuDung() {
        return hanSuDung;
    }

    public String getLoaiNuoc() {
        return loaiNuoc;
    }

    public String getMa() {
        return ma;
    }

    public NhaCungCap getNhaCungCap() {
        return nhaCungCap;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public String getTen() {
        return ten;
    }

    public boolean getTrangThai() {
        return trangThai;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public void setGiaBan(long giaBan) {
        this.giaBan = giaBan;
    }

    public void setGiaNhap(long giaNhap) {
        this.giaNhap = giaNhap;
    }

    public void setGioiThieu(String gioiThieu) {
        this.gioiThieu = gioiThieu;
    }

    public void setHanSanXuat(Date hanSanXuat) {
        this.hanSanXuat = hanSanXuat;
    }

    public void setHanSuDung(Date hanSuDung) {
        this.hanSuDung = hanSuDung;
    }

    public void setLoaiNuoc(String loaiNuoc) {
        this.loaiNuoc = loaiNuoc;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public void setDungTichMl(int dungTichMl) {
        this.dungTichMl = dungTichMl;
    }
}
