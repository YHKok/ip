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
     * AiAssisted
     * Used ChatGPT to help improve the JavaDocs for this method
     *
     * Loads all previously saved tasks from the default storage file
     * ({@code ./data/grimm.txt}) into the current {@link TaskList}.
     *
     * <p>If the file is missing or the data inside is invalid,
     * a corresponding error message is displayed through {@link Ui}.</p>
     *
     * @implNote This method is invoked in the constructor and
     *           is not intended to be called by user code.
     */
    private void loadTasksFromFile() {
        try {
            Storage storage = new Storage("./data/grimm.txt");
            for (Task t : storage.load()) {
                this.taskList.add(t);
            }
        } catch (FileNotFoundException e) {
            this.ui.invalidFile();
        } catch (GrimmException e) {
            this.ui.invalidGrimmMsg(e.getMessage());
        }
    }

    /**
     * AiAssisted
     * Used ChatGPT to help improve the JavaDocs for this method
     *
     * Performs a modification—mark, unmark, or delete—on a task
     * identified by the index supplied in the given {@link Parser}.
     *
     * @param parser  parser instance containing the user’s command details,
     *                including the task index to operate on.
     * @param command one of {@link Command#MARK}, {@link Command#UNMARK},
     *                or {@link Command#DELETE} specifying the operation.
     * @return UI-formatted feedback string describing the outcome
     *         (success or failure message).
     *
     * @throws AssertionError if the parsed index is non-positive or
     *         if {@code command} is not a recognised modification type.
     */
    private String modifyTasks(Parser parser, Command command) {
        try {
            int num = parser.parseInt();
            assert num > 0 : "Task index should exist in the list";
            this.taskList.checkExceedIndex(num);
            Task task;
            switch (command) {
                case MARK -> {
                    task = this.taskList.mark(num);
                    return this.ui.markMsg(task);
                }
                case UNMARK -> {
                    task = this.taskList.unmark(num);
                    return this.ui.unmarkMsg(task);
                }
                case DELETE -> {
                    task = this.taskList.delete(num);
                    return this.ui.deleteMsg(task, this.taskList);
                }
                default -> {
                    return this.ui.unknownCommand();
                }
            }
        } catch (GrimmException e) {
            return this.ui.invalidGrimmMsg(e.getMessage());
        }
    }

    /**
     * AiAssisted
     * Used ChatGPT to help improve the JavaDocs for this constructor
     *
     * Creates a new {@code Grimm} instance.
     *
     * <p>Initialises an empty {@link TaskList}, the user interface handler,
     * and a {@link Parser}, then attempts to populate the task list from
     * the default storage file by calling {@link #loadTasksFromFile()}.</p>
     */
    public Grimm() {
        this.taskList = new TaskList();
        this.ui = new Ui();
        this.parser = new Parser();
        loadTasksFromFile();
    }

    /**
     * AiAssisted
     * Used ChatGPT to help improve the JavaDocs for this constructor
     *
     * Produces the chatbot-style response for a single line of user input.
     *
     * <p>The input string is parsed into a {@link Command} and its
     * arguments. The corresponding action is executed—such as listing,
     * adding, updating, or deleting tasks—and the outcome is converted
     * into a displayable message via {@link Ui}.</p>
     *
     * <p>All internal exceptions are caught and presented as user-friendly
     * messages. No exceptions propagate to the caller.</p>
     *
     * @param input raw user input text.
     * @return a formatted message describing the result of the command.
     */
    public String getResponse(String input) {
        Parser parser = this.parser.parse(input);
        assert parser != null : "Parser should not be null";
        Command command = parser.getCommand();
        assert command != null : "Command should not be null";
        String desc = parser.getDesc();

        switch (command) {
            case BYE -> {
                try {
                    Storage storage = new Storage("./data/grimm.txt");
                    storage.save(this.taskList.getTaskList());
                } catch (IOException e) {
                    return this.ui.invalidFile();
                } finally {
                    return this.ui.bye();
                }
            }
            case LIST -> {
                return this.ui.showTasks(this.taskList.getTaskList());
            }
            case MARK -> {
                return modifyTasks(parser, command);
            }
            case UNMARK -> {
                return modifyTasks(parser, command);
            }
            case TODO -> {
                try {
                    parser.checkValidName();
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
                return modifyTasks(parser, command);
            }
            case FIND -> {
                List<Task> filteredTaskList = this.taskList.findTask(desc);
                if (filteredTaskList.isEmpty()) {
                    return this.ui.listEmptyMsg();
                } else {
                    return this.ui.showTasks(filteredTaskList);
                }
            }
            case UPDATE -> {
                try {
                    String[] updateParts = parser.parseUpdate();
                    int num = Integer.parseInt(updateParts[0]);
                    assert num > 0 : "Task index should exist in the list";
                    this.taskList.checkExceedIndex(num);
                    String taskType = updateParts[1];
                    String taskDesc = updateParts[2];
                    Task task;
                    switch (taskType) {
                        case "todo" -> {
                            task = new ToDo(taskDesc);
                        }
                        case "deadline" -> {
                            String[] deadlineParts = taskDesc.split(" /by ", 2);
                            if (deadlineParts.length < 2) {
                                return this.ui.invalidDeadline();
                            }
                            task = new Deadline(deadlineParts[0], deadlineParts[1]);
                        }
                        case "event" -> {
                            String[] eventPartsFrom = taskDesc.split(" /from ", 2);
                            if (eventPartsFrom.length < 2) {
                                return this.ui.invalidEvent();
                            }
                            String[] eventParts = eventPartsFrom[1].split(" /to ", 2);
                            if (eventParts.length < 2) {
                                return this.ui.invalidEvent();
                            }
                            task = new Event(eventPartsFrom[0], eventParts[0], eventParts[1]);
                        }
                        default -> {
                            return this.ui.unknownCommand();
                        }
                    }

                    Task updatedTask = this.taskList.update(num, task);
                    return this.ui.updateMsg(updatedTask);

                } catch (GrimmException e) {
                    return this.ui.invalidGrimmMsg(e.getMessage());
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

}
