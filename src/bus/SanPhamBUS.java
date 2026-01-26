package bus;

import java.util.ArrayList;

import dao.DanhMucDao;
import dao.SanPhamDAO;
import dto.SanPham;
import dto.Size;
import util.XuLyExcel;

public class SanPhamBUS {
    SanPhamDAO sanPhamDAO = new SanPhamDAO();
    SizeBUS sizeBUS = new SizeBUS();
    CongThucBUS congThucBUS = new CongThucBUS();
    ArrayList<SanPham> listSanPham;

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
        return listSanPham;
    }

    public int getTongSoTrang(int pageSize) {
        return (int) Math.ceil((double) listSanPham.size() / pageSize);
    }

    public ArrayList<SanPham> layTrang(int page, int pageSize) {
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

    public ArrayList<String> layLuaChonDanhMuc() {
        DanhMucDao danhMucDao = new DanhMucDao();
        return danhMucDao.layLuaChonDanhMuc();
    }

    public boolean xuatFileExcel() {
        return XuLyExcel.xuatFile(layListSanPham());
    }

    public SanPham timSanPham(String ma) {
        SanPham sanPham = sanPhamDAO.timSanPham(ma);
        if (sanPham == null)
            return null;
        if (sanPham.getLoaiNuoc().equals("Pha chế")) {
            sanPham.setListSize(sizeBUS.laySizeChoSP(sanPham.getMaSP()));
            sanPham.setCongThuc(congThucBUS.timCongThucChoSP(sanPham.getMaSP()));
        }
        return sanPham;
    }

    public Boolean themSanPham(SanPham sanPham) {
        sanPhamDAO.themSanPham(sanPham);

        CongThucBUS congThucBUS = new CongThucBUS();
        SizeBUS sizeBUS = new SizeBUS();

        sanPham.getCongThuc().setMaSp(sanPham.getMaSP());
        congThucBUS.themCongThuc(sanPham.getCongThuc());

        for (Size size : sanPham.getListSize()) {
            size.setMaSP(sanPham.getMaSP());
            sizeBUS.themSize(size);
        }
        
        return true;
    }

    public Boolean XoaSanPham(SanPham sanPham) {
        return true;
    }

    public boolean capNhapSanPham(SanPham sanPham) {
        return true;
    }
}
