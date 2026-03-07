package dto;

import java.util.ArrayList;

import util.ExcelExport;

public class NhaCungCap implements ExcelExport {
  private String maNCC;
  private String tenNCC;
  private String soDienThoai;
  private String diaChi;
  private boolean cungCapNL = false;
  private boolean cungCapSP = false;

  private ArrayList<ChiTietNhaCungCap> listChiTietNhaCungCap = new ArrayList<>();

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
    if(cungCapNL){
      loai = "Nguyên Liệu";
    }else if(cungCapSP){

    }
    return new Object[] {
        maNCC, tenNCC, cungCapNL ? "Nguyên Liệu" : "Sản Phẩm", soDienThoai, diaChi
    };
  }

  public NhaCungCap() {
  }

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

  public void setCungCapNL(boolean cungCapNL) {
    this.cungCapNL = cungCapNL;
  }

  public void setCungCapSP(boolean cungCapSP) {
    this.cungCapSP = cungCapSP;
  }

  public boolean getCungCapNL() {
    return cungCapNL;
  }

  public boolean getCungCapSP() {
    return cungCapSP;
  }

  public boolean themChiTietNhaCungCap(ChiTietNhaCungCap chiTietNhaCungCap) {
    if (chiTietNhaCungCap == null) {
      return false;
    }
    listChiTietNhaCungCap.add(chiTietNhaCungCap);
    if (chiTietNhaCungCap.getLoaiDoiTuong().equals("Sản phẩm")) {
      cungCapSP = true;
    }
    if (chiTietNhaCungCap.getLoaiDoiTuong().equals("Nguyên liệu")) {
      cungCapNL = true;
    }
    return true;
  }

  public ArrayList<ChiTietNhaCungCap> getListChiTietNhaCungCap() {
    return listChiTietNhaCungCap;
  }

  public void setListChiTietNhaCungCap(ArrayList<ChiTietNhaCungCap> listChiTietNhaCungCap) {
    this.listChiTietNhaCungCap = listChiTietNhaCungCap;
  }

  @Override
  public String toString() {
    return this.tenNCC;
  }

}
