package dto;

public class TaiKhoan {
    private String tenTaiKhoan;
    private String tenDangNhap;
    private String matKhau;
    private String maNQ;
    private boolean trangThai;

    public TaiKhoan() {
    }

    public TaiKhoan(String tenTaiKhoan,String tenDangNhap, String matKhau, String maNQ, boolean trangThai) {
        this.tenTaiKhoan = tenTaiKhoan;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.maNQ = maNQ;
        this.trangThai = trangThai;
    }

    public String getTenTaiKhoan(){
        return tenTaiKhoan;
    }
    
    public void setTenTaiKhoan(String tenTaiKhoan){
        this.tenTaiKhoan = tenTaiKhoan;
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

    public String getMaNQ() {
        return maNQ;
    }

    public void setMaNQ(String maNQ) {
        this.maNQ = maNQ;
    }

    public boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
}