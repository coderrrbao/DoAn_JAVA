package dto;

import java.util.ArrayList;

public class PhieuNhapSanPham {
    private String maPN;
    private String ngayNhap;
    private String maNV;
    private double tongTien;
    private String maNCC;
    private String trangThaiXuLy;
    private String ghiChu;
    private ArrayList<LoSanPham> listLoSanPham;

    public PhieuNhapSanPham() {
    }

    public PhieuNhapSanPham(String maLoSP, String ngayNhap, String maNV, double tongTien, String maNCC,
            String trangThaiXuLy, String ghiChu) {
        this.maPN = maLoSP;
        this.ngayNhap = ngayNhap;
        this.maNV = maNV;
        this.tongTien = tongTien;
        this.maNCC = maNCC;
        this.trangThaiXuLy = trangThaiXuLy;
        this.ghiChu = ghiChu;
    }

    public String getMaPN() {
        return maPN;
    }

    public void setMaPN(String maLoSP) {
        this.maPN = maLoSP;
    }

    public String getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    public String getTrangThaiXuLy() {
        return trangThaiXuLy;
    }

    public void setTrangThaiXuLy(String trangThaiXuLy) {
        this.trangThaiXuLy = trangThaiXuLy;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public ArrayList<LoSanPham> getListLoSanPham() {
        return listLoSanPham;
    }

    public void setListLoSanPham(ArrayList<LoSanPham> listLoSanPham) {
        this.listLoSanPham = listLoSanPham;
    }
}
