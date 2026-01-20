package dto;

public class NguyenLieu {
    private String maNL;
    private String tenNL;
    private NhaCungCap nhaCungCap;
    private double gia;
    private String donVi;
    private int mucCanhBao;
    private boolean trangThai;

    public NguyenLieu() {
    }

    public NguyenLieu(String maNL, String tenNL, NhaCungCap nhaCungCap, double gia,
            String donVi, int mucCanhBao, boolean trangThai) {
        this.maNL = maNL;
        this.tenNL = tenNL;
        this.nhaCungCap = nhaCungCap;
        this.gia = gia;
        this.donVi = donVi;
        this.mucCanhBao = mucCanhBao;
        this.trangThai = trangThai;
    }

    public String getMaNL() {
        return maNL;
    }

    public void setMaNL(String maNL) {
        this.maNL = maNL;
    }

    public String getTenNL() {
        return tenNL;
    }

    public void setTenNL(String tenNL) {
        this.tenNL = tenNL;
    }

    public NhaCungCap getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public int getMucCanhBao() {
        return mucCanhBao;
    }

    public void setMucCanhBao(int mucCanhBao) {
        this.mucCanhBao = mucCanhBao;
    }

    public boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
}
