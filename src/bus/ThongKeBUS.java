package bus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dao.HoaDonDAO;
import dto.ChiTietHoaDon;
import dto.DanhMuc;
import dto.SanPham;
import ui.thongke.ThongKeValue;

public class ThongKeBUS {
    private SanPhamBUS sanPhamBUS = SanPhamBUS.getSanPhamBUS();
    private HoaDonDAO hoaDonDAO = new HoaDonDAO();
    private ChiTietHoaDonBUS chiTietHoaDonBUS = new ChiTietHoaDonBUS();



    public Map<SanPham, Integer> layTop5_SanPhamBanChay() {
        Map<SanPham, Integer> result = new LinkedHashMap<>();
        int soSpCan = 5;
        for (Map.Entry<SanPham, Integer> entry : laySL_SP_BanRaGiamDan().entrySet()) {
            if (soSpCan <= 0) {
                break;
            }
            result.put(entry.getKey(), entry.getValue());
            soSpCan--;
        }
        return result;
    }

    public Map<DanhMuc, Integer> laySL_SP_BanRaTheoDanhMuc() {
        Map<DanhMuc, Integer> mapResult = new HashMap<>();

        for (Map.Entry<SanPham, Integer> entry : laySL_SP_BanRaGiamDan().entrySet()) {
            mapResult.put(entry.getKey().getDanhMuc(),
                    mapResult.getOrDefault(entry.getKey().getDanhMuc(), 0) + entry.getValue());
        }
        return mapResult;
    }

    public Map<SanPham, Integer> laySL_SP_BanRaGiamDan() {
        Map<SanPham, Integer> result = new LinkedHashMap<>();

        Map<String, Integer> mapSanPham = new HashMap<>();
        ArrayList<ChiTietHoaDon> listChiTietHoaDon = chiTietHoaDonBUS.layListChiTietHoaDon();

        for (ChiTietHoaDon chiTietHoaDon : listChiTietHoaDon) {
            mapSanPham.put(chiTietHoaDon.getSanPham().getMaSP(),
                    mapSanPham.getOrDefault(chiTietHoaDon.getSanPham().getMaSP(), 0) + chiTietHoaDon.getSoLuong());
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<>(mapSanPham.entrySet());
        list.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        for (Map.Entry<String, Integer> entry : list) {
            String maSP = entry.getKey();
            Integer soLuong = entry.getValue();
            result.put(sanPhamBUS.timSanPham(maSP), soLuong);
        }
        return result;
    }

    public ArrayList<ThongKeValue> getThongKeTheoNgay(String ngay) {
        if (ngay == null || ngay.isEmpty()) {
            return new ArrayList<>();
        }
        return hoaDonDAO.layKeQuaThongKeTheoNgay(ngay);
    }

    public ArrayList<ThongKeValue> getThongKeTheoThang(int thang, int nam) {
        return hoaDonDAO.layKetQuaThongKeTheoThang(thang, nam);
    }

    public ArrayList<ThongKeValue> getThongKeTheoNam(int nam) {
        if (nam < 0) {
            return new ArrayList<>();
        }
        return hoaDonDAO.layKetQuaThongKeTheoNam(nam);
    }
}
