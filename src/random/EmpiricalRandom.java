package random;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EmpiricalRandom implements IRandomGenerator {
    private static class Interval {
        IRandomGenerator generator;
        double probability;

        Interval(IRandomGenerator generator, double probability) {
            this.generator = generator;
            this.probability = probability;
        }
    }

    private final Random rand;
    private final List<Interval> intervals = new ArrayList<>();
    private final Type type;

    EmpiricalRandom(long seed, Type type) {
        this.rand = new Random(seed);
        this.type = type;
    }

    void addInterval(double min, double max, long seed, double probability) {
        switch(type) {
            case DISCRETE:
                intervals.add(new Interval(new DiscreteRandom((int) min, (int) max, seed), probability));
                break;
            case CONTINUOUS:
                intervals.add(new Interval(new ContinuousRandom(min, max, seed), probability));
                break;
        }
    }

    @Override
    public Number nextValue() {
        double randVal = rand.nextDouble();
        double cumulativeProb = 0.0;

        for (Interval interval : intervals) {
            cumulativeProb += interval.probability;
            if (randVal < cumulativeProb) {
                return interval.generator.nextValue();
            }
        }
        throw new IllegalStateException("Interval selection failed. Probability distribution have to sum up to 1.");
    }
}
