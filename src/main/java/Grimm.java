import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Grimm {
    private final List<Tasks> tasksList;

    public Grimm() {
        this.tasksList = new ArrayList<>();
    }

    private void add(Tasks task) {
        this.tasksList.add(task);
    }

    private void mark(int num) {
        this.tasksList.get(num - 1).mark();
    }

    private void unmark(int num) {
        this.tasksList.get(num - 1).unmark();
    }

    private String getTask(int num) {
        return this.tasksList.get(num - 1).toString();
    }

    private int getSize() {
        return this.tasksList.size();
    }

    private void showTasks() {
        if (this.tasksList.isEmpty()) {
            System.out.println("No items in the list");
            return;
        }

        int i = 1;
        for (Tasks task : this.tasksList) {
            System.out.println(i + ". " + task);
            i++;
        }
    }

    private void exceedIndex(int num) throws GrimmException {
        if (this.tasksList.isEmpty()) {
            throw new GrimmException("The stage is empty. Try again.");
        }
        if (num < 1 || num > this.getSize()) {
            throw new GrimmException("The stage holds fewer players than you summon. Try again.");
        }
    }

    public static void main(String[] args) {
        Grimm grimm = new Grimm();
        String logo = "Hello, I'm Grimm\nWhat can I do for you?\n";
        System.out.println(logo);
        Scanner scan = new Scanner(System.in);
        while (true) {
            String input = scan.nextLine();
            String[] words = input.toLowerCase().split(" ", 2);
            String command = words[0];
            String desc = "";
            if (words.length > 1) {
                desc = words[1];
            }

            switch (command) {
                case "bye" -> {
                    System.out.println("Bye. Hope to see you again soon!");
                    return;
                }
                case "list" -> {
                    grimm.showTasks();
                }
                case "mark" -> {
                    int num = Integer.parseInt(desc.trim());
                    grimm.mark(num);
                    System.out.println("Nice! I've marked this task as done: \n" + grimm.getTask(num));
                }
                case "unmark" -> {
                    int num = Integer.parseInt(desc.trim());
                    grimm.unmark(num);
                    System.out.println("OK, I've marked this task as not done yet: \n" + grimm.getTask(num));
                }
                case "todo" -> {
                    ToDo todo = new ToDo(words[1]);
                    grimm.add(todo);
                    System.out.println("Got it. I've added this task:\n" + todo + "\nNow you have " + grimm.getSize() + " tasks in the list.");
                }
                case "deadline" -> {
                    String[] descParts = desc.split(" /by ", 2);
                    Deadlines deadline = new Deadlines(descParts[0], descParts[1]);
                    grimm.add(deadline);
                    System.out.println("Got it. I've added this task:\n" + deadline + "\nNow you have " + grimm.getSize() + " tasks in the list.");
                }
                case "event" -> {
                    String[] descParts = desc.split(" /from ", 2);
                    String[] duration = descParts[1].split(" /to ", 2);
                    Events event = new Events(descParts[0], duration[0], duration[1]);
                    grimm.add(event);
                    System.out.println("Got it. I've added this task:\n" + event + "\nNow you have " + grimm.getSize() + " tasks in the list.");
                }
                default -> {
                    System.out.println("The stage is not prepared for such words, little one.");
                }

            }
        }
    }
}
