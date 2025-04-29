import random.RandomCreator;
import simulation.MySimulation;

public class Main {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        RandomCreator.setSeed(42);
        for (int i = 0; i < 1; i++) {
//            System.out.println();
            MySimulation sim = new MySimulation(6,5,38,57);
//            MySimulation sim = new MySimulation(100,100,100,200);
            sim.simulate(1000);
        }
        System.out.println("duration: " + ((System.currentTimeMillis() - startTime)/60000.0));
    }
}