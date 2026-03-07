package util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {
    public static <T extends ExcelExport> boolean export(List<T> list, String sheetName) {
        if (list == null || list.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không có dữ liệu để xuất!");
            return false;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File(sheetName + ".xlsx"));

        if (fileChooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();

        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet(sheetName);

            String[] headers = list.get(0).getExcelHeaders();
            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            int rowNum = 1;
            for (T item : list) {
                Row row = sheet.createRow(rowNum++);
                Object[] rowData = item.toExcelRow();

                for (int i = 0; i < rowData.length; i++) {
                    row.createCell(i).setCellValue(
                            rowData[i] == null ? "" : rowData[i].toString());
                }
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream out = new FileOutputStream(fileToSave)) {
                workbook.write(out);
            }

            JOptionPane.showMessageDialog(null, "Xuất thành công!");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage());
            return false;
        }
    }

    public static <T> List<T> importFile(File file, ExcelRowMapper<T> mapper) {

        List<T> list = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(file);
                Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null)
                    continue;

                T obj = mapper.map(row);
                list.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static String getNullableString(Row row, int index) {
        Cell cell = row.getCell(index);
        if (cell == null)
            return null;

        switch (cell.getCellType()) {
            case STRING: {
                String value = cell.getStringCellValue().trim();
                return value.isEmpty() ? null : value;
            }
            case NUMERIC:

                return String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return null;
        }
    }

    public static boolean getBooleanCell(Row row, int index) {
        Cell cell = row.getCell(index);
        if (cell == null)
            return false;

        if (cell.getCellType() == CellType.BOOLEAN) {
            return cell.getBooleanCellValue();
        }

        if (cell.getCellType() == CellType.STRING) {
            return Boolean.parseBoolean(cell.getStringCellValue());
        }

        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue() == 1;
        }

        return false;
    }

    public static Double getDoubleCell(Row row, int index) {
        Cell cell = row.getCell(index);
        if (cell == null)
            return null;

        if (cell.getCellType() == CellType.NUMERIC)
            return cell.getNumericCellValue();

        if (cell.getCellType() == CellType.STRING)
            return Double.parseDouble(cell.getStringCellValue());

        return null;
    }

    public static Double getIntCell(Row row, int index) {
        Cell cell = row.getCell(index);
        if (cell == null)
            return null;

        if (cell.getCellType() == CellType.NUMERIC)
            return cell.getNumericCellValue();

        if (cell.getCellType() == CellType.STRING)
            return  Integer.parseInteger(cell.getStringCellValue());

        return null;
    }
}
