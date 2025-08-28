package grimm.model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents a Deadline task with a due date.
 * <p>
 * The Deadline has a description and a due date. It extends the Task class.
 * Functionality to format and display due dates.
 * </p>
 */

public class Deadline extends Task {
    private String dueDate;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.forLanguageTag("en-SG"));

    /**
     * Constructs a Deadline task with a given description and due date.
     * <p>
     *     The deadline task will be unmarked.
     * </p>
     *
     * @param name The description of the task.
     * @param dueDate The due date of the task in "MM/dd/yyyy" format.
     */
    public Deadline(String name, String dueDate) {
        super(name);
        this.dueDate = dueDate;
    }

    /**
     * Constructs a Deadline task with a given description, a mark status, and a due date.
     *
     * @param name     The description of the task.
     * @param mark     The mark status of the task (true if marked, false if unmarked).
     * @param dueDate  The due date of the task in "MM/dd/yyyy" format.
     */
    public Deadline(String name, boolean mark, String dueDate) {
        super(name, mark);
        this.dueDate = dueDate;
    }

    public String getDueDate() {
        return this.dueDate;
    }

    /**
     * Formats the due date from "MM/dd/yyyy" to "d MMMM yyyy" in Singapore time.
     * If the due date is invalid, it returns the original string.
     *
     * @return The formatted due date, or the original string if parsing fails.
     */
    public String formatDate() {
        try {
            LocalDate localDate = LocalDate.parse(this.dueDate, INPUT_FORMAT);
            return localDate.format(OUTPUT_FORMAT);
        } catch (DateTimeException e) {
            return this.dueDate;
        }
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.formatDate() + ")";
    }
}
