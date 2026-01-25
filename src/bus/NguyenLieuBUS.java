package bus;

import java.util.ArrayList;

import dao.NguyenLieuDAO;
import dto.NguyenLieu;

public class NguyenLieuBUS {
    private NguyenLieuDAO nguyenLieuDAO = new NguyenLieuDAO();
    private NhaCungCapBUS nhaCungCapBUS = new NhaCungCapBUS();
    public NguyenLieu timNguyenLieu(String ma){
        return nguyenLieuDAO.timNguyenLieu(ma);
    }

    public  ArrayList<NguyenLieu> layListNguyenLieu(){
        ArrayList<NguyenLieu> listNguyenLieu = nguyenLieuDAO.layListNguyenLieu();
        for (NguyenLieu nguyenLieu :  listNguyenLieu){
            nguyenLieu.setNhaCungCap(nhaCungCapBUS.timNhaCungCap(nguyenLieu.getNhaCungCap().getMaNCC()));
        }
        return listNguyenLieu;
    }
}
