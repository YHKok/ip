import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Grimm {
    private List<Tasks> tasksList;

    public Grimm() {
        this.tasksList = new ArrayList<>();
    }

    public void add(Tasks task) {
        this.tasksList.add(task);
    }

    public void mark(int num) {
        this.tasksList.get(num - 1).mark();
    }

    public void unmark(int num) {
        this.tasksList.get(num - 1).unmark();
    }

    public String getTask(int num) {
        return this.tasksList.get(num - 1).toString();
    }

    public int getSize() {
        return this.tasksList.size();
    }

    public void showTasks() {
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

    public static void main(String[] args) {
        Grimm grimm = new Grimm();
        String logo = "Hello, I'm Grimm\nWhat can I do for you?\n";
        System.out.println(logo);
        Scanner scan = new Scanner(System.in);
        while (true) {
            String input = scan.nextLine();
            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }

            if (input.equals("list")) {
                grimm.showTasks();
                continue;
            }

            if (input.startsWith("unmark")) {
                String[] words = input.split(" ");
                int num = Integer.parseInt(words[1]);
                grimm.unmark(num);
                System.out.println("OK, I've marked this task as not done yet: \n" + grimm.getTask(num));
            } else if (input.startsWith("mark")) {
                String[] words = input.split(" ");
                int num = Integer.parseInt(words[1]);
                grimm.mark(num);
                System.out.println("Nice! I've marked this task as done: \n" + grimm.getTask(num));
            } else if (input.startsWith("todo")) {
                String[] words = input.split(" ", 2);
                ToDo todo = new ToDo(words[1]);
                grimm.add(todo);
                System.out.println("Got it. I've added this task:\n" + todo.toString() + "\nNow you have " + grimm.getSize() + " tasks in the list.");
            } else if (input.startsWith("deadline")) {
                String[] words = input.substring("deadline".length()).trim().split(" /by ");
                Deadlines deadline = new Deadlines(words[0], words[1]);
                grimm.add(deadline);
                System.out.println("Got it. I've added this task:\n" + deadline.toString() + "\nNow you have " + grimm.getSize() + " tasks in the list.");
            } else if (input.startsWith("event")) {
                String[] words = input.substring("event".length()).trim().split(" /from ");
                String[] duration = words[1].split(" /to ");
                Events event = new Events(words[0], duration[0], duration[1]);
                grimm.add(event);
                System.out.println("Got it. I've added this task:\n" + event.toString() + "\nNow you have " + grimm.getSize() + " tasks in the list.");
            } else {
                grimm.add(new Tasks(input));
                System.out.println("added: " + input);
            }
        }
    }
}
