package bus;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dao.ChiTietCongThucDAO;
import dto.ChiTietCongThuc;
import dto.NguyenLieu;

public class ChiTietCongThucBUS {

    private static ChiTietCongThucBUS instance;

    private final ChiTietCongThucDAO chiTietCongThucDAO = new ChiTietCongThucDAO();
    private final NguyenLieuBUS nguyenLieuBUS = NguyenLieuBUS.getNguyenLieuBUS();

    private Map<String, ArrayList<ChiTietCongThuc>> cacheCTCT = new HashMap<>();
    private boolean canUpdate = false;

    private ChiTietCongThucBUS() {
        khoiTao();
    }

    public static ChiTietCongThucBUS getInstance() {
        if (instance == null) {
            instance = new ChiTietCongThucBUS();
        }
        return instance;
    }

    public void khoiTao() {
        lamMoiCache();
        ArrayList<ChiTietCongThuc> listChiTietCongThuc = chiTietCongThucDAO.laylistChiTietCongThuc();

        for (ChiTietCongThuc chiTietCongThuc : listChiTietCongThuc) {
            NguyenLieu nguyenLieu = nguyenLieuBUS.timNguyenLieu(chiTietCongThuc.getNguyenLieu().getMaNL());
            chiTietCongThuc.setNguyenLieu(nguyenLieu);
        }

        for (ChiTietCongThuc chiTietCongThuc : listChiTietCongThuc) {
            ArrayList<ChiTietCongThuc> list = new ArrayList<>();
            if (cacheCTCT.containsKey(chiTietCongThuc.getMaCT())) {
                list = cacheCTCT.get(chiTietCongThuc.getMaCT());
                list.add(chiTietCongThuc);
            } else {
                list.add(chiTietCongThuc);
            }
            cacheCTCT.put(chiTietCongThuc.getMaCT(), list);
        }

        if (canUpdate)
            danhDauCanCapNhat();
    }

    public ArrayList<ChiTietCongThuc> laylistCTCTbangMaCT(String maCT) {
        if (canUpdate || cacheCTCT == null) {
            khoiTao();
            canUpdate = false;
        }

        return cacheCTCT.get(maCT);
    }

    public Boolean themCTCT(ChiTietCongThuc chiTietCongThuc, Connection conn) {
        if (chiTietCongThucDAO.themCTCT(chiTietCongThuc, conn)) {
            danhDauCanCapNhat();
            return true;
        }
        return false;
    }

    public Boolean xoaCTCT(ChiTietCongThuc chiTietCongThuc, Connection conn) {
        if (chiTietCongThucDAO.xoaCTCT(chiTietCongThuc, conn)) {
            danhDauCanCapNhat();
            return true;
        }
        return false;
    }

    public boolean capNhapChiTietCongThuc(ChiTietCongThuc chiTietCongThuc, Connection conn) {
        if (chiTietCongThucDAO.capNhapChiTietCongThuc(chiTietCongThuc, conn)) {
            danhDauCanCapNhat();
            return true;
        }
        return false;
    }

    public void lamMoiCache() {
        cacheCTCT.clear();
    }

    private void danhDauCanCapNhat() {
        this.canUpdate = true;
        CongThucBUS.getCongThucBUS().setCanUpdate(true);
    }
}