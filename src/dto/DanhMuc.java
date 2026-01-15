package dto;

public class DanhMuc {

    private String maDM;
    private String tenDM;
    private String moTa;
    private boolean TrangThai;

    public DanhMuc() {
    }

    public DanhMuc(String maDM, String tenDM, String moTa) {
        this.maDM = maDM;
        this.tenDM = tenDM;
        this.moTa = moTa;
    }

    public void setTrangThai(boolean TrangThai){
        this.TrangThai = TrangThai;
    }
    public boolean getTrangThai(){
        return TrangThai;
    }
    public String getMaDM() { 
        return maDM; 
    }
    public void setMaDM(String maDM) { 
        this.maDM = maDM; 
    }

    public String getTenDM() { 
        return tenDM; 
    }
    public void setTenDM(String tenDM) { 
        this.tenDM = tenDM; 
    }

    public String getMoTa() { 
        return moTa; 
    }
    public void setMoTa(String moTa) { 
        this.moTa = moTa; 
    }
}

