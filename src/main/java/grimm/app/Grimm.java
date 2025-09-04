package grimm.app;

import grimm.exception.GrimmException;
import grimm.model.Deadline;
import grimm.model.Event;
import grimm.model.Task;
import grimm.model.TaskList;
import grimm.model.ToDo;
import grimm.parse.Command;
import grimm.parse.Parser;
import grimm.storage.Storage;
import grimm.ui.DialogBox;
import grimm.ui.Ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.DateTimeException;
import java.util.List;


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
    public Grimm() {
        this.taskList = new TaskList();
        this.ui = new Ui();
        this.parser = new Parser();
        try {
            Storage storage = new Storage("./data/grimm.txt");
            for (Task t : storage.load()) {
                this.taskList.add(t);
            }
        } catch (FileNotFoundException e) {
//            this.ui.invalidFile();
        } catch (GrimmException e) {
//            this.ui.invalidGrimmMsg(e.getMessage());
        }
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        Parser parser = this.parser.parse(input);
        Command command = parser.getCommand();
        String desc = parser.getDesc();

        switch (command) {
            case BYE -> {
                try {
                    Storage storage = new Storage("./data/grimm.txt");
                    storage.save(this.taskList.getTaskList());
                } catch (IOException e) {
                    return this.ui.invalidFile();
                }

                return this.ui.bye();
            }
            case LIST -> {
                return this.ui.showTasks(this.taskList.getTaskList());
            }
            case MARK -> {
                try {
                    int num = parser.parseInt();
                    this.taskList.exceedIndex(num);
                    Task task = this.taskList.mark(num);
                    return this.ui.markMsg(task);
                } catch (GrimmException e) {
                    return this.ui.invalidGrimmMsg(e.getMessage());
                }
            }
            case UNMARK -> {
                try {
                    int num = parser.parseInt();
                    this.taskList.exceedIndex(num);
                    Task task = this.taskList.unmark(num);
                    return this.ui.unmarkMsg(task);
                } catch (GrimmException e) {
                    return this.ui.invalidGrimmMsg(e.getMessage());
                }
            }
            case TODO -> {
                try {
                    parser.validName();
                    ToDo todo = new ToDo(desc);
                    this.taskList.add(todo);
                    return this.ui.addMsg(todo, this.taskList.getSize());
                } catch (GrimmException e) {
                    return this.ui.invalidGrimmMsg(e.getMessage());
                }
            }

            case DEADLINE -> {
                try {
                    String[] descParts = parser.parseDeadline();
                    Deadline deadline = new Deadline(descParts[0], descParts[1]);
                    this.taskList.add(deadline);
                    return this.ui.addMsg(deadline, this.taskList.getSize());
                } catch (GrimmException e) {
                    return this.ui.invalidDeadline();
                } catch (DateTimeException e) {
                    return this.ui.invalidDate();
                }
            }
            case EVENT -> {
                try {
                    String[] descParts = parser.parseEvent();
                    Event event = new Event(descParts[0], descParts[1], descParts[2]);
                    this.taskList.add(event);
                    return this.ui.addMsg(event, this.taskList.getSize());
                } catch (GrimmException e) {
                    return this.ui.invalidEvent();
                } catch (DateTimeException e) {
                    return this.ui.invalidDatetime();
                }
            }
            case DELETE -> {
                try {
                    int num = parser.parseInt();
                    this.taskList.exceedIndex(num);
                    Task task = this.taskList.delete(num);
                    return this.ui.deleteMsg(task, this.taskList);
                } catch (GrimmException e) {
                    return this.ui.invalidGrimmMsg(e.getMessage());
                }
            }
            case FIND -> {
                List<Task> filteredTaskList = this.taskList.findTask(desc);
                if (filteredTaskList.isEmpty()) {
                    return this.ui.listEmptyMsg();
                } else {
                    return this.ui.showTasks(filteredTaskList);
                }
            }
            default -> {
                return this.ui.unknownCommand();
            }
        }
    }

    /**
     * The main method that runs the Grimm application.
     * <p>
     * This method initializes the application, loads tasks from a storage file,
     * and listens for user commands to interact with the task list. It also handles
     * saving the tasks back to the file upon exiting the application.
     * </p>
     *
     * @param args The command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        System.out.println("Silksong is finally here");
    }

    public String getWelcomeMsg() {
        return this.ui.welcome();
    }
}
