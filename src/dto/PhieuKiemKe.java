package dto;


public class PhieuKiemKe {
    private String maKK;
    private String maNV;
    private String ngayKiem;
    private String maLo;
    private String loaiLo;
    private Double soLuongSoSach;
    private Double soLuongThuc;
    private String ghiChu;
    private String trangThaiXuLy;


    public PhieuKiemKe() {

    }

    public String getGhiChu() {
        return ghiChu;
    }

    public String getLoaiLo() {
        return loaiLo;
    }

    public String getMaKK() {
        return maKK;
    }

    public String getMaLo() {
        return maLo;
    }

    public String getNgayKiem() {
        return ngayKiem;
    }

    public Double getSoLuongSoSach() {
        return soLuongSoSach;
    }

    public Double getSoLuongThuc() {
        return soLuongThuc;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public void setLoaiLo(String loaiLo) {
        this.loaiLo = loaiLo;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaKK(String maKK) {
        this.maKK = maKK;
    }

    public void setMaLo(String maLo) {
        this.maLo = maLo;
    }

    public void setNgayKiem(String ngayKiem) {
        this.ngayKiem = ngayKiem;
    }

    public void setSoLuongSoSach(Double soLuongSoSach) {
        this.soLuongSoSach = soLuongSoSach;
    }

    public void setSoLuongThuc(Double soLuongThuc) {
        this.soLuongThuc = soLuongThuc;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTrangThaiXuLy() {
        return trangThaiXuLy;
    }

    public void setTrangThaiXuLy(String trangThaiXuLy) {
        this.trangThaiXuLy = trangThaiXuLy;
    }
}
