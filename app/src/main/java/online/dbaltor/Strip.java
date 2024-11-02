package online.dbaltor;

import java.util.ArrayList;
import java.util.List;

public class Strip {
    private static final int NUMBER_OF_TICKETS = 6;

    private final List<Ticket> tickets = new ArrayList<>(NUMBER_OF_TICKETS);

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void generateTickets() {
        for (var i = 1; i <= NUMBER_OF_TICKETS; i++) {
            tickets.add(new Ticket());
        }
    }
}
