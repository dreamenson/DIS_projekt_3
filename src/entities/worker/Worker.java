package entities.worker;

import OSPABA.Simulation;
import OSPStat.Stat;
import OSPStat.WStat;
import entities.place.Place;
import entities.order.Product;

public class Worker {
    private final WorkerType type;
    private final int index;
    private boolean isFree = true;
    private Place place = null;
//    private final WeightedStatistics workRatio = new WeightedStatistics();
    private final WStat workRatio;
    private final Stat workRatioStatistics = new Stat();
//    private double lastTime = 0;
    private final Simulation simulation;
    private Product product;
    private Activity activity;

    public Worker(WorkerType type, int index, Simulation simulation) {
        this.type = type;
        this.index = index;
        this.simulation = simulation;
        product = null;
        activity = null;
        workRatio = new WStat(simulation);
    }

    public void setBusy(Product product, Activity activity) {
        addValueToStatistic(isFree ? 0 : 1);
        isFree = false;
//        lastTime = simulation.getCurrentTime();
        this.product = product;
        this.activity = activity;
    }

    public void setFree() {
        addValueToStatistic(isFree ? 0 : 1);
        isFree = true;
//        lastTime = simulation.getCurrentTime();
        product = null;
        activity = null;
    }

    private void addValueToStatistic(double value) {
//        double weight = simulation.getCurrentTime() - lastTime;
//        workRatio.addValue(value, weight);
        workRatio.addSample(value);
    }

    public void setLastInterval() {
        addValueToStatistic(isFree ? 0 : 1);
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public WorkerType getType() {
        return type;
    }

    public double getWorkRatio() {
        return workRatio.mean();
    }

    public int getIndex() {
        return index;
    }

    public Product getProduct() {
        return product;
    }

    public Activity getActivity() {
        return activity;
    }

    public boolean isFree() {
        return isFree;
    }

    public void updateStatistics() {
        workRatioStatistics.addSample(workRatio.mean());
    }

    public Stat getWorkRatioStatistics() {
        return workRatioStatistics;
    }

    public void reset() {
        isFree = true;
        place = null;
        workRatio.clear();
//        lastTime = 0;
        product = null;
        activity = null;
    }
}
