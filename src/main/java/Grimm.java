import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.DateTimeException;
import java.util.*;

public class Grimm {
    private final TaskList taskList;

    private Grimm() {
        this.taskList = new TaskList();
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
                grimm.taskList.add(t);
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
                        storage.save(grimm.taskList.getTaskList());
                    } catch (IOException e) {
                        System.out.println("This is not a file I know. Try again.");
                    }
                    System.out.println("Bye. Hope to see you again soon!");
                    return;
                }
                case "list" -> grimm.taskList.showTasks();
                case "mark" -> {
                    try {
                        int num = Integer.parseInt(desc.trim());
                        grimm.taskList.exceedIndex(num);
                        grimm.taskList.mark(num);
                    } catch (NumberFormatException e) {
                        System.out.println("This is not a number I know. Try again.");
                    } catch (GrimmException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case "unmark" -> {
                    try {
                        int num = Integer.parseInt(desc.trim());
                        grimm.taskList.exceedIndex(num);
                        grimm.taskList.unmark(num);
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
                        grimm.taskList.add(todo);
                    } catch (GrimmException e) {
                        System.out.println(e.getMessage());
                    }                }
                case "deadline" -> {
                    try {
                        grimm.validName(desc);
                        String[] descParts = desc.split(" /by ", 2);
                        grimm.validFormat(descParts);
                        Deadline deadline = new Deadline(descParts[0], descParts[1]);
                        grimm.taskList.add(deadline);
                        System.out.println("Got it. I've added this task:\n" + deadline + "\nNow you have " + grimm.taskList.getSize() + " tasks in the list.");
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
                        grimm.taskList.add(event);
                        System.out.println("Got it. I've added this task:\n" + event + "\nNow you have " + grimm.taskList.getSize() + " tasks in the list.");
                    } catch (GrimmException e) {
                        System.out.println("An event cannot begin and end without a time. Try again with: event <desc> /from <start> /to <end>.");
                    }
                }
                case "delete" -> {
                    try {
                        int num = Integer.parseInt(desc.trim());
                        grimm.taskList.exceedIndex(num);
                        grimm.taskList.delete(num);
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
