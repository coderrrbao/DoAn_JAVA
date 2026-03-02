package bus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import dao.NhomQuyenDAO;
import dao.conection.DBConnection;
import dto.NhomQuyen;
import dto.PhanQuyen;
import dto.Quyen;

public class NhomQuyenBUS {
    private static NhomQuyenBUS nhomQuyenBUS;
    private NhomQuyenDAO nhomQuyenDAO = new NhomQuyenDAO();
    private ArrayList<NhomQuyen> listNhomQuyen;
    private boolean canUpdate = true;

    public static NhomQuyenBUS getNhomQuyenBUS() {
        if (nhomQuyenBUS == null) {
            nhomQuyenBUS = new NhomQuyenBUS();
        }
        return nhomQuyenBUS;
    }

    public NhomQuyenBUS() {
        khoiTao();
    }

    public void khoiTao() {
        listNhomQuyen = nhomQuyenDAO.layDanhSachNhomQuyen_Dao();
        QuyenBUS quyenBUS = QuyenBUS.getQuyenBUS();
        for (NhomQuyen nhomQuyen : listNhomQuyen) {
            nhomQuyen.setListQuyen(quyenBUS.layQuyenChoNhomQuyen(nhomQuyen.getMaNQ()));
        }

    }

    public ArrayList<NhomQuyen> layDanhSachNhomQuyen() {
        if (canUpdate || listNhomQuyen == null) {
            khoiTao();
            canUpdate = false;
        }
        return listNhomQuyen;
    }

    public NhomQuyen timNhomQuyen(String maNQ) {

        if (canUpdate || listNhomQuyen == null) {
            khoiTao();
            canUpdate = false;
        }

        for (NhomQuyen nq : listNhomQuyen) {
            if (nq.getMaNQ().equals(maNQ)) {
                return nq;
            }
        }
        return null;
    }

    public boolean themNhomQuyen(NhomQuyen nhomQuyen) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);

            PhanQuyenBUS phanQuyenBUS = PhanQuyenBUS.getPhanQuyenBUS();
            String maPQ = nhomQuyenDAO.taoMaNhomQuyenMoi(conn);
            nhomQuyen.setMaNQ(maPQ);
            if (!nhomQuyenDAO.themNhomQuyen(nhomQuyen, conn)) {
                throw new SQLException();
            }
            QuyenBUS quyenBUS = QuyenBUS.getQuyenBUS();
            for (Quyen quyen : nhomQuyen.getListQuyen()) {
                PhanQuyen phanQuyen = new PhanQuyen(nhomQuyen.getMaNQ(), quyen.getMaQuyen());
                quyen = quyenBUS.timQuyenTheoTen(quyen.getTenQuyen());
                phanQuyen.setMaQuyen(quyen.getMaQuyen());
                if (!phanQuyenBUS.themPhanQuyen(phanQuyen, conn)) {
                    throw new SQLException();
                }
            }

            conn.commit();

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                    canUpdate = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        this.canUpdate = true;
        return true;
    }


     public boolean xoaNhomQuyen(NhomQuyen nhomQuyen) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);

            if (!nhomQuyenDAO.xoaNhomQuyen(nhomQuyen, conn)) {
                throw new SQLException();
            }
          
            conn.commit();

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                    canUpdate = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        this.canUpdate = true;
        return true;
    }

    public void setCanUpdate(boolean canUpdate) {
        this.canUpdate = canUpdate;
    }
}