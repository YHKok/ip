import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Event extends Task {
    private String startDate;
    private String endDate;

    public Event(String name, String startDate, String endDate) {
        super(name);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Event(String name, boolean mark, String startDate, String endDate) {
        super(name, mark);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public String formatDateTime() {
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy HHmm");
            LocalDateTime start = LocalDateTime.parse(this.startDate, format);
            LocalDateTime end = LocalDateTime.parse(this.endDate, format);
            DateTimeFormatter sgFormat = DateTimeFormatter.ofPattern("d MMMM yyyy, h:mm a").withLocale(Locale.forLanguageTag("en-SG"));
            return start.format(sgFormat) + " to " + end.format(sgFormat);
        } catch (DateTimeException e) {
            return this.startDate + " to: " + this.endDate;
        }
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.formatDateTime() + ")";
    }
}
