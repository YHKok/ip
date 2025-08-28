package grimm.parse;

import grimm.exception.GrimmException;

public class Parser {
    private Command command;
    private String desc;

    public Parser() {}

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

    public Parser parse(String input) {
        if (input == null || input.isEmpty()) {
            return new Parser(new Command(Command.UNKNOWN), "");
        }
        String[] words = input.split(" ", 2);
        String command = words[0].toLowerCase();
        String desc = "";
        if (words.length > 1) {
            desc = words[1];
        }

        return new Parser(new Command(command), desc);
    }

    public void validName() throws GrimmException {
        if (this.desc.isEmpty()) {
            throw new GrimmException("A task with no name? Try again with a description.");
        }
    }

    public void validDeadlineFormat(String[] input) throws GrimmException {
        if (input.length < 2) {
            throw new GrimmException("A deadline with no end? Try again with: deadline <desc> /by <time>.");
        }
    }

    public void validEventFormat(String[] input) throws GrimmException {
        if (input.length < 2) {
            throw new GrimmException("An event with no start and end? Try again with: event <desc> /from <time> /to <time>.");
        }
    }

    public int parseInt() throws GrimmException {
        try {
            return Integer.parseInt(this.desc);
        } catch (NumberFormatException e) {
            throw new GrimmException("This is not a number I know. Try again.");
        }
    }

    public String[] parseDeadline() throws GrimmException {
        this.validName();
        String[] descParts = this.desc.split(" /by ", 2);
        this.validDeadlineFormat(descParts);
        return new String[] {descParts[0], descParts[1]};
    }

    public String[] parseEvent() throws GrimmException {
        this.validName();
        String[] descParts = this.desc.split(" /from ", 2);
        this.validEventFormat(descParts);
        String[] duration = descParts[1].split(" /to ", 2);
        this.validEventFormat(duration);
        return new String[] {descParts[0], duration[0], duration[1]};
    }
}
