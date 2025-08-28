package grimm.model;

/**
 * Represents a To-Do task.
 * <p>
 * The ToDo class extends the Task class and represents a simple task
 * that can be marked as completed or remain unmarked. It includes a description
 * and has its own string representation.
 * </p>
 */
public class ToDo extends Task {
    /**
     * Constructs a ToDo task with a given description.
     * <p>
     * The task will initially be unmarked.
     * </p>
     *
     * @param name The description of the ToDo task.
     */
    public ToDo(String name) {
        super(name);
    }

    /**
     * Constructs a ToDo task with a given description and mark status.
     *
     * @param name The description of the ToDo task.
     * @param mark The initial mark status of the task (true for marked, false for unmarked).
     */
    public ToDo(String name, boolean mark) {
        super(name, mark);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
