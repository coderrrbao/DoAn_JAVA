package bus;

import dao.PhieuHuySanPhamDAO;
import dao.conection.DBConnection;
import dto.LoSanPham;
import dto.PhieuHuySanPham;
import util.XuLyExcel;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PhieuHuySanPhamBUS {
  private static PhieuHuySanPhamBUS instance;
  private PhieuHuySanPhamDAO dao = new PhieuHuySanPhamDAO();
  private ArrayList<PhieuHuySanPham> listPhieuHuy = null;
  private boolean canUpdate = true;

  public static PhieuHuySanPhamBUS getPhieuHuySanPhamBUS() {
    if (instance == null)
      instance = new PhieuHuySanPhamBUS();
    return instance;
  }

  public void khoiTao() {
    listPhieuHuy = dao.layListPhieuHuy();
    for (PhieuHuySanPham ph : listPhieuHuy) {
      ph.setListLoSanPhamHuy(dao.layChiTietHuyTheoMaPH(ph.getMaPH()));
    }
    canUpdate = false;
  }

  public ArrayList<PhieuHuySanPham> layListPhieuHuy() {
    if (canUpdate || listPhieuHuy == null)
      khoiTao();
    return listPhieuHuy;
  }

  public boolean thucHienHuy(PhieuHuySanPham phieuHuy, Object[][] data) {
    Connection conn = DBConnection.getConnection();
    try {
      conn.setAutoCommit(false);
      String maPH = dao.layMaPhieuHuySPKhaDung(conn);
      phieuHuy.setMaPH(maPH);

      if (!dao.themPhieuHuy(phieuHuy, conn))
        throw new SQLException();

      for (Object[] row : data) {
        String maLo = row[3].toString();
        double soLuong = Double.parseDouble(row[2].toString());
        double gia = Double.parseDouble(row[4].toString());

        if (!dao.themChiTietHuy(maPH, maLo, soLuong, gia, conn))
          throw new SQLException();
      }
      conn.commit();
      this.canUpdate = true;
      return true;
    } catch (SQLException e) {
      try {
        if (conn != null)
          conn.rollback();
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
    try {
      conn.setAutoCommit(false);
      if (!dao.capNhatPhieuHuy(ph, conn))
        throw new SQLException("Câp nhật thất bại");
      if ("Đã xác nhận".equalsIgnoreCase(ph.getTrangThaiXuLy().trim())) {
        ArrayList<LoSanPham> chiTiet = dao.layChiTietHuyTheoMaPH(ph.getMaPH());
        if (chiTiet == null)
          throw new SQLException("Không tìm thấy chi tiết phiếu hủy");
        for (LoSanPham Lo : chiTiet) {
          if (!dao.truKhoLoSanPham(Lo.getMaLoSP(), Lo.getSoLuong(), conn))
            throw new SQLException("Trừ kho thất bại");
        }
      }
      conn.commit();
      this.canUpdate = true;
      khoiTao();
      bus.LoSanPhamBUS.getLoSanPhamBUS().khoitao();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      try {
        if (conn != null)
          conn.rollback();
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

  public boolean themPhieuHuy(PhieuHuySanPham p, Connection conn) throws SQLException {

    p.setMaPH(dao.layMaPhieuHuySPKhaDung(conn));

    double tongTien = 0;
    for (LoSanPham lo : p.getListLoSanPhamHuy()) {
      tongTien += lo.getSoLuong() * lo.getGiaNhap();
    }
    p.setTongGiaTri(tongTien);

    if (!dao.themPhieuHuy(p, conn))
      return false;

    for (LoSanPham lo : p.getListLoSanPhamHuy()) {
      if (!dao.themChiTietHuy(p.getMaPH(), lo.getMaLoSP(), lo.getSoLuong(), lo.getGiaNhap(), conn)) {
        return false;
      }
    }
    return true;
  }

  public boolean nhapExcel(File file) {
    ArrayList<PhieuHuySanPham> dsNhap = XuLyExcel.nhapFilePhieuHuySanPham(file);
    if (dsNhap == null || dsNhap.isEmpty())
      return false;

    Connection conn = null;
    try {
      conn = DBConnection.getConnection();
      conn.setAutoCommit(false);

      for (PhieuHuySanPham phieu : dsNhap) {
        if (!themPhieuHuy(phieu, conn)) {
          throw new SQLException("Lỗi thêm phiếu hủy sản phẩm từ Excel");
        }
      }

      conn.commit();
      canUpdate = true;
      khoiTao();
      return true;

    } catch (Exception e) {
      e.printStackTrace();
      if (conn != null) {
        try {
          conn.rollback();
        } catch (SQLException ex) {
        }
      }
      return false;
    } finally {
      if (conn != null) {
        try {
          conn.setAutoCommit(true);
          conn.close();
        } catch (SQLException e) {
        }
      }
    }
  }

  public boolean xuatExcel(File file) {
    ArrayList<PhieuHuySanPham> dsPhieu = layListPhieuHuy();
    if (dsPhieu == null)
      return false;
    return XuLyExcel.xuatFilePhieuHuySanPham(file, dsPhieu);
  }

  public boolean xoaMemPhieuHuy(String maPH) {
    Connection conn = DBConnection.getConnection();
    try {
      if (!dao.xoaMemPhieuHuy(maPH, conn))
        throw new SQLException("Xóa thất bại");
      this.canUpdate = true;
      khoiTao();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    } finally {
      try {
        if (conn != null)
          conn.close();
      } catch (Exception e) {
      }
    }
  }
}
