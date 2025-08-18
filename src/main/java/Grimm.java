import java.util.Scanner;

public class Grimm {
    public static void main(String[] args) {
        String logo = "Hello, I'm Grimm\nWhat can I do for you?\n";
        System.out.println(logo);
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        while (!input.toLowerCase().equals("bye")) {
            if (input.toLowerCase().equals("dream"))
                System.out.println("The child......");
            else if (input.toLowerCase().equals("lore"))
                System.out.println("The ritual......");
            else
                System.out.println(input);
            input = scan.nextLine();
        }

        System.out.println("Bye. Hope to see you again soon!");
    }
}
