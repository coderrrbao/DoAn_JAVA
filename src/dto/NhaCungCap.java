package dto;

public class NhaCungCap {
    private String ma;
    private String ten;
    private String diaCHi;
    private String sdt;

    public NhaCungCap() {
    }

    public NhaCungCap(String ma, String ten, String diaCHi, String sdt) {
        this.ma = ma;
        this.ten = ten;
        this.diaCHi = diaCHi;
        this.sdt = sdt;
    }

    public String getDiaCHi() {
        return diaCHi;
    }

    public String getMa() {
        return ma;
    }

    public String getSdt() {
        return sdt;
    }

    public String getTen() {
        return ten;
    }

    public void setDiaCHi(String diaCHi) {
        this.diaCHi = diaCHi;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
}