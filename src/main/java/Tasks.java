public class Tasks {
    private boolean done;
    private String name;

    public Tasks(String name) {
        this.name = name;
        this.done = false;
    }

    public void mark() {
        this.done = true;
    }

    public void unmark() {
        this.done = false;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        if (this.done) {
            return "[X] " + this.getName();
        } else {
            return "[ ] " + this.getName();
        }
    }

}
