package dto;

public class CongThuc {

    private String maCT;
    private String maSP;
    private boolean TrangThai;
    
    public CongThuc() {
    }

    public CongThuc(String maCT, String maSP) {
        this.maCT = maCT;
        this.maSP = maSP;
    }

    public void setTrangThai(boolean TrangThai){
        this.TrangThai = TrangThai;
    }
    public boolean getTrangThai(){
        return TrangThai;
    }
    public String getMaCT() { 
        return maCT; 
    }
    public void setMaCT(String maCT) { 
        this.maCT = maCT; 
    }

    public String getMaSP() { 
        return maSP; 
    }
    public void setMaSP(String maSP) { 
        this.maSP = maSP; 
    }
}
