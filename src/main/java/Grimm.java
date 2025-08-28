import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.DateTimeException;
import java.util.*;

public class Grimm {
    private final TaskList taskList;
    private final Ui ui;

    private Grimm() {
        this.taskList = new TaskList();
        this.ui = new Ui();
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
            grimm.ui.invalidFile();
        } catch (GrimmException e) {
            grimm.ui.invalidGrimmMsg(e.getMessage());
        }

        grimm.ui.welcome();
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
                        grimm.ui.invalidFile();
                    } finally {
                        grimm.ui.bye();
                    }

                    return;
                }
                case "list" -> grimm.ui.showTasks(grimm.taskList.getTaskList());
                case "mark" -> {
                    try {
                        int num = Integer.parseInt(desc.trim());
                        grimm.taskList.exceedIndex(num);
                        Task task = grimm.taskList.mark(num);
                        grimm.ui.markMsg(task);
                    } catch (NumberFormatException e) {
                        grimm.ui.invalidNumber();
                    } catch (GrimmException e) {
                        grimm.ui.invalidGrimmMsg(e.getMessage());
                    }
                }
                case "unmark" -> {
                    try {
                        int num = Integer.parseInt(desc.trim());
                        grimm.taskList.exceedIndex(num);
                        Task task = grimm.taskList.unmark(num);
                        grimm.ui.unmarkMsg(task);
                    } catch (NumberFormatException e) {
                        grimm.ui.invalidNumber();
                    } catch (GrimmException e) {
                        grimm.ui.invalidGrimmMsg(e.getMessage());
                    }
                }
                case "todo" -> {
                    try {
                        grimm.validName(desc);
                        ToDo todo = new ToDo(desc);
                        grimm.taskList.add(todo);
                        grimm.ui.addMsg(todo, grimm.taskList.getSize());
                    } catch (GrimmException e) {
                        grimm.ui.invalidGrimmMsg(e.getMessage());
                    }
                }

                case "deadline" -> {
                    try {
                        grimm.validName(desc);
                        String[] descParts = desc.split(" /by ", 2);
                        grimm.validFormat(descParts);
                        Deadline deadline = new Deadline(descParts[0], descParts[1]);
                        grimm.taskList.add(deadline);
                        grimm.ui.addMsg(deadline, grimm.taskList.getSize());
                    } catch (GrimmException e) {
                        grimm.ui.invalidDeadline();
                    } catch (DateTimeException e) {
                        grimm.ui.invalidDate();
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
                        grimm.ui.addMsg(event, grimm.taskList.getSize());
                    } catch (GrimmException e) {
                        grimm.ui.invalidEvent();
                    } catch (DateTimeException e) {
                        grimm.ui.invalidDatetime();
                    }
                }
                case "delete" -> {
                    try {
                        int num = Integer.parseInt(desc.trim());
                        grimm.taskList.exceedIndex(num);
                        Task task = grimm.taskList.delete(num);
                        grimm.ui.deleteMsg(task, num);
                    } catch (NumberFormatException e) {
                        grimm.ui.invalidNumber();
                    } catch (GrimmException e) {
                        grimm.ui.invalidGrimmMsg(e.getMessage());
                    }
                }
                default -> grimm.ui.unknownCommand();
            }
        }
    }
}
