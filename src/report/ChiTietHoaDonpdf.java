package report;

public class ChiTietHoaDonpdf {

    private String tenSP;
    private String giaSP;
    private String slSP;
    private String tongTienSP;

    public ChiTietHoaDonpdf(String tenSP, String giaSP, String slSP, String tongTienSP) {
        this.tenSP = tenSP;
        this.giaSP = giaSP;
        this.slSP = slSP;
        this.tongTienSP = tongTienSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public String getGiaSP() {
        return giaSP;
    }

    public String getSlSP() {
        return slSP;
    }

    public String getTongTienSP() {
        return tongTienSP;
    }
}
