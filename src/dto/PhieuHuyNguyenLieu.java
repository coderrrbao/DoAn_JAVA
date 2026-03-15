package dto;

import java.sql.Date;
import java.util.ArrayList;

public class PhieuHuyNguyenLieu {
    private String maPH;
    private Date ngayHuy;
    private String maNV;
    private String maNVXacNhan;
    private String lyDo;
    private double tongTien;
    private String trangThaiXuLy;

    private ArrayList<ChiTietPhieuHuyNguyenLieu> listChiTiet = new ArrayList<>();

    public PhieuHuyNguyenLieu() {
    }

    public String getMaPH() {
        return maPH;
    }

    public void setMaPH(String maPH) {
        this.maPH = maPH;
    }

    public String getMaNVXacNhan() {
        return maNVXacNhan;
    }

    public void setMaNVXacNhan(String maNVXacNhan) {
        this.maNVXacNhan = maNVXacNhan;
    }

    public ArrayList<ChiTietPhieuHuyNguyenLieu> getListChiTiet() {
        return listChiTiet;
    }

    public void setListChiTiet(ArrayList<ChiTietPhieuHuyNguyenLieu> listChiTiet) {
        this.listChiTiet = listChiTiet;
    }

    public String getLyDo() {
        return lyDo;
    }

    public String getMaNV() {
        return maNV;
    }

    public Date getNgayHuy() {
        return ngayHuy;
    }

    public double getTongTien() {
        return tongTien;
    }

    public String getTrangThaiXuLy() {
        return trangThaiXuLy;
    }

    public void setLyDo(String lyDo) {
        this.lyDo = lyDo;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public void setNgayHuy(Date ngayHuy) {
        this.ngayHuy = ngayHuy;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public void setTrangThaiXuLy(String trangThaiXuLy) {
        this.trangThaiXuLy = trangThaiXuLy;
    }
}