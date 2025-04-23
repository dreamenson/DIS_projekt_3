package random;

import java.util.Random;

public class DiscreteRandom implements IRandomGenerator {
    private final int min;  // included
    private final int max;  // excluded
    private final Random rand;

    DiscreteRandom(int min, int max, long seed) {
        this.min = min;
        this.max = max;
        this.rand = new Random(seed);
    }

    @Override
    public Number nextValue() {
        return rand.nextInt(min, max);
    }
}
