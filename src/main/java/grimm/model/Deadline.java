package grimm.model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Deadline extends Task {
    private String dueDate;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.forLanguageTag("en-SG"));



    public Deadline(String name, String dueDate) {
        super(name);
        this.dueDate = dueDate;
    }

    public Deadline(String name, boolean mark, String dueDate) {
        super(name, mark);
        this.dueDate = dueDate;
    }

    public String getDueDate() {
        return this.dueDate;
    }

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
