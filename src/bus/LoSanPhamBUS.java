package bus;

import java.util.ArrayList;

import dao.LoSanPhamDAO;
import dto.LoSanPham;

public class LoSanPhamBUS {
    private LoSanPhamDAO loSanPhamDAO = new LoSanPhamDAO();

    public ArrayList<LoSanPham> layListLoSanPham() {
        return loSanPhamDAO.layListLoSanPham();
    }
}
