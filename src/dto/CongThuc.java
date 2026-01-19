package dto;

public class CongThuc {

    private String maCT;
    private String maSP;
    private boolean trangThai;

    public CongThuc() {
    }

    public CongThuc(String maCT, String maSP) {
        this.maCT = maCT;
        this.maSP = maSP;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public boolean getTrangThai() {
        return trangThai;
    }

    public String getMaCT() {
        return maCT;
    }

    public void setMaCT(String maCT) {
        this.maCT = maCT;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }
}
