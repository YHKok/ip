package grimm.parse;

import grimm.exception.GrimmException;

/**
 * Parses and validates user input commands for the Grimm task management system.
 * <p>
 * The Parser class interprets user commands, validate the task descriptions,
 * and ensure that the input follows the correct format.
 * Provides utility methods to process specific task types.
 * </p>
 */
public class Parser {
    private Command command;
    private String desc;


    /**
     * Default constructor for Parser.
     * Initializes an empty parser with no command and description.
     */
    public Parser() {}

    /**
     * Constructs a Parser with a given command and description.
     *
     * @param command The command to be parsed.
     * @param desc    The description for the task associated with the command.
     */
    public Parser(Command command, String desc) {
        this.command = command;
        if (desc == null) {
            this.desc = "";
        }
        this.desc = desc;
    }

    public Command getCommand() {
        return this.command;
    }

    public String getDesc() {
        return this.desc;
    }

    /**
     * Parses the user input into a Command and its description.
     * <p>
     * Splits the input into the command and the description. The command is validated and converted
     * into a Command object. If the input is empty, an "UNKNOWN" command is created.
     * </p>
     *
     * @param input The user input to parse.
     * @return A new Parser object with the parsed command and description.
     * @throws GrimmException if the input is invalid or empty.
     */
    public Parser parse(String input) {
        if (input == null || input.isEmpty()) {
            return new Parser(Command.UNKNOWN, "");
        }
        String[] words = input.split(" ", 2);
        String command = words[0].toLowerCase();
        String desc = "";
        if (words.length > 1) {
            desc = words[1];
        }

        return new Parser(Command.convert(command), desc);
    }

    /**
     * Validates that the task description is not empty.
     *
     * @throws GrimmException if the task description is empty.
     */
    public void checkValidName() throws GrimmException {
        if (this.desc.isEmpty()) {
            throw new GrimmException("A task with no name? Try again with a description.");
        }
    }

    /**
     * Validates the format for a deadline command.
     * <p>
     * Ensures that the description includes both the task and the due date.
     * </p>
     *
     * @param input The input array containing the description and due date.
     * @throws GrimmException if the format is invalid.
     */
    public void checkValidDeadlineFormat(String[] input) throws GrimmException {
        if (input.length < 2) {
            throw new GrimmException("A deadline with no end? Try again with: deadline <desc> /by <time>.");
        }
    }

    /**
     * Validates the format for an event command.
     * <p>
     * Ensures that the description includes both the event details and the start and end times.
     * </p>
     *
     * @param input The input array containing the event details and timings.
     * @throws GrimmException if the format is invalid.
     */
    public void checkValidEventFormat(String[] input) throws GrimmException {
        if (input.length < 2) {
            throw new GrimmException("An event with no start and end? Try again with: event <desc> /from <time> /to <time>.");
        }
    }

    /**
     * Attempts to parse the description as an integer.
     *
     * @return The integer parsed from the description.
     * @throws GrimmException if the description cannot be parsed as an integer.
     */
    public int parseInt() throws GrimmException {
        try {
            return Integer.parseInt(this.desc);
        } catch (NumberFormatException e) {
            throw new GrimmException("This is not a number I know. Try again.");
        }
    }

    /**
     * Parses the deadline description and returns the description and due date.
     *
     * @return A string array containing the task description and due date.
     * @throws GrimmException if the format is invalid.
     */
    public String[] parseDeadline() throws GrimmException {
        this.checkValidName();
        String[] descParts = this.desc.split(" /by ", 2);
        this.checkValidDeadlineFormat(descParts);
        return new String[] {descParts[0], descParts[1]};
    }

    /**
     * Parses the event description and returns the description, start date, and end date.
     *
     * @return A string array containing the event description, start time, and end time.
     * @throws GrimmException if the format is invalid.
     */
    public String[] parseEvent() throws GrimmException {
        this.checkValidName();
        String[] descParts = this.desc.split(" /from ", 2);
        this.checkValidEventFormat(descParts);
        String[] duration = descParts[1].split(" /to ", 2);
        this.checkValidEventFormat(duration);
        return new String[] {descParts[0], duration[0], duration[1]};
    }
}
