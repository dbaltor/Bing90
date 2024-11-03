package online.dbaltor;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Column {
    private final int numberOfRows;
    private final int blankMap;
    private final int capacity;
    private final Queue<Integer> orderingQueue;

    public Column(int numberOfRows, int blankMap) {
        this.orderingQueue = new PriorityQueue<>(numberOfRows);
        this.blankMap = blankMap;
        this.numberOfRows = numberOfRows;
        this.capacity = ((~blankMap & 0b100) >> 2)
            + ((~blankMap & 0b010) >> 1)
            + (~blankMap & 0b001);
    }

    public boolean add(Integer number) {
        if (orderingQueue.size() < capacity) {
            orderingQueue.add(number);
            return true;
        }
        return false;
    }

    public List<Integer> getNumbers() {
        var result = new ArrayList<Integer>(numberOfRows);
        for (var i = 2; i >= 0; i--) {
            if ((~blankMap & (1 << i)) != 0) {
                result.add(orderingQueue.poll());
            } else {
                result.add(0);
            }
        }
        return result;
    }
}