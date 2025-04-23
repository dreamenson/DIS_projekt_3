package random;

import java.util.Random;

public class ContinuousRandom implements IRandomGenerator {
    private final double min;  // included
    private final double max;  // excluded
    private final Random rand;

    ContinuousRandom(double min, double max, long seed) {
        this.min = min;
        this.max = max;
        this.rand = new Random(seed);
    }

    @Override
    public Number nextValue() {
        return rand.nextDouble(min, max);
    }
}
