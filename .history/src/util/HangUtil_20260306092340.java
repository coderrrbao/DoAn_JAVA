package util;

public class HangUtil {
    public static InteparseNumber(String text) {
        if (text == null || text.isBlank())
            return 0;
        text = text.replace(",", "");
        return Integer.parseInt(text);
    }
}
