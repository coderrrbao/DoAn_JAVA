package bus;

import java.util.ArrayList;

import dao.DanhMucDao;
import dto.DanhMuc;

public class DanhMucBUS {
    private DanhMucDao danhMucDao = new DanhMucDao();
    public DanhMuc timDanhMuc(String ma){
        return danhMucDao.timDanhMuc(ma);
    }
    public DanhMuc timDanhMucTheoTen(String ten){
        return danhMucDao.timDanhMucTheoTen(ten);
    }
    public ArrayList<DanhMuc> layDanhMucDangHoatDong(){
        return danhMucDao.layDanhMucDangHoatDong();
    }
}
