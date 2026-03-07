package util;

public class HangUtil {
    public static Integer.parseIntparseNumber(String text) {
        if (text == null || text.isBlank())
            return 0;
        text = text.replace(",", "");
        return Integer.parseInt(text);
    }
}
