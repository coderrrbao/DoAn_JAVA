package bus;
import java.sql.Timestamp;
import java.util.ArrayList;
import dao.HoaDonDAO;
import dao.ChiTietHoaDonDAO;
import dao.SanPhamDAO;
import dto.ChiTietHoaDon;
import dto.HoaDon;

public class HoaDonBUS {
    private HoaDonDAO hoaDonDAO = new HoaDonDAO();
    private ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO();
    private SanPhamDAO sanPhamDAO = new SanPhamDAO();

    public boolean ThanhToan(HoaDon hd) {
        if (!hoaDonDAO.themHoaDon(hd)) {
            return false;
        }

        for (ChiTietHoaDon ct : hd.getListChiTietHoaDon()) {
            if (!chiTietHoaDonDAO.themChiTietHoaDon(ct)) {
                return false;
            }
        }

        return true;
    }

    public String taoMaHoaDonMoi() {
        String maCuoi = hoaDonDAO.layMaHoaDonCuoiCung();

        if (maCuoi == null) {
            return "HD001";
        }

        try {
            String phanSo = maCuoi.replaceAll("[^0-9]", "");
            int soThuTu = Integer.parseInt(phanSo);

            soThuTu++;

            return String.format("HD%03d", soThuTu);
        } catch (NumberFormatException e) {
            System.out.println("Lỗi parse mã cũ: " + maCuoi);
            return "HD" + System.currentTimeMillis();
        }
    }
}
