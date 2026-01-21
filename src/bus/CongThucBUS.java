package bus;

import java.util.ArrayList;
import dao.CongThucDAO;
import dto.ChiTietCongThuc;
import dto.CongThuc;
import dto.NguyenLieu;

public class CongThucBUS {
    private NguyenLieuBUS nguyenLieuBUS = new NguyenLieuBUS();
    private CongThucDAO congThucDAO = new CongThucDAO();
    private ChiTietCongThucBUS chiTietCongThucBUS = new ChiTietCongThucBUS();

    public NguyenLieu timNguyenLieu(String maNL) {
        return nguyenLieuBUS.timNguyenLieu(maNL);
    }

    public CongThuc timCongThucChoSP(String ma) {
        CongThuc congThuc = congThucDAO.timCongThuc(ma);
        if (congThuc == null) {
            return null;
        }
        ArrayList<ChiTietCongThuc> listChiTietCongThuc = chiTietCongThucBUS.laylistCTCTbangMaCT(congThuc.getMaCT());
        congThuc.setListChiTietCongThuc(listChiTietCongThuc);
        return congThuc;
    }
}
