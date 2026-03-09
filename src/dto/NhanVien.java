package dto;

import util.ExcelExport;

public class NhanVien implements ExcelExport {
    private String maNV;
    private String tenNV;
    private String gioiTinh;
    private String ngaySinh;
    private String sdt;
    private String diaChi;
    private String anh;

    @Override
    public String[] getExcelHeaders() {
        return new String[] {
                "Mã NV",
                "Họ và tên",
                "Giới tính",
                "Ngày sinh",
                "Số điện thoại",
                "Địa chỉ"
                // Đã bỏ Chức vụ
        };
    }

    @Override
    public Object[] toExcelRow() {
        return new Object[] {
                maNV,
                tenNV,
                gioiTinh,
                ngaySinh,
                sdt,
                diaChi
                // Đã bỏ Chức vụ
        };
    }

    public NhanVien() {
    }

    // Constructor đã bỏ chucVu
    public NhanVien(String maNV, String tenNV, String gioiTinh, String ngaySinh, String sdt,
            String diaChi, String anh) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.anh = anh;
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

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }
}