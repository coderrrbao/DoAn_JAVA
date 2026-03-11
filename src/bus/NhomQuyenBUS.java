package bus;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.swing.JOptionPane;

import dao.NhomQuyenDAO;
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

    public boolean themNhomQuyen(NhomQuyen nhomQuyen, Connection conn) {
        try {
            PhanQuyenBUS phanQuyenBUS = PhanQuyenBUS.getPhanQuyenBUS();

            String maNQMoi = nhomQuyenDAO.taoMaNhomQuyenMoi(conn);
            nhomQuyen.setMaNQ(maNQMoi);

            if (!nhomQuyenDAO.themNhomQuyen(nhomQuyen, conn)) {
                return false;
            }
            for (Quyen quyen : nhomQuyen.getListQuyen()) {
                PhanQuyen phanQuyen = new PhanQuyen(maNQMoi, quyen.getMaQuyen());

                if (!phanQuyenBUS.themPhanQuyen(phanQuyen, conn)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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

        @SuppressWarnings("unchecked")
        ArrayList<NhomQuyen> listNQExcel = (ArrayList<NhomQuyen>) data[0];
        @SuppressWarnings("unchecked")
        ArrayList<PhanQuyen> listPQExcel = (ArrayList<PhanQuyen>) data[1];
        Map<String, NhomQuyen> mapNQ = new HashMap<>();
        for (NhomQuyen nq : listNQExcel) {
            if (nq.getListQuyen() == null) {
                nq.setListQuyen(new ArrayList<>());
            }
            mapNQ.put(nq.getMaNQ(), nq);
        }

        QuyenBUS quyenBUS = QuyenBUS.getQuyenBUS();
        for (PhanQuyen pq : listPQExcel) {
            NhomQuyen nhomQuyen = mapNQ.get(pq.getMaNQ());
            if (nhomQuyen != null) {
                Quyen quyenChiTiet = quyenBUS.timTheoMa(pq.getMaQuyen());
                if (quyenChiTiet != null) {
                    nhomQuyen.getListQuyen().add(quyenChiTiet);
                } else {
                    System.out.println("Cảnh báo: Không tìm thấy mã quyền " + pq.getMaQuyen() + " trong hệ thống.");
                }
            }
        }

        ArrayList<NhomQuyen> listNhomQuyen = new ArrayList<>(mapNQ.values());

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            for (NhomQuyen nhomQuyen : listNhomQuyen) {
                if (!themNhomQuyen(nhomQuyen, conn)) {
                    throw new Exception();
                }
            }
            conn.commit();
            this.canUpdate = true;
            this.khoiTao();
            return true;

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
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
}