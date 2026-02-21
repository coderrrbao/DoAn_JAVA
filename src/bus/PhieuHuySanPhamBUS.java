package bus;

import dao.PhieuHuySanPhamDAO;
import dto.PhieuHuySanPham;
import java.util.ArrayList;

public class PhieuHuySanPhamBUS {
    private static PhieuHuySanPhamBUS instance;
    private PhieuHuySanPhamDAO dao = new PhieuHuySanPhamDAO();
    private ArrayList<PhieuHuySanPham> listPhieuHuy = null;
    private boolean canUpdate = true;

    public static PhieuHuySanPhamBUS getPhieuHuySanPhamBUS() {
        if (instance == null) instance = new PhieuHuySanPhamBUS();
        return instance;
    }

    public void khoiTao() {
        listPhieuHuy = dao.layListPhieuHuy();
        canUpdate = false;
    }

    public ArrayList<PhieuHuySanPham> layListPhieuHuy() {
        if (canUpdate || listPhieuHuy == null) khoiTao();
        return listPhieuHuy;
    }

    // Sửa lại tham số để nhận Object[][] từ Dialog
    public boolean thucHienHuy(String maNV, String lyDo, Object[][] data) {
        boolean check = dao.luuPhieuHuy(maNV, lyDo, data);
        if (check) canUpdate = true;
        return check;
    }
}