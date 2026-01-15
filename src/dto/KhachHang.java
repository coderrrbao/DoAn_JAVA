package dto;

public class KhachHang {

    private String maKH;
    private String tenKH;
    private String gioiTinh;
    private String sdt;
    private String ngayDangKy;
    private boolean TrangThai;

    public KhachHang() {
    }

    public KhachHang(String maKH, String tenKH, String gioiTinh, String sdt, String ngayDangKy) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.gioiTinh = gioiTinh;
        this.sdt = sdt;
        this.ngayDangKy = ngayDangKy;
    }
  
    public void setTrangThai(boolean TrangThai){
        this.TrangThai = TrangThai;
    }
    public boolean getTrangThai(){
        return TrangThai;
    }
    public String getMaKH() { 
        return maKH; 
    }
    public void setMaKH(String maKH) { 
        this.maKH = maKH; 
    }

    public String getTenKH() { 
        return tenKH; 
    }
    public void setTenKH(String tenKH) { 
        this.tenKH = tenKH; 
    }

    public String getGioiTinh() { 
        return gioiTinh; 
    }
    public void setGioiTinh(String gioiTinh) { 
        this.gioiTinh = gioiTinh; 
    }

    public String getSdt() { 
        return sdt; 
    }
    public void setSdt(String sdt) { 
        this.sdt = sdt; 
    }

    public String getNgayDangKy() { 
        return ngayDangKy; 
    }
    public void setNgayDangKy(String ngayDangKy) { 
        this.ngayDangKy = ngayDangKy; 
    }
}
