package bus;

import java.util.ArrayList;

import dao.PhieuNhapSanPhamDAO;
import dto.PhieuNhapSanPham;

public class PhieuNhapSanPhamBUS {
    private static PhieuNhapSanPhamBUS phieuNhapSanPhamBUS = null;
    private PhieuNhapSanPhamDAO phieuNhapSanPhamDAO = new PhieuNhapSanPhamDAO();

    public static PhieuNhapSanPhamBUS getPhieuNhapSanPhamBUS() {
        if (phieuNhapSanPhamBUS == null) {
            phieuNhapSanPhamBUS = new PhieuNhapSanPhamBUS();
        }
        return phieuNhapSanPhamBUS;
    }

    private ArrayList<PhieuNhapSanPham> listPhieuNhapSanPham = null;
    private boolean canUpdate;

    public PhieuNhapSanPhamBUS() {
        khoiTao();
    }

    public void khoiTao() {
        listPhieuNhapSanPham = phieuNhapSanPhamDAO.layListPhieuNhapSanPham();
        canUpdate = false;
    }

    public ArrayList<PhieuNhapSanPham> layListPhieuNhapSanPham() {
        if (canUpdate || listPhieuNhapSanPham == null) {
            khoiTao();
        }
        return listPhieuNhapSanPham;
    }
}
