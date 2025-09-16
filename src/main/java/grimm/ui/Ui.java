package grimm.ui;

import grimm.model.Task;
import grimm.model.TaskList;

import java.util.List;

/**
 * Handles all the user interface outputs for the Grimm task management application.
 * <p>
 * The Ui class displays the various messages to the user based on
 * user actions or errors.
 * It handles messages related to tasks, errors, and application
 * flow such as marking and unmarking tasks, listing tasks, and invalid commands.
 * </p>
 */
public class Ui {

    /**
     * AiAssisted
     * Used ChatGPT to help improve the personality of the chatbot
     */
    public static String welcome() {
        return "Ahh… the stage is set!\n"
                + "I am Grimm, master of the Flame and keeper of grand performances.\n"
                + "Speak, little vessel—what dance of tasks shall we begin?\n";
    }

    /**
     * AiAssisted
     * Used ChatGPT to help improve the personality of the chatbot
     */
    public String bye() {
        return "The curtain falls and the flames dim.\n"
                + "Until our next performance, child of shadow… farewell!\n";
    }

    public String listEmptyMsg() {
        return "No acts for this stage yet. The troupe awaits your command.";
    }

    public String addMsg(Task task, int size) {
        return "Got it. I've added this task:\n" + task + "\nNow you have " + size + " tasks in the list.";
    }

    public String markMsg(Task task) {
        return "Nice! I've marked this task as done: \n" + task;
    }

    public String unmarkMsg(Task task) {
        return "OK, I've marked this task as not done yet: \n" + task;
    }

    public String deleteMsg(Task task, TaskList taskList) {
        return "Noted. I've removed this task:\n" + task + "\nNow you have " + taskList.getSize() + " tasks in the list.";
    }

    public String updateMsg(Task task) {
        return "OK, I've updated this task: \n" + task;
    }

    public String invalidFile() {
        return "This is not a file I know. Try again.";
    }

    public String invalidDate() {
        return "The Troupe does not understand this date. Try again with: MM/dd/yyyy format";
    }

    public String invalidDatetime() {
        return "The Troupe does not understand this date. Try again with: MM/dd/yyyy HHmm format";
    }

    public String unknownCommand() {
        return "The stage is not prepared for such words, little one. Try a valid command.";
    }

    public String invalidDeadline() {
        return "A deadline with no end? Try again with: deadline <desc> /by <time>.";
    }

    public String invalidEvent() {
        return "An event cannot begin and end without a time. Try again with: event <desc> /from <start> /to <end>.";
    }

    public String invalidGrimmMsg(String msg) {
        return msg;
    }

    /**
     * Formats and returns the list of tasks as a numbered string.
     *
     * <p>
     * If the provided task list is empty, listEmptyMsg() is returned instead.
     * </p>
     *
     * @param tasks the list of tasks to display
     * @return a formatted list of tasks or an empty list message
     */
    public String showTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            return this.listEmptyMsg();
        }

        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Task task : tasks) {
            sb.append(i).append(". ").append(task).append("\n");
            i++;
        }

        return sb.toString();
    }
}
