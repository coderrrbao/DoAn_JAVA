package dto;

import util.ExcelExport;

public class ChiTietNhaCungCap implements ExcelExport {
    private String maCTNCC;
    private String maNCC;
    private String loaiDoiTuong;
    private String maDoiTuong;
    private double giaNhap;

    @Override
    public String[] getExcelHeaders() {
        return new String[] {
                "Mã CTNCC",
                "Mã NCC",
                "Loại Đối Tượng",
                "Mã Đối Tượng",
                "Giá Nhập"
        };
    }

    @Override
    public Object[] toExcelRow() {
        return new Object[] {
                maCTNCC, 
                maNCC, 
                loaiDoiTuong, 
                maDoiTuong, 
                giaNhap
        };
    }

    public ChiTietNhaCungCap() {
    }

    public ChiTietNhaCungCap(String maNCCDT, String maNCC, String loaiDoiTuong, String maDoiTuong, double giaNhap) {
        this.maCTNCC = maNCCDT;
        this.maNCC = maNCC;
        this.loaiDoiTuong = loaiDoiTuong;
        this.maDoiTuong = maDoiTuong;
        this.giaNhap = giaNhap;
    }

    public ChiTietNhaCungCap(String maNCC, String loaiDoiTuong, String maDoiTuong, double giaNhap) {
        this.maNCC = maNCC;
        this.loaiDoiTuong = loaiDoiTuong;
        this.maDoiTuong = maDoiTuong;
        this.giaNhap = giaNhap;
    }

    public String getMaCTNCC() {
        return maCTNCC;
    }

    public void setMaCTNCC(String maCTNCC) {
        this.maCTNCC = maCTNCC;
    }

    public String getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    public String getLoaiDoiTuong() {
        return loaiDoiTuong;
    }

    public void setLoaiDoiTuong(String loaiDoiTuong) {
        this.loaiDoiTuong = loaiDoiTuong;
    }

    public String getMaDoiTuong() {
        return maDoiTuong;
    }

    public void setMaDoiTuong(String maDoiTuong) {
        this.maDoiTuong = maDoiTuong;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }
}