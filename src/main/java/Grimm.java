import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.DateTimeException;
import java.util.*;

public class Grimm {
    private final TaskList taskList;
    private final Ui ui;
    private final Parser parser;

    private Grimm() {
        this.taskList = new TaskList();
        this.ui = new Ui();
        this.parser = new Parser();
    }

    public static void main(String[] args) {
        Grimm grimm = new Grimm();
        try {
            Storage storage = new Storage("./data/grimm.txt");
            for (Task t : storage.load()) {
                grimm.taskList.add(t);
            }
        } catch (FileNotFoundException e) {
            grimm.ui.invalidFile();
        } catch (GrimmException e) {
            grimm.ui.invalidGrimmMsg(e.getMessage());
        }

        grimm.ui.welcome();
        Scanner scan = new Scanner(System.in);
        while (true) {
            String input = scan.nextLine();
            Parser parser = grimm.parser.parse(input);
            String command = parser.getCommand().getCommand();
            String desc = parser.getDesc();

            switch (command) {
                case Command.BYE -> {
                    try {
                        Storage storage = new Storage("./data/grimm.txt");
                        storage.save(grimm.taskList.getTaskList());
                    } catch (IOException e) {
                        grimm.ui.invalidFile();
                    } finally {
                        grimm.ui.bye();
                    }

                    return;
                }
                case Command.LIST -> grimm.ui.showTasks(grimm.taskList.getTaskList());
                case Command.MARK -> {
                    try {
                        int num = parser.parseInt();
                        grimm.taskList.exceedIndex(num);
                        Task task = grimm.taskList.mark(num);
                        grimm.ui.markMsg(task);
                    } catch (GrimmException e) {
                        grimm.ui.invalidGrimmMsg(e.getMessage());
                    }
                }
                case Command.UNMARK -> {
                    try {
                        int num = parser.parseInt();
                        grimm.taskList.exceedIndex(num);
                        Task task = grimm.taskList.unmark(num);
                        grimm.ui.unmarkMsg(task);
                    } catch (GrimmException e) {
                        grimm.ui.invalidGrimmMsg(e.getMessage());
                    }
                }
                case Command.TODO -> {
                    try {
                        parser.validName();
                        ToDo todo = new ToDo(desc);
                        grimm.taskList.add(todo);
                        grimm.ui.addMsg(todo, grimm.taskList.getSize());
                    } catch (GrimmException e) {
                        grimm.ui.invalidGrimmMsg(e.getMessage());
                    }
                }

                case Command.DEADLINE -> {
                    try {
                        String[] descParts = parser.parseDeadline();
                        Deadline deadline = new Deadline(descParts[0], descParts[1]);
                        grimm.taskList.add(deadline);
                        grimm.ui.addMsg(deadline, grimm.taskList.getSize());
                    } catch (GrimmException e) {
                        grimm.ui.invalidDeadline();
                    } catch (DateTimeException e) {
                        grimm.ui.invalidDate();
                    }
                }
                case Command.EVENT -> {
                    try {
                        String[] descParts = parser.parseEvent();
                        Event event = new Event(descParts[0], descParts[1], descParts[2]);
                        grimm.taskList.add(event);
                        grimm.ui.addMsg(event, grimm.taskList.getSize());
                    } catch (GrimmException e) {
                        grimm.ui.invalidEvent();
                    } catch (DateTimeException e) {
                        grimm.ui.invalidDatetime();
                    }
                }
                case Command.DELETE -> {
                    try {
                        int num = parser.parseInt();
                        grimm.taskList.exceedIndex(num);
                        Task task = grimm.taskList.delete(num);
                        grimm.ui.deleteMsg(task, grimm.taskList);
                    } catch (GrimmException e) {
                        grimm.ui.invalidGrimmMsg(e.getMessage());
                    }
                }
                default -> grimm.ui.unknownCommand();
            }
        }
    }
}
