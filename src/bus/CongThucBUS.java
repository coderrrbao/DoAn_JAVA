package bus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import dao.CongThucDAO;
import dto.ChiTietCongThuc;
import dto.CongThuc;

public class CongThucBUS {
    private static CongThucBUS instance;

    private final CongThucDAO congThucDAO = new CongThucDAO();
    private final ChiTietCongThucBUS chiTietCongThucBUS = new ChiTietCongThucBUS();
    private Map<String, CongThuc> cacheCongThuc = new HashMap<>();

    private CongThucBUS() {
        khoitao();
    }

    public static CongThucBUS getCongThucBUS() {
        if (instance == null) {
            instance = new CongThucBUS();
        }
        return instance;
    }

    private void khoitao() {
        ArrayList<CongThuc> listCongThuc = congThucDAO.layListCongThuc();
        for (CongThuc congThuc : listCongThuc) {
            congThuc.setListChiTietCongThuc(chiTietCongThucBUS.laylistCTCTbangMaCT(congThuc.getMaCT()));
            cacheCongThuc.put(congThuc.getMaSp(), congThuc);
        }
    }

    public CongThuc timCongThucChoSP(String maSP) {
        return cacheCongThuc.get(maSP);
    }

    public Boolean themCongThuc(CongThuc congThuc) {
        String maCTMoi = congThucDAO.layMaCongThucKhaDung();
        congThuc.setMaCT(maCTMoi);

        if (!congThucDAO.themCongThuc(congThuc)) {
            return false;
        }

        if (congThuc.getListChiTietCongThuc() != null) {
            for (ChiTietCongThuc ct : congThuc.getListChiTietCongThuc()) {
                ct.setMaCT(maCTMoi);
                if (!chiTietCongThucBUS.themCTCT(ct)) {
                    return false;
                }
            }
        }

        cacheCongThuc.put(congThuc.getMaSp(), congThuc);
        return true;
    }

    public void lamMoiMap() {
        cacheCongThuc.clear();
        khoitao();
    }
}