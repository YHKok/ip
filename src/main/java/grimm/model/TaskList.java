package grimm.model;

import grimm.exception.GrimmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of tasks.
 * <p>
 * The TaskList class manages a collection of tasks and provides functionality
 * for adding, marking, unmarking, deleting tasks, and checking for out-of-bounds indices.
 * </p>
 */
public class TaskList {
    private final List<Task> taskList = new ArrayList<>();
    private Task task;

    public int getSize() {
        return this.taskList.size();
    }

    public Task getTask(int num) {
        return this.taskList.get(num - 1);
    }

    public List<Task> getTaskList() {
        return this.taskList;
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The task to be added.
     */
    public void add(Task task) {
        this.taskList.add(task);
    }

    /**
     * Marks a task as done based on 1-based index.
     *
     * @param num The index of the task to mark.
     * @return The marked task.
     * @throws GrimmException if the index is out of bounds.
     */
    public Task mark(int num) {
        this.task = this.getTask(num);
        this.task.mark();
        return this.task;
    }

    /**
     * Unmarks a task, setting it to the incomplete state, based on 1-based index.
     *
     * @param num The index of the task to unmark.
     * @return The unmarked task.
     * @throws GrimmException if the index is out of bounds.
     */
    public Task unmark(int num) {
        this.task = this.getTask(num);
        this.task.unmark();
        return this.task;
    }

    /**
     * Deletes a task based on 1-based index.
     *
     * @param num The index of the task to delete.
     * @return The deleted task.
     * @throws GrimmException if the index is out of bounds.
     */
    public Task delete(int num) {
        this.task = this.getTask(num);
        this.taskList.remove(num - 1);
        return this.task;
    }

    /**
     * Validates if the provided index is within the valid range of the task list.
     *
     * @param num The index to validate.
     * @throws GrimmException if the index is invalid (either less than 1 or greater than the list size).
     */
    public void exceedIndex(int num) throws GrimmException {
        if (this.taskList.isEmpty()) {
            throw new GrimmException("The stage is empty. Try again.");
        }
        if (num < 1 || num > this.getSize()) {
            throw new GrimmException("The stage holds fewer players than you summon. Try again.");
        }
    }

}
