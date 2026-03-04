package util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import dto.SanPham;
import dto.TaiKhoan;

public class XuLyExcel {
    
    public static boolean xuatFile(ArrayList<SanPham> list) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file");
        fileChooser.setSelectedFile(new File("DanhSachSanPham.xlsx"));

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Sản Phẩm");

                // 1. Tạo Header (Đã bỏ "Nhà Cung Cấp")
                String[] headers = { "Mã SP", "Tên Sản Phẩm", "Danh Mục", "Giá Bán", "Loại Nước",
                        "Thể Tích", "Trạng Thái Xử lí", "Số size", "Đường dẫn ảnh", "Trạng thái" };
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

                // 2. Đổ dữ liệu từ danh sách vào các dòng (Đã sắp xếp lại chỉ số thứ tự cột liên tục từ 0 -> 9)
                int rowNum = 1;
                for (SanPham sp : list) {
                    Row row = sheet.createRow(rowNum++);

                    row.createCell(0).setCellValue(sp.getMaSP());
                    row.createCell(1).setCellValue(sp.getTenSP());
                    
                    // Thêm kiểm tra null để tránh lỗi NullPointerException khi xuất file
                    String tenDanhMuc = (sp.getDanhMuc() != null) ? sp.getDanhMuc().getTenDM() : "Chưa có";
                    row.createCell(2).setCellValue(tenDanhMuc); 
                    
                    row.createCell(3).setCellValue(sp.getGiaBan());
                    row.createCell(4).setCellValue(sp.getLoaiNuoc());
                    row.createCell(5).setCellValue(sp.getTheTich() + " ml");
                    row.createCell(6).setCellValue(sp.getTrangThaiXuLy());
                    row.createCell(7).setCellValue(sp.getListSize() == null ? 0 : sp.getListSize().size());
                    row.createCell(8).setCellValue(sp.getAnh());
                    
                    // Mẹo nhỏ: Chuyển boolean thành text để file Excel nhìn thân thiện và chuyên nghiệp hơn
                    String trangThaiStr = sp.getTrangThai() ? "Đang hoạt động" : "Đã xóa";
                    row.createCell(9).setCellValue(trangThaiStr);
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
    //xuất tài khoản 
    public static boolean xuatFileTaiKhoan(ArrayList<TaiKhoan> list) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file");
        fileChooser.setSelectedFile(new File("DanhSachTaiKhoan.xlsx"));

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

            File fileToSave = fileChooser.getSelectedFile();

            try (Workbook workbook = new XSSFWorkbook()) {

                Sheet sheet = workbook.createSheet("Tài Khoản");

                // ===== 1. Tạo Header =====
                String[] headers = {
                        "Mã TK",
                        "Mã Nhân Viên",
                        "Tên Đăng Nhập",
                        "Mật Khẩu",
                        "Nhóm Quyền",
                        "Trạng Thái Xử Lý"
                };

                Row headerRow = sheet.createRow(0);

                // Style in đậm
                CellStyle headerStyle = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                headerStyle.setFont(font);

                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(headerStyle);
                }

                // ===== 2. Đổ dữ liệu =====
                int rowNum = 1;

                for (TaiKhoan tk : list) {

                    Row row = sheet.createRow(rowNum++);

                    row.createCell(0).setCellValue(tk.getMaTK());
                    row.createCell(1).setCellValue(tk.getMaNV());
                    row.createCell(2).setCellValue(tk.getTenDangNhap());
                    row.createCell(3).setCellValue(tk.getMatKhau());

                    // Kiểm tra null để tránh lỗi
                    String tenNhomQuyen = (tk.getNhomQuyen() != null)
                            ? tk.getNhomQuyen().getTenNhomQuyen()
                            : "Chưa có";

                    row.createCell(4).setCellValue(tenNhomQuyen);

                    row.createCell(5).setCellValue(tk.getTrangThaiXuLy());
                }

                // ===== 3. Auto size cột =====
                for (int i = 0; i < headers.length; i++) {
                    sheet.autoSizeColumn(i);
                }

                // ===== 4. Ghi file =====
                try (FileOutputStream out = new FileOutputStream(fileToSave)) {
                    workbook.write(out);
                    JOptionPane.showMessageDialog(null, "Xuất tài khoản thành công!");
                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất file: " + e.getMessage());
            }
        }

        return false;
    }
}