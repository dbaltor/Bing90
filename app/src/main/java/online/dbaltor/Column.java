package online.dbaltor;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Column {
    private final List<Boolean> columnLayout;
    private final int capacity;
    private final Queue<Integer> orderingQueue;
    private final ArrayList<Integer> numbers;

    public Column(boolean row1, boolean row2, boolean row3) {
        if (!(row1 || row2 || row3))
            throw new IllegalArgumentException("Column cannot contain three blanks");
        this.columnLayout = List.of(row1, row2, row3);
        this.capacity = (int) columnLayout.stream()
                .filter(bool -> bool)
                .count();
        this.orderingQueue = new PriorityQueue<>(capacity);
        this.numbers = new ArrayList<>(3);
    }

    public boolean add(Integer number) {
        if (numbers.size() < capacity) {
            orderingQueue.add(number);
            if (orderingQueue.size() == capacity) {
                createNumberList();
            }
            return true;
        }
        return false;
    }

    private void createNumberList() {
        columnLayout.forEach(isNumber -> {
            if (isNumber)
                numbers.add(orderingQueue.poll());
            else
                numbers.add(0);
        });
    }

    public List<Integer> getNumbers() {
        if (numbers.size() < capacity) {
            throw new IllegalStateException("Column has not been fully initialised");
        }
        return numbers;
    }
}
