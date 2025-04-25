package gui;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class SimulationWorker extends SwingWorker<Void, Double> {
    private final long reps, stepSize;
    private final int workersA, workersB, workersC;
    private final double skipValue;
    private final ChartManager chartManager;
    private boolean mode, isStopped;
//    private JoineryEventSimulator simulator;

    public SimulationWorker(long reps, long stepSize, double skipValue, int workersA, int workersB, int workersC, ChartManager chartManager) {
        this.reps = reps;
        this.stepSize = stepSize;
        this.skipValue = skipValue;
        this.workersA = workersA;
        this.workersB = workersB;
        this.workersC = workersC;
        this.chartManager = chartManager;
    }

    @Override
    protected Void doInBackground() {
        mode = (reps == 1);
        isStopped = false;
        chartManager.clear();

        if (mode) chartManager.setSingleReplicationChart();
        else chartManager.setFullReplicationsChart();

//        try {
//            simulator = new JoineryEventSimulator(workersA, workersB, workersC);
//            simulator.attach(this);
//            simulator.run(reps);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return null;
    }

    public void stop() {
        isStopped = true;
//        simulator.stop();
    }

    public void changePause() {
//        simulator.changePause();
    }

    public void setSpeed(int speed) {
//        simulator.setSpeed(speed);
    }

//    @Override
//    public void update(JoineryEventSimulator simulator) {
//        if (mode) updateOneRep(simulator);
//        else updateMoreReps(simulator);
//    }

//    private void updateOneRep(JoineryEventSimulator simulator) {
//        try {
//            SwingUtilities.invokeAndWait(() -> chartManager.updateOneRep(simulator));
//        } catch (InterruptedException | InvocationTargetException e) {
//            e.printStackTrace();
//            Thread.currentThread().interrupt();
//        }
//    }
//
//    private void updateMoreReps(JoineryEventSimulator simulator) {
//        SwingUtilities.invokeLater(() -> {
//            double rep = simulator.getActualRep();
//            if ((rep % stepSize == 0 && rep >= reps * skipValue) || isStopped) {
//                chartManager.updateFullReps(simulator);
//            }
//        });
//    }
}
