package util;

public class HangUtil {
    public static Int parseNumber(String text) {
        if (text == null || text.isBlank())
            return 0;
        text = text.replace(",", "");
        return Int.(text);
    }
}
