package grimm.parse;

/**
 * Represents a command in the Grimm application.
 * <p>
 * The Command enum encapsulates various commands such as "bye", "list", "mark",
 * "unmark", "todo", "deadline", "event", and "delete". Each command is represented
 * by a string value and the enum provides functionality to validate commands and
 * convert input strings into Command objects.
 * </p>
 */
public enum Command {
    BYE("bye"),
    LIST("list"),
    MARK("mark"),
    UNMARK("unmark"),
    TODO ("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    DELETE("delete"),
    UNKNOWN("");
    private String command;

    /**
     * Constructs a Command object with the specified command string.
     * <p>
     * If the provided command is null, it will be initialized to an unknown command.
     * </p>
     *
     * @param command The command string to initialize the Command object with.
     */
    Command(String command) {
        this.command = command;
    }

    public String getCommand() {
        return this.command;
    }

    /**
     * Converts a string input into a corresponding Command enum.
     * <p>
     * This method matches the input string with one of the predefined
     * commands in the enum. If no match is found, it returns UNKNOWN.
     * </p>
     *
     * @param input The string input to be converted into a Command.
     * @return The corresponding Command enum, or UNKNOWN if no match is found.
     */
    public static Command convert(String input) {
        for (Command command : Command.values()) {
            if (command.getCommand().equalsIgnoreCase(input)) {
                return command;
            }
        }
        return UNKNOWN;
    }
}
