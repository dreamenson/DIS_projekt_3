package gui;

import javax.swing.*;
import java.awt.*;

public class SimulationGUI {
    private final JFrame frame;
    private final JTextField workersAInput, workersBInput, workersCInput, placeCntInput;
    private final JTextField replicationsInput, stepSizeInput, skipValueInput;
    private final JButton startButton, pauseButton, stopButton;
    private final JSlider speedSlider;
    private ChartManager chartManager;
    private SimulationWorker simulationWorker;
    private boolean isPaused = false;

    public SimulationGUI() {
        frame = new JFrame("Carpentry Simulation GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLayout(new BorderLayout());

        // Control Panel
        JPanel controlPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Simulation Inputs"));
        workersAInput = new JTextField("6", 5);
        workersBInput = new JTextField("5", 5);
        workersCInput = new JTextField("38", 5);
        placeCntInput = new JTextField("57", 5);
        replicationsInput = new JTextField("500", 5);
        stepSizeInput = new JTextField("2", 5);
        skipValueInput = new JTextField("0.3", 5);
        controlPanel.add(new JLabel("Workers A:")); controlPanel.add(workersAInput);
        controlPanel.add(new JLabel("Replications:")); controlPanel.add(replicationsInput);
        controlPanel.add(new JLabel("Workers B:")); controlPanel.add(workersBInput);
        controlPanel.add(new JLabel("Step Size:")); controlPanel.add(stepSizeInput);
        controlPanel.add(new JLabel("Workers C:")); controlPanel.add(workersCInput);
        controlPanel.add(new JLabel("Skip Value:")); controlPanel.add(skipValueInput);
        controlPanel.add(new JLabel("Place count:")); controlPanel.add(placeCntInput);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        startButton = new JButton("Start");
        pauseButton = new JButton("Pause");
        pauseButton.setEnabled(false);
        stopButton = new JButton("Stop");
        stopButton.setEnabled(false);
        speedSlider = new JSlider(JSlider.HORIZONTAL, 0, 10, 5);
        buttonPanel.add(startButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(new JLabel("Speed:"));
        buttonPanel.add(speedSlider);

        startButton.addActionListener(e -> startSimulation());
        stopButton.addActionListener(e -> stopSimulation());
        pauseButton.addActionListener(e -> pauseSimulation());
        speedSlider.addChangeListener(e -> setSimulationSpeed());

        // Graph Panel
        JTabbedPane jTabbedPane = new JTabbedPane();
        frame.add(jTabbedPane, BorderLayout.CENTER);
        chartManager = new ChartManager(jTabbedPane);

        // Adding Components to Frame
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(controlPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(jTabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void startSimulation() {
        int reps = Integer.parseInt(replicationsInput.getText());
        int stepSize = Integer.parseInt(stepSizeInput.getText());
        double skipValue = Double.parseDouble(skipValueInput.getText());
        int workersA = Integer.parseInt(workersAInput.getText());
        int workersB = Integer.parseInt(workersBInput.getText());
        int workersC = Integer.parseInt(workersCInput.getText());
        int placeCnt = Integer.parseInt(placeCntInput.getText());

        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        pauseButton.setEnabled(true);

        simulationWorker = new SimulationWorker(reps, stepSize, skipValue, workersA, workersB, workersC, placeCnt,chartManager) {
            @Override
            protected void done() {
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                pauseButton.setEnabled(false);
            }
        };
        simulationWorker.execute();
    }

    private void stopSimulation() {
        if (simulationWorker != null) {
            simulationWorker.stop();
        }
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        pauseButton.setEnabled(false);
    }

    private void pauseSimulation() {
        if (simulationWorker != null) {
            if (!isPaused) {
                simulationWorker.pause();
            } else {
                simulationWorker.resume();
            }
        }
        isPaused = !isPaused;
        pauseButton.setText(isPaused ? "Resume" : "Pause");
        stopButton.setEnabled(!isPaused);
    }

    private void setSimulationSpeed() {
        if (simulationWorker != null) {
            int speedValue = (int) (2 * Math.pow(1.77,speedSlider.getMaximum() - speedSlider.getValue()));
            simulationWorker.setSpeed(speedValue);
        }
    }
}
