package util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import dto.ChiTietHoaDon;
import dto.HoaDon;
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

    public static boolean xuatFileHoaDon(ArrayList<HoaDon> list) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setSelectedFile(new File("DanhSachHoaDon.xlsx"));

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            if (!fileToSave.getName().toLowerCase().endsWith(".xlsx")) {
                fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + ".xlsx");
            }

            try (org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {

                org.apache.poi.ss.usermodel.Sheet sheet1 = workbook.createSheet("Danh Sách Hóa Đơn");
                String[] headers1 = { "Mã Hóa Đơn", "Nhân viên", "Khách Hàng", "Khuyến mãi", "Ngày bán", "Tổng tiền" };

                org.apache.poi.ss.usermodel.Row headerRow = sheet1.createRow(0);
                org.apache.poi.ss.usermodel.CellStyle headerStyle = workbook.createCellStyle();
                org.apache.poi.ss.usermodel.Font font = workbook.createFont();
                font.setBold(true);
                headerStyle.setFont(font);

                for (int i = 0; i < headers1.length; i++) {
                    org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers1[i]);
                    cell.setCellStyle(headerStyle);
                }

                int rowNum1 = 1;
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                for (HoaDon hd : list) {
                    org.apache.poi.ss.usermodel.Row row = sheet1.createRow(rowNum1++);
                    row.createCell(0).setCellValue(hd.getMaHD() != null ? hd.getMaHD() : "");
                    row.createCell(1).setCellValue((hd.getNhanVien() != null && hd.getNhanVien().getMaNV() != null) ? hd.getNhanVien().getMaNV() : "N/A");
                    row.createCell(2).setCellValue(hd.getMaKH() != null ? hd.getMaKH() : "Khách vãng lai");
                    row.createCell(3).setCellValue(hd.getTienKhuyenMai());
                    row.createCell(4).setCellValue(hd.getNgayBan() != null ? sdf.format(hd.getNgayBan()) : "");
                    row.createCell(5).setCellValue(hd.getTongTien());
                }
                for (int i = 0; i < headers1.length; i++) sheet1.autoSizeColumn(i);

                org.apache.poi.ss.usermodel.Sheet sheet2 = workbook.createSheet("Chi Tiết Hóa Đơn");
                String[] headers2 = { "Mã CTHD", "Mã Hóa Đơn", "Mã Sản Phẩm", "Mã Size", "Đơn Giá", "Số lượng", "Thành Tiền" };

                org.apache.poi.ss.usermodel.Row header2Row = sheet2.createRow(0);
                for (int i = 0; i < headers2.length; i++) {
                    org.apache.poi.ss.usermodel.Cell cell = header2Row.createCell(i);
                    cell.setCellValue(headers2[i]);
                    cell.setCellStyle(headerStyle);
                }

                int rowNum2 = 1;
                bus.ChiTietHoaDonBUS ctBus = new bus.ChiTietHoaDonBUS();

                for (HoaDon hd : list) {
                    ArrayList<dto.ChiTietHoaDon> listChiTiet = ctBus.layChiTietTheoMaHD(hd.getMaHD());
                    for (dto.ChiTietHoaDon cthd : listChiTiet) {
                        org.apache.poi.ss.usermodel.Row row = sheet2.createRow(rowNum2++);
                        row.createCell(0).setCellValue(cthd.getMaCTHD());
                        row.createCell(1).setCellValue(cthd.getMaHD());
                        row.createCell(2).setCellValue((cthd.getSanPham() != null) ? cthd.getSanPham().getMaSP() : "");
                        row.createCell(3).setCellValue((cthd.getSize() != null) ? cthd.getSize().getMaSize() : "");
                        row.createCell(4).setCellValue(cthd.getGia());
                        row.createCell(5).setCellValue(cthd.getSoLuong());
                        row.createCell(6).setCellValue(cthd.getGia() * cthd.getSoLuong());
                    }
                }
                for (int i = 0; i < headers2.length; i++) sheet2.autoSizeColumn(i);

                try (java.io.FileOutputStream out = new java.io.FileOutputStream(fileToSave)) {
                    workbook.write(out);
                    javax.swing.JOptionPane.showMessageDialog(null, "Xuất danh sách Hóa Đơn thành công!\nĐã lưu tại: " + fileToSave.getAbsolutePath());
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Excel: " + e.getMessage(), "Lỗi", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
        return false;
    }
}