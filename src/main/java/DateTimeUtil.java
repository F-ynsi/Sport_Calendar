import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class DateTimeUtil {
    static DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.SHORT);

    public static LocalDateTime parseLocalDateTime(String str) {
        return LocalDateTime.parse(str,  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
