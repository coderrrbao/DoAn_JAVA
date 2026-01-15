package dto;
public class ChiTietCongThuc {

    private String maCT;
    private String maNL;
    private double soLuong;
    private boolean TrangThai;

    public ChiTietCongThuc() {
    }

    public ChiTietCongThuc(String maCT, String maNL, double soLuong) {
        this.maCT = maCT;
        this.maNL = maNL;
        this.soLuong = soLuong;
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

    public String getMaNL() { 
        return maNL; 
    }
    public void setMaNL(String maNL) { 
        this.maNL = maNL; 
    }

    public double getSoLuong() { 
        return soLuong; 
    }
    public void setSoLuong(double soLuong) { 
        this.soLuong = soLuong; 
    }
}
