import gui.SimulationGUI;
import random.RandomCreator;
import simulation.MySimulation;

public class Main {
    public static void main(String[] args) {
//        long startTime = System.currentTimeMillis();
//        RandomCreator.setSeed(42);
//        for (int i = 0; i < 5; i++) {
//            MySimulation sim = new MySimulation(6,5,38,57);
////            MySimulation sim = new MySimulation(8,100,100,48);
//            sim.simulate(100);
//        }
//        System.out.println("duration: " + ((System.currentTimeMillis() - startTime)/60000.0));

        new SimulationGUI();
    }
}