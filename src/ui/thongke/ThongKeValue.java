package ui.thongke;

public class ThongKeValue {
    private double tongTien;
    private String thoiGian;
    private String loai = "VND";

    public String getLoai() {
        return loai;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
}
