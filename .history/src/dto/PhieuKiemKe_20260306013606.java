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
                "Mã NCC",
                "Tên Nhà Cung Cấp",
                "Loại Cung Cấp",
                "Số Điện Thoại",
                "Địa Chỉ"
        };
    }

    @Override
    public Object[] toExcelRow() {
        String loai = "";
        if (cungCapNL) {
            loai = "Nguyên liệu";
        } else if (cungCapSP) {
            loai = "Sản phẩm";
        } else {
            loai = "Không có";
        }
        return new Object[] {
        };
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
