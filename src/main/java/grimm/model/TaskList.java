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

    public void add(Task task) {
        this.taskList.add(task);
    }

    public Task mark(int num) {
        this.task = this.getTask(num);
        assert this.task != null : "Task to mark should exist";
        this.task.mark();
        return this.task;
    }

    public Task unmark(int num) {
        this.task = this.getTask(num);
        assert this.task != null : "Task to unmark should exist";
        this.task.unmark();
        return this.task;
    }

    public Task delete(int num) {
        this.task = this.getTask(num);
        assert this.task != null : "Task to delete should exist";
        this.taskList.remove(num - 1);
        return this.task;
    }

    public Task update(int num, Task task) {
        this.task = this.getTask(num);
        assert this.task != null : "Task to update should exist";
        this.taskList.set(num - 1, task);
        return this.task;
    }

    /**
     * Validates if the provided index is within the valid range of the task list.
     *
     * @param num The index to validate.
     * @throws GrimmException if the index is invalid (either less than 1 or greater than the list size).
     */
    public void checkExceedIndex(int num) throws GrimmException {
        if (this.taskList.isEmpty()) {
            throw new GrimmException("The stage is empty. Try again.");
        }
        if (num < 1 || num > this.getSize()) {
            throw new GrimmException("The stage holds fewer players than you summon. Try again.");
        }
    }

    public List<Task> findTask(String input) {
        List<Task> newTaskList = new ArrayList<>();
        for (Task task : this.taskList) {
            if (task.getName().toLowerCase().contains(input.toLowerCase())) {
                newTaskList.add(task);
            }
        }

        return newTaskList;
    }

}
