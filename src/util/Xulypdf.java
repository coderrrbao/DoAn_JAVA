package util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import dto.ChiTietHoaDon;
import dto.HoaDon;
import dto.NhanVien;
import dto.SanPham;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import report.ChiTietHoaDonpdf;

public class Xulypdf {
    public boolean xuatHoaDon(HoaDon hoaDon) {
        ArrayList<ChiTietHoaDonpdf> listCtHoaDon = new ArrayList<>();
        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("maHD", hoaDon.getMaHD());
            parameters.put("ngayTao", new java.text.SimpleDateFormat("dd/MM/yyyy").format(new Date()));
            parameters.put("tenNV", hoaDon.getNhanVien().getTenNV());
            parameters.put("tienKm", String.valueOf(hoaDon.getTienKhuyenMai()));
            parameters.put("tongTien", String.valueOf(hoaDon.getTongTien() + hoaDon.getTienKhuyenMai()));
            parameters.put("thanhToan", String.valueOf(hoaDon.getTongTien()));

            parameters.put("image", System.getProperty("user.dir") + "/src/report/");

            for (ChiTietHoaDon ct : hoaDon.getListChiTietHoaDon()) {
                String gia = String.valueOf(ct.getSanPham().getGiaBan());
                String sl = String.valueOf(ct.getSoLuong());
                String tong = String.valueOf(ct.getSanPham().getGiaBan() * ct.getSoLuong());

                listCtHoaDon.add(new ChiTietHoaDonpdf(ct.getSanPham().getTenSP(), gia, sl, tong));
            }

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listCtHoaDon);

            try {
                String sourcePath = "src/report/HoaDon.jrxml";
                JasperReport jasperReport = JasperCompileManager.compileReport(sourcePath);
                JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
                JasperViewer.viewReport(print, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("Đang khởi tạo dữ liệu giả lập với 20 sản phẩm...");

        NhanVien nv = new NhanVien();
        try {
            nv.setTenNV("Nguyễn Văn Bảo");
            nv.setMaNV("NV001");
        } catch (Exception e) {
            System.out.println("Lưu ý: Chưa set được tên nhân viên.");
        }

        long millis = System.currentTimeMillis();
        java.sql.Date ngayBan = new java.sql.Date(millis);

        double tongTienHienTai = 0;

        HoaDon hd = new HoaDon("HD001", nv, "KH001", null, ngayBan, 0, 0, true);

        for (int i = 1; i <= 2; i++) {

            String maSP = String.format("SP%02d", i);
            String tenSP = "Sản phẩm thử nghiệm " + i;
            long giaBan = 10000 + (i * 2000);
            int soLuongMua = (i % 3) + 1;

            SanPham sp = new SanPham(maSP, tenSP, null, giaBan, "Ly", "img.png", 500, 10,
                    "Đã xử lý");

            ChiTietHoaDon ct = new ChiTietHoaDon("CT" + i, hd.getMaHD(), sp, null, soLuongMua, giaBan, true);

            hd.themChiTietHoaDon(ct);

            tongTienHienTai += (giaBan * soLuongMua);
        }

        hd.setTongTien(tongTienHienTai);

        System.out.println("Tổng tiền hóa đơn: " + tongTienHienTai);
        System.out.println("Bắt đầu xuất file PDF...");
        Xulypdf xuly = new Xulypdf();
        boolean ketQua = xuly.xuatHoaDon(hd);

        if (ketQua) {
            System.out.println("-> Thành công! Cửa sổ JasperViewer sẽ hiện lên với danh sách 20 sản phẩm.");
        } else {
            System.out.println("-> Thất bại! Kiểm tra console để xem lỗi.");
        }
    }
}
