package dto;

public class NhaCungCap {

    private String maNCC;
    private String tenNCC;
    private String soDienThoai;
    private boolean TrangThai;

    public NhaCungCap() {
    }

    public NhaCungCap(String maNCC, String tenNCC, String soDienThoai) {
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.soDienThoai = soDienThoai;
    }

    public void setTrangThai(boolean TrangThai){
        this.TrangThai = TrangThai;
    }
    public boolean getTrangThai(){
        return TrangThai;
    }
    public String getMaNCC() { 
        return maNCC; 
    }
    public void setMaNCC(String maNCC) { 
        this.maNCC = maNCC; 
    }

    public String getTenNCC() { 
        return tenNCC; 
    }
    public void setTenNCC(String tenNCC) { 
        this.tenNCC = tenNCC; 
    }

    public String getSoDienThoai() { 
        return soDienThoai; 
    }
    public void setSoDienThoai(String soDienThoai) { 
        this.soDienThoai = soDienThoai; 
    }
}
