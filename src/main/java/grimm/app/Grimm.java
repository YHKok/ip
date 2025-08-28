package grimm.app;

import grimm.exception.GrimmException;
import grimm.model.*;
import grimm.parse.Command;
import grimm.parse.Parser;
import grimm.storage.Storage;
import grimm.ui.Ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.DateTimeException;
import java.util.*;

/**
 * The main class that runs the Grimm application.
 * <p>
 * The Grimm class is responsible for handling the user input, processing commands, and managing tasks.
 * It interacts with various components such as the TaskList, Ui, and Storage to perform actions on tasks,
 * such as adding, marking, unmarking, deleting, and displaying tasks.
 * </p>
 */
public class Grimm {
    private final TaskList taskList;
    private final Ui ui;
    private final Parser parser;

    /**
     * Constructs a Grimm object and initializes the task list, UI, and parser.
     */
    private Grimm() {
        this.taskList = new TaskList();
        this.ui = new Ui();
        this.parser = new Parser();
    }

    /**
     * The main method that runs the Grimm application.
     * <p>
     * This method initializes the application, loads tasks from a storage file,
     * and listens for user commands to interact with the task list. It also handles
     * saving the tasks back to the file upon exiting the application.
     * </p>
     *
     * @param args The command-line arguments passed to the application (not used here).
     */
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
            Command command = parser.getCommand();
            String desc = parser.getDesc();

            switch (command) {
                case BYE -> {
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
                case LIST -> grimm.ui.showTasks(grimm.taskList.getTaskList());
                case MARK -> {
                    try {
                        int num = parser.parseInt();
                        grimm.taskList.exceedIndex(num);
                        Task task = grimm.taskList.mark(num);
                        grimm.ui.markMsg(task);
                    } catch (GrimmException e) {
                        grimm.ui.invalidGrimmMsg(e.getMessage());
                    }
                }
                case UNMARK -> {
                    try {
                        int num = parser.parseInt();
                        grimm.taskList.exceedIndex(num);
                        Task task = grimm.taskList.unmark(num);
                        grimm.ui.unmarkMsg(task);
                    } catch (GrimmException e) {
                        grimm.ui.invalidGrimmMsg(e.getMessage());
                    }
                }
                case TODO -> {
                    try {
                        parser.validName();
                        ToDo todo = new ToDo(desc);
                        grimm.taskList.add(todo);
                        grimm.ui.addMsg(todo, grimm.taskList.getSize());
                    } catch (GrimmException e) {
                        grimm.ui.invalidGrimmMsg(e.getMessage());
                    }
                }

                case DEADLINE -> {
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
                case EVENT -> {
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
                case DELETE -> {
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
