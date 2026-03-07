package dto;

import java.time.LocalDate;

import util.ExcelExport;

public class PhieuKiemKe implements ExcelExport {
    private String maKK;
    private String ngayKiem;
    private String maLo;
    private String loaiLo;
    private Double soLuongSoSach;
    private Double soLuongThuc;
    private String ghiChu;
    private String maNV;
    private String trangThaiXuLy;

    @Override
    public String[] getExcelHeaders() {
        return new String[] {
                "Mã Phiếu Kiểm", "Ngày Kiểm", "Mã Lô", "Loại Lô", "SL Sổ Sách", "SL Thực Tế", "Chênh Lệch",
                "Trạng Thái"
        };
    }

    @Override
    public Object[] toExcelRow() {
        return new Object[] {
                maKK,
                ngayKiem,
                maLo,
                loaiLo,
                soLuongSoSach,
                soLuongThuc,
                soLuongThuc - soLuongSoSach,
                trangThaiXuLy
        };
    }

    public PhieuKiemKe(String maKK
String ngayKiem,
String maLo,
String loaiLo,
Double soLuongSoSach,
Double soLuongThuc,
String ghiChu,
String maNV,
String trangThaiXuLy){
    this.maKK = maKK;
this.ngayKiem = ngayKiem;
this.maLo = maLo;
this.loaiLo = loaiLo;
this.soLuongSoSach = soLuongSoSach;
this.soLuongThuc = soLuongThuc;
this.ghiChu = ghiChu;
this.maNV = maNV;
this.trangThaiXuLy = trangThaiXuLy;
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
