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

public class Storage {
    private String path;

    public Storage(String path) {
        this.path = path;
    }

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

    public void save(List<Task> tasks) throws IOException {
        FileWriter writer = new FileWriter(this.path);
        for (Task t : tasks) {
            String input = saveToTxt(t);
            writer.write(input + System.lineSeparator());
        }
        writer.close();
    }

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
            default -> {
                throw new GrimmException("The Troupe senses an unknown command.....");
            }
        }
    }

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
