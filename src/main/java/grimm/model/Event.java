package grimm.model;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Event extends Task {
    private String startDate;
    private String endDate;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("d MMMM yyyy, h:mm a").withLocale(Locale.forLanguageTag("en-SG"));


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

            LocalDateTime start = LocalDateTime.parse(this.startDate, INPUT_FORMAT);
            LocalDateTime end = LocalDateTime.parse(this.endDate, INPUT_FORMAT);
            return start.format(OUTPUT_FORMAT) + " to " + end.format(OUTPUT_FORMAT);
        } catch (DateTimeException e) {
            return this.startDate + " to: " + this.endDate;
        }
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.formatDateTime() + ")";
    }
}
