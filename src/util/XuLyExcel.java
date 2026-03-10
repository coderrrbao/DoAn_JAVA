package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import bus.NhomQuyenBUS;
import bus.ChiTietHoaDonBUS;
import bus.DanhMucBUS;
import bus.NguyenLieuBUS;
import dto.ChiTietCongThuc;
import dto.ChiTietHoaDon;
import dto.ChiTietNhaCungCap;
import dto.CongThuc;
import dto.DanhMuc;
import dto.HangThanhVien;
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

                CellStyle headerStyle = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                headerStyle.setFont(font);

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

                    int size = (nq.getListQuyen() != null) ? nq.getListQuyen().size() : 0;
                    row.createCell(2).setCellValue(size);
                }
                for (int i = 0; i < headers1.length; i++)
                    sheet1.autoSizeColumn(i);

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

            Sheet sheet1 = workbook.createSheet("NhaCungCap");

            String[] headers1 = { "Mã NCC", "Tên Nhà Cung Cấp", "", "Số Điện Thoại", "Địa Chỉ" };
            Row headerRow1 = sheet1.createRow(0);
            for (int i = 0; i < headers1.length; i++) {
                headerRow1.createCell(i).setCellValue(headers1[i]);
            }

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

                Row row1 = sheet1.createRow(rowNum1++);
                row1.createCell(0).setCellValue(ncc.getMaNCC());
                row1.createCell(1).setCellValue(ncc.getTenNCC());
                row1.createCell(2).setCellValue("");
                row1.createCell(3).setCellValue(ncc.getSoDienThoai());
                row1.createCell(4).setCellValue(ncc.getDiaChi());

                for (ChiTietNhaCungCap ct : ncc.getListChiTietNhaCungCap()) {
                    Row row2 = sheet2.createRow(rowNum2++);
                    row2.createCell(0).setCellValue(stt++);
                    row2.createCell(1).setCellValue(ncc.getMaNCC());
                    row2.createCell(2).setCellValue(ct.getLoaiDoiTuong());
                    row2.createCell(3).setCellValue(ct.getMaDoiTuong());
                    row2.createCell(4).setCellValue(ct.getGiaNhap());
                }
            }

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

                ncc.setSoDienThoai(formatter.formatCellValue(row.getCell(3)).trim());
                ncc.setDiaChi(formatter.formatCellValue(row.getCell(4)).trim());

                mapNcc.put(maNcc, ncc);
                danhSachNcc.add(ncc);
            }

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
public static boolean xuatFileHangThanhVien(File file, ArrayList<HangThanhVien> list) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Hang Thanh Vien");
            String[] columns = { "Mã Hạng", "Tên Hạng", "Phần Trăm Giảm (%)", "Điều Kiện (VNĐ)" };

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
            for (HangThanhVien htv : list) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(htv.getMaHang());
                row.createCell(1).setCellValue(htv.getTenHang());
                row.createCell(2).setCellValue(htv.getPhanTramGiam());
                row.createCell(3).setCellValue(htv.getDieuKien());
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<HangThanhVien> nhapFileHangThanhVien(File file) {
        ArrayList<HangThanhVien> dsMoi = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                HangThanhVien htv = new HangThanhVien();
                htv.setMaHang(null); 
                htv.setTenHang(docCell(row.getCell(1)));
                htv.setPhanTramGiam(chuyenSoInt(docCell(row.getCell(2))));
                htv.setDieuKien(chuyenSoDouble(docCell(row.getCell(3))));

                dsMoi.add(htv);
            }
            return dsMoi;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean xuatFilePhieuKiemKe(java.util.ArrayList<dto.PhieuKiemKe> list, String filePath) {
        try (org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Phiếu Kiểm Kê");

            String[] headers = {
                    "Mã Kiểm Kê", "Mã Nhân Viên", "Ngày Kiểm", "Mã Lô",
                    "Loại Lô", "SL Sổ Sách", "SL Thực Tế", "Ghi Chú", "Trạng Thái"
            };

            org.apache.poi.ss.usermodel.CellStyle headerStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowNum = 1;
            for (dto.PhieuKiemKe pkk : list) {
                org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(pkk.getMaKK());

                row.createCell(1).setCellValue(pkk.getMaNV());

                row.createCell(2).setCellValue(pkk.getNgayKiem());

                row.createCell(3).setCellValue(pkk.getMaLo());

                row.createCell(4).setCellValue(pkk.getLoaiLo());

                if (pkk.getSoLuongSoSach() != null) {
                    row.createCell(5).setCellValue(pkk.getSoLuongSoSach());
                }

                if (pkk.getSoLuongThuc() != null) {
                    row.createCell(6).setCellValue(pkk.getSoLuongThuc());
                }

                row.createCell(7).setCellValue(pkk.getGhiChu());

                row.createCell(8).setCellValue(pkk.getTrangThaiXuLy());
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            try (java.io.FileOutputStream out = new java.io.FileOutputStream(filePath)) {
                workbook.write(out);
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static java.util.ArrayList<dto.PhieuKiemKe> nhapFilePhieuKiemKe(java.io.File file) {
        java.util.ArrayList<dto.PhieuKiemKe> danhSach = new java.util.ArrayList<>();

        try (java.io.FileInputStream fis = new java.io.FileInputStream(file);
                org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook(fis)) {

            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);
            org.apache.poi.ss.usermodel.DataFormatter formatter = new org.apache.poi.ss.usermodel.DataFormatter();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);
                if (row == null)
                    continue;

                String maLo = formatter.formatCellValue(row.getCell(2)).trim();
                if (maLo.isEmpty())
                    continue;

                dto.PhieuKiemKe pkk = new dto.PhieuKiemKe();

                pkk.setMaKK(formatter.formatCellValue(row.getCell(0)).trim());

                pkk.setNgayKiem(formatter.formatCellValue(row.getCell(1)).trim());

                pkk.setMaLo(maLo);

                pkk.setLoaiLo(formatter.formatCellValue(row.getCell(3)).trim());

                try {
                    pkk.setSoLuongSoSach(Double.parseDouble(formatter.formatCellValue(row.getCell(4)).trim()));
                } catch (Exception e) {
                    pkk.setSoLuongSoSach(0.0);
                }

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

    public static boolean xuatFileNhanVien(java.io.File file, java.util.ArrayList<dto.NhanVien> list) {
        try (org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Nhân Viên");

            String[] headers = { "Mã NV", "Tên Nhân Viên", "Giới Tính", "Ngày Sinh", "Số Điện Thoại", "Địa Chỉ",
                    "Ảnh" };

            org.apache.poi.ss.usermodel.CellStyle headerStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowNum = 1;
            for (dto.NhanVien nv : list) {
                org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(nv.getMaNV());
                row.createCell(1).setCellValue(nv.getTenNV());
                row.createCell(2).setCellValue(nv.getGioiTinh());
                row.createCell(3).setCellValue(nv.getNgaySinh());
                row.createCell(4).setCellValue(nv.getSdt());
                row.createCell(5).setCellValue(nv.getDiaChi());
                row.createCell(6).setCellValue(nv.getAnh());
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            try (java.io.FileOutputStream out = new java.io.FileOutputStream(file)) {
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
                pn.setTongTien(0);

                mapPN.put(maPN, pn);
                danhSachPN.add(pn);
            }

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

                        String soLuongStr = formatter.formatCellValue(row.getCell(3)).replaceAll("[^0-9.]", "");
                        lo.setSoLuong(soLuongStr.isEmpty() ? 0.0 : Double.parseDouble(soLuongStr));

                        lo.setNgayNhap(pn.getNgayNhap());
                        lo.setNgaySanXuat(formatter.formatCellValue(row.getCell(4)).trim());
                        lo.setHanSuDung(formatter.formatCellValue(row.getCell(5)).trim());

                        String giaStr = formatter.formatCellValue(row.getCell(6)).replaceAll("[^0-9.]", "");
                        double gia = giaStr.isEmpty() ? 0.0 : Double.parseDouble(giaStr);
                        lo.setGiaNhap(gia);

                        lo.setTrangThaiXuLy(pn.getTrangThaiXuLy());

                        pn.getListLoSanPham().add(lo);

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

            Sheet sheet1 = workbook.createSheet("Phiếu Nhập");
            String[] headers1 = { "Mã PN", "Ngày Nhập", "Mã NV", "Ghi Chú", "Mã NCC", "Trạng Thái", "Tổng Tiền" };
            Row rowH1 = sheet1.createRow(0);
            for (int i = 0; i < headers1.length; i++) {
                Cell cell = rowH1.createCell(i);
                cell.setCellValue(headers1[i]);
                cell.setCellStyle(headerStyle);
            }

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

                Row row1 = sheet1.createRow(rowNum1++);
                row1.createCell(0).setCellValue(pn.getMaPN());
                row1.createCell(1).setCellValue(pn.getNgayNhap());
                row1.createCell(2).setCellValue(pn.getMaNV());
                row1.createCell(3).setCellValue(pn.getGhiChu());
                row1.createCell(4).setCellValue(pn.getMaNCC());
                row1.createCell(5).setCellValue(pn.getTrangThaiXuLy());
                row1.createCell(6).setCellValue(pn.getTongTien());

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

                        String slStr = formatter.formatCellValue(row.getCell(3)).replaceAll("[^0-9.]", "");
                        lo.setSoLuong(slStr.isEmpty() ? 0.0 : Double.parseDouble(slStr));

                        lo.setNgayNhap(pn.getNgayNhap());
                        lo.setNgaySanXuat(formatter.formatCellValue(row.getCell(4)).trim());
                        lo.setHanSuDung(formatter.formatCellValue(row.getCell(5)).trim());

                        String giaStr = formatter.formatCellValue(row.getCell(6)).replaceAll("[^0-9.]", "");
                        double gia = giaStr.isEmpty() ? 0.0 : Double.parseDouble(giaStr);
                        lo.setGiaNhap(gia);

                        pn.getListLoNguyenLieu().add(lo);

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

    public static boolean xuatFilePhieuNhapNguyenLieu(java.io.File file,
            java.util.ArrayList<dto.PhieuNhapNguyenLieu> list) {
        if (list == null || list.isEmpty())
            return false;

        try (org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {

            org.apache.poi.ss.usermodel.CellStyle headerStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            org.apache.poi.ss.usermodel.Sheet sheet1 = workbook.createSheet("Phiếu Nhập NL");
            String[] headers1 = { "Mã PN", "Ngày Nhập", "Mã NV", "Tổng Tiền", "Mã NCC", "Trạng Thái", "Ghi Chú" };

            org.apache.poi.ss.usermodel.Row rowH1 = sheet1.createRow(0);
            for (int i = 0; i < headers1.length; i++) {
                org.apache.poi.ss.usermodel.Cell cell = rowH1.createCell(i);
                cell.setCellValue(headers1[i]);
                cell.setCellStyle(headerStyle);
            }

            org.apache.poi.ss.usermodel.Sheet sheet2 = workbook.createSheet("Chi Tiết Lô NL");
            String[] headers2 = { "Mã Lô NL", "Mã PN", "Mã NL", "Số Lượng", "NSX", "HSD", "Giá Nhập", "Thành Tiền" };

            org.apache.poi.ss.usermodel.Row rowH2 = sheet2.createRow(0);
            for (int i = 0; i < headers2.length; i++) {
                org.apache.poi.ss.usermodel.Cell cell = rowH2.createCell(i);
                cell.setCellValue(headers2[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowNum1 = 1;
            int rowNum2 = 1;

            for (dto.PhieuNhapNguyenLieu pn : list) {

                org.apache.poi.ss.usermodel.Row row1 = sheet1.createRow(rowNum1++);
                row1.createCell(0).setCellValue(pn.getMaPN());
                row1.createCell(1).setCellValue(pn.getNgayNhap());
                row1.createCell(2).setCellValue(pn.getMaNV());
                row1.createCell(3).setCellValue(pn.getTongTien());
                row1.createCell(4).setCellValue(pn.getMaNCC());
                row1.createCell(5).setCellValue(pn.getTrangThaiXuLy());
                row1.createCell(6).setCellValue(pn.getGhiChu());

                if (pn.getListLoNguyenLieu() != null) {
                    for (dto.LoNguyenLieu lo : pn.getListLoNguyenLieu()) {
                        org.apache.poi.ss.usermodel.Row row2 = sheet2.createRow(rowNum2++);
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

            for (int i = 0; i < headers1.length; i++)
                sheet1.autoSizeColumn(i);
            for (int i = 0; i < headers2.length; i++)
                sheet2.autoSizeColumn(i);

            try (java.io.FileOutputStream out = new java.io.FileOutputStream(file)) {
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
                kh.setSdt(formatter.formatCellValue(row.getCell(3)).trim());
                kh.setTenDaMua(Double.parseDouble(formatter.formatCellValue(row.getCell(4)).trim()));
                kh.setMaHang(formatter.formatCellValue(row.getCell(5)).trim());

                danhSach.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return danhSach;
    }

    public static boolean xuatFileKhachHang(File file, ArrayList<KhachHang> list) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sản Phẩm");

            String[] headers = { "Mã KH", "Tên Khách Hàng", "Giới Tính", "Số Điện Thoại", "Tổng Chi Tiêu",
                    "Hạng Thành Viên" };
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
            for (KhachHang kh : list) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(kh.getMaKH());
                row.createCell(1).setCellValue(kh.getTenKH());
                row.createCell(2).setCellValue(kh.getGioiTinh());
                row.createCell(3).setCellValue(kh.getSdt());
                row.createCell(4).setCellValue(kh.getTenDaMua());
                row.createCell(5).setCellValue(kh.getMaHang());
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
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

    public static boolean xuatFileSanPham(File file, ArrayList<SanPham> listSanPham) {
        if (listSanPham == null || listSanPham.isEmpty()) {
            return false;
        }

        try (Workbook workbook = new XSSFWorkbook()) {

            // 1. Tạo Style in đậm cho dòng tiêu đề (Header)
            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            // 2. KHỞI TẠO 4 SHEET
            Sheet sheetSP = workbook.createSheet("Sản Phẩm");
            Sheet sheetSize = workbook.createSheet("Size");
            Sheet sheetCT = workbook.createSheet("Công Thức");
            Sheet sheetCTCT = workbook.createSheet("Chi Tiết Công Thức");

            // 3. TẠO HEADER CHO 4 SHEET
            String[] headersSP = { "Mã SP", "Tên SP", "Mã Danh Mục", "Giá Bán", "Loại Nước", "Ảnh", "Thể Tích",
                    "Mức Cảnh Báo", "Trạng Thái" };
            String[] headersSize = { "Mã Size", "Mã SP", "Tên Size", "Phần Trăm Giá", "Phần Trăm NL" };
            String[] headersCT = { "Mã CT", "Mã SP" };
            String[] headersCTCT = { "Mã CTCT", "Mã CT", "Mã Nguyên Liệu", "Số Lượng" };

            taoHeader(sheetSP, headersSP, headerStyle);
            taoHeader(sheetSize, headersSize, headerStyle);
            taoHeader(sheetCT, headersCT, headerStyle);
            taoHeader(sheetCTCT, headersCTCT, headerStyle);

            // 4. DUYỆT LIST SẢN PHẨM VÀ BÓC TÁCH DỮ LIỆU ĐIỀN VÀO TỪNG SHEET
            int rowNumSP = 1;
            int rowNumSize = 1;
            int rowNumCT = 1;
            int rowNumCTCT = 1;

            for (SanPham sp : listSanPham) {
                // --- Ghi dữ liệu Sheet Sản Phẩm ---
                Row rowSP = sheetSP.createRow(rowNumSP++);
                rowSP.createCell(0).setCellValue(sp.getMaSP());
                rowSP.createCell(1).setCellValue(sp.getTenSP());
                rowSP.createCell(2).setCellValue(sp.getDanhMuc() != null ? sp.getDanhMuc().getMaDM() : "");
                rowSP.createCell(3).setCellValue(sp.getGiaBan());
                rowSP.createCell(4).setCellValue(sp.getLoaiNuoc());
                rowSP.createCell(5).setCellValue(sp.getAnh());
                rowSP.createCell(6).setCellValue(sp.getTheTich());
                rowSP.createCell(7).setCellValue(sp.getMucCanhBao());
                rowSP.createCell(8).setCellValue(sp.getTrangThaiXuLy());

                // --- Ghi dữ liệu Sheet Size ---
                if (sp.getListSize() != null) {
                    for (Size size : sp.getListSize()) {
                        Row rowSize = sheetSize.createRow(rowNumSize++);
                        rowSize.createCell(0).setCellValue(size.getMaSize());
                        rowSize.createCell(1).setCellValue(size.getMaSP() != null ? size.getMaSP() : sp.getMaSP());
                        rowSize.createCell(2).setCellValue(size.getTenSize());
                        rowSize.createCell(3).setCellValue(size.getPhanTramGia());
                        rowSize.createCell(4).setCellValue(size.getPhanTramNL());

                    }
                }

                // --- Ghi dữ liệu Sheet Công Thức & Chi Tiết Công Thức ---
                CongThuc ct = sp.getCongThuc();
                if (ct != null) {
                    // Sheet Công Thức
                    Row rowCT = sheetCT.createRow(rowNumCT++);
                    rowCT.createCell(0).setCellValue(ct.getMaCT());
                    rowCT.createCell(1).setCellValue(ct.getMaSp() != null ? ct.getMaSp() : sp.getMaSP());

                    // Sheet Chi Tiết Công Thức
                    if (ct.getListChiTietCongThuc() != null) {
                        for (ChiTietCongThuc ctct : ct.getListChiTietCongThuc()) {
                            Row rowCTCT = sheetCTCT.createRow(rowNumCTCT++);
                            rowCTCT.createCell(0).setCellValue(ctct.getMaCTCT());
                            rowCTCT.createCell(1).setCellValue(ctct.getMaCT() != null ? ctct.getMaCT() : ct.getMaCT());
                            rowCTCT.createCell(2)
                                    .setCellValue(ctct.getNguyenLieu() != null ? ctct.getNguyenLieu().getMaNL() : "");
                            rowCTCT.createCell(3).setCellValue(ctct.getSoLuong());
                        }
                    }
                }
            }

            // 5. AUTO-SIZE CÁC CỘT CHO ĐẸP
            tuDongGianCot(sheetSP, headersSP.length);
            tuDongGianCot(sheetSize, headersSize.length);
            tuDongGianCot(sheetCT, headersCT.length);
            tuDongGianCot(sheetCTCT, headersCTCT.length);

            // 6. GHI RA FILE
            try (FileOutputStream out = new FileOutputStream(file)) {
                workbook.write(out);
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // --- CÁC HÀM HỖ TRỢ TRONG CLASS XULYEXCEL ---

    private static void taoHeader(Sheet sheet, String[] headers, CellStyle style) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
        }
    }

    private static void tuDongGianCot(Sheet sheet, int soLuongCot) {
        for (int i = 0; i < soLuongCot; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    public static ArrayList<SanPham> nhapFileSanPham(File file) {
        // Dùng LinkedHashMap để giữ nguyên thứ tự sản phẩm như trong file Excel
        Map<String, SanPham> mapSanPham = new LinkedHashMap<>();
        Map<String, CongThuc> mapCongThuc = new LinkedHashMap<>();

        try (FileInputStream fis = new FileInputStream(file);
                Workbook workbook = new XSSFWorkbook(fis)) {

            // --- 1. ĐỌC SHEET "SẢN PHẨM" (Sheet 0) ---
            Sheet sheetSP = workbook.getSheetAt(0);
            for (int i = 1; i <= sheetSP.getLastRowNum(); i++) {
                Row row = sheetSP.getRow(i);
                if (row == null)
                    continue;

                String maSP = docCell(row.getCell(0));
                if (maSP.isEmpty())
                    continue; // Bỏ qua dòng trống

                SanPham sp = new SanPham();
                sp.setMaSP(maSP);
                sp.setTenSP(docCell(row.getCell(1)));

            
                String danhMucVal = docCell(row.getCell(2));
                DanhMucBUS  danhMucBUS = new DanhMucBUS();
                sp.setDanhMuc(danhMucBUS.timDanhMuc(danhMucVal));

                sp.setGiaBan(chuyenSoLong(docCell(row.getCell(3))));
                sp.setLoaiNuoc(docCell(row.getCell(4)));
                sp.setAnh(docCell(row.getCell(5)));
                sp.setTheTich(chuyenSoInt(docCell(row.getCell(6))));
                sp.setMucCanhBao(chuyenSoInt(docCell(row.getCell(7))));
                sp.setTrangThaiXuLy(docCell(row.getCell(8)));

                sp.setListSize(new ArrayList<>()); // Khởi tạo list size rỗng
                mapSanPham.put(maSP, sp);
            }

            // --- 2. ĐỌC SHEET "SIZE" (Sheet 1) ---
            Sheet sheetSize = workbook.getSheetAt(1);
            for (int i = 1; i <= sheetSize.getLastRowNum(); i++) {
                Row row = sheetSize.getRow(i);
                if (row == null)
                    continue;

                String maSP = docCell(row.getCell(1));
                if (mapSanPham.containsKey(maSP)) {
                    Size size = new Size();
                    size.setMaSize(docCell(row.getCell(0)));
                    size.setMaSP(maSP);
                    size.setTenSize(docCell(row.getCell(2)));
                    size.setPhanTramGia(chuyenSoInt(docCell(row.getCell(3))));
                    size.setPhanTramNL(chuyenSoInt(docCell(row.getCell(4))));

                    mapSanPham.get(maSP).addSize(size); // Đẩy vào listSize của sản phẩm
                }
            }

            // --- 3. ĐỌC SHEET "CÔNG THỨC" (Sheet 2) ---
            Sheet sheetCT = workbook.getSheetAt(2);
            for (int i = 1; i <= sheetCT.getLastRowNum(); i++) {
                Row row = sheetCT.getRow(i);
                if (row == null)
                    continue;

                String maCT = docCell(row.getCell(0));
                String maSP = docCell(row.getCell(1));

                if (mapSanPham.containsKey(maSP)) {
                    CongThuc ct = new CongThuc();
                    ct.setMaCT(maCT);
                    ct.setMaSp(maSP);
                    ct.setListChiTietCongThuc(new ArrayList<>());

                    mapCongThuc.put(maCT, ct); // Lưu vào map để lát ráp CTCT vào
                    mapSanPham.get(maSP).setCongThuc(ct);
                }
            }

            // --- 4. ĐỌC SHEET "CHI TIẾT CÔNG THỨC" (Sheet 3) ---
            Sheet sheetCTCT = workbook.getSheetAt(3);
            for (int i = 1; i <= sheetCTCT.getLastRowNum(); i++) {
                Row row = sheetCTCT.getRow(i);
                if (row == null)
                    continue;

                String maCT = docCell(row.getCell(1));
                if (mapCongThuc.containsKey(maCT)) {
                    ChiTietCongThuc ctct = new ChiTietCongThuc();
                    ctct.setMaCTCT(docCell(row.getCell(0)));
                    ctct.setMaCT(maCT);

                    NguyenLieu nl = NguyenLieuBUS.getNguyenLieuBUS().timNguyenLieu(docCell(row.getCell(2)));
       
                    ctct.setNguyenLieu(nl);
                    System.out.println(chuyenSoDouble(docCell(row.getCell(3))));
                    ctct.setSoLuong(chuyenSoDouble(docCell(row.getCell(3))));

                    mapCongThuc.get(maCT).getListChiTietCongThuc().add(ctct);
                }
            }

            return new ArrayList<>(mapSanPham.values());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String docCell(Cell cell) {
    if (cell == null)
        return "";
    switch (cell.getCellType()) {
        case STRING:
            return cell.getStringCellValue().trim();
        case NUMERIC:
            double value = cell.getNumericCellValue();
            
            if (value == (long) value) {
                return String.valueOf((long) value);
            }
            return String.valueOf(value);
            
        case BOOLEAN:
            return String.valueOf(cell.getBooleanCellValue());
        case FORMULA:
            try {
                return String.valueOf(cell.getNumericCellValue());
            } catch (Exception e) {
                return cell.getStringCellValue();
            }
        default:
            return "";
    }
}

    private static Double chuyenSoDouble(String val) {
        try {
            return Double.parseDouble(val);
        } catch (Exception e) {
            return 0.0;
        }
    }
private static int chuyenSoInt(String val) {
        try {
            return Integer.parseInt(val);
        } catch (Exception e) {
            return 0;
        }
    }

    private static long chuyenSoLong(String val) {
        try {
            return Long.parseLong(val);
        } catch (Exception e) {
            return 0L;
        }
    }

}
