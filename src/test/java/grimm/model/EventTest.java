package grimm.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventTest {

    @Test
    void event_validSameDay() {
        Event e = new Event("defeat NKG", "12/02/2002 1430", "12/02/2002 1600");
        assertEquals(
                "2 December 2002, 2:30 pm to 2 December 2002, 4:00 pm",
                e.formatDateTime()
        );
    }

    @Test
    void event_validDiffDay() {
        Event e = new Event("defeat NKG",
                "01/01/2025 2300",   // 11:00 PM
                "01/02/2025 0130");  // 1:30 AM next day
        assertEquals(
                "1 January 2025, 11:00 pm to 2 January 2025, 1:30 am",
                e.formatDateTime()
        );
    }

    @Test
    void event_unmarked() {
        Event e = new Event("defeat NKG", "03/15/2024 0900", "03/15/2024 0930");
        assertEquals(
                "[E][ ] defeat NKG (from: 15 March 2024, 9:00 am to 15 March 2024, 9:30 am)",
                e.toString()
        );
    }

    @Test
    void event_marked() {
        Event e = new Event("defeat NKG", true, "11/05/2024 1500", "11/05/2024 1615");
        assertEquals(
                "[E][X] defeat NKG (from: 5 November 2024, 3:00 pm to 5 November 2024, 4:15 pm)",
                e.toString()
        );
    }

}