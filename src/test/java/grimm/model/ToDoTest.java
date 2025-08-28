package grimm.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ToDoTest {
    @Test
    public void toDo_default() {
        ToDo t = new ToDo("read book");
        assertFalse(t.getMark(), "New ToDo should not be marked by default");
        assertEquals("[T][ ] read book", t.toString());
    }

    @Test
    public void toDo_markedTrue() {
        ToDo t = new ToDo("return book", true);
        assertTrue(t.getMark(), "Constructor with true should mark the task");
        assertEquals("[T][X] return book", t.toString());
    }

    @Test
    public void toDo_markedFalse() {
        ToDo t = new ToDo("join the Troupe", false);
        assertFalse(t.getMark(), "Constructor with false should leave task unmarked");
        assertEquals("[T][ ] join the Troupe", t.toString());
    }

    @Test
    public void toDo_markThenUnmark() {
        ToDo t = new ToDo("defeat Grimm");
        assertFalse(t.getMark());

        t.mark();
        assertTrue(t.getMark());
        assertEquals("[T][X] defeat Grimm", t.toString());

        t.unmark();
        assertFalse(t.getMark());
        assertEquals("[T][ ] defeat Grimm", t.toString());
    }

    @Test
    public void toDo_getName() {
        ToDo t = new ToDo("study CS2103");
        assertEquals("study CS2103", t.getName());
    }

    @Test
    public void toDo_emptyName() {
        ToDo t = new ToDo("");
        assertEquals("[T][ ] ", t.toString(), "Empty description should still format correctly");
    }
}
