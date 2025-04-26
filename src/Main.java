import simulation.MySimulation;

public class Main {
    public static void main(String[] args) {
        MySimulation sim = new MySimulation(4,5,38,60);
        sim.simulate(10);
    }
}