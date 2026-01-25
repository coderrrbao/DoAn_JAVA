package bus;

import java.util.ArrayList;

import dao.DanhMucDao;
import dao.SanPhamDAO;
import dto.DanhMuc;
import dto.SanPham;
import util.XuLyExcel;

public class SanPhamBUS {
    SanPhamDAO quanLySanPhamDAO = new SanPhamDAO();
    SizeBUS sizeBUS = new SizeBUS();
    CongThucBUS congThucBUS = new CongThucBUS();
    ArrayList<SanPham> listSanPham;

    public SanPhamBUS() {
        reload();
    }

    public void reload() {
        listSanPham = quanLySanPhamDAO.layListSanPham();
        for (SanPham sanPham : listSanPham) {
            if (sanPham.getLoaiNuoc().equals("Pha chế")) {
                sanPham.setListSize(sizeBUS.laySizeChoSP(sanPham.getMaSP()));
                sanPham.setCongThuc(congThucBUS.timCongThucChoSP(sanPham.getMaSP()));
            }
        }
    }

    public ArrayList<SanPham> layListSanPham() {
        return listSanPham;
    }

    public int getTongSoTrang(int pageSize) {
        return (int) Math.ceil((double) listSanPham.size() / pageSize);
    }

    public ArrayList<SanPham> layTrang(int page, int pageSize) {
        ArrayList<SanPham> kq = new ArrayList<>();
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, listSanPham.size());

        if (start >= listSanPham.size()) return kq;

        for (int i = start; i < end; i++) {
            kq.add(listSanPham.get(i));
        }
        return kq;
    }

    public ArrayList<String> layLuaChonNCC() {
        ArrayList<String> list = quanLySanPhamDAO.layLuaChonNCC();
        return list;
    }

    public ArrayList<String> layLuaChonDanhMuc() {
        DanhMucDao danhMucDao = new DanhMucDao();
        return danhMucDao.layLuaChonDanhMuc();
    }

    public ArrayList<DanhMuc> layDanhMucDangHoatDong() {
        DanhMucDao danhMucDao = new DanhMucDao();
        return danhMucDao.layDanhMucDangHoatDong();
    }


    public boolean xuatFileExcel() {
        return XuLyExcel.xuatFile(layListSanPham());
    }

    public SanPham timSanPham(String ma) {
        SanPham sanPham = quanLySanPhamDAO.timSanPham(ma);
        if (sanPham.getLoaiNuoc().equals("Pha chế")) {
            sanPham.setListSize(sizeBUS.laySizeChoSP(sanPham.getMaSP()));
            sanPham.setCongThuc(congThucBUS.timCongThucChoSP(sanPham.getMaSP()));
        }
        return sanPham;
    }

    public ArrayList<SanPham> locSanPham(String loai, String maDM) {
        ArrayList<SanPham> list = SanPhamDAO.locSanPham(loai, maDM);

        // nếu là pha chế thì gắn size + công thức
        for (SanPham sp : list) {
            if ("Pha chế".equals(sp.getLoaiNuoc())) {
                sp.setListSize(sizeBUS.laySizeChoSP(sp.getMaSP()));
                sp.setCongThuc(congThucBUS.timCongThucChoSP(sp.getMaSP()));
            }
        }
        return list;
    }


    public Boolean themSanPham(SanPham  sanPham){
        return true;
    }
    public Boolean XoaSanPham(SanPham sanPham){
        return true;
    }
    public boolean capNhapSanPham(SanPham sanPham){
        return true;
    }
}
