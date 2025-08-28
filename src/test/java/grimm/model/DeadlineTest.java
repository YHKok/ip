package grimm.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeadlineTest {

    @Test
    void deadline_validFormat() {
        Deadline d = new Deadline("submit report", "12/02/2002");
        assertEquals("2 December 2002", d.formatDate());
    }

    @Test
    void deadline_unMarked() {
        Deadline d = new Deadline("submit report", "12/02/2002");
        assertEquals("[D][ ] submit report (by: 2 December 2002)", d.toString());
    }

    @Test
    void deadlline_marked() {
        Deadline d = new Deadline("submit report", true, "12/02/2002");
        assertEquals("[D][X] submit report (by: 2 December 2002)", d.toString());
    }
}