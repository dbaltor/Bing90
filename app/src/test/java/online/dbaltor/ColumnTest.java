package online.dbaltor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ColumnTest {


    @Test
    @DisplayName("A column should contain numbers in ascending order")
    public void aColumnShouldContainNumbersInAscendingOrder() {
        // Given
        var column1 = new Column(true, true, true); // means three numbers
        column1.add(9);
        column1.add(5);
        column1.add(1);
        var column2 = new Column(true, false, true); // means one space in the middle
        column2.add(9);
        column2.add(5);
        // When
        var column1Numbers = column1.getNumbers();
        var column2Numbers = column2.getNumbers();
        // Then
        assertIterableEquals(List.of(1, 5, 9), column1Numbers);
        assertIterableEquals(List.of(5, 0, 9), column2Numbers);
    }

    @Test
    @DisplayName("A column should not contain three blanks")
    public void aColumnShouldNotContainThreeSpaces() {
        // Given
        var expectedMessage = "Column cannot contain three blanks";
        // When
        var exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    var column = new Column(false, false, false);
                });
        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    @DisplayName("A column should not return numbers before initialised")
    public void aColumnShouldNotReturnNumbersBeforeInitialised() {
        // Given
        var expectedMessage = "Column has not been fully initialised";
        var column = new Column(false, true, true);
        column.add(1);
        // When
        var exception = assertThrows(IllegalStateException.class,
                () -> {
                    var numbers = column.getNumbers();
                });
        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }
}
