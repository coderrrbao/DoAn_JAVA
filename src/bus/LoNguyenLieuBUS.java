package bus;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import dao.LoNguyenLieuDAO;
import dao.conection.DBConnection;
import dto.ChiTietCongThuc;
import dto.LoNguyenLieu;
import dto.LoSanPham;
import dto.NguyenLieu;
import dto.SanPham;

public class LoNguyenLieuBUS {

    private static LoNguyenLieuBUS loNguyenLieuBUS = null;

    public static LoNguyenLieuBUS getLoNguyenLieuBUS() {
        if (loNguyenLieuBUS == null) {
            loNguyenLieuBUS = new LoNguyenLieuBUS();
        }
        return loNguyenLieuBUS;
    }

    private LoNguyenLieuDAO loNguyenLieuDAO = new LoNguyenLieuDAO();
    private ArrayList<LoNguyenLieu> listLoNguyenLieu;
    private boolean canUpdate = false;

    public LoNguyenLieuBUS() {
        khoitao();
    }

    public void khoitao() {
        listLoNguyenLieu = loNguyenLieuDAO.layListLoNguyenLieu();
    }

    public ArrayList<LoNguyenLieu> layListLoNguyenLieu() {
        if (canUpdate || listLoNguyenLieu == null) {
            canUpdate = false;
            khoitao();
        }
        return listLoNguyenLieu;
    }

    public LoNguyenLieu timLoNguyenLieu(String maLo) {
        if (canUpdate || listLoNguyenLieu == null) {
            khoitao();
            canUpdate = false;
        }
        for (LoNguyenLieu lo : listLoNguyenLieu) {
            if (lo.getMaLoNL().equals(maLo)) {
                return lo;
            }
        }
        return null;
    }

    public int layTongLoChoNguyenLieu(String maNL) {
        if (canUpdate || listLoNguyenLieu == null) {
            khoitao();
            canUpdate = false;
        }
        int tong = 0;
        for (LoNguyenLieu loNguyenLieu : listLoNguyenLieu) {
            if (loNguyenLieu.getMaNL().equals(maNL)) {
                tong++;
            }
        }
        return tong;
    }

    public int layTongLoHetHangChoNguyenLieu(String maNL) {
        if (canUpdate || listLoNguyenLieu == null) {
            khoitao();
            canUpdate = false;
        }
        int tong = 0;
        for (LoNguyenLieu lo : listLoNguyenLieu) {
            LocalDate ngayHetHan = LocalDate.parse(lo.getHanSuDung());
            if (lo.getMaNL().equals(maNL) && !ngayHetHan.isAfter(LocalDate.now())) {
                tong++;
            }
        }
        return tong;
    }

    public ArrayList<LoNguyenLieu> layLoConHanChoNguyenLieu(String maNL) {
        if (canUpdate || listLoNguyenLieu == null) {
            khoitao();
            canUpdate = false;
        }
        ArrayList<LoNguyenLieu> list = new ArrayList<>();
        for (LoNguyenLieu lo : listLoNguyenLieu) {
            LocalDate ngayHetHan = LocalDate.parse(lo.getHanSuDung());
            if (lo.getMaNL().equals(maNL) && !ngayHetHan.isBefore(LocalDate.now())) {
                list.add(lo);
            }
        }
        return list;
    }

    public ArrayList<LoNguyenLieu> layLoNguyenLieuDeBan(String maNL, double soLuongCan) {
        if (soLuongCan <= 0) {
            return new ArrayList<>();
        }
        ArrayList<LoNguyenLieu> listLoKhaDung = layLoConHanChoNguyenLieu(maNL);
        ArrayList<LoNguyenLieu> listKetQua = new ArrayList<>();

        try {
            while (soLuongCan > 0 && !listLoKhaDung.isEmpty()) {

                LoNguyenLieu loSapHetHan = listLoKhaDung.get(0);
                for (LoNguyenLieu lo : listLoKhaDung) {
                    LocalDate hsdLoHienTai = LocalDate.parse(lo.getHanSuDung());
                    LocalDate hsdLoNhoNhat = LocalDate.parse(loSapHetHan.getHanSuDung());
                    if (hsdLoHienTai.isBefore(hsdLoNhoNhat)) {
                        loSapHetHan = lo;
                    }
                }

                listKetQua.add(loSapHetHan);
                soLuongCan -= loSapHetHan.getSoLuong();

                listLoKhaDung.remove(loSapHetHan);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listKetQua;
    }

    public HashMap<LoNguyenLieu, Double> capNhapTonKhoSauKhiBan(Connection conn, NguyenLieu nguyenLieu,
            double soLuongCan) {
        HashMap<LoNguyenLieu, Double> mapHangHoa = new HashMap<>();
        ArrayList<LoNguyenLieu> listLoNLCanSLy = layLoNguyenLieuDeBan(nguyenLieu.getMaNL(), soLuongCan);

        for (LoNguyenLieu loNL : listLoNLCanSLy) {
            if (soLuongCan <= 0)
                break;

            double slTrongLo = loNL.getSoLuong();
            double slLayRa;

            if (soLuongCan >= slTrongLo) {
                slLayRa = slTrongLo;
                xoaLoNguyenLieu(conn, loNL.getMaLoNL());
                soLuongCan -= slTrongLo;
            } else {
                slLayRa = soLuongCan;
                truSoLuongLo(conn, loNL.getMaLoNL(), soLuongCan);
                soLuongCan = 0;
            }

            mapHangHoa.put(loNL, mapHangHoa.getOrDefault(loNL, 0.0) + slLayRa);

            System.out.println("Vui lòng lấy " + slLayRa + " nguyên liệu " + nguyenLieu.getTenNL() +
                    " ở lô có mã: " + loNL.getMaLoNL() + " để sử dụng.");
        }

        return mapHangHoa;
    }

    public ArrayList<LoNguyenLieu> layLoChoNguyenLieu(String maNL) {
        if (canUpdate || listLoNguyenLieu == null) {
            khoitao();
            canUpdate = false;
        }

        ArrayList<LoNguyenLieu> list = new ArrayList<>();
        for (LoNguyenLieu lo : listLoNguyenLieu) {
            if (lo.getMaNL().equals(maNL)) {
                list.add(lo);
            }
        }

        return list;
    }

    public int layTongLoHetHan() {
        if (canUpdate || listLoNguyenLieu == null) {
            khoitao();
            canUpdate = false;
        }
        int tong = 0;
        try {
            for (LoNguyenLieu loNguyenLieu : listLoNguyenLieu) {
                LocalDate ngayHetHang = LocalDate.parse(loNguyenLieu.getHanSuDung());
                if (ngayHetHang.isBefore(LocalDate.now())) {
                    tong++;
                }
            }
            return tong;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public double laySoLuongNguyenLieuTrongKho(String ma) {
        double tong = 0;
        LocalDate homNay = LocalDate.now();
        for (LoNguyenLieu lo : listLoNguyenLieu) {
            if (lo.getMaNL().equals(ma)) {
                LocalDate ngayHetHan = LocalDate.parse(lo.getHanSuDung());
                if (ngayHetHan.isAfter(homNay)) {
                    tong += lo.getSoLuong();
                }
            }
        }
        return tong;
    }

    public boolean capNhapLoNguyenLieu(LoNguyenLieu loNguyenLieu) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);

            if (!loNguyenLieuDAO.capNhapLoNguyenLieu(loNguyenLieu, conn)) {
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

    public boolean themLoNguyenLieu(LoNguyenLieu loNguyenLieu, Connection conn) {
        try {
            conn.setAutoCommit(false);

            if (!loNguyenLieuDAO.themLoNguyenLieu(loNguyenLieu, conn)) {
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

    public ArrayList<LoNguyenLieu> layLoNguyenLieuChoPhieuNhap(String maPN) {
        if (canUpdate || listLoNguyenLieu == null) {
            khoitao();
            canUpdate = false;
        }
        ArrayList<LoNguyenLieu> list = new ArrayList<>();
        for (LoNguyenLieu loNguyenLieu : listLoNguyenLieu) {
            if (loNguyenLieu.getMaPN().equals(maPN)) {
                list.add(loNguyenLieu);
            }
        }
        return list;
    }

    public boolean xacNhanLoNguyenLieu(LoNguyenLieu loNguyenLieu, Connection conn) {
        try {
            conn.setAutoCommit(false);

            if (!loNguyenLieuDAO.xacNhanLoNguyenLieu(loNguyenLieu, conn)) {
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
            try {
                canUpdate = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean xoaLoNguyenLieu(LoNguyenLieu loNguyenLieu, Connection conn) {
        try {
            conn.setAutoCommit(false);

            if (!loNguyenLieuDAO.xoaLoNguyenLieu(loNguyenLieu, conn)) {
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
            try {
                canUpdate = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean duSoLuongTrongKho(NguyenLieu nguyenLieu, double soLuongCan) {
        if (canUpdate || listLoNguyenLieu == null) {
            khoitao();
            canUpdate = false;
        }
        double soLuong = 0;
        for (LoNguyenLieu loNguyenLieu : listLoNguyenLieu) {
            if (loNguyenLieu.getMaNL().equals(nguyenLieu.getMaNL())) {
                LocalDate ngayHetHang = LocalDate.parse(loNguyenLieu.getHanSuDung());
                if (ngayHetHang.isAfter(LocalDate.now())) {
                    soLuong += loNguyenLieu.getSoLuong();
                }
            }
        }
        return soLuong >= soLuongCan;
    }

    public int laySoLuongSanPhamPhaCheTrongKho(SanPham sanPham) {
        if (sanPham == null || sanPham.getCongThuc() == null)
            return 0;

        double soLyToiDa = Double.MAX_VALUE;

        for (ChiTietCongThuc ct : sanPham.getCongThuc().getListChiTietCongThuc()) {
            double tongTonNL = laySoLuongNguyenLieuTrongKho(ct.getNguyenLieu().getMaNL());
            double dinhMuc = ct.getSoLuong();

            if (dinhMuc > 0) {
                double khaNangPha = Math.floor(tongTonNL / dinhMuc);
                if (khaNangPha < soLyToiDa) {
                    soLyToiDa = khaNangPha;
                }
            }
        }
        return (int) (soLyToiDa == Double.MAX_VALUE ? 0 : soLyToiDa);
    }

    public boolean xoaLoNguyenLieu(Connection conn, String maLoNL) {
        try {
            boolean result = loNguyenLieuDAO.xoaLoNguyenLieu(conn, maLoNL);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean truSoLuongLo(Connection conn, String maLoNL, double soLuongTru) {
        try {

            boolean result = loNguyenLieuDAO.truSoLuongLo(conn, maLoNL, soLuongTru);

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