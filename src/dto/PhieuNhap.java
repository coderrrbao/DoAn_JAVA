package dto;

public class PhieuNhap {

    private String maPN;
    private String ngayNhap;
    private String maNV;
    private double tongTien;
    private String maNCC;
    private boolean TrangThai;

    public PhieuNhap() {
    }

    public PhieuNhap(String maPN, String ngayNhap, String maNV, double tongTien, String maNCC) {
        this.maPN = maPN;
        this.ngayNhap = ngayNhap;
        this.maNV = maNV;
        this.tongTien = tongTien;
        this.maNCC = maNCC;
    }

    public void setTrangThai(boolean TrangThai){
        this.TrangThai = TrangThai;
    }
    public boolean getTrangThai(){
        return TrangThai;
    }
    public String getMaPN() { 
        return maPN; 
    }
    public void setMaPN(String maPN) { 
        this.maPN = maPN; 
    }

    public String getNgayNhap() { 
        return ngayNhap; 
    }
    public void setNgayNhap(String ngayNhap) { 
        this.ngayNhap = ngayNhap; 
    }

    public String getMaNV() { 
        return maNV; 
    }
    public void setMaNV(String maNV) { 
        this.maNV = maNV; 
    }

    public double getTongTien() { 
        return tongTien; 
    }
    public void setTongTien(double tongTien) { 
        this.tongTien = tongTien; 
    }

    public String getMaNCC() { 
        return maNCC; 
    }
    public void setMaNCC(String maNCC) { 
        this.maNCC = maNCC; 
    }
}
