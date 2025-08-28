public class Command {
    private static final String BYE = "bye";
    private static final String LIST = "list";
    private static final String MARK = "mark";
    private static final String UNMARK = "unmark";
    private static final String TODO = "todo";
    private static final String DEADLINE = "deadline";
    private static final String EVENT = "event";
    private static final String DELETE = "delete";

    public boolean isValid(String command) {
        return command.equals(BYE) || command.equals(LIST) || command.equals(MARK) || command.equals(UNMARK) || command.equals(TODO) || command.equals(DEADLINE) || command.equals(EVENT) || command.equals(DELETE);
    }

}
