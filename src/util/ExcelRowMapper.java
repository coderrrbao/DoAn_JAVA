package util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.formula.functions.T;

public interface ExcelRowMapper<T> {
    T map(Row row);
}
