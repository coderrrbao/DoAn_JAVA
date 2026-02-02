package bus;

import java.util.ArrayList;

import dao.LoNguyenLieuDAO;
import dto.LoNguyenLieu;

public class LoNguyenLieuBUS {
    private LoNguyenLieuDAO loNguyenLieuDAO = new LoNguyenLieuDAO();

    public ArrayList<LoNguyenLieu> layListLoNguyenLieu() {
        return loNguyenLieuDAO.layListLoNguyenLieu();
    }
}
