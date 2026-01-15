package dto;

public class NguyenLieu {

    private String maNL;
    private String tenNL;
    private String maNCC;
    private boolean TrangThai;

    public NguyenLieu() {
    }

    public NguyenLieu(String maNL, String tenNL, String maNCC) {
        this.maNL = maNL;
        this.tenNL = tenNL;
        this.maNCC = maNCC;
    }

    public void setTrangThai(boolean TrangThai){
        this.TrangThai = TrangThai;
    }
    public boolean getTrangThai(){
        return TrangThai;
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

    public String getMaNCC() { 
        return maNCC; 
    }
    public void setMaNCC(String maNCC) { 
        this.maNCC = maNCC; 
    }
}
