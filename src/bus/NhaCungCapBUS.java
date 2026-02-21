package bus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dao.NhaCungCapDAO;
import dto.ChiTietNhaCungCap;
import dto.NguyenLieu;
import dto.NhaCungCap;
import dto.SanPham;

public class NhaCungCapBUS {

    private static NhaCungCapBUS nhaCungCapBUS = null;

    public static NhaCungCapBUS getNhaCungCapBUS() {
        if (nhaCungCapBUS == null) {
            nhaCungCapBUS = new NhaCungCapBUS();
        }
        return nhaCungCapBUS;
    }

    private NhaCungCapDAO nhaCungCapDAO = new NhaCungCapDAO();
    private ArrayList<NhaCungCap> listNhaCungCap;
    private boolean canUpdate = false;

    public void khoitao() {
        listNhaCungCap = nhaCungCapDAO.layListNhaCungCap();
        ArrayList<ChiTietNhaCungCap> listChiTietNCC = ChiTietNhaCungCapBUS.getChiTietNhaCungCapBUS()
                .layListChiTietNhaCungCap();

        Map<String, ArrayList<ChiTietNhaCungCap>> map = new HashMap<>();
        for (NhaCungCap nhaCungCap : listNhaCungCap) {
            map.put(nhaCungCap.getMaNCC(), new ArrayList<ChiTietNhaCungCap>());
        }
        for (ChiTietNhaCungCap chiTietNhaCungCap : listChiTietNCC) {
            ArrayList<ChiTietNhaCungCap> listCTNCC = map.get(chiTietNhaCungCap.getMaNCC());
            if (listCTNCC != null) {
                listCTNCC.add(chiTietNhaCungCap);
                map.put(chiTietNhaCungCap.getMaNCC(), listCTNCC);
            }
        }

        for (NhaCungCap nhaCungCap : listNhaCungCap) {
            for (ChiTietNhaCungCap chiTietNhaCungCap : map.get(nhaCungCap.getMaNCC())) {
                nhaCungCap.themChiTietNhaCungCap(chiTietNhaCungCap);
            }

        }
    }

    public ArrayList<NhaCungCap> laylistNhaCungCap() {
        if (canUpdate || listNhaCungCap == null) {
            khoitao();
            canUpdate = false;
        }
        return listNhaCungCap;
    }

    public NhaCungCap timNhaCungCap(String ma) {
        if (canUpdate || listNhaCungCap == null) {
            khoitao();
            canUpdate = false;
        }
        for (NhaCungCap ncc : listNhaCungCap) {
            if (ncc.getMaNCC().equals(ma)) {
                return ncc;
            }
        }
        return null;
    }

    public NhaCungCap timNhaCungCapTheoTen(String ten) {
        if (canUpdate || listNhaCungCap == null) {
            khoitao();
            canUpdate = false;
        }
        for (NhaCungCap ncc : listNhaCungCap) {
            if (ncc.getTenNCC().equalsIgnoreCase(ten)) {
                return ncc;
            }
        }
        return null;
    }

    public ArrayList<String> layLuaChonNCC() {
        if (canUpdate || listNhaCungCap == null) {
            khoitao();
            canUpdate = false;
        }
        ArrayList<String> list = new ArrayList<>();
        for (NhaCungCap nhaCungCap : listNhaCungCap) {
            list.add(nhaCungCap.getTenNCC());
        }
        return list;
    }

    public ArrayList<String> layLuaChonNCCNguyenLieu() {
        if (canUpdate || listNhaCungCap == null) {
            khoitao();
            canUpdate = false;
        }
        ArrayList<String> list = new ArrayList<>();
        for (NhaCungCap nhaCungCap : listNhaCungCap) {
            if (nhaCungCap.getCungCapNL()) {
                list.add(nhaCungCap.getTenNCC());
            }

        }
        return list;
    }

    public ArrayList<String> layLuaChonNCCSanPham() {
        if (canUpdate || listNhaCungCap == null) {
            khoitao();
            canUpdate = false;
        }
        ArrayList<String> list = new ArrayList<>();
        for (NhaCungCap nhaCungCap : listNhaCungCap) {
            if (nhaCungCap.getCungCapSP()) {
                list.add(nhaCungCap.getTenNCC());
            }

        }
        return list;
    }

    public void danhDauCanCapNhat() {
        this.canUpdate = true;
    }

}