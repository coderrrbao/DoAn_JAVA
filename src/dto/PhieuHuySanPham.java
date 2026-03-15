package dto;

import java.sql.Date;
import java.util.ArrayList;

public class PhieuHuySanPham {
  private String maPH;
  private Date ngayHuy;
  private String maNV;
  private String maNVXacNhan;
  private String lyDo;
  private double tongGiaTri;
  private String trangThaiXuLy;

  private ArrayList<ChiTietPhieuHuySanPham> listChiTiet = new ArrayList<>();

  public PhieuHuySanPham() {
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

  public String getMaNVXacNhan() {
    return maNVXacNhan;
  }

  public void setMaNVXacNhan(String maNVXacNhan) {
    this.maNVXacNhan = maNVXacNhan;
  }

  public String getLyDo() {
    return lyDo;
  }

  public void setLyDo(String lyDo) {
    this.lyDo = lyDo;
  }

  public double getTongGiaTri() {
    return tongGiaTri;
  }

  public void setTongGiaTri(double tongGiaTri) {
    this.tongGiaTri = tongGiaTri;
  }

  public String getTrangThaiXuLy() {
    return trangThaiXuLy;
  }

  public void setTrangThaiXuLy(String trangThai) {
    this.trangThaiXuLy = trangThai;
  }

  public ArrayList<ChiTietPhieuHuySanPham> getListChiTiet() {
    return listChiTiet;
  }

  public void setListChiTiet(ArrayList<ChiTietPhieuHuySanPham> listChiTiet) {
    this.listChiTiet = listChiTiet;
  }
}