package bus;

import dao.NguyenLieuDAO;
import dao.conection.DBConnection;
import dto.NguyenLieu;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

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
    Connection conn = DBConnection.getConnection();
    try {
      listNguyenLieu = nguyenLieuDAO.layListNguyenLieu(conn);
    } finally {
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
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
}
