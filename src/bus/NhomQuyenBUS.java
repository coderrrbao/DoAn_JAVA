package bus;

import java.util.ArrayList;
import dao.NhomQuyenDAO;
import dto.NhomQuyen;

public class NhomQuyenBUS {
    private static NhomQuyenBUS nhomQuyenBUS;
    private NhomQuyenDAO nhomQuyenDAO = new NhomQuyenDAO();
    private ArrayList<NhomQuyen> listNhomQuyen;
    private boolean canUpdate = true;

    public static NhomQuyenBUS getNhomQuyenBUS() {
        if (nhomQuyenBUS == null) {
            nhomQuyenBUS = new NhomQuyenBUS();
        }
        return nhomQuyenBUS;
    }

    public NhomQuyenBUS() {
        khoiTao();
    }

    public void khoiTao() {
        listNhomQuyen = nhomQuyenDAO.layDanhSachNhomQuyen_Dao();
    }

    public ArrayList<NhomQuyen> layDanhSachNhomQuyen() {
        if (canUpdate || listNhomQuyen == null) {
            khoiTao();
            canUpdate = false;
        }
        return listNhomQuyen;
    }

    public NhomQuyen timNhomQuyen(String maNQ) {

        if (canUpdate || listNhomQuyen == null) {
            khoiTao();
            canUpdate = false;
        }

        for (NhomQuyen nq : listNhomQuyen) {
            if (nq.getMaNQ().equals(maNQ)) {
                return nq;
            }
        }
        return null;
    }

    public void setCanUpdate(boolean canUpdate) {
        this.canUpdate = canUpdate;
    }
}