package grimm.model;

public class Task {
    private boolean isMarked = false;
    private String name;

    public Task(String name) {
        this.name = name;
    }

    public Task(String name, boolean mark) {
        this.name = name;
        if (mark) {
            this.mark();
        }
    }

    public void mark() {
        this.isMarked = true;
    }

    public void unmark() {
        this.isMarked = false;
    }

    public String getName() {
        return this.name;
    }

    public boolean getMark() {
        return this.isMarked;
    }

    @Override
    public String toString() {
        if (this.isMarked) {
            return "[X] " + this.getName();
        } else {
            return "[ ] " + this.getName();
        }
    }

}
