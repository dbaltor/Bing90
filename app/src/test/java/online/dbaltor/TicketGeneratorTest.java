package online.dbaltor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.random.RandomGeneratorFactory;
import java.util.stream.IntStream;


class TicketGeneratorTest {

    @Test
    @Timeout(1)
    @DisplayName("Should generate 10K strips within 1s")
    public void shouldGenerate10KStripsWithin1S() {
        // used the fastest pseudo random generator for single thread
        var random = RandomGeneratorFactory.of("Xoroshiro128PlusPlus").create();
        IntStream.range(0, 10_000)
                .parallel()
                .forEach(execution -> {
                    var strip = new Strip(random);
                    strip.generateTickets();
                });
    }
}