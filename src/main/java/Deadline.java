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

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.dueDate + ")";
    }
}
