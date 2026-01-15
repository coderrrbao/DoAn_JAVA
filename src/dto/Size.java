package dto;

public class Size {

    private String maSize;
    private String maSP;
    private String tenSize;
    private int phanTramGia;
    private int phanTramNL;
    private boolean TrangThai;

    public Size() {
    }

    public Size(String maSize, String maSP, String tenSize, int phanTramGia, int phanTramNL) {
        this.maSize = maSize;
        this.maSP = maSP;
        this.tenSize = tenSize;
        this.phanTramGia = phanTramGia;
        this.phanTramNL = phanTramNL;
    }

    public void setTrangThai(boolean TrangThai){
        this.TrangThai = TrangThai;
    }
    public boolean getTrangThai(){
        return TrangThai;
    }
    public String getMaSize() { 
        return maSize; 
    }
    public void setMaSize(String maSize) { 
        this.maSize = maSize; 
    }

    public String getMaSP() { 
        return maSP; 
    }
    public void setMaSP(String maSP) { 
        this.maSP = maSP; 
    }

    public String getTenSize() { 
        return tenSize; 
    }
    public void setTenSize(String tenSize) { 
        this.tenSize = tenSize; 
    }

    public int getPhanTramGia() { 
        return phanTramGia; 
    }
    public void setPhanTramGia(int phanTramGia) { 
        this.phanTramGia = phanTramGia; 
    }

    public int getPhanTramNL() { 
        return phanTramNL; 
    }
    public void setPhanTramNL(int phanTramNL) { 
        this.phanTramNL = phanTramNL; 
    }
}
