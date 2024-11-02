package online.dbaltor;

import java.util.*;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Ticket {
    private static final int NUMBER_OF_COLUMNS = 9;
    private static final int NUMBER_OF_ROWS = 3;
    private static final int NUMBERS_PER_ROW = 5;

    private static final List<List<List<Boolean>>> VALID_COLUMN_LAYOUTS = List.of(
            List.of(
                    List.of(true, false, false),
                    List.of(false, true, false),
                    List.of(false, false, true)),
            List.of(
                    List.of(true, true, false),
                    List.of(true, false, true),
                    List.of(false, true, true)),
            List.of(
                    List.of(true, true, true)));

    private final List<Column> columns = new ArrayList<>(NUMBER_OF_COLUMNS);
    private final RandomGenerator random;
    private final int[] numbersPerColumn;

    public Ticket(RandomGenerator random, int[] numbersPerColumn) {
        this.random = random;
        this.numbersPerColumn = numbersPerColumn;
        var grid = generateGrid();

        IntStream.range(0, NUMBER_OF_COLUMNS).forEach(col ->
                columns.add(new Column(grid[0][col], grid[1][col], grid[2][col])));
    }

    private boolean[][] generateGrid() {
        var grid = new boolean[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];

        IntStream.range(0, NUMBER_OF_COLUMNS).forEach(
                columnNumber -> fillOutColumn(grid, columnNumber));
        balanceRows(grid);
        return grid;
    }

    private void balanceRows(boolean[][] grid) {
        while (true) {
            var numbersPerRow =
                    IntStream.range(0, NUMBER_OF_ROWS)
                            .mapToObj(rowNumber -> IntStream.range(0, grid[rowNumber].length)
                                    .mapToObj(col -> grid[rowNumber][col])
                                    .filter(bool -> bool)
                                    .count())
                            .map(Math::toIntExact)
                            .toList();
            int minNumbersPerRow = Collections.min(numbersPerRow);
            int rowWithLessNumbers = numbersPerRow.indexOf(minNumbersPerRow);
            int maxNumbersPerRow = Collections.max(numbersPerRow);
            int rowWithMoreNumbers = numbersPerRow.indexOf(maxNumbersPerRow);

            if (minNumbersPerRow == maxNumbersPerRow) break;

            int targetDiff = getTargetDiff(maxNumbersPerRow, minNumbersPerRow);
            moveNumbersAcrossRows(
                    grid,
                    rowWithLessNumbers,
                    rowWithMoreNumbers,
                    targetDiff);
        }
    }

    private int getTargetDiff(int maxNumbersPerRow, int minNumbersPerRow) {
        return Math.min(
                maxNumbersPerRow - NUMBERS_PER_ROW,
                NUMBERS_PER_ROW - minNumbersPerRow);
    }

    private void moveNumbersAcrossRows(boolean[][] grid, int rowWithLessNumbers, int rowWithMoreNumbers, int targetDiff) {
        var counter = 0;
        for (var col = 0; col < NUMBER_OF_COLUMNS; col++) {
            if (numbersPerColumn[col] != 3 && grid[rowWithMoreNumbers][col]) {
                if (!grid[rowWithLessNumbers][col]) {
                    grid[rowWithMoreNumbers][col] = false;
                    grid[rowWithLessNumbers][col] = true;
                    counter++;
                    if (counter == targetDiff) break;
                }
            }
        }
    }

    private void fillOutColumn(boolean[][] grid, int columnNumber) {
        var layouts = VALID_COLUMN_LAYOUTS.get(numbersPerColumn[columnNumber] - 1);
        var columnLayout = layouts.get(random.nextInt(0, layouts.size()));
        updateColumn(grid, columnNumber, columnLayout);
    }

    private void updateColumn(boolean[][] grid, int columnNumber, List<Boolean> columnLayout) {
        IntStream.range(0, NUMBER_OF_ROWS).forEach(
                rowNumber -> grid[rowNumber][columnNumber] = columnLayout.get(rowNumber));
    }

    /**
     * Add a number to the ticket if the correct column still have available slots.
     *
     * @param number the number being added to the ticket
     * @return indicator whether the number has been added
     */
    public boolean add(int number) {
        var columnNumber = (number == 90) ? 8 : number / 10;
        return columns.get(columnNumber).add(number);
    }

    public List<Integer> getRow(int rowNumber) {
        if (rowNumber < 0 || rowNumber >= NUMBER_OF_ROWS) {
            throw new IllegalArgumentException("Invalid row number");
        }

        return columns.stream()
                .map(column -> column.getNumbers().get(rowNumber))
                .toList();
    }

    public List<Column> getColumns() {
        return columns;
    }

    public List<List<Integer>> getRows() {
        return IntStream.range(0, NUMBER_OF_ROWS)
                .mapToObj(rowNumber ->
                        columns.stream()
                                .mapToInt(column -> column.getNumbers().get(rowNumber))
                                .boxed()
                                .toList())
                .toList();
    }

    public String print() {
        return getRows().stream()
                .map(row -> row.stream()
                        .map(this::printNumber)
                        .collect(Collectors.joining(" | ")))
                .collect(Collectors.joining("\n"));
    }

    private String printNumber(Integer n) {
        return n == 0 ? "  " : String.format("%02d", n);
    }
}
