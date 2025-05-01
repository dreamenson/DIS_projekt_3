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
    private final WStat workRatio;
    private final Stat workRatioStatistics = new Stat();
    private Product product;
    private Activity activity;

    public Worker(WorkerType type, int index, Simulation simulation) {
        this.type = type;
        this.index = index;
        product = null;
        activity = null;
        workRatio = new WStat(simulation);
    }

    public void setBusy(Product product, Activity activity) {
        addValueToStatistic(100);
        isFree = false;
        this.product = product;
        this.activity = activity;
    }

    public void setFree() {
        addValueToStatistic(0);
        isFree = true;
        product = null;
        activity = null;
    }

    private void addValueToStatistic(double value) {
        workRatio.addSample(value);
    }

    public void updateAfterReplication() {
        workRatio.updateAfterReplication();
        workRatioStatistics.addSample(workRatio.mean());
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

    public Stat getWorkRatioStatistics() {
        return workRatioStatistics;
    }

    public void reset() {
        isFree = true;
        place = null;
        workRatio.clear();
        product = null;
        activity = null;
    }
}
