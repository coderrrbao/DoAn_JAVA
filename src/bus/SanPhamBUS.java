package bus;

import java.util.ArrayList;

import dao.DanhMucDao;
import dao.SanPhamDAO;
import dto.SanPham;
import util.XuLyExcel;

public class SanPhamBUS {
    SanPhamDAO quanLySanPhamDAO = new SanPhamDAO();
    SizeBUS sizeBUS = new SizeBUS();
    CongThucBUS congThucBUS = new CongThucBUS();

    public ArrayList<SanPham> layListSanPham() {
        ArrayList<SanPham> listSanPham = quanLySanPhamDAO.layListSanPham();

        for (SanPham sanPham : listSanPham) {
            if (sanPham.getLoaiNuoc().equals("Pha chế")) {
                sanPham.setListSize(sizeBUS.laySizeChoSP(sanPham.getMaSP()));

                sanPham.setCongThuc(congThucBUS.timCongThucChoSP(sanPham.getMaSP()));
            }
        }
        return listSanPham;
    }

    public ArrayList<String> layLuaChonNCC() {
        ArrayList<String> list = quanLySanPhamDAO.layLuaChonNCC();
        return list;
    }

    public ArrayList<String> layLuaChonDanhMuc() {
        DanhMucDao danhMucDao = new DanhMucDao();
        return danhMucDao.layLuaChonDanhMuc();
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

}
