public class Command {
    public static final String BYE = "bye";
    public static final String LIST = "list";
    public static final String MARK = "mark";
    public static final String UNMARK = "unmark";
    public static final String TODO = "todo";
    public static final String DEADLINE = "deadline";
    public static final String EVENT = "event";
    public static final String DELETE = "delete";
    public static final String UNKNOWN = "";
    private String command;

    public Command(String command) {
        if (command == null) {
            this.command = UNKNOWN;
        }
        this.command = command;
    }

    public String getCommand() {
        return this.command;
    }

    public boolean isValid(String command) {
        return command.equals(BYE) || command.equals(LIST) || command.equals(MARK) || command.equals(UNMARK) || command.equals(TODO) || command.equals(DEADLINE) || command.equals(EVENT) || command.equals(DELETE);
    }

}
