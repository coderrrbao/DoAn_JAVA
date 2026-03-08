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
    //xuat exc nhom quyen
    public boolean XuatExc(){
        return XuLyExcel.xuatFileNhomQuyen(listNhomQuyen);
    }

    // nhập excel nhóm quyền + phân quyền
    public boolean nhapExcelPhanQuyen() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Chọn file Excel");
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            Connection conn = DBConnection.getConnection();
            try {
                conn.setAutoCommit(false);
                Object[] data = XuLyExcel.nhapFilePhanQuyen(file);
                ArrayList<NhomQuyen> listNQ = (ArrayList<NhomQuyen>) data[0];
                ArrayList<PhanQuyen> listPQ = (ArrayList<PhanQuyen>) data[1];
                PhanQuyenDAO pqDAO = new PhanQuyenDAO();

                for (NhomQuyen nq : listNQ) {

                    if (!nhomQuyenDAO.themNhomQuyen(nq, conn)) {
                        throw new Exception("Lỗi thêm nhóm quyền");
                    }
                }

                for (PhanQuyen pq : listPQ) {

                    if (!pqDAO.themPhanQuyen(pq, conn)) {
                        throw new Exception("Lỗi thêm phân quyền");
                    }
                }
                conn.commit();
                this.canUpdate = true;
                JOptionPane.showMessageDialog(null, "Nhập Excel thành công!");
                return true;
            } catch (Exception e) {
                try {
                    conn.rollback();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Nhập Excel thất bại!");
            } finally {

                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }
}