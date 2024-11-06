package online.dbaltor;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

/**
 * I have optimised Collections.shuffle for this application
 */
public class Randomizer {

    // used a fast jumpable pseudo random generator
    private static final String PRNG = "Xoroshiro128PlusPlus";

    private final RandomGenerator random;

    public Randomizer() {
        this.random = RandomGeneratorFactory.of(PRNG).create();
    }

    public int nextInt(int bound) {
        return random.nextInt(bound);
    }

    public void shuffle(int[] numbers) {
        for (int i = numbers.length; i > 1; i--)
            swap(numbers, i - 1, random.nextInt(i));
    }

    private static void swap(int[] arr, int i, int j) {
        var tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

}
