package util;

public class HangUtil {
    public static long parseNumber(String text) {
        if (text == null || text.isBlank())
            return 0;
        text = text.replace(",", "");
        return Long.parseLong(text);
    }
}
