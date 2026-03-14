package bus;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.swing.JOptionPane;

import dao.SanPhamDAO;
import dao.conection.DBConnection;
import dto.ChiTietCongThuc;
import dto.SanPham;
import dto.Size;
import util.XuLyExcel;

public class SanPhamBUS {

    private static SanPhamBUS sanPhamBUS = null;

    public static SanPhamBUS getSanPhamBUS() {
        if (sanPhamBUS == null) {
            sanPhamBUS = new SanPhamBUS();
        }
        return sanPhamBUS;
    }

    SanPhamDAO sanPhamDAO = new SanPhamDAO();
    SizeBUS sizeBUS = SizeBUS.getSizeBUS();
    CongThucBUS congThucBUS = CongThucBUS.getCongThucBUS();
    ArrayList<SanPham> listSanPham;

    boolean canUpdate = false;

    public SanPhamBUS() {
        khoitao();
    }

    public void khoitao() {
        listSanPham = sanPhamDAO.layListSanPham();
        for (SanPham sanPham : listSanPham) {
            if (sanPham.getLoaiNuoc().equals("Pha chế")) {
                sanPham.setListSize(sizeBUS.laySizeChoSP(sanPham.getMaSP()));
                sanPham.setCongThuc(congThucBUS.timCongThucChoSP(sanPham.getMaSP()));
            }
        }
    }

    public ArrayList<SanPham> layListSanPham() {
        if (canUpdate || listSanPham == null) {
            canUpdate = false;
            khoitao();
        }
        return listSanPham;
    }

    public int getTongSoTrang(int pageSize) {
        if (canUpdate || listSanPham == null) {
            khoitao();
        }
        return (int) Math.ceil((double) listSanPham.size() / pageSize);
    }

    public ArrayList<SanPham> layTrang(int page, int pageSize) {
        if (canUpdate || listSanPham == null) {
            canUpdate = false;
            khoitao();
        }
        ArrayList<SanPham> kq = new ArrayList<>();
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, listSanPham.size());

        if (start >= listSanPham.size())
            return kq;

        for (int i = start; i < end; i++) {
            kq.add(listSanPham.get(i));
        }
        return kq;
    }

    public SanPham timSanPham(String ma) {
        if (canUpdate || listSanPham == null) {
            khoitao();
            canUpdate = false;
        }
        for (SanPham sanPham : listSanPham) {
            if (sanPham.getMaSP().equals(ma)) {
                return sanPham;
            }
        }
        return null;
    }

    public SanPham timSanPhamTheoTen(String ten) {
        if (canUpdate || listSanPham == null) {
            khoitao();
            canUpdate = false;
        }
        String tenGoc = ten.replace("  ->", "");

        if (tenGoc.contains(" (")) {
            tenGoc = tenGoc.substring(0, tenGoc.lastIndexOf(" ("));
        }
        for (SanPham sanPham : listSanPham) {
            if (sanPham.getTenSP().equals(tenGoc)) {
                return sanPham;
            }
        }
        return null;
    }

    public Boolean themSanPham(SanPham sanPham) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);

            CongThucBUS congThucBUS = CongThucBUS.getCongThucBUS();
            SizeBUS sizeBUS = SizeBUS.getSizeBUS();

            if (!sanPhamDAO.themSanPham(sanPham, conn)) {
                throw new SQLException();
            }

            sanPham.getCongThuc().setMaSp(sanPham.getMaSP());
            if (!congThucBUS.themCongThuc(sanPham.getCongThuc(), conn)) {
                throw new SQLException();
            }

            for (Size size : sanPham.getListSize()) {
                size.setMaSP(sanPham.getMaSP());
                if (!sizeBUS.themSize(size, conn)) {
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

    public String layMaSanPhamKhaDung() {
        return sanPhamDAO.layMaSanPhamKhaDung(null);
    }

    public boolean suaCanhBao(SanPham sanPham) {
        return sanPhamDAO.capNhatMucCanhBao(sanPham);
    }

    public Boolean XoaSanPham(String maSp) {
        if (!sanPhamDAO.xoaSanPham(maSp)) {
            return false;
        }
        canUpdate = true;
        return true;
    }

    public boolean capNhapSanPham(SanPham sanPham, SanPham sanPhamMoi) {
        Connection conn = DBConnection.getConnection();

        try {
            conn.setAutoCommit(false);
            if (sanPham.getCongThuc() != null) {
                ChiTietCongThucBUS chiTietCongThucBUS = ChiTietCongThucBUS.getInstance();
                Map<String, ChiTietCongThuc> map = new HashMap<>();
                for (ChiTietCongThuc chiTietCongThuc : sanPhamMoi.getCongThuc().getListChiTietCongThuc()) {
                    if (chiTietCongThuc.getMaCTCT().equals("")) {
                        chiTietCongThuc.setMaCT(sanPham.getCongThuc().getMaCT());
                        if (!chiTietCongThucBUS.themCTCT(chiTietCongThuc, conn)) {
                            throw new SQLException();
                        }
                    }
                    map.put(chiTietCongThuc.getMaCTCT(), chiTietCongThuc);
                }
                for (ChiTietCongThuc chiTietCongThuc : sanPham.getCongThuc().getListChiTietCongThuc()) {
                    if (!map.containsKey(chiTietCongThuc.getMaCTCT())) {
                        if (!chiTietCongThucBUS.xoaCTCT(chiTietCongThuc, conn)) {
                            throw new SQLException();
                        }
                    } else {
                        ChiTietCongThuc chiTietCongThucMoi = map.get(chiTietCongThuc.getMaCTCT());
                        if (!chiTietCongThuc.getNguyenLieu().getMaNL()
                                .equals(chiTietCongThucMoi.getNguyenLieu().getMaNL())
                                || chiTietCongThuc.getSoLuong() != chiTietCongThucMoi.getSoLuong()) {
                            chiTietCongThucBUS.capNhapChiTietCongThuc(chiTietCongThucMoi, conn);
                            canUpdate = true;
                        }
                    }
                }

            } else {
                CongThucBUS congThucBUS = CongThucBUS.getCongThucBUS();
                if (sanPhamMoi.getCongThuc() != null && sanPhamMoi.getLoaiNuoc().equals("Pha chế")) {

                    sanPhamMoi.getCongThuc().setMaSp(sanPham.getMaSP());
                    if (!congThucBUS.themCongThuc(sanPhamMoi.getCongThuc(), conn)) {
                        throw new SQLException();
                    }

                }

            }
            Map<String, Size> map = new HashMap<>();
            if (sanPhamMoi.getListSize() != null) {

                SizeBUS sizeBUS = SizeBUS.getSizeBUS();
                for (Size size : sanPhamMoi.getListSize()) {
                    if (size.getMaSize().equals("")) {
                        if (!sizeBUS.themSize(size, conn)) {
                            throw new SQLException();
                        }
                    }

                    map.put(size.getMaSize(), size);
                }
            }
            if (sanPham.getListSize() != null) {
                for (Size size : sanPham.getListSize()) {
                    if (!map.containsKey(size.getMaSize())) {
                        if (!sizeBUS.xoaSize(size, conn)) {
                            throw new SQLException();
                        }
                    } else {
                        Size sizeMoi = map.get(size.getMaSize());
                        if (size.getPhanTramGia() != sizeMoi.getPhanTramGia()
                                || size.getPhanTramNL() != sizeMoi.getPhanTramNL()
                                || !size.getTenSize().equals(sizeMoi.getTenSize())) {
                            sizeBUS.capNhapSize(sizeMoi, conn);
                            canUpdate = true;
                        }
                    }
                }
            }

            if (!sanPhamDAO.capNhapSanPham(sanPhamMoi, conn)) {
                throw new SQLException();
            }
            conn.commit();

        } catch (SQLException e) {
            try {
                conn.rollback();

            } catch (Exception ex) {
                e.printStackTrace();
            }
            return false;

        } finally {
            try {
                conn.setAutoCommit(true);
                conn.close();
                canUpdate = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static String xoaDau(String text) {
        if (text == null)
            return "";
        java.text.Normalizer.Form form = java.text.Normalizer.Form.NFD;
        return java.text.Normalizer.normalize(text, form)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase();
    }

    public ArrayList<SanPham> locSanPham(String ten, String loai, String maDM) {
        if (canUpdate || listSanPham == null) {
            canUpdate = false;
            khoitao();
        }

        ArrayList<SanPham> ketQua = new ArrayList<>();
        String tuKhoaChuanHoa = xoaDau(ten != null ? ten.trim() : "");

        for (SanPham sp : listSanPham) {
            String tenSP = sp.getTenSP() != null ? sp.getTenSP() : "";
            boolean khopTen = xoaDau(tenSP).contains(tuKhoaChuanHoa);

            boolean khopLoai = (loai == null || loai.equals("Tất cả")) ||
                    (sp.getLoaiNuoc() != null && sp.getLoaiNuoc().equals(loai));

            boolean khopDM = (maDM == null || maDM.equals("Tất cả")) ||
                    (sp.getDanhMuc() != null && sp.getDanhMuc().getMaDM() != null
                            && sp.getDanhMuc().getMaDM().equals(maDM));

            if (khopTen && khopLoai && khopDM) {
                ketQua.add(sp);
            }
        }
        return ketQua;
    }

    public boolean xuatExcel(File file) {
        return XuLyExcel.xuatFileSanPham(file, this.layListSanPham());
    }

    public boolean themSanPham(SanPham sanPham, Connection conn) throws SQLException {
        CongThucBUS congThucBUS = CongThucBUS.getCongThucBUS();
        SizeBUS sizeBUS = SizeBUS.getSizeBUS();

        if (!sanPhamDAO.themSanPham(sanPham, conn)) {
            return false;
        }

        if (sanPham.getCongThuc() != null && sanPham.getLoaiNuoc().equals("Pha chế")) {
            sanPham.getCongThuc().setMaSp(sanPham.getMaSP());
            if (!congThucBUS.themCongThuc(sanPham.getCongThuc(), conn)) {
                return false;
            }
        }

        if (sanPham.getListSize() != null) {
            for (Size size : sanPham.getListSize()) {
                size.setMaSP(sanPham.getMaSP());
                if (!sizeBUS.themSize(size, conn)) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean nhapExcel(File file) {

        ArrayList<SanPham> listSanPham = XuLyExcel.nhapFileSanPham(file);
        if (listSanPham == null || listSanPham.isEmpty()) {
            return false;
        }

        HashSet<String> setTenSp = new HashSet<>();
        for (SanPham sanPham : layListSanPham()) {
            setTenSp.add(sanPham.getTenSP());
        }

        for (SanPham sanPham : listSanPham) {
            if (setTenSp.contains(sanPham.getTenSP())) {
                JOptionPane.showMessageDialog(null, "Sản phẩm đã tồn tại: " + sanPham.getTenSP());
                return false;
            }
        }

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            for (SanPham sanPham : listSanPham) {

                if (!themSanPham(sanPham, conn)) {
                    throw new SQLException("Lỗi khi thêm sản phẩm: " + sanPham.getTenSP());
                }
            }

            conn.commit();
            this.canUpdate = true;
            this.khoitao();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
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

    public void setCanUpdate(boolean canUpdate) {
        this.canUpdate = canUpdate;
    }
}