package entities.place;

import OSPABA.Simulation;
import OSPStat.Stat;
import OSPStat.WStat;
import entities.order.Product;

public class Place implements Comparable<Place> {
    private final int id;
    private Product product;
    private final WStat busyRatio;
    private final Stat busyRatioStat = new Stat();

    public Place(int id, Simulation simulation) {
        this.id = id;
        busyRatio = new WStat(simulation);
    }

    public int getId() {
        return id;
    }

    public boolean isFree() {
        return product == null;
    }

    public void assignProduct(Product product) {
        busyRatio.addSample(100);
        this.product = product;
    }

    public void release() {
        busyRatio.addSample(0);
        product = null;
    }

    public void updateAfterReplication() {
        busyRatio.updateAfterReplication();
        busyRatioStat.addSample(busyRatio.mean());
    }

    public void reset() {
        release();
        busyRatio.clear();
    }

    public double getBusyRatio() {
        return busyRatio.mean();
    }

    public Stat getBusyRatioStat() {
        return busyRatioStat;
    }

    @Override
    public int compareTo(Place other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                '}';
    }
}
