package dto;

import java.sql.Date;
import java.util.ArrayList;

public class PhieuHuyNguyenLieu {
  private String maPH;

  private Date ngayHuy;
  private String maNV;
  private String lyDo;
  private double tongTien;
  private String trangThaiXuLy;
  private ArrayList<LoNguyenLieu> listLoNguyenLieuHuy = new ArrayList<>();

  public PhieuHuyNguyenLieu() {
  }

  public PhieuHuyNguyenLieu(
      String maPH,

      Date ngayHuy,
      String maNV,
      String lyDo,
      double tongTien,
      String trangThai) {
    this.maPH = maPH;

    this.ngayHuy = ngayHuy;
    this.maNV = maNV;
    this.lyDo = lyDo;
    this.tongTien = tongTien;
    this.trangThaiXuLy = trangThai;
  }

  public String getMaPH() {
    return maPH;
  }

  public void setMaPH(String maPH) {
    this.maPH = maPH;
  }

  public Date getNgayHuy() {
    return ngayHuy;
  }

  public void setNgayHuy(Date ngayHuy) {
    this.ngayHuy = ngayHuy;
  }

  public String getMaNV() {
    return maNV;
  }

  public void setMaNV(String maNV) {
    this.maNV = maNV;
  }

  public String getLyDo() {
    return lyDo;
  }

  public void setLyDo(String lyDo) {
    this.lyDo = lyDo;
  }

  public double getTongTien() {
    return tongTien;
  }

  public void setTongTien(double tongTien) {
    this.tongTien = tongTien;
  }

  public String getTrangThaiXuLy() {
    return trangThaiXuLy;
  }

  public void setTrangThaiXuLy(String trangThaiXuLy) {
    this.trangThaiXuLy = trangThaiXuLy;
  }

  public ArrayList<LoNguyenLieu> getListLoNguyenLieuHuy() {
    return listLoNguyenLieuHuy;
  }

  public void setListLoNguyenLieuHuy(ArrayList<LoNguyenLieu> list) {
    this.listLoNguyenLieuHuy = list;
  }
}
