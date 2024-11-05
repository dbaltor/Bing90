package online.dbaltor;

import java.util.random.RandomGeneratorFactory;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TicketGenerator {

    public static void main(String[] args) {
        // used the fastest pseudo random generator for single thread
        var random = RandomGeneratorFactory.of("Xoroshiro128PlusPlus").create();
        var numberOfStrips = 1;
        if (args.length > 1) {
            try {
                numberOfStrips = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("####################################################################");
                System.out.println("The input is optional. If informed, it must be the number of strips.");
                System.out.println("####################################################################");
                System.exit(1);
            }
        }
        if (numberOfStrips == 1) {
            var strip = new Strip(random);
            strip.generateTickets();
            System.out.println(strip.printTickets());
        } else {
            var output = IntStream.range(1, numberOfStrips)
                    .parallel()
                    .mapToObj(execution -> {
                        var strip = new Strip(random);
                        strip.generateTickets();
                        return strip.printTickets();
                    })
                    .collect(Collectors.joining("\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n"));
            System.out.println(output);
        }
    }
}
