package dto;

public class PhanQuyen {
    private String maQuyen;
    private String maNQ;

    public PhanQuyen() {
    }

    public PhanQuyen(String maQuyen, String maNQ) {
        this.maQuyen = maQuyen;
        this.maNQ = maNQ;
    }

    public String getMaQuyen() {
        return maQuyen;
    }

    public void setMaQuyen(String maQuyen) {
        this.maQuyen = maQuyen;
    }

    public String getMaNQ() {
        return maNQ;
    }

    public void setMaNQ(String maNQ) {
        this.maNQ = maNQ;
    }
}
