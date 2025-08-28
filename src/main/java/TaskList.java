import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Task> taskList = new ArrayList<>();

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
        System.out.println("Got it. I've added this task:\n" + task + "\nNow you have " + this.getSize() + " tasks in the list.");
    }

    public void mark(int num) {
        this.taskList.get(num - 1).mark();
        System.out.println("Nice! I've marked this task as done: \n" + this.getTask(num));
    }

    public void unmark(int num) {
        this.taskList.get(num - 1).unmark();
        System.out.println("OK, I've marked this task as not done yet: \n" + this.getTask(num));
    }

    public void delete(int num) {
        Task task = this.taskList.get(num - 1);
        this.taskList.remove(num - 1);
        System.out.println("Noted. I've removed this task:\n" + task + "\nNow you have " + this.getSize() + " tasks in the list.");

    }

    public void showTasks() {
        if (this.taskList.isEmpty()) {
            System.out.println("No acts for this stage yet. The troupe awaits your command.");
            return;
        }

        int i = 1;
        for (Task task : this.taskList) {
            System.out.println(i + ". " + task);
            i++;
        }
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
