package dto;

public class KhuyenMai {

    private String maKM;
    private int phanTramGiam;
    private String tuNgay;
    private String denNgay;
    private String maSP;
    private boolean TrangThai;
    public KhuyenMai() {
    }

    public KhuyenMai(String maKM, int phanTramGiam, String tuNgay, String denNgay, String maSP) {
        this.maKM = maKM;
        this.phanTramGiam = phanTramGiam;
        this.tuNgay = tuNgay;
        this.denNgay = denNgay;
        this.maSP = maSP;
    }

    public void setTrangThai(boolean TrangThai){
        this.TrangThai = TrangThai;
    }
    public boolean getTrangThai(){
        return TrangThai;
    }
    public String getMaKM() { 
        return maKM; 
    }
    public void setMaKM(String maKM) { 
        this.maKM = maKM; 
    }

    public int getPhanTramGiam() { 
        return phanTramGiam; 
    }
    public void setPhanTramGiam(int phanTramGiam) { 
        this.phanTramGiam = phanTramGiam; 
    }

    public String getTuNgay() { 
        return tuNgay; 
    }
    public void setTuNgay(String tuNgay) { 
        this.tuNgay = tuNgay; 
    }

    public String getDenNgay() { 
        return denNgay; 
    }
    public void setDenNgay(String denNgay) { 
        this.denNgay = denNgay; 
    }

    public String getMaSP() { 
        return maSP; 
    }
    public void setMaSP(String maSP) { 
        this.maSP = maSP; 
    }
}
