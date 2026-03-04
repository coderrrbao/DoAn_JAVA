package bus;

import java.util.ArrayList;
import dao.QuyenDAO;
import dto.PhanQuyen;
import dto.Quyen;

public class QuyenBUS {
    private static QuyenBUS quyenBUS;
    private QuyenDAO quyenDao = new QuyenDAO();
    private ArrayList<Quyen> listQuyen;
    private boolean canUpdate = true;

    public static QuyenBUS getQuyenBUS() {
        if (quyenBUS == null) {
            quyenBUS = new QuyenBUS();
        }
        return quyenBUS;
    }

    public QuyenBUS() {
        khoiTao();
    }

    public void khoiTao() {
        listQuyen = quyenDao.layListQuyen();
    }

    public ArrayList<Quyen> layDanhSachQuyen() {
        if (canUpdate || listQuyen == null) {
            khoiTao();
            canUpdate = false;
        }
        return listQuyen;
    }

    public ArrayList<Quyen> layQuyenChoNhomQuyen(String maNQ) {
        ArrayList<Quyen> listQuyen = new ArrayList<>();
        PhanQuyenBUS phanQuyenBUS = PhanQuyenBUS.getPhanQuyenBUS();
        for (PhanQuyen phanQuyen : phanQuyenBUS.layDanhSachPhanQuyen()) {
            if (phanQuyen.getMaNQ().equals(maNQ)) {
                Quyen quyen = timTheoMa(phanQuyen.getMaQuyen());
                listQuyen.add(quyen);
            }
        }
        return listQuyen;
    }

    public Quyen timQuyenTheoTen(String tenQuyen) {
        if (canUpdate || listQuyen == null) {
            khoiTao();
            canUpdate = false;
        }
        for (Quyen q : listQuyen) {
            if (q.getTenQuyen().equals(tenQuyen)) {
                return q;
            }
        }
        return null;
    }

    public Quyen timTheoMa(String maQuyen) {
        if (canUpdate || listQuyen == null) {
            khoiTao();
            canUpdate = false;
        }
        for (Quyen q : listQuyen) {
            if (q.getMaQuyen().equals(maQuyen)) {
                return q;
            }
        }
        return null;
    }
}