package online.dbaltor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        // Then
        assertEquals(3, numberOfColumns);
    }

    @Test
    @DisplayName("A column should contain numbers in ascending order")
    public void aColumnShouldContainNumbersInAscendingOrder() {
        // Given
        var column1 = new Column(3, 0b000); // 000 means no spaces
        column1.add(9);
        column1.add(5);
        column1.add(1);
        var column2 = new Column(3, 0b010); // 010 means one space in the middle
        column2.add(9);
        column2.add(5);
        // When
        var column1Numbers = column1.getNumbers();
        var column2Numbers = column2.getNumbers();
        // Then
        assertIterableEquals(List.of(1, 5, 9), column1Numbers);
        assertIterableEquals(List.of(5, 0, 9), column2Numbers);
    }

    @Test
    @DisplayName("A column should not contain three blanks")
    public void aColumnShouldNotContainThreeSpaces() {
        // Given
        var expectedMessage = "A column cannot contain three blanks";
        // When
        var exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    var column = new Column(3, 0b111);
                });
        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }
}
