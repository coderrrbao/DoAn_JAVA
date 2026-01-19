package dto;

public class NhomQuyen {
    private String maNQ;
    private String tenPhanQuyen;

    public NhomQuyen() {
    }

    public NhomQuyen(String maNQ, String tenPhanQuyen) {
        this.maNQ = maNQ;
        this.tenPhanQuyen = tenPhanQuyen;
    }

    public String getMaNQ() {
        return maNQ;
    }

    public void setMaNQ(String maNQ) {
        this.maNQ = maNQ;
    }

    public String getTenPhanQuyen() {
        return tenPhanQuyen;
    }

    public void setTenPhanQuyen(String tenPhanQuyen) {
        this.tenPhanQuyen = tenPhanQuyen;
    }
}
