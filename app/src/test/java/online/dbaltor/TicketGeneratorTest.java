package online.dbaltor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class TicketGeneratorTest {

    private final int[] numbersPerColumn = new int[]{2, 2, 1, 2, 3, 1, 1, 1, 2};

    @Test
    @DisplayName("A strip should contain 6 tickets")
    public void aStripShouldContain6Tickets() {
        // Given
        var strip = new Strip();
        strip.generateTickets();
        // When
        var numberOfTickets = strip.getTickets().size();
        // Then
        assertEquals(6, numberOfTickets);
    }

    @Test
    @DisplayName("A ticket should contain 9 columns")
    public void aTicketShouldContain9Columns() {
        // Given
        var ticket = new Ticket(new Random(), numbersPerColumn);
        // When
        var numberOfColumns = ticket.getNumberOfColumns();
        // Then
        assertEquals(9, numberOfColumns);
    }

    @Test
    @DisplayName("A ticket should contain 3 rows")
    public void aTicketShouldContain3Rows() {
        // Given
        var ticket = new Ticket(new Random(), numbersPerColumn);
        // When
        var numberOfColumns = ticket.getNumberOfRows();
        // Then
        assertEquals(3, numberOfColumns);
    }

    @Test
    @DisplayName("A column should contain numbers in ascending order")
    public void aColumnShouldContainNumbersInAscendingOrder() {
        // Given
        var column1 = new Column(true, true, true); // means three numbers
        column1.add(9);
        column1.add(5);
        column1.add(1);
        var column2 = new Column(true, false, true); // means one space in the middle
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
        var expectedMessage = "Column cannot contain three blanks";
        // When
        var exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    var column = new Column(false, false, false);
                });
        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    @DisplayName("A column should not return numbers before initialised")
    public void aColumnShouldNotReturnNumbersBeforeInitialised() {
        // Given
        var expectedMessage = "Column has not been fully initialised";
        var column = new Column(false, true, true);
        column.add(1);
        // When
        var exception = assertThrows(IllegalStateException.class,
                () -> {
                    var numbers = column.getNumbers();
                });
        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    @DisplayName("A ticket should reject a request for an invalid row number")
    public void aTicketShouldRejectARequestForAnInvalidRowNumber() {
        // Given
        var ticket = new Ticket(new Random(), numbersPerColumn);
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
        var ticket = new Ticket(new Random(), numbersPerColumn);
        // When
        ticket.add(31);
        ticket.add(34);
        ticket.add(37);
        // Then
        assertTrue(ticket.getColumns().get(3).getNumbers().contains(31));
    }

    @Test
    @DisplayName("Each ticket row should contain 5 numbers and 4 blanks")
    public void eachTicketRowShouldContain5NumbersAnd4Blanks() {
        // Given
        var ticket = new Ticket(new Random(), numbersPerColumn);
        ticket.add(1);
        ticket.add(2);
        ticket.add(3);
        ticket.add(11);
        ticket.add(12);
        ticket.add(13);
        ticket.add(21);
        ticket.add(22);
        ticket.add(23);
        ticket.add(31);
        ticket.add(32);
        ticket.add(33);
        ticket.add(41);
        ticket.add(42);
        ticket.add(43);
        ticket.add(51);
        ticket.add(52);
        ticket.add(53);
        ticket.add(61);
        ticket.add(62);
        ticket.add(63);
        ticket.add(71);
        ticket.add(72);
        ticket.add(73);
        ticket.add(81);
        ticket.add(82);
        ticket.add(83);
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

    @Test
    @DisplayName("A strip should not contain duplicate numbers")
    public void aStripShouldNotContainDuplicateNumbers() {
        // Given
        var set = new HashSet<Integer>();
        var strip = new Strip();
        strip.generateTickets();
        // When
        strip.getTickets().forEach(ticket -> {
            for (var row = 0; row < 3; row++) {
                ticket.getRow(row).stream()
                        .filter(number -> number != 0)
                        .forEach(number -> {
                            if (!set.add(number)) {
                                throw new IllegalStateException("Found duplicate number");
                            }
                        });
            }
        });
        // Then
        assertTrue(true);
    }

    @Test
    @DisplayName("A strip should contain 90 numbers")
    public void aStripShouldContain90Numbers() {
        // Given
        var strip = new Strip();
        // When
        var grid = strip.generateGrid(new Random());
        // Then
        var sum = Arrays.stream(grid)
                .flatMapToInt(IntStream::of)
                .sum();
        assertEquals(90, sum);
    }

    @Test
    @DisplayName("Each ticket should contain 15 numbers")
    public void eachTicketShouldContain15Numbers() {
        // Given
        var strip = new Strip();
        // When
        var grid = strip.generateGrid(new Random());
        // Then
        var sum = Arrays.stream(grid)
                .mapToLong(s -> Arrays.stream(s).sum())
                .filter(n -> n != 15)
                .count();
        assertEquals(0, sum);
    }
}
