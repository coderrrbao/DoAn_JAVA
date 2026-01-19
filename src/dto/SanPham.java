package dto;

public class SanPham {
    private String maSP;
    private String tenSP;
    private DanhMuc danhMuc;
    private long giaNhap;
    private long giaBan;
    private NhaCungCap nhaCungCap;
    private int soLuongTon;
    private String loaiNuoc;
    private String anh;
    private int theTich;
    private int mucCanhBao;
    private boolean trangThai;

    public SanPham() {
    }

    public SanPham(String maSP, String tenSP, DanhMuc danhMuc, long giaNhap, long giaBan,
            NhaCungCap nhaCungCap, int soLuongTon, String loaiNuoc, String anh,
            int theTich, int mucCanhBao, boolean trangThai) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.danhMuc = danhMuc;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.nhaCungCap = nhaCungCap;
        this.soLuongTon = soLuongTon;
        this.loaiNuoc = loaiNuoc;
        this.anh = anh;
        this.theTich = theTich;
        this.mucCanhBao = mucCanhBao;
        this.trangThai = trangThai;
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

    public long getGiaNhap() {
        return giaNhap;
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

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public String getTenSP() {
        return tenSP;
    }

    public boolean getTrangThai() {
        return trangThai;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public void setGiaBan(long giaBan) {
        this.giaBan = giaBan;
    }

    public void setGiaNhap(long giaNhap) {
        this.giaNhap = giaNhap;
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

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
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
}