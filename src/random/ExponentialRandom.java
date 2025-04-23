package random;

import java.util.Random;

public class ExponentialRandom implements IRandomGenerator {
    private final double mean;
    private final Random rand;

    ExponentialRandom(double mean, long seed) {
        this.mean = mean;
        this.rand = new Random(seed);
    }

    @Override
    public Number nextValue() {
        double U = 1 - rand.nextDouble();
        return (-Math.log(U) * mean);
    }
}
