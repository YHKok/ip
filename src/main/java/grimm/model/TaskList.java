package grimm.model;

import grimm.exception.GrimmException;

import java.util.ArrayList;
import java.util.List;

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
        this.task.mark();
        return this.task;
    }

    public Task unmark(int num) {
        this.task = this.getTask(num);
        this.task.unmark();
        return this.task;
    }

    public Task delete(int num) {
        this.task = this.getTask(num);
        this.taskList.remove(num - 1);
        return this.task;
    }

    public void exceedIndex(int num) throws GrimmException {
        if (this.taskList.isEmpty()) {
            throw new GrimmException("The stage is empty. Try again.");
        }
        if (num < 1 || num > this.getSize()) {
            throw new GrimmException("The stage holds fewer players than you summon. Try again.");
        }
    }

}
