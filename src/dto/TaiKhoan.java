package dto;

public class TaiKhoan {
    private String maTK;
    private String maNV;
    private String tenDangNhap;
    private String matKhau;
    private NhomQuyen nhomQuyen;
    private String trangThaiXuLy;

    public TaiKhoan() {
    }

    public TaiKhoan(String maTK, String maNV, String tenDangNhap, String matKhau, NhomQuyen nhomQuyen,
            String trangThai) {
        this.maTK = maTK;
        this.maNV = maNV;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.nhomQuyen = nhomQuyen;
        this.trangThaiXuLy = trangThai;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public NhomQuyen getNhomQuyen() {
        return nhomQuyen;
    }

    public void setNhomQuyen(NhomQuyen nhomQuyen) {
        this.nhomQuyen = nhomQuyen;
    }

    public String getTrangThaiXuLy() {
        return trangThaiXuLy;
    }

    public void setTrangThaiXuLy(String trangThaiXuLy) {
        this.trangThaiXuLy = trangThaiXuLy;
    }

    public String getMaTK() {
        return maTK;
    }

    public void setMaTK(String maTK) {
        this.maTK = maTK;
    }
}