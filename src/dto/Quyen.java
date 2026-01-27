package dto;

public class Quyen {
    private String maQuyen;
    private String maNQ;
    private String tenQuyen;

    public Quyen() {
    }

    public Quyen(String maQuyen, String tenQuyen) {
        this.maQuyen = maQuyen;
        this.tenQuyen = tenQuyen;
    }

    public String getMaNQ(){
        return maNQ;
    }
    public void setMaNQ(String maNQ){
        this.maNQ = maNQ;
    }
    public String getMaQuyen() {
        return maQuyen;
    }

    public void setMaQuyen(String maQuyen) {
        this.maQuyen = maQuyen;
    }

    public String getTenQuyen() {
        return tenQuyen;
    }

    public void setTenQuyen(String tenQuyen) {
        this.tenQuyen = tenQuyen;
    }
}
