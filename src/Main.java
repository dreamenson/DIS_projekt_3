import simulation.MySimulation;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        MySimulation sim = new MySimulation(5,5,5,20);
        sim.simulate(2);
    }
}