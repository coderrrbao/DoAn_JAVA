package dto;

public class HoaDon {

    private String maHD;
    private String maNV;
    private String maKH;
    private String maKM;
    private String ngayBan;
    private double tongTien;
    private double tienGiamGia;
    private boolean TrangThai;

    public HoaDon() {
    }

    public HoaDon(String maHD, String maNV, String maKH, String maKM,
                  String ngayBan, double tongTien, double tienGiamGia) {
        this.maHD = maHD;
        this.maNV = maNV;
        this.maKH = maKH;
        this.maKM = maKM;
        this.ngayBan = ngayBan;
        this.tongTien = tongTien;
        this.tienGiamGia = tienGiamGia;
    }

    public void setTrangThai(boolean TrangThai){
        this.TrangThai = TrangThai;
    }
    public boolean getTrangThai(){
        return TrangThai;
    }
    public String getMaHD() { 
        return maHD; 
    }
    public void setMaHD(String maHD) { 
        this.maHD = maHD; 
    }

    public String getMaNV() { 
        return maNV; 
    }
    public void setMaNV(String maNV) { 
        this.maNV = maNV; 
    }

    public String getMaKH() { 
        return maKH; 
    }
    public void setMaKH(String maKH) { 
        this.maKH = maKH; 
    }

    public String getMaKM() { 
        return maKM; 
    }
    public void setMaKM(String maKM) { 
        this.maKM = maKM; 
    }

    public String getNgayBan() { 
        return ngayBan; 
    }
    public void setNgayBan(String ngayBan) { 
        this.ngayBan = ngayBan; 
    }

    public double getTongTien() { 
        return tongTien; 
    }
    public void setTongTien(double tongTien) { 
        this.tongTien = tongTien; 
    }

    public double getTienGiamGia() { 
        return tienGiamGia; 
    }
    public void setTienGiamGia(double tienGiamGia) { 
        this.tienGiamGia = tienGiamGia; 
    }
}
