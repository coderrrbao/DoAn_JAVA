package dto;

public class TaiKhoan {

    private String maNV;
    private String matKhau;
    private boolean TrangThai;

    public TaiKhoan() {
    }

    public TaiKhoan(String maNV, String matKhau) {
        this.maNV = maNV;
        this.matKhau = matKhau;
    }
 
    public void setTrangThai(boolean TrangThai){
        this.TrangThai = TrangThai;
    }
    public boolean getTrangThai(){
        return TrangThai;
    }
    public String getMaNV() { 
        return maNV; 
    }
    public void setMaNV(String maNV) { 
        this.maNV = maNV; 
    }

    public String getMatKhau() { 
        return matKhau; 
    }
    public void setMatKhau(String matKhau) { 
        this.matKhau = matKhau; 
    }
}
