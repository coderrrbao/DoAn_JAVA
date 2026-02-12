package bus;

import java.util.ArrayList;

import dao.PhieuNhapNguyenLieuDAO;
import dto.PhieuNhapNguyenLieu;

public class PhieuNhapNguyenLieuBUS {

    private static PhieuNhapNguyenLieuBUS phieuNhapNguyenLieuBUS = null;
    private PhieuNhapNguyenLieuDAO phieuNhapNguyenLieuDAO = new PhieuNhapNguyenLieuDAO();

    public static PhieuNhapNguyenLieuBUS getPhieuNhapNguyenLieuBUS() {
        if (phieuNhapNguyenLieuBUS == null) {
            phieuNhapNguyenLieuBUS = new PhieuNhapNguyenLieuBUS();
        }
        return phieuNhapNguyenLieuBUS;
    }

    private ArrayList<PhieuNhapNguyenLieu> listPhieuNhapNguyenLieu = null;
    private boolean canUpdate;

    public PhieuNhapNguyenLieuBUS() {
        khoiTao();
    }

    public void khoiTao() {
        listPhieuNhapNguyenLieu = phieuNhapNguyenLieuDAO.layListPhieuNhapNguyenLieu();
        canUpdate = false;
    }

    public ArrayList<PhieuNhapNguyenLieu> layListPhieuNhapNguyenLieu() {
        if (canUpdate || listPhieuNhapNguyenLieu == null) {
            khoiTao();
        }
        return listPhieuNhapNguyenLieu;
    }
}