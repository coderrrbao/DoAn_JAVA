package dto;
public class SanPham {

    private String maSP;
    private String tenSP;
    private String maDM;
    private double giaNhap;
    private double giaBan;
    private String maNCC;
    private int soLuongTon;
    private String trangThaiSP;
    private String anh;
    private String moTa;
    private boolean coSan;
    private boolean TrangThai;

    public SanPham() {
    }

    public SanPham(String maSP, String tenSP, String maDM, double giaNhap, double giaBan,
                   String maNCC, int soLuongTon, String trangThaiSP, String anh, String moTa, boolean coSan) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.maDM = maDM;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.maNCC = maNCC;
        this.soLuongTon = soLuongTon;
        this.trangThaiSP = trangThaiSP;
        this.anh = anh;
        this.moTa = moTa;
        this.coSan = coSan;
    }

    public void setTrangThai(boolean TrangThai){
        this.TrangThai = TrangThai;
    }
    public boolean getTrangThai(){
        return TrangThai;
    }
    public String getMaSP() { 
        return maSP; 
    }
    public void setMaSP(String maSP) { 
        this.maSP = maSP; 
    }

    public String getTenSP() { 
        return tenSP; 
    }
    public void setTenSP(String tenSP) { 
        this.tenSP = tenSP; 
    }

    public String getMaDM() { 
        return maDM; 
    }
    public void setMaDM(String maDM) { 
        this.maDM = maDM; 
    }

    public double getGiaNhap() { 
        return giaNhap; 
    }
    public void setGiaNhap(double giaNhap) { 
        this.giaNhap = giaNhap; 
    }

    public double getGiaBan() { 
        return giaBan; 
    }
    public void setGiaBan(double giaBan) { 
        this.giaBan = giaBan; 
    }

    public String getMaNCC() { 
        return maNCC; 
    }
    public void setMaNCC(String maNCC) { 
        this.maNCC = maNCC; 
    }

    public int getSoLuongTon() { 
        return soLuongTon; 
    }
    public void setSoLuongTon(int soLuongTon) { 
        this.soLuongTon = soLuongTon; 
    }

    public String getTrangThaiSP() { 
        return trangThaiSP; 
    }
    public void setTrangThaiSP(String trangThaiSP) { 
        this.trangThaiSP = trangThaiSP; 
    }

    public String getAnh() { 
        return anh; 
    }
    public void setAnh(String anh) { 
        this.anh = anh; 
    }

    public String getMoTa() { 
        return moTa; 
    }
    public void setMoTa(String moTa) { 
        this.moTa = moTa; 
    }

    public boolean isCoSan() { 
        return coSan; 
    }
    public void setCoSan(boolean coSan) { 
        this.coSan = coSan; 
    }
}
