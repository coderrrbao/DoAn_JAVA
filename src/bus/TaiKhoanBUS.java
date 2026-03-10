package bus;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.TaiKhoanDao;
import dao.conection.DBConnection;
import dto.NhomQuyen;
import dto.TaiKhoan;
import util.XuLyExcel;

public class TaiKhoanBUS {
    private static TaiKhoanBUS instance = null;

    public static TaiKhoanBUS getTaiKhoanBUS() {
        if (instance == null) {
            instance = new TaiKhoanBUS();
        }
        return instance;
    }

    private NhomQuyenBUS nhomQuyenBUS = NhomQuyenBUS.getNhomQuyenBUS();
    private TaiKhoanDao dao = new TaiKhoanDao();
    private ArrayList<TaiKhoan> listTaiKhoan;
    private boolean canUpdate = false;

    private TaiKhoanBUS() {
        khoitao();
    }

    public void khoitao() {
        listTaiKhoan = dao.layDanhSachTaiKhoan();

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
        if (tk == null)
            return false;

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
        if (tenDangNhap == null || tenDangNhap.trim().isEmpty())
            return false;

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

    public String layMaTaiKhoanKhaDung() {
        String ma = "";
        try (Connection conn = DBConnection.getConnection()) {
            ma = dao.layMaTaiKhoanKhaDung(conn);

            int so = Integer.parseInt(ma.substring(2)) + 1;
            ma = String.format("TK%02d", so);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ma;
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

    public boolean suaTaiKhoan(TaiKhoan tk) {
        if (tk == null || tk.getMaTK() == null)
            return false;

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
        if (taiKhoan == null)
            return null;

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
            if (tk.getTenDangNhap().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean xuatExc(File file) {
        return XuLyExcel.xuatFileTaiKhoan(file, layDanhSachTaiKhoan());
    }

    public boolean nhapTaiKhoanExcel(File file) {
        ArrayList<TaiKhoan> list = XuLyExcel.nhapFileTaiKhoan(file);
        if (list == null || list.isEmpty())
            return false;

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            String maHienTai = dao.layMaTaiKhoanKhaDung(conn);
            int so = 0;
            if (maHienTai != null && !maHienTai.isEmpty()) {
                so = Integer.parseInt(maHienTai.substring(2));
            }

            for (TaiKhoan tk : list) {
                so++;
                String maMoi = String.format("TK%02d", so);
                tk.setMaTK(maMoi);

                if (tk.getNhomQuyen() == null) {
                    throw new Exception("Thiếu nhóm quyền: " + tk.getTenDangNhap());
                }

                String tenNhom = tk.getNhomQuyen().getTenNhomQuyen();
                NhomQuyen nq = nhomQuyenBUS.timNhomQuyenTheoTen(tenNhom);
                if (nq == null) {
                    throw new Exception("Nhóm quyền không tồn tại trong hệ thống: " + tenNhom);
                }
                tk.setNhomQuyen(nq);

                if (dao.kiemTraTrungUsername(conn, tk.getTenDangNhap())) {
                    throw new Exception("Username đã tồn tại: " + tk.getTenDangNhap());
                }

                if (!dao.insertTaiKhoan(conn, tk)) {
                    throw new Exception("Lỗi khi thêm tài khoản vào DB: " + tk.getTenDangNhap());
                }
            }

            conn.commit();
            canUpdate = true;
            khoitao();
            return true;

        } catch (Exception e) {
            System.err.println("Lỗi Import Excel: " + e.getMessage());
            try {
                if (conn != null)
                    conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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