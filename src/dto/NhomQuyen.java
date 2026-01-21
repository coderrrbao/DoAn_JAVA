package dto;

import java.util.ArrayList;

public class NhomQuyen {
    private String maNQ;
    private String tenNhomQuyen;
    private ArrayList<Quyen> listQuyen = new ArrayList<>();

    public NhomQuyen() {
    }

    public NhomQuyen(String maNQ, String tenPhanQuyen) {
        this.maNQ = maNQ;
        this.tenNhomQuyen = tenPhanQuyen;
    }

    public String getMaNQ() {
        return maNQ;
    }

    public void setMaNQ(String maNQ) {
        this.maNQ = maNQ;
    }

    public String getTenNhomQuyen() {
        return tenNhomQuyen;
    }

    public void setTenNhomQuyen(String tenPhanQuyen) {
        this.tenNhomQuyen = tenPhanQuyen;
    }

    public ArrayList<Quyen> getListQuyen() {
        return listQuyen;
    }

    public void setListQuyen(ArrayList<Quyen> listQuyen) {
        this.listQuyen = listQuyen;
    }
}
