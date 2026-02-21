package dto;

public class NguyenLieu {
    private String maNL;
    private String tenNL;
    private double gia;
    private String donVi;
    private int mucCanhBao;

    public NguyenLieu() {
    }

    public NguyenLieu(String maNL, String tenNL, double gia,
            String donVi, int mucCanhBao, boolean trangThai) {
        this.maNL = maNL;
        this.tenNL = tenNL;
        this.gia = gia;
        this.donVi = donVi;
        this.mucCanhBao = mucCanhBao;
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

}
