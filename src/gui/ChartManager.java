package gui;

import OSPABA.Simulation;
import OSPStat.Stat;
import entities.order.Order;
import entities.order.Product;
import entities.place.Place;
import entities.worker.Worker;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import simulation.MyMessage;
import simulation.MySimulation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.PriorityQueue;

public class ChartManager {
    private final JTabbedPane jTabbedPane;

    // more reps
    private final JFreeChart chart;
    private final XYSeries fullSeries;
    private double maxY = Double.MIN_VALUE;
    private double minY = Double.MAX_VALUE;
    private JLabel replicationLabel1, replicationLabel2, replicationLabel5;
    private JTable productionStatsTable, workerStatsTable, placesStatsTable;
    private JTextArea workerAText, workerCText, workerBText;
    private JTextArea firstHalfText, secondHalfText;
    private JTextArea unstartedText, aPriorText, bPriorText, cPriorText;

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

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel simulationTimePanel = new JLabel("Simulation time:");
        simulationTimeLabel = new JLabel("0");
        topPanel.add(simulationTimePanel);
        topPanel.add(simulationTimeLabel);

        topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, topPanel.getPreferredSize().height));
        jPanel.add(topPanel);

        // queues
        JPanel fifoPanel = new JPanel(new GridLayout(1, 3, 5, 10));
        unstartedText = new JTextArea();
        aPriorText = new JTextArea();
        bPriorText = new JTextArea();
        cPriorText = new JTextArea();
        unstartedText.setBorder(BorderFactory.createTitledBorder("Unstarted orders List"));
        aPriorText.setBorder(BorderFactory.createTitledBorder("A prior front"));
        bPriorText.setBorder(BorderFactory.createTitledBorder("B prior front"));
        cPriorText.setBorder(BorderFactory.createTitledBorder("C prior front"));

        JScrollPane unstartedScroll = new JScrollPane(unstartedText);
        JScrollPane varnishScroll = new JScrollPane(aPriorText);
        JScrollPane assemblyScroll = new JScrollPane(bPriorText);
        JScrollPane armourScroll = new JScrollPane(cPriorText);

        fifoPanel.add(unstartedScroll);
        fifoPanel.add(varnishScroll);
        fifoPanel.add(assemblyScroll);
        fifoPanel.add(armourScroll);

        fifoPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 400));
        jPanel.add(fifoPanel);

        // workers
        JPanel workerTextPanel = new JPanel(new GridLayout(1, 3, 5, 10));
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

        jPanel.add(workerTextPanel);

        jTabbedPane.addTab("State", jPanel);

        // stats
        JPanel jPanel2 = new JPanel(new BorderLayout());

        JPanel jPanelFlow = new JPanel(new GridLayout(0, 1));
        jPanelFlow.setAlignmentX(Component.LEFT_ALIGNMENT);

        jPanelFlow.add(createLabeledPanel("Simulation time:", simulationTimeLabel2 = new JLabel("0")));
        jPanelFlow.add(createLabeledPanel("Order count [#]:", orderCountLabel = new JLabel("0")));
        jPanelFlow.add(createLabeledPanel("Order finished [#]:", finishedCountLabel = new JLabel("0")));
        jPanelFlow.add(createLabeledPanel("Unstarted orders [qty] mean:", unstartedOrdersLabel = new JLabel("0")));
        jPanelFlow.add(createLabeledPanel("Order production time [h] mean:", durationLabel = new JLabel("0")));

        jPanel2.add(jPanelFlow, BorderLayout.NORTH);

        JPanel workerTextPanel2 = new JPanel(new GridLayout(1, 3, 5, 10));
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

        jPanel2.add(workerTextPanel2, BorderLayout.CENTER);

        jTabbedPane.addTab("Stats", jPanel2);
    }

    private JPanel createLabeledPanel(String text, JLabel label) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(text));
        panel.add(label);
        return panel;
    }

    public void addAnimator(Simulation simulation) {
        JFrame frame = new JFrame("Animator");
        frame.setBounds(0, 0, 1400, 900);
        simulation.animator().canvas().setBounds(0,0,1400,900);

        frame.add(simulation.animator().canvas());
        frame.setVisible(true);
        frame.setLayout(null);
    }

    private void setupMoreRepsPanel() {
        jTabbedPane.removeAll();

        // production
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

        // workers
        JPanel workersPanel = new JPanel(new BorderLayout());
        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));

        JPanel topPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel replicationLabel3 = new JLabel("Current Replication:");
        replicationLabel2 = new JLabel("0");
        topPanel2.add(replicationLabel3);
        topPanel2.add(replicationLabel2);

        topContainer.add(topPanel2);

        String[] columnNames2 = {"Workers Group Work Ratio [%]", "Min", "Max", "Mean", "Conf. interval - 95%"};
        Object[][] data2 = {
                {"Workers A", "N/A", "N/A", "N/A", "N/A"},
                {"Workers B", "N/A", "N/A", "N/A", "N/A"},
                {"Workers C", "N/A", "N/A", "N/A", "N/A"}
        };
        workerStatsTable = new JTable(new DefaultTableModel(data2, columnNames2));
        JScrollPane statsScrollPane2 = new JScrollPane(workerStatsTable);
        statsScrollPane2.setPreferredSize(new Dimension(10, 75));
        topContainer.add(statsScrollPane2);

        workersPanel.add(topContainer, BorderLayout.NORTH);

        JPanel workerTextPanel = new JPanel(new GridLayout(1, 3, 5, 10));
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

        workersPanel.add(workerTextPanel, BorderLayout.CENTER);

        jTabbedPane.addTab("Workers", workersPanel);

        // places
        JPanel placesPanel = new JPanel(new BorderLayout());
        JPanel topContainer2 = new JPanel();
        topContainer2.setLayout(new BoxLayout(topContainer2, BoxLayout.Y_AXIS));

        JPanel topPanel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel replicationLabel4 = new JLabel("Current Replication:");
        replicationLabel5 = new JLabel("0");
        topPanel3.add(replicationLabel4);
        topPanel3.add(replicationLabel5);

        topContainer2.add(topPanel3);

        String[] columnNames3 = {"Work Places Busy Ratio [%]", "Min", "Max", "Mean", "Conf. interval - 95%"};
        Object[][] data3 = {
                {"All places", "N/A", "N/A", "N/A", "N/A"}
        };
        placesStatsTable = new JTable(new DefaultTableModel(data3, columnNames3));
        JScrollPane statsScrollPane3 = new JScrollPane(placesStatsTable);
        statsScrollPane3.setPreferredSize(new Dimension(10, 45));
        topContainer2.add(statsScrollPane3);

        placesPanel.add(topContainer2, BorderLayout.NORTH);

        JPanel placesTextPanel = new JPanel(new GridLayout(1, 2, 5, 10));
        firstHalfText = new JTextArea();
        secondHalfText = new JTextArea();
        firstHalfText.setBorder(BorderFactory.createTitledBorder("1st half"));
        secondHalfText.setBorder(BorderFactory.createTitledBorder("2nd half"));

        JScrollPane firstHalfScroll = new JScrollPane(firstHalfText);
        JScrollPane secondHalfScroll = new JScrollPane(secondHalfText);

        placesTextPanel.add(firstHalfScroll);
        placesTextPanel.add(secondHalfScroll);

        placesPanel.add(placesTextPanel, BorderLayout.CENTER);

        jTabbedPane.addTab("Work places", placesPanel);
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
        replicationLabel5.setText("" + rep);

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
        updateStatistics(
                new Stat[] {
                        simulator.getPlaceBusyRatio()
                },
                placesStatsTable
        );
        updateWorkers(simulator);
        updatePlaces(simulator);
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
            textArea.append(String.format("%d:  %.4f %%  - %s%n", worker.getIndex(), stat.mean(), formatConfInt(stat.confidenceInterval_95())));
        }
        textArea.setCaretPosition(0);
    }

    private void updatePlaces(MySimulation simulator) {
        int cnt = simulator.getPlaceCnt();
        List<Place> places = simulator.placeAgent().getPlaces();

        int mid = (cnt + 1) / 2;
        List<Place> firstHalf = places.subList(0, mid);
        List<Place> secondHalf = places.subList(mid, cnt);

        updatePlacesText(firstHalf, firstHalfText);
        updatePlacesText(secondHalf, secondHalfText);
    }

    private void updatePlacesText(List<Place> places, JTextArea textArea) {
        textArea.setText(null);
        for (Place place : places) {
            Stat stat = place.getBusyRatioStat();
            textArea.append(String.format("%d:  %.4f %%  - %s%n", place.getId(), stat.mean(), formatConfInt(stat.confidenceInterval_95())));
        }
        textArea.setCaretPosition(0);
    }

    public void updateOneRep(MySimulation simulator) {
        String text = parseTime(simulator.currentTime());
        simulationTimeLabel.setText(text);
        simulationTimeLabel2.setText(text);
        updateWorkersJobs(simulator);
        updatePriorQueues(simulator);
        updateOrderList(simulator);
        updateWorkersWorkRatio(simulator);
        updateStats(simulator);
    }

    private void updateOrderList(MySimulation simulation) {
        List<Order> orders = simulation.carpentryAgent().getUnstartedOrders();
        unstartedText.setText(null);
        unstartedText.append(String.format("Size = %d%n", orders.size()));
        for (Order order : orders) {
            unstartedText.append(order.getDetailString());
        }
        unstartedText.setCaretPosition(0);
    }

    private void updateStats(MySimulation simulator) {
        orderCountLabel.setText(String.format("%d", simulator.agentBoss().getOrderCount()));
        finishedCountLabel.setText(String.format("%d", simulator.agentBoss().getOrderFinishedCount()));
        unstartedOrdersLabel.setText(String.format("%.4f", simulator.carpentryAgent().getUnstartedOrdersWStat().mean()));
        durationLabel.setText(String.format("%.4f", simulator.agentBoss().getOrderDurationStat().mean()));
    }

    private void updateWorkersWorkRatio(MySimulation simulator) {
        updateWorkerWorkRatio(simulator.aWAgent().getWorkers(), workerAText2);
        updateWorkerWorkRatio(simulator.bWAgent().getWorkers(), workerBText2);
        updateWorkerWorkRatio(simulator.cWAgent().getWorkers(), workerCText2);
    }

    private void updateWorkerWorkRatio(List<Worker> workers, JTextArea textArea) {
        textArea.setText(null);
        for (Worker worker : workers) {
            textArea.append(String.format("%d: mean %.4f %%%n", worker.getIndex(), worker.getWorkRatio()));
        }
        textArea.setCaretPosition(0);
    }

    private void updatePriorQueues(MySimulation simulator) {
        updatePriorQueue(simulator.aWAgent().getMessages(), aPriorText);
        updatePriorQueue(simulator.bWAgent().getMessages(), bPriorText);
        updatePriorQueue(simulator.cWAgent().getMessages(), cPriorText);
    }

    private void updatePriorQueue(PriorityQueue<MyMessage> messages, JTextArea textArea) {
        textArea.setText(null);
        textArea.append(String.format("Size = %d%n -- HEAD --%n", messages.size()));
        for (MyMessage msg : messages) {
            textArea.append(parseProduct(msg.getProduct()) + "\n");
        }
        textArea.setCaretPosition(0);
    }

    private void updateWorkersJobs(MySimulation simulator) {
        updateWorkerJobText(simulator.aWAgent().getWorkers(), workerAText);
        updateWorkerJobText(simulator.bWAgent().getWorkers(), workerBText);
        updateWorkerJobText(simulator.cWAgent().getWorkers(), workerCText);
    }

    private void updateWorkerJobText(List<Worker> workers, JTextArea textArea) {
        textArea.setText(null);
        for (Worker worker : workers) {
            if (worker.isFree()) {
                textArea.append(String.format("%d:   -- free -- on WP %d%n", worker.getIndex(), worker.getPlace().getId()));
            } else {
                textArea.append(String.format("%d: %s %s%n", worker.getIndex(),
                        worker.getActivity().toString().toLowerCase(), parseProduct(worker.getProduct())));
            }
        }
        textArea.setCaretPosition(0);
    }

    private static String parseProduct(Product product) {
        return String.format("#%d_%d-%s on WP %d", product.getOrder().getId(), product.getId(), product.getType().name(),
                product.getPlace() == null ? null : product.getPlace().getId());
    }

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

