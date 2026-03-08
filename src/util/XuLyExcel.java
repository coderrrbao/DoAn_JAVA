package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import dto.HoaDon;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import bus.NhomQuyenBUS;
import dto.NhomQuyen;
import dto.PhanQuyen;
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
                String[] headers = {
                        "Mã TK",
                        "Mã Nhân Viên",
                        "Tên Đăng Nhập",
                        "Mật Khẩu",
                        "Nhóm Quyền",
                        "Trạng Thái Xử Lý"
                };
                Row headerRow = sheet.createRow(0);

                CellStyle headerStyle = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                headerStyle.setFont(font);

                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(headerStyle);
                }
                int rowNum = 1;
                for (TaiKhoan tk : list) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(tk.getMaTK());
                    row.createCell(1).setCellValue(tk.getMaNV());
                    row.createCell(2).setCellValue(tk.getTenDangNhap());
                    row.createCell(3).setCellValue(tk.getMatKhau());

                    String tenNhomQuyen = (tk.getNhomQuyen() != null)
                            ? tk.getNhomQuyen().getTenNhomQuyen()
                            : "Chưa có";

                    row.createCell(4).setCellValue(tenNhomQuyen);
                    row.createCell(5).setCellValue(tk.getTrangThaiXuLy());
                }
                for (int i = 0; i < headers.length; i++) {
                    sheet.autoSizeColumn(i);
                }
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
    
    public static boolean xuatFileNhomQuyen(ArrayList<NhomQuyen> list) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file");
        fileChooser.setSelectedFile(new File("DanhSachNhomQuyen.xlsx"));
        
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getName().toLowerCase().endsWith(".xlsx")) {
                fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + ".xlsx");
            }

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Nhóm Quyền");
                String[] headers = {
                        "Mã nhóm quyền",
                        "Tên nhóm quyền",
                        "Tổng số quyền"
                };
                Row headerRow = sheet.createRow(0);
                CellStyle headerStyle = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                headerStyle.setFont(font);
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(headerStyle);
                }
                int rowNum = 1;

                for (NhomQuyen nq : list) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(nq.getMaNQ());
                    row.createCell(1).setCellValue(nq.getTenNhomQuyen());
                    row.createCell(2).setCellValue(nq.getListQuyen().size());
                }

                for (int i = 0; i < headers.length; i++) {
                    sheet.autoSizeColumn(i);
                }
                try (FileOutputStream out = new FileOutputStream(fileToSave)) {
                    workbook.write(out);
                    JOptionPane.showMessageDialog(null,
                            "Xuất danh sách nhóm quyền thành công!");
                    return true;
                }

            } catch (Exception e) {

                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Lỗi khi xuất file: " + e.getMessage());
            }
        }

        return false;
    }
    
    //nhap exc phan quyen nhom quyen
    public static Object[] nhapFilePhanQuyen(File file) {
        ArrayList<NhomQuyen> listNhomQuyen = new ArrayList<>();
        ArrayList<PhanQuyen> listPhanQuyen = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet1 = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet1.getLastRowNum(); i++) {
                Row row = sheet1.getRow(i);
                if (row == null) continue;

                NhomQuyen nq = new NhomQuyen();

                nq.setMaNQ(getStringCell(row.getCell(0)));
                nq.setTenNhomQuyen(getStringCell(row.getCell(1)));

                listNhomQuyen.add(nq);
            }

            Sheet sheet2 = workbook.getSheetAt(1);
            for (int i = 1; i <= sheet2.getLastRowNum(); i++) {
                Row row = sheet2.getRow(i);
                if (row == null) continue;
                PhanQuyen pq = new PhanQuyen();
                pq.setMaNQ(getStringCell(row.getCell(0)));
                pq.setMaQuyen(getStringCell(row.getCell(1)));
                listPhanQuyen.add(pq);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Object[]{listNhomQuyen, listPhanQuyen};
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
    //nhap excel tk
    public static ArrayList<TaiKhoan> nhapFileTaiKhoan(File file) {
        ArrayList<TaiKhoan> danhSach = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                if (row == null) continue;

                TaiKhoan tk = new TaiKhoan();
                tk.setMaNV(getStringCell(row.getCell(1)));
                tk.setTenDangNhap(getStringCell(row.getCell(2)));
                tk.setMatKhau(getStringCell(row.getCell(3)));

                NhomQuyen nq = NhomQuyenBUS.getNhomQuyenBUS().timNhomQuyenTheoTen(getStringCell(row.getCell(4)));
                tk.setNhomQuyen(nq);

                danhSach.add(tk);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return danhSach;
    }
    private static String getStringCell(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((long) cell.getNumericCellValue());
            default:
                return "";
        }
    }

    public static boolean xuatFileKhuyenMai(java.util.ArrayList<dto.KhuyenMai> list) {
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel Khuyến Mãi");
        fileChooser.setSelectedFile(new java.io.File("DanhSachKhuyenMai.xlsx"));

        if (fileChooser.showSaveDialog(null) == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getName().toLowerCase().endsWith(".xlsx")) {
                fileToSave = new java.io.File(fileToSave.getParentFile(), fileToSave.getName() + ".xlsx");
            }

            try (org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
                org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Khuyến Mãi");

                String[] headers = { "Mã KM", "Phần Trăm Giảm", "Ngày Bắt Đầu", "Ngày Kết Thúc" };

                org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(0);
                org.apache.poi.ss.usermodel.CellStyle headerStyle = workbook.createCellStyle();
                org.apache.poi.ss.usermodel.Font font = workbook.createFont();
                font.setBold(true);
                headerStyle.setFont(font);

                for (int i = 0; i < headers.length; i++) {
                    org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(headerStyle);
                }

                int rowNum = 1;
                for (dto.KhuyenMai km : list) {
                    org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(km.getMaKM());
                    row.createCell(1).setCellValue(km.getPhanTramGiam());
                    row.createCell(2).setCellValue(km.getTuNgay() != null ? km.getTuNgay() : "");
                    row.createCell(3).setCellValue(km.getDenNgay() != null ? km.getDenNgay() : "");
                }

                for (int i = 0; i < headers.length; i++) sheet.autoSizeColumn(i);

                try (java.io.FileOutputStream out = new java.io.FileOutputStream(fileToSave)) {
                    workbook.write(out);
                    javax.swing.JOptionPane.showMessageDialog(null, "Xuất danh sách Khuyến Mãi thành công!");
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(null, "Lỗi xuất file: " + e.getMessage());
            }
        }
        return false;
    }

    public static java.util.ArrayList<dto.KhuyenMai> nhapFileKhuyenMai() {
        java.util.ArrayList<dto.KhuyenMai> ds = new java.util.ArrayList<>();
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel Khuyến Mãi để nhập");

        if (fileChooser.showOpenDialog(null) == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            try (java.io.FileInputStream fis = new java.io.FileInputStream(file);
                 org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook(fis)) {

                org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);
                java.util.Iterator<org.apache.poi.ss.usermodel.Row> rowIterator = sheet.iterator();
                org.apache.poi.ss.usermodel.DataFormatter formatter = new org.apache.poi.ss.usermodel.DataFormatter();
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");

                if (rowIterator.hasNext()) rowIterator.next();

                while (rowIterator.hasNext()) {
                    org.apache.poi.ss.usermodel.Row row = rowIterator.next();
                    if (row.getCell(0) == null || formatter.formatCellValue(row.getCell(0)).trim().isEmpty()) {
                        continue;
                    }

                    dto.KhuyenMai km = new dto.KhuyenMai();
                    km.setMaKM(formatter.formatCellValue(row.getCell(0)));
                    try { km.setPhanTramGiam(Integer.parseInt(formatter.formatCellValue(row.getCell(1)))); } catch (Exception e) { km.setPhanTramGiam(0); }

                    String start = formatter.formatCellValue(row.getCell(2));
                    if (!start.isEmpty()) {
                        km.setTuNgay(start);

                    }

                    String end = formatter.formatCellValue(row.getCell(3));
                    if (!end.isEmpty()) {
                        km.setDenNgay(end);
                    }

                    ds.add(km);
                }
            } catch (Exception e) {
                e.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(null, "Lỗi đọc file Excel: File không đúng định dạng!");
            }
        }
        return ds;
    }



}