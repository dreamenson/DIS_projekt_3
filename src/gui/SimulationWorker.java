package gui;

import OSPABA.ISimDelegate;
import OSPABA.SimState;
import OSPABA.Simulation;
import simulation.MySimulation;

import javax.swing.*;

public class SimulationWorker extends SwingWorker<Void, Double> implements ISimDelegate {
    private final int reps, stepSize;
    private final int workersA, workersB, workersC, placeCnt;
    private final double skipValue;
    private final ChartManager chartManager;
    private boolean mode, isStopped;
    private MySimulation simulation;

    public SimulationWorker(int reps, int stepSize, double skipValue, int workersA, int workersB, int workersC, int placeCnt,ChartManager chartManager) {
        this.reps = reps;
        this.stepSize = stepSize;
        this.skipValue = skipValue;
        this.workersA = workersA;
        this.workersB = workersB;
        this.workersC = workersC;
        this.placeCnt = placeCnt;
        this.chartManager = chartManager;
    }

    @Override
    protected Void doInBackground() {
        mode = (reps == 1);
        isStopped = false;
        chartManager.clear();

        if (mode) chartManager.setSingleReplicationChart();
        else chartManager.setFullReplicationsChart();

        try {
            simulation = new MySimulation(workersA, workersB, workersC, placeCnt);
            simulation.registerDelegate(this);
            simulation.simulate(reps);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void stop() {
        isStopped = true;
        simulation.stopSimulation();
    }

    public void pause() {
        simulation.pauseSimulation();
    }

    public void resume() {
        simulation.resumeSimulation();
    }

    public void setSpeed(int speed) {
//        simulator.setSpeed(speed);
    }

    @Override
    public void simStateChanged(Simulation simulation, SimState simState) {
//        System.out.println("simStateChanged");
        if (mode) return;
        else updateMoreReps(simulation);
    }

    @Override
    public void refresh(Simulation simulation) {
        System.out.println("refresh");
//        if (mode) updateOneRep(simulation);
        if (mode) return;
        else updateMoreReps(simulation);
    }


//    private void updateOneRep(JoineryEventSimulator simulator) {
//        try {
//            SwingUtilities.invokeAndWait(() -> chartManager.updateOneRep(simulator));
//        } catch (InterruptedException | InvocationTargetException e) {
//            e.printStackTrace();
//            Thread.currentThread().interrupt();
//        }
//    }
//
    private void updateMoreReps(Simulation simulator) {
        SwingUtilities.invokeLater(() -> {
            double rep = simulator.currentReplication() + 1;
            if ((rep % stepSize == 0 && rep >= reps * skipValue) || isStopped) {
                chartManager.updateFullReps((MySimulation) simulator);
            }
        });
    }
}
