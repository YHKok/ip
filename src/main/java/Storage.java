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

    public List<Task> load() throws FileNotFoundException {
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

    private Task addToList(String input) {
        String[] data = input.split(",", 4);
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
                return new Deadline(desc, isMarked, dueBy);
            }
            case "E" -> {
                int split = dueBy.lastIndexOf("-");
                String from = dueBy.substring(0, split);
                String to = dueBy.substring(split + 1);
                return new Event(desc, isMarked, from, to);
            }
            default -> {
                return null;
            }
        }
    }

    public void save(List<Task> tasks) throws IOException {
        FileWriter writer = new FileWriter(this.path);
        for (Task t : tasks) {
            String input = saveToTxt(t);
            writer.write(input + System.lineSeparator());
        }
        writer.close();
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
            return command + "," + isMarked + "," + task.getName() + deadlines.getDueDate();
        } else if (task instanceof Event events) {
            command = "E";
            return command + "," + isMarked + "," + task.getName() + events.getStartDate() + "-" + events.getEndDate();
        } else {
            return "";
        }
    }
}
