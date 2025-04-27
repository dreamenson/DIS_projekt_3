import simulation.MySimulation;

public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
//            System.out.println();
            MySimulation sim = new MySimulation(5,5,38,58);
            sim.simulate(100);
        }
    }
}