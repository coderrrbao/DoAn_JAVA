package util;

public interface ExcelExport {
    String[] getExcelHeaders();
    Object[] toExcelRow();
}
