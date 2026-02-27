package bus;

import java.util.*;

import dao.NhanVienDAO;

public class NhanVienBUS {
    private NhanVienDAO dao = new NhanVienDAO();

    public List<String> layDanhSachChucVu() {
        return dao.layDanhSachChucVu();
    }

    public void
}
