package random;

import java.util.Random;

public class TriangularRandom implements IRandomGenerator {
    private final double a; // lower limit
    private final double b; // upper limit
    private final double c; // mode
    private final double F;
    private final Random rand;

    TriangularRandom(double min, double max, double mode, long seed) {
        this.a = min;
        this.b = max;
        this.c = mode;
        this.rand = new Random(seed);
        this.F = (mode - min) / (max - min);
    }

    @Override
    public Number nextValue() {
        double U = rand.nextDouble();
        if (U < F) {
            return (a + Math.sqrt(U * (b - a) * (c - a)));
        } else {
            return (b - Math.sqrt((1 - U) * (b - a) * (b - c)));
        }
    }
}
