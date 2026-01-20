package bus;

import java.util.ArrayList;

import dao.CongThucDAO;
import dto.ChiTietCongThuc;
import dto.CongThuc;
import dto.NguyenLieu;

public class CongThucBUS {
    private NguyenLieuBUS nguyenLieuBUS = new NguyenLieuBUS();
    private CongThucDAO congThucDAO = new CongThucDAO();

    public NguyenLieu timNguyenLieu(String maNL) {
        return nguyenLieuBUS.timNguyenLieu(maNL);
    }
    public CongThuc timCongThuc(String ma){
        
        CongThuc congThuc = congThucDAO.timCongThuc(ma);
        if (congThuc==null){
            return null;
        }
        ArrayList<ChiTietCongThuc> listChiTietCongThuc = congThucDAO.laylistChiTietCongThuc(ma);
        for (ChiTietCongThuc chiTietCongThuc : listChiTietCongThuc){
            NguyenLieu nguyenLieu = nguyenLieuBUS.timNguyenLieu(chiTietCongThuc.getMaCTCT());
            chiTietCongThuc.setNguyenLieu(nguyenLieu);
        }
        congThuc.setListChiTietCongThuc(listChiTietCongThuc);
        return congThuc;
    }
}


