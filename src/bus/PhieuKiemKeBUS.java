package bus;

import java.util.ArrayList;

import dao.PhieuKiemKeDAO;
import dto.PhieuKiemKe;

public class PhieuKiemKeBUS {
    private PhieuKiemKeDAO phieuKiemKeDAO = new PhieuKiemKeDAO();

    public ArrayList<PhieuKiemKe> layListKiemKe() {
        return phieuKiemKeDAO.layListPhieuKiemKe();
    }

    public boolean themPhieuKiemKe(PhieuKiemKe phieuKiemKe) {
        return phieuKiemKeDAO.themPhieuKiemKe(phieuKiemKe);
    }
}
