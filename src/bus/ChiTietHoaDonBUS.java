package bus;

import java.util.ArrayList;

import dao.ChiTietHoaDonDAO;
import dto.ChiTietHoaDon;

public class ChiTietHoaDonBUS {
    private ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO();

    public ArrayList<ChiTietHoaDon> layListChiTietHoaDon() {
        return chiTietHoaDonDAO.layListChiTietHoaDon();
    }
}
