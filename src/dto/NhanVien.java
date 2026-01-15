package dto;

public class NhanVien {

    private String maNV;
    private String tenNV;
    private String gioiTinh;
    private String ngaySinh;
    private String sdt;
    private String diaChi;
    private String chucVu;
    private boolean trangThai;
    private String phanQuyen;

    public NhanVien() {
    }

    public NhanVien(String maNV, String tenNV, String gioiTinh, String ngaySinh, String sdt,
                    String diaChi, String chucVu, boolean trangThai, String phanQuyen) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.chucVu = chucVu;
        this.trangThai = trangThai;
        this.phanQuyen = phanQuyen;
    }

    public String getMaNV() { 
        return maNV; 
    }
    public void setMaNV(String maNV) { 
        this.maNV = maNV; 
    }

    public String getTenNV() { 
        return tenNV; 
    }
    public void setTenNV(String tenNV) { 
        this.tenNV = tenNV; 
    }

    public String getGioiTinh() { 
        return gioiTinh; 
    }
    public void setGioiTinh(String gioiTinh) { 
        this.gioiTinh = gioiTinh; 
    }

    public String getNgaySinh() { 
        return ngaySinh; 
    }
    public void setNgaySinh(String ngaySinh) { 
        this.ngaySinh = ngaySinh; 
    }

    public String getSdt() { 
        return sdt; 
    }
    public void setSdt(String sdt) { 
        this.sdt = sdt; 
    }

    public String getDiaChi() { 
        return diaChi; 
    }
    public void setDiaChi(String diaChi) { 
        this.diaChi = diaChi; 
    }

    public String getChucVu() { 
        return chucVu; 
    }
    public void setChucVu(String chucVu) { 
        this.chucVu = chucVu; 
    }

    public boolean getTrangThai() { 
        return trangThai; 
    }
    public void setTrangThai(boolean trangThai) { 
        this.trangThai = trangThai; 
    }

    public String getPhanQuyen() { 
        return phanQuyen; 
    }
    public void setPhanQuyen(String phanQuyen) { 
        this.phanQuyen = phanQuyen; 
    }
}
