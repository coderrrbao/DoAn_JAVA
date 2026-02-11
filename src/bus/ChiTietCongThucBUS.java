package bus;

import java.sql.Connection;
import java.util.ArrayList;

import dao.ChiTietCongThucDAO;
import dto.ChiTietCongThuc;
import dto.NguyenLieu;

public class ChiTietCongThucBUS {

    private ChiTietCongThucDAO chiTietCongThucDAO = new ChiTietCongThucDAO();
    private NguyenLieuBUS nguyenLieuBUS = new NguyenLieuBUS();
    
    public ArrayList<ChiTietCongThuc> laylistCTCTbangMaCT(String maCT) {
        ArrayList<ChiTietCongThuc> listChiTietCongThuc = chiTietCongThucDAO.laylistChiTietCongThuc(maCT);
        
        for (ChiTietCongThuc chiTietCongThuc : listChiTietCongThuc) {
            NguyenLieu nguyenLieu = nguyenLieuBUS.timNguyenLieu(chiTietCongThuc.getNguyenLieu().getMaNL());
            chiTietCongThuc.setNguyenLieu(nguyenLieu);
        }
        return listChiTietCongThuc;
    }
    public Boolean themCTCT(ChiTietCongThuc chiTietCongThuc,Connection conn){
        return chiTietCongThucDAO.themCTCT(chiTietCongThuc,conn);
    }
    public Boolean xoaCTCT(ChiTietCongThuc chiTietCongThuc,Connection conn){
        return chiTietCongThucDAO.xoaCTCT(chiTietCongThuc,conn);
    }
    public boolean capNhapChiTietCongThuc(ChiTietCongThuc chiTietCongThuc,Connection conn){
        return chiTietCongThucDAO.capNhapChiTietCongThuc(chiTietCongThuc,conn);
    }
}
