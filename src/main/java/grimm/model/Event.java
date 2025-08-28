package grimm.model;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents an Event task with a start and end date.
 * <p>
 * The Event has a description, start date and end date. It extends the Task class.
 * Functionality to format and display start date and end date.
 * </p>
 */
public class Event extends Task {
    private String startDate;
    private String endDate;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("d MMMM yyyy, h:mm a").withLocale(Locale.forLanguageTag("en-SG"));

    /**
     * Constructs an Event task with a given description, start date, and end date.
     * <p>
     * The Event will be unmarked.
     * </p>
     *
     * @param name      The description of the event.
     * @param startDate The start date of the event in "MM/dd/yyyy HHmm" format.
     * @param endDate   The end date of the event in "MM/dd/yyyy HHmm" format.
     */
    public Event(String name, String startDate, String endDate) {
        super(name);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Constructs an Event task with a given description, mark status, start date, and end date.
     *
     * @param name      The description of the event.
     * @param mark      The mark status of the event (true if marked, false if unmarked).
     * @param startDate The start date of the event in "MM/dd/yyyy HHmm" format.
     * @param endDate   The end date of the event in "MM/dd/yyyy HHmm" format.
     */

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

    /**
     * Formats the start and end dates of the event from "MM/dd/yyyy HHmm" to "d MMMM yyyy, h:mm a".
     * If the date format is invalid, it returns the raw start and end dates.
     *
     * @return A formatted string with the event's start and end date-times, or the original string if parsing fails.
     */
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
