package dto;

import java.util.ArrayList;

public class NhaCungCap {
  private String maNCC;
  private String tenNCC;
  private String soDienThoai;
  private String diaChi;

  private ArrayList<SanPham> listSanPhamCungCap = null;
  private ArrayList<NguyenLieu> listNguyenLieuCungCap = null;

  public NhaCungCap() {}

  public NhaCungCap(String maNCC, String tenNCC, String soDienThoai, String diaChi) {
    this.maNCC = maNCC;
    this.tenNCC = tenNCC;
    this.soDienThoai = soDienThoai;
    this.diaChi = diaChi;
  }

  public String getMaNCC() {
    return maNCC;
  }

  public String getTenNCC() {
    return tenNCC;
  }

  public String getSoDienThoai() {
    return soDienThoai;
  }

  public String getDiaChi() {
    return diaChi;
  }

  public void setMaNCC(String maNCC) {
    this.maNCC = maNCC;
  }

  public void setTenNCC(String tenNCC) {
    this.tenNCC = tenNCC;
  }

  public void setSoDienThoai(String soDienThoai) {
    this.soDienThoai = soDienThoai;
  }

  public void setDiaChi(String diaChi) {
    this.diaChi = diaChi;
  }

  public void themSanPham(SanPham sanPham) {
    if (sanPham == null) {
      return;
    }
    if (listSanPhamCungCap == null) {
      listSanPhamCungCap = new ArrayList<>();
    }
    listSanPhamCungCap.add(sanPham);
  }

  public void themNguyenLieu(NguyenLieu nguyenLieu) {
    if (nguyenLieu == null) {
      return;
    }
    if (listNguyenLieuCungCap == null) {
      listNguyenLieuCungCap = new ArrayList<>();
    }
    listNguyenLieuCungCap.add(nguyenLieu);
  }

  public ArrayList<NguyenLieu> getListNguyenLieuCungCap() {
    return listNguyenLieuCungCap;
  }

  public ArrayList<SanPham> getListSanPhamCungCap() {
    return listSanPhamCungCap;
  }

  @Override
  public String toString() {
    return this.tenNCC;
  }
}
