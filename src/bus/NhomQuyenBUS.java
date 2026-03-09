package bus;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import dao.NhomQuyenDAO;
import dao.PhanQuyenDAO;
import dao.conection.DBConnection;
import dto.NhomQuyen;
import dto.PhanQuyen;
import dto.Quyen;
import util.XuLyExcel;

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

    public NhomQuyen timNhomQuyenTheoTen(String ten) {

        if (canUpdate || listNhomQuyen == null) {
            khoiTao();
            canUpdate = false;
        }

        for (NhomQuyen nq : listNhomQuyen) {
            if (nq.getTenNhomQuyen().equals(ten)) {
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

    public boolean capNhatNhomQuyen(NhomQuyen nhomQuyen) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            if (!nhomQuyenDAO.capNhatNhomQuyen(nhomQuyen, conn)) {
                throw new SQLException();
            }
            PhanQuyenBUS phanQuyenBUS = PhanQuyenBUS.getPhanQuyenBUS();
            ArrayList<PhanQuyen> listPhanQuyenTrongDB = phanQuyenBUS.layPhanQuyenChoNhomQuyen(nhomQuyen.getMaNQ());
            HashSet<String> set = new HashSet<>();
            for (PhanQuyen phanQuyen : listPhanQuyenTrongDB) {
                set.add(phanQuyen.getMaQuyen());
            }
            QuyenBUS quyenBUS = QuyenBUS.getQuyenBUS();
            for (Quyen quyen : nhomQuyen.getListQuyen()) {
                Quyen q = quyenBUS.timQuyenTheoTen(quyen.getTenQuyen());
                quyen.setMaQuyen(q.getMaQuyen());
            }

            HashSet<String> set2 = new HashSet<>();
            for (Quyen quyen : nhomQuyen.getListQuyen()) {
                if (!set.contains(quyen.getMaQuyen())) {
                    if (!phanQuyenBUS.themPhanQuyen(new PhanQuyen(nhomQuyen.getMaNQ(), quyen.getMaQuyen()), conn)) {
                        throw new SQLException();
                    }
                }
                set2.add(quyen.getMaQuyen());
            }
            for (PhanQuyen phanQuyen : listPhanQuyenTrongDB) {
                if (!set2.contains(phanQuyen.getMaQuyen())) {
                    if (!phanQuyenBUS.xoaPhanQuyen(phanQuyen, conn)) {
                        throw new SQLException();
                    }
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

    public boolean XuatExc() {
        PhanQuyenBUS phanQuyenBUS = PhanQuyenBUS.getPhanQuyenBUS();
        return XuLyExcel.xuatFileNhomQuyen(layDanhSachNhomQuyen(), phanQuyenBUS.layDanhSachPhanQuyen());
    }

    public boolean nhapExcelPhanQuyen(File file) {
        if (file == null || !file.exists()) {
            JOptionPane.showMessageDialog(null, "File không hợp lệ!");
            return false;
        }

        Object[] data = XuLyExcel.nhapFilePhanQuyen(file);
        if (data == null || data.length < 2) {
            JOptionPane.showMessageDialog(null, "Dữ liệu file Excel không đúng định dạng!");
            return false;
        }

        ArrayList<NhomQuyen> listNQExcel = (ArrayList<NhomQuyen>) data[0];
        ArrayList<PhanQuyen> listPQExcel = (ArrayList<PhanQuyen>) data[1];

        this.khoiTao();
        HashSet<String> dsMaHienTai = new HashSet<>();
        for (NhomQuyen nq : this.listNhomQuyen) {
            dsMaHienTai.add(nq.getMaNQ());
        }

        for (NhomQuyen nq : listNQExcel) {
            if (!this.themNhomQuyen(nq)) {
                return false;
            }

            Connection conn = DBConnection.getConnection();
            PhanQuyenDAO pqDAO = new PhanQuyenDAO();
            try {
                conn.setAutoCommit(false);
                for (PhanQuyen pq : listPQExcel) {
                    if (pqDAO.themPhanQuyen(pq, conn)) {
                    }
                }
                conn.commit();
            } catch (Exception e) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
            } finally {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            this.canUpdate = true;
        }
        return true;
    }
}