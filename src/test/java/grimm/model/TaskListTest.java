package grimm.model;

import grimm.exception.GrimmException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertSame;

class TaskListTest {

    private TaskList list;

    @BeforeEach
    public void setUp() {
        list = new TaskList();
    }

    @Test
    public void taskList_get() {
        assertEquals(0, list.getSize());

        Task t1 = new Task("read book");
        Task t2 = new ToDo("return book");

        list.add(t1);
        list.add(t2);

        assertEquals(2, list.getSize());
        assertSame(t1, list.getTask(1));
        assertSame(t2, list.getTask(2));
    }

    @Test
    public void taskList_returnsMarkedTask() {
        Task t = new Task("defeat Grimm");
        list.add(t);

        Task marked = list.mark(1);
        assertSame(t, marked);
        assertTrue(marked.getMark());
        assertEquals("[X] defeat Grimm", marked.toString());
    }

    @Test
    public void taskList_returnsUnmarkedTask() {
        Task t = new Task("defeat Grimm", true);
        list.add(t);

        Task unmarked = list.unmark(1);
        assertSame(t, unmarked);
        assertFalse(unmarked.getMark());
        assertEquals("[ ] defeat Grimm", unmarked.toString());
    }

    @Test
    public void taskList_deleteTask() {
        Task t1 = new Task("a");
        Task t2 = new Task("b");
        list.add(t1);
        list.add(t2);

        Task removed = list.delete(1);
        assertSame(t1, removed);
        assertEquals(1, list.getSize());
        assertSame(t2, list.getTask(1));
    }
}
