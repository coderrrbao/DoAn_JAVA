package dto;

import java.sql.Date;
import java.util.ArrayList;

public class PhieuHuySanPham {
  private String maPH;

  private Date ngayHuy;
  private String maNV;
  private String lyDo;
  private double tongGiaTri;
  private String trangThai;
  private java.util.ArrayList<LoSanPham> listLoSanPhamHuy = new ArrayList<>();

  public PhieuHuySanPham(
      String maPH,

      Date ngayHuy,
      String maNV,
      String lyDo,
      double tongGiaTri,
      String trangThai) {
    this.maPH = maPH;

    this.ngayHuy = ngayHuy;
    this.maNV = maNV;
    this.lyDo = lyDo;
    this.tongGiaTri = tongGiaTri;
    this.trangThai = trangThai;
  }

  public PhieuHuySanPham() {
    this.maPH = "";

    this.ngayHuy = null;
    this.maNV = "";
    this.lyDo = "";
    this.tongGiaTri = 0;
    this.trangThai = "";
    this.listLoSanPhamHuy = new ArrayList<>();
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

  public double getTongGiaTri() {
    return tongGiaTri;
  }

  public void setTongGiaTri(double tongGiaTri) {
    this.tongGiaTri = tongGiaTri;
  }

  public String getTrangThaiXuLy() {
    return trangThai;
  }

  public void setTrangThaiXuLy(String trangThai) {
    this.trangThai = trangThai;
  }

  public ArrayList<LoSanPham> getListLoSanPhamHuy() {
    return listLoSanPhamHuy;
  }

  public void setListLoSanPhamHuy(ArrayList<LoSanPham> list) {
    this.listLoSanPhamHuy = list;
  }
}
