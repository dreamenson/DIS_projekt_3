package gui;

import OSPABA.ISimDelegate;
import OSPABA.SimState;
import OSPABA.Simulation;
import OSPAnimator.Animator;
import simulation.MySimulation;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class SimulationWorker extends SwingWorker<Void, Double> implements ISimDelegate {
    private final int reps, stepSize;
    private final int workersA, workersB, workersC, placeCnt;
    private final double skipValue;
    private final long seed;
    private final ChartManager chartManager;
    private boolean slowMode, isStopped;
    private MySimulation simulation;

    public SimulationWorker(int reps, int stepSize, double skipValue, long seed, int workersA, int workersB, int workersC, int placeCnt,ChartManager chartManager) {
        this.reps = reps;
        this.stepSize = stepSize;
        this.skipValue = skipValue;
        this.seed = seed;
        this.workersA = workersA;
        this.workersB = workersB;
        this.workersC = workersC;
        this.placeCnt = placeCnt;
        this.chartManager = chartManager;
    }

    @Override
    protected Void doInBackground() {
        slowMode = (reps == 1);
        isStopped = false;
        chartManager.clear();

        if (slowMode) chartManager.setSingleReplicationChart();
        else chartManager.setFullReplicationsChart();

        try {
            if (slowMode) {
                simulation = new MySimulation(workersA, workersB, workersC, placeCnt, seed, true);
                chartManager.addAnimator(simulation);
                simulation.animator().setSynchronizedTime(true);
                setSpeed(115.59);
            } else {
                simulation = new MySimulation(workersA, workersB, workersC, placeCnt, seed, false);
            }
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

    public void setSpeed(double speed) {
        if (slowMode) {
            simulation.setSimSpeed(speed, 0.1);
            simulation.animator().setSimSpeed(speed, 0.1);
        }
    }

    @Override
    public void simStateChanged(Simulation simulation, SimState simState) {
        if (!slowMode) {
            updateMoreReps(simulation);
        }
    }

    @Override
    public void refresh(Simulation simulation) {
        if (slowMode) {
            updateOneRep(simulation);
        }
    }


    private void updateOneRep(Simulation simulator) {
        try {
            SwingUtilities.invokeAndWait(() -> chartManager.updateOneRep((MySimulation) simulator));
        } catch (InterruptedException | InvocationTargetException e) {
//            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    private void updateMoreReps(Simulation simulator) {
        SwingUtilities.invokeLater(() -> {
            double rep = simulator.currentReplication() + 1;
            if ((rep % stepSize == 0 && rep >= reps * skipValue) || isStopped) {
                chartManager.updateFullReps((MySimulation) simulator);
            }
        });
    }
}
