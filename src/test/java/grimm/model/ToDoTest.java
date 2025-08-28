package grimm.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ToDoTest {
    @Test
    public void toDo_default_notMarked() {
        ToDo t = new ToDo("read book");
        assertFalse(t.getMark(), "New ToDo should not be marked by default");
        assertEquals("[T][ ] read book", t.toString());
    }

    @Test
    public void toDo_markedTrue_constructor() {
        ToDo t = new ToDo("return book", true);
        assertTrue(t.getMark(), "Constructor with true should mark the task");
        assertEquals("[T][X] return book", t.toString());
    }

    @Test
    public void toDo_markedFalse_constructor() {
        ToDo t = new ToDo("join the Troupe", false);
        assertFalse(t.getMark(), "Constructor with false should leave task unmarked");
        assertEquals("[T][ ] join the Troupe", t.toString());
    }

    @Test
    public void toDo_markThenUnmark_taskStatusChanges() {
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
    public void toDo_getName_taskNameReturned() {
        ToDo t = new ToDo("study CS2103");
        assertEquals("study CS2103", t.getName());
    }

    @Test
    public void toDo_emptyName_emptyDescriptionHandled() {
        ToDo t = new ToDo("");
        assertEquals("[T][ ] ", t.toString(), "Empty description should still format correctly");
    }
}
