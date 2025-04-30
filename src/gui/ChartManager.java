package gui;

import OSPStat.Stat;
import entities.worker.Worker;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import simulation.MySimulation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ChartManager {
    private final JTabbedPane jTabbedPane;

    // more reps
    private final JFreeChart chart;
    private final XYSeries fullSeries;
    private double maxY = Double.MIN_VALUE;
    private double minY = Double.MAX_VALUE;
    private JLabel replicationLabel1, replicationLabel2;
    private JTable productionStatsTable, workerStatsTable;
    private JTextArea workerAText, workerCText, workerBText;
    private JTextArea unstartedText, varnishText, assemblyText, armourText;

    // one rep
    private JLabel simulationTimeLabel, simulationTimeLabel2;
    private JLabel orderCountLabel, finishedCountLabel, unstartedOrdersLabel, durationLabel;
    private JTextArea workerAText2, workerCText2, workerBText2;

    public ChartManager(JTabbedPane jTabbedPane) {
        this.jTabbedPane = jTabbedPane;
        fullSeries = new XYSeries("All reps");

        chart = ChartFactory.createXYLineChart("Order production time", "Replications", "Mean value [h]", new XYSeriesCollection(fullSeries));

        setupMoreRepsPanel();
    }

    private void setupSingleRepPanel() {
        jTabbedPane.removeAll();

        JPanel jPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel simulationTimePanel = new JLabel("Simulation time:");
        simulationTimeLabel = new JLabel("0");
        topPanel.add(simulationTimePanel);
        topPanel.add(simulationTimeLabel);

        jPanel.add(topPanel, BorderLayout.NORTH);

        // queues
        JPanel fifoPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        unstartedText = new JTextArea();
        varnishText = new JTextArea();
        assemblyText = new JTextArea();
        armourText = new JTextArea();
        unstartedText.setBorder(BorderFactory.createTitledBorder("Unstarted orders FIFO"));
        varnishText.setBorder(BorderFactory.createTitledBorder("Varnishing FIFO"));
        assemblyText.setBorder(BorderFactory.createTitledBorder("Assembly FIFO"));
        armourText.setBorder(BorderFactory.createTitledBorder("Armour FIFO"));

        JScrollPane unstartedScroll = new JScrollPane(unstartedText);
        JScrollPane varnishScroll = new JScrollPane(varnishText);
        JScrollPane assemblyScroll = new JScrollPane(assemblyText);
        JScrollPane armourScroll = new JScrollPane(armourText);

        fifoPanel.add(unstartedScroll);
        fifoPanel.add(varnishScroll);
        fifoPanel.add(assemblyScroll);
        fifoPanel.add(armourScroll);

        jPanel.add(fifoPanel, BorderLayout.CENTER);

        // workers
        JPanel workerTextPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        workerAText = new JTextArea();
        workerBText = new JTextArea();
        workerCText = new JTextArea();
        workerAText.setBorder(BorderFactory.createTitledBorder("Workers A"));
        workerBText.setBorder(BorderFactory.createTitledBorder("Workers B"));
        workerCText.setBorder(BorderFactory.createTitledBorder("Workers C"));

        JScrollPane workerAScroll = new JScrollPane(workerAText);
        JScrollPane workerBScroll = new JScrollPane(workerBText);
        JScrollPane workerCScroll = new JScrollPane(workerCText);

        workerTextPanel.add(workerAScroll);
        workerTextPanel.add(workerBScroll);
        workerTextPanel.add(workerCScroll);

        workerTextPanel.setPreferredSize(new Dimension(1000, 350));
        jPanel.add(workerTextPanel, BorderLayout.SOUTH);

        jTabbedPane.addTab("One rep", jPanel);

        // stats
        JPanel jPanel2 = new JPanel(new BorderLayout());

        JPanel jPanelFlow = new JPanel(new GridLayout(0, 1));
        JPanel topPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel simulationTimePanel2 = new JLabel("Simulation time:");
        simulationTimeLabel2 = new JLabel("0");
        topPanel2.add(simulationTimePanel2);
        topPanel2.add(simulationTimeLabel2);
        jPanelFlow.add(topPanel2);

        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel orderCountPanel = new JLabel("Order count:");
        orderCountLabel = new JLabel("0");
        panel2.add(orderCountPanel);
        panel2.add(orderCountLabel);
        jPanelFlow.add(panel2);

        JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel finishedCountPanel = new JLabel("Order finished:");
        finishedCountLabel = new JLabel("0");
        panel3.add(finishedCountPanel);
        panel3.add(finishedCountLabel);
        jPanelFlow.add(panel3);

        JPanel panel4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel unstartedOrdersPanel = new JLabel("Unstarted orders mean:");
        unstartedOrdersLabel = new JLabel("0");
        panel4.add(unstartedOrdersPanel);
        panel4.add(unstartedOrdersLabel);
        jPanelFlow.add(panel4);

        JPanel panel5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel durationPanel = new JLabel("Order production time mean:");
        durationLabel = new JLabel("0");
        panel5.add(durationPanel);
        panel5.add(durationLabel);
        jPanelFlow.add(panel5);

        JPanel workersPanel2 = new JPanel(new BorderLayout());
        JPanel workerTextPanel2 = new JPanel(new GridLayout(1, 3, 10, 10));
        workerAText2 = new JTextArea();
        workerBText2 = new JTextArea();
        workerCText2 = new JTextArea();
        workerAText2.setBorder(BorderFactory.createTitledBorder("Workers A"));
        workerBText2.setBorder(BorderFactory.createTitledBorder("Workers B"));
        workerCText2.setBorder(BorderFactory.createTitledBorder("Workers C"));

        JScrollPane workerAScroll2 = new JScrollPane(workerAText2);
        JScrollPane workerBScroll2 = new JScrollPane(workerBText2);
        JScrollPane workerCScroll2 = new JScrollPane(workerCText2);

        workerTextPanel2.add(workerAScroll2);
        workerTextPanel2.add(workerBScroll2);
        workerTextPanel2.add(workerCScroll2);

        workerTextPanel2.setPreferredSize(new Dimension(1000, 350));
        workersPanel2.add(workerTextPanel2, BorderLayout.SOUTH);

        jPanel2.add(jPanelFlow, BorderLayout.NORTH);
        jPanel2.add(workersPanel2, BorderLayout.SOUTH);

        jTabbedPane.addTab("Stats", jPanel2);
    }

    private void setupMoreRepsPanel() {
        jTabbedPane.removeAll();

        JPanel productionPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel replicationLabel = new JLabel("Current Replication:");
        replicationLabel1 = new JLabel("0");
        topPanel.add(replicationLabel);
        topPanel.add(replicationLabel1);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(1000, 600));

        productionPanel.add(topPanel, BorderLayout.NORTH);
        productionPanel.add(chartPanel, BorderLayout.CENTER);

        String[] columnNames = {"Metric", "Min", "Max", "Mean", "Conf. interval - 95%"};
        Object[][] data = {
                {"Production Time [h]", "N/A", "N/A", "N/A", "N/A"},
                {"Unstarted Orders [qty]", "N/A", "N/A", "N/A", "N/A"},
                {"Total Orders [#]", "N/A", "N/A", "N/A", "N/A"},
                {"Finished Orders [#]", "N/A", "N/A", "N/A", "N/A"}
        };
        productionStatsTable = new JTable(new DefaultTableModel(data, columnNames));
        JScrollPane statsScrollPane = new JScrollPane(productionStatsTable);
        statsScrollPane.setPreferredSize(new Dimension(1000, 100));
        productionPanel.add(statsScrollPane, BorderLayout.SOUTH);

        jTabbedPane.addTab("Production", productionPanel);

        JPanel workersPanel = new JPanel(new BorderLayout());
        JPanel topPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel replicationLabel3 = new JLabel("Current Replication:");
        replicationLabel2 = new JLabel("0");
        topPanel2.add(replicationLabel3);
        topPanel2.add(replicationLabel2);

        workersPanel.add(topPanel2, BorderLayout.NORTH);

        String[] columnNames2 = {"Workers Group Work Ratio <0,1>", "Min", "Max", "Mean", "Conf. interval - 95%"};
        Object[][] data2 = {
                {"Workers A", "N/A", "N/A", "N/A", "N/A"},
                {"Workers B", "N/A", "N/A", "N/A", "N/A"},
                {"Workers C", "N/A", "N/A", "N/A", "N/A"}
        };
        workerStatsTable = new JTable(new DefaultTableModel(data2, columnNames2));
        JScrollPane statsScrollPane2 = new JScrollPane(workerStatsTable);
        statsScrollPane2.setPreferredSize(new Dimension(1000, 100));
        workersPanel.add(statsScrollPane2, BorderLayout.CENTER);

        JPanel workerTextPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        workerAText = new JTextArea();
        workerBText = new JTextArea();
        workerCText = new JTextArea();
        workerAText.setBorder(BorderFactory.createTitledBorder("Workers A"));
        workerBText.setBorder(BorderFactory.createTitledBorder("Workers B"));
        workerCText.setBorder(BorderFactory.createTitledBorder("Workers C"));

        JScrollPane workerAScroll = new JScrollPane(workerAText);
        JScrollPane workerBScroll = new JScrollPane(workerBText);
        JScrollPane workerCScroll = new JScrollPane(workerCText);

        workerTextPanel.add(workerAScroll);
        workerTextPanel.add(workerBScroll);
        workerTextPanel.add(workerCScroll);

        workerTextPanel.setPreferredSize(new Dimension(1000, 480));
        workersPanel.add(workerTextPanel, BorderLayout.SOUTH);

        jTabbedPane.addTab("Workers", workersPanel);
    }

    public void setFullReplicationsChart() {
        setupMoreRepsPanel();
    }

    public void setSingleReplicationChart() {
        setupSingleRepPanel();
    }

    public void addDataPoint(double x, double y) {
        fullSeries.add(x, y);
        boolean update = false;

        if (y < minY) {
            minY = y;
            update = true;
        }
        if (y > maxY) {
            maxY = y;
            update = true;
        }

        if (update) updateYScale();
    }

    public void updateYScale() {
        if (minY == maxY) {
            double rangePadding = 1e-5; // small padding if same min and max
            minY -= rangePadding;
            maxY += rangePadding;
        }
        chart.getXYPlot().getRangeAxis().setRange(minY, maxY);
    }

    public void clear() {
        fullSeries.clear();
        minY = Double.MAX_VALUE;
        maxY = Double.MIN_VALUE;
    }

    public void updateFullReps(MySimulation simulator) {
        int rep = simulator.currentReplication() + 1;
        double mean = simulator.getOrderDuration().mean();
        addDataPoint(rep, mean);
        replicationLabel1.setText("" + rep);
        replicationLabel2.setText("" + rep);

        updateStatistics(
                new Stat[] {
                        simulator.getOrderDuration(),
                        simulator.getUnstartedOrders(),
                        simulator.getOrderCount(),
                        simulator.getOrderFinishedCount()
                },
                productionStatsTable
        );
        updateStatistics(
                new Stat[] {
                        simulator.getaWorkRatio(),
                        simulator.getbWorkRatio(),
                        simulator.getcWorkRatio()
                },
                workerStatsTable
        );
        updateWorkers(simulator);
    }

    private void updateStatistics(Stat[] stats, JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (int i = 0; i < stats.length; i++) {
            model.setValueAt(stats[i].min(), i, 1);
            model.setValueAt(stats[i].max(), i, 2);
            model.setValueAt(stats[i].mean(), i, 3);
            model.setValueAt(formatConfInt(stats[i].confidenceInterval_95()), i, 4);
        }
    }

    private void updateWorkers(MySimulation simulator) {
        updateWorkerText(simulator.aWAgent().getWorkers(), workerAText);
        updateWorkerText(simulator.bWAgent().getWorkers(), workerBText);
        updateWorkerText(simulator.cWAgent().getWorkers(), workerCText);
    }

    private static String formatConfInt(double[] data) {
        StringBuilder sb = new StringBuilder("<");
        for (int i = 0; i < data.length; i++) {
            sb.append(String.format("%.4f", data[i]));
            if (i < data.length - 1) {
                sb.append(",");
            }
        }
        sb.append(">");
        return sb.toString();
    }

    private void updateWorkerText(List<Worker> workers, JTextArea textArea) {
        textArea.setText(null);
        for (Worker worker : workers) {
            Stat stat = worker.getWorkRatioStatistics();
            textArea.append(String.format("%d:  %.4f  - %s%n", worker.getIndex(), stat.mean(), formatConfInt(stat.confidenceInterval_95())));
        }
        textArea.setCaretPosition(0);
    }
//
//    public void updateOneRep(JoineryEventSimulator simulator) {
//        String text = parseTime(simulator.getCurrentTime());
//        simulationTimeLabel.setText(text);
//        simulationTimeLabel2.setText(text);
//        updateWorkersJobs(simulator);
//        updateFIFOs(simulator);
//        updateWorkersWorkRatio(simulator);
//        updateStats(simulator);
//    }
//
//    private void updateStats(JoineryEventSimulator simulator) {
//        orderCountLabel.setText(String.format("%d", simulator.getOrderCount()));
//        finishedCountLabel.setText(String.format("%d", simulator.getProductionCount()));
//        unstartedOrdersLabel.setText(String.format("%.4f", simulator.getNewOrdersFIFORepStatistics().getMean()));
//        durationLabel.setText(String.format("%.4f", simulator.getOrderCompletionDurationRepStatistics().getMean()));
//    }
//
//    private void updateWorkersWorkRatio(JoineryEventSimulator simulator) {
//        updateWorkerWorkRatio(simulator.getWorkersAList(), workerAText2);
//        updateWorkerWorkRatio(simulator.getWorkersBList(), workerBText2);
//        updateWorkerWorkRatio(simulator.getWorkersCList(), workerCText2);
//    }
//
//    private void updateWorkerWorkRatio(List<Worker> workers, JTextArea textArea) {
//        textArea.setText(null);
//        for (Worker worker : workers) {
//            textArea.append(String.format("%d: actual mean %.4f%n", worker.getIndex(), worker.getWorkRatio()));
//        }
//        textArea.setCaretPosition(0);
//    }
//
//    private void updateFIFOs(JoineryEventSimulator simulator) {
//        updateFIFO(simulator.getNewOrdersFIFO(), unstartedText);
//        updateFIFO(simulator.getVarnishingFIFO(), varnishText);
//        updateFIFO(simulator.getAssemblyFIFO(), assemblyText);
//        updateFIFO(simulator.getArmourFIFO(), armourText);
//    }
//
//    private void updateFIFO(Queue<Product> products, JTextArea textArea) {
//        textArea.setText(null);
//        textArea.append(String.format("Size = %d%n -- HEAD --%n", products.size()));
//        for (Product product : products) {
//            textArea.append(parseProduct(product) + "\n");
//        }
//        textArea.setCaretPosition(0);
//    }
//
//    private void updateWorkersJobs(JoineryEventSimulator simulator) {
//        updateWorkerJobText(simulator.getWorkersAList(), workerAText);
//        updateWorkerJobText(simulator.getWorkersBList(), workerBText);
//        updateWorkerJobText(simulator.getWorkersCList(), workerCText);
//    }
//
//    private void updateWorkerJobText(List<Worker> workers, JTextArea textArea) {
//        textArea.setText(null);
//        for (Worker worker : workers) {
//            if (worker.isFree()) {
//                textArea.append(String.format("%d:   -- free --%n", worker.getIndex()));
//            } else {
//                textArea.append(String.format("%d: %s %s%n", worker.getIndex(),
//                        worker.getActivity().toString().toLowerCase(), parseProduct(worker.getProduct())));
//            }
//        }
//        textArea.setCaretPosition(0);
//    }
//
//    private static String parseProduct(Product product) {
//        return String.format("#%d-%s on WP %d", product.getId(), product.getType().name(),
//                product.getPlace() == null ? null : product.getPlace().getId());
//    }

    public static String parseTime(double time) {
        int workDaySeconds = 8*60*60;
        int workWeekSeconds = 5 * workDaySeconds;

        int totalWeeks = (int) (time / workWeekSeconds) + 1;
        int remainingSeconds = (int) (time % workWeekSeconds);
        int totalDays = (remainingSeconds / workDaySeconds) + 1;
        int remainingWorkSeconds = remainingSeconds % workDaySeconds;
        int hours = 6 + (remainingWorkSeconds / 3600);
        int minutes = (remainingWorkSeconds % 3600) / 60;

        return String.format("%d. week, %d. day, %02d:%02d", totalWeeks, totalDays, hours, minutes);
    }
}

