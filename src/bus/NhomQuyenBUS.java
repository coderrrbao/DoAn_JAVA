package bus;

import java.util.ArrayList;

import dao.NhomQuyenDAO;
import dto.NhomQuyen;

public class NhomQuyenBUS {
    NhomQuyenDAO nhomQuyenDAO = new NhomQuyenDAO();
    //lay list 
    public ArrayList<NhomQuyen> layDanhSachNhomQuyen_BUS(){
        return nhomQuyenDAO.layDanhSachNhomQuyen_Dao();
    }
}
