package bus;

import java.util.ArrayList;

import dao.DanhMucDao;
import dao.QuanLySanPhamDAO;
import dto.SanPham;
import util.XuLyExcel;

public class QuanLySanPhamBUS {
    QuanLySanPhamDAO quanLySanPhamDAO = new QuanLySanPhamDAO();
    public ArrayList <SanPham> layListSanPham(){
        return quanLySanPhamDAO.layListSanPham();
    }
    public ArrayList<String> layLuaChonNCC(){
        ArrayList<String> list = quanLySanPhamDAO.layLuaChonNCC();
        return list;
    }
    public ArrayList<String> layLuaChonDanhMuc(){
        DanhMucDao danhMucDao = new DanhMucDao();
        return danhMucDao.layLuaChonDanhMuc();
    }
    public boolean xuatFileExcel(){
        return XuLyExcel.xuatFile(layListSanPham());
    }
    public SanPham timSanPham(String ma){
        return quanLySanPhamDAO.timSanPham(ma);
    }

}
