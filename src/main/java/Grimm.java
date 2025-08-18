import java.util.ArrayList;
import java.util.Scanner;

public class Grimm {
    private ArrayList<String> listArr = new ArrayList<String>(100);

    public void add(String input) {
        listArr.add(input);
    }

    public ArrayList<String> getList() {
        return this.listArr;
    }

    public void showList(ArrayList<String> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No items in the list");
        }

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(i + 1 + ". " + tasks.get(i));
        }
    }

    public static void main(String[] args) {
        Grimm grimm = new Grimm();
        String logo = "Hello, I'm Grimm\nWhat can I do for you?\n";
        System.out.println(logo);
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        while (!input.toLowerCase().equals("bye")) {
            if (input.toLowerCase().equals("list")) {
                grimm.showList(grimm.listArr);
            } else {
                grimm.add(input);
                System.out.println("added: " + input);
            }

            input = scan.nextLine();
        }

        System.out.println("Bye. Hope to see you again soon!");
    }
}
