package util;

public class HangUtil {
    public static e parseNumber(String text) {
        if (text == null || text.isBlank())
            return 0;
        text = text.replace(",", "");
        return e.(text);
    }
}
