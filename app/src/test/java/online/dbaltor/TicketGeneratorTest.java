package online.dbaltor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicketGeneratorTest {

    @Test
    @DisplayName("A strip should contain 6 tickets")
    public void aStripShouldContain6Tickets() {
        // Given
        var strip = new Strip();
        // When
        strip.generateTickets();
        // Then
        var numberOfTickets = strip.getTickets().size();
        assertEquals(6, numberOfTickets);
    }

    @Test
    @DisplayName("A ticket should contain 9 columns")
    public void aTicketShouldContain9Columns() {
        // Given
        var ticket = new Ticket();
        // When
        var numberOfColumns = ticket.getNumberOfColumns();
        // Then
        assertEquals(9, numberOfColumns);
    }

    @Test
    @DisplayName("A ticket should contain 3 rows")
    public void aTicketShouldContain3Rows() {
        // Given
        var ticket = new Ticket();
        // When
        var numberOfColumns = ticket.getNumberOfRows();
        assertEquals(3, numberOfColumns);
    }
}
