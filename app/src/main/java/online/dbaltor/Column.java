package online.dbaltor;

import java.util.PriorityQueue;
import java.util.Queue;

public class Column {
    private Queue<Integer> numbers;

    public Column(int numberOfRows) {
        this.numbers = new PriorityQueue<>(numberOfRows);
    }

    public void add(Integer number) {
        numbers.add(number);
    }
}
