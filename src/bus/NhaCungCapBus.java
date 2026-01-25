package bus;

import java.util.ArrayList;

import dao.NhaCungCapDAO;
import dto.NhaCungCap;

public class NhaCungCapBUS {
    private NhaCungCapDAO nhaCungCapDAO = new NhaCungCapDAO();

    public NhaCungCap timNhaCungCap(String ma) {
        return nhaCungCapDAO.timNhaCungCap(ma);
    }public NhaCungCap timNhaCungCapTheoTen(String ten) {
        return nhaCungCapDAO.timNhaCungCapTheoTen(ten);
    }

    public ArrayList<NhaCungCap> laylistNhaCungCap(){
        return nhaCungCapDAO.layListNhaCungCap();
    }
}
