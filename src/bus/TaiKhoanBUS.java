package bus;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import dao.TaiKhoanDao;
import dao.conection.DBConnection;
import dto.TaiKhoan;
import util.XuLyExcel;

public class TaiKhoanBUS {

    // 1. Áp dụng Singleton Pattern

    private static TaiKhoanBUS instance = null;

    public static TaiKhoanBUS getTaiKhoanBUS() {
        if (instance == null) {
            instance = new TaiKhoanBUS();
        }
        return instance;
    }
    private NhomQuyenBUS nhomQuyenBUS = new NhomQuyenBUS();
    private TaiKhoanDao dao = new TaiKhoanDao();
    private ArrayList<TaiKhoan> listTaiKhoan;
    private boolean canUpdate = false;

    private TaiKhoanBUS() {
        khoitao();
    }

    public void khoitao() {
        listTaiKhoan = dao.layDanhSachTaiKhoan();
        NhomQuyenBUS nhomQuyenBUS = NhomQuyenBUS.getNhomQuyenBUS();
        for (TaiKhoan taiKhoan : listTaiKhoan) {
            if (taiKhoan.getNhomQuyen() != null) {
                taiKhoan.setNhomQuyen(nhomQuyenBUS.timNhomQuyen(taiKhoan.getNhomQuyen().getMaNQ()));
            }
        }
    }

    public ArrayList<TaiKhoan> layDanhSachTaiKhoan() {
        if (canUpdate || listTaiKhoan == null) {
            canUpdate = false;
            khoitao();
        }
        return listTaiKhoan;
    }

    public TaiKhoan timTaiKhoan(String maTK) {
        if (canUpdate || listTaiKhoan == null) {
            khoitao();
            canUpdate = false;
        }

        for (TaiKhoan tk : listTaiKhoan) {
            if (tk.getMaTK().equals(maTK)) {
                return tk;
            }
        }

        return null;
    }

    public boolean themTaiKhoan(TaiKhoan tk) {
        if (tk == null) {
            return false;
        }

        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            if (!dao.themTaiKhoan(tk, conn)) {
                throw new SQLException();
            }
            conn.commit();
            canUpdate = true;
            return true;
        } catch (Exception e) {
            try {
                if (conn != null)
                    conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean xoaTaiKhoan(String tenDangNhap) {
        if (tenDangNhap == null || tenDangNhap.trim().isEmpty()) {
            return false;
        }

        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            if (!dao.xoaTaiKhoan(tenDangNhap, conn)) {
                throw new SQLException();
            }
            conn.commit();
            canUpdate = true;
            return true;
        } catch (Exception e) {
            try {
                if (conn != null)
                    conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean suaMatKhau(String tenDangNhap, String matKhauMoi) {
        if (tenDangNhap == null || tenDangNhap.trim().isEmpty() ||
                matKhauMoi == null || matKhauMoi.trim().isEmpty()) {
            return false;
        }

        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            if (!dao.suaMatKhau(tenDangNhap, matKhauMoi, conn)) {
                throw new SQLException();
            }
            conn.commit();
            canUpdate = true;
            return true;
        } catch (Exception e) {
            try {
                if (conn != null)
                    conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //sua tai khoan
    public boolean suaTaiKhoan(TaiKhoan tk) {
        if (tk == null || tk.getMaTK() == null) {
            return false;
        }

        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);

            if (!dao.suaTaiKhoan(tk, conn)) {
                throw new SQLException();
            }

            conn.commit();
            canUpdate = true;
            return true;

        } catch (Exception e) {
            try {
                if (conn != null)
                    conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;

        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public TaiKhoan dangNhap(String tenDangNhap, String matKhau) {
        if (tenDangNhap == null || matKhau == null)
            return null;
        TaiKhoan taiKhoan = dao.dangNhap(tenDangNhap, matKhau);
        if (taiKhoan == null) {
            return null;
        }
        NhomQuyenBUS nhomQuyenBUS = NhomQuyenBUS.getNhomQuyenBUS();
        taiKhoan.setNhomQuyen(nhomQuyenBUS.timNhomQuyen(taiKhoan.getNhomQuyen().getMaNQ()));
        return taiKhoan;
    }

    public Boolean kiemTraUsernameTonTai(String username) {
        if (username == null || username.trim().isEmpty())
            return false;
        if (listTaiKhoan == null || canUpdate) {
            khoitao();
            canUpdate = false;
        }
        for (TaiKhoan tk : listTaiKhoan) {
            if (tk.getTenDangNhap().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    public int getTongSoTrang(int pageSize) {
        if (canUpdate || listTaiKhoan == null) {
            khoitao();
        }
        return (int) Math.ceil((double) listTaiKhoan.size() / pageSize);
    }

    public ArrayList<TaiKhoan> layTrang(int page, int pageSize) {
        if (canUpdate || listTaiKhoan == null) {
            canUpdate = false;
            khoitao();
        }
        ArrayList<TaiKhoan> kq = new ArrayList<>();
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, listTaiKhoan.size());

        if (start >= listTaiKhoan.size())
            return kq;

        for (int i = start; i < end; i++) {
            kq.add(listTaiKhoan.get(i));
        }
        return kq;
    }
    //xuat exc
    public boolean xuatExc(){
        return XuLyExcel.xuatFileTaiKhoan(layDanhSachTaiKhoan());
    }

    //nhap excel
    public boolean nhapTuExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel");

        if (fileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            return false;
        }

        File file = fileChooser.getSelectedFile();

        ArrayList<TaiKhoan> list = XuLyExcel.nhapFileTaiKhoan(file);

        if (list == null || list.isEmpty()) {
            JOptionPane.showMessageDialog(null, "File không có dữ liệu!");
            return false;
        }

        Connection conn = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            for (TaiKhoan tk : list) {

                // Map theo MaNQ (ĐÚNG với file của em)
                tk.setNhomQuyen(
                    nhomQuyenBUS.timNhomQuyen(tk.getNhomQuyen().getMaNQ())
                );

                if (tk.getNhomQuyen() == null) {
                    throw new Exception("Nhóm quyền không tồn tại: " + tk.getMaTK());
                }

                // Kiểm tra trùng username
                if (dao.kiemTraTrungUsername(conn, tk.getTenDangNhap())) {
                    throw new Exception("Username đã tồn tại: " + tk.getTenDangNhap());
                }

                dao.insertTaiKhoan(conn, tk);
            }

            conn.commit();
            JOptionPane.showMessageDialog(null, "Nhập Excel thành công!");
            canUpdate = true;
            return true;

        } catch (Exception e) {

            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            JOptionPane.showMessageDialog(null,
                    "Nhập Excel thất bại!\n" + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);

            return false;

        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}