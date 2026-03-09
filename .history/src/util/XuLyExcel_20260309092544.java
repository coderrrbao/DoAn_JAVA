package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import bus.NhomQuyenBUS;
import bus.ChiTietHoaDonBUS;
import dto.ChiTietCongThuc;
import dto.ChiTietHoaDon;
import dto.ChiTietNhaCungCap;
import dto.CongThuc;
import dto.DanhMuc;
import dto.HoaDon;
import dto.KhachHang;
import dto.KhuyenMai;
import dto.LoNguyenLieu;
import dto.LoSanPham;
import dto.NguyenLieu;
import dto.NhaCungCap;
import dto.NhanVien;
import dto.NhomQuyen;
import dto.PhanQuyen;
import dto.PhieuNhapNguyenLieu;
import dto.PhieuNhapSanPham;
import dto.SanPham;
import dto.Size;
import dto.TaiKhoan;
import ui.login.PhienDangNhap;

public class XuLyExcel {

    // xuất tài khoản
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

    public static boolean xuatFileNhomQuyen(ArrayList<NhomQuyen> listNhomQuyen, ArrayList<PhanQuyen> listPhanQuyen) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file");
        fileChooser.setSelectedFile(new File("DanhSachPhanQuyen.xlsx"));

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getName().toLowerCase().endsWith(".xlsx")) {
                fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + ".xlsx");
            }

            try (Workbook workbook = new XSSFWorkbook()) {
                // --- Cấu hình Style cho Header ---
                CellStyle headerStyle = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                headerStyle.setFont(font);

                // ================= SHEET 1: NHÓM QUYỀN =================
                Sheet sheet1 = workbook.createSheet("Nhóm Quyền");
                String[] headers1 = { "Mã nhóm quyền", "Tên nhóm quyền", "Tổng số quyền" };

                Row headerRow1 = sheet1.createRow(0);
                for (int i = 0; i < headers1.length; i++) {
                    Cell cell = headerRow1.createCell(i);
                    cell.setCellValue(headers1[i]);
                    cell.setCellStyle(headerStyle);
                }

                int rowNum1 = 1;
                for (NhomQuyen nq : listNhomQuyen) {
                    Row row = sheet1.createRow(rowNum1++);
                    row.createCell(0).setCellValue(nq.getMaNQ());
                    row.createCell(1).setCellValue(nq.getTenNhomQuyen());
                    // Kiểm tra null để tránh lỗi nếu listQuyen chưa được khởi tạo
                    int size = (nq.getListQuyen() != null) ? nq.getListQuyen().size() : 0;
                    row.createCell(2).setCellValue(size);
                }
                for (int i = 0; i < headers1.length; i++)
                    sheet1.autoSizeColumn(i);

                // ================= SHEET 2: CHI TIẾT PHÂN QUYỀN =================
                Sheet sheet2 = workbook.createSheet("Chi Tiết Phân Quyền");
                String[] headers2 = { "Mã nhóm quyền", "Mã quyền" };

                Row headerRow2 = sheet2.createRow(0);
                for (int i = 0; i < headers2.length; i++) {
                    Cell cell = headerRow2.createCell(i);
                    cell.setCellValue(headers2[i]);
                    cell.setCellStyle(headerStyle);
                }

                int rowNum2 = 1;
                for (PhanQuyen pq : listPhanQuyen) {
                    Row row = sheet2.createRow(rowNum2++);
                    row.createCell(0).setCellValue(pq.getMaNQ());
                    row.createCell(1).setCellValue(pq.getMaQuyen());
                }
                for (int i = 0; i < headers2.length; i++)
                    sheet2.autoSizeColumn(i);

                // --- Ghi file ---
                try (FileOutputStream out = new FileOutputStream(fileToSave)) {
                    workbook.write(out);
                    JOptionPane.showMessageDialog(null, "Xuất file Excel 2 sheet thành công!");
                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất file: " + e.getMessage());
            }
        }
        return false;
    }

    // nhap exc phan quyen nhom quyen
    public static Object[] nhapFilePhanQuyen(File file) {
        ArrayList<NhomQuyen> listNhomQuyen = new ArrayList<>();
        ArrayList<PhanQuyen> listPhanQuyen = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file);
                Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet1 = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet1.getLastRowNum(); i++) {
                Row row = sheet1.getRow(i);
                if (row == null)
                    continue;

                NhomQuyen nq = new NhomQuyen();

                nq.setMaNQ(getStringCell(row.getCell(0)));
                nq.setTenNhomQuyen(getStringCell(row.getCell(1)));

                listNhomQuyen.add(nq);
            }

            Sheet sheet2 = workbook.getSheetAt(1);
            for (int i = 1; i <= sheet2.getLastRowNum(); i++) {
                Row row = sheet2.getRow(i);
                if (row == null)
                    continue;
                PhanQuyen pq = new PhanQuyen();
                pq.setMaNQ(getStringCell(row.getCell(0)));
                pq.setMaQuyen(getStringCell(row.getCell(1)));
                listPhanQuyen.add(pq);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Object[] { listNhomQuyen, listPhanQuyen };
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

            try (Workbook workbook = new XSSFWorkbook()) {

                Sheet sheet1 = workbook.createSheet("Danh Sách Hóa Đơn");
                String[] headers1 = { "Mã Hóa Đơn", "Nhân viên", "Khách Hàng", "Khuyến mãi", "Ngày bán", "Tổng tiền" };

                Row headerRow = sheet1.createRow(0);
                CellStyle headerStyle = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                headerStyle.setFont(font);

                for (int i = 0; i < headers1.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers1[i]);
                    cell.setCellStyle(headerStyle);
                }

                int rowNum1 = 1;
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                for (HoaDon hd : list) {
                    Row row = sheet1.createRow(rowNum1++);
                    row.createCell(0).setCellValue(hd.getMaHD() != null ? hd.getMaHD() : "");
                    row.createCell(1)
                            .setCellValue((hd.getNhanVien() != null && hd.getNhanVien().getMaNV() != null)
                                    ? hd.getNhanVien().getMaNV()
                                    : "N/A");
                    row.createCell(2).setCellValue(hd.getMaKH() != null ? hd.getMaKH() : "Khách vãng lai");
                    row.createCell(3).setCellValue(hd.getTienKhuyenMai());
                    row.createCell(4).setCellValue(hd.getNgayBan() != null ? sdf.format(hd.getNgayBan()) : "");
                    row.createCell(5).setCellValue(hd.getTongTien());
                }
                for (int i = 0; i < headers1.length; i++)
                    sheet1.autoSizeColumn(i);

                Sheet sheet2 = workbook.createSheet("Chi Tiết Hóa Đơn");
                String[] headers2 = { "Mã CTHD", "Mã Hóa Đơn", "Mã Sản Phẩm", "Mã Size", "Đơn Giá", "Số lượng",
                        "Thành Tiền" };

                Row header2Row = sheet2.createRow(0);
                for (int i = 0; i < headers2.length; i++) {
                    Cell cell = header2Row.createCell(i);
                    cell.setCellValue(headers2[i]);
                    cell.setCellStyle(headerStyle);
                }

                int rowNum2 = 1;
                ChiTietHoaDonBUS ctBus = new ChiTietHoaDonBUS();

                for (HoaDon hd : list) {
                    ArrayList<ChiTietHoaDon> listChiTiet = ctBus.layChiTietTheoMaHD(hd.getMaHD());
                    for (ChiTietHoaDon cthd : listChiTiet) {
                        Row row = sheet2.createRow(rowNum2++);
                        row.createCell(0).setCellValue(cthd.getMaCTHD());
                        row.createCell(1).setCellValue(cthd.getMaHD());
                        row.createCell(2).setCellValue((cthd.getSanPham() != null) ? cthd.getSanPham().getMaSP() : "");
                        row.createCell(3).setCellValue((cthd.getSize() != null) ? cthd.getSize().getMaSize() : "");
                        row.createCell(4).setCellValue(cthd.getGia());
                        row.createCell(5).setCellValue(cthd.getSoLuong());
                        row.createCell(6).setCellValue(cthd.getGia() * cthd.getSoLuong());
                    }
                }
                for (int i = 0; i < headers2.length; i++)
                    sheet2.autoSizeColumn(i);

                try (FileOutputStream out = new FileOutputStream(fileToSave)) {
                    workbook.write(out);
                    JOptionPane.showMessageDialog(null,
                            "Xuất danh sách Hóa Đơn thành công!\nĐã lưu tại: " + fileToSave.getAbsolutePath());
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Excel: " + e.getMessage(), "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        return false;
    }

    // nhap excel tk
    public static ArrayList<TaiKhoan> nhapFileTaiKhoan(File file) {
        ArrayList<TaiKhoan> danhSach = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(file);
                Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                if (row == null)
                    continue;

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
        if (cell == null)
            return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((long) cell.getNumericCellValue());
            default:
                return "";
        }
    }

    public static boolean xuatFileKhuyenMai(ArrayList<KhuyenMai> list) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel Khuyến Mãi");
        fileChooser.setSelectedFile(new File("DanhSachKhuyenMai.xlsx"));

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getName().toLowerCase().endsWith(".xlsx")) {
                fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + ".xlsx");
            }

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Khuyến Mãi");

                String[] headers = { "Mã KM", "Phần Trăm Giảm", "Ngày Bắt Đầu", "Ngày Kết Thúc" };

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
                for (KhuyenMai km : list) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(km.getMaKM());
                    row.createCell(1).setCellValue(km.getPhanTramGiam());
                    row.createCell(2).setCellValue(km.getTuNgay() != null ? km.getTuNgay() : "");
                    row.createCell(3).setCellValue(km.getDenNgay() != null ? km.getDenNgay() : "");
                }

                for (int i = 0; i < headers.length; i++)
                    sheet.autoSizeColumn(i);

                try (FileOutputStream out = new FileOutputStream(fileToSave)) {
                    workbook.write(out);
                    JOptionPane.showMessageDialog(null, "Xuất danh sách Khuyến Mãi thành công!");
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi xuất file: " + e.getMessage());
            }
        }
        return false;
    }

    public static ArrayList<KhuyenMai> nhapFileKhuyenMai() {
        ArrayList<KhuyenMai> ds = new ArrayList<>();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel Khuyến Mãi để nhập");

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileInputStream fis = new FileInputStream(file);
                    Workbook workbook = new XSSFWorkbook(fis)) {

                Sheet sheet = workbook.getSheetAt(0);
                Iterator<Row> rowIterator = sheet.iterator();
                DataFormatter formatter = new DataFormatter();

                if (rowIterator.hasNext())
                    rowIterator.next();

                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    if (row.getCell(0) == null || formatter.formatCellValue(row.getCell(0)).trim().isEmpty()) {
                        continue;
                    }

                    KhuyenMai km = new KhuyenMai();
                    km.setMaKM(formatter.formatCellValue(row.getCell(0)));
                    try {
                        km.setPhanTramGiam(Integer.parseInt(formatter.formatCellValue(row.getCell(1))));
                    } catch (Exception e) {
                        km.setPhanTramGiam(0);
                    }

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
                JOptionPane.showMessageDialog(null, "Lỗi đọc file Excel: File không đúng định dạng!");
            }
        }
        return ds;
    }

    public static boolean xuatFileNhaCungCap(File file, ArrayList<NhaCungCap> list) {
        try (Workbook workbook = new XSSFWorkbook()) {
            // --- SHEET 1: NHÀ CUNG CẤP ---
            Sheet sheet1 = workbook.createSheet("NhaCungCap");
            // Header không có "Loại Cung Cấp"
            String[] headers1 = { "Mã NCC", "Tên Nhà Cung Cấp", "", "Số Điện Thoại", "Địa Chỉ" };
            Row headerRow1 = sheet1.createRow(0);
            for (int i = 0; i < headers1.length; i++) {
                headerRow1.createCell(i).setCellValue(headers1[i]);
            }

            // --- SHEET 2: CHI TIẾT ---
            Sheet sheet2 = workbook.createSheet("ChiTietNhaCungCap");
            String[] headers2 = { "STT", "Mã NCC", "Loại Đối Tượng", "Mã Đối Tượng", "Giá Nhập" };
            Row headerRow2 = sheet2.createRow(0);
            for (int i = 0; i < headers2.length; i++) {
                headerRow2.createCell(i).setCellValue(headers2[i]);
            }

            int rowNum1 = 1;
            int rowNum2 = 1;
            int stt = 1;

            for (NhaCungCap ncc : list) {
                // Ghi Sheet 1 (Bỏ cột index 2)
                Row row1 = sheet1.createRow(rowNum1++);
                row1.createCell(0).setCellValue(ncc.getMaNCC());
                row1.createCell(1).setCellValue(ncc.getTenNCC());
                row1.createCell(2).setCellValue(""); // Để trống cột Loại cung cấp
                row1.createCell(3).setCellValue(ncc.getSoDienThoai());
                row1.createCell(4).setCellValue(ncc.getDiaChi());

                // Ghi Sheet 2
                for (ChiTietNhaCungCap ct : ncc.getListChiTietNhaCungCap()) {
                    Row row2 = sheet2.createRow(rowNum2++);
                    row2.createCell(0).setCellValue(stt++);
                    row2.createCell(1).setCellValue(ncc.getMaNCC());
                    row2.createCell(2).setCellValue(ct.getLoaiDoiTuong());
                    row2.createCell(3).setCellValue(ct.getMaDoiTuong());
                    row2.createCell(4).setCellValue(ct.getGiaNhap());
                }
            }

            // Tự động giãn cột
            for (int i = 0; i < 5; i++) {
                sheet1.autoSizeColumn(i);
                sheet2.autoSizeColumn(i);
            }

            try (FileOutputStream out = new FileOutputStream(file)) {
                workbook.write(out);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<NhaCungCap> nhapFileNhaCungCap(File file) {
        ArrayList<NhaCungCap> danhSachNcc = new ArrayList<>();
        HashMap<String, NhaCungCap> mapNcc = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(file);
                Workbook workbook = new XSSFWorkbook(fis)) {

            DataFormatter formatter = new DataFormatter();

            // --- SHEET 1: NHÀ CUNG CẤP ---
            Sheet sheet1 = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet1.getLastRowNum(); i++) {
                Row row = sheet1.getRow(i);
                if (row == null)
                    continue;

                String maNcc = formatter.formatCellValue(row.getCell(0)).trim();
                if (maNcc.isEmpty())
                    continue;

                NhaCungCap ncc = new NhaCungCap();
                ncc.setMaNCC(maNcc);
                ncc.setTenNCC(formatter.formatCellValue(row.getCell(1)).trim());

                // Bỏ qua row.getCell(2) - Cột loại cung cấp

                ncc.setSoDienThoai(formatter.formatCellValue(row.getCell(3)).trim());
                ncc.setDiaChi(formatter.formatCellValue(row.getCell(4)).trim());

                mapNcc.put(maNcc, ncc);
                danhSachNcc.add(ncc);
            }

            // --- SHEET 2: CHI TIẾT NHÀ CUNG CẤP ---
            if (workbook.getNumberOfSheets() > 1) {
                Sheet sheet2 = workbook.getSheetAt(1);
                for (int i = 1; i <= sheet2.getLastRowNum(); i++) {
                    Row row = sheet2.getRow(i);
                    if (row == null)
                        continue;

                    String maNccLienKet = formatter.formatCellValue(row.getCell(1)).trim();
                    NhaCungCap ncc = mapNcc.get(maNccLienKet);

                    if (ncc != null) {
                        ChiTietNhaCungCap ct = new ChiTietNhaCungCap();
                        ct.setLoaiDoiTuong(formatter.formatCellValue(row.getCell(2)).trim());
                        ct.setMaDoiTuong(formatter.formatCellValue(row.getCell(3)).trim());

                        String giaStr = formatter.formatCellValue(row.getCell(4)).replaceAll("[^0-9.]", "");
                        ct.setGiaNhap(giaStr.isEmpty() ? 0 : Double.parseDouble(giaStr));

                        // TỰ ĐỘNG CẬP NHẬT FLAG cungCapSP/cungCapNL QUA HÀM NÀY
                        ncc.themChiTietNhaCungCap(ct);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return danhSachNcc;
    }

    // ================= XUẤT HẠNG THÀNH VIÊN =================
    public static boolean xuatFileHangThanhVien(java.util.ArrayList<dto.HangThanhVien> list) {
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel Hạng Thành Viên");
        fileChooser.setSelectedFile(new java.io.File("DanhSachHangThanhVien.xlsx"));

        if (fileChooser.showSaveDialog(null) == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getName().toLowerCase().endsWith(".xlsx")) {
                fileToSave = new java.io.File(fileToSave.getParentFile(), fileToSave.getName() + ".xlsx");
            }

            try (org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
                org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Hạng Thành Viên");

                // Style cho Header
                org.apache.poi.ss.usermodel.CellStyle headerStyle = workbook.createCellStyle();
                org.apache.poi.ss.usermodel.Font font = workbook.createFont();
                font.setBold(true);
                headerStyle.setFont(font);

                // Dùng dummy object để lấy Header từ interface
                dto.HangThanhVien dummy = new dto.HangThanhVien();
                String[] headers = dummy.getExcelHeaders();

                org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(0);
                for (int i = 0; i < headers.length; i++) {
                    org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(headerStyle);
                }

                // Ghi dữ liệu
                int rowNum = 1;
                for (dto.HangThanhVien htv : list) {
                    org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum++);
                    Object[] rowData = htv.toExcelRow();
                    for (int i = 0; i < rowData.length; i++) {
                        row.createCell(i).setCellValue(rowData[i] != null ? rowData[i].toString() : "");
                    }
                }

                // Căn chỉnh cột
                for (int i = 0; i < headers.length; i++)
                    sheet.autoSizeColumn(i);

                try (java.io.FileOutputStream out = new java.io.FileOutputStream(fileToSave)) {
                    workbook.write(out);
                    javax.swing.JOptionPane.showMessageDialog(null, "Xuất danh sách Hạng Thành Viên thành công!");
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(null, "Lỗi khi xuất file: " + e.getMessage());
            }
        }
        return false;
    }

    // ================= NHẬP HẠNG THÀNH VIÊN =================
    public static java.util.ArrayList<dto.HangThanhVien> nhapFileHangThanhVien(java.io.File file) {
        java.util.ArrayList<dto.HangThanhVien> danhSach = new java.util.ArrayList<>();

        try (java.io.FileInputStream fis = new java.io.FileInputStream(file);
                org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook(fis)) {

            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);
            org.apache.poi.ss.usermodel.DataFormatter formatter = new org.apache.poi.ss.usermodel.DataFormatter();

            // Duyệt từ dòng 1 (bỏ qua dòng 0 là Header)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);
                if (row == null)
                    continue;

                // Tên hạng không được để trống
                String tenHang = formatter.formatCellValue(row.getCell(1)).trim();
                if (tenHang.isEmpty())
                    continue;

                dto.HangThanhVien htv = new dto.HangThanhVien();
                // Mã Hạng để trống, DB hoặc BUS sẽ tự sinh mã mới
                htv.setTenHang(tenHang);

                // Xử lý Phần trăm giảm
                try {
                    htv.setPhanTramGiam(Integer.parseInt(formatter.formatCellValue(row.getCell(2)).trim()));
                } catch (Exception e) {
                    htv.setPhanTramGiam(0);
                }

                // Xử lý Điều kiện (cần xóa dấu phẩy do lúc xuất file có format hàng nghìn)
                try {
                    String dieuKienStr = formatter.formatCellValue(row.getCell(3)).replace(",", "").trim();
                    htv.setDieuKien(Double.parseDouble(dieuKienStr));
                } catch (Exception e) {
                    htv.setDieuKien(0);
                }

                danhSach.add(htv);
            }

        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Lỗi đọc file Excel: File không đúng định dạng!");
        }

        return danhSach;
    }

    // ================= XUẤT PHIẾU KIỂM KÊ =================
    // ================= XUẤT PHIẾU KIỂM KÊ (Chuẩn 3 lớp) =================
    public static boolean xuatFilePhieuKiemKe(java.util.ArrayList<dto.PhieuKiemKe> list, String filePath) {
        try (org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Phiếu Kiểm Kê");

            // Style cho Header
            org.apache.poi.ss.usermodel.CellStyle headerStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            // Lấy Header từ DTO
            dto.PhieuKiemKe dummy = new dto.PhieuKiemKe();
            String[] headers = dummy.getExcelHeaders();

            // Ghi Header
            org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Ghi dữ liệu
            int rowNum = 1;
            for (dto.PhieuKiemKe pkk : list) {
                org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum++);
                Object[] rowData = pkk.toExcelRow();
                for (int i = 0; i < rowData.length; i++) {
                    // Kiểm tra null để tránh lỗi NullPointerException
                    row.createCell(i).setCellValue(rowData[i] != null ? rowData[i].toString() : "");
                }
            }

            // Tự động căn chỉnh độ rộng cột
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Mở luồng để ghi ra file dựa vào filePath truyền vào
            try (java.io.FileOutputStream out = new java.io.FileOutputStream(filePath)) {
                workbook.write(out);
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Ném lỗi lên trên thay vì show Message box ở đây
            // throw new RuntimeException("Lỗi ghi file Excel: " + e.getMessage());
            return false;
        }
    }

    // ================= NHẬP PHIẾU KIỂM KÊ (Chuẩn 3 lớp) =================
    public static java.util.ArrayList<dto.PhieuKiemKe> nhapFilePhieuKiemKe(java.io.File file) {
        java.util.ArrayList<dto.PhieuKiemKe> danhSach = new java.util.ArrayList<>();

        try (java.io.FileInputStream fis = new java.io.FileInputStream(file);
                org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook(fis)) {

            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);
            org.apache.poi.ss.usermodel.DataFormatter formatter = new org.apache.poi.ss.usermodel.DataFormatter();

            // Duyệt từ dòng 1 (bỏ qua Header)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);
                if (row == null)
                    continue;

                // Kiểm tra xem dòng có dữ liệu Lô không (dựa vào cột 2 là Mã Lô)
                String maLo = formatter.formatCellValue(row.getCell(2)).trim();
                if (maLo.isEmpty())
                    continue;

                dto.PhieuKiemKe pkk = new dto.PhieuKiemKe();

                // Cột 0: Mã Phiếu Kiểm
                pkk.setMaKK(formatter.formatCellValue(row.getCell(0)).trim());

                // Cột 1: Ngày kiểm
                pkk.setNgayKiem(formatter.formatCellValue(row.getCell(1)).trim());

                // Cột 2: Mã Lô
                pkk.setMaLo(maLo);

                // Cột 3: Loại Lô
                pkk.setLoaiLo(formatter.formatCellValue(row.getCell(3)).trim());

                // Cột 4: Số lượng sổ sách
                try {
                    pkk.setSoLuongSoSach(Double.parseDouble(formatter.formatCellValue(row.getCell(4)).trim()));
                } catch (Exception e) {
                    pkk.setSoLuongSoSach(0.0);
                }

                // Cột 5: Số lượng thực tế
                try {
                    pkk.setSoLuongThuc(Double.parseDouble(formatter.formatCellValue(row.getCell(5)).trim()));
                } catch (Exception e) {
                    pkk.setSoLuongThuc(0.0);
                }

                pkk.setMaNV(PhienDangNhap.getUser().getMaNV());

                pkk.setTrangThaiXuLy(formatter.formatCellValue(row.getCell(7)).trim());

                danhSach.add(pkk);
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Lưu ý: Không dùng JOptionPane ở đây nữa
        }

        return danhSach;
    }

    public static ArrayList<NhanVien> nhapFileNhanVien(File file) {
        ArrayList<NhanVien> danhSach = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file);
                Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null)
                    continue;

                String tenNV = formatter.formatCellValue(row.getCell(1)).trim();
                if (tenNV.isEmpty())
                    continue;

                NhanVien nv = new NhanVien();
                nv.setMaNV(formatter.formatCellValue(row.getCell(0)).trim());
                nv.setTenNV(tenNV);
                nv.setGioiTinh(formatter.formatCellValue(row.getCell(2)).trim());
                nv.setNgaySinh(formatter.formatCellValue(row.getCell(3)).trim());
                nv.setSdt(formatter.formatCellValue(row.getCell(4)).trim());
                nv.setDiaChi(formatter.formatCellValue(row.getCell(5)).trim());

                nv.setAnh("");
                danhSach.add(nv);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return danhSach;
    }

    public static boolean xuatFileNhanVien(File file, ArrayList<NhanVien> list) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Nhân Viên");

            // Header logic
            Row headerRow = sheet.createRow(0);
            String[] headers = new NhanVien().getExcelHeaders();
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Data logic
            int rowNum = 1;
            for (NhanVien nv : list) {
                Row row = sheet.createRow(rowNum++);
                Object[] rowData = nv.toExcelRow();
                for (int i = 0; i < rowData.length; i++) {
                    row.createCell(i).setCellValue(rowData[i] != null ? rowData[i].toString() : "");
                }
            }

            try (FileOutputStream out = new FileOutputStream(file)) {
                workbook.write(out);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<PhieuNhapSanPham> nhapFilePhieuNhapSanPham(File file) {
        ArrayList<PhieuNhapSanPham> danhSachPN = new ArrayList<>();
        HashMap<String, PhieuNhapSanPham> mapPN = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(file);
                Workbook workbook = new XSSFWorkbook(fis)) {

            DataFormatter formatter = new DataFormatter();

            // --- SHEET 1: PHIẾU NHẬP ---
            Sheet sheet1 = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet1.getLastRowNum(); i++) {
                Row row = sheet1.getRow(i);
                if (row == null)
                    continue;

                String maPN = formatter.formatCellValue(row.getCell(0)).trim();
                if (maPN.isEmpty())
                    continue;

                PhieuNhapSanPham pn = new PhieuNhapSanPham();
                pn.setMaPN(maPN);
                pn.setNgayNhap(formatter.formatCellValue(row.getCell(1)).trim());
                pn.setMaNV(formatter.formatCellValue(row.getCell(2)).trim());
                pn.setGhiChu(formatter.formatCellValue(row.getCell(3)).trim());
                pn.setMaNCC(formatter.formatCellValue(row.getCell(4)).trim());
                pn.setTrangThaiXuLy(formatter.formatCellValue(row.getCell(5)).trim());
                pn.setListLoSanPham(new ArrayList<>());
                pn.setTongTien(0); // Sẽ cộng dồn từ các lô ở sheet 2

                mapPN.put(maPN, pn);
                danhSachPN.add(pn);
            }

            // --- SHEET 2: LÔ SẢN PHẨM ---
            if (workbook.getNumberOfSheets() > 1) {
                Sheet sheet2 = workbook.getSheetAt(1);
                for (int i = 1; i <= sheet2.getLastRowNum(); i++) {
                    Row row = sheet2.getRow(i);
                    if (row == null)
                        continue;

                    String maPNLienKet = formatter.formatCellValue(row.getCell(1)).trim();
                    PhieuNhapSanPham pn = mapPN.get(maPNLienKet);

                    if (pn != null) {
                        LoSanPham lo = new LoSanPham();
                        lo.setMaLoSP(formatter.formatCellValue(row.getCell(0)).trim());
                        lo.setMaPN(maPNLienKet);
                        lo.setMaSP(formatter.formatCellValue(row.getCell(2)).trim());

                        // Xử lý số lượng (Double)
                        String soLuongStr = formatter.formatCellValue(row.getCell(3)).replaceAll("[^0-9.]", "");
                        lo.setSoLuong(soLuongStr.isEmpty() ? 0.0 : Double.parseDouble(soLuongStr));

                        lo.setNgayNhap(pn.getNgayNhap()); // Lấy ngày từ phiếu nhập
                        lo.setNgaySanXuat(formatter.formatCellValue(row.getCell(4)).trim());
                        lo.setHanSuDung(formatter.formatCellValue(row.getCell(5)).trim());

                        // Xử lý giá nhập (Double)
                        String giaStr = formatter.formatCellValue(row.getCell(6)).replaceAll("[^0-9.]", "");
                        double gia = giaStr.isEmpty() ? 0.0 : Double.parseDouble(giaStr);
                        lo.setGiaNhap(gia);

                        lo.setTrangThaiXuLy(pn.getTrangThaiXuLy());

                        pn.getListLoSanPham().add(lo);
                        // Cộng dồn tổng tiền cho Phiếu nhập
                        pn.setTongTien(pn.getTongTien() + (lo.getSoLuong() * gia));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return danhSachPN;
    }

    public static boolean xuatFilePhieuNhapSanPham(File file, ArrayList<PhieuNhapSanPham> list) {
        if (list == null)
            return false;

        try (Workbook workbook = new XSSFWorkbook()) {
            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            // --- SHEET 1: PHIẾU NHẬP ---
            Sheet sheet1 = workbook.createSheet("Phiếu Nhập");
            String[] headers1 = { "Mã PN", "Ngày Nhập", "Mã NV", "Ghi Chú", "Mã NCC", "Trạng Thái", "Tổng Tiền" };
            Row rowH1 = sheet1.createRow(0);
            for (int i = 0; i < headers1.length; i++) {
                Cell cell = rowH1.createCell(i);
                cell.setCellValue(headers1[i]);
                cell.setCellStyle(headerStyle);
            }

            // --- SHEET 2: CHI TIẾT LÔ SẢN PHẨM ---
            Sheet sheet2 = workbook.createSheet("Chi Tiết Lô");
            String[] headers2 = { "Mã Lô", "Mã PN", "Mã SP", "Số Lượng", "NSX", "HSD", "Giá Nhập", "Thành Tiền" };
            Row rowH2 = sheet2.createRow(0);
            for (int i = 0; i < headers2.length; i++) {
                Cell cell = rowH2.createCell(i);
                cell.setCellValue(headers2[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowNum1 = 1;
            int rowNum2 = 1;

            for (PhieuNhapSanPham pn : list) {
                // Ghi Sheet 1
                Row row1 = sheet1.createRow(rowNum1++);
                row1.createCell(0).setCellValue(pn.getMaPN());
                row1.createCell(1).setCellValue(pn.getNgayNhap());
                row1.createCell(2).setCellValue(pn.getMaNV());
                row1.createCell(3).setCellValue(pn.getGhiChu());
                row1.createCell(4).setCellValue(pn.getMaNCC());
                row1.createCell(5).setCellValue(pn.getTrangThaiXuLy());
                row1.createCell(6).setCellValue(pn.getTongTien());

                // Ghi Sheet 2
                if (pn.getListLoSanPham() != null) {
                    for (LoSanPham lo : pn.getListLoSanPham()) {
                        Row row2 = sheet2.createRow(rowNum2++);
                        row2.createCell(0).setCellValue(lo.getMaLoSP());
                        row2.createCell(1).setCellValue(pn.getMaPN());
                        row2.createCell(2).setCellValue(lo.getMaSP());
                        row2.createCell(3).setCellValue(lo.getSoLuong());
                        row2.createCell(4).setCellValue(lo.getNgaySanXuat());
                        row2.createCell(5).setCellValue(lo.getHanSuDung());
                        row2.createCell(6).setCellValue(lo.getGiaNhap());
                        row2.createCell(7).setCellValue(lo.getSoLuong() * lo.getGiaNhap());
                    }
                }
            }

            // Auto-size columns
            for (int i = 0; i < headers1.length; i++)
                sheet1.autoSizeColumn(i);
            for (int i = 0; i < headers2.length; i++)
                sheet2.autoSizeColumn(i);

            try (FileOutputStream out = new FileOutputStream(file)) {
                workbook.write(out);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<PhieuNhapNguyenLieu> nhapFilePhieuNhapNguyenLieu(File file) {
        ArrayList<PhieuNhapNguyenLieu> danhSachPN = new ArrayList<>();
        HashMap<String, PhieuNhapNguyenLieu> mapPN = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(file);
                Workbook workbook = new XSSFWorkbook(fis)) {

            DataFormatter formatter = new DataFormatter();

            // --- SHEET 1: PHIẾU NHẬP NGUYÊN LIỆU ---
            Sheet sheet1 = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet1.getLastRowNum(); i++) {
                Row row = sheet1.getRow(i);
                if (row == null)
                    continue;

                String maPN = formatter.formatCellValue(row.getCell(0)).trim();
                if (maPN.isEmpty())
                    continue;

                PhieuNhapNguyenLieu pn = new PhieuNhapNguyenLieu();
                pn.setMaPN(maPN);
                pn.setNgayNhap(formatter.formatCellValue(row.getCell(1)).trim());
                pn.setMaNV(formatter.formatCellValue(row.getCell(2)).trim());
                pn.setGhiChu(formatter.formatCellValue(row.getCell(3)).trim());
                pn.setMaNCC(formatter.formatCellValue(row.getCell(4)).trim());
                pn.setTrangThaiXuLy(formatter.formatCellValue(row.getCell(5)).trim());
                pn.setListLoNguyenLieu(new ArrayList<>());
                pn.setTongTien(0);

                mapPN.put(maPN, pn);
                danhSachPN.add(pn);
            }

            // --- SHEET 2: CHI TIẾT LÔ NGUYÊN LIỆU ---
            if (workbook.getNumberOfSheets() > 1) {
                Sheet sheet2 = workbook.getSheetAt(1);
                for (int i = 1; i <= sheet2.getLastRowNum(); i++) {
                    Row row = sheet2.getRow(i);
                    if (row == null)
                        continue;

                    String maPNLienKet = formatter.formatCellValue(row.getCell(1)).trim();
                    PhieuNhapNguyenLieu pn = mapPN.get(maPNLienKet);

                    if (pn != null) {
                        LoNguyenLieu lo = new LoNguyenLieu();
                        lo.setMaLoNL(formatter.formatCellValue(row.getCell(0)).trim());
                        lo.setMaPN(maPNLienKet);
                        lo.setMaNL(formatter.formatCellValue(row.getCell(2)).trim());

                        // Parse số lượng
                        String slStr = formatter.formatCellValue(row.getCell(3)).replaceAll("[^0-9.]", "");
                        lo.setSoLuong(slStr.isEmpty() ? 0.0 : Double.parseDouble(slStr));

                        lo.setNgayNhap(pn.getNgayNhap());
                        lo.setNgaySanXuat(formatter.formatCellValue(row.getCell(4)).trim());
                        lo.setHanSuDung(formatter.formatCellValue(row.getCell(5)).trim());

                        // Parse giá nhập
                        String giaStr = formatter.formatCellValue(row.getCell(6)).replaceAll("[^0-9.]", "");
                        double gia = giaStr.isEmpty() ? 0.0 : Double.parseDouble(giaStr);
                        lo.setGiaNhap(gia);

                        pn.getListLoNguyenLieu().add(lo);
                        // Tự động tính lại tổng tiền Master dựa trên Detail
                        pn.setTongTien(pn.getTongTien() + (lo.getSoLuong() * gia));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return danhSachPN;
    }

    public static boolean xuatFilePhieuNhapNguyenLieu(File file, ArrayList<PhieuNhapNguyenLieu> list) {
        if (list == null || list.isEmpty())
            return false;

        try (Workbook workbook = new XSSFWorkbook()) {
            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            // --- SHEET 1: PHIẾU NHẬP ---
            Sheet sheet1 = workbook.createSheet("Phiếu Nhập NL");
            String[] headers1 = new PhieuNhapNguyenLieu().getExcelHeaders(); // {"Mã PN", "Ngày Nhập", ...}
            Row rowH1 = sheet1.createRow(0);
            for (int i = 0; i < headers1.length; i++) {
                Cell cell = rowH1.createCell(i);
                cell.setCellValue(headers1[i]);
                cell.setCellStyle(headerStyle);
            }

            // --- SHEET 2: CHI TIẾT LÔ NL ---
            Sheet sheet2 = workbook.createSheet("Chi Tiết Lô NL");
            String[] headers2 = { "Mã Lô NL", "Mã PN", "Mã NL", "Số Lượng", "NSX", "HSD", "Giá Nhập", "Thành Tiền" };
            Row rowH2 = sheet2.createRow(0);
            for (int i = 0; i < headers2.length; i++) {
                Cell cell = rowH2.createCell(i);
                cell.setCellValue(headers2[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowNum1 = 1;
            int rowNum2 = 1;

            for (PhieuNhapNguyenLieu pn : list) {
                // Ghi Sheet 1
                Row row1 = sheet1.createRow(rowNum1++);
                Object[] data1 = pn.toExcelRow();
                for (int i = 0; i < data1.length; i++) {
                    row1.createCell(i).setCellValue(data1[i] != null ? data1[i].toString() : "");
                }

                // Ghi Sheet 2
                if (pn.getListLoNguyenLieu() != null) {
                    for (LoNguyenLieu lo : pn.getListLoNguyenLieu()) {
                        Row row2 = sheet2.createRow(rowNum2++);
                        row2.createCell(0).setCellValue(lo.getMaLoNL());
                        row2.createCell(1).setCellValue(pn.getMaPN());
                        row2.createCell(2).setCellValue(lo.getMaNL());
                        row2.createCell(3).setCellValue(lo.getSoLuong());
                        row2.createCell(4).setCellValue(lo.getNgaySanXuat());
                        row2.createCell(5).setCellValue(lo.getHanSuDung());
                        row2.createCell(6).setCellValue(lo.getGiaNhap());
                        row2.createCell(7).setCellValue(lo.getSoLuong() * lo.getGiaNhap());
                    }
                }
            }

            // Auto size
            for (int i = 0; i < headers1.length; i++)
                sheet1.autoSizeColumn(i);
            for (int i = 0; i < headers2.length; i++)
                sheet2.autoSizeColumn(i);

            try (FileOutputStream out = new FileOutputStream(file)) {
                workbook.write(out);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<SanPham> nhapFileSanPham(File file) {
        ArrayList<SanPham> danhSach = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file);
                Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();

            // Duyệt từ dòng 1 (bỏ qua header dòng 0)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null)
                    continue;

                // Đọc Tên SP trước để kiểm tra dòng trống
                String tenSP = formatter.formatCellValue(row.getCell(1)).trim();
                if (tenSP.isEmpty())
                    continue;

                SanPham sp = new SanPham();
                sp.setMaSP(formatter.formatCellValue(row.getCell(0)).trim());
                sp.setTenSP(tenSP);

                // Xử lý Danh Mục (Chỉ set tên, mã sẽ được BUS tìm lại trong DB)
                String tenDM = formatter.formatCellValue(row.getCell(2)).trim();
                DanhMuc danhMuc = new DanhMuc();
                danhMuc.setTenDM(tenDM);
                sp.setDanhMuc(danhMuc);

                // Xử lý các số liệu (Gia, TheTich...)
                try {
                    sp.setGiaBan(Long.parseLong(formatter.formatCellValue(row.getCell(3)).replaceAll("[^0-9]", "")));
                    sp.setGiaNhap(Long.parseLong(formatter.formatCellValue(row.getCell(4)).replaceAll("[^0-9]", "")));
                    sp.setTheTich(Integer.parseInt(formatter.formatCellValue(row.getCell(5)).replaceAll("[^0-9]", "")));
                    sp.setMucCanhBao(
                            Integer.parseInt(formatter.formatCellValue(row.getCell(6)).replaceAll("[^0-9]", "")));
                } catch (Exception e) {
                    // Nếu lỗi định dạng số, có thể gán mặc định hoặc bỏ qua dòng này
                    System.out.println("Lỗi định dạng số tại dòng: " + (i + 1));
                }

                sp.setLoaiNuoc(formatter.formatCellValue(row.getCell(7)).trim());
                sp.setTrangThaiXuLy("Đang kinh doanh"); // Mặc định khi nhập
                sp.setTrangThai(true);
                sp.setAnh(""); // Ảnh thường được cập nhật sau thủ công

                danhSach.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return danhSach;
    }

    // ================= XUẤT FILE SẢN PHẨM =================
    public static boolean xuatFileSanPham(File file, ArrayList<SanPham> list) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sản Phẩm");

            // 1. Tạo Header
            String[] headers = { "Mã SP", "Tên Sản Phẩm", "Danh Mục", "Giá Bán", "Giá Nhập", "Thể Tích", "Mức Cảnh Báo",
                    "Loại Nước" };
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

            // 2. Ghi dữ liệu
            int rowNum = 1;
            for (SanPham sp : list) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(sp.getMaSP());
                row.createCell(1).setCellValue(sp.getTenSP());
                row.createCell(2).setCellValue(sp.getDanhMuc() != null ? sp.getDanhMuc().getTenDM() : "");
                row.createCell(3).setCellValue(sp.getGiaBan());
                row.createCell(4).setCellValue(sp.getGiaNhap());
                row.createCell(5).setCellValue(sp.getTheTich());
                row.createCell(6).setCellValue(sp.getMucCanhBao());
                row.createCell(7).setCellValue(sp.getLoaiNuoc());
            }

            // Tự động giãn cột
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // 3. Ghi file
            try (FileOutputStream out = new FileOutputStream(file)) {
                workbook.write(out);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

        public static ArrayList<KhachHang> nhapFileKhachHang(File file) {
        ArrayList<KhachHang> danhSach = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file);
                Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null)
                    continue;

                String tenKH = formatter.formatCellValue(row.getCell(1)).trim();
                if (tenKH.isEmpty())
                    continue;

                KhachHang kh = new KhachHang();
                kh.setMaKH(formatter.formatCellValue(row.getCell(0)).trim());
                kh.setTenKH(tenKH);
                kh.setGioiTinh(formatter.formatCellValue(row.getCell(2)).trim());
                kh.setSdt(tenKH);(formatter.formatCellValue(row.getCell(0)).trim());
                kh.setMaKH(formatter.formatCellValue(row.getCell(0)).trim());
                kh.setMaKH(formatter.formatCellValue(row.getCell(0)).trim());


                // Xử lý các số liệu (Gia, TheTich...)
                try {
                    sp.setGiaBan(Long.parseLong(formatter.formatCellValue(row.getCell(3)).replaceAll("[^0-9]", "")));
                    sp.setGiaNhap(Long.parseLong(formatter.formatCellValue(row.getCell(4)).replaceAll("[^0-9]", "")));
                    sp.setTheTich(Integer.parseInt(formatter.formatCellValue(row.getCell(5)).replaceAll("[^0-9]", "")));
                    sp.setMucCanhBao(
                            Integer.parseInt(formatter.formatCellValue(row.getCell(6)).replaceAll("[^0-9]", "")));
                } catch (Exception e) {
                    // Nếu lỗi định dạng số, có thể gán mặc định hoặc bỏ qua dòng này
                    System.out.println("Lỗi định dạng số tại dòng: " + (i + 1));
                }

                sp.setLoaiNuoc(formatter.formatCellValue(row.getCell(7)).trim());
                sp.setTrangThaiXuLy("Đang kinh doanh"); // Mặc định khi nhập
                sp.setTrangThai(true);
                sp.setAnh(""); // Ảnh thường được cập nhật sau thủ công

                danhSach.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return danhSach;
    }

    // ================= XUẤT FILE SẢN PHẨM =================
    public static boolean xuatFileSanPham(File file, ArrayList<SanPham> list) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sản Phẩm");

            // 1. Tạo Header
            String[] headers = { "Mã SP", "Tên Sản Phẩm", "Danh Mục", "Giá Bán", "Giá Nhập", "Thể Tích", "Mức Cảnh Báo",
                    "Loại Nước" };
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

            // 2. Ghi dữ liệu
            int rowNum = 1;
            for (SanPham sp : list) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(sp.getMaSP());
                row.createCell(1).setCellValue(sp.getTenSP());
                row.createCell(2).setCellValue(sp.getDanhMuc() != null ? sp.getDanhMuc().getTenDM() : "");
                row.createCell(3).setCellValue(sp.getGiaBan());
                row.createCell(4).setCellValue(sp.getGiaNhap());
                row.createCell(5).setCellValue(sp.getTheTich());
                row.createCell(6).setCellValue(sp.getMucCanhBao());
                row.createCell(7).setCellValue(sp.getLoaiNuoc());
            }

            // Tự động giãn cột
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // 3. Ghi file
            try (FileOutputStream out = new FileOutputStream(file)) {
                workbook.write(out);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
