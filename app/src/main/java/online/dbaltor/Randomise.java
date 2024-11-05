package online.dbaltor;

import java.util.random.RandomGenerator;

/**
 * I have optimised Collections.shuffle for this application
 */
public class Randomise {

    public static void shuffle(int[] numbers, RandomGenerator random) {
        for (int i = numbers.length; i > 1; i--)
            swap(numbers, i - 1, random.nextInt(i));
    }

    private static void swap(int[] arr, int i, int j) {
        var tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
