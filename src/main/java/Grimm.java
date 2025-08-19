import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Grimm {
    private List<Tasks> tasksList;

    public Grimm() {
        this.tasksList = new ArrayList<>();
    }

    public void add(Tasks input) {
        this.tasksList.add(input);
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

    public void showTasks() {
        if (this.tasksList == null) {
            System.out.println("No items in the list");
            return;
        }

        for (int i = 0; i < this.tasksList.size(); i++) {
            System.out.println(i + 1 + ". " + this.tasksList.get(i).toString());
        }
    }

    public static void main(String[] args) {
        Grimm grimm = new Grimm();
        String logo = "Hello, I'm Grimm\nWhat can I do for you?\n";
        System.out.println(logo);
        Scanner scan = new Scanner(System.in);
        Tasks input = new Tasks(scan.nextLine());
        while (!input.getName().equals("bye")) {
            if (input.getName().equals("list")) {
                grimm.showTasks();
            } else if (input.getName().contains("unmark")) {
                String[] words = input.getName().split(" ");
                int num = Integer.parseInt(words[1]);
                grimm.unmark(num);
                System.out.println("OK, I've marked this task as not done yet: \n" + grimm.getTask(num));
            } else if (input.getName().contains("mark")) {
                String[] words = input.getName().split(" ");
                int num = Integer.parseInt(words[1]);
                grimm.mark(num);
                System.out.println("Nice! I've marked this task as done: \n" + grimm.getTask(num));
            } else {
                grimm.add(input);
                System.out.println("added: " + input);
            }

            input = new Tasks(scan.nextLine());
        }

        System.out.println("Bye. Hope to see you again soon!");
    }
}
