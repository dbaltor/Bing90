package online.dbaltor;

import java.util.*;
import java.util.stream.IntStream;

public class Strip {
    private static final int NUMBERS = 90;
    private static final int NUMBER_OF_TICKETS = 6;
    private static final int NUMBERS_PER_TICKET = 15;
    private static final int NUMBER_OF_COLUMNS = 9;

    private final List<Ticket> tickets = new ArrayList<>(NUMBER_OF_TICKETS);
    private final List<Integer> randomNumbers = new ArrayList<>(NUMBERS);
    private final Random random = new Random();

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

    public int[][] generateGrid(Random random) {
        var sum9 = List.of(
                Arrays.asList(1, 1, 1, 1, 2, 3),
                Arrays.asList(1, 1, 1, 2, 2, 2));
        var sum10 = List.of(
                Arrays.asList(1, 1, 1, 1, 3, 3),
                Arrays.asList(1, 1, 1, 2, 2, 3),
                Arrays.asList(1, 1, 2, 2, 2, 2));
        var sum11 = List.of(
                Arrays.asList(1, 1, 1, 2, 3, 3),
                Arrays.asList(1, 1, 2, 2, 2, 3),
                Arrays.asList(1, 2, 2, 2, 2, 2));

        var grid = new int[NUMBER_OF_TICKETS][NUMBER_OF_COLUMNS];

        fillOutColumn(random, sum9, grid, 0);
        for (var i = 1; i < NUMBER_OF_COLUMNS - 1; i++) {
            fillOutColumn(random, sum10, grid, i);
        }
        fillOutColumn(random, sum11, grid, NUMBER_OF_COLUMNS - 1);
        balanceTickets(grid);
        return grid;
    }

    private void fillOutColumn(Random random, List<List<Integer>> template, int[][] grid, int columnIndex) {
        var column = template.get(random.nextInt(0, template.size() - 1));
        Collections.shuffle(column);
        addColumn(grid, columnIndex, column);
    }

    private void addColumn(int[][] grid, int columnNumber, List<Integer> numbers) {
        for (var rowNumber = 0; rowNumber < numbers.size(); rowNumber++) {
            grid[rowNumber][columnNumber] = numbers.get(rowNumber);
        }
    }

    private void balanceTickets(int[][] grid) {
        while (true) {
            var numbersPerTicket = new ArrayList<Integer>(NUMBER_OF_TICKETS);
            for (var ticketNumber = 0; ticketNumber < NUMBER_OF_TICKETS; ticketNumber++)
                numbersPerTicket.add(IntStream.of(grid[ticketNumber]).sum());

            int minNumbersPerTicket = Collections.min(numbersPerTicket);
            int smallestTicket = numbersPerTicket.indexOf(minNumbersPerTicket);
            int maxNumbersPerTicket = Collections.max(numbersPerTicket);
            int largestTicket = numbersPerTicket.indexOf(maxNumbersPerTicket);

            if (minNumbersPerTicket == maxNumbersPerTicket) break;

            swapNumbersBetweenTickets(
                    grid,
                    smallestTicket,
                    largestTicket,
                    getTargetDiff(maxNumbersPerTicket, minNumbersPerTicket));
        }
    }

    private int getTargetDiff(int maxNumbersPerTicket, int minNumbersPerTicket) {
        return Math.min(
                maxNumbersPerTicket - NUMBERS_PER_TICKET,
                NUMBERS_PER_TICKET - minNumbersPerTicket);
    }

    private void swapNumbersBetweenTickets(int[][] grid, int smallestTicket, int largestTicket, int targetDiff) {
        int maxDiffColumn = -1, maxDiff = 0;
        for (var i = 0; i < grid[0].length; i++) {
            int diff = grid[largestTicket][i] - grid[smallestTicket][i];
            if (diff > maxDiff) {
                maxDiff = diff;
                maxDiffColumn = i;
            }
        }
        var adjust = Math.min(targetDiff, maxDiff);
        grid[largestTicket][maxDiffColumn] = grid[largestTicket][maxDiffColumn] - adjust;
        grid[smallestTicket][maxDiffColumn] = grid[smallestTicket][maxDiffColumn] + adjust;
    }
}
