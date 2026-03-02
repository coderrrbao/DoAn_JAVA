package dto;

public class PhanQuyen {
    private String maNQ;    // Mã Nhóm Quyền (vd: NQ01)
    private String maQuyen; // Mã Quyền (vd: Q01)

    public PhanQuyen() {
    }

    public PhanQuyen(String maNQ, String maQuyen) {
        this.maNQ = maNQ;
        this.maQuyen = maQuyen;
    }

    public String getMaNQ() {
        return maNQ;
    }

    public void setMaNQ(String maNQ) {
        this.maNQ = maNQ;
    }

    public String getMaQuyen() {
        return maQuyen;
    }

    public void setMaQuyen(String maQuyen) {
        this.maQuyen = maQuyen;
    }
}