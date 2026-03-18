package bus;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import dao.LoSanPhamDAO;
import dao.conection.DBConnection;
import dto.LoSanPham;
import dto.SanPham;

public class LoSanPhamBUS {

    private static LoSanPhamBUS loSanPhamBUS = null;

    public static LoSanPhamBUS getLoSanPhamBUS() {
        if (loSanPhamBUS == null) {
            loSanPhamBUS = new LoSanPhamBUS();
        }
        return loSanPhamBUS;
    }

    private LoSanPhamDAO loSanPhamDAO = new LoSanPhamDAO();
    private ArrayList<LoSanPham> listLoSanPham;
    private boolean canUpdate = false;

    public LoSanPhamBUS() {
        khoitao();
    }

    public void khoitao() {
        listLoSanPham = loSanPhamDAO.layListLoSanPham();
    }

    public ArrayList<LoSanPham> layListLoSanPham() {
        if (canUpdate || listLoSanPham == null) {
            canUpdate = false;
            khoitao();
        }
        return listLoSanPham;
    }

    public LoSanPham timLoSanPham(String maLo) {
        if (canUpdate || listLoSanPham == null) {
            khoitao();
            canUpdate = false;
        }
        for (LoSanPham lo : listLoSanPham) {
            if (lo.getMaLoSP().equals(maLo)) {
                return lo;
            }
        }
        return null;
    }

    public int laySoLuongSanPhamTrongKho(String maSP) {
        if (maSP == "") {
            return -1;
        }
        if (canUpdate || listLoSanPham == null) {
            khoitao();
            canUpdate = false;
        }
        try {
            int tong = 0;
            LocalDate homNay = LocalDate.now();
            for (LoSanPham loSanPham : listLoSanPham) {
                if (loSanPham.getMaSP().equals(maSP)) {
                    LocalDate ngayHetHang = LocalDate.parse(loSanPham.getHanSuDung());
                    if (ngayHetHang.isAfter(homNay)) {
                        tong += loSanPham.getSoLuong();
                    }
                }
            }
            return tong;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ArrayList<LoSanPham> layLoSanPhamDeBan(SanPham sanPham, double soLuongCan) {
        if (canUpdate || listLoSanPham == null) {
            khoitao();
            canUpdate = false;
        }
        if (soLuongCan <= 0) {
            return new ArrayList<>();
        }
        ArrayList<LoSanPham> listCanDung = new ArrayList<>();
        ArrayList<LoSanPham> listLoSanPham = layLoConHanChoSanPham(sanPham.getMaSP());

        try {
            while (soLuongCan > 0 && listLoSanPham.size() > 0) {

                LoSanPham loSapHetHang = null;
                loSapHetHang = listLoSanPham.get(0);

                for (LoSanPham loSanPham : listLoSanPham) {
                    LocalDate ngayHetHang = LocalDate.parse(loSanPham.getHanSuDung());
                    if (ngayHetHang.isBefore(LocalDate.parse(loSapHetHang.getHanSuDung()))) {
                        loSapHetHang = loSanPham;
                    }
                }
                listLoSanPham.remove(loSapHetHang);

                if (loSapHetHang != null) {
                    soLuongCan -= loSapHetHang.getSoLuong();
                    listCanDung.add(loSapHetHang);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCanDung;
    }

    public int layTongLoChoSanPham(String maSP) {
        if (canUpdate || listLoSanPham == null) {
            khoitao();
            canUpdate = false;
        }
        int tong = 0;
        for (LoSanPham loSanPham : listLoSanPham) {
            if (loSanPham.getMaSP().equals(maSP)) {
                tong++;
            }
        }
        return tong;
    }

    public ArrayList<LoSanPham> layLoChoSanPham(String maSP) {
        if (canUpdate || listLoSanPham == null) {
            khoitao();
            canUpdate = false;
        }
        ArrayList<LoSanPham> list = new ArrayList<>();
        for (LoSanPham loSanPham : listLoSanPham) {
            if (loSanPham.getMaSP().equals(maSP)) {
                list.add(loSanPham);
            }
        }
        return list;
    }

    public int layTongLoHetHangChoSanPham(String maSP) {
        if (canUpdate || listLoSanPham == null) {
            khoitao();
            canUpdate = false;
        }
        int tong = 0;
        try {
            LocalDate homNay = LocalDate.now();
            for (LoSanPham loSanPham : listLoSanPham) {
                if (loSanPham.getMaSP().equals(maSP)) {
                    LocalDate ngayHetHang = LocalDate.parse(loSanPham.getHanSuDung());
                    if (!ngayHetHang.isAfter(homNay)) {
                        tong++;
                    }
                }
            }
            return tong;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ArrayList<LoSanPham> layLoConHanChoSanPham(String maSP) {
        if (canUpdate || listLoSanPham == null) {
            khoitao();
            canUpdate = false;
        }
        ArrayList<LoSanPham> list = new ArrayList<>();
        try {
            LocalDate homNay = LocalDate.now();
            for (LoSanPham loSanPham : listLoSanPham) {
                if (loSanPham.getMaSP().equals(maSP)) {
                    LocalDate ngayHetHang = LocalDate.parse(loSanPham.getHanSuDung());
                    if (!ngayHetHang.isBefore(homNay)) {
                        list.add(loSanPham);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int layTongLoHetHan() {
        if (canUpdate || listLoSanPham == null) {
            khoitao();
            canUpdate = false;
        }
        int tong = 0;
        try {
            LocalDate homNay = LocalDate.now();
            for (LoSanPham loSanPham : listLoSanPham) {
                LocalDate ngayHetHang = LocalDate.parse(loSanPham.getHanSuDung());
                if (!ngayHetHang.isAfter(homNay)) {
                    tong++;
                }
            }
            return tong;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean capNhapLoSanPham(LoSanPham loSanPham) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);

            if (!loSanPhamDAO.capNhapLoSanPham(loSanPham, conn)) {
                throw new SQLException();
            }

            conn.commit();
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
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
        return true;
    }

    public boolean xacNhanLoSanPham(LoSanPham loSanPham, Connection conn) {
        try {
            conn.setAutoCommit(false);

            if (!loSanPhamDAO.xacNhanLoSanPham(loSanPham, conn)) {
                throw new SQLException();
            }

            conn.commit();
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    canUpdate = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public boolean themLoSanPham(LoSanPham loSanPham, Connection conn) {
        try {
            conn.setAutoCommit(false);
            if (!loSanPhamDAO.themLoSanPham(loSanPham, conn)) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                canUpdate = true;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return true;
    }

    public ArrayList<LoSanPham> layLoSanPhamChoPhieuNhap(String maPN) {
        if (canUpdate || listLoSanPham == null) {
            khoitao();
            canUpdate = false;
        }
        ArrayList<LoSanPham> list = new ArrayList<>();
        for (LoSanPham loSanPham : listLoSanPham) {
            if (loSanPham.getMaPN().equals(maPN)) {
                list.add(loSanPham);
            }
        }
        return list;
    }

    public HashMap<LoSanPham, Double> capNhapTonKhoSauKhiBan(Connection conn, SanPham sanPham, double soLuongCan) {

        HashMap<LoSanPham, Double> mapHangHoa = new HashMap<>();

        ArrayList<LoSanPham> listLoSPCanSLy = layLoSanPhamDeBan(sanPham, soLuongCan);

        for (LoSanPham loSanPham : listLoSPCanSLy) {
            if (soLuongCan <= 0)
                break;

            double slTrongLo = loSanPham.getSoLuong();
            double slThucTeLay;

            if (soLuongCan >= slTrongLo) {
                slThucTeLay = slTrongLo;
                xoaLoSanPham(conn, loSanPham.getMaLoSP());
                soLuongCan -= slTrongLo;
            } else {
                slThucTeLay = soLuongCan;
                truSoLuongLo(conn, loSanPham.getMaLoSP(), soLuongCan);
                soLuongCan = 0;
            }

            mapHangHoa.put(loSanPham, mapHangHoa.getOrDefault(loSanPham, 0.0) + slThucTeLay);

            System.out.println("Vui lòng lấy " + slThucTeLay + " sản phẩm " + sanPham.getTenSP() +
                    " ở lô có mã: " + loSanPham.getMaLoSP() + " để sử dụng.");
        }

        return mapHangHoa;
    }

    public boolean xoaLoSanPham(Connection conn, String maLoSP) {
        try {
            boolean result = loSanPhamDAO.xoaLoSanPham(conn, maLoSP);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean truSoLuongLo(Connection conn, String maLoSP, double soLuongTru) {
        try {
            boolean result = loSanPhamDAO.truSoLuongLo(conn, maLoSP, soLuongTru);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setCanUpdate(boolean canUpdate) {
        this.canUpdate = canUpdate;
    }
}