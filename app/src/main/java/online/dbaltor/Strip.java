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
        IntStream.rangeClosed(1, NUMBERS).forEach(randomNumbers::add);
        Collections.shuffle(randomNumbers);
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void generateTickets() {
        var grid = generateGrid(random);
        IntStream.range(0, NUMBER_OF_TICKETS)
                .forEach(col -> tickets.add(new Ticket(random, grid[col])));
        for (var number : randomNumbers) {
            if (tickets.get(0).add(number)) continue;
            if (tickets.get(1).add(number)) continue;
            if (tickets.get(2).add(number)) continue;
            if (tickets.get(3).add(number)) continue;
            if (tickets.get(4).add(number)) continue;
            tickets.get(5).add(number);
        }
    }

    private int[][] generateGrid(Random random) {
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
        IntStream.range(1, NUMBER_OF_COLUMNS - 1).forEach(i -> fillOutColumn(random, sum10, grid, i));
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
        IntStream.range(0, numbers.size())
                .forEach(rowNumber -> grid[rowNumber][columnNumber] = numbers.get(rowNumber));
    }

    private void balanceTickets(int[][] grid) {
        while (true) {
            var numbersPerTicket = IntStream.range(0, NUMBER_OF_TICKETS)
                    .mapToObj(ticketNumber -> IntStream.of(grid[ticketNumber]).sum())
                    .toList();
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
