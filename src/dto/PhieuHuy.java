package dto;

public class PhieuHuy {

    private String maPH;
    private String ngayHuy;
    private String maNV;
    private String lyDo;
    private String loaiHuy;   // "SP" hoặc "NL"
    private String maLoSP;    // null nếu huỷ nguyên liệu
    private String maLoNL;    // null nếu huỷ sản phẩm
    private int soLuong;
    private double tongGiaTri;
    private boolean TrangThai;
    
    public PhieuHuy() {
    }

    public PhieuHuy(String maPH, String ngayHuy, String maNV, String lyDo, String loaiHuy,
                    String maLoSP, String maLoNL, int soLuong, double tongGiaTri) {
        this.maPH = maPH;
        this.ngayHuy = ngayHuy;
        this.maNV = maNV;
        this.lyDo = lyDo;
        this.loaiHuy = loaiHuy;
        this.maLoSP = maLoSP;
        this.maLoNL = maLoNL;
        this.soLuong = soLuong;
        this.tongGiaTri = tongGiaTri;
    }

    
    public void setTrangThai(boolean TrangThai){
        this.TrangThai = TrangThai;
    }
    public boolean getTrangThai(){
        return TrangThai;
    }
    public String getMaPH() { 
        return maPH; 
    }
    public void setMaPH(String maPH) { 
        this.maPH = maPH; 
    }

    public String getNgayHuy() { 
        return ngayHuy; 
    }
    public void setNgayHuy(String ngayHuy) { 
        this.ngayHuy = ngayHuy; 
    }

    public String getMaNV() { 
        return maNV; 
    }
    public void setMaNV(String maNV) { 
        this.maNV = maNV; 
    }

    public String getLyDo() { 
        return lyDo; 
    }
    public void setLyDo(String lyDo) { 
        this.lyDo = lyDo; 
    }

    public String getLoaiHuy() { 
        return loaiHuy; 
    }
    public void setLoaiHuy(String loaiHuy) { 
        this.loaiHuy = loaiHuy; 
    }

    public String getMaLoSP() { 
        return maLoSP; 
    }
    public void setMaLoSP(String maLoSP) { 
        this.maLoSP = maLoSP; 
    }

    public String getMaLoNL() { 
        return maLoNL; 
    }
    public void setMaLoNL(String maLoNL) { 
        this.maLoNL = maLoNL; 
    }

    public int getSoLuong() { 
        return soLuong; 
    }
    public void setSoLuong(int soLuong) { 
        this.soLuong = soLuong; 
    }

    public double getTongGiaTri() { 
        return tongGiaTri; 
    }
    public void setTongGiaTri(double tongGiaTri) { 
        this.tongGiaTri = tongGiaTri; 
    }
}
