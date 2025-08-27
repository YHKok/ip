import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Deadline extends Task {
    private String dueDate;

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
            DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate localDate = LocalDate.parse(this.dueDate, format);
            DateTimeFormatter sgFormat = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.forLanguageTag("en-SG"));
            return localDate.format(sgFormat);
        } catch (DateTimeException e) {
            return this.dueDate;
        }
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.formatDate() + ")";
    }
}
