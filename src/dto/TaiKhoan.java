package dto;

public class TaiKhoan {
    private String maNV; // Khóa ngoại liên kết với NhanVien
    private String tenTaiKhoan;
    private String matKhau;
    private String phanQuyen;
    private String trangThai;

    // Constructor không đối số
    public TaiKhoan() {}

    // Constructor đầy đủ đối số
    public TaiKhoan(String maNV, String tenTaiKhoan, String matKhau, String phanQuyen, String trangThai) {
        this.maNV = maNV;
        this.tenTaiKhoan = tenTaiKhoan;
        this.matKhau = matKhau;
        this.phanQuyen = phanQuyen;
        this.trangThai = trangThai;
    }

    // Getter và Setter
    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }

    public String getTenTaiKhoan() { return tenTaiKhoan; }
    public void setTenTaiKhoan(String tenTaiKhoan) { this.tenTaiKhoan = tenTaiKhoan; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }

    public String getPhanQuyen() { return phanQuyen; }
    public void setPhanQuyen(String phanQuyen) { this.phanQuyen = phanQuyen; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}