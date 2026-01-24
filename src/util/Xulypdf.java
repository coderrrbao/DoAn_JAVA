package util;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import dto.ChiTietHoaDon;
import dto.HoaDon;
import dto.NhanVien;
import dto.SanPham;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
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
            parameters.put("tongTien", String.valueOf(hoaDon.getTongTien()));
            parameters.put("thanhToan", String.valueOf(hoaDon.getTongTien()));
            parameters.put("image", System.getProperty("user.dir")+"/src/report/");

            for (ChiTietHoaDon ct : hoaDon.getListChiTietHoaDon()) {
                String gia = String.valueOf(ct.getSanPham().getGiaBan());
                String sl = String.valueOf(ct.getSoLuong());
                String tong = String.valueOf(ct.getSanPham().getGiaBan() * ct.getSoLuong());

                listCtHoaDon.add(new ChiTietHoaDonpdf(ct.getSanPham().getTenSP(), gia, sl, tong));
            }

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listCtHoaDon);
            String reportPath = "src/report/HoaDon.jasper";
            JasperPrint print = JasperFillManager.fillReport(reportPath, parameters, dataSource);
            JasperViewer.viewReport(print, false);
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

        // 2. Tạo Hóa Đơn trước để lấy mã
        long millis = System.currentTimeMillis();
        java.sql.Date ngayBan = new java.sql.Date(millis);
        // Khởi tạo tổng tiền bằng 0, sẽ cộng dồn trong vòng lặp
        double tongTienHienTai = 0;

        HoaDon hd = new HoaDon("HD001", nv, "KH001", null, ngayBan, 0, 0, true);

        // 3. Vòng lặp tạo 20 Sản phẩm và 20 Chi tiết hóa đơn
        for (int i = 1; i <= 20; i++) {
            // Tạo mã và tên sp động: SP01, SP02...
            String maSP = String.format("SP%02d", i);
            String tenSP = "Sản phẩm thử nghiệm " + i;
            long giaBan = 10000 + (i * 2000); // Giá tăng dần để test format tiền
            int soLuongMua = (i % 3) + 1; // Số lượng ngẫu nhiên từ 1 đến 3

            // Khởi tạo Sản phẩm
            SanPham sp = new SanPham(maSP, tenSP, null, giaBan - 5000, giaBan, null, 100, "Ly", "img.png", 500, 10,
                    "Đã xử lý",true);

            // Tạo Chi tiết hóa đơn cho sản phẩm này
            // Constructor: maCT, maHD, sanPham, size, soLuong, gia, trangThai
            ChiTietHoaDon ct = new ChiTietHoaDon("CT" + i, hd.getMaHD(), sp, null, soLuongMua, giaBan, true);

            // Add vào hóa đơn
            hd.themChiTietHoaDon(ct);

            // Cộng dồn vào tổng tiền
            tongTienHienTai += (giaBan * soLuongMua);
        }

        // Cập nhật lại tổng tiền cuối cùng cho hóa đơn
        hd.setTongTien(tongTienHienTai);

        // 4. Gọi hàm xuất PDF
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
