package bus;

import java.util.ArrayList;

import dao.QuanLySanPhamDAO;
import dto.SanPham;

public class QuanLySanPhamBUS {
    QuanLySanPhamDAO quanLySanPhamDAO = new QuanLySanPhamDAO();
    public ArrayList <SanPham> layListSanPham(){
        return quanLySanPhamDAO.layListSanPham();
    }
    public ArrayList<String> layLuaChonNCC(){
        ArrayList<String> list = quanLySanPhamDAO.layLuaChonNCC();
        return list;
    }

}
