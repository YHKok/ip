package grimm.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ToDoTest {
    @Test
    public void toDo_default_notMarked() {
        ToDo t = new ToDo("Read book");
        assertFalse(t.getMark(), "New ToDo should not be marked by default");
        assertEquals("[T][ ] Read book", t.toString());
    }

    @Test
    public void toDo_markedTrue_constructor() {
        ToDo t = new ToDo("Return book", true);
        assertTrue(t.getMark(), "Constructor with true should mark the task");
        assertEquals("[T][X] Return book", t.toString());
    }

    @Test
    public void toDo_markedFalse_constructor() {
        ToDo t = new ToDo("Join the Troupe", false);
        assertFalse(t.getMark(), "Constructor with false should leave task unmarked");
        assertEquals("[T][ ] Join the Troupe", t.toString());
    }

    @Test
    public void toDo_markThenUnmark_taskStatusChanges() {
        ToDo t = new ToDo("Defeat Grimm");
        assertFalse(t.getMark());

        t.mark();
        assertTrue(t.getMark());
        assertEquals("[T][X] Defeat Grimm", t.toString());

        t.unmark();
        assertFalse(t.getMark());
        assertEquals("[T][ ] Defeat Grimm", t.toString());
    }

    @Test
    public void toDo_getName_taskNameReturned() {
        ToDo t = new ToDo("Nurture Grimmchild");
        assertEquals("Nurture Grimmchild", t.getName());
    }

    @Test
    public void toDo_emptyName_emptyDescriptionHandled() {
        ToDo t = new ToDo("");
        assertEquals("[T][ ] ", t.toString(), "Empty description should still format correctly");
    }
}
