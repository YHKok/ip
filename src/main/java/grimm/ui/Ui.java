package grimm.ui;

import grimm.model.Task;
import grimm.model.TaskList;

import java.util.List;

/**
 * Handles all the user interface outputs for the Grimm task management application.
 * <p>
 * The Ui class is responsible for displaying various messages to the user based on
 * user actions or errors. It handles messages related to tasks, errors, and application
 * flow such as marking/unmarking tasks, listing tasks, and responding to invalid commands.
 * </p>
 */
public class Ui {

    public void welcome() {
        System.out.println("Hello, I'm grimm.app.Grimm\nWhat can I do for you?\n");
    }

    public void bye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void listEmptyMsg() {
        System.err.println("No acts for this stage yet. The troupe awaits your command.");
    }

    public void addMsg(Task task, int size) {
        System.out.println("Got it. I've added this task:\n" + task + "\nNow you have " + size + " tasks in the list.");
    }

    public void markMsg(Task task) {
        System.out.println("Nice! I've marked this task as done: \n" + task);
    }

    public void unmarkMsg(Task task) {
        System.out.println("OK, I've marked this task as not done yet: \n" + task);
    }

    public void deleteMsg(Task task, TaskList taskList) {
        System.out.println("Noted. I've removed this task:\n" + task + "\nNow you have " + taskList.getSize() + " tasks in the list.");
    }

    public void invalidFile() {
        System.err.println("This is not a file I know. Try again.");
    }

    public void invalidDate() {
        System.err.println("The Troupe does not understand this date. Try again with: MM/dd/yyyy format");
    }

    public void invalidDatetime() {
        System.err.println("The Troupe does not understand this date. Try again with: MM/dd/yyyy HHmm format");
    }

    public void unknownCommand() {
        System.err.println("The stage is not prepared for such words, little one. Try a valid command.");
    }

    public void invalidDeadline() {
        System.err.println("A deadline with no end? Try again with: deadline <desc> /by <time>.");
    }

    public void invalidEvent() {
        System.err.println("An event cannot begin and end without a time. Try again with: event <desc> /from <start> /to <end>.");
    }

    public void invalidGrimmMsg(String msg) {
        System.err.println(msg);
    }

    /**
     * Prints the list of tasks to the user.
     * <p>
     * If the task list is empty, it calls the listEmptyMsg() method to notify the user.
     * </p>
     *
     * @param tasks The list of tasks to be displayed.
     */
    public void showTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            this.listEmptyMsg();
            return;
        }

        int i = 1;
        for (Task task : tasks) {
            System.out.println(i + ". " + task);
            i++;
        }
    }
}
