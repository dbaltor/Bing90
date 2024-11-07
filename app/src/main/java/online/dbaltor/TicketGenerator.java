package online.dbaltor;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TicketGenerator {

    private final Randomizer randomizer;

    public TicketGenerator(Randomizer randomizer) {
        this.randomizer = randomizer;
    }

    public String generate(int numberOfStrips) {
        if (numberOfStrips == 1) {
            var strip = new Strip(randomizer);
            strip.generateTickets();
            return strip.printTickets();
        } else {
            return IntStream.range(0, numberOfStrips)
                    .parallel()
                    .mapToObj(execution -> {
                        var strip = new Strip(randomizer);
                        strip.generateTickets();
                        return strip.printTickets();
                    })
                    .collect(Collectors.joining("\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n"));
        }
    }
}
