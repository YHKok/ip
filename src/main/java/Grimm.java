import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Grimm {
    private final List<Task> taskList;

    private Grimm() {
        this.taskList = new ArrayList<>();
    }

    private void add(Task task) {
        this.taskList.add(task);
    }

    private void mark(int num) {
        this.taskList.get(num - 1).mark();
        System.out.println("Nice! I've marked this task as done: \n" + this.getTask(num));
    }

    private void unmark(int num) {
        this.taskList.get(num - 1).unmark();
        System.out.println("OK, I've marked this task as not done yet: \n" + this.getTask(num));
    }

    private void delete(int num) {
        Task task = this.taskList.get(num - 1);
        this.taskList.remove(num - 1);
        System.out.println("Noted. I've removed this task:\n" + task + "\nNow you have " + this.getSize() + " tasks in the list.");

    }

    private String getTask(int num) {
        return this.taskList.get(num - 1).toString();
    }

    private int getSize() {
        return this.taskList.size();
    }

    private void showTasks() {
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

    private void exceedIndex(int num) throws GrimmException {
        if (this.taskList.isEmpty()) {
            throw new GrimmException("The stage is empty. Try again.");
        }
        if (num < 1 || num > this.getSize()) {
            throw new GrimmException("The stage holds fewer players than you summon. Try again.");
        }
    }

    private void validName(String input) throws GrimmException {
        if (input.isEmpty()) {
            throw new GrimmException("A task with no name? Try again with a description.");
        }
    }

    private void validFormat(String[] input) throws GrimmException {
        if (input.length < 2) {
            throw new GrimmException("A deadline with no end? Try again with: deadline <desc> /by <time>.");
        }
    }

    public static void main(String[] args) {
        Grimm grimm = new Grimm();
        try {
            Storage storage = new Storage("./data/grimm.txt");
            for (Task t : storage.load()) {
                grimm.add(t);
            }
        } catch (FileNotFoundException e) {
            System.out.println("This is not a file I know. Try again.");
        } catch (GrimmException e) {
            System.out.println(e.getMessage());
        }

        String logo = "Hello, I'm Grimm\nWhat can I do for you?\n";
        System.out.println(logo);
        Scanner scan = new Scanner(System.in);
        while (true) {
            String input = scan.nextLine();
            String[] words = input.split(" ", 2);
            String command = words[0].toLowerCase();
            String desc = "";
            if (words.length > 1) {
                desc = words[1];
            }

            switch (command) {
                case "bye" -> {
                    try {
                        Storage storage = new Storage("./data/grimm.txt");
                        storage.save(grimm.taskList);
                    } catch (IOException e) {
                        System.out.println("This is not a file I know. Try again.");
                    }
                    System.out.println("Bye. Hope to see you again soon!");
                    return;
                }
                case "list" -> grimm.showTasks();
                case "mark" -> {
                    try {
                        int num = Integer.parseInt(desc.trim());
                        grimm.exceedIndex(num);
                        grimm.mark(num);
                    } catch (NumberFormatException e) {
                        System.out.println("This is not a number I know. Try again.");
                    } catch (GrimmException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case "unmark" -> {
                    try {
                        int num = Integer.parseInt(desc.trim());
                        grimm.exceedIndex(num);
                        grimm.unmark(num);
                    } catch (NumberFormatException e) {
                        System.out.println("This is not a number I know. Try again.");
                    } catch (GrimmException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case "todo" -> {
                    try {
                        grimm.validName(desc);
                        ToDo todo = new ToDo(desc);
                        grimm.add(todo);
                        System.out.println("Got it. I've added this task:\n" + todo+ "\nNow you have " + grimm.getSize() + " tasks in the list.");
                    } catch (GrimmException e) {
                        System.out.println(e.getMessage());
                    }                }
                case "deadline" -> {
                    try {
                        grimm.validName(desc);
                        String[] descParts = desc.split(" /by ", 2);
                        grimm.validFormat(descParts);
                        Deadline deadline = new Deadline(descParts[0], descParts[1]);
                        grimm.add(deadline);
                        System.out.println("Got it. I've added this task:\n" + deadline + "\nNow you have " + grimm.getSize() + " tasks in the list.");
                    } catch (GrimmException e) {
                        System.out.println(e.getMessage());
                    } catch (DateTimeException e) {
                        System.err.println("The Troupe does not understand this date. Try again with: MM/dd/yyyy format");
                    }
                }
                case "event" -> {
                    try {
                        grimm.validName(desc);
                        String[] descParts = desc.split(" /from ", 2);
                        grimm.validFormat(descParts);
                        String[] duration = descParts[1].split(" /to ", 2);
                        grimm.validFormat(duration);
                        Event event = new Event(descParts[0], duration[0], duration[1]);
                        grimm.add(event);
                        System.out.println("Got it. I've added this task:\n" + event + "\nNow you have " + grimm.getSize() + " tasks in the list.");
                    } catch (GrimmException e) {
                        System.out.println("An event cannot begin and end without a time. Try again with: event <desc> /from <start> /to <end>.");
                    }
                }
                case "delete" -> {
                    try {
                        int num = Integer.parseInt(desc.trim());
                        grimm.exceedIndex(num);
                        grimm.delete(num);
                    } catch (NumberFormatException e) {
                        System.out.println("This is not a number I know. Try again.");
                    } catch (GrimmException e) {
                        System.out.println(e.getMessage());
                    }
                }
                default -> System.out.println("The stage is not prepared for such words, little one. Try a valid command.");
            }
        }
    }
}
