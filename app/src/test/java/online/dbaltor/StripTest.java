package online.dbaltor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.HashSet;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StripTest {

    private Strip strip;

    @BeforeEach
    void setUp(){
        strip = new Strip(new Randomizer());
    }
    @Test
    @DisplayName("A strip should contain 6 tickets")
    public void aStripShouldContain6Tickets() {
        // Given
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

    @Test
    @Timeout(1)
    @DisplayName("Should generate 10K strips within 1s")
    public void shouldGenerate10KStripsWithin1S() {
        IntStream.range(0, 10_000)
                .parallel()
                .forEach(execution -> {
                    var strip = new Strip(new Randomizer());
                    strip.generateTickets();
                });
    }
}
