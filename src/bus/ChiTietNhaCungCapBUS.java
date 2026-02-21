package bus;

import java.util.ArrayList;

import dao.ChiTietNhaCungCapDAO;
import dto.ChiTietNhaCungCap;

public class ChiTietNhaCungCapBUS {

    // 1. Áp dụng Singleton Pattern
    private static ChiTietNhaCungCapBUS chiTietNhaCungCapBUS = null;

    public static ChiTietNhaCungCapBUS getChiTietNhaCungCapBUS() {
        if (chiTietNhaCungCapBUS == null) {
            chiTietNhaCungCapBUS = new ChiTietNhaCungCapBUS();
        }
        return chiTietNhaCungCapBUS;
    }

    private ChiTietNhaCungCapDAO chiTietNhaCungCapDAO = new ChiTietNhaCungCapDAO();

    // 2. Khai báo Mảng lưu trữ (Cache)
    private ArrayList<ChiTietNhaCungCap> listChiTietNhaCungCap;
    private boolean canUpdate = false;

    // Constructor
    public ChiTietNhaCungCapBUS() {
        khoitao();
    }

    // Hàm load dữ liệu từ Database (thông qua DAO) vào mảng
    public void khoitao() {
        listChiTietNhaCungCap = chiTietNhaCungCapDAO.layListChiTietNhaCungCap();
    }

    // ========================================================
    // HÀM LẤY LIST MÀ BẠN YÊU CẦU
    // ========================================================


   public ArrayList<ChiTietNhaCungCap> layListChiTietNhaCungCap() {
        if (canUpdate || listChiTietNhaCungCap == null) {
            canUpdate = false;
            khoitao();
        }
        return listChiTietNhaCungCap;
    } 

    public void setCanUpdate(boolean canUpdate) {
        this.canUpdate = canUpdate;
    }
}