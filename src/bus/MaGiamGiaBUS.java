package bus;

import dao.MaGiamGiaDAO;
import dto.MaGiamGia;
import java.util.Date;

public class MaGiamGiaBUS {

    private MaGiamGiaDAO maGiamGiaDAO = new MaGiamGiaDAO();

    public MaGiamGia timMaGiamGia(String maKM) {
        if (maKM == null || maKM.trim().isEmpty()) {
            return null;
        }
        return maGiamGiaDAO.getMaGiamGiatheoMa(maKM.trim());
    }

    public String kiemTraTrangThaiHopLe(MaGiamGia mgg) {
        if (mgg == null) {
            return "Mã giảm giá không tồn tại!";
        }
        if (!mgg.getTrangThai()) {
            return "Mã giảm giá này đã bị khóa hoặc ngừng áp dụng!";
        }
        long currentTime = System.currentTimeMillis();

        if (mgg.getTuNgay() != null && mgg.getDenNgay() != null) {
            long timeStart = mgg.getTuNgay().getTime();
            long timeEnd = mgg.getDenNgay().getTime() + 86399999;

            if (currentTime < timeStart) {
                return "Mã giảm giá này chưa đến thời gian bắt đầu áp dụng!";
            }
            if (currentTime > timeEnd) {
                return "Mã giảm giá này đã hết hạn sử dụng!";
            }
        }
        return "";
    }

    public java.util.ArrayList<MaGiamGia> layDanhSachKhuyenMai() {
        return maGiamGiaDAO.layDanhSachKhuyenMai();
    }
}