package online.dbaltor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ticket {
    private static final int NUMBER_OF_COLUMNS = 9;
    private static final int NUMBER_OF_ROWS = 3;
    private static final int BLANKS_PER_ROW = 4;
    private static final double NUMBERS_PER_ROW = 5;
    // 1 means a blank
    private static final List<Integer> VALID_COLUMN_LAYOUTS = List.of(0b000, 0b100, 0b010, 0b001, 0b110, 0b011, 0b101);

    private final List<Column> columns = new ArrayList<>(NUMBER_OF_COLUMNS);
    private final Counters counters = new Counters();

    public int getNumberOfColumns() {
        return NUMBER_OF_COLUMNS;
    }

    public int getNumberOfRows() {
        return NUMBER_OF_ROWS;
    }

    public Ticket() {
        mapBlanks();
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

    public List<Column> getColumns() {
        return columns;
    }

    public List<Integer> getRow(int rowNumber) {
        if (rowNumber < 0 || rowNumber >= NUMBER_OF_ROWS) {
            throw new IllegalArgumentException("Invalid row number");
        }
        var row = new ArrayList<Integer>(NUMBER_OF_COLUMNS);
        columns.forEach(
                column -> row.add(column.getNumbers().get(rowNumber))
        );
        return row;
    }

    /**
     * Create the ticket layout by mapping where the blanks will be in each column.
     * <p>
     * The following rules are enforced:
     * - a column cannot have three blanks
     * - a row contain five numbers and four blanks
     */
    private void mapBlanks() {
        var random = new Random();
        int columnLayout;
        var columnIndex = 0;

        while (columnIndex < NUMBER_OF_COLUMNS) {
            columnLayout = getColumnLayout(random, counters);

            if (columnLayout == 0b111) { // three blanks
                columnLayout = swapOneBlankWithPreviousColumn(columnLayout, columnIndex);
            }

            updateCounters(columnLayout, columnIndex);
            columns.add(new Column(NUMBER_OF_ROWS, columnLayout));
            columnIndex++;
        }
    }

    private int getColumnLayout(Random random, Counters counters) {
        var columnLayout = VALID_COLUMN_LAYOUTS.get(random.nextInt(0, VALID_COLUMN_LAYOUTS.size()));
        if (counters.row1BlankCounter == BLANKS_PER_ROW) {
            columnLayout = 0b011 & columnLayout; // set a number for row 1
        }
        if (counters.row1NumberCounter == NUMBERS_PER_ROW) {
            columnLayout = 0b100 | columnLayout; // set a blank for row 1
        }
        if (counters.row2BlankCounter == BLANKS_PER_ROW) {
            columnLayout = 0b101 & columnLayout; // set a number for row 2
        }
        if (counters.row2NumberCounter == NUMBERS_PER_ROW) {
            columnLayout = 0b010 | columnLayout; // set a blank for row 2
        }
        if (counters.row3BlankCounter == BLANKS_PER_ROW) {
            columnLayout = 0b110 & columnLayout; // set a number for row 3
        }
        if (counters.row3NumberCounter == NUMBERS_PER_ROW) {
            columnLayout = 0b001 | columnLayout; // set a blank for row 3
        }
        return columnLayout;
    }

    private int swapOneBlankWithPreviousColumn(int columnLayout, int columnIndex) {
        for (var i = columnIndex - 1; i >= 0; i--) {
            var blankMap = columns.get(i).getBlankMap();
            switch (blankMap) {
                case 0, 1, 2 : {
                    // add blank to previous column
                    columns.set(i, new Column(NUMBER_OF_ROWS, blankMap | 0b100));
                    // update counters with +1 blank and -1 number
                    counters.incrementRow1BlankCounterBy(1, columnIndex - 1);
                    // remove blank from this column
                    return 0b011;
                }
                case 4 : {
                    // add blank to previous column
                    columns.set(i, new Column(NUMBER_OF_ROWS, blankMap | 0b001));
                    // update counters with +1 blank and -1 number
                    counters.incrementRow3BlankCounterBy(1, columnIndex - 1);
                    // remove blank from this column
                    return 0b110;
                }
            }
        }
        return columnLayout;
    }

    private void updateCounters(int columnLayout, int columnIndex) {
        counters.incrementRow1BlankCounterBy((columnLayout & 0b100) >> 2, columnIndex);
        counters.incrementRow2BlankCounterBy((columnLayout & 0b010) >> 1, columnIndex);
        counters.incrementRow3BlankCounterBy(columnLayout & 0b001, columnIndex);
    }

    private static class Counters {
        int row1BlankCounter;
        int row2BlankCounter;
        int row3BlankCounter;
        int row1NumberCounter;
        int row2NumberCounter;
        int row3NumberCounter;

        void incrementRow1BlankCounterBy(int value, int columnIndex) {
            row1BlankCounter = row1BlankCounter + value;
            row1NumberCounter = columnIndex + 1 - row1BlankCounter;
        }

        void incrementRow2BlankCounterBy(int value, int columnIndex) {
            row2BlankCounter = row2BlankCounter + value;
            row2NumberCounter = columnIndex + 1 - row2BlankCounter;
        }

        void incrementRow3BlankCounterBy(int value, int columnIndex) {
            row3BlankCounter = row3BlankCounter + value;
            row3NumberCounter = columnIndex + 1 - row3BlankCounter;
        }
    }
}
