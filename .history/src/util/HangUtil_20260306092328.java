package util;

public class HangUtil {
    public static Integer.parseparseNumber(String text) {
        if (text == null || text.isBlank())
            return 0;
        text = text.replace(",", "");
        return Integer.parse(text);
    }
}
