package bus;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import dao.KhachHangDAO;
import dto.HangThanhVien;
import dto.KhachHang;

public class KhachHangBUS {

    private KhachHangDAO khachHangDAO = new KhachHangDAO();

    public KhachHang timTheoSDT(String sdt) {
        if (sdt == null || sdt.trim().isEmpty()) {
            return null;
        }
        return khachHangDAO.layKhachHangTheoSDT(sdt);
    }

    public ArrayList<KhachHang> getDanhSachKhachHang() {
        return khachHangDAO.layDanhSachKhachHang();
    }

    public String taoMaKHMoi() {
        java.util.ArrayList<KhachHang> ds = khachHangDAO.layDanhSachKhachHang();
        if (ds == null || ds.isEmpty())
            return "KH001";

        int maxId = 0;
        for (KhachHang kh : ds) {
            String ma = kh.getMaKH();
            if (ma != null && ma.startsWith("KH")) {
                try {
                    int num = Integer.parseInt(ma.substring(2).trim());
                    if (num > maxId)
                        maxId = num;
                } catch (Exception e) {
                }
            }
        }
        return String.format("KH%03d", maxId + 1);
    }

    public boolean capNhatTienDaMua(String maKH, double tienThem) {
        return khachHangDAO.capNhatTienDaMua(maKH, tienThem);
    }

    public List<KhachHang> layDanhSachKhachHang() {
        return khachHangDAO.layDanhSachKhachHang();
    }

    public KhachHang timKhachHangTheoMa(String maKH) {
        if (maKH == null || maKH.trim().isEmpty()) {
            return null;
        }
        return khachHangDAO.layKhachHangTheoMa(maKH);
    }

    public boolean themKhachHang(KhachHang kh) throws Exception {
        if (kh.getTenKH() == null || kh.getTenKH().trim().isEmpty()) {
            throw new Exception("Tên khách hàng không được để trống!");
        }
        if (kh.getSdt() == null || kh.getSdt().trim().isEmpty()) {
            throw new Exception("Số điện thoại không được để trống!");
        }
        if (!kh.getSdt().matches("\\d{10,11}")) {
            throw new Exception("Số điện thoại phải có từ 10-11 chữ số!");
        }

        if (kh.getTenDaMua() < 0)
            kh.setTenDaMua(0);
        if (kh.getMaHang() == null || kh.getMaHang().trim().isEmpty()) {
            kh.setMaHang("HTV01");
        }

        boolean ok = khachHangDAO.themKhachHang(kh);
        if (!ok) {
            throw new Exception("Lỗi: Không thể lưu vào cơ sở dữ liệu!");
        }

        return true;
    }

    public String capNhatKhachHang(KhachHang kh) {
        if (kh == null || kh.getMaKH() == null || kh.getMaKH().trim().isEmpty()) {
            return "Không tìm thấy mã khách hàng";
        }
        if (kh.getTenKH() == null || kh.getTenKH().trim().isEmpty()
                || kh.getSdt() == null || kh.getSdt().trim().isEmpty()) {
            return "Vui lòng nhập đầy đủ tên và số điện thoại";
        }
        boolean ok = khachHangDAO.capNhatKhachHang(kh);
        if (!ok) {
            return "Lỗi cập nhật khách hàng";
        }
        return null;
    }

    public boolean xoaKhachHang(String maKH) {
        if (maKH == null || maKH.trim().isEmpty()) {
            return false;
        }
        return khachHangDAO.xoaKhachHang(maKH);
    }

    public boolean xuatExcel(String filePath) {
        ArrayList<KhachHang> list = layDanhSachKhachHang();
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Khách Hàng");
            String[] columns = { "Mã Khách Hàng", "Tên Khách Hàng", "Giới Tính", "Số Điện Thoại", "Tổng Chỉ Tiêu", "Hạng Thành Viên" };

            Row headerRow = sheet.createRow(0);
            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowNum = 1;
            for (KhachHang kh : list) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(kh.getMaKH());
                row.createCell(1).setCellValue(kh.getTenKH());
                row.createCell(2).setCellValue(kh.getGioiTinh());
                row.createCell(3).setCellValue(kh.getSdt());
                row.createCell(4).setCellValue(kh.getTenDaMua());
                row.createCell(5).setCellValue(kh.getMaHang());
            }

            for (int i = 0; i < columns.length; i++)
                sheet.autoSizeColumn(i);

            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean nhapExcel(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
                Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            ArrayList<KhachHang> dsMoi = new ArrayList<>();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null)
                    continue;

                KhachHang kh = new KhachHang();
                // Bỏ qua mã (cột 0), gán null để DAO tự tạo mã
                kh.setMaKH(null);
                kh
                kh.setTenHang(getCellValueAsString(row.getCell(1)));
                kh.setPhanTramGiam(
                        row.getCell(2) != null ? (int) row.getCell(2).getNumericCellValue() : 0);
                kh.setDieuKien(row.getCell(3) != null ? row.getCell(3).getNumericCellValue() : 0);

                dsMoi.add(kh);
            }

            for (KhachHang kh : dsMoi) {
                this.themKhachHang(kh);
            }
            canUpdate = true;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
