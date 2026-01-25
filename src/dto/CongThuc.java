package dto;

import java.util.ArrayList;

public class CongThuc {

    private String maCT;
    private ArrayList<ChiTietCongThuc> listChiTietCongThuc;
    private boolean trangThai;

    public CongThuc() {
        listChiTietCongThuc = new ArrayList<>();
    }

    public CongThuc(String maCT, ArrayList<ChiTietCongThuc> listChiTietCongThuc) {
        this.maCT = maCT;
        this.listChiTietCongThuc = listChiTietCongThuc;
    }

    public CongThuc(String maCT) {
        this.maCT = maCT;
        listChiTietCongThuc = new ArrayList<>();
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

    public ArrayList<ChiTietCongThuc> getListChiTietCongThuc() {
        return listChiTietCongThuc;
    }

    public void setListChiTietCongThuc(ArrayList<ChiTietCongThuc> listChiTietCongThuc) {
        this.listChiTietCongThuc = listChiTietCongThuc;
    }

}
