package bus;

import java.util.ArrayList;

import dao.ChiTietCongThucDAO;
import dto.ChiTietCongThuc;

public class ChiTietCongThucBUS {
    private ChiTietCongThucDAO chiTietCongThucDAO = new ChiTietCongThucDAO();
    public ArrayList<ChiTietCongThuc> laylistChiTietCongThuc(String maCT){
        return chiTietCongThucDAO.laylistChiTietCongThuc(maCT);
    }
}
