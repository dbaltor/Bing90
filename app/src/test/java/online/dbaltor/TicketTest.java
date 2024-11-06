package online.dbaltor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TicketTest {

    private final int[] numbersPerColumn = new int[]{2, 2, 1, 2, 3, 1, 1, 1, 2};

    @Test
    @DisplayName("A ticket should contain 9 columns")
    public void aTicketShouldContain9Columns() {
        // Given
        var ticket = new Ticket(new Randomizer(), numbersPerColumn);
        // When
        var numberOfColumns = ticket.getColumns().size();
        // Then
        assertEquals(9, numberOfColumns);
    }

    @Test
    @DisplayName("A ticket should contain 3 rows")
    public void aTicketShouldContain3Rows() {
        // Given
        var ticket = initialiseTicket();
        // When
        var numberOfRows = ticket.getRows().size();
        // Then
        assertEquals(3, numberOfRows);
    }

    @Test
    @DisplayName("A ticket should reject a request for an invalid row number")
    public void aTicketShouldRejectARequestForAnInvalidRowNumber() {
        // Given
        var ticket = new Ticket(new Randomizer(), numbersPerColumn);
        // When
        var exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    ticket.getRow(4);
                });
        // Then
        assertEquals("Invalid row number", exception.getMessage());
    }

    @Test
    @DisplayName("A number should be added to the right column")
    public void aNumberShouldBeAddedToTheRightColumn() {
        // Given
        var ticket = new Ticket(new Randomizer(), numbersPerColumn);
        ticket.add(31);
        ticket.add(34);
        ticket.add(37);
        // When
        var numbersInThirdColumn = ticket.getColumns().get(3).getNumbers();
        // Then
        assertTrue(numbersInThirdColumn.contains(31));
    }

    @Test
    @DisplayName("Each ticket row should contain 5 numbers and 4 blanks")
    public void eachTicketRowShouldContain5NumbersAnd4Blanks() {
        // Given
        var ticket = initialiseTicket();
        // When
        var row0Blanks = ticket.getRow(0).stream()
                .filter(number -> number == 0)
                .count();
        var row0Numbers = ticket.getRow(0).stream()
                .filter(number -> number != 0)
                .count();
        // Then
        assertEquals(4, row0Blanks);
        assertEquals(5, row0Numbers);
    }

    private Ticket initialiseTicket() {
        var ticket = new Ticket(new Randomizer(), numbersPerColumn);
        ticket.add(1);
        ticket.add(2);
        ticket.add(11);
        ticket.add(12);
        ticket.add(21);
        ticket.add(31);
        ticket.add(32);
        ticket.add(41);
        ticket.add(42);
        ticket.add(43);
        ticket.add(51);
        ticket.add(61);
        ticket.add(71);
        ticket.add(81);
        ticket.add(90);
        return ticket;
    }
}
