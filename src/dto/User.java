package dto;

import java.sql.Date;

public class User {
    private String ma;
    private String ten;
    private String chucVu;
    private String tenDangNhap;
    private String matKhau;
    private Date ngaySinh;
    private String gmail;
    private String soDt;
    private String anh;

    public User() {
    }

    public User(String ma, String ten, String chucVu, String tenDangNhap,
            String matKhau, Date ngaySinh, String gmail, String soDt, String anh) {
        this.ma = ma;
        this.ten = ten;
        this.chucVu = chucVu;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.ngaySinh = ngaySinh;
        this.gmail = gmail;
        this.soDt = soDt;
        this.anh = anh;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getSoDt() {
        return soDt;
    }

    public void setSoDt(String soDt) {
        this.soDt = soDt;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    // toString
    @Override
    public String toString() {
        return "User{" +
                "ma='" + ma + '\'' +
                ", ten='" + ten + '\'' +
                ", chucVu='" + chucVu + '\'' +
                ", tenDangNhap='" + tenDangNhap + '\'' +
                ", matKhau='" + matKhau + '\'' +
                ", ngaySinh=" + ngaySinh +
                ", gmail='" + gmail + '\'' +
                ", soDt='" + soDt + '\'' +
                ", anh='" + anh + '\'' +
                '}';
    }
}