package util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
// Giả sử model của bạn nằm trong package model
// import model.SanPham; 

import dto.SanPham;

public class XuLyExcel {
    // Truyền List<SanPham> vào làm tham số
    public static boolean xuatFile(ArrayList<SanPham> list) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file");
        fileChooser.setSelectedFile(new File("DanhSachSanPham.xlsx"));

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Sản Phẩm");

                // 1. Tạo Header
                String[] headers = {"Mã SP", "Tên Sản Phẩm", "Danh Mục", "Nhà Cung Cấp", "Giá Nhập", "Giá Bán", "Số Lượng Tồn", "Loại Nước", "Thể Tích", "Trạng Thái"};
                Row headerRow = sheet.createRow(0);
                
                // Định dạng Header (In đậm)
                CellStyle headerStyle = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                headerStyle.setFont(font);

                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(headerStyle);
                }

                // 2. Đổ dữ liệu từ danh sách vào các dòng
                int rowNum = 1;
                for (SanPham sp : list) {
                    Row row = sheet.createRow(rowNum++);
                    
                    row.createCell(0).setCellValue(sp.getMaSP());
                    row.createCell(1).setCellValue(sp.getTenSP());
                    row.createCell(2).setCellValue(sp.getDanhMuc().getTenDM()); // Giả sử DanhMuc có hàm getTenDM
                    row.createCell(3).setCellValue(sp.getNhaCungCap().getTenNCC()); // Giả sử NCC có hàm getTenNCC
                    row.createCell(4).setCellValue(sp.getGiaNhap());
                    row.createCell(5).setCellValue(sp.getGiaBan());
                    row.createCell(6).setCellValue(sp.getSoLuongTon());
                    row.createCell(7).setCellValue(sp.getLoaiNuoc());
                    row.createCell(8).setCellValue(sp.getTheTich() + " ml");
                    row.createCell(9).setCellValue(sp.getTrangThai()? "Tồn tại" : "Đã xóa");
                }

                // Tự động căn chỉnh độ rộng cột
                for (int i = 0; i < headers.length; i++) {
                    sheet.autoSizeColumn(i);
                }

                // 3. Ghi file
                try (FileOutputStream out = new FileOutputStream(fileToSave)) {
                    workbook.write(out);
                    JOptionPane.showMessageDialog(null, "Xuất dữ liệu thành công!");
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi ghi file: " + e.getMessage());
            }
        }
        return false;
    }
}