package bus;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import dao.CongThucDAO;
import dto.ChiTietCongThuc;
import dto.CongThuc;

public class CongThucBUS {
    private static CongThucBUS instance;

    private final CongThucDAO congThucDAO = new CongThucDAO();
    private final ChiTietCongThucBUS chiTietCongThucBUS = ChiTietCongThucBUS.getInstance();
    private Map<String, CongThuc> cacheCongThuc = new HashMap<>();

    private boolean canUpdate = false;

    private CongThucBUS() {
        khoitao();
    }

    public static CongThucBUS getCongThucBUS() {
        if (instance == null) {
            instance = new CongThucBUS();
        }
        return instance;
    }

    public void khoitao() {
        cacheCongThuc.clear();
        ArrayList<CongThuc> listCongThuc = congThucDAO.layListCongThuc();
        for (CongThuc congThuc : listCongThuc) {
            congThuc.setListChiTietCongThuc(chiTietCongThucBUS.laylistCTCTbangMaCT(congThuc.getMaCT()));
            cacheCongThuc.put(congThuc.getMaSp(), congThuc);
        }
        canUpdate = false;
    }

    public CongThuc timCongThucChoSP(String maSP) {

        if (canUpdate) {
            khoitao();
        }
        return cacheCongThuc.get(maSP);
    }

    public Boolean themCongThuc(CongThuc congThuc, Connection conn) {
        String maCTMoi = congThucDAO.layMaCongThucKhaDung(conn);
        congThuc.setMaCT(maCTMoi);

        if (!congThucDAO.themCongThuc(congThuc, conn)) {
            return false;
        }

        if (congThuc.getListChiTietCongThuc() != null) {
            for (ChiTietCongThuc ct : congThuc.getListChiTietCongThuc()) {
                ct.setMaCT(maCTMoi);
                if (!chiTietCongThucBUS.themCTCT(ct, conn)) {
                    return false;
                }
            }
        }

        this.canUpdate = true;
        return true;
    }

    public void setCanUpdate(boolean canUpdate) {
        this.canUpdate = canUpdate;
        SanPhamBUS.getSanPhamBUS().setCanUpdate(true);
    }
}