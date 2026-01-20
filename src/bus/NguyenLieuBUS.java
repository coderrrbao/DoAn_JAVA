package bus;

import dao.NguyenLieuDAO;
import dto.NguyenLieu;

public class NguyenLieuBUS {
    private NguyenLieuDAO nguyenLieuDAO = new NguyenLieuDAO();
    public NguyenLieu timNguyenLieu(String ma){
        return nguyenLieuDAO.timNguyenLieu(ma);
    }
}
