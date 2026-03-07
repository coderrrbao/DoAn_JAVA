package util;

public class HangUtil {
    public static Integer paparseNumber(String text) {
        if (text == null || text.isBlank())
            return 0;
        text = text.replace(",", "");
        return Integer.pa(text);
    }
}
