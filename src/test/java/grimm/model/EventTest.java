package grimm.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EventTest {

    @Test
    public void event_validSameDay_formatCorrectly() {
        Event e = new Event("Defeat NKG", "12/02/2002 1430", "12/02/2002 1600");
        assertEquals(
                "2 December 2002, 2:30 pm to 2 December 2002, 4:00 pm",
                e.formatDateTime()
        );
    }

    @Test
    public void event_validDiffDay_formatCorrectly() {
        Event e = new Event("Defeat NKG",
                "01/01/2025 2300",   // 11:00 PM
                "01/02/2025 0130");  // 1:30 AM next day
        assertEquals(
                "1 January 2025, 11:00 pm to 2 January 2025, 1:30 am",
                e.formatDateTime()
        );
    }

    @Test
    public void event_unmarked_correctFormat() {
        Event e = new Event("Defeat NKG", "03/15/2024 0900", "03/15/2024 0930");
        assertEquals(
                "[E][ ] Defeat NKG (from: 15 March 2024, 9:00 am to 15 March 2024, 9:30 am)",
                e.toString()
        );
    }

    @Test
    public void event_marked_correctFormat() {
        Event e = new Event("Defeat NKG", true, "11/05/2024 1500", "11/05/2024 1615");
        assertEquals(
                "[E][X] Defeat NKG (from: 5 November 2024, 3:00 pm to 5 November 2024, 4:15 pm)",
                e.toString()
        );
    }

}