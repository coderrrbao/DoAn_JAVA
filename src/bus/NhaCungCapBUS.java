package bus;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import dao.NhaCungCapDAO;
import dao.conection.DBConnection;
import dto.ChiTietNhaCungCap;
import dto.NhaCungCap;
import util.XuLyExcel;

public class NhaCungCapBUS {

    private static NhaCungCapBUS nhaCungCapBUS = null;

    public static NhaCungCapBUS getNhaCungCapBUS() {
        if (nhaCungCapBUS == null) {
            nhaCungCapBUS = new NhaCungCapBUS();
        }
        return nhaCungCapBUS;
    }

    private NhaCungCapDAO nhaCungCapDAO = new NhaCungCapDAO();
    private ArrayList<NhaCungCap> listNhaCungCap;
    private boolean canUpdate = false;

    public void khoitao() {
        listNhaCungCap = nhaCungCapDAO.layListNhaCungCap();
        ArrayList<ChiTietNhaCungCap> listChiTietNCC = ChiTietNhaCungCapBUS.getChiTietNhaCungCapBUS()
                .layListChiTietNhaCungCap();

        Map<String, ArrayList<ChiTietNhaCungCap>> map = new HashMap<>();
        for (NhaCungCap nhaCungCap : listNhaCungCap) {
            map.put(nhaCungCap.getMaNCC(), new ArrayList<ChiTietNhaCungCap>());
        }
        for (ChiTietNhaCungCap chiTietNhaCungCap : listChiTietNCC) {
            ArrayList<ChiTietNhaCungCap> listCTNCC = map.get(chiTietNhaCungCap.getMaNCC());
            if (listCTNCC != null) {
                listCTNCC.add(chiTietNhaCungCap);
                map.put(chiTietNhaCungCap.getMaNCC(), listCTNCC);
            }
        }

        for (NhaCungCap nhaCungCap : listNhaCungCap) {
            for (ChiTietNhaCungCap chiTietNhaCungCap : map.get(nhaCungCap.getMaNCC())) {
                nhaCungCap.themChiTietNhaCungCap(chiTietNhaCungCap);
            }

        }
    }

    public ArrayList<NhaCungCap> laylistNhaCungCap() {
        if (canUpdate || listNhaCungCap == null) {
            khoitao();
            canUpdate = false;
        }
        return listNhaCungCap;
    }

    public NhaCungCap timNhaCungCap(String ma) {
        if (canUpdate || listNhaCungCap == null) {
            khoitao();
            canUpdate = false;
        }
        for (NhaCungCap ncc : listNhaCungCap) {
            if (ncc.getMaNCC().equals(ma)) {
                return ncc;
            }
        }
        return null;
    }

    public NhaCungCap timNhaCungCapTheoTen(String ten) {
        if (canUpdate || listNhaCungCap == null) {
            khoitao();
            canUpdate = false;
        }
        for (NhaCungCap ncc : listNhaCungCap) {
            if (ncc.getTenNCC().equalsIgnoreCase(ten)) {
                return ncc;
            }
        }
        return null;
    }

    public ArrayList<String> layLuaChonNCC() {
        if (canUpdate || listNhaCungCap == null) {
            khoitao();
            canUpdate = false;
        }
        ArrayList<String> list = new ArrayList<>();
        for (NhaCungCap nhaCungCap : listNhaCungCap) {
            list.add(nhaCungCap.getTenNCC());
        }
        return list;
    }

    public ArrayList<String> layLuaChonNCCNguyenLieu() {
        if (canUpdate || listNhaCungCap == null) {
            khoitao();
            canUpdate = false;
        }
        ArrayList<String> list = new ArrayList<>();
        for (NhaCungCap nhaCungCap : listNhaCungCap) {
            if (nhaCungCap.getCungCapNL()) {
                list.add(nhaCungCap.getTenNCC());
            }

        }
        return list;
    }

    public ArrayList<String> layLuaChonNCCSanPham() {
        if (canUpdate || listNhaCungCap == null) {
            khoitao();
            canUpdate = false;
        }
        ArrayList<String> list = new ArrayList<>();
        for (NhaCungCap nhaCungCap : listNhaCungCap) {
            if (nhaCungCap.getCungCapSP()) {
                list.add(nhaCungCap.getTenNCC());
            }

        }
        return list;
    }

    public void danhDauCanCapNhat() {
        this.canUpdate = true;
    }

    public boolean themNhaCungCap(NhaCungCap nhaCungCap) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            String maNCC = nhaCungCapDAO.layMaNhaCungCapKhaDung();
            nhaCungCap.setMaNCC(maNCC);
            if (!nhaCungCapDAO.themNhaCungCap(nhaCungCap)) {
                throw new SQLException();
            }
            ChiTietNhaCungCapBUS chiTietNhaCungCapBUS = ChiTietNhaCungCapBUS.getChiTietNhaCungCapBUS();

            for (ChiTietNhaCungCap chiTietNhaCungCap : nhaCungCap.getListChiTietNhaCungCap()) {
                chiTietNhaCungCap.setMaNCC(maNCC);
                if (!chiTietNhaCungCapBUS.themChiTietNhaCungCap(chiTietNhaCungCap, conn)) {
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

    public boolean capNhapNhaCungCap(NhaCungCap nhaCungCap, ArrayList<String> listNCCCanXoa) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            if (!nhaCungCapDAO.capNhapNhaCungCap(nhaCungCap, conn)) {
                throw new SQLException();
            }

            ChiTietNhaCungCapBUS chiTietNhaCungCapBUS = ChiTietNhaCungCapBUS.getChiTietNhaCungCapBUS();
            for (String ma : listNCCCanXoa) {
                if (!chiTietNhaCungCapBUS.xoaChiTietNhaCungCap(ma, conn)) {
                    throw new SQLException();
                }
            }

            for (ChiTietNhaCungCap chiTietNhaCungCap : nhaCungCap.getListChiTietNhaCungCap()) {
                chiTietNhaCungCap.setMaNCC(nhaCungCap.getMaNCC());
                if (chiTietNhaCungCap.getMaCTNCC().equals("")) {
                    if (!chiTietNhaCungCapBUS.themChiTietNhaCungCap(chiTietNhaCungCap, conn)) {
                        throw new SQLException();
                    }
                } else {
                    if (!chiTietNhaCungCapBUS.capNhapChiTietNhaCungCap(chiTietNhaCungCap, conn)) {
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

    public boolean xoaNhaCungCap(NhaCungCap nhaCungCap) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            if (!nhaCungCapDAO.xoaNhaCungCap(nhaCungCap, conn)) {
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

    public boolean nhapExcel(File file) {

        ArrayList<NhaCungCap> dsNhap = XuLyExcel.nhapFileNhaCungCap(file);

        if (dsNhap == null || dsNhap.isEmpty())
            return false;

        int thanhCong = 0;

        for (NhaCungCap ncc : dsNhap) {
            if (themNhaCungCap(ncc)) {
                thanhCong++;
            }
        }
        return thanhCong > 0;
    }

    public boolean xuatExcel(File file) {

        ArrayList<NhaCungCap> listHienTai = laylistNhaCungCap();

        return XuLyExcel.xuatFileNhaCungCap(file, listHienTai);
    }

}