package random;

import java.util.Random;

public class RandomCreator {
    private static Random rand = new Random();

    public static DiscreteRandom newDiscreteRandom(int min, int max) {
        return new DiscreteRandom(min, max, nextSeed());
    }

    public static ContinuousRandom newContinuousRandom(double min, double max) {
        return new ContinuousRandom(min, max, nextSeed());
    }

    public static ExponentialRandom newExponentialRandom(double mean) {
        return new ExponentialRandom(mean, nextSeed());
    }

    public static TriangularRandom newTriangularRandom(double min, double max, double mode) {
        return new TriangularRandom(min, max, mode, nextSeed());
    }

    public static EmpiricalRandom newEmpiricalRandom(Type type) {
        return new EmpiricalRandom(nextSeed(), type);
    }

    public static void addInterval(EmpiricalRandom emp, double min, double max, double probability) {
        emp.addInterval(min, max, nextSeed(), probability);
    }

    public static void setSeed(long seed) {
        rand = new Random(seed);
    }

    private static long nextSeed() {
        return rand.nextLong();
    }
}
