package bus;

import dao.NguyenLieuDAO;
import dao.conection.DBConnection;
import dto.NguyenLieu;
import util.XuLyExcel;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

public class NguyenLieuBUS {
  private static NguyenLieuBUS nguyenLieuBUS = null;

  public static NguyenLieuBUS getNguyenLieuBUS() {
    if (nguyenLieuBUS == null) {
      nguyenLieuBUS = new NguyenLieuBUS();
    }
    return nguyenLieuBUS;
  }

  private NguyenLieuDAO nguyenLieuDAO = new NguyenLieuDAO();
  private ArrayList<NguyenLieu> listNguyenLieu;
  private boolean canUpdate = false;

  public NguyenLieuBUS() {
    khoitao();
  }

  public void khoitao() {

    try (Connection conn = DBConnection.getConnection()) {
      listNguyenLieu = nguyenLieuDAO.layListNguyenLieu(conn);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public boolean suaCanhBao(NguyenLieu nguyenLieu) {
    return nguyenLieuDAO.capNhatMucCanhBao(nguyenLieu);
  }

  public ArrayList<NguyenLieu> layListNguyenLieu() {
    if (canUpdate || listNguyenLieu == null) {
      khoitao();
      canUpdate = false;
    }
    return listNguyenLieu;
  }

  public NguyenLieu timNguyenLieu(String ma) {
    if (canUpdate || listNguyenLieu == null) {
      khoitao();
      canUpdate = false;
    }
    for (NguyenLieu nl : listNguyenLieu) {
      if (nl.getMaNL().equals(ma)) {
        return nl;
      }
    }
    return null;
  }

  public boolean themNguyenLieu(NguyenLieu nl) {
    Connection conn = DBConnection.getConnection();
    try {
      conn.setAutoCommit(false);
      if (!nguyenLieuDAO.themNguyenLieu(nl, conn)) {
        throw new SQLException();
      }
      conn.commit();
      canUpdate = true;
      return true;
    } catch (SQLException e) {
      try {
        conn.rollback();
      } catch (Exception ex) {
      }
      return false;
    } finally {
      dongKetNoi(conn);
    }
  }

  public boolean themNguyenLieu(NguyenLieu nl, Connection conn) {
    try {
      if (!nguyenLieuDAO.themNguyenLieu(nl, conn)) {
        throw new SQLException();
      }
      conn.commit();
      canUpdate = true;
      return true;
    } catch (SQLException e) {
      return false;
    }
  }

  public boolean xoaNguyenLieu(String maNL) {
    Connection conn = DBConnection.getConnection();
    try {
      conn.setAutoCommit(false);
      if (!nguyenLieuDAO.xoaNguyenLieu(maNL, conn)) {
        throw new SQLException();
      }
      conn.commit();
      canUpdate = true;
      return true;
    } catch (SQLException e) {
      try {
        conn.rollback();
      } catch (Exception ex) {
      }
      return false;
    } finally {
      dongKetNoi(conn);
    }
  }

  public boolean capNhatNguyenLieu(NguyenLieu nl) {
    Connection conn = DBConnection.getConnection();
    try {
      conn.setAutoCommit(false);
      if (!nguyenLieuDAO.capNhatNguyenLieu(nl, conn)) {
        throw new SQLException();
      }
      conn.commit();
      canUpdate = true;
      return true;
    } catch (SQLException e) {
      try {
        conn.rollback();
      } catch (Exception ex) {
      }
      return false;
    } finally {
      dongKetNoi(conn);
    }
  }

  public ArrayList<NguyenLieu> timKiemNguyenLieu(String keyword) {
    ArrayList<NguyenLieu> ketQua = new ArrayList<>();
    ArrayList<NguyenLieu> dsGốc = layListNguyenLieu();

    String lowerKeyword = keyword.toLowerCase().trim();
    if (keyword == null || keyword.trim().isEmpty()) {
      return dsGốc;
    }
    for (NguyenLieu nl : dsGốc) {
      if (nl.getMaNL().toLowerCase().contains(lowerKeyword)
          || nl.getTenNL().toLowerCase().contains(lowerKeyword)) {
        ketQua.add(nl);
      }
    }
    return ketQua;
  }

  private void dongKetNoi(Connection conn) {
    if (conn != null) {
      try {
        conn.setAutoCommit(true);
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public boolean xuatExcel(File file) {
    return XuLyExcel.xuatFileNguyenLieu(file, this.layListNguyenLieu());
  }

  public boolean nhapExcel(File file) {
    ArrayList<NguyenLieu> listMoi = XuLyExcel.nhapFileNguyenLieu(file);
    if (listMoi == null || listMoi.isEmpty()) {
      return false;
    }

    HashSet<String> setTenNL = new HashSet<>();
    for (NguyenLieu nl : layListNguyenLieu()) {
      setTenNL.add(nl.getTenNL().trim().toLowerCase());
    }

    for (NguyenLieu nl : listMoi) {
      if (setTenNL.contains(nl.getTenNL().trim().toLowerCase())) {
        return false;
      }
    }

    Connection conn = null;
    try {
      conn = DBConnection.getConnection();
      conn.setAutoCommit(false);

      for (NguyenLieu nl : listMoi) {
        if (!themNguyenLieu(nl, conn)) {
          throw new SQLException("Lỗi khi thêm nguyên liệu: " + nl.getTenNL());
        }
      }

      conn.commit();
      this.canUpdate = true;
      this.khoitao();
      return true;

    } catch (Exception e) {
      e.printStackTrace();
      if (conn != null) {
        try {
          conn.rollback();
        } catch (SQLException ex) {
          ex.printStackTrace();
        }
      }
      return false;
    } finally {
      dongKetNoi(conn);
    }
  }
}
