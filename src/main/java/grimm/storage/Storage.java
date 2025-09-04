package grimm.storage;

import grimm.model.Deadline;
import grimm.model.Event;
import grimm.model.Task;
import grimm.model.ToDo;
import grimm.exception.GrimmException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Handles reading and writing tasks from and to a file.
 * <p>
 * The Storage class provides methods to load tasks from a file and save tasks to a file.
 * Process tasks to and from their respective string representations.
 * It handles various types of tasks, including ToDo, Deadline, and Event.
 * </p>
 */
public class Storage {
    private String path;

    /**
     * Constructs a Storage object with the specified file path.
     *
     * @param path The path to the file where tasks will be stored or loaded from.
     */
    public Storage(String path) {
        this.path = path;
    }

    /**
     * Loads tasks from the file specified by the path.
     * <p>
     * This method reads each line from the file and parses the task information.
     * Returns a list of Task objects.
     * </p>
     *
     * @return A list of Task objects loaded from the file.
     * @throws FileNotFoundException if the file is not found.
     * @throws GrimmException if the file format is invalid or corrupted.
     */
    public List<Task> load() throws FileNotFoundException, GrimmException {
        File file = new File(this.path);
        List<Task> taskList = new ArrayList<>();
        Scanner scan = new Scanner(file);
        while (scan.hasNextLine()) {
            String data = scan.nextLine();
            Task task = addToList(data);
            if (task != null) {
                taskList.add(task);
            }
        }
        return taskList;
    }

    /**
     * Saves a list of tasks to the file specified by the path.
     * <p>
     * This method writes the tasks to the file in a format that can be reloaded later.
     * </p>
     *
     * @param tasks The list of tasks to save.
     * @throws IOException if an I/O error occurs while writing to the file.
     */
    public void save(List<Task> tasks) throws IOException {
        FileWriter writer = new FileWriter(this.path);
        for (Task t : tasks) {
            String input = saveToTxt(t);
            writer.write(input + System.lineSeparator());
        }
        writer.close();
    }

    /**
     * Processes a task string and converts it to a corresponding Task object.
     * <p>
     * This method interprets the data from the file and constructs a task based on
     * whether it is a ToDo, Deadline, or Event.
     * </p>
     *
     * @param input The task data string to be converted into a Task object.
     * @return A Task object created from the input string.
     * @throws GrimmException if the input format is invalid or corrupted.
     */
    private Task addToList(String input) throws GrimmException {
        String[] data = input.split(",", 4);
        if (data.length < 3) {
            throw new GrimmException("The Troupe senses a corrupted file. Try again with: <T/D/E>,<0/1>,<desc>,<date><time>");
        }
        String command = data[0].toUpperCase();
        boolean isMarked = data[1].equals("1");
        String desc = data[2];
        String dueBy = "";
        if (data.length > 3) {
            dueBy = data[3];
        }

        switch (command) {
            case "T" -> {
                return new ToDo(desc, isMarked);
            }
            case "D" -> {
                if (dueBy.isEmpty()) {
                    throw new GrimmException("The Troupe senses a corrupted file.Try again with: D,<0/1>,<desc>,<date>");
                }
                return new Deadline(desc, isMarked, dueBy);
            }
            case "E" -> {
                if (!dueBy.contains("-")) {
                    throw new GrimmException("The Troupe senses a corrupted file.Try again with: E,<0/1>,<desc>,<date>");
                }
                String[] dueByParts = dueBy.split("-");
                if (dueByParts.length < 2) {
                    throw new GrimmException("The Troupe senses a corrupted file.Try again with: E,<0/1>,<desc>,<datetime-datetime>");
                }
                return new Event(desc, isMarked, dueByParts[0], dueByParts[1]);
            }
            default -> throw new GrimmException("The Troupe senses an unknown command.....");
        }
    }

    /**
     * Converts a Task object to its corresponding string representation for saving to a file.
     * <p>
     * This method returns a string that represents the task, including its type, mark status,
     * name, and other details due date or event timing.
     * </p>
     *
     * @param task The Task object to be saved to the file.
     * @return A string representation of the task.
     */
    private String saveToTxt(Task task) {
        if (task == null) {
            return "";
        }

        String command = "";
        String isMarked = "0";
        if (task.getMark()) {
            isMarked = "1";
        }
        if (task instanceof ToDo) {
            command = "T";
            return command + "," + isMarked + "," + task.getName();
        } else if (task instanceof Deadline deadlines) {
            command = "D";
            return command + "," + isMarked + "," + task.getName() + "," + deadlines.getDueDate();
        } else if (task instanceof Event events) {
            command = "E";
            return command + "," + isMarked + "," + task.getName() + "," + events.getStartDate() + "-" + events.getEndDate();
        } else {
            return "";
        }
    }
}
