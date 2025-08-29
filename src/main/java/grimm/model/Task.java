package grimm.model;

/**
 * Represents a generic task with a description and a mark status.
 * <p>
 * The task has a description and can be marked as completed or unmarked. It can be used as the base class
 * for more specific task types, such as ToDo, Deadline, and Event.
 * </p>
 */
public class Task {
    private boolean isMarked = false;
    private String name;

    /**
     * Constructs a Task with a given description.
     * <p>
     * The task will be unmarked.
     * </p>
     *
     * @param name The description of the task.
     */
    public Task(String name) {
        this.name = name;
    }

    /**
     * Constructs a Task with a given description and mark status.
     * <p>
     * If the mark parameter is true, the task will be marked as completed.
     * </p>
     *
     * @param name The description of the task.
     * @param isMarked The initial mark status of the task (true for marked, false for unmarked).
     */
    public Task(String name, boolean isMarked) {
        this.name = name;
        if (isMarked) {
            this.mark();
        }
    }

    public void mark() {
        this.isMarked = true;
    }

    public void unmark() {
        this.isMarked = false;
    }

    public String getName() {
        return this.name;
    }

    public boolean getMark() {
        return this.isMarked;
    }

    @Override
    public String toString() {
        if (this.isMarked) {
            return "[X] " + this.getName();
        } else {
            return "[ ] " + this.getName();
        }
    }

}
