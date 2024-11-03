package online.dbaltor;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Column {
    private final int blankMap;
    private final int capacity;
    private final Queue<Integer> orderingQueue;
    private final ArrayList<Integer> numbers;

    public Column(int numberOfRows, int blankMap) {
        if (blankMap == 0b111) // means three spaces
            throw new IllegalArgumentException("Column cannot contain three blanks");
        this.blankMap = blankMap;
        this.capacity = ((~blankMap & 0b100) >> 2)
                + ((~blankMap & 0b010) >> 1)
                + (~blankMap & 0b001);
        this.orderingQueue = new PriorityQueue<>(numberOfRows);
        this.numbers = new ArrayList<Integer>(numberOfRows);
    }

    public int getBlankMap() {
        return blankMap;
    }

    public boolean add(Integer number) {
        if (orderingQueue.size() < capacity) {
            orderingQueue.add(number);
            if (orderingQueue.size() == capacity) {
                createNumberList();
            }
            return true;
        }
        return false;
    }

    private void createNumberList() {
        for (var i = 2; i >= 0; i--) {
            if ((blankMap & (1 << i)) != 0) { // blank position
                numbers.add(0);
            } else {
                numbers.add(orderingQueue.poll());
            }
        }
    }

    public List<Integer> getNumbers() {
        if (numbers.size() < capacity) {
            throw new IllegalStateException("Column has not been fully initialised");
        }
        return numbers;
    }
}
