package dto;

import java.util.ArrayList;

import util.ExcelExport;

public class PhieuNhapNguyenLieu implements ExcelExport {
    private String maPN;
    private String ngayNhap;
    private String maNV;
    private double tongTien;
    private String maNCC;
    private String trangThaiXuLy;
    private String ghiChu;
    private ArrayList<LoNguyenLieu> listLoNguyenLieu = new ArrayList<>();

    private boolean trangThai;

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

        };
    }

    public PhieuNhapNguyenLieu() {
    }

    public PhieuNhapNguyenLieu(String maPNNL, String ngayNhap, String maNV, double tongTien,
            String maNCC,
            String trangThaiXuLy, String ghiChu, boolean trangThai) {
        this.ngayNhap = ngayNhap;
        this.maNV = maNV;
        this.tongTien = tongTien;
        this.maNCC = maNCC;
        this.trangThaiXuLy = trangThaiXuLy;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
        this.maPN = maPNNL;
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

    public boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaPN() {
        return maPN;
    }

    public void setMaPN(String maPNNL) {
        this.maPN = maPNNL;
    }

    public boolean themLoNguyenLieu(LoNguyenLieu loNguyenLieu) {
        if (loNguyenLieu == null) {
            return false;
        }
        return listLoNguyenLieu.add(loNguyenLieu);
    }

    public ArrayList<LoNguyenLieu> getListLoNguyenLieu() {
        return listLoNguyenLieu;
    }

    public void setListLoNguyenLieu(ArrayList<LoNguyenLieu> listLoNguyenLieu) {
        this.listLoNguyenLieu = listLoNguyenLieu;
    }
}