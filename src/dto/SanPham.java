package dto;

import java.util.ArrayList;

public class SanPham {
    private String maSP;
    private String tenSP;
    private DanhMuc danhMuc;
    private long giaBan;
    private NhaCungCap nhaCungCap;
    private String loaiNuoc;
    private String anh;
    private int theTich;
    private int mucCanhBao;
    private boolean trangThai;
    private ArrayList<Size> listSize;
    private CongThuc congThuc;
    private String trangThaiXuLy;

    public SanPham() {
    }

    public SanPham(String maSP, String tenSP, DanhMuc danhMuc, long giaBan,
            NhaCungCap nhaCungCap, String loaiNuoc, String anh,
            int theTich, int mucCanhBao, String trangThaiXuLi, boolean trangThai) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.danhMuc = danhMuc;
        this.giaBan = giaBan;
        this.nhaCungCap = nhaCungCap;
        this.loaiNuoc = loaiNuoc;
        this.anh = anh;
        this.theTich = theTich;
        this.mucCanhBao = mucCanhBao;
        this.trangThai = trangThai;
        this.trangThaiXuLy = trangThaiXuLi;
        listSize = new ArrayList<>();
    }

    public SanPham(String maSP, String tenSP, DanhMuc danhMuc, long giaBan,
            NhaCungCap nhaCungCap, String loaiNuoc, String anh,
            int theTich, int mucCanhBao, CongThuc congThuc, String trangThaiXuLi, boolean trangThai) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.danhMuc = danhMuc;
        this.giaBan = giaBan;
        this.nhaCungCap = nhaCungCap;
        this.loaiNuoc = loaiNuoc;
        this.anh = anh;
        this.theTich = theTich;
        this.mucCanhBao = mucCanhBao;
        this.trangThai = trangThai;
        this.congThuc = congThuc;
        this.trangThaiXuLy = trangThaiXuLi;
        listSize = new ArrayList<>();
    }

    public int getTheTich() {
        return theTich;
    }

    public String getAnh() {
        return anh;
    }

    public long getGiaBan() {
        return giaBan;
    }

    public int getMucCanhBao() {
        return mucCanhBao;
    }

    public String getLoaiNuoc() {
        return loaiNuoc;
    }

    public String getMaSP() {
        return maSP;
    }

    public DanhMuc getDanhMuc() {
        return danhMuc;
    }

    public NhaCungCap getNhaCungCap() {
        return nhaCungCap;
    }

    public String getTenSP() {
        return tenSP;
    }

    public boolean getTrangThai() {
        return trangThai;
    }

    public ArrayList<Size> getListSize() {
        return listSize;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public void setGiaBan(long giaBan) {
        this.giaBan = giaBan;
    }

    public void setLoaiNuoc(String loaiNuoc) {
        this.loaiNuoc = loaiNuoc;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public void setDanhMuc(DanhMuc danhMuc) {
        this.danhMuc = danhMuc;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public void setTheTich(int theTich) {
        this.theTich = theTich;
    }

    public void setMucCanhBao(int mucCanhBao) {
        this.mucCanhBao = mucCanhBao;
    }

    public void setListSize(ArrayList<Size> listSize) {
        this.listSize = listSize;
    }

    // Thêm size vào list
    public void addSize(Size size) {
        if (size != null) {
            listSize.add(size);
        }
    }

    // Xóa size khỏi list
    public void removeSize(Size size) {
        if (size != null) {
            listSize.remove(size);
        }
    }

    // Xóa size theo index
    public void removeSizeAt(int index) {
        if (index >= 0 && index < listSize.size()) {
            listSize.remove(index);
        }
    }

    // Xóa size theo mã size
    public void removeSizeByMaSize(String maSize) {
        listSize.removeIf(size -> size.getMaSize().equals(maSize));
    }

    public CongThuc getCongThuc() {
        return congThuc;
    }

    public void setCongThuc(CongThuc congThuc) {
        this.congThuc = congThuc;
    }

    public String getTrangThaiXuLy() {
        return trangThaiXuLy;
    }

    public void setTrangThaiXuLy(String trangThaiXuLi) {
        this.trangThaiXuLy = trangThaiXuLi;
    }
}