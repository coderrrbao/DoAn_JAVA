package bus;

import dao.PhieuHuySanPhamDAO;
import dao.conection.DBConnection;
import dto.PhieuHuySanPham;
import java.sql.Connection;
import java.sql.SQLException;
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
    // Bắt chước Nhập kho: Nạp danh sách lô chi tiết cho mỗi phiếu hủy
    for (PhieuHuySanPham ph : listPhieuHuy) {
      ph.setListLoSanPhamHuy(dao.layChiTietHuyTheoMaPH(ph.getMaPH()));
    }
    canUpdate = false;
  }

  public ArrayList<PhieuHuySanPham> layListPhieuHuy() {
    if (canUpdate || listPhieuHuy == null) khoiTao();
    return listPhieuHuy;
  }

  public boolean thucHienHuy(PhieuHuySanPham phieuHuy, Object[][] data) {
    Connection conn = DBConnection.getConnection();
    try {
      conn.setAutoCommit(false);
      String maPH = dao.layMaPhieuHuySPKhaDung(conn);
      phieuHuy.setMaPH(maPH);

      // 1. Lưu thông tin phiếu chính
      if (!dao.themPhieuHuy(phieuHuy, conn)) throw new SQLException();

      // 2. Duyệt danh sách từ giao diện để lưu vào bảng chi tiết
      for (Object[] row : data) {
        String maLo = row[3].toString();
        double soLuong = Double.parseDouble(row[2].toString());
        double gia = Double.parseDouble(row[4].toString());

        if (!dao.themChiTietHuy(maPH, maLo, soLuong, gia, conn)) throw new SQLException();
        if (!dao.truKhoLoSanPham(maLo, soLuong, conn)) throw new SQLException();
      }
      conn.commit();
      this.canUpdate = true;
      return true;
    } catch (SQLException e) {
      try {
        if (conn != null) conn.rollback();
      } catch (Exception ex) {
      }
      return false;
    } finally {
      try {
        conn.setAutoCommit(true);
        conn.close();
      } catch (Exception e) {
      }
    }
  }

  public boolean capNhatPhieuHuy(PhieuHuySanPham ph) {
    Connection conn = DBConnection.getConnection();
    boolean check = dao.capNhatPhieuHuy(ph, conn);
    if (check) this.canUpdate = true;
    try {
      conn.close();
    } catch (Exception e) {
    }
    return check;
  }
}
