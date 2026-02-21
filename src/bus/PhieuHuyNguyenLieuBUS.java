package bus;

import dao.PhieuHuyNguyenLieuDAO;
import dto.PhieuHuyNguyenLieu;
import java.util.ArrayList;

public class PhieuHuyNguyenLieuBUS {
  private static PhieuHuyNguyenLieuBUS instance;
  private PhieuHuyNguyenLieuDAO dao = new PhieuHuyNguyenLieuDAO();
  private ArrayList<PhieuHuyNguyenLieu> listPhieuHuy = null;
  private boolean canUpdate = true;

  public static PhieuHuyNguyenLieuBUS getPhieuHuyNguyenLieuBUS() {
    if (instance == null) instance = new PhieuHuyNguyenLieuBUS();
    return instance;
  }

  public void khoiTao() {
    listPhieuHuy = dao.layListPhieuHuy();
    canUpdate = false;
  }

  public ArrayList<PhieuHuyNguyenLieu> layListPhieuHuy() {
    if (canUpdate || listPhieuHuy == null) khoiTao();
    return listPhieuHuy;
  }

  public boolean thucHienHuy(String maNV, String lyDo, Object[][] data) {
    boolean check = dao.luuPhieuHuy(maNV, lyDo, data);
    if (check) canUpdate = true;
    return check;
  }
}
