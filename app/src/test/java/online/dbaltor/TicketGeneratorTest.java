package online.dbaltor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.stream.IntStream;


class TicketGeneratorTest {

    @Test
    @Timeout(2)
    @DisplayName("Should generate 10K strips within 1s")
    public void shouldGenerate10KStripsWithin1S() {
        IntStream.range(0, 10_000)
                .parallel()
                .forEach(execution -> {
                    var strip = new Strip();
                    strip.generateTickets();
                });
    }
}