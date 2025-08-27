public class ToDo extends Task {
    public ToDo(String name) {
        super(name);
    }

    public ToDo(String name, boolean mark) {
        super(name, mark);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
