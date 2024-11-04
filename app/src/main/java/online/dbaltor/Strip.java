package online.dbaltor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Strip {
    private static final int NUMBER_OF_TICKETS = 6;
    public static final int NUMBERS = 90;

    private final List<Ticket> tickets = new ArrayList<>(NUMBER_OF_TICKETS);
    private final List<Integer> randomNumbers = new ArrayList<>(NUMBERS);

    public Strip() {
        for (var i = 1; i <= NUMBERS; i++) {
            randomNumbers.add(i);
        }
        Collections.shuffle(randomNumbers);
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void generateTickets() {
        for (var i = 0; i < NUMBER_OF_TICKETS; i++) {
            tickets.add(new Ticket());
        }
        for (var number : randomNumbers) {
            if (tickets.get(0).add(number)) continue;
            if (tickets.get(1).add(number)) continue;
            if (tickets.get(2).add(number)) continue;
            if (tickets.get(3).add(number)) continue;
            if (tickets.get(4).add(number)) continue;
            tickets.get(5).add(number);
        }
    }

}
