package online.dbaltor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StripTest {

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
    @DisplayName("A strip should contain 90 numbers")
    public void aStripShouldContain90Numbers() {
        // Given
        var strip = new Strip();
        strip.generateTickets();
        // When
        var sum = strip.getTickets().stream()
                .flatMap(ticket -> ticket.getRows().stream()
                        .flatMap(row -> row.stream()
                                .filter(number -> number != 0)))
                .count();
        // Then
        assertEquals(90, sum);
    }

    @Test
    @DisplayName("A strip should not contain duplicate numbers")
    public void aStripShouldNotContainDuplicateNumbers() {
        // Given
        var set = new HashSet<Integer>();
        var strip = new Strip();
        strip.generateTickets();
        // When

        strip.getTickets().stream()
                .flatMap(ticket -> ticket.getRows().stream()
                        .flatMap(row -> row.stream()
                                .filter(number -> number != 0)))
                .forEach(number -> {
                    if (!set.add(number)) {
                        throw new IllegalStateException("Found duplicate number");
                    }
                });
        // Then
        assertTrue(true);
    }
}
